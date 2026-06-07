package varliklar;

import motor.OyunPaneli;
import java.util.Random;

// DusmanUretici sinifi, oyuncunun kamera ekraninin hemen disinda belirli araliklarla ve
// zamanla zorlasan dusman dalgalari (spawn) uretir
public class DusmanUretici {
    // Bagli oldugu oyun paneli referansi
    private final OyunPaneli panel;
    // Rastgele sayi uretici
    private final Random rastgele;
    
    // En son ne zaman dusman uretildigini tutan zaman damgasi
    private long sonUretimZamani;
    // Iki uretim arasindaki bekleme suresi (milisaniye cinsinden)
    private long uretimAraligi;
    // Oyunun baslangic zaman damgasi (zorlugu gecen sureye gore ayarlamak icin)
    private long baslangicZamani;
    
    // Kurucu metot: Referanslari ve zaman damgalarini hazirlar
    public DusmanUretici(OyunPaneli panel) {
        this.panel = panel;
        this.rastgele = new Random();
        this.baslangicZamani = System.currentTimeMillis();
        this.sonUretimZamani = System.currentTimeMillis();
        this.uretimAraligi = 1800; // Baslangicta her 1.8 saniyede bir yeni dusman dalgasi doğar
    }

    // Dusman uretim zamanlayicilarini ve zorlugu baslangic degerlerine sifirlar (Andac)
    public void sifirla() {
        this.baslangicZamani = System.currentTimeMillis();
        this.sonUretimZamani = System.currentTimeMillis();
        this.uretimAraligi = 1800;
    }
    
    // Her oyun karesinde (tick) cagrilan guncelleme metodu
    public void guncelle() {
        long suAnkiZaman = System.currentTimeMillis();
        
        // Eger son uretimden bu yana gecen sure uretim araligini astiysa yeni dusmanlar uretilir
        if (suAnkiZaman - sonUretimZamani >= uretimAraligi) {
            // OPTIMIZASYON: Ekranda cok fazla dusman birikmesini engellemek icin limit koyuyoruz
            if (panel.dusmanlar.size() < 150) {
                dusmanUret();
            }
            sonUretimZamani = suAnkiZaman;
            
            // Gecen sureye bagli olarak uretim hizini hafifce artiririz (uretim araligini azaltarak)
            // Minimum 1000 ms (1 saniye) bekleme limiti koyarak oyunun cignenemez hale gelmesini engelleriz
            long gecenSure = suAnkiZaman - baslangicZamani;
            uretimAraligi = Math.max(700, 1800 - (gecenSure / 60000) * 150);
        }

        // OPTIMIZASYON: Oyuncudan cok uzakta kalan ve haritada biriken dusmanlari temizleyerek kasilmayi onleriz
        // Her 2 saniyede bir (120 tick) bu kontrolu yaparak gereksiz islemci yoruculugunu engelleriz
        if (panel.oyunSuresiKareSayisi % 120 == 0) {
            double limitMesafeKaresi = 1200.0 * 1200.0;
            panel.dusmanlar.removeIf(d -> {
                double dx = d.x - panel.oyuncu.x;
                double dy = d.y - panel.oyuncu.y;
                return (dx * dx + dy * dy) > limitMesafeKaresi;
            });
        }
    }
    
    // Kamera koordinatlarinin hemen disinda dusmanlar ureten ic metot
    private void dusmanUret() {
        long suAnkiZaman = System.currentTimeMillis();
        long gecenSure = suAnkiZaman - baslangicZamani; // Milisaniye cinsinden gecen sure
        
        // Zorluk derecesi carpani (Zorluk scaling hızı artırıldı - her 2 dakikada zorluk iki katına çıkar - Andac) - zorlukModu ile olceklenir (Andaç)
        double zorlukCarpani = (1.0 + (double) gecenSure / 120000.0) * panel.zorlukModu;
        
        // Tek dalgada uretilecek dusman sayisi (zorluk artırıldı - Andac)
        // Baslangicta 3 dusman, maksimum ayni anda 25 dusman dogurabilir
        int uretilecekDusmanSayisi = Math.min(25, 3 + (int) (gecenSure / 20000));
        
        // Belirlenen sayida dusman uretir
        for (int i = 0; i < uretilecekDusmanSayisi; i++) {
            // Kamera sinirlarinin hemen disinda rastgele bir dogum noktasi hesaplar
            double[] dogumNoktasi = rastgeleDogumNoktasiHesapla();
            double spawnX = dogumNoktasi[0];
            double spawnY = dogumNoktasi[1];
            
            // Gecen sureye gore hangi dusman turunun dogacagini rastgele secer
            Dusman yeniDusman;
            double olasilik = rastgele.nextDouble();
            
            if (gecenSure < 30000) {
                // Ilk 30 saniye: Sadece standart Zombi (Dusman)
                yeniDusman = standartDusmanOlustur(spawnX, spawnY, zorlukCarpani);
            } else if (gecenSure < 60000) {
                // 30 - 60 saniye arasi: %70 standart, %30 Hizli Dusman
                if (olasilik < 0.70) {
                    yeniDusman = standartDusmanOlustur(spawnX, spawnY, zorlukCarpani);
                } else {
                    yeniDusman = hizliDusmanOlustur(spawnX, spawnY, zorlukCarpani);
                }
            } else {
                // 60 saniye sonrasi: %60 standart, %30 Hizli Dusman, %10 Golem Boss
                if (olasilik < 0.60) {
                    yeniDusman = standartDusmanOlustur(spawnX, spawnY, zorlukCarpani);
                } else if (olasilik < 0.90) {
                    yeniDusman = hizliDusmanOlustur(spawnX, spawnY, zorlukCarpani);
                } else {
                    yeniDusman = golemDusmanOlustur(spawnX, spawnY, zorlukCarpani);
                }
            }
            
            // Olusturulan yeni dusmani paneldeki dusman listesine ekler
            panel.dusmanlar.add(yeniDusman);
        }
    }
    
