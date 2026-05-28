package varliklar;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.image.BufferedImage;
import motor.GorselYukleyici;

// Dusman sinifi, oyundaki tum dusmanlarin temelini olusturur
public class Dusman {
    // Dusmanin X koordinati (harita uzerindeki yatay konum)
    public double x;
    // Dusmanin Y koordinati (harita uzerindeki dikey konum)
    public double y;
    
    // Dusmanin mevcut can degeri
    public double can;
    // Dusmanin hareket hizi
    public double hiz;
    // Dusmanin oyuncuya verecegi hasar
    public double hasar;
    
    // Dusmanin carpisma yariçap degeri (daire tabanli carpisma tespiti icin)
    public double yariCap;
    
    // Dusman karakterinin gorsel resmi (piksel art)
    private BufferedImage dusmanGorseli;
    
    // Kurucu metot: Dusmani baslangic degerleriyle olusturur
    public Dusman(double x, double y, double can, double hiz, double hasar, double yariCap) {
        this.x = x;
        this.y = y;
        this.can = can;
        this.hiz = hiz;
        this.hasar = hasar;
        this.yariCap = yariCap;
        
        // Dusman gorselini yuklemeyi dener
        // Adim 5'te bu yol daha netlestirilebilir veya farkli bir sprite secilebilir
        this.dusmanGorseli = GorselYukleyici.gorselYukle("assets/Heroes99_free 23.13.22/h99_enemy.png");
    }
    
    // Dusmanin durumunu her karede (tick) gunceller ve oyuncuya dogru hareket etmesini saglar
    public void guncelle(Oyuncu oyuncu) {
        // Dusmandan oyuncuya dogru olan yatay ve dikey mesafeyi hesaplar
        double dx = oyuncu.x - this.x;
        double dy = oyuncu.y - this.y;
        
        // Iki nokta arasindaki dogrusal mesafeyi hesaplar
        double mesafe = Math.sqrt(dx * dx + dy * dy);
        
        // Sifira bolme hatasini onlemek icin mesafe kontrol edilir
        if (mesafe > 0) {
            // Yon vektorunu normalize ederek (uzunlugunu 1 birim yaparak) hiziyla carpar ve konumu gunceller
            this.x += (dx / mesafe) * this.hiz;
            this.y += (dy / mesafe) * this.hiz;
        }
        
        // Dusmanin 3000x3000px harita sinirlarinin disina cikmasini engeller
        // Sinir degerleri 0 ile 3000 arasinda tutulur (Matematiksel sinirlama)
        this.x = Math.max(0, Math.min(this.x, 3000));
        this.y = Math.max(0, Math.min(this.y, 3000));
    }
    
    // Dusmanin bir mermiyle vuruldugunda geriye savrulmasini saglayan metot (Knockback)
    public void geriIt(double yonX, double yonY, double miktar) {
        // Gelen merminin hareket yonuyle dogru orantili olarak dusmani geriye iter
        this.x += yonX * miktar;
        this.y += yonY * miktar;
        
        // Geri itilme sonrasinda da harita sinirlari kontrol edilir
        this.x = Math.max(0, Math.min(this.x, 3000));
        this.y = Math.max(0, Math.min(this.y, 3000));
    }
    
    // Dusmani ekrana cizen metot
    public void ciz(Graphics2D g2) {
        // Eger dusman gorseli basariyla yuklendiyse resmi cizer
        if (dusmanGorseli != null) {
            // Resmi dusmanin merkez koordinatlarina hizalayarak cizer
            int cizimX = (int) (x - yariCap);
            int cizimY = (int) (y - yariCap);
            g2.drawImage(dusmanGorseli, cizimX, cizimY, (int) (yariCap * 2), (int) (yariCap * 2), null);
        } else {
            // Resim bulunamazsa veya yuklenemezse yesil renkli yedek bir daire cizer (Zombi hissi icin)
            g2.setColor(Color.GREEN);
            // Dusmanin dairesini merkezler
            int cizimX = (int) (x - yariCap);
            int cizimY = (int) (y - yariCap);
            g2.fillOval(cizimX, cizimY, (int) (yariCap * 2), (int) (yariCap * 2));
            
            // Dusman oldugunu belli etmek icin icine kirmizi kucuk gozler cizelim
            g2.setColor(Color.RED);
            g2.fillOval((int) (x - 6), (int) (y - 4), 4, 4);
            g2.fillOval((int) (x + 2), (int) (y - 4), 4, 4);
        }
    }
}
