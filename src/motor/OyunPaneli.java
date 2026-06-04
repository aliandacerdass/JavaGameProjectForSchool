package motor;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import varliklar.*;
import mekanikler.*;

// OyunPaneli sinifi, oyunun cizimlerinin yapildigi ve ekranda gosterildigi ana alandir
public class OyunPaneli extends JPanel {
    
    // Zemin kaplamasi icin kullanilacak karo gorseli (Andaç)
    private BufferedImage zeminKarosu;
    
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
    // Dusman dalga ureticisi nesnesi (Emre)
    public final DusmanUretici dusmanUretici;

    // Kurucu metot: Panelin boyutlarini ve arka planini ayarlar
    public OyunPaneli() {
        // Panelin tercih edilen boyutunu belirler
        this.setPreferredSize(new Dimension(EKRAN_GENISLIGI, EKRAN_YUKSEKLIGI));
        // Arka plan rengini bataklik yesili yapar (Zemin karosuyla butunlesmesi icin)
        this.setBackground(new Color(52, 83, 24));
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
        
        // Zemin tileset'ini yukler ve bir karosunu keser (Andaç)
        try {
            BufferedImage tileset = GorselYukleyici.gorselYukle("assets/Dark_Swamp_Starter_Pack_v1.0 23.13.22/RawAssets/GroundTileset.png");
            if (tileset != null) {
                // GroundTileset uzerindeki bataklik/camur zemin karosunu keser (x=0, y=0, w=24, h=24)
                this.zeminKarosu = tileset.getSubimage(0, 0, 24, 24);
            }
        } catch (Exception e) {
            System.out.println("Zemin karosu yuklenirken hata olustu, grid cizimine gecilecek. Detay: " + e.getMessage());
            this.zeminKarosu = null;
        }
        
        // Panelin klavye girdilerini dinlemesini saglar
        this.addKeyListener(this.tusKontrol);
        // Panelin fare girdilerini dinlemesini saglar
        this.addMouseListener(this.fareKontrol);
        this.addMouseMotionListener(this.fareKontrol);
        
        // Test silahlarini ekler (Oyuncunun saldiri yapabilmesi icin)
        this.silahlar.add(new AtesTopu(this.oyuncu));
        this.silahlar.add(new DonerBicak(this.oyuncu));
    }
    
