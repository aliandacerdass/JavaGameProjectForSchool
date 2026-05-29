package varliklar;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.image.BufferedImage;
import motor.GorselYukleyici;

// HizliDusman sinifi, normal dusmanlardan daha hizli ancak daha az cana sahip olan bir dusman turudur
public class HizliDusman extends Dusman {
    
    // Kurucu metot: Hizli dusmani baslangic koordinatlariyla olusturur
    public HizliDusman(double x, double y) {
        // Ust sinifin kurucu metodunu cagirir: Konum (x, y), Can (15.0), Hiz (3.0), Hasar (5.0), Yaricap (12.0)
        super(x, y, 15.0, 3.0, 5.0, 12.0);
        
        // Hizli dusmana ozel geri itilme carpani (hafif oldugu icin mermiler onu daha cok iter)
        this.geriItmeCarpani = 1.3;
        
        // Hizli dusman yuruyus animasyonu sheet dosyasi 8 kareden olusmaktadir
        this.maksAnimasyonKaresi = 8;
        
        // Hizli dusman gorseli icin slime yurume animasyonu sheet dosyasini yukler
        BufferedImage sheet = GorselYukleyici.gorselYukle("assets/FreeCharactersAnimationsAssetPack 23.13.22/SpriteSheets(96x96)/Monster_Slime/With_Shadows/Monster_Slime_Walk-Sheet.png");
        // Eger sheet dosyasi basariyla yuklendiyse
        if (sheet != null) {
            // Slime sheet gorselini programatik olarak hizli dusmana ozel kirmizi renge boyar (R=2.0, G=0.5, B=0.5 carpanlariyla)
            this.dusmanSheet = GorselYukleyici.gorselRenklendir(sheet, 2.0, 0.5, 0.5);
            // Kirpma veya yukleme sirasinda hata ihtimaline karsi koruma saglar
            try {
                // Eger renklendirilmis sheet basariyla olusturulduysa
                if (this.dusmanSheet != null) {
                    // Ilk kareyi yedek statik gorsel olarak kirpar (37, 42) konumundan 20x20 boyutunda
                    this.dusmanGorseli = this.dusmanSheet.getSubimage(37, 42, 20, 20);
                }
            } catch (Exception e) {
                // Hata durumunda konsola uyari yazdirir
                System.out.println("UYARI: Hizli dusman gorseli kesilirken hata olustu. Yedek sekil cizilecek. Detay: " + e.getMessage());
            }
        }
    }
    
    // Gorsel bulunamazsa veya yuklenemezse cizilecek geometrik yedek sekil metodu
    @Override
    protected void yedekGeometrikCiz(Graphics2D g2) {
        // Hizli dusman oldugu icin mor/magenta renk secer
        g2.setColor(Color.MAGENTA);
        // Cizilecek dairenin sol ust kose x koordinatini hesaplar
        int cizimX = (int) (x - yariCap);
        // Cizilecek dairenin sol ust kose y koordinatini hesaplar
        int cizimY = (int) (y - yariCap);
        // Mor renkle hizli dusman bedeni olarak yedek daireyi doldurur
        g2.fillOval(cizimX, cizimY, (int) (yariCap * 2), (int) (yariCap * 2));
        
        // Gozler
        g2.setColor(Color.YELLOW);
        // Sol goz dairesini doldurur
        g2.fillOval((int) (x - 4), (int) (y - 3), 3, 3);
        // Sag goz dairesini doldurur
        g2.fillOval((int) (x + 1), (int) (y - 3), 3, 3);
    }
}
