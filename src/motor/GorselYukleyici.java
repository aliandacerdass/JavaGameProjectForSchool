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
            
            // 5. Yol: GorselYukleyici.class konumuna göre kök dizini bularak yükleme
            try {
                java.net.URL classURL = GorselYukleyici.class.getResource("GorselYukleyici.class");
                if (classURL != null && classURL.getProtocol().equals("file")) {
                    String classPath = classURL.getPath();
                    int index = classPath.indexOf("JavaGameProjectForSchool");
                    if (index != -1) {
                        String rootPath = classPath.substring(0, index + "JavaGameProjectForSchool".length());
                        File mutlakDosya = new File(rootPath, dosyaYolu);
                        if (mutlakDosya.exists()) {
                            return ImageIO.read(mutlakDosya);
                        }
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
