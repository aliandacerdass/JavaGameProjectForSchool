package mekanikler;

import java.awt.Graphics2D;
import java.awt.Color;

// Mermi sinifi, oyundaki tum atilan mermilerin temelini olusturur
public class Mermi {
    // Merminin harita uzerindeki X koordinati
    public double x;
    // Merminin harita uzerindeki Y koordinati
    public double y;
    
    // Merminin yatay hareket yonu (vektor bileşeni)
    public double yonX;
    // Merminin dikey hareket yonu (vektor bileşeni)
    public double yonY;
    
    // Merminin hareket hizi (saniyede/karede gidecegi piksel)
    public double hiz;
    // Merminin dusmana verecegi hasar miktari
    public double hasar;
    
    // Merminin aktiflik durumu (aktif degilse sistemden temizlenir)
    public boolean aktif;
    
    // Daire tabanli carpişma algilama için merminin yariçap degeri
    public double yariCap;
    
    // Merminin maksimum ulasabilecegi menzil (piksel cinsinden)
    public double maksimumMenzil;
    // Merminin atildigi andan itibaren kat ettigi toplam mesafe
    public double katEdilenMesafe;
    
    // Kurucu metot: Merminin baslangic koordinatlarini, yonunu ve ozelliklerini atar
    public Mermi(double baslangicX, double baslangicY, double yonX, double yonY, double hiz, double hasar, double yariCap, double maksimumMenzil) {
        // Baslangic X koordinatini atar
        this.x = baslangicX;
        // Baslangic Y koordinatini atar
        this.y = baslangicY;
        
        // Yon vektorlerini atar (kodun ilerleyen kısımlarında normalize edilmis olmalidir)
        this.yonX = yonX;
        this.yonY = yonY;
        
        // Hiz ve hasar degerlerini atar
        this.hiz = hiz;
        this.hasar = hasar;
        
        // Carpisma yariçapini belirler
        this.yariCap = yariCap;
        // Maksimum gidebilecegi menzili belirler
        this.maksimumMenzil = maksimumMenzil;
        
        // Yeni olusturulan mermi varsayilan olarak aktiftir
        this.aktif = true;
        // Baslangicta kat edilen mesafe sifirdir
        this.katEdilenMesafe = 0;
    }
    
    // Merminin konumunu ve kat ettigi mesafeyi guncelleyen metot
    public void guncelle() {
        // Eger mermi aktif degilse guncelleme yapmaz
        if (!aktif) {
            return;
        }
        
        // Yon vektoru ve hiza bagli olarak X konumunu gunceller
        this.x += this.yonX * this.hiz;
        // Yon vektoru ve hiza bagli olarak Y konumunu gunceller
        this.y += this.yonY * this.hiz;
        
        // Merminin bu adimda kat ettigi mesafeyi toplam mesafeye ekler
        this.katEdilenMesafe += this.hiz;
        
        // Eger mermi ulasabilecegi maksimum menzili astiysa aktifligini kapatir
        if (this.katEdilenMesafe >= this.maksimumMenzil) {
            this.aktif = false;
        }
    }
    
    // Mermiyi ekrana cizen metot (Piksel temali geri donus cizimi icerir)
    public void ciz(Graphics2D g2) {
        // Eger mermi aktif degilse cizim yapmaz
        if (!aktif) {
            return;
        }
        
        // Merminin rengini belirler (Ateş topu/Mermi rengi olarak Turuncu)
        g2.setColor(Color.ORANGE);
        
        // Daire tabanli carpisma yariçapini kullanarak mermiyi ekrana oval olarak cizer
        // X ve Y koordinatlari merminin merkezini temsil ettiginden sol ust koseyi hesaplamak icin yariCap cikarilir
        int cizimX = (int) (this.x - this.yariCap);
        int cizimY = (int) (this.y - this.yariCap);
        int cap = (int) (this.yariCap * 2);
        
        // Ovali cizer
        g2.fillOval(cizimX, cizimY, cap, cap);
    }
}
