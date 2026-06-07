package varliklar;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.image.BufferedImage;
import motor.GorselYukleyici;

// Dusman sinifi, oyundaki tum dusmanlarin temelini olusturur
public class Dusman {
    // Dusmanin X koordinati (harita uzerindeki yatay konum)
    public double x;
    // Dusmanin Y koordinati (harita uzerindeki dikey konum)
    public double y;
    
    // Dusmanin mevcut can degeri
    public double can;
    // Dusmanin hareket hizi
    public double hiz;
    // Dusmanin oyuncuya verecegi hasar
    public double hasar;
    
    // Dusmanin carpisma yariçap degeri (daire tabanli carpisma tespiti icin)
    public double yariCap;
    
    // Dusmanin geri itilme direnci carpani (1.0 tam itilme, 0.0 hic itilmeme)
    public double geriItmeCarpani;
    
    // Dusman karakterinin gorsel resmi (piksel art)
    protected BufferedImage dusmanGorseli;    
    // Animasyon parametreleri (Emre)
    protected BufferedImage dusmanSheet;
    // Animasyonun o anki oynatilan kare indeksini tutar
    protected int animasyonKaresi = 0;
    // Karenin gecis hizini ayarlayan sayac
    protected int kareSayaci = 0;
    // Animasyonun toplam kare sayisi (varsayilan olarak slime walk icin 8 kare)
    protected int maksAnimasyonKaresi = 8;
    // Dusmanın baktıgı yon (true: sag, false: sol - Slime varsayılan olarak sola bakar)
    protected boolean sagaBakiyor = false;
    
    // Kurucu metot: Dusmani baslangic degerleriyle olusturur
    public Dusman(double x, double y, double can, double hiz, double hasar, double yariCap) {
        this.x = x;
        this.y = y;
        this.can = can;
        this.hiz = hiz;
        this.hasar = hasar;
        this.yariCap = yariCap;
        // Varsayilan geri itilme carpani 1.0 (tam itilme)
        this.geriItmeCarpani = 1.0;
        
        // Dusman gorselini spritesheet'ten yuklemeyi dener ve ilk kareyi kirpar
        // Daha dinamik bir gorunum elde etmek icin Idle-Sheet yerine Walk-Sheet yukluyoruz (Emre)
        BufferedImage sheet = GorselYukleyici.gorselYukle("assets/FreeCharactersAnimationsAssetPack 23.13.22/SpriteSheets(96x96)/Monster_Slime/With_Shadows/Monster_Slime_Walk-Sheet.png");
        if (sheet != null) {
            this.dusmanSheet = sheet;
            this.maksAnimasyonKaresi = 8;
            try {
                // Slime karakterinin gorseldeki merkezini (37, 42) koordinatlarindan 20x20 boyutunda keseriz
                // Boylece seffaf bosluklar atilir ve dusmanlar ekranda net bir sekilde gorunur hale gelir (Emre)
                this.dusmanGorseli = sheet.getSubimage(37, 42, 20, 20);
            } catch (Exception e) {
                System.out.println("UYARI: Dusman gorseli kesilirken hata olustu. Yedek sekil cizilecek.");
                this.dusmanGorseli = null;
            }
        }
    }
    
    // Dusmanin durumunu her karede (tick) gunceller ve oyuncuya dogru hareket etmesini saglar
    public void guncelle(Oyuncu oyuncu) {
        // Dusmandan oyuncuya dogru olan yatay ve dikey mesafeyi hesaplar
        double dx = oyuncu.x - this.x;
        double dy = oyuncu.y - this.y;
        
        // Iki nokta arasindaki dogrusal mesafeyi hesaplar
        double mesafe = Math.sqrt(dx * dx + dy * dy);
        
        // Sifira bolme hatasini onlemek icin mesafe kontrol edilir
        if (mesafe > 0) {
            // Yon vektorunu normalize ederek (uzunlugunu 1 birim yaparak) hiziyla carpar ve konumu gunceller
            this.x += (dx / mesafe) * this.hiz;
            this.y += (dy / mesafe) * this.hiz;
        }
        
        // Baktigi yonu yatay hareketine gore gunceller (Slime varsayılan olarak sola bakar)
        if (dx > 0) {
            this.sagaBakiyor = true;
        } else if (dx < 0) {
            this.sagaBakiyor = false;
        }
        
        // Dusmanin 3000x3000px harita sinirlarinin disina cikmasini engeller
        // Sinir degerleri 0 ile 3000 arasinda tutulur (Matematiksel sinirlama)
        this.x = Math.max(0, Math.min(this.x, 3000));
        this.y = Math.max(0, Math.min(this.y, 3000));
        
        // Animasyon sayacini bir artirarak zaman akisini simule eder
        kareSayaci++;
        // Her 7 oyun karesinde bir animasyonun diger karesine gecisi tetikler
        if (kareSayaci >= 7) {
            // Animasyon gecis sayacini sifirlar
            kareSayaci = 0;
            // Bir sonraki kareye gecer ve maksimum kare limitine gore mod alir
            animasyonKaresi = (animasyonKaresi + 1) % maksAnimasyonKaresi;
        }
    }
    
