import motor.Pencere;
import motor.OyunPaneli;

// Oyunun ana giris noktasi (Main metodu)
public class AnaGiris {
    public static void main(String[] args) {
        // Yeni bir pencere nesnesi olusturur
        Pencere pencere = new Pencere();
        
        // Yeni bir oyun paneli nesnesi olusturur
        OyunPaneli oyunPaneli = new OyunPaneli();
        
        // Pencereyi oyun paneli ile birlikte baslatir
        pencere.pencereyiBaslat(oyunPaneli);
    }
}
