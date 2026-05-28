package motor;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

// TusKontrolcu sinifi, klavyeden alinan basma ve birakma girdilerini dinler
public class TusKontrolcu extends KeyAdapter {
    
    // Klavyedeki tum tuslarin anlik basili olma durumunu tutan boolean dizi
    private final boolean[] tuslar = new boolean[256];
    
    // Hareket yonu tuslarinin basili olup olmadigini kontrol eden yardimci degiskenler
    public boolean yukari, asagi, sola, saga;
    // Oyun yeniden baslatma tusu (R)
    public boolean rTusu;
    
    // Oyun paneli referansi
    private final OyunPaneli panel;
    
    // Kurucu metot: Oyun paneli referansini alir
    public TusKontrolcu(OyunPaneli panel) {
        this.panel = panel;
    }
    
    // Bir tus basildiginda tetiklenen metot
    @Override
    public void keyPressed(KeyEvent e) {
        int kod = e.getKeyCode();
        
        // Tus kodu dizi sinirlari icindeyse ilgili tusu aktif (true) yapar
        if (kod >= 0 && kod < tuslar.length) {
            tuslar[kod] = true;
        }
        
        // Girdileri gunceller
        girdileriGuncelle();
    }
    
    // Bir tus birakildiginda tetiklenen metot
    @Override
    public void keyReleased(KeyEvent e) {
        int kod = e.getKeyCode();
        
        // Tus kodu dizi sinirlari icindeyse ilgili tusu pasif (false) yapar
        if (kod >= 0 && kod < tuslar.length) {
            tuslar[kod] = false;
        }
        
        // Girdileri gunceller
        girdileriGuncelle();
    }
    
    // Basili olan tuslara gore yon degiskenlerini guncelleyen ic metot
    private void girdileriGuncelle() {
        // W tusu veya Yukari yon tusu basiliysa yukari yonu aktif olur
        yukari = tuslar[KeyEvent.VK_W] || tuslar[KeyEvent.VK_UP];
        // S tusu veya Asagi yon tusu basiliysa asagi yonu aktif olur
        asagi = tuslar[KeyEvent.VK_S] || tuslar[KeyEvent.VK_DOWN];
        // A tusu veya Sol yon tusu basiliysa sola yonu aktif olur
        sola = tuslar[KeyEvent.VK_A] || tuslar[KeyEvent.VK_LEFT];
        // D tusu veya Sag yon tusu basiliysa saga yonu aktif olur
        saga = tuslar[KeyEvent.VK_D] || tuslar[KeyEvent.VK_RIGHT];
        // R tusu basiliysa yeniden baslatma tetiklenir
        rTusu = tuslar[KeyEvent.VK_R];
        
        // Ekrani yeniden cizmek icin panelin repaint metodunu tetikleriz
        if (panel != null) {
            panel.repaint();
        }
    }
    
    // Tum tus durumlarini sifirlayan metot
    public void temizle() {
        for (int i = 0; i < tuslar.length; i++) {
            tuslar[i] = false;
        }
        yukari = asagi = sola = saga = rTusu = false;
    }
}
