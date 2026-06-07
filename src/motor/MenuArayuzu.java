package motor;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;

// MenuArayuzu sinifi, oyun ilk acildiginda gorunen ana giris ekranini,
// ses ve zorluk ayarlarini ve baslat butonunu yonetir (Andaç)
public class MenuArayuzu {
    private final OyunPaneli panel;
    
    // Buton alanlari (Tiklama algilamak icin)
    public final Rectangle baslatButon;
    public final Rectangle sesEksiButon;
    public final Rectangle sesArtiButon;
    public final Rectangle kolayButon;
    public final Rectangle normalButon;
    public final Rectangle zorButon;
    
    // Kurucu metot: Butonlarin koordinatlarini ekrana gore konumlandirir
    public MenuArayuzu(OyunPaneli panel) {
        this.panel = panel;
        
        int centerX = OyunPaneli.EKRAN_GENISLIGI / 2;
        
        // Baslat Butonu: Ortalanmis 200x50px kutu
        this.baslatButon = new Rectangle(centerX - 100, 240, 200, 50);
        
        // Ses seviyesi + ve - butonlari
        this.sesEksiButon = new Rectangle(centerX - 120, 360, 40, 30);
        this.sesArtiButon = new Rectangle(centerX + 80, 360, 40, 30);
        
        // Zorluk secim butonlari: Kolay, Normal, Zor
        this.kolayButon = new Rectangle(centerX - 130, 460, 80, 35);
        this.normalButon = new Rectangle(centerX - 40, 460, 80, 35);
        this.zorButon = new Rectangle(centerX + 50, 460, 80, 35);
    }
    
    // Fare tiklamalarini algilayip ayarlari guncelleyen metot
    public void guncelle() {
        if (panel.fareKontrol.tiklamayiKontrolEt()) {
            int mx = panel.fareKontrol.getTiklamaX();
            int my = panel.fareKontrol.getTiklamaY();
            
            // 1. BASLAT BUTONU TIKLANIRSA
            if (baslatButon.contains(mx, my)) {
                // Oyunu sifirlayip durumunu OYUN yapar
                panel.oyunuSifirla();
                panel.durum = OyunDurumu.OYUN;
                panel.fareKontrol.temizle();
                
                // Baslangic ses efekti calar
                SesSentezleyici.gucMeyvesi();
                System.out.println("Oyun menu uzerinden baslatildi!");
                return;
            }
            
            // 2. SES EKSI BUTONU
            if (sesEksiButon.contains(mx, my)) {
                SesSentezleyici.sesSeviyesi = Math.max(0.0f, SesSentezleyici.sesSeviyesi - 0.1f);
                SesSentezleyici.gucMeyvesi(); // Yeni ses seviyesini oyuncuya hissettirmek icin bip calar
                panel.fareKontrol.temizle();
            }
            
            // 3. SES ARTI BUTONU
            if (sesArtiButon.contains(mx, my)) {
                SesSentezleyici.sesSeviyesi = Math.min(1.0f, SesSentezleyici.sesSeviyesi + 0.1f);
                SesSentezleyici.gucMeyvesi(); // Yeni ses seviyesini oyuncuya hissettirmek icin bip calar
                panel.fareKontrol.temizle();
            }
            
            // 4. KOLAY ZORLUK MODU
            if (kolayButon.contains(mx, my)) {
                panel.zorlukModu = 0.7;
                SesSentezleyici.atesTopu(); // Secim sesi calar
                panel.fareKontrol.temizle();
            }
            
            // 5. NORMAL ZORLUK MODU
            if (normalButon.contains(mx, my)) {
                panel.zorlukModu = 1.0;
                SesSentezleyici.atesTopu(); // Secim sesi calar
                panel.fareKontrol.temizle();
            }
            
            // 6. ZOR ZORLUK MODU
            if (zorButon.contains(mx, my)) {
                panel.zorlukModu = 1.5;
                SesSentezleyici.atesTopu(); // Secim sesi calar
                panel.fareKontrol.temizle();
            }
        }
    }
    