    // Kamera ekraninin (800x600) disina (+60px ofset payi) rastgele koordinat ureten metot
    private double[] rastgeleDogumNoktasiHesapla() {
        double spawnX = 0;
        double spawnY = 0;
        
        // 0: Ust, 1: Alt, 2: Sol, 3: Sag sinir
        int yon = rastgele.nextInt(4);
        double ofset = 60.0; // Kamera ekraninin disindaki pay
        
        double kamX = panel.kameraX;
        double kamY = panel.kameraY;
        
        switch (yon) {
            case 0: // UST SINIR
                spawnX = kamX - ofset + rastgele.nextDouble() * (OyunPaneli.EKRAN_GENISLIGI + 2 * ofset);
                spawnY = kamY - ofset;
                break;
            case 1: // ALT SINIR
                spawnX = kamX - ofset + rastgele.nextDouble() * (OyunPaneli.EKRAN_GENISLIGI + 2 * ofset);
                spawnY = kamY + OyunPaneli.EKRAN_YUKSEKLIGI + ofset;
                break;
            case 2: // SOL SINIR
                spawnX = kamX - ofset;
                spawnY = kamY - ofset + rastgele.nextDouble() * (OyunPaneli.EKRAN_YUKSEKLIGI + 2 * ofset);
                break;
            case 3: // SAG SINIR
                spawnX = kamX + OyunPaneli.EKRAN_GENISLIGI + ofset;
                spawnY = kamY - ofset + rastgele.nextDouble() * (OyunPaneli.EKRAN_YUKSEKLIGI + 2 * ofset);
                break;
        }
        
        // Dusmanlarin 3000x3000px haritanin disinda dogmasini engeller
        spawnX = Math.max(0, Math.min(spawnX, OyunPaneli.HARITA_BOYUTU));
        spawnY = Math.max(0, Math.min(spawnY, OyunPaneli.HARITA_BOYUTU));
        
        return new double[]{spawnX, spawnY};
    }
    
    // Zorluk carpanina gore standart zombi dusman nesnesi olusturur (Statlar artırıldı - Andac)
    private Dusman standartDusmanOlustur(double x, double y, double zorlukCarpani) {
        // Can (40 * zorluk), Hiz (1.6 * zorluk), Hasar (12 * zorluk), Yaricap (16)
        double can = 40.0 * zorlukCarpani;
        double hiz = 1.6 * Math.min(2.0, 1.0 + (zorlukCarpani - 1.0) * 0.3);
        double hasar = 12.0 * zorlukCarpani;
        return new Dusman(x, y, can, hiz, hasar, 16.0);
    }
    
    // Zorluk carpanina gore hizli zombi dusman nesnesi olusturur (Statlar artırıldı - Andac)
    private HizliDusman hizliDusmanOlustur(double x, double y, double zorlukCarpani) {
        double can = 20.0 * zorlukCarpani;
        double hiz = 3.2 * Math.min(1.8, 1.0 + (zorlukCarpani - 1.0) * 0.2);
        HizliDusman hd = new HizliDusman(x, y);
        hd.can = can;
        hd.hiz = hiz;
        hd.hasar = 7.0 * zorlukCarpani;
        return hd;
    }
    
    // Zorluk carpanina gore golem boss nesnesi olusturur (Statlar artırıldı - Andac)
    private GolemDusman golemDusmanOlustur(double x, double y, double zorlukCarpani) {
        double can = 200.0 * zorlukCarpani;
        double hiz = 0.9 * Math.min(1.5, 1.0 + (zorlukCarpani - 1.0) * 0.1);
        GolemDusman gd = new GolemDusman(x, y);
        gd.can = can;
        gd.hiz = hiz;
        gd.hasar = 35.0 * zorlukCarpani;
        return gd;
    }
}
