package motor;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

// Oyun icindeki tum resimleri guvenli bir sekilde yukleyen yardimci sinif
public class GorselYukleyici {
    
    // Verilen dosya yolundaki resmi BufferedImage olarak yukleyen metot
    public static BufferedImage gorselYukle(String dosyaYolu) {
        try {
            // Belirtilen dosya yolundan resmi okumaya calisir
            File dosya = new File(dosyaYolu);
            if (dosya.exists()) {
                return ImageIO.read(dosya);
            } else {
                // Dosya fiziksel olarak yoksa konsola bilgi yazar ve null doner
                System.out.println("UYARI: Gorsel dosyasi bulunamadi -> " + dosyaYolu);
                return null;
            }
        } catch (Exception e) {
            // Yukleme esnasinda bir hata olusursa oyunu cokertmez, uyari yazar ve null doner
            System.out.println("HATA: Gorsel yuklenirken bir problem olustu -> " + dosyaYolu + " | Detay: " + e.getMessage());
            return null;
        }
    }
}
