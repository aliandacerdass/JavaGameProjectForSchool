package mekanikler;

import varliklar.Oyuncu;
import java.awt.Graphics2D;
import java.awt.Color;

// GucMeyvesi, haritada nadiren dogan kirmizi renkli bir meyvedir.
// Oyuncu bu meyveyi topladiginda 5 saniye boyunca gecici hiz kazanir (Andac)
public class GucMeyvesi {
    // Meyvenin X koordinati
    public double x;
    // Meyvenin Y koordinati
    public double y;
    
    // Toplanma/Temas yaricapi
    public double yariCap;
    // Toplanma durumu
    public boolean toplandi;
    
    // Kurucu metot: Meyveyi baslangic konumuyla olusturur
    public GucMeyvesi(double x, double y) {
        this.x = x;
        this.y = y;
        this.yariCap = 10.0; // Temas alanı yarıçapı
        this.toplandi = false;
    }
    
    // Her oyun karesinde (tick) oyuncuyla olan temasi denetler
    public void guncelle(motor.OyunPaneli panel) {
        if (toplandi) {
            return;
        }
        
        Oyuncu oyuncu = panel.oyuncu;
        
        // Oyuncu ile olan uzakligi hesaplar
        double dx = oyuncu.x - this.x;
        double dy = oyuncu.y - this.y;
        double uzaklik = Math.sqrt(dx * dx + dy * dy);
        
        // Eger oyuncu meyveye çarptiysa
        if (uzaklik < (this.yariCap + oyuncu.yariCap)) {
            this.toplandi = true;
            
            // Oyuncuya 5 saniye boyunca (5000 ms) +2.5 hiz artisi uygular
            oyuncu.hizBoostuUygula(2.5, 5000);
            
            // Retro meyve toplama sesini calar (Andaç)
            motor.SesSentezleyici.gucMeyvesi();
            
            System.out.println("Guc Meyvesi toplandi! Karakter anlik hiz kazandi.");
        }
    }
    
    // Ekrana retro kirmizi piksel elma/meyve cizer
    public void ciz(Graphics2D g2) {
        if (toplandi) {
            return;
        }
        
        int cx = (int) (x - yariCap);
        int cy = (int) (y - yariCap);
        int cap = (int) (yariCap * 2);
        
        // 1. Kirmizi ana meyve govdesi (Elma)
        g2.setColor(new Color(220, 20, 60)); // Parlak Kırmızı
        g2.fillOval(cx, cy, cap, cap);
        
        // 2. Kahverengi dal/sap (Stem)
        g2.setColor(new Color(139, 69, 19)); // Kahverengi
        g2.fillRect((int)x - 1, (int)y - (int)yariCap - 3, 2, 4);
        
        // 3. Yesil yaprak (Leaf)
        g2.setColor(new Color(34, 139, 34)); // Orman Yeşili
        g2.fillRect((int)x + 1, (int)y - (int)yariCap - 2, 3, 2);
        
        // 4. Parlama efekti (Pixel highlight)
        g2.setColor(Color.WHITE);
        g2.fillRect((int)x - 4, (int)y - 4, 3, 3);
        
        // Dış siyah çerçeve (Retro hissi)
        g2.setColor(new Color(15, 23, 42, 100));
        g2.drawOval(cx, cy, cap, cap);
    }
}
