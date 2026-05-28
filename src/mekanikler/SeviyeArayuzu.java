package mekanikler;

import motor.OyunPaneli;
import motor.OyunDurumu;
import motor.FareKontrolcu;
import varliklar.Oyuncu;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

// SeviyeArayuzu sinifi, seviye atlandiginda oyunu duraklatip 3 rastgele geliştirme seçenegi sunar
public class SeviyeArayuzu {
    
    // Yükseltme seçeneği detaylarını tutan iç yardımcı sınıf
    public static class GelistirmeSecenegi {
        public String baslik;
        public String aciklama;
        public int tip; // 1: Can Fulle, 2: Hiz Artir, 3: Ates Topu Ekle/Guncelle, 4: Doner Bicak Ekle/Guncelle
        public Rectangle kartAlani;
        
        public GelistirmeSecenegi(String baslik, String aciklama, int tip) {
            this.baslik = baslik;
            this.aciklama = aciklama;
            this.tip = tip;
            // Kart alanının koordinatları aktif edildiğinde atanacaktır
            this.kartAlani = new Rectangle(0, 0, 0, 0);
        }
    }
    
    // Oyun paneli referansi
    private final OyunPaneli panel;
    
    // O an oyuncuya sunulan 3 adet aktif geliştirme seçeneği
    private final ArrayList<GelistirmeSecenegi> sunulanSecenekler;
    
    // Rastgele secim islemleri icin Random nesnesi
    private final Random random;
    
    // Kurucu metot: Oyun paneli referansini alir
    public SeviyeArayuzu(OyunPaneli panel) {
        this.panel = panel;
        this.sunulanSecenekler = new ArrayList<>();
        this.random = new Random();
    }
    
    // Seviye atlama ekranını tetikleyen ve 3 rastgele yükseltme oluşturan metot
    public void aktifEt() {
        // Eski seçenekleri temizler
        sunulanSecenekler.clear();
        
        // Olasi tum gelistirmelerin gecici listesi
        ArrayList<GelistirmeSecenegi> tumSecenekler = new ArrayList<>();
        
        // Her zaman secilebilecek temel stat gelistirmelerini ekler
        tumSecenekler.add(new GelistirmeSecenegi("Saglik Paketi", "Caninizi tamamen yeniler ve doldurur.", 1));
        tumSecenekler.add(new GelistirmeSecenegi("Kevlar Botlar", "Hareket hizinizi kalici olarak artirir.", 2));
        
        // Ateş Topu silah kontrolu
        Silah atesTopu = silahBul("Ates Topu");
        if (atesTopu == null) {
            // Silah yoksa yeni ekleme secenegi sunar
            tumSecenekler.add(new GelistirmeSecenegi("Yeni: Ates Topu", "En yakin dusmana otomatik ates toplari firlatir.", 3));
        } else if (atesTopu.seviye < 5) {
            // Seviye 5'ten kucukse guncelleme secenegi sunar
            tumSecenekler.add(new GelistirmeSecenegi("Guncelle: Ates Topu", "Ates topu hasarini ve atis hizini artirir.", 3));
        }
        
        // Döner Bıçak silah kontrolu
        Silah donerBicak = silahBul("Doner Bicak");
        if (donerBicak == null) {
            // Silah yoksa yeni ekleme secenegi sunar
            tumSecenekler.add(new GelistirmeSecenegi("Yeni: Doner Bicak", "Oyuncu etrafinda donen bicaklar olusturur.", 4));
        } else if (donerBicak.seviye < 5) {
            // Seviye 5'ten kucukse guncelleme secenegi sunar
            tumSecenekler.add(new GelistirmeSecenegi("Guncelle: Doner Bicak", "Bicaklarin hasarini ve donus yaricapini artirir.", 4));
        }
        
        // Olusturulan olasi secenekler arasindan rastgele 3 adet benzersiz secenek secer
        int secilecekAdet = Math.min(3, tumSecenekler.size());
        for (int i = 0; i < secilecekAdet; i++) {
            int rastgeleIndis = random.nextInt(tumSecenekler.size());
            GelistirmeSecenegi secilen = tumSecenekler.remove(rastgeleIndis);
            
            // Secilen kartin ekrandaki yerini ve boyutunu belirler (Yan yana 3 kart)
            // Kart Boyutlari: Genislik 180px, Yukseklik 300px. Y konumu sabit 180px.
            int kartGenislik = 180;
            int kartYukseklik = 300;
            int kartY = 180;
            
            // X konumlarini kart indislerine gore ayarlar
            int kartX = 100 + i * 210; // Kartlar arasinda 30px bosluk birakir
            
            // Kart alanini gunceller
            secilen.kartAlani.setBounds(kartX, kartY, kartGenislik, kartYukseklik);
            
            // Aktif sunulan secenekler listesine ekler
            sunulanSecenekler.add(secilen);
        }
        
        // Oyun durumunu duraklatma (GELISIM) moduna alir
        panel.durum = OyunDurumu.GELISIM;
        // Fare koordinatlarini temizler (tiklama kalintilarini onlemek icin)
        panel.fareKontrol.temizle();
        
        System.out.println("Seviye Atlama Arayuzu Aktif Edildi. Secenekler Hazirlandi.");
    }
    
    // Belirli bir ada sahip silahi panel silah listesinde arayan yardimci metot
    private Silah silahBul(String silahAdi) {
        for (Silah s : panel.silahlar) {
            if (s.ad.equals(silahAdi)) {
                return s;
            }
        }
        return null;
    }
    
