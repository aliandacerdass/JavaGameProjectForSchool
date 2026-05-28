package motor;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

// Oyun icindeki tum resimleri guvenli bir sekilde yukleyen yardimci sinif
public class GorselYukleyici {
    
    // Verilen dosya yolundaki resmi BufferedImage olarak yukleyen metot (Andaç)
    public static BufferedImage gorselYukle(String dosyaYolu) {
        try {
            // 1. Yol: Doğrudan relative path (Çalışma dizini proje kök dizini ise)
            File dosya = new File(dosyaYolu);
            if (dosya.exists()) {
                return ImageIO.read(dosya);
            }
            
            // 2. Yol: Bir üst dizin (Eğer çalışma dizini src, out veya bin ise)
            File ustDizinDosya = new File("../" + dosyaYolu);
            if (ustDizinDosya.exists()) {
                return ImageIO.read(ustDizinDosya);
            }

            // 3. Yol: İki üst dizin (Eğer çalışma dizini out/production/Project vb. ise)
            File ikiUstDizinDosya = new File("../../" + dosyaYolu);
            if (ikiUstDizinDosya.exists()) {
                return ImageIO.read(ikiUstDizinDosya);
            }

            // 4. Yol: ClassLoader ile kaynak olarak yüklemeyi deneme
            java.io.InputStream stream = GorselYukleyici.class.getClassLoader().getResourceAsStream(dosyaYolu);
            if (stream != null) {
                return ImageIO.read(stream);
            }
            
            // 5. Yol: GorselYukleyici.class konumuna göre yukarı doğru çıkıp "assets" klasörünü arama
            try {
                java.net.URL classURL = GorselYukleyici.class.getResource("GorselYukleyici.class");
                if (classURL != null && classURL.getProtocol().equals("file")) {
                    // Sınıf dosyasının klasöründen başlayarak yukarı doğru parent klasörleri tararız
                    File guncelKlasor = new File(classURL.getPath()).getParentFile();
                    while (guncelKlasor != null) {
                        File assetsKlasoru = new File(guncelKlasor, "assets");
                        if (assetsKlasoru.exists() && assetsKlasoru.isDirectory()) {
                            // "assets" klasörünü bulduğumuzda onun üst dizinini proje kök dizini kabul edip görseli yükleriz
                            File mutlakDosya = new File(assetsKlasoru.getParentFile(), dosyaYolu);
                            if (mutlakDosya.exists()) {
                                return ImageIO.read(mutlakDosya);
                            }
                        }
                        // Bir üst dizine çık
                        guncelKlasor = guncelKlasor.getParentFile();
                    }
                }
            } catch (Exception ex) {
                // Hata durumunda sessizce devam et
            }

            // Hiçbir şekilde dosya bulunamazsa uyarı yazılır ve null dönülür
            System.out.println("UYARI: Gorsel dosyasi bulunamadi -> " + dosyaYolu);
            return null;
        } catch (Exception e) {
            // Yukleme esnasinda bir hata olusursa oyunu cokertmez, uyari yazar ve null doner
            System.out.println("HATA: Gorsel yuklenirken bir problem olustu -> " + dosyaYolu + " | Detay: " + e.getMessage());
            return null;
        }
    }
}
