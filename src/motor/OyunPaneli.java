package motor;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.util.ArrayList;
import varliklar.Oyuncu;
import varliklar.Dusman;
import varliklar.DusmanUretici;
import mekanikler.Silah;
import mekanikler.Mermi;
import mekanikler.DeneyimKristali;
import mekanikler.SeviyeArayuzu;

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
    
    // Oyunun anlik durumunu saklayan degisken (Varsayilan olarak OYUN durumunda baslar)
    public OyunDurumu durum = OyunDurumu.OYUN;
    
    // Oyundaki aktif silahların listesi (Gizem)
    public final ArrayList<Silah> silahlar = new ArrayList<>();
    // Oyundaki aktif mermilerin listesi (Gizem & Andaç)
    public final ArrayList<Mermi> mermiler = new ArrayList<>();
    // Haritadaki aktif düşmanların listesi (Emre)
    public final ArrayList<Dusman> dusmanlar = new ArrayList<>();
    // Yere düşen deneyim kristallerinin listesi (Gizem)
    public final ArrayList<DeneyimKristali> kristaller = new ArrayList<>();
    
    // Seviye atlama kart arayüzü nesnesi (Gizem)
    public final SeviyeArayuzu seviyeArayuzu;
    
    // Klavye girdi kontrolcusu
    public final TusKontrolcu tusKontrol;
    // Fare girdi kontrolcusu
    public final FareKontrolcu fareKontrol;
    
    // Oyuncu karakteri nesnesi
    public final Oyuncu oyuncu;
    // Dusman dalga ureticisi nesnesi (Emre)
    public final DusmanUretici dusmanUretici;

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
        
        // Seviye atlama arayuzunu baslatir
        this.seviyeArayuzu = new SeviyeArayuzu(this);
        
        // Klavye kontrolcusunu olusturur
        this.tusKontrol = new TusKontrolcu(this);
        // Fare kontrolcusunu olusturur
        this.fareKontrol = new FareKontrolcu(this);
        
        // Dusman ureticisini baslatir (Emre)
        this.dusmanUretici = new DusmanUretici(this);
        
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
        
        // Aktif Deneyim Kristallerini cizer (Gizem)
        for (int i = 0; i < kristaller.size(); i++) {
            kristaller.get(i).ciz(g2);
        }
        
        // Aktif Mermileri cizer (Gizem & Andaç)
        for (int i = 0; i < mermiler.size(); i++) {
            mermiler.get(i).ciz(g2);
        }
        
        // Aktif Dusmanlari cizer (Emre)
        for (int i = 0; i < dusmanlar.size(); i++) {
            dusmanlar.get(i).ciz(g2);
        }
        
        // Oyuncu karakterini dunya koordinatlarina gore cizer (Emre'nin Oyuncu sinifindaki ciz metodu)
        oyuncu.ciz(g2);
        
        // 2. SABİT EKRAN ARAYÜZÜNÜ ÇİZ (Kamerayi geri cevir / translate geri al)
        g2.translate(kameraX, kameraY);
        
        // Sabit ekran yazilari (Kameradan etkilenmeyen HUD elemanlari)
        g2.setColor(Color.WHITE);
        g2.drawString("Piksel Hayatta Kalma Oyunu - Kamera ve Harita Sinirlari Entegre Edildi!", 20, 30);
        g2.drawString("Oyuncu Konumu: X=" + (int) oyuncu.x + ", Y=" + (int) oyuncu.y, 20, 55);
        g2.drawString("Can: " + (int) oyuncu.can + "/" + (int) oyuncu.maksCan + " | Seviye: " + oyuncu.seviye + " | DP: " + (int) oyuncu.deneyim + "/" + (int) oyuncu.sonrakiSeviyeDeneyimi, 20, 80);
        g2.drawString("Aktif Silah Sayısı: " + silahlar.size() + " | Mermi Sayısı: " + mermiler.size(), 20, 105);
        
        // Eger oyun GELISIM (seviye atlama duraklamasi) durumundaysa, kart seçim arayuzunu en uste cizer
        if (durum == OyunDurumu.GELISIM) {
            seviyeArayuzu.ciz(g2);
        }
        
        // Cizim islemlerinin bellek temizligini yapar
        g2.dispose();
    }
    
    // Oyun durumlarini guncelleyen metot (OyunDongusu tarafindan periyodik olarak tetiklenir)
    public void guncelle() {
        // Eger oyun GELISIM (seviye atlama) durumundaysa sadece kart arayuzunu gunceller (diger fizik/hareket nesneleri dondurulur)
        if (durum == OyunDurumu.GELISIM) {
            seviyeArayuzu.guncelle();
            return; // Diger nesnelerin hareketlerini ve guncellemelerini pas gecer (Pause)
        }
        
        // Eger oyun normal OYUN durumundaysa tum fizik ve hareketleri gunceller
        if (durum == OyunDurumu.OYUN) {
            // Oyuncunun hareketlerini ve konumunu gunceller
            oyuncu.guncelle(this.tusKontrol);
            
            // Oyuncunun aktif silahlarini gunceller ve ates etmelerini saglar
            for (int i = 0; i < silahlar.size(); i++) {
                silahlar.get(i).guncelle(dusmanlar, mermiler);
            }
            
            // Aktif mermilerin konumlarini gunceller, menzil disi kalanlari temizler
            for (int i = mermiler.size() - 1; i >= 0; i--) {
                Mermi m = mermiler.get(i);
                m.guncelle();
                if (!m.aktif) {
                    mermiler.remove(i);
                }
            }
            
            // Aktif deneyim kristallerinin oyuncuya çekilmesini ve toplanmasini gunceller
            for (int i = kristaller.size() - 1; i >= 0; i--) {
                DeneyimKristali dk = kristaller.get(i);
                dk.guncelle(this);
                if (dk.toplandi) {
                    kristaller.remove(i);
                }
            }
            
            // Aktif dusmanlarin hareket yapay zekasini gunceller
            for (int i = 0; i < dusmanlar.size(); i++) {
                dusmanlar.get(i).guncelle(oyuncu);
            }
            
            // Dusman ureticisini gunceller ve yeni dalgalari dogurur (Emre)
            this.dusmanUretici.guncelle();
            
            // Kamerayi oyuncuyu tam ortalayacak sekilde konumlandirir
            kameraX = oyuncu.x - EKRAN_GENISLIGI / 2.0;
            kameraY = oyuncu.y - EKRAN_YUKSEKLIGI / 2.0;
            
            // Kameranin 3000x3000px haritanin disina cikmasini engeller (Siyah bosluk gosterilmez)
            kameraX = Math.max(0, Math.min(kameraX, HARITA_BOYUTU - EKRAN_GENISLIGI));
            kameraY = Math.max(0, Math.min(kameraY, HARITA_BOYUTU - EKRAN_YUKSEKLIGI));
        }
    }
}
