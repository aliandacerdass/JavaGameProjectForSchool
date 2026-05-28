package motor;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;

// OyunPaneli sinifi, oyunun cizimlerinin yapildigi ve ekranda gosterildigi ana alandir
public class OyunPaneli extends JPanel {
    
    // Ekran genisligi (piksel cinsinden)
    public static final int EKRAN_GENISLIGI = 800;
    // Ekran yuksekligi (piksel cinsinden)
    public static final int EKRAN_YUKSEKLIGI = 600;
    
    // Klavye girdi kontrolcusu
    public final TusKontrolcu tusKontrol;
    // Fare girdi kontrolcusu
    public final FareKontrolcu fareKontrol;

    // Kurucu metot: Panelin boyutlarini ve arka planini ayarlar
    public OyunPaneli() {
        // Panelin tercih edilen boyutunu belirler
        this.setPreferredSize(new Dimension(EKRAN_GENISLIGI, EKRAN_YUKSEKLIGI));
        // Arka plan rengini siyah yapar
        this.setBackground(Color.BLACK);
        // Odaklanabilirlik ozelligini aktif eder (klavye girdilerini alabilmek icin)
        this.setFocusable(true);
        
        // Klavye kontrolcusunu olusturur
        this.tusKontrol = new TusKontrolcu(this);
        // Fare kontrolcusunu olusturur
        this.fareKontrol = new FareKontrolcu(this);
        
        // Panelin klavye girdilerini dinlemesini saglar
        this.addKeyListener(this.tusKontrol);
        // Panelin fare girdilerini dinlemesini saglar
        this.addMouseListener(this.fareKontrol);
        this.addMouseMotionListener(this.fareKontrol);
    }
    
    // AWT/Swing tarafindan panel cizimi gerektiginde otomatik cagirilan metot
    @Override
    protected void paintComponent(Graphics g) {
        // Ust sinifin paintComponent metodunu cagirarak paneli temizler
        super.paintComponent(g);
        
        // Daha gelismis cizim metotlari icin Graphics nesnesini Graphics2D'ye donustururuz
        Graphics2D g2 = (Graphics2D) g;
        
        // Test cizimi: Ekranin ortasina gecici bir yazi yazar
        g2.setColor(Color.WHITE);
        g2.drawString("Piksel Hayatta Kalma Oyunu - Motor Baslatildi!", 250, 200);
        g2.drawString("Girdi Test Ekrani (Adim 2):", 250, 240);
        g2.drawString("YUKARI (W): " + (tusKontrol.yukari ? "BASILI" : "SERBEST"), 250, 270);
        g2.drawString("ASAGI (S): " + (tusKontrol.asagi ? "BASILI" : "SERBEST"), 250, 290);
        g2.drawString("SOLA (A): " + (tusKontrol.sola ? "BASILI" : "SERBEST"), 250, 310);
        g2.drawString("SAGA (D): " + (tusKontrol.saga ? "BASILI" : "SERBEST"), 250, 330);
        g2.drawString("Son Tiklanan Fare Konumu: X=" + fareKontrol.getTiklamaX() + ", Y=" + fareKontrol.getTiklamaY(), 250, 370);
        
        // Cizim islemlerinin bellek temizligini yapar
        g2.dispose();
    }
}