    // Giris ekranini ekrana cizen metot (Retro yesil cam efekti ve premium grafikler)
    public void ciz(Graphics2D g2) {
        int centerX = OyunPaneli.EKRAN_GENISLIGI / 2;
        
        // 1. Koyu bataklik/retro ana arka plan dolgusu
        g2.setColor(new Color(15, 23, 15)); // Cok koyu yesil siyah arasi
        g2.fillRect(0, 0, OyunPaneli.EKRAN_GENISLIGI, OyunPaneli.EKRAN_YUKSEKLIGI);
        
        // Arka plana hafif retro grid cizgileri ekleyelim (Premium gorunum icin)
        g2.setColor(new Color(30, 50, 30, 40));
        for (int i = 0; i < OyunPaneli.EKRAN_GENISLIGI; i += 40) {
            g2.drawLine(i, 0, i, OyunPaneli.EKRAN_YUKSEKLIGI);
        }
        for (int j = 0; j < OyunPaneli.EKRAN_YUKSEKLIGI; j += 40) {
            g2.drawLine(0, j, OyunPaneli.EKRAN_GENISLIGI, j);
        }
        
        // 2. Ana Panel Kutusu (Glassmorphism cam paneli)
        int panelW = 460;
        int panelH = 480;
        int panelX = centerX - panelW / 2;
        int panelY = 70;
        
        // Yari saydam cam dolgusu
        g2.setColor(new Color(25, 38, 25, 235));
        g2.fillRect(panelX, panelY, panelW, panelH);
        
        // Neon yesili cift cerceve
        g2.setColor(new Color(50, 205, 50));
        g2.drawRect(panelX, panelY, panelW, panelH);
        g2.setColor(new Color(34, 139, 34));
        g2.drawRect(panelX + 3, panelY + 3, panelW - 6, panelH - 6);
        
        // 3. LOGO / OYUN ISMI "slimeSlayer" (Cift katmanli neon efekti)
        g2.setFont(new Font("Arial", Font.BOLD, 48));
        String baslik = "slimeSlayer";
        
        // Parlama (Glow)
        g2.setColor(new Color(0, 100, 0, 150));
        g2.drawString(baslik, centerX - 138, 147);
        
        // Ana yazi (Neon Parlak Yesil)
        g2.setColor(new Color(50, 255, 50));
        g2.drawString(baslik, centerX - 140, 145);
        
        // Alt slogan yazisi
        g2.setFont(new Font("Arial", Font.ITALIC, 13));
        g2.setColor(Color.LIGHT_GRAY);
        g2.drawString("Java 2D Retro Survival Roguelite", centerX - 105, 175);
        
        // Ince neon ayirici cizgi
        g2.setColor(new Color(50, 205, 50, 100));
        g2.drawLine(panelX + 30, 195, panelX + panelW - 30, 195);
        
        // Fare koordinatlarini hover efekti icin okuruz
        int fx = panel.fareKontrol.getFareX();
        int fy = panel.fareKontrol.getFareY();
        
        // 4. BASLAT BUTONU CIZIMI
        boolean baslatHover = baslatButon.contains(fx, fy);
        int baslatYOffset = baslatHover ? -4 : 0; // Hover olunca 4px yukari kalkar
        
        g2.setColor(baslatHover ? new Color(50, 255, 50) : new Color(34, 139, 34));
        g2.fillRect(baslatButon.x, baslatButon.y + baslatYOffset, baslatButon.width, baslatButon.height);
        
        g2.setColor(Color.WHITE);
        g2.drawRect(baslatButon.x, baslatButon.y + baslatYOffset, baslatButon.width, baslatButon.height);
        g2.drawRect(baslatButon.x + 2, baslatButon.y + baslatYOffset + 2, baslatButon.width - 4, baslatButon.height - 4);
        
        g2.setFont(new Font("Arial", Font.BOLD, 20));
        g2.setColor(Color.WHITE);
        g2.drawString("OYUNU BASLAT", baslatButon.x + 24, baslatButon.y + baslatYOffset + 32);
        
        // 5. SES SEVIYESI AYARI CIZIMI
        g2.setFont(new Font("Arial", Font.BOLD, 15));
        g2.setColor(Color.YELLOW);
        g2.drawString("SES SEVIYESI", centerX - 52, 335);
        
        // Eksi Butonu
        boolean eksiHover = sesEksiButon.contains(fx, fy);
        g2.setColor(eksiHover ? Color.LIGHT_GRAY : Color.GRAY);
        g2.fillRect(sesEksiButon.x, sesEksiButon.y, sesEksiButon.width, sesEksiButon.height);
        g2.setColor(Color.WHITE);
        g2.drawRect(sesEksiButon.x, sesEksiButon.y, sesEksiButon.width, sesEksiButon.height);
        g2.setFont(new Font("Arial", Font.BOLD, 20));
        g2.drawString("-", sesEksiButon.x + 16, sesEksiButon.y + 22);
        
        // Ses Durum Barı
        int barX = centerX - 65;
        int barY = 365;
        int barW = 130;
        int barH = 20;
        g2.setColor(new Color(15, 23, 42));
        g2.fillRect(barX, barY, barW, barH);
        g2.setColor(Color.DARK_GRAY);
        g2.drawRect(barX, barY, barW, barH);
        
        // Ses doluluk hilesi
        int dolulukW = (int) (barW * SesSentezleyici.sesSeviyesi);
        g2.setColor(new Color(0, 255, 255));
        g2.fillRect(barX + 2, barY + 2, Math.max(0, dolulukW - 4), barH - 4);
        
        // Yuzde yazisi
        g2.setFont(new Font("Arial", Font.BOLD, 11));
        g2.setColor(Color.WHITE);
        g2.drawString(String.format("%.0f%%", SesSentezleyici.sesSeviyesi * 100), barX + barW / 2 - 12, barY + 14);
        
        // Arti Butonu
        boolean artiHover = sesArtiButon.contains(fx, fy);
        g2.setColor(artiHover ? Color.LIGHT_GRAY : Color.GRAY);
        g2.fillRect(sesArtiButon.x, sesArtiButon.y, sesArtiButon.width, sesArtiButon.height);
        g2.setColor(Color.WHITE);
        g2.drawRect(sesArtiButon.x, sesArtiButon.y, sesArtiButon.width, sesArtiButon.height);
        g2.setFont(new Font("Arial", Font.BOLD, 20));
        g2.drawString("+", sesArtiButon.x + 14, sesArtiButon.y + 22);
        
        // 6. ZORLUK SECEGI CIZIMI
        g2.setFont(new Font("Arial", Font.BOLD, 15));
        g2.setColor(Color.YELLOW);
        g2.drawString("ZORLUK DERECESI", centerX - 70, 435);
        
        // Kolay Butonu (0.7)
        boolean kolaySecili = (panel.zorlukModu == 0.7);
        boolean kolayHover = kolayButon.contains(fx, fy);
        g2.setColor(kolaySecili ? new Color(34, 139, 34) : (kolayHover ? Color.LIGHT_GRAY : Color.GRAY));
        g2.fillRect(kolayButon.x, kolayButon.y, kolayButon.width, kolayButon.height);
        g2.setColor(Color.WHITE);
        g2.drawRect(kolayButon.x, kolayButon.y, kolayButon.width, kolayButon.height);
        g2.setFont(new Font("Arial", Font.BOLD, 12));
        g2.drawString("KOLAY", kolayButon.x + 18, kolayButon.y + 22);
        
        // Normal Butonu (1.0)
        boolean normalSecili = (panel.zorlukModu == 1.0);
        boolean normalHover = normalButon.contains(fx, fy);
        g2.setColor(normalSecili ? new Color(218, 165, 32) : (normalHover ? Color.LIGHT_GRAY : Color.GRAY));
        g2.fillRect(normalButon.x, normalButon.y, normalButon.width, normalButon.height);
        g2.setColor(Color.WHITE);
        g2.drawRect(normalButon.x, normalButon.y, normalButon.width, normalButon.height);
        g2.drawString("NORMAL", normalButon.x + 13, normalButon.y + 22);
        
        // Zor Butonu (1.5)
        boolean zorSecili = (panel.zorlukModu == 1.5);
        boolean zorHover = zorButon.contains(fx, fy);
        g2.setColor(zorSecili ? new Color(178, 34, 34) : (zorHover ? Color.LIGHT_GRAY : Color.GRAY));
        g2.fillRect(zorButon.x, zorButon.y, zorButon.width, zorButon.height);
        g2.setColor(Color.WHITE);
        g2.drawRect(zorButon.x, zorButon.y, zorButon.width, zorButon.height);
        g2.drawString("ZOR", zorButon.x + 26, zorButon.y + 22);
        
        // Alt yapimci notu
        g2.setFont(new Font("Arial", Font.PLAIN, 10));
        g2.setColor(new Color(200, 200, 200, 80));
        g2.drawString("Yapimci Ekip: Gizem, Andac, Emre", centerX - 80, 530);
    }
}
