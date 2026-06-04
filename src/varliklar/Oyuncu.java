package varliklar;

import motor.TusKontrolcu;
import motor.GorselYukleyici;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.image.BufferedImage;

// Oyuncu sinifi, kullanicinin kontrol ettigi ana karakteri temsil eder
public class Oyuncu {
    // Oyuncunun X koordinati (harita uzerindeki yatay konum)
    public double x;
    // Oyuncunun Y koordinati (harita uzerindeki dikey konum)
    public double y;
    
    // Oyuncunun mevcut can degeri
    public double can;
    // Oyuncunun maksimum can kapasitesi
    public double maksCan;
    
    // Oyuncunun hareket hizi
    public double hiz;
    
    // Oyuncunun carpisma yariçap degeri (daire tabanli carpisma tespiti icin)
    public double yariCap;
    
    // Oyuncunun mevcut seviyesi (level)
    public int seviye;
    // Oyuncunun topladigi mevcut tecrube/deneyim puani
    public double deneyim;
    // Bir sonraki seviyeye gecmek icin gereken toplam deneyim baraji
    public double sonrakiSeviyeDeneyimi;
    
    // Oyuncu karakterinin gorsel resmi (piksel art)
    private BufferedImage oyuncuGorseli;
    
    // Oyuncunun animasyon parametreleri (Emre)
    private BufferedImage oyuncuSheet;
    private int animasyonKaresi = 0;
    private int kareSayaci = 0;
    private int aktifSatir = 0; // 0: Idle, 1: Sol/Asagi, 2: Sag, 6: Yukari
    private boolean sagaBakiyor = true; // Oyuncunun baktigi yon (true: sag, false: sol)
    
    // Kurucu metot: Karakteri baslangic degerleriyle olusturur
    public Oyuncu(double x, double y) {
        // Baslangic konumlarini atar
        this.x = x;
        this.y = y;
        
        // Can statlarini tanimlar
        this.maksCan = 100;
        this.can = 100;
        
        // Temel hareket hizini belirler
        this.hiz = 4.0;
        
        // Daire tabanli carpisma yariçapini belirler (32x32 piksel boyutlari icin ideal yariçap)
        this.yariCap = 16.0;
        
        // Seviye ve tecrube barajlarini baslatir
        this.seviye = 1;
        this.deneyim = 0;
        this.sonrakiSeviyeDeneyimi = 100;
        
        // Oyuncu gorselini spritesheet'ten yuklemeyi dener ve ilk kareyi kirpar
        BufferedImage sheet = GorselYukleyici.gorselYukle("assets/Heroes99_free 23.13.22/character_spritesheet.png");
        if (sheet != null) {
            this.oyuncuSheet = sheet;
            try {
                // Heroes99 spritesheet'i uzerinden ilk kahramani (28, 4) koordinatlarindan 24x30 boyutunda keseriz
                // Boylece karakter tam ortalanmis ve gereksiz seffaf alanlar atilmis olur (Emre)
                this.oyuncuGorseli = sheet.getSubimage(28, 4, 24, 30);
            } catch (Exception e) {
                System.out.println("UYARI: Oyuncu gorseli kesilirken hata olustu. Yedek sekil cizilecek.");
                this.oyuncuGorseli = null;
            }
        }
    }
    