    // AWT/Swing tarafindan panel cizimi gerektiginde otomatik cagirilan metot
    @Override
    protected void paintComponent(Graphics g) {
        // Ust sinifin paintComponent metodunu cagirarak paneli temizler
        super.paintComponent(g);
        
        // Daha gelismis cizim metotlari icin Graphics nesnesini Graphics2D'ye donustururuz
        Graphics2D g2 = (Graphics2D) g;
        
        // Piksel art görsellerin bulaniklasmasini onlemek icin antialiasing kapatilir ve en yakin komsu interpolasyonu aktif edilir (Andaç)
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        
        // 1. DÜNYA NESNELERİNİ ÇİZ (Kamera kaydirmasini uygula)
        g2.translate(-kameraX, -kameraY);
        
        // Zemin cizimi (Andaç)
        if (zeminKarosu != null) {
            // Sadece kameranın gördüğü alandaki karoları (tiles) çizerek optimizasyon yapıyoruz
            // 48x48 boyutlarındaki karolar için başlangıç ve bitiş indekslerini hesaplarız (2x retro olcek)
            int karoBoyutu = 48;
            int baslangicX = Math.max(0, (int) (kameraX / karoBoyutu) * karoBoyutu);
            int bitisX = Math.min(HARITA_BOYUTU, (int) ((kameraX + EKRAN_GENISLIGI) / karoBoyutu + 2) * karoBoyutu);
            int baslangicY = Math.max(0, (int) (kameraY / karoBoyutu) * karoBoyutu);
            int bitisY = Math.min(HARITA_BOYUTU, (int) ((kameraY + EKRAN_YUKSEKLIGI) / karoBoyutu + 2) * karoBoyutu);
            
            // Hesaplanan sınırlar dahilinde zemin karolarını yan yana döşeriz (tiling)
            for (int x = baslangicX; x < bitisX; x += karoBoyutu) {
                for (int y = baslangicY; y < bitisY; y += karoBoyutu) {
                    g2.drawImage(zeminKarosu, x, y, karoBoyutu, karoBoyutu, null);
                }
            }
        } else {
            // FALLBACK (Yedek Çizim): Eğer zemin gorseli yüklenemediyse gri grid çizgileri çizeriz
            g2.setColor(new Color(40, 40, 40));
            for (int i = 0; i <= HARITA_BOYUTU; i += 100) {
                g2.drawLine(i, 0, i, HARITA_BOYUTU);
                g2.drawLine(0, i, HARITA_BOYUTU, i);
            }
        }
        
        // Haritanin dis sinirlarini kirmizi bir cerceve ile belirler
        g2.setColor(Color.RED);
        g2.drawRect(0, 0, HARITA_BOYUTU, HARITA_BOYUTU);
        
        // Yerdeki deneyim kristallerini cizer
        for (int i = 0; i < kristaller.size(); i++) {
            kristaller.get(i).ciz(g2);
        }
        
        // Havada giden aktif mermileri cizer
        for (int i = 0; i < mermiler.size(); i++) {
            mermiler.get(i).ciz(g2);
        }
        
        // Aktif dusmanlari cizer
        for (int i = 0; i < dusmanlar.size(); i++) {
            dusmanlar.get(i).ciz(g2);
        }
        
        // Oyuncu karakterini dunya koordinatlarina gore cizer (Emre'nin Oyuncu sinifindaki ciz metodu)
        oyuncu.ciz(g2);
        
        // 2. SABİT EKRAN ARAYÜZÜNÜ ÇİZ (Kamerayi geri cevir / translate geri al)
        g2.translate(kameraX, kameraY);
        
        // Varsayılan fontları ayarla (her frame sıfırlamak için)
        Font varsayilanFont = new Font("Arial", Font.PLAIN, 12);
        Font boldFont = new Font("Arial", Font.BOLD, 12);
        g2.setFont(varsayilanFont);
        
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
        g2.setFont(boldFont);
        g2.setColor(Color.WHITE);
        String xpMetni = "SEVIYE: " + oyuncu.seviye + "  |  DP: " + (int) oyuncu.deneyim + " / " + (int) oyuncu.sonrakiSeviyeDeneyimi;
        g2.drawString(xpMetni, EKRAN_GENISLIGI / 2 - 100, xpBarY + 11);
        
        // --- 2b. SOL ÜST CAN BARI (HP BAR) ---
        int hpBarX = 35; // Kalp ikonuna yer acmak icin saga kaydirildi
        int hpBarY = 35;
        int hpBarGenislik = 200;
        int hpBarYukseklik = 22;
        
        // HP Bar Arka Plani (Koyu Gri)
        g2.setColor(new Color(50, 50, 50, 200));
        g2.fillRect(hpBarX, hpBarY, hpBarGenislik, hpBarYukseklik);
        
        // HP Bar Doluluk Orani
        double hpOran = oyuncu.can / oyuncu.maksCan;
        int hpDolulukGenislik = (int) (hpBarGenislik * Math.min(1.0, Math.max(0.0, hpOran)));
        
        // HP Bar Dolu Alanı (Göz Alıcı Koyu Kırmızı - Kritik canda yanıp söner)
        if (hpOran < 0.3 && (oyunSuresiKareSayisi / 15) % 2 == 0) {
            g2.setColor(Color.ORANGE);
        } else {
            g2.setColor(new Color(220, 20, 60));
        }
        g2.fillRect(hpBarX, hpBarY, hpDolulukGenislik, hpBarYukseklik);
        
        // HP Bar Çerçevesi (Piksel Altın Rengi Çift Çerçeve)
        g2.setColor(new Color(218, 165, 32)); // Altın rengi
        g2.drawRect(hpBarX, hpBarY, hpBarGenislik, hpBarYukseklik);
        g2.drawRect(hpBarX + 2, hpBarY + 2, hpBarGenislik - 4, hpBarYukseklik - 4);
        
        // HP Metni (Can Barı üzerine ortalanmış)
        g2.setFont(boldFont);
        g2.setColor(Color.WHITE);
        String hpMetni = "CAN: " + (int) oyuncu.can + " / " + (int) oyuncu.maksCan;
        g2.drawString(hpMetni, hpBarX + 45, hpBarY + 16);
        
        // Can barının soluna küçük bir kalp ikonu (piksel)
        int kalpX = hpBarX - 18;
        int kalpY = hpBarY + 3;
        g2.setColor(Color.RED);
        g2.fillRect(kalpX + 2, kalpY, 3, 3);
        g2.fillRect(kalpX + 7, kalpY, 3, 3);
        g2.fillRect(kalpX, kalpY + 3, 12, 5);
        g2.fillRect(kalpX + 2, kalpY + 8, 8, 3);
        g2.fillRect(kalpX + 4, kalpY + 11, 4, 3);
        g2.fillRect(kalpX + 5, kalpY + 14, 2, 2);
        
        // --- 2c. ÜST ORTA KRONOMETRE (STOPWATCH) ---
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
        g2.setFont(new Font("Arial", Font.BOLD, 14));
        g2.setColor(Color.YELLOW);
        g2.drawString(zamanMetni, kronoX + 26, kronoY + 18);
        
        // Saat simgesi (piksel)
        int saatX = kronoX + 8;
        int saatY = kronoY + 7;
        g2.setColor(Color.WHITE);
        g2.drawOval(saatX, saatY, 12, 12);
        g2.drawLine(saatX + 6, saatY + 6, saatX + 6, saatY + 3);
        g2.drawLine(saatX + 6, saatY + 6, saatX + 9, saatY + 6);
        
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
        
        // Envanter Başlığı
        g2.setFont(boldFont);
        g2.setColor(Color.WHITE);
        g2.drawString("ENVANTER:", envX + 10, envY + 26);
        
        // Slot kutularını ciz (Gizem/Andaç)
        int slotX = envX + 90;
        int slotY = envY + 6;
        int slotBoyut = 32;
        
        for (int i = 0; i < 3; i++) {
            // Slot kutusu arka planı
            g2.setColor(new Color(30, 41, 59, 200));
            g2.fillRect(slotX, slotY, slotBoyut, slotBoyut);
            
            // Slot kutusu çerçevesi (Piksel stil)
            g2.setColor(Color.GRAY);
            g2.drawRect(slotX, slotY, slotBoyut, slotBoyut);
            
            // Eğer o slotta silah varsa silahı ve seviyesini çiz
            if (i < silahlar.size()) {
                Silah s = silahlar.get(i);
                if (s.ad.equals("Ates Topu")) {
                    // Ateş Topu İkonu (Turuncu/Kırmızı alev)
                    g2.setColor(Color.ORANGE);
                    g2.fillOval(slotX + 8, slotY + 8, 16, 16);
                    g2.setColor(Color.RED);
                    g2.drawOval(slotX + 8, slotY + 8, 16, 16);
                } else if (s.ad.equals("Doner Bicak")) {
                    // Döner Bıçak İkonu (Cyan çarpı şeklinde bıçak)
                    g2.setColor(Color.CYAN);
                    g2.fillRect(slotX + 14, slotY + 6, 4, 20);
                    g2.fillRect(slotX + 6, slotY + 14, 20, 4);
                }
                
                // Silah Seviyesi (Sağ altta küçük sarı yazı)
                g2.setFont(new Font("Arial", Font.BOLD, 10));
                g2.setColor(Color.YELLOW);
                g2.drawString("" + s.seviye, slotX + 22, slotY + 28);
            }
            
            slotX += 38;
        }
        
        // Genel İpuçları Yazısı (Ekranın sol altında)
        g2.setFont(new Font("Arial", Font.PLAIN, 11));
        g2.setColor(new Color(200, 200, 200, 150));
        g2.drawString("Hareket: WASD | Durum: Seviye atlayarak yeni silahlar kazanın ve güçlenin!", 20, EKRAN_YUKSEKLIGI - 20);
        
        // Eger oyun GELISIM (seviye atlama duraklamasi) durumundaysa, kart seçim arayuzunu en uste cizer
        if (durum == OyunDurumu.GELISIM) {
            seviyeArayuzu.ciz(g2);
        }
        
        // --- 2e. OYUN BİTTİ EKRANI ÇİZİMİ ---
        if (durum == OyunDurumu.OYUN_BITTI) {
            // Ekrana yari saydam koyu siyah perde çekeriz
            g2.setColor(new Color(5, 5, 10, 220));
            g2.fillRect(0, 0, EKRAN_GENISLIGI, EKRAN_YUKSEKLIGI);
            
            // Retro bir panel kutusu
            int panelW = 400;
            int panelH = 260;
            int panelX = (EKRAN_GENISLIGI - panelW) / 2;
            int panelY = (EKRAN_YUKSEKLIGI - panelH) / 2;
            
            g2.setColor(new Color(15, 23, 42, 245));
            g2.fillRect(panelX, panelY, panelW, panelH);
            
            // Kırmızı ve altın çift çerçeve
            g2.setColor(Color.RED);
            g2.drawRect(panelX, panelY, panelW, panelH);
            g2.setColor(new Color(218, 165, 32));
            g2.drawRect(panelX + 3, panelY + 3, panelW - 6, panelH - 6);
            
            // OYUN BITTI yazisi
            g2.setColor(Color.RED);
            g2.setFont(new Font("Arial", Font.BOLD, 40));
            g2.drawString("OYUN BITTI", panelX + 85, panelY + 60);
            
            // İnce ayırıcı çizgi
            g2.setColor(Color.DARK_GRAY);
            g2.drawLine(panelX + 40, panelY + 80, panelX + panelW - 40, panelY + 80);
            
            // Skor ve İstatistikler
            g2.setFont(new Font("Arial", Font.BOLD, 16));
            g2.setColor(Color.WHITE);
            g2.drawString("Ulasilan Seviye: " + oyuncu.seviye, panelX + 60, panelY + 120);
            g2.drawString("Hayatta Kalma Suresi: " + zamanMetni, panelX + 60, panelY + 155);
            
            // İnce ayırıcı çizgi
            g2.drawLine(panelX + 40, panelY + 185, panelX + panelW - 40, panelY + 185);
            
            // R tusu ile yeniden baslatma talimati yazisi
            g2.setColor(Color.CYAN);
            g2.setFont(new Font("Arial", Font.BOLD, 14));
            g2.drawString("Yeniden baslamak icin 'R' tusuna basin.", panelX + 65, panelY + 220);
        }
        
        // Cizim islemlerinin bellek temizligini yapar
        g2.dispose();
    }
    
