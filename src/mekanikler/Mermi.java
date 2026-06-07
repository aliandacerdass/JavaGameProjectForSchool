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
    
    // Mermiyi ekrana cizen metot (Piksel ve alev animasyonu efektli Ates Topu)
    public void ciz(Graphics2D g2) {
        // Eger mermi aktif degilse cizim yapmaz
        if (!aktif) {
            return;
        }
        
        // --- 1. ALEV KUYRUĞU / PARÇACIK EFEKTİ (TRAILING PARTICLES) ---
        // Alev topunun hareket yonunun tersine dogru kuculen ve saydamlasan alev kuyrugu cizeriz
        int r = (int) this.yariCap;
        
        // Kuyruk parcalari arasinda dongu
        for (int i = 3; i >= 1; i--) {
            // Kuyruk konumu: Mevcut konumdan geriye dogru yon vektorune gore kaydirilir
            double kaymaMiktari = r * 0.8 * i;
            int kX = (int) (this.x - this.yonX * kaymaMiktari);
            int kY = (int) (this.y - this.yonY * kaymaMiktari);
            
            // Boyut ve saydamlik her adimda kuculur
            int kCap = (int) (r * 2 * (1.0 - i * 0.25));
            int kAlfa = 220 - i * 50; // Alev ucuna dogru kararma/silinme
            
            if (kCap > 0 && kAlfa > 0) {
                // Kuyrugun rengi disari dogru kirmizilasar
                if (i == 3) {
                    g2.setColor(new Color(139, 0, 0, kAlfa)); // Koyu Kirmizi
                } else if (i == 2) {
                    g2.setColor(new Color(255, 69, 0, kAlfa)); // Turuncu kirmizi
                } else {
                    g2.setColor(new Color(255, 140, 0, kAlfa)); // Koyu turuncu
                }
                g2.fillOval(kX - kCap / 2, kY - kCap / 2, kCap, kCap);
            }
        }
        
        // --- 2. KATMANLI PARLAK MERKEZ (LAMINATED CORE) ---
        // Alev topunun merkezine dogru parlakligi ve sicakligi artan katmanli bir cizim uyguluyoruz
        
        // Katman A: En dis kirmizi alev halkasi
        g2.setColor(new Color(220, 20, 60, 200)); // Parlak Kirmizi
        g2.fillOval((int)(x - r * 1.2), (int)(y - r * 1.2), (int)(r * 2.4), (int)(r * 2.4));
        
        // Katman B: Orta turuncu govde
        g2.setColor(new Color(255, 140, 0)); // Turuncu
        g2.fillOval((int)(x - r), (int)(y - r), r * 2, r * 2);
        
        // Katman C: Sicak sari katman
        g2.setColor(new Color(255, 215, 0)); // Sari
        g2.fillOval((int)(x - r * 0.75), (int)(y - r * 0.75), (int)(r * 1.5), (int)(r * 1.5));
        
        // Katman D: En sicak beyaz merkez cekirdegi
        g2.setColor(Color.WHITE);
        g2.fillOval((int)(x - r * 0.4), (int)(y - r * 0.4), (int)(r * 0.8), (int)(r * 0.8));
    }
}
