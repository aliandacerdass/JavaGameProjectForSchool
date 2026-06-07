package mekanikler;

import varliklar.Oyuncu;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.image.BufferedImage;
import motor.GorselYukleyici;

// DonerBicakMermi sinifi, normal mermilerden farkli olarak oyuncunun etrafinda trigonometrik donus yapar
public class DonerBicakMermi extends Mermi {
    // Bagli oldugu ve etrafinda donecegi oyuncu referansi
    private Oyuncu oyuncu;
    
    // Merminin oyuncu merkezine olan uzakligi (donus yariçapi)
    private double yorungeYaricapi;
    
    // Merminin radyan cinsinden mevcut acisi (0 - 2*PI arasinda degisir)
    private double aci;
    
    // Her guncellemede acinin ne kadar artacagini belirleyen donus hizi (radyan cinsinden)
    private double donusHizi;
    
    // Kurucu metot: Donen bicak mermisini oyuncu baglantisi ve parametreleriyle baslatir
    public DonerBicakMermi(Oyuncu oyuncu, double yorungeYaricapi, double donusHizi, double hasar, double yariCap, double maksimumMenzil, double baslangicAcisi) {
        // Ust sinifin kurucu metodunu cagirir (yon ve mermi hizi donus formuluyle hesaplanacagindan ilk etapta 0 verilir)
        super(oyuncu.x, oyuncu.y, 0.0, 0.0, 0.0, hasar, yariCap, maksimumMenzil);
        
        // Oyuncu baglantisini saklar
        this.oyuncu = oyuncu;
        // Donus yorunge yariçapini atar
        this.yorungeYaricapi = yorungeYaricapi;
        // Donus hizini atar
        this.donusHizi = donusHizi;
        // Merminin baslangic acisini atar (ornegin coklu bicaklarda farkli acilar vermek icin)
        this.aci = baslangicAcisi;
    }
    
    // Donen bicagin konumunu oyuncuya gore trigonometrik olarak gunceller
    @Override
    public void guncelle() {
        // Eger mermi aktif degilse hicbir guncelleme yapmaz
        if (!aktif) {
            return;
        }
        
        // Acıyı donus hizina gore artirir
        this.aci += this.donusHizi;
        
        // Acı 360 dereceyi (2*PI) gecerse degeri sifirlayarak tasmayi onler
        if (this.aci >= Math.PI * 2) {
            this.aci -= Math.PI * 2;
        }
        
        // Trigonometrik formullerle oyuncunun etrafindaki dairesel X ve Y koordinatlarini hesaplar
        // x = oyuncu_merkez_x + cos(aci) * yaricap
        // y = oyuncu_merkez_y + sin(aci) * yaricap
        this.x = oyuncu.x + Math.cos(this.aci) * this.yorungeYaricapi;
        this.y = oyuncu.y + Math.sin(this.aci) * this.yorungeYaricapi;
        
        // Donus esnasinda kat edilen hayali mesafeyi artirir
        // Bu mesafe maksimum menzile ulastiginda bicak yok olacaktir
        this.katEdilenMesafe += Math.abs(this.donusHizi) * 40.0;
        
        // Menzil asildiysa mermiyi pasif hale getirir
        if (this.katEdilenMesafe >= this.maksimumMenzil) {
            this.aktif = false;
        }
    }
    
    // Donen bicak resmi (bir kez statik olarak yuklenir)
    private static BufferedImage bicakGorseli = null;

    static {
        try {
            bicakGorseli = GorselYukleyici.gorselYukle("assets/doner_bicak.png");
        } catch (Exception e) {
            System.out.println("UYARI: doner_bicak.png yuklenemedi, yedek cizime gecilecek.");
        }
    }

