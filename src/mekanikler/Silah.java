package mekanikler;

import varliklar.Oyuncu;
import varliklar.Dusman;
import java.util.ArrayList;
import java.awt.Graphics2D;

// Silah sinifi, oyundaki tum saldiri araclarinin (silahlarin) temelini olusturur
public abstract class Silah {
    // Silahin adi (Orn: "Ates Topu", "Doner Bicak")
    public String ad;
    
    // Silahin mevcut seviyesi (Seviye atladikca hasar ve saldiri hizi artar)
    public int seviye;
    
    // Silahin verdigi temel hasar miktari
    public double hasar;
    
    // Silahin iki saldirisi arasindaki bekleme suresi (milisaniye cinsinden cooldown)
    public long saldiriBeklemeSuresi;
    
    // En son ne zaman saldirildigini tutan zaman damgasi (milisaniye cinsinden)
    public long sonSaldiriZamani;
    
    // Silahin bagli oldugu ve takip ettigi oyuncu nesnesi
    public Oyuncu oyuncu;
    
    // Kurucu metot: Silahin temel parametrelerini ve oyuncu baglantisini atar
    public Silah(String ad, Oyuncu oyuncu, double hasar, long saldiriBeklemeSuresi) {
        // Silah adini atar
        this.ad = ad;
        // Oyuncu referansini saklar
        this.oyuncu = oyuncu;
        // Silahin baslangic seviyesi 1'dir
        this.seviye = 1;
        
        // Hasar ve saldiri bekleme suresi baslangic degerlerini atar
        this.hasar = hasar;
        this.saldiriBeklemeSuresi = saldiriBeklemeSuresi;
        
        // Oyun basladiginda ilk saldirinin hemen yapilabilmesi icin zamani geriye donuk sifirlar
        this.sonSaldiriZamani = 0;
    }
    
    // Silahin her oyun karesinde (tick) durumunu guncellemesini saglayan temel metot
    public void guncelle(ArrayList<Dusman> dusmanlar, ArrayList<Mermi> mermiler) {
        // Sistemdeki mevcut zamani milisaniye olarak alir
        long suAnkiZaman = System.currentTimeMillis();
        
        // Eger son saldiridan bu yana gecen sure, bekleme suresinden buyukse yeni saldiri yapilabilir
        if (suAnkiZaman - sonSaldiriZamani >= saldiriBeklemeSuresi) {
            // Saldiri metodunu cagirir (alt siniflar kendi saldiri mantigini buraya yazacak)
            saldir(dusmanlar, mermiler);
            // Son saldiri zamanini gunceller
            this.sonSaldiriZamani = suAnkiZaman;
        }
    }
    
    // Her silahin kendine has saldirisini tetikleyecek soyut (abstract) metot
    // Bu metodu her silah sinifi (AtesTopu, DonerBicak) kendi kurallarina gore ezmelidir (override)
    public abstract void saldir(ArrayList<Dusman> dusmanlar, ArrayList<Mermi> mermiler);
    
    // Ekranda cizilmesi gereken gorsel/piksel bir silahi cizmek icin kullanilacak metot
    public abstract void ciz(Graphics2D g2);
    
    // Silahin seviyesini artiran ve statlarini guclendiren metot
    public void seviyeAtla() {
        // Seviye degerini 1 artirir
        this.seviye++;
        
        // Seviye artisina gore hasari %20 artirir
        this.hasar = this.hasar * 1.2;
        
        // Seviye artisina gore bekleme suresini %10 azaltarak daha hizli vurmasini saglar
        // Minimum saldiri bekleme suresi sınırı koyarak oyunun çökmesi engellenir
        this.saldiriBeklemeSuresi = Math.max(100, (long) (this.saldiriBeklemeSuresi * 0.9));
        
        // Konsola bilgi yazdirir (gelistirici takibi icin)
        System.out.println(this.ad + " seviye atladi! Yeni Seviye: " + this.seviye + ", Yeni Hasar: " + this.hasar);
    }
}
