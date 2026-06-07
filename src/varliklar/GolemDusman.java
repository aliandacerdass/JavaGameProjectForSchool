package varliklar;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.image.BufferedImage;
import motor.GorselYukleyici;

// GolemDusman sinifi, yavas hareket eden ancak yuksek cana, hasara ve geri itilme direncine sahip bir golem boss/dusmanidir
public class GolemDusman extends Dusman {
    // Golem gorsel onbellegi (static) - Disk erisimini ve griye donusturme dongulerini engeller (Andaç)
    private static BufferedImage ortakGolemSheet = null;
    private static BufferedImage ortakGolemGorsel = null;
    
    // Kurucu metot: Golem dusmani baslangic koordinatlariyla olusturur
    public GolemDusman(double x, double y) {
        // Ust sinifin kurucu metodunu cagirir: Konum (x, y), Can (150.0), Hiz (0.8), Hasar (25.0), Yaricap (28.0)
        super(x, y, 150.0, 0.8, 25.0, 28.0);
        
        // Goleme ozel geri itilme carpani (破坏力 geriItmeCarpani = 0.15)
        this.geriItmeCarpani = 0.15;
        
        // Golemin animasyon kare sayisi yuruyus sayfasi ile uyumlu olarak 8 karedir (Emre)
        this.maksAnimasyonKaresi = 8;
        
        // Golem gorselini onbellekten yukler veya ilk defa olusturup onbellegini kaydeder (Andaç)
        if (ortakGolemSheet == null) {
            BufferedImage sheet = GorselYukleyici.gorselYukle("assets/FreeCharactersAnimationsAssetPack 23.13.22/SpriteSheets(96x96)/Monster_Slime/With_Shadows/Monster_Slime_Walk-Sheet.png");
            if (sheet != null) {
                ortakGolemSheet = GorselYukleyici.gorseliGriyap(sheet);
                try {
                    if (ortakGolemSheet != null) {
                        ortakGolemGorsel = ortakGolemSheet.getSubimage(37, 42, 20, 20);
                    }
                } catch (Exception e) {
                    System.out.println("UYARI: Golem gorseli kesilirken hata olustu.");
                }
            }
        }
        
        this.dusmanSheet = ortakGolemSheet;
        this.dusmanGorseli = ortakGolemGorsel;
    }
    
    // Gorsel bulunamazsa veya yuklenemezse cizilecek geometrik yedek sekil metodu
    @Override
    protected void yedekGeometrikCiz(Graphics2D g2) {
        // Golem oldugu icin koyu gri renk secer
        g2.setColor(Color.DARK_GRAY);
        // Cizilecek dairenin sol ust kose x koordinatini hesaplar
        int cizimX = (int) (x - yariCap);
        // Cizilecek dairenin sol ust kose y koordinatini hesaplar
        int cizimY = (int) (y - yariCap);
        // Koyu gri renkle golem bedeni olarak yedek daireyi doldurur
        g2.fillOval(cizimX, cizimY, (int) (yariCap * 2), (int) (yariCap * 2));
        
        // Gozler (Golem oldugu icin parlak kirmizi iri gozler cizelim)
        g2.setColor(Color.RED);
        // Sol goz dairesini doldurur
        g2.fillOval((int) (x - 8), (int) (y - 6), 6, 6);
        // Sag goz dairesini doldurur
        g2.fillOval((int) (x + 2), (int) (y - 6), 6, 6);
    }
}
