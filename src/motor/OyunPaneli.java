package motor;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import varliklar.*;
import mekanikler.*;

// OyunPaneli sinifi, oyunun cizimlerinin yapildigi ve ekranda gosterildigi ana alandir
public class OyunPaneli extends JPanel {
    
    // Ekran genisligi (piksel cinsinden)
    public static final int EKRAN_GENISLIGI = 800;
    // Ekran yuksekligi (piksel cinsinden)
    public static final int EKRAN_YUKSEKLIGI = 600;
    
    // Harita boyutu (piksel cinsinden sabit 3000x3000px)
    public static final int HARITA_BOYUTU = 3000;
    
    // Kamera koordinatlari (Ekranin sol ust kosesinin haritadaki yeri)
    public double kameraX = 0;
    public double kameraY = 0;
    
    // Klavye girdi kontrolcusu
    public final TusKontrolcu tusKontrol;
    // Fare girdi kontrolcusu
    public final FareKontrolcu fareKontrol;
    
    // Oyuncu karakteri nesnesi
    public final Oyuncu oyuncu;
    
    // Oyundaki aktif dusmanlarin listesi
    public final ArrayList<Dusman> dusmanlar;
    // Oyundaki aktif mermilerin listesi
    public final ArrayList<Mermi> mermiler;
    // Oyundaki aktif deneyim kristallerinin listesi
    public final ArrayList<DeneyimKristali> kristaller;
    // Oyuncunun sahip oldugu aktif silahlarin listesi
    public final ArrayList<Silah> silahlar;
    
    // Oyunun anlik durumunu tutan degisken (Varsayilan olarak OYUN durumunda baslar)
    public OyunDurumu oyunDurumu = OyunDurumu.OYUN;

    // Kurucu metot: Panelin boyutlarini ve arka planini ayarlar
    public OyunPaneli() {
        // Panelin tercih edilen boyutunu belirler
        this.setPreferredSize(new Dimension(EKRAN_GENISLIGI, EKRAN_YUKSEKLIGI));
        // Arka plan rengini siyah yapar
        this.setBackground(Color.BLACK);
        // Odaklanabilirlik ozelligini aktif eder (klavye girdilerini alabilmek icin)
        this.setFocusable(true);
        
        // Oyuncuyu haritanin ortasinda dogacak sekilde baslatir (1500, 1500)
        this.oyuncu = new Oyuncu(1500, 1500);
        
        // Liste yapilarini baslatir
        this.dusmanlar = new ArrayList<>();
        this.mermiler = new ArrayList<>();
        this.kristaller = new ArrayList<>();
        this.silahlar = new ArrayList<>();
        
        // Test silahlarini ekler (Oyuncunun saldiri yapabilmesi icin)
        this.silahlar.add(new AtesTopu(this.oyuncu));
        this.silahlar.add(new DonerBicak(this.oyuncu));
        
        // Klavye kontrolcusunu olusturur
        this.tusKontrol = new TusKontrolcu(this);
        // Fare kontrolcusunu olusturur
        this.fareKontrol = new FareKontrolcu(this);
        
        // Panelin klavye girdilerini dinlemesini saglar
        this.addKeyListener(this.tusKontrol);
        // Panelin fare girdilerini dinlemesini saglar
        this.addMouseListener(this.fareKontrol);
        this.addMouseMotionListener(this.fareKontrol);
    }
    
