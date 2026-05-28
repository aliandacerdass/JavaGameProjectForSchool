package motor;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.util.ArrayList;
import varliklar.Oyuncu;
import varliklar.Dusman;
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
    
    // Oyunda gecen sureyi saniye bazinda hesaplamak icin kare/frame sayaci (Gizem)
    public int oyunSuresiKareSayisi = 0;
    
    // Klavye girdi kontrolcusu
    public final TusKontrolcu tusKontrol;
    // Fare girdi kontrolcusu
    public final FareKontrolcu fareKontrol;
    
    // Oyuncu karakteri nesnesi
    public final Oyuncu oyuncu;

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
        
        // --- 2a. EN TEPEDE YATAY DENEYİM BARI (XP BAR) ---
        int xpBarX = 20;
        int xpBarY = 10;
        int xpBarGenislik = EKRAN_GENISLIGI - 40; // 760 piksel genislik
        int xpBarYukseklik = 14;
        
        // XP Bar Arka Plani (Koyu Cam)
        g2.setColor(new Color(30, 30, 40, 220));
        g2.fillRect(xpBarX, xpBarY, xpBarGenislik, xpBarYukseklik);
        
        // XP Bar Doluluk Orani
        double xpOran = oyuncu.deneyim / oyuncu.sonrakiSeviyeDeneyimi;
        int xpDolulukGenislik = (int) (xpBarGenislik * Math.min(1.0, Math.max(0.0, xpOran)));
        
        // XP Bar Dolu Alanı (Neon Mor/Eflatun renk - Premium hissi için)
        g2.setColor(new Color(138, 43, 226));
        g2.fillRect(xpBarX, xpBarY, xpDolulukGenislik, xpBarYukseklik);
        
        // XP Bar Çift Çerçevesi (Piksel stil)
        g2.setColor(Color.WHITE);
        g2.drawRect(xpBarX, xpBarY, xpBarGenislik, xpBarYukseklik);
        
        // XP Metni (Barın üstüne ortalanmış)
        g2.setColor(Color.WHITE);
        String xpMetni = "SEVIYE: " + oyuncu.seviye + "  |  DP: " + (int) oyuncu.deneyim + " / " + (int) oyuncu.sonrakiSeviyeDeneyimi;
        g2.drawString(xpMetni, EKRAN_GENISLIGI / 2 - 100, xpBarY + 11);
        
        // --- 2b. SOL ÜST CAN BARI (HP BAR) ---
        int hpBarX = 20;
        int hpBarY = 35;
        int hpBarGenislik = 200;
        int hpBarYukseklik = 22;
        
        // HP Bar Arka Plani (Koyu Gri)
        g2.setColor(new Color(50, 50, 50, 200));
        g2.fillRect(hpBarX, hpBarY, hpBarGenislik, hpBarYukseklik);
        
        // HP Bar Doluluk Orani
        double hpOran = oyuncu.can / oyuncu.maksCan;
        int hpDolulukGenislik = (int) (hpBarGenislik * Math.min(1.0, Math.max(0.0, hpOran)));
        
        // HP Bar Dolu Alanı (Göz Alıcı Koyu Kırmızı)
        g2.setColor(new Color(220, 20, 60));
        g2.fillRect(hpBarX, hpBarY, hpDolulukGenislik, hpBarYukseklik);
        
        // HP Bar Çerçevesi (Piksel Altın Rengi Çift Çerçeve)
        g2.setColor(new Color(218, 165, 32)); // Altın rengi
        g2.drawRect(hpBarX, hpBarY, hpBarGenislik, hpBarYukseklik);
        g2.drawRect(hpBarX + 2, hpBarY + 2, hpBarGenislik - 4, hpBarYukseklik - 4);
        
        // HP Metni (Can Barı üzerine ortalanmış)
        g2.setColor(Color.WHITE);
        String hpMetni = "CAN: " + (int) oyuncu.can + " / " + (int) oyuncu.maksCan;
        g2.drawString(hpMetni, hpBarX + 45, hpBarY + 16);
        
        // --- 2c. ÜST ORTA KRONOMETRE (STOPWATCH) ---
        // Saniye ve dakika hesaplaması (60 kare = 1 saniye)
        int saniye = oyunSuresiKareSayisi / 60;
        int dakika = saniye / 60;
        int kalanSaniye = saniye % 60;
        String zamanMetni = String.format("%02d:%02d", dakika, kalanSaniye);
        
        // Kronometre Kutusu (Piksel cam tasarım)
        int kronoX = EKRAN_GENISLIGI / 2 - 45;
        int kronoY = 35;
        int kronoGenislik = 90;
        int kronoYukseklik = 26;
        
        g2.setColor(new Color(15, 23, 42, 220));
        g2.fillRect(kronoX, kronoY, kronoGenislik, kronoYukseklik);
        g2.setColor(Color.CYAN);
        g2.drawRect(kronoX, kronoY, kronoGenislik, kronoYukseklik);
        
        // Zaman Yazısı
        g2.setColor(Color.YELLOW);
        g2.drawString(zamanMetni, kronoX + 26, kronoY + 18);
        
        // --- 2d. SAĞ ÜST ENVANTER VE DURUM GÖSTERGESİ ---
        int envX = EKRAN_GENISLIGI - 220;
        int envY = 35;
        int envGenislik = 200;
        int envYukseklik = 45;
        
        // Envanter Kutusu (Koyu Cam Görünümü)
        g2.setColor(new Color(15, 23, 42, 220));
        g2.fillRect(envX, envY, envGenislik, envYukseklik);
        g2.setColor(Color.CYAN);
        g2.drawRect(envX, envY, envGenislik, envYukseklik);
        
        // Envanter İçeriği (Aktif silahları küçük sembollerle listeler)
        g2.setColor(Color.WHITE);
        g2.drawString("ENVANTER:", envX + 10, envY + 18);
        
        int ikonX = envX + 85;
        for (int i = 0; i < silahlar.size(); i++) {
            Silah s = silahlar.get(i);
            if (s.ad.equals("Ates Topu")) {
                // Ateş Topu İkonu (Turuncu daire)
                g2.setColor(Color.ORANGE);
                g2.fillOval(ikonX, envY + 6, 12, 12);
                g2.setColor(Color.WHITE);
                g2.drawString("L" + s.seviye, ikonX + 15, envY + 17);
                ikonX += 45;
            } else if (s.ad.equals("Doner Bicak")) {
                // Döner Bıçak İkonu (Cyan daire)
                g2.setColor(Color.CYAN);
                g2.fillOval(ikonX, envY + 6, 12, 12);
                g2.setColor(Color.WHITE);
                g2.drawString("L" + s.seviye, ikonX + 15, envY + 17);
                ikonX += 45;
            }
        }
        
        // Genel İpuçları Yazısı (Ekranın sol altında)
        g2.setColor(new Color(200, 200, 200, 150));
        g2.drawString("Hareket: WASD | Durum: Seviye atlayarak yeni silahlar kazanın ve güçlenin!", 20, EKRAN_YUKSEKLIGI - 20);
        
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
            // Saniye sayacı için kare sayısını artırır (Gizem)
            oyunSuresiKareSayisi++;
            
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
            
            // Kamerayi oyuncuyu tam ortalayacak sekilde konumlandirir
            kameraX = oyuncu.x - EKRAN_GENISLIGI / 2.0;
            kameraY = oyuncu.y - EKRAN_YUKSEKLIGI / 2.0;
            
            // Kameranin 3000x3000px haritanin disina cikmasini engeller (Siyah bosluk gosterilmez)
            kameraX = Math.max(0, Math.min(kameraX, HARITA_BOYUTU - EKRAN_GENISLIGI));
            kameraY = Math.max(0, Math.min(kameraY, HARITA_BOYUTU - EKRAN_YUKSEKLIGI));
        }
    }
}