    // Dusmanin bir mermiyle vuruldugunda geriye savrulmasini saglayan metot (Knockback)
    public void geriIt(double yonX, double yonY, double miktar) {
        // Gelen merminin hareket yonuyle ve geri itme carpaniyla dogru orantili olarak dusmani geriye iter
        this.x += yonX * miktar * this.geriItmeCarpani;
        this.y += yonY * miktar * this.geriItmeCarpani;
        
        // Geri itilme sonrasinda da harita sinirlari kontrol edilir
        this.x = Math.max(0, Math.min(this.x, 3000));
        this.y = Math.max(0, Math.min(this.y, 3000));
    }
    
    // Dusmani ekrana cizen metot
    public void ciz(Graphics2D g2) {
        // --- OPTIMIZASYON: EKRAN DISI AYIKLAMA (FRUSTUM CULLING) ---
        // Ekrana cizilecek alan disindaki dusmanlari cizmeyerek performans artisi sagliyoruz
        java.awt.Rectangle ekranSiniri = g2.getClipBounds();
        if (ekranSiniri != null) {
            if (x + yariCap < ekranSiniri.x || x - yariCap > ekranSiniri.x + ekranSiniri.width ||
                y + yariCap < ekranSiniri.y || y - yariCap > ekranSiniri.y + ekranSiniri.height) {
                return; // Gorunmeyen dusmani cizmeyi atla
            }
        }

        // Eger dusman sheet dosyasi varsa animasyonlu cizer (Emre)
        if (dusmanSheet != null) {
            try {
                // Dinamik olarak o anki zıplama animasyon karesini spritesheet'ten keseriz (96px genisliginde grid)
                int cellX = animasyonKaresi * 96 + 37;
                int cellY = 42;
                
                int cizimX = (int) (x - yariCap);
                int cizimY = (int) (y - yariCap);
                int w = (int) (yariCap * 2);
                int h = (int) (yariCap * 2);
                
                // Baktigi yone gore resmi yatay olarak cevirir (Andac/Emre)
                // getSubimage yerine 9 parametreli drawImage kullanarak cop bellek (GC) olusumunu engelliyoruz
                if (sagaBakiyor) {
                    g2.drawImage(dusmanSheet, cizimX + w, cizimY, cizimX, cizimY + h, cellX, cellY, cellX + 20, cellY + 20, null);
                } else {
                    g2.drawImage(dusmanSheet, cizimX, cizimY, cizimX + w, cizimY + h, cellX, cellY, cellX + 20, cellY + 20, null);
                }
            } catch (Exception e) {
                yedekGorselCiz(g2);
            }
        } else if (dusmanGorseli != null) {
            // Statik gorsel cizimi
            int cizimX = (int) (x - yariCap);
            int cizimY = (int) (y - yariCap);
            int w = (int) (yariCap * 2);
            int h = (int) (yariCap * 2);
            if (sagaBakiyor) {
                g2.drawImage(dusmanGorseli, cizimX + w, cizimY, -w, h, null);
            } else {
                g2.drawImage(dusmanGorseli, cizimX, cizimY, w, h, null);
            }
        } else {
            // Resim bulunamazsa veya yuklenemezse yesil renkli yedek bir daire cizer
            yedekGeometrikCiz(g2);
        }
    }

    // Harici yedek metotlar (Emre)
    protected void yedekGorselCiz(Graphics2D g2) {
        if (dusmanGorseli != null) {
            int cizimX = (int) (x - yariCap);
            int cizimY = (int) (y - yariCap);
            int w = (int) (yariCap * 2);
            int h = (int) (yariCap * 2);
            if (sagaBakiyor) {
                g2.drawImage(dusmanGorseli, cizimX + w, cizimY, -w, h, null);
            } else {
                g2.drawImage(dusmanGorseli, cizimX, cizimY, w, h, null);
            }
        } else {
            yedekGeometrikCiz(g2);
        }
    }

    protected void yedekGeometrikCiz(Graphics2D g2) {
        g2.setColor(Color.GREEN);
        int cizimX = (int) (x - yariCap);
        int cizimY = (int) (y - yariCap);
        g2.fillOval(cizimX, cizimY, (int) (yariCap * 2), (int) (yariCap * 2));
        g2.setColor(Color.RED);
        g2.fillOval((int) (x - 6), (int) (y - 4), 4, 4);
        g2.fillOval((int) (x + 2), (int) (y - 4), 4, 4);
    }
}
