package mekanikler;

import varliklar.Oyuncu;
import varliklar.Dusman;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.BasicStroke;
import java.util.ArrayList;

// KalkanSilahi, oyuncunun etrafinda dairesel bir koruma alani olusturur ve iceri giren dusmanlara saniyede iki kez hasar verir
public class KalkanSilahi extends Silah {
    // Kalkanin koruma yaricapi (seviyeyle artar)
    public double kalkanYaricapi;
    
    // Kurucu metot: Kalkan silahini oyuncu baglantisi ve varsayilan statlarla baslatir
    public KalkanSilahi(Oyuncu oyuncu) {
        // Silah adi: "Kalkan", hasar: 4.0, bekleme suresi: 800 ms (hasar sıklığı azaltıldı - Andac)
        super("Kalkan", oyuncu, 4.0, 800);
        this.kalkanYaricapi = 55.0; // Baslangic yaricapi 55px (azaltıldı - Andac)
    }
    
    // Kalkan her tetiklendiginde etraftaki dusmanlarin mesafesini tarar
    @Override
    public void saldir(motor.OyunPaneli panel) {
        ArrayList<Dusman> dusmanlar = panel.dusmanlar;
        // Tum dusmanlari tara
        for (Dusman dusman : dusmanlar) {
            double dx = dusman.x - oyuncu.x;
            double dy = dusman.y - oyuncu.y;
            double mesafeKaresi = dx * dx + dy * dy;
            
            // Eger dusman kalkanin kapsama alanindaysa hasar alir ve hafif geri sekebilir
            if (mesafeKaresi < kalkanYaricapi * kalkanYaricapi && dusman.can > 0) {
                // Hasar ver
                dusman.can -= this.hasar;
                
                // Ekrana hasar sayisi firlatir (Mavi renkli kalkan hasari - Andaç)
                panel.hasarSayilari.add(new HasarSayisi(dusman.x, dusman.y - 12, String.format("-%.0f", this.hasar), new Color(0, 191, 255)));
                
                // Hafifce oyuncunun tersi yonunde geri it (Knockback)
                double mesafe = Math.sqrt(mesafeKaresi);
                if (mesafe > 0) {
                    // Yonu normalize et ve dusmani hafifce (5px) geri it
                    dusman.geriIt(dx / mesafe, dy / mesafe, 5.0);
                }
            }
        }
    }
    
    // Kalkanin ekrandaki gorsel efektini cizen metot (Retro enerjik cam kalkan)
    @Override
    public void ciz(Graphics2D g2) {
        int r = (int) this.kalkanYaricapi;
        
        // 1. Yarı saydam dairesel enerji alanı
        g2.setColor(new Color(0, 191, 255, 30)); // 30/255 saydamlikta mavi
        g2.fillOval((int)(oyuncu.x - r), (int)(oyuncu.y - r), r * 2, r * 2);
        
        // 2. Kalkanın parlayan dış halkası
        g2.setColor(new Color(0, 255, 255, 100)); // Halka kenari
        g2.drawOval((int)(oyuncu.x - r), (int)(oyuncu.y - r), r * 2, r * 2);
        
        // 3. Etrafında dönen 3 adet koruyucu enerji küresi (Andaç)
        double aci = (System.currentTimeMillis() / 350.0) % (Math.PI * 2);
        for (int i = 0; i < 3; i++) {
            double orbAci = aci + i * (2.0 * Math.PI / 3.0);
            int orbX = (int) (oyuncu.x + Math.cos(orbAci) * r);
            int orbY = (int) (oyuncu.y + Math.sin(orbAci) * r);
            
            // Çift halkalı piksel küre
            g2.setColor(Color.WHITE);
            g2.fillOval(orbX - 5, orbY - 5, 10, 10);
            g2.setColor(new Color(0, 255, 255));
            g2.drawOval(orbX - 5, orbY - 5, 10, 10);
        }
    }
    
    // Kalkan seviye atladiginda hasarla birlikte yaricapi da buyur
    @Override
    public void seviyeAtla() {
        super.seviyeAtla();
        // Seviye basina koruma alanini 6 piksel artirir (Andac)
        this.kalkanYaricapi += 6.0;
        System.out.println(this.ad + " yaricapi genisledi! Yeni Yaricap: " + this.kalkanYaricapi);
    }
}