    // Oyuncunun hareket ve diger durumlarini her karede (tick) gunceller
    public void guncelle(TusKontrolcu tusKontrol) {
        // Yatay ve dikey hareket yon degiskenleri
        double hareketX = 0;
        double hareketY = 0;
        
        // Klavye girdilerine gore hareket yonlerini belirler (Andac'in TusKontrolcu sinifindaki saga/sola/yukari/asagi kullanilir)
        if (tusKontrol.yukari) {
            hareketY -= 1;
        }
        if (tusKontrol.asagi) {
            hareketY += 1;
        }
        if (tusKontrol.sola) {
            hareketX -= 1;
        }
        if (tusKontrol.saga) {
            hareketX += 1;
        }
        
        // Eger herhangi bir hareket girdisi varsa
        if (hareketX != 0 || hareketY != 0) {
            // Çapraz hareket kontrolu (Eger hem X hem Y ekseninde hareket varsa)
            if (hareketX != 0 && hareketY != 0) {
                // Çapraz harekette hizin 1.41 katina cikmasini engellemek icin normalizasyon degeri (sin(45) = 0.707)
                hareketX *= 0.707;
                hareketY *= 0.707;
            }
            
            // Konumu hiza bagli olarak gunceller
            x += hareketX * hiz;
            y += hareketY * hiz;
            
            // Oyuncunun 3000x3000px harita sinirlarinin disina cikmasini engeller
            // Sinir degerleri 0 ile 3000 arasinda tutulur (Matematiksel sinirlama)
            x = Math.max(0, Math.min(x, 3000));
            y = Math.max(0, Math.min(y, 3000));
        }

        // Hareket yonune ve durumuna gore animasyon ve aktif satiri gunceller (Emre)
        boolean hareketVar = false;
        int yeniSatir = aktifSatir;
        if (tusKontrol.yukari) {
            yeniSatir = 6; // Yukari yuruyus satiri (Satir 6)
            hareketVar = true;
        } else if (tusKontrol.asagi) {
            yeniSatir = 1; // Asagi yuruyus satiri (Satir 1)
            hareketVar = true;
        } else if (tusKontrol.sola) {
            yeniSatir = 3; // Sola yuruyus satiri (Saga yuruyus satiri 3'u kullanip cizimde cevirecegiz)
            this.sagaBakiyor = false;
            hareketVar = true;
        } else if (tusKontrol.saga) {
            yeniSatir = 3; // Saga yuruyus satiri (Satir 3)
            this.sagaBakiyor = true;
            hareketVar = true;
        }
        
        if (hareketVar) {
            // Yon degistiyse veya durmaktan harekete gectiysek animasyon karelerini sifirlariz
            if (aktifSatir != yeniSatir || animasyonKaresi >= 8) {
                aktifSatir = yeniSatir;
                animasyonKaresi = 0;
                kareSayaci = 0;
            }
            // Hareket ediyorsa yuruyus animasyon karesini gunceller (8 karelik yorunge)
            kareSayaci++;
            if (kareSayaci >= 5) { // Her 5 karede bir kare degisir
                kareSayaci = 0;
                animasyonKaresi = (animasyonKaresi + 1) % 8;
            }
        } else {
            // Duruyorsa yonune gore uygun idle animasyonunu secip oynatir (Andaç/Emre)
            int hedefIdleSatiri = 0;
            int minKare = 0;
            int maksKare = 6;
            
            if (aktifSatir == 6 || (aktifSatir == 5 && animasyonKaresi >= 2)) {
                // En son yukari yurunuyorduysa -> Idle Yukari (Satir 5, kare 2 statik, saldiri bug'ini onler)
                hedefIdleSatiri = 5;
                minKare = 2;
                maksKare = 3; 
            } else if (aktifSatir == 3 || (aktifSatir == 5 && animasyonKaresi < 2)) {
                // En son sag/sol yurunuyorduysa -> Idle Sol/Sag (Satir 5, kare 0 statik, saldiri bug'ini onler)
                hedefIdleSatiri = 5;
                minKare = 0;
                maksKare = 1;
            } else {
                // En son asagi yurunuyorduysa veya ilk durum -> Idle Asagi (Satir 0, kare 0-5 hareketli)
                hedefIdleSatiri = 0;
                minKare = 0;
                maksKare = 6;
            }
            
            // Idle durumuna gecerken veya durum degisirken tasmayi onlemek icin kareleri sinirlariz
            if (aktifSatir != hedefIdleSatiri || animasyonKaresi < minKare || animasyonKaresi >= maksKare) {
                aktifSatir = hedefIdleSatiri;
                animasyonKaresi = minKare;
                kareSayaci = 0;
            }
            
            kareSayaci++;
            if (kareSayaci >= 8) { // Dururken animasyon daha yavas akar
                kareSayaci = 0;
                animasyonKaresi = minKare + ((animasyonKaresi - minKare + 1) % (maksKare - minKare));
            }
        }
    }
    
