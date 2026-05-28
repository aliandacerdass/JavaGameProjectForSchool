package varliklar;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.image.BufferedImage;
import motor.GorselYukleyici;

// HizliDusman sinifi, normal dusmanlardan daha hizli ancak daha az cana sahip olan bir dusman turudur
public class HizliDusman extends Dusman {
    
    // Hizli dusmana ozel gorsel (piksel art)
    private BufferedImage hizliDusmanGorseli;
    
    // Kurucu metot: Hizli dusmani baslangic koordinatlariyla olusturur
    public HizliDusman(double x, double y) {
        // Koordinatlar, Can (15.0), Hiz (3.0), Hasar (5.0), Yaricap (12.0)
        super(x, y, 15.0, 3.0, 5.0, 12.0);
        
        // Hizli dusmana ozel geri itilme carpani (hafif oldugu icin mermiler onu daha cok iter)
        this.geriItmeCarpani = 1.3;
        
        // Hizli dusman gorselini yuklemeyi dener
        this.hizliDusmanGorseli = GorselYukleyici.gorselYukle("assets/Heroes99_free 23.13.22/h99_fast_enemy.png");
    }
    
    // Hizli dusmanin ekranda cizilmesini saglayan metot
    @Override
    public void ciz(Graphics2D g2) {
        // Eger gorsel yuklendiyse resmi cizer
        if (hizliDusmanGorseli != null) {
            int cizimX = (int) (x - yariCap);
            int cizimY = (int) (y - yariCap);
            g2.drawImage(hizliDusmanGorseli, cizimX, cizimY, (int) (yariCap * 2), (int) (yariCap * 2), null);
        } else {
            // Resim bulunamazsa mor renkli yedek bir daire cizer
            g2.setColor(Color.MAGENTA);
            int cizimX = (int) (x - yariCap);
            int cizimY = (int) (y - yariCap);
            g2.fillOval(cizimX, cizimY, (int) (yariCap * 2), (int) (yariCap * 2));
            
            // Gozler
            g2.setColor(Color.YELLOW);
            g2.fillOval((int) (x - 4), (int) (y - 3), 3, 3);
            g2.fillOval((int) (x + 1), (int) (y - 3), 3, 3);
        }
    }
}