    // AWT/Swing tarafindan panel cizimi gerektiginde otomatik cagirilan metot
    @Override
    protected void paintComponent(Graphics g) {
        // Ust sinifin paintComponent metodunu cagirarak paneli temizler
        super.paintComponent(g);
        
        // Daha gelismis cizim metotlari icin Graphics nesnesini Graphics2D'ye donustururuz
        Graphics2D g2 = (Graphics2D) g;
        
        // 1. DÜNYA NESNELERİNİ ÇİZ (Kamera kaydirmasini uygula)
        g2.translate(-kameraX, -kameraY);
        
        // Dunya koordinatlarinda basit piksel-art grid cizgileri (Referans zemin cizimleri)
        g2.setColor(new Color(40, 40, 40));
        for (int i = 0; i <= HARITA_BOYUTU; i += 100) {
            // Dikey grid cizgisi
            g2.drawLine(i, 0, i, HARITA_BOYUTU);
            // Yatay grid cizgisi
            g2.drawLine(0, i, HARITA_BOYUTU, i);
        }
        
        // Haritanin dis sinirlarini kirmizi bir cerceve ile belirler
        g2.setColor(Color.RED);
        g2.drawRect(0, 0, HARITA_BOYUTU, HARITA_BOYUTU);
        
        // Yerdeki deneyim kristallerini cizer (Gizem'in DeneyimKristali sinifindaki ciz metodu)
        for (int i = 0; i < kristaller.size(); i++) {
            kristaller.get(i).ciz(g2);
        }
        
        // Havada giden aktif mermileri cizer (Gizem'in Mermi sinifindaki ciz metodu)
        for (int i = 0; i < mermiler.size(); i++) {
            mermiler.get(i).ciz(g2);
        }
        
        // Aktif dusmanlari cizer (Emre'nin Dusman sinifindaki ciz metodu)
        for (int i = 0; i < dusmanlar.size(); i++) {
            dusmanlar.get(i).ciz(g2);
        }
        
        // Oyuncu karakterini dunya koordinatlarina gore cizer (Emre'nin Oyuncu sinifindaki ciz metodu)
        oyuncu.ciz(g2);
        
        // 2. SABİT EKRAN ARAYÜZÜNÜ ÇİZ (Kamerayi geri cevir / translate geri al)
        g2.translate(kameraX, kameraY);
        
        // Sabit ekran yazilari (Kameradan etkilenmeyen HUD elemanlari)
        g2.setColor(Color.WHITE);
        g2.drawString("Piksel Hayatta Kalma Oyunu - Motor Baslatildi!", 20, 30);
        g2.drawString("Oyuncu Konumu: X=" + (int) oyuncu.x + ", Y=" + (int) oyuncu.y, 20, 55);
        g2.drawString("Kamera Konumu: X=" + (int) kameraX + ", Y=" + (int) kameraY, 20, 80);
        
        // Oyuncu Can (HP) Bari Çizimi
        g2.setColor(Color.RED);
        g2.fillRect(520, 20, 250, 20); // Kirmizi zemin barı
        g2.setColor(Color.GREEN);
        int canBarGenisligi = (int) ((oyuncu.can / oyuncu.maksCan) * 250);
        g2.fillRect(520, 20, Math.max(0, canBarGenisligi), 20); // Mevcut saglik bari
        g2.setColor(Color.WHITE);
        g2.drawRect(520, 20, 250, 20); // Çerçeve cizgisi
        g2.drawString("HP: " + (int) oyuncu.can + " / " + (int) oyuncu.maksCan, 530, 35);
        
        // Seviye ve Deneyim Puanı Bilgisi Çizimi
        g2.drawString("SEVIYE: " + oyuncu.seviye, 520, 60);
        g2.drawString("EXP: " + (int) oyuncu.deneyim + " / " + (int) oyuncu.sonrakiSeviyeDeneyimi, 520, 80);
        
        // Can barının hemen sol tarafında silah sayısını göster
        g2.drawString("Silahlar: Ates Topu (Oto), Doner Bicak (Oto)", 20, 110);
        
        // 3. OYUN BİTTİ EKRANI ÇİZİMİ (Eger durum OYUN_BITTI ise ekrana kirmizi yazi yazar)
        if (oyunDurumu == OyunDurumu.OYUN_BITTI) {
            // Ekrana yari saydam siyah perde çekeriz
            g2.setColor(new Color(0, 0, 0, 180));
            g2.fillRect(0, 0, EKRAN_GENISLIGI, EKRAN_YUKSEKLIGI);
            
            // OYUN BITTI yazisi
            g2.setColor(Color.RED);
            g2.setFont(new Font("Arial", Font.BOLD, 48));
            g2.drawString("OYUN BITTI", 270, 240);
            
            // R tusu ile yeniden baslatma talimati yazisi
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.PLAIN, 20));
            g2.drawString("Yeniden baslamak icin 'R' tusuna basin.", 220, 310);
        }
        
        // Cizim islemlerinin bellek temizligini yapar
        g2.dispose();
    }
    
    // Oyun durumlarini guncelleyen metot (OyunDongusu tarafindan periyodik olarak tetiklenir)
    public void guncelle() {
        // Eger oyun bittiyse sadece yeniden baslatma tusunu kontrol ederiz
        if (oyunDurumu == OyunDurumu.OYUN_BITTI) {
            if (tusKontrol.rTusu) {
                oyunuSifirla();
            }
            return;
        }
        
        // 1. Oyuncunun hareketlerini ve konumunu gunceller
        oyuncu.guncelle(this.tusKontrol);
        
        // Oyuncunun canı bittiyse oyunu OYUN_BITTI durumuna aliriz
        if (oyuncu.can <= 0) {
            oyunDurumu = OyunDurumu.OYUN_BITTI;
            return;
        }
        
        // 2. Eger haritada hic dusman kalmadiysa test amaciyla yeni dusmanlar spawn eder (Andac test edebilsin diye)
        if (dusmanlar.isEmpty()) {
            // Oyuncunun etrafinda farkli yönlerde 3 zombi/dusman olusturur
            dusmanlar.add(new Dusman(oyuncu.x + 300, oyuncu.y, 40.0, 1.8, 15.0, 16.0));
            dusmanlar.add(new Dusman(oyuncu.x - 300, oyuncu.y, 40.0, 1.8, 15.0, 16.0));
            dusmanlar.add(new Dusman(oyuncu.x, oyuncu.y + 350, 100.0, 1.0, 30.0, 24.0)); // Daha yavas ama guclu boss zombi
        }
        
        // 3. Sahip olunan silahlarin cooldown'larini kontrol eder ve otomatik tetikler
        for (int i = 0; i < silahlar.size(); i++) {
            silahlar.get(i).guncelle(dusmanlar, mermiler);
        }
        
        // 4. Dusmanlarin oyuncuyu takip etmesini ve konum guncellemesini saglar
        for (int i = 0; i < dusmanlar.size(); i++) {
            dusmanlar.get(i).guncelle(oyuncu);
        }
        
        // 5. Ucus halindeki mermilerin konumlarini gunceller
        for (int i = 0; i < mermiler.size(); i++) {
            mermiler.get(i).guncelle();
        }
        
        // 6. Yerde duran veya miknatisa kapilmis kristallerin durumunu gunceller
        for (int i = 0; i < kristaller.size(); i++) {
            kristaller.get(i).guncelle(oyuncu);
        }
        
        // 7. Çarpışmaları denetler (Andac'in CarpismaDenetleyici sınıfındaki metot cagrilir)
        CarpismaDenetleyici.carpismalariDenetle(oyuncu, dusmanlar, mermiler, kristaller);
        
        // 8. Ölen, pasif olan veya toplanan nesneleri listelerden temizler (removeIf performanslidir)
        dusmanlar.removeIf(d -> d.can <= 0);
        mermiler.removeIf(m -> !m.aktif);
        kristaller.removeIf(k -> k.toplandi);
        
        // 9. Kamerayi oyuncuyu tam ortalayacak sekilde konumlandirir
        kameraX = oyuncu.x - EKRAN_GENISLIGI / 2.0;
        kameraY = oyuncu.y - EKRAN_YUKSEKLIGI / 2.0;
        
        // Kameranin 3000x3000px haritanin disina cikmasini engeller (Siyah bosluk gosterilmez)
        kameraX = Math.max(0, Math.min(kameraX, HARITA_BOYUTU - EKRAN_GENISLIGI));
        kameraY = Math.max(0, Math.min(kameraY, HARITA_BOYUTU - EKRAN_YUKSEKLIGI));
    }
    
    // R tusuna basildiginda oyunu sifirlayip yeniden baslatan metot
    public void oyunuSifirla() {
        // Tum listeleri temizler
        dusmanlar.clear();
        mermiler.clear();
        kristaller.clear();
        
        // Oyuncunun canini ve durumlarini sifirlar
        oyuncu.can = oyuncu.maksCan;
        oyuncu.x = 1500;
        oyuncu.y = 1500;
        oyuncu.seviye = 1;
        oyuncu.deneyim = 0;
        oyuncu.sonrakiSeviyeDeneyimi = 100;
        
        // Girdi durumlarini temizler (R tusunun sonsuz tetiklenmesini onlemek icin)
        tusKontrol.temizle();
        fareKontrol.temizle();
        
        // Silahlari baslangic seviyelerine doner
        silahlar.clear();
        silahlar.add(new AtesTopu(this.oyuncu));
        silahlar.add(new DonerBicak(this.oyuncu));
        
        // Oyun durumunu aktif oynanis durumuna getirir
        oyunDurumu = OyunDurumu.OYUN;
        System.out.println("Oyun yeniden baslatildi!");
    }
}
