package varliklar;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.image.BufferedImage;
import motor.GorselYukleyici;

// GolemDusman sinifi, yavas hareket eden ancak yuksek cana, hasara ve geri itilme direncine sahip bir golem boss/dusmanidir
public class GolemDusman extends Dusman {
    
    // Kurucu metot: Golem dusmani baslangic koordinatlariyla olusturur
    public GolemDusman(double x, double y) {
        // Ust sinifin kurucu metodunu cagirir: Konum (x, y), Can (150.0), Hiz (0.8), Hasar (25.0), Yaricap (28.0)
        super(x, y, 150.0, 0.8, 25.0, 28.0);
        
        // Goleme ozel geri itilme carpani (cok agir oldugu icin mermiler onu neredeyse hic itmez)
        this.geriItmeCarpani = 0.15;
        
        // Golemin animasyon kare sayisi idle sayfasi ile uyumlu olarak 6 karedir
        this.maksAnimasyonKaresi = 6;
        
        // Golem gorseli icin temel slime bekleme animasyonu sheet dosyasini yukler
        BufferedImage sheet = GorselYukleyici.gorselYukle("assets/FreeCharactersAnimationsAssetPack 23.13.22/SpriteSheets(96x96)/Monster_Slime/With_Shadows/Monster_Slime_Idle-Sheet.png");
        // Eger sheet dosyasi basariyla yuklendiyse
        if (sheet != null) {
            // Slime sheet gorselini programatik olarak goleme ozel gri kaya rengine donusturur
            this.dusmanSheet = GorselYukleyici.gorseliGriyap(sheet);
            // Kirpma veya yukleme sirasinda hata ihtimaline karsi koruma saglar
            try {
                // Eger gri tonlamali sheet basariyla olusturulduysa
                if (this.dusmanSheet != null) {
                    // Ilk kareyi yedek statik gorsel olarak kirpar (37, 42) konumundan 20x20 boyutunda
                    this.dusmanGorseli = this.dusmanSheet.getSubimage(37, 42, 20, 20);
                }
            } catch (Exception e) {
                // Hata durumunda konsola uyari yazdirir
                System.out.println("UYARI: Golem gorseli kesilirken hata olustu. Yedek sekil cizilecek. Detay: " + e.getMessage());
            }
        }
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