    // Oyuncuyu ekrana cizen metot
    public void ciz(Graphics2D g2) {
        // Eger oyuncu sheet dosyasi yuklendiyse animasyonlu cizer (Emre)
        if (oyuncuSheet != null) {
            try {
                // Dinamik olarak o anki animasyon karesini spritesheet'ten keseriz (100x40 grid)
                int cellX = animasyonKaresi * 100 + 28;
                int cellY = aktifSatir * 40 + 4;
                BufferedImage kareGorseli = oyuncuSheet.getSubimage(cellX, cellY, 24, 30);
                
                int cizimX = (int) (x - 16);
                int cizimY = (int) (y - 24);
                int w = 32;
                int h = 40;
                
                // Yansitma/Cevirme Mantigi:
                // - Yururken (Satir 3) varsayilan sagdir -> sola bakarken ceviririz (!sagaBakiyor)
                // - Beklerken (Satir 5, kare 0-1) varsayilan soldur -> saga bakarken ceviririz (sagaBakiyor)
                boolean cevir = (aktifSatir == 3 && !sagaBakiyor) || (aktifSatir == 5 && animasyonKaresi < 2 && sagaBakiyor);
                
                if (cevir) {
                    g2.drawImage(kareGorseli, cizimX + w, cizimY, -w, h, null);
                } else {
                    g2.drawImage(kareGorseli, cizimX, cizimY, w, h, null);
                }
            } catch (Exception e) {
                // Herhangi bir hata durumunda statik resim cizimine duser
                yedekGorselCiz(g2);
            }
        } else if (oyuncuGorseli != null) {
            // Statik gorsel cizimi
            int cizimX = (int) (x - 16);
            int cizimY = (int) (y - 24);
            int w = 32;
            int h = 40;
            boolean cevir = (aktifSatir == 3 && !sagaBakiyor) || (aktifSatir == 5 && animasyonKaresi < 2 && sagaBakiyor);
            if (cevir) {
                g2.drawImage(oyuncuGorseli, cizimX + w, cizimY, -w, h, null);
            } else {
                g2.drawImage(oyuncuGorseli, cizimX, cizimY, w, h, null);
            }
        } else {
            // Resim bulunamazsa veya yuklenemezse mavi renkli yedek bir daire cizer
            yedekGeometrikCiz(g2);
        }
    }

    // Harici yedek metotlar (Emre)
    private void yedekGorselCiz(Graphics2D g2) {
        if (oyuncuGorseli != null) {
            int cizimX = (int) (x - 16);
            int cizimY = (int) (y - 24);
            int w = 32;
            int h = 40;
            boolean cevir = (aktifSatir == 3 && !sagaBakiyor) || (aktifSatir == 5 && animasyonKaresi < 2 && sagaBakiyor);
            if (cevir) {
                g2.drawImage(oyuncuGorseli, cizimX + w, cizimY, -w, h, null);
            } else {
                g2.drawImage(oyuncuGorseli, cizimX, cizimY, w, h, null);
            }
        } else {
            yedekGeometrikCiz(g2);
        }
    }

    private void yedekGeometrikCiz(Graphics2D g2) {
        g2.setColor(Color.BLUE);
        int cizimX = (int) (x - yariCap);
        int cizimY = (int) (y - yariCap);
        g2.fillOval(cizimX, cizimY, (int) (yariCap * 2), (int) (yariCap * 2));
        g2.setColor(Color.BLACK);
        g2.fillOval((int) (x - 4), (int) (y - 4), 8, 8);
    }
}
