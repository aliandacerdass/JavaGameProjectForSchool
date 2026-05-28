package varliklar;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.image.BufferedImage;
import motor.GorselYukleyici;

// GolemDusman sinifi, yavas hareket eden ancak yuksek cana, hasara ve geri itilme direncine sahip bir golem boss/dusmanidir
public class GolemDusman extends Dusman {
    
    // Golem dusmana ozel gorsel (piksel art)
    private BufferedImage golemGorseli;
    
    // Kurucu metot: Golem dusmani baslangic koordinatlariyla olusturur
    public GolemDusman(double x, double y) {
        // Koordinatlar, Can (150.0), Hiz (0.8), Hasar (25.0), Yaricap (28.0)
        super(x, y, 150.0, 0.8, 25.0, 28.0);
        
        // Goleme ozel geri itilme carpani (cok agir oldugu icin mermiler onu neredeyse hic itmez)
        this.geriItmeCarpani = 0.15;
        
        // Golem gorselini yuklemeyi dener
        this.golemGorseli = GorselYukleyici.gorselYukle("assets/Heroes99_free 23.13.22/h99_golem_enemy.png");
    }
    
    // Golem dusmanin ekranda cizilmesini saglayan metot
    @Override
    public void ciz(Graphics2D g2) {
        // Eger gorsel yuklendiyse resmi cizer
        if (golemGorseli != null) {
            int cizimX = (int) (x - yariCap);
            int cizimY = (int) (y - yariCap);
            g2.drawImage(golemGorseli, cizimX, cizimY, (int) (yariCap * 2), (int) (yariCap * 2), null);
        } else {
            // Resim bulunamazsa koyu gri renkli yedek buyuk bir daire cizer (Kaya/Golem hissi)
            g2.setColor(Color.DARK_GRAY);
            int cizimX = (int) (x - yariCap);
            int cizimY = (int) (y - yariCap);
            g2.fillOval(cizimX, cizimY, (int) (yariCap * 2), (int) (yariCap * 2));
            
            // Gozler (Golem oldugu icin parlak kirmizi iri gozler cizelim)
            g2.setColor(Color.RED);
            g2.fillOval((int) (x - 8), (int) (y - 6), 6, 6);
            g2.fillOval((int) (x + 2), (int) (y - 6), 6, 6);
        }
    }
}
