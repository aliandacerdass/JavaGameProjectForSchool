package varliklar;

import motor.TusKontrolcu;
import motor.GorselYukleyici;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.image.BufferedImage;

// Oyuncu sinifi, kullanicinin kontrol ettigi ana karakteri temsil eder
public class Oyuncu {
    // Oyuncunun X koordinati (harita uzerindeki yatay konum)
    public double x;
    // Oyuncunun Y koordinati (harita uzerindeki dikey konum)
    public double y;
    
    // Oyuncunun mevcut can degeri
    public double can;
    // Oyuncunun maksimum can kapasitesi
    public double maksCan;
    
    // Oyuncunun hareket hizi
    public double hiz;
    
    // Oyuncunun carpisma yariçap degeri (daire tabanli carpisma tespiti icin)
    public double yariCap;
    
    // Oyuncunun mevcut seviyesi (level)
    public int seviye;
    // Oyuncunun topladigi mevcut tecrube/deneyim puani
    public double deneyim;
    // Bir sonraki seviyeye gecmek icin gereken toplam deneyim baraji
    public double sonrakiSeviyeDeneyimi;
    
    // Oyuncu karakterinin gorsel resmi (piksel art)
    private BufferedImage oyuncuGorseli;
    
    // Kurucu metot: Karakteri baslangic degerleriyle olusturur
    public Oyuncu(double x, double y) {
        // Baslangic konumlarini atar
        this.x = x;
        this.y = y;
        
        // Can statlarini tanimlar
        this.maksCan = 100;
        this.can = 100;
        
        // Temel hareket hizini belirler
        this.hiz = 4.0;
        
        // Daire tabanli carpisma yariçapini belirler (32x32 piksel boyutlari icin ideal yariçap)
        this.yariCap = 16.0;
        
        // Seviye ve tecrube barajlarini baslatir
        this.seviye = 1;
        this.deneyim = 0;
        this.sonrakiSeviyeDeneyimi = 100;
        
        // Oyuncu gorselini yuklemeyi dener
        // Adim 5'te bu yol daha netlestirilebilir veya farkli bir sprite secilebilir
        this.oyuncuGorseli = GorselYukleyici.gorselYukle("assets/Heroes99_free 23.13.22/h99_char.png");
    }
    
    // Oyuncunun hareket ve diger durumlarini her karede (tick) gunceller
    public void guncelle(TusKontrolcu tusKontrol) {
        // Yatay ve dikey hareket yon degiskenleri
        double hareketX = 0;
        double hareketY = 0;
        
        // Klavye girdilerine gore hareket yonlerini belirler (Andac'in TusKontrolcu sinifindaki saga/sola/yukari/asagi kullanilir)
        if (tusKontrol.yukari) {
            hareketY -= 1;
        }
        if (tusKontrol.asagi) {
            hareketY += 1;
        }
        if (tusKontrol.sola) {
            hareketX -= 1;
        }
        if (tusKontrol.saga) {
            hareketX += 1;
        }
        
        // Eger herhangi bir hareket girdisi varsa
        if (hareketX != 0 || hareketY != 0) {
            // Çapraz hareket kontrolu (Eger hem X hem Y ekseninde hareket varsa)
            if (hareketX != 0 && hareketY != 0) {
                // Çapraz harekette hizin 1.41 katina cikmasini engellemek icin normalizasyon degeri (sin(45) = 0.707)
                hareketX *= 0.707;
                hareketY *= 0.707;
            }
            
            // Konumu hiza bagli olarak gunceller
            x += hareketX * hiz;
            y += hareketY * hiz;
            
            // Oyuncunun 3000x3000px harita sinirlarinin disina cikmasini engeller
            // Sinir degerleri 0 ile 3000 arasinda tutulur (Matematiksel sinirlama)
            x = Math.max(0, Math.min(x, 3000));
            y = Math.max(0, Math.min(y, 3000));
        }
    }
    
    // Oyuncuyu ekrana cizen metot
    public void ciz(Graphics2D g2) {
        // Eger oyuncu gorseli basariyla yuklendiyse resmi cizer
        if (oyuncuGorseli != null) {
            // Resmi oyuncunun merkez koordinatlarina hizalayarak cizer
            int cizimX = (int) (x - yariCap);
            int cizimY = (int) (y - yariCap);
            g2.drawImage(oyuncuGorseli, cizimX, cizimY, (int) (yariCap * 2), (int) (yariCap * 2), null);
        } else {
            // Resim bulunamazsa veya yuklenemezse mavi renkli yedek bir daire cizer
            g2.setColor(Color.BLUE);
            // Oyuncunun dairesini merkezler
            int cizimX = (int) (x - yariCap);
            int cizimY = (int) (y - yariCap);
            g2.fillOval(cizimX, cizimY, (int) (yariCap * 2), (int) (yariCap * 2));
            
            // Karakter hissi vermesi icin icine kucuk siyah bir goz cizelim
            g2.setColor(Color.BLACK);
            g2.fillOval((int) (x - 4), (int) (y - 4), 8, 8);
        }
    }
}
