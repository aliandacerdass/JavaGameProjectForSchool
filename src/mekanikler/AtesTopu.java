package mekanikler;

import varliklar.Oyuncu;
import varliklar.Dusman;
import java.util.ArrayList;
import java.awt.Graphics2D;
import java.awt.Color;

// AtesTopu sinifi, en yakin dusmana kilitlenip ates eden bir silah turudur
public class AtesTopu extends Silah {
    
    // Kurucu metot: Ates Topu silahini baslangic degerleriyle olusturur
    public AtesTopu(Oyuncu oyuncu) {
        // Silah adi, oyuncu referansi, baslangic hasari (20) ve saldiri bekleme suresi (1200 ms)
        super("Ates Topu", oyuncu, 20.0, 1200);
    }
    
    // Ates Topunun saldiri mekanizmasi: En yakin dusmana doğru mermi firlatir
    @Override
    public void saldir(ArrayList<Dusman> dusmanlar, ArrayList<Mermi> mermiler) {
        // Eger haritada hic dusman yoksa saldiri yapilmaz
        if (dusmanlar == null || dusmanlar.isEmpty()) {
            return;
        }
        
        // En yakin dusmani bulmak icin gecici degiskenler tanimlanir
        Dusman enYakinDusman = null;
        double enKisaMesafe = Double.MAX_VALUE;
        
        // Haritadaki tum dusmanlari tarayarak en yakindakini tespit eder
        for (Dusman dusman : dusmanlar) {
            // Oyuncu ile dusman arasindaki yatay mesafe
            double dx = dusman.x - oyuncu.x;
            // Oyuncu ile dusman arasindaki dikey mesafe
            double dy = dusman.y - oyuncu.y;
            
            // Iki nokta arasindaki mesafe formulu (karekök kullanmadan hizli karsilastirma)
            double mesafeKaresi = (dx * dx) + (dy * dy);
            
            // Eger bu dusman su ana kadarki en yakin dusmansa guncelleme yapar
            if (mesafeKaresi < enKisaMesafe) {
                enKisaMesafe = mesafeKaresi;
                enYakinDusman = dusman;
            }
        }
        
        // Eger en yakin dusman basariyla tespit edildiyse ona dogru ates eder
        if (enYakinDusman != null) {
            // Dusman ile oyuncu arasindaki dogru yon vektorunu hesaplar
            double dx = enYakinDusman.x - oyuncu.x;
            double dy = enYakinDusman.y - oyuncu.y;
            
            // Gercek mesafeyi hesaplar (vektor normalizasyonu icin karekök kullanilir)
            double mesafe = Math.sqrt(dx * dx + dy * dy);
            
            double yonX = 1.0;
            double yonY = 0.0;
            
            // Sifira bolme hatasini onlemek icin mesafe kontrol edilir
            if (mesafe > 0) {
                // Yon vektorunu normalize eder (uzunlugunu 1 birim yapar)
                yonX = dx / mesafe;
                yonY = dy / mesafe;
            }
            
            // Yeni bir mermi nesnesi olusturur
            // Oyuncunun merkezinden (x, y) baslar, hesaplanan yonde, 6.0 hizinda, mevcut silahin hasariyla gider
            // Mermi yariçapi 8 piksel, maksimum menzili ise 600 pikseldir
            Mermi atesMermisi = new Mermi(
                oyuncu.x, 
                oyuncu.y, 
                yonX, 
                yonY, 
                6.0,          // Mermi Hizi
                this.hasar,   // Hasar
                8.0,          // Yaricap
                600.0         // Maksimum Menzil
            );
            
            // Olusturulan mermiyi oyunun genel mermi listesine ekler
            mermiler.add(atesMermisi);
            
            // Konsola bilgilendirme yazdirir (gelistirici takibi icin)
            System.out.println("Ates Topu firlatildi! Hedef koordinat: (" + enYakinDusman.x + ", " + enYakinDusman.y + ")");
        }
    }
    
    // Ates Topunun oyuncu uzerindeki anlik gorsel cizimi (Ates topu havada cizildigi icin silah olarak oyuncu ustunde cizim yapmaz)
    @Override
    public void ciz(Graphics2D g2) {
        // Havada giden mermileri zaten Mermi.ciz metodu cizmektedir, burasi bos kalabilir.
    }
}