    // Oyun durumlarini guncelleyen metot (OyunDongusu tarafindan periyodik olarak tetiklenir)
    public void guncelle() {
        // Eger oyun bittiyse sadece yeniden baslatma tusunu kontrol ederiz
        if (durum == OyunDurumu.OYUN_BITTI) {
            if (tusKontrol.rTusu) {
                oyunuSifirla();
            }
            return;
        }
        
        // Eger oyun GELISIM (seviye atlama) durumundaysa sadece kart arayuzunu gunceller
        if (durum == OyunDurumu.GELISIM) {
            seviyeArayuzu.guncelle();
            return;
        }
        
        // Eger oyun normal OYUN durumundaysa tum fizik ve hareketleri gunceller
        if (durum == OyunDurumu.OYUN) {
            // Saniye sayacı için kare sayısını artırır (Gizem)
            oyunSuresiKareSayisi++;
            
            // Oyuncunun hareketlerini ve konumunu gunceller
            oyuncu.guncelle(this.tusKontrol);
            
            // Oyuncunun canı bittiyse oyunu OYUN_BITTI durumuna aliriz
            if (oyuncu.can <= 0) {
                durum = OyunDurumu.OYUN_BITTI;
                return;
            }
            
            // Eger haritada hic dusman kalmadiysa test amaciyla yeni dusmanlar spawn eder (Andac test edebilsin diye)
            if (dusmanlar.isEmpty()) {
                // Standart yesil slime dusman ekleriz
                dusmanlar.add(new Dusman(oyuncu.x + 300, oyuncu.y, 40.0, 1.8, 15.0, 16.0));
                // Hizli kirmizi slime dusman ekleriz
                dusmanlar.add(new HizliDusman(oyuncu.x - 300, oyuncu.y));
                // Golem gri kaya slime boss ekleriz
                dusmanlar.add(new GolemDusman(oyuncu.x, oyuncu.y + 350));
            }
            
            // Oyuncunun aktif silahlarini gunceller ve ates etmelerini saglar
            for (int i = 0; i < silahlar.size(); i++) {
                silahlar.get(i).guncelle(dusmanlar, mermiler);
            }
            
            // Aktif mermilerin konumlarini gunceller
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
            
            // Çarpışmaları denetler (Andac'in CarpismaDenetleyici sınıfındaki metot cagrilir)
            CarpismaDenetleyici.carpismalariDenetle(oyuncu, dusmanlar, mermiler, kristaller);
            
            // Ölen düşmanları temizler
            dusmanlar.removeIf(d -> d.can <= 0);
            
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
        
        // Oyun suresini sifirla
        oyunSuresiKareSayisi = 0;
        
        // Oyun durumunu aktif oynanis durumuna getirir
        durum = OyunDurumu.OYUN;
        System.out.println("Oyun yeniden baslatildi!");
    }
}
