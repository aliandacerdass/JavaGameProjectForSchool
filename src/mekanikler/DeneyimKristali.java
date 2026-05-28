package mekanikler;

import varliklar.Oyuncu;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Polygon;

// DeneyimKristali sinifi, dusmanlar öldügünde yere dusen ve oyuncuya seviye atlatan tecrube puanlarini temsil eder
public class DeneyimKristali {
    // Kristalin harita uzerindeki X koordinati
    public double x;
    // Kristalin harita uzerindeki Y koordinati
    public double y;
    
    // Kristal toplandiginda oyuncunun kazanacagi deneyim/tecrube puani miktari
    public double deneyimMiktari;
    
    // Kristalin toplanma yariçapı (daire tabanli temas tespiti icin)
    public double yariCap;
    
    // Mıknatıs etkisinin devreye girecegi maksimum mesafe (oyuncu bu menzile girerse kristal oyuncuya cekilir)
    public double toplamaMenzili;
    
    // Kristalin oyuncuya dogru cekilirkenki anlik hizi (oyuncuya yaklastikca ivmelenir)
    private double cekmeHizi;
    
    // Kristalin oyuncu tarafindan alinip alinmadigini belirten durum bayragi
    public boolean toplandi;
    
    // Kurucu metot: Kristali baslangic konumu ve verecegi tecrube puani ile olusturur
    public DeneyimKristali(double x, double y, double deneyimMiktari) {
        // Koordinatlari atar
        this.x = x;
        this.y = y;
        
        // Tecrube puani miktarini saklar
        this.deneyimMiktari = deneyimMiktari;
        
        // Temel fiziksel ve miknatis parametrelerini ayarlar
        this.yariCap = 6.0;             // Carpisma yariçapı (küçük piksel elmas görünümü icin)
        this.toplamaMenzili = 120.0;    // Mıknatıs menzili (120 piksel mesafe idealdir)
        this.cekmeHizi = 1.0;           // Çekilme hızı başlangıçta yavaştır
        this.toplandi = false;          // Baslangicta henüz toplanmamistir
    }
    
    // Her oyun karesinde (tick) kristalin konumunu ve oyuncuya olan mesafesini gunceller
    public void guncelle(Oyuncu oyuncu) {
        // Eger zaten toplandiysa guncelleme yapmaz
        if (toplandi) {
            return;
        }
        
        // Oyuncu ile kristal arasindaki yatay ve dikey mesafe farklarini hesaplar
        double dx = oyuncu.x - this.x;
        double dy = oyuncu.y - this.y;
        
        // Iki nokta arasindaki gerçek uzakligi hesaplar (Mıknatıs çekimi için karekök gereklidir)
        double uzaklik = Math.sqrt(dx * dx + dy * dy);
        
        // 1. Mıknatıs Çekim Mekanizması: Oyuncu toplama menziline girdi mi?
        if (uzaklik <= this.toplamaMenzili) {
            // Oyuncuya dogru yön vektorünü normalize ederek hesaplar
            double yonX = 1.0;
            double yonY = 0.0;
            
            if (uzaklik > 0) {
                yonX = dx / uzaklik;
                yonY = dy / uzaklik;
            }
            
            // Çekim hızını her karede ivmelendirir (Mıknatıs hissini güçlendirmek için her tick hız artar)
            // Maksimum çekim hız sınırı 12.0 piksel/kare olarak belirlenir
            this.cekmeHizi = Math.min(12.0, this.cekmeHizi + 0.25);
            
            // Konumu oyuncunun merkezine doğru günceller
            this.x += yonX * this.cekmeHizi;
            this.y += yonY * this.cekmeHizi;
        }
        
        // 2. Temas ve Toplanma Mekanizması: Kristal oyuncuya çarptı mı?
        // Daire tabanli çarpışma formülü kullanılır: Uzaklık < (Kristal Yarıçapı + Oyuncu Yarıçapı)
        if (uzaklik < (this.yariCap + oyuncu.yariCap)) {
            // Kristal toplanma bayragini true yapar (bir sonraki karede yok olması için)
            this.toplandi = true;
            
            // Oyuncunun deneyim puanini artirir
            oyuncu.deneyim += this.deneyimMiktari;
            
            // Konsola bilgilendirme yazdirir (gelistirici takibi icin)
            System.out.println("Deneyim Kristali toplandi! +" + this.deneyimMiktari + " DP. Toplam: " + oyuncu.deneyim + "/" + oyuncu.sonrakiSeviyeDeneyimi);
            
            // Seviye atlama baraj kontrolu
            if (oyuncu.deneyim >= oyuncu.sonrakiSeviyeDeneyimi) {
                // Deneyim fazlasini bir sonraki seviyeye devretmek icin cikarir
                oyuncu.deneyim -= oyuncu.sonrakiSeviyeDeneyimi;
                
                // Oyuncu seviyesini artirir
                oyuncu.seviye++;
                
                // Bir sonraki seviye icin gerekli tecrube barajini %50 artirarak zorlastirir
                oyuncu.sonrakiSeviyeDeneyimi = Math.round(oyuncu.sonrakiSeviyeDeneyimi * 1.5);
                
                // Konsola seviye atlama logu yazdirir
                System.out.println("OYUNCU SEVİYE ATLADI! Yeni Seviye: " + oyuncu.seviye);
                
                // NOT: Adım 4'te burası SeviyeArayuzu.java entegrasyonu ile dondurulup secenek ekranı acacaktır.
            }
        }
    }
    
    // Kristali ekrana cizen metot (Piksel temali yesil bir elmas/kristal gorunumu)
    public void ciz(Graphics2D g2) {
        // Eger zaten toplandiysa ekranda cizilmez
        if (toplandi) {
            return;
        }
        
        // Elmas (diamond) sekli olusturmak icin 4 adet kose noktasi belirleriz
        int[] xNoktalari = {(int) x, (int) (x + yariCap), (int) x, (int) (x - yariCap)};
        int[] yNoktalari = {(int) (y - yariCap), (int) y, (int) (y + yariCap), (int) y};
        
        // Poligon nesnesi olustururuz
        Polygon elmas = new Polygon(xNoktalari, yNoktalari, 4);
        
        // Parlak piksel elmas rengi (Neon Yeşili)
        g2.setColor(Color.GREEN);
        g2.fillPolygon(elmas);
        
        // Kristalin etrafına ince koyu yeşil bir çerçeve cizerek belirginlestirir
        g2.setColor(new Color(0, 100, 0));
        g2.drawPolygon(elmas);
        
        // Kristalin ortasina parıldama efekti vermek icin kucuk beyaz bir parıltı dairesi ekler
        g2.setColor(Color.WHITE);
        g2.fillOval((int) (x - 2), (int) (y - 2), 4, 4);
    }
}