    // Kart arayuzundeki fare tiklamalarini algilayan guncelleme metodu
    public void guncelle() {
        // Eger fare tiklamasi algilandiysa
        if (panel.fareKontrol.tiklamayiKontrolEt()) {
            // Tiklanan X ve Y koordinatlarini alir
            int mx = panel.fareKontrol.getTiklamaX();
            int my = panel.fareKontrol.getTiklamaY();
            
            // 3 secenegi kontrol ederek tiklamanin hangi kartin icinde kaldigini sorgular
            for (GelistirmeSecenegi secenek : sunulanSecenekler) {
                // Eger tiklanan yer bu kartin sinirlari icindeyse
                if (secenek.kartAlani.contains(mx, my)) {
                    // Secilen gelistirmeyi oyuncuya uygular
                    gelistirmeyiUygula(secenek);
                    
                    // Oyun durumunu tekrar OYUN moduna getirerek devam ettirir
                    panel.durum = OyunDurumu.OYUN;
                    // Fare tiklama koordinatlarini sıfırlar
                    panel.fareKontrol.temizle();
                    break;
                }
            }
        }
    }
    
    // Secilen gelistirmeyi uygulayan metot
    private void gelistirmeyiUygula(GelistirmeSecenegi secenek) {
        Oyuncu oyuncu = panel.oyuncu;
        
        switch (secenek.tip) {
            case 1: // Can Fulle
                oyuncu.can = oyuncu.maksCan;
                System.out.println("Oyuncunun cani tamamen yenilendi!");
                break;
                
            case 2: // Hiz Artisi
                // Hareketi %15 (yaklasik 0.6 piksel) hizlandirir
                oyuncu.hiz += 0.6;
                System.out.println("Oyuncu hizi kalici olarak artirildi! Yeni Hiz: " + oyuncu.hiz);
                break;
                
            case 3: // Ates Topu Ekle / Seviye Artir
                Silah atesTopu = silahBul("Ates Topu");
                if (atesTopu == null) {
                    // Silah henuz yoksa ekler
                    panel.silahlar.add(new AtesTopu(oyuncu));
                    System.out.println("Yeni Silah: Ates Topu envantere eklendi!");
                } else {
                    // Varsa seviye atlatir
                    atesTopu.seviyeAtla();
                }
                break;
                
            case 4: // Doner Bicak Ekle / Seviye Artir
                Silah donerBicak = silahBul("Doner Bicak");
                if (donerBicak == null) {
                    // Silah henuz yoksa ekler
                    panel.silahlar.add(new DonerBicak(oyuncu));
                    System.out.println("Yeni Silah: Doner Bicak envantere eklendi!");
                } else {
                    // Varsa seviye atlatir
                    donerBicak.seviyeAtla();
                }
                break;
        }
    }
    
    // Seviye atlama arayuzunu ekrana cizen metot (Piksel art tasarim detaylari icerir)
    public void ciz(Graphics2D g2) {
        // 1. Ekranı loşlaştırmak için yarı saydam siyah bir katman çizer
        g2.setColor(new Color(0, 0, 0, 180));
        g2.fillRect(0, 0, OyunPaneli.EKRAN_GENISLIGI, OyunPaneli.EKRAN_YUKSEKLIGI);
        
        // 2. Ana Başlık Çizimi
        g2.setColor(Color.YELLOW);
        // Piksel font hissiyati vermek icin kalın ve buyuk yazariz
        g2.drawString("SEVIYE ATLADINIZ!", 330, 80);
        g2.setColor(Color.WHITE);
        g2.drawString("Karakterinizi guclendirmek icin bir kart secin:", 250, 120);
        
        // 3. 3 Adet Kartın Çizimi
        for (GelistirmeSecenegi secenek : sunulanSecenekler) {
            Rectangle r = secenek.kartAlani;
            
            // Kart arka planı (Koyu mavi retro cam görünümü - Glassmorphism)
            g2.setColor(new Color(15, 23, 42, 245));
            g2.fillRect(r.x, r.y, r.width, r.height);
            
            // Kart kenar çerçevesi (Piksel çizgisini korumak için kalınlığı drawRect ile yaparız)
            g2.setColor(Color.CYAN);
            g2.drawRect(r.x, r.y, r.width, r.height);
            g2.drawRect(r.x + 2, r.y + 2, r.width - 4, r.height - 4); // Cift cerceve piksel stil
            
            // Kart başlığı (Geliştirme Adı)
            g2.setColor(Color.YELLOW);
            g2.drawString(secenek.baslik, r.x + 15, r.y + 45);
            
            // Alt ayırıcı piksel çizgisi
            g2.setColor(Color.CYAN);
            g2.drawLine(r.x + 10, r.y + 70, r.x + r.width - 10, r.y + 70);
            
            // Açıklama Metni (Satır sığdırma ve piksel hizalama)
            g2.setColor(Color.WHITE);
            String[] kelimeler = secenek.aciklama.split(" ");
            StringBuilder satir = new StringBuilder();
            int yaziY = r.y + 110;
            
            for (String kelime : kelimeler) {
                // Basit kelime bazli satir sarma mekanizmasi
                if (satir.length() + kelime.length() > 20) {
                    g2.drawString(satir.toString(), r.x + 15, yaziY);
                    satir = new StringBuilder();
                    yaziY += 25;
                }
                satir.append(kelime).append(" ");
            }
            if (satir.length() > 0) {
                g2.drawString(satir.toString(), r.x + 15, yaziY);
            }
            
            // Kart Altındaki "SEC" Buton Alanı Çizimi
            g2.setColor(new Color(0, 120, 120));
            g2.fillRect(r.x + 20, r.y + r.height - 50, r.width - 40, 30);
            g2.setColor(Color.WHITE);
            g2.drawRect(r.x + 20, r.y + r.height - 50, r.width - 40, 30);
            g2.drawString("SEC", r.x + 75, r.y + r.height - 30);
        }
    }
}