    // Donen bicagin cizim metodu (Piksel tarzi metalik donen shuriken efekti)
    @Override
    public void ciz(Graphics2D g2) {
        // Eger aktif degilse cizim yapmaz
        if (!aktif) {
            return;
        }
        
        // Donus hareketini canlandirmak icin local bir Graphics2D kopyasi olusturuyoruz
        Graphics2D gKopya = (Graphics2D) g2.create();
        
        // Dunyadaki koordinata translate ederiz
        gKopya.translate(this.x, this.y);
        
        // Kendi etrafinda hizla donmesi icin aciyi katlayarak dondururuz
        gKopya.rotate(this.aci * 6.0);
        
        int r = (int) this.yariCap;
        
        if (bicakGorseli != null) {
            // Eger resim dosyasi yuklenebildiyse resmi merkezde dondurerek ciz
            gKopya.drawImage(bicakGorseli, -r, -r, r * 2, r * 2, null);
        } else {
            // --- YEDEK ÇİZİM: Eger resim dosyası bulunamazsa poligonlarla shuriken cizer ---
            // --- 1. DIŞ BIÇAK PARLAMA HALKASI ---
            gKopya.setColor(new Color(0, 255, 255, 60)); // Cyan golgesi/parlamasi
            gKopya.fillOval(-r * 2, -r * 2, r * 4, r * 4);
            
            // --- 2. 4 KESKİN UÇ (POLYGONS) ---
            gKopya.setColor(Color.CYAN);
            
            // Dikey bıçaklar (Yukarı ve Aşağı uçlar)
            int[] xNoktalariDikey = { -3, 0, 3 };
            int[] yNoktalariDikey1 = { 0, -r * 2, 0 }; // Yukari
            int[] yNoktalariDikey2 = { 0, r * 2, 0 };  // Asagi
            gKopya.fillPolygon(xNoktalariDikey, yNoktalariDikey1, 3);
            gKopya.fillPolygon(xNoktalariDikey, yNoktalariDikey2, 3);
            
            // Yatay bıçaklar (Sol ve Sağ uçlar)
            int[] yNoktalariYatay = { -3, 0, 3 };
            int[] xNoktalariYatay1 = { 0, -r * 2, 0 }; // Sol
            int[] xNoktalariYatay2 = { 0, r * 2, 0 };  // Sag
            gKopya.fillPolygon(xNoktalariYatay1, yNoktalariYatay, 3);
            gKopya.fillPolygon(xNoktalariYatay2, yNoktalariYatay, 3);
            
            // --- 3. METALİK MERKEZ HALKASI ---
            gKopya.setColor(Color.LIGHT_GRAY);
            gKopya.fillOval(-r / 2, -r / 2, r, r);
            
            // En merkezdeki delik veya parlama noktası
            gKopya.setColor(Color.WHITE);
            gKopya.fillOval(-r / 4, -r / 4, r / 2, r / 2);
            
            // Keskin metal hatlar (White highlights)
            gKopya.setColor(Color.WHITE);
            gKopya.drawLine(0, -r * 2, 0, r * 2);
            gKopya.drawLine(-r * 2, 0, r * 2, 0);
        }
        
        // Kopya grafik baglamini serbest birakiriz
        gKopya.dispose();
    }

    // Vurulan dusmanlarin en son hasar alma zamanlari (Andac)
    private final java.util.HashMap<varliklar.Dusman, Long> sonVurusZamanlari = new java.util.HashMap<>();

    // Dusmana tekrar vurup vuramayacagini denetleyen metot (500 ms bekleme suresi - Andac)
    public boolean dusmanaVurabilirMi(varliklar.Dusman d, long suAn) {
        if (!sonVurusZamanlari.containsKey(d)) {
            return true;
        }
        return (suAn - sonVurusZamanlari.get(d)) >= 500; // Dusman basina yarim saniye bekleme
    }

    // Vurulan dusmani ve zaman damgasini listeye kaydeden metot (Andac)
    public void vurulanDusmaniEkle(varliklar.Dusman d, long suAn) {
        sonVurusZamanlari.put(d, suAn);
    }
}
