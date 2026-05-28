package mekanikler;

import varliklar.Oyuncu;
import varliklar.Dusman;
import java.util.ArrayList;
import java.awt.Graphics2D;

// DonerBicak sinifi, oyuncunun etrafinda donerek cevredeki dusmanlara hasar veren bir silah turudur
public class DonerBicak extends Silah {
    
    // Yorunge yariçapi (bicaklarin oyuncudan ne kadar uzakta donecegi, piksel cinsinden)
    private double yorungeYaricapi;
    
    // Bicaklarin donus hizi (her tick basina radyan artis miktari)
    private double donusHizi;
    
    // Kurucu metot: Doner Bicak silahini baslangic degerleriyle olusturur
    public DonerBicak(Oyuncu oyuncu) {
        // Silah adi, oyuncu referansi, baslangic hasari (15.0) ve saldiri bekleme suresi (4000 ms)
        super("Doner Bicak", oyuncu, 15.0, 4000);
        
        // Yorunge yariçapini 80 piksel olarak belirler
        this.yorungeYaricapi = 80.0;
        // Donus hizini 0.05 radyan (yaklasik kare basina 3 derece) yapar
        this.donusHizi = 0.05;
    }
    
    // Doner Bicagin saldiri mekanizmasi: Oyuncu etrafinda donen mermiler (bicaklar) olusturur
    @Override
    public void saldir(ArrayList<Dusman> dusmanlar, ArrayList<Mermi> mermiler) {
        // Silahin seviyesine bagli olarak firlatilacak bicak sayisini belirler
        // Seviye 1 ise 1 bicak, Seviye 2 ise 2 bicak, Seviye 3 ise 3 bicak... vb.
        int bicakSayisi = this.seviye;
        
        // Bicaklarin oyuncu etrafinda esit acilarla dagilmasini saglar
        // Toplam 360 dereceyi (2 * PI radyan) bicak sayisina bolerek aci araligini bulur
        double aciAraligi = (2 * Math.PI) / bicakSayisi;
        
        // Belirlenen sayida donen bicak mermisi olusturur ve listeye ekler
        for (int i = 0; i < bicakSayisi; i++) {
            // Her bicagin baslangic acisini esit sekilde kaydirir
            double baslangicAcisi = i * aciAraligi;
            
            // Yeni bir DonerBicakMermi nesnesi olusturur
            // Oyuncu baglantisi, 80px donus yaricapi, 0.05 donus hizi, silahin hasari,
            // 12px bicak boyutu (yaricap), 1000 birim hayali menzil (yaklasik 3 tam tur donus suresi)
            // ve baslangic acisi parametrelerini gonderir
            DonerBicakMermi yeniBicak = new DonerBicakMermi(
                this.oyuncu,
                this.yorungeYaricapi,
                this.donusHizi,
                this.hasar,
                12.0,            // Bicak yariçapi
                1000.0,          // Maksimum Donus Menzili (Hayat Suresi)
                baslangicAcisi   // Baslangic Aci Konumu
            );
            
            // Mermiyi oyunun genel aktif mermiler listesine ekler
            mermiler.add(yeniBicak);
        }
        
        // Konsola bilgilendirme yazdirir (gelistirici takibi icin)
        System.out.println("Doner Bicak aktif edildi! Toplam bıçak sayısı: " + bicakSayisi);
    }
    
    // Doner Bicagin seviyesi arttiginda yorungesini ve hizini da hafifce artirir
    @Override
    public void seviyeAtla() {
        // Ust sinifin seviyeAtla metodunu cagirarak temel hasar ve bekleme suresini gunceller
        super.seviyeAtla();
        
        // Seviye basina yorunge yariçapini 5 piksel genisletir (daha genis bir alanı korumak icin)
        this.yorungeYaricapi += 5.0;
        
        // Seviye basina donus hizini %5 artirir (daha hizli donup daha cok temas etmesi icin)
        this.donusHizi = this.donusHizi * 1.05;
    }
    
    // Ekranda cizilmesi gereken gorsel/piksel bir silahi cizmek icin kullanilacak metot
    @Override
    public void ciz(Graphics2D g2) {
        // Havada donen bicak mermilerini zaten DonerBicakMermi.ciz metodu cizmektedir, burasi bos kalabilir.
    }
}
