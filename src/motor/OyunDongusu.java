package motor;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// OyunDongusu sinifi, javax.swing.Timer kullanarak FPS/UPS zamanlamasini yonetir
public class OyunDongusu implements ActionListener {
    
    // javax.swing.Timer nesnesi (60 FPS hedefi icin yaklasik 16 milisaniyede bir tetiklenir)
    private final Timer timer;
    // Oyun dongusunun tetikleyecegi ana oyun paneli referansi
    private final OyunPaneli panel;
    
    // Kurucu metot: Oyun paneli referansini alir ve Timer zamanlayicisini baslatir
    public OyunDongusu(OyunPaneli panel) {
        this.panel = panel;
        
        // 60 FPS hedefi icin 1000ms / 60fps = ~16.6ms gecikme suresi ayarlanir
        // Bu sinif ActionListener arayuzunu implemente ettigi icin 'this' anahtariyla baglanir
        this.timer = new Timer(16, this);
    }
    
    // Zamanlayici her tetiklendiginde (her oyun karesinde) calisan metot
    @Override
    public void actionPerformed(ActionEvent e) {
        // Oyun durumunu gunceller
        oyunGuncelle();
        // Ekrani yeniden cizdirir
        panel.repaint();
    }
    
    // Oyun dongusunu baslatan metot
    public void baslat() {
        if (!timer.isRunning()) {
            timer.start();
            System.out.println("Oyun Dongusu Baslatildi. (60 FPS)");
        }
    }
    
    // Oyun dongusunu duraklatan metot
    public void durdur() {
        if (timer.isRunning()) {
            timer.stop();
            System.out.println("Oyun Dongusu Durduruldu.");
        }
    }
    
    // Oyun nesnelerinin pozisyon, carpisma ve diger mantiksal durumlarini guncelleyen metot
    private void oyunGuncelle() {
        // OyunPaneli icinde tanimlayacagimiz guncelle metodunu cagirir
        panel.guncelle();
    }
}
