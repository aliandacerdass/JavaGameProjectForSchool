package motor;

import javax.swing.JFrame;

// Pencere sinifi, oyunun isletim sistemindeki pencere cercevesini yonetir
public class Pencere extends JFrame {
    
    // Kurucu metot: Pencerenin basligini ve temel ozelliklerini ayarlar
    public Pencere() {
        // Pencerenin basligini belirler
        super("Piksel Hayatta Kalma Oyunu");
        
        // Pencere kapatildiginda uygulamanin sonlandirilmasini saglar
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Pencerenin boyutunun kullanici tarafindan degistirilmesini engeller (Piksel hassasiyeti icin)
        this.setResizable(false);
    }
    
    // Pencereyi baslatan ve ekranda gosteren metot
    public void pencereyiBaslat(OyunPaneli oyunPaneli) {
        // Oyun panelini pencereye ekler
        this.add(oyunPaneli);
        
        // Pencere boyutunu icindeki panelin tercih edilen boyutuna gore otomatik ayarlar
        this.pack();
        
        // Pencereyi ekranin merkezine konumlandirir
        this.setLocationRelativeTo(null);
        
        // Pencereyi gorunur hale getirir
        this.setVisible(true);
    }
}
