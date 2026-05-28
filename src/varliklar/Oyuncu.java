package varliklar;

// Oyuncu sinifi, kullanicinin kontrol ettigi ana karakteri temsil eder (Gecici Taslak/Stub)
public class Oyuncu {
    // Oyuncunun X koordinati
    public double x;
    // Oyuncunun Y koordinati
    public double y;
    
    // Oyuncunun mevcut can degeri
    public double can;
    // Oyuncunun maksimum can kapasitesi
    public double maksCan;
    
    // Oyuncunun hareket hizi
    public double hiz;
    
    // Oyuncunun mevcut seviyesi (level)
    public int seviye;
    // Oyuncunun topladigi mevcut tecrube/deneyim puani
    public double deneyim;
    // Bir sonraki seviyeye gecmek icin gereken toplam deneyim baraji
    public double sonrakiSeviyeDeneyimi;
    
    // Kurucu metot: Karakteri baslangic degerleriyle olusturur
    public Oyuncu(double x, double y) {
        this.x = x;
        this.y = y;
        this.maksCan = 100;
        this.can = 100;
        this.hiz = 4.0;
        this.seviye = 1;
        this.deneyim = 0;
        this.sonrakiSeviyeDeneyimi = 100;
    }
}
