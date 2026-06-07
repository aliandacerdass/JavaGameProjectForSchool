package mekanikler;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

// HasarSayisi sinifi, dusmanlar hasar aldiginda ekranda yukari dogru suzulen ve yavasca silinen metinleri temsil eder (Andac)
public class HasarSayisi {
    // Dunya koordinatlarindaki konumu
    public double x;
    public double y;
    
    // Gosterilecek metin (Orn: "-20")
    public String metin;
    // Metnin rengi (Silah turune gore degisir)
    public Color renk;
    
    // Kalan omur (kare/frame sayisi)
    public int omur;
    // Maksimum omur
    public int maksOmur;
    
    // Kurucu metot: Konum, metin ve renk ile baslatir
    public HasarSayisi(double x, double y, String metin, Color renk) {
        this.x = x;
        this.y = y;
        this.metin = metin;
        this.renk = renk;
        this.omur = 35; // 35 kare (yaklasik 0.6 saniye) omur
        this.maksOmur = 35;
    }
    
    // Her karede yukari dogru kaymasini ve omrunun azalmasini saglar
    public void guncelle() {
        this.y -= 1.0; // Saniyede yaklasik 60px yukari kayar
        this.omur--;
    }
    
    // Sayiyi ekrana saydamlasma (fade out) efektiyle cizer
    public void ciz(Graphics2D g2) {
        if (omur <= 0) {
            return;
        }
        
        // Saydamlik oranini kalan omre gore hesaplar (255 tam gorunur, 0 tamamen seffaf)
        int alfa = (int) (255.0 * ((double) omur / maksOmur));
        alfa = Math.max(0, Math.min(255, alfa));
        
        // Renkleri olusturur
        Color metinRengi = new Color(renk.getRed(), renk.getGreen(), renk.getBlue(), alfa);
        Color golgeRengi = new Color(0, 0, 0, alfa);
        
        // Font stilini ve boyutunu ayarlar
        g2.setFont(new Font("Arial", Font.BOLD, 13));
        
        // Okunabilirlik icin siyah golge cizer (1px kaymis sekilde)
        g2.setColor(golgeRengi);
        g2.drawString(metin, (int) x + 1, (int) y + 1);
        
        // Ana renkli hasar metnini cizer
        g2.setColor(metinRengi);
        g2.drawString(metin, (int) x, (int) y);
    }
}
