package varliklar;

// Dusman sinifi, oyundaki tum dusmanlarin temelini olusturur (Gecici Taslak/Stub)
public class Dusman {
    // Dusmanin X koordinati
    public double x;
    // Dusmanin Y koordinati
    public double y;
    
    // Dusmanin mevcut can degeri
    public double can;
    // Dusmanin hareket hizi
    public double hiz;
    // Dusmanin oyuncuya verecegi hasar
    public double hasar;
    
    // Dusmanin carpisma yariçap degeri
    public double yariCap;
    
    // Kurucu metot: Dusmani baslangic degerleriyle olusturur
    public Dusman(double x, double y, double can, double hiz, double hasar, double yariCap) {
        this.x = x;
        this.y = y;
        this.can = can;
        this.hiz = hiz;
        this.hasar = hasar;
        this.yariCap = yariCap;
    }
}
