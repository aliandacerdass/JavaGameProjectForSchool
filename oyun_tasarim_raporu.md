# 🎮 Java 2D Roguelite Oyun Tasarım ve Uygulama Raporu - slimeSlayer

Bu rapor; okul projesi kapsamında tamamen saf (pure) Java Swing ve AWT kütüphaneleri kullanılarak geliştirilen 2D üstten bakışlı (top-down) hayatta kalma oyunu **slimeSlayer**'ın temel özelliklerini, oynanışını, dosya/kod bloğu etkilerini, kullanılan Java yapılarını ve projede yararlanılan görsel kaynakları detaylandırmaktadır.

---

## 1. Oyunun Temel Özellikleri ve Oynanış

### Genel Bakış
Oyun, popüler "Vampire Survivors" mekaniklerini temel alan bir **Survival Roguelite** oyunudur. Oyuncu, karanlık bir bataklık haritasında tek bir karakteri kontrol eder. Zaman ilerledikçe haritanın dışından sürekli artan sayıda ve güçte zombi ve golem dalgaları doğar. Oyuncu, otomatik saldıran yetenekleriyle hayatta kalmalı, düşmanlardan düşen kristalleri toplayarak seviye atlamalı ve en yüksek süre boyunca hayatta kalmaya çalışmalıdır.

### Temel Oynanış Kuralları
* **Giriş ve Ayarlar Ekranı:** Oyuncu oyunu ilk açtığında neon tasarımlı, retro bataklık temalı bir giriş ekranıyla karşılaşır. Bu ekrandan ses seviyesi (+ / - butonları ve yüzde barı) ile oyun zorluğu (Kolay, Normal, Zor) ayarlanabilir.
* **Menüye Dönüş:** Oyun bittiğinde oyuncu dilerse `R` tuşu ile doğrudan aynı ayarlarla yeniden başlayabilir, dilerse `M` tuşu ile ana menüye dönüp ayarlarını değiştirebilir.
* **Hareket:** Oyuncu `WASD` tuşları ile 8 yönlü olarak hareket eder.
* **Otomatik Saldırı:** Oyuncunun yetenekleri (Ateş Topu, Döner Bıçak, Kalkan) belirli zaman aralıklarında otomatik olarak tetiklenir.
* **Deneyim ve Gelişim:** Düşmanlar öldüğünde tecrübe kristali bırakırlar. Düşmanların türüne göre kazanılan tecrübe puanı değişir: Sıradan Slime 15 DP, Hızlı Slime 25 DP ve büyük Golem Boss 60 DP kazandırır. Tecrübe barı dolduğunda oyun duraklar ve 3 rastgele geliştirme seçeneği sunulur.
* **Hız Meyvesi:** Haritada her 30 saniyede bir nadir olarak kırmızı bir **Güç Meyvesi** doğar. Toplandığında oyuncunun etrafında kırmızı bir aura belirir ve 5 saniye boyunca geçici hız artışı kazanır.
* **Uçuşan Hasar Sayıları:** Düşmanlar hasar aldıklarında, darbenin türüne göre renklendirilmiş (Ateş Topu için turuncu, Döner Bıçak için turkuaz, Kalkan için mavi) hasar sayıları kafalarının üzerinde belirir, yukarı doğru süzülür ve saydamlaşarak kaybolur.
* **8-bit Retro Sesler:** Oyundaki tüm önemli olaylar (Ateş topu fırlatma, hasar alma, meyve toplama, seviye atlama ve oyun bitişi) harici ses dosyalarına ihtiyaç duymadan Java'nın standart ses kütüphanesiyle anlık olarak sentezlenen retro arcade ses dalgalarıyla desteklenir.
* **Knockback & İtme (Fizik):** Çarpışmalarda geri sekme uygulanır. Golem bossu kütlesinden dolayı çok düşük geri itilme çarpanına sahiptir.

---

## 2. Kod Mimarisi ve Kod Bloklarının İşlevleri

Projede kullanılan sınıflar ve bunların oyundaki doğrudan etkileri aşağıdaki tabloda açıklanmıştır:

| Sınıf / Dosya Yolu | Paket (Package) | İşlevi ve Oyuna Doğrudan Etkisi |
| :--- | :--- | :--- |
| [AnaGiris.java](file:///Users/aliandacerdass/SlimeSlayer/src/AnaGiris.java) | `default` | Oyunun başlangıç noktasıdır. `main` metoduyla ekran penceresini ve oyun döngüsünü kurup başlatır. |
| [Pencere.java](file:///Users/aliandacerdass/SlimeSlayer/src/motor/Pencere.java) | `motor` | Ekran boyutunu (800x600), pencerenin başlığını ve Swing `JFrame` kapatma eylemlerini yönetir. |
| [OyunPaneli.java](file:///Users/aliandacerdass/SlimeSlayer/src/motor/OyunPaneli.java) | `motor` | Oyunun kalbidir. Varlık listelerini (düşmanlar, mermiler, kristaller, meyveler, hasar sayıları) barındırır. Çizimleri ve kamerayı yönetir. |
| [OyunDongusu.java](file:///Users/aliandacerdass/SlimeSlayer/src/motor/OyunDongusu.java) | `motor` | Oyunu saniyede **60 FPS** hızında çalışmaya kilitleyen kararlı `Thread` yapısıdır. |
| [GorselYukleyici.java](file:///Users/aliandacerdass/SlimeSlayer/src/motor/GorselYukleyici.java) | `motor` | Spritesheet ve görselleri diskten okur. Düşman renklerini değiştiren filtreleme işlemlerini üstlenir. |
| [SesSentezleyici.java](file:///Users/aliandacerdass/SlimeSlayer/src/motor/SesSentezleyici.java) | `motor` | Java Sound API (sampled) aracılığıyla harici dosyalara ihtiyaç duymadan 8-bit retro arcade ses dalgalarını dinamik sentezler ve yürütür. |
| [MenuArayuzu.java](file:///Users/aliandacerdass/SlimeSlayer/src/motor/MenuArayuzu.java) | `motor` | Oyun ilk açıldığında gösterilen ana giriş ekranı arayüzünü, ses (+/-) ve zorluk (kolay/normal/zor) ayarlarını yönetir. |
| [TusKontrolcu.java](file:///Users/aliandacerdass/SlimeSlayer/src/motor/TusKontrolcu.java) | `motor` | Klavyeden basılan `WASD`, yeniden başlatma tuşu `R` ve menü tuşu `M` girdilerini yakalar. |
| [FareKontrolcu.java](file:///Users/aliandacerdass/SlimeSlayer/src/motor/FareKontrolcu.java) | `motor` | Seviye atlama ekranındaki kartların ve ana menü butonlarının üzerine gelindiğini (hover) ve tıklandığını takip eder. |
| [Oyuncu.java](file:///Users/aliandacerdass/SlimeSlayer/src/varliklar/Oyuncu.java) | `varliklar` | Oyuncu karakterinin canını, hızını, animasyon karelerini, yönünü ve Güç Meyvesi'nden aldığı hız boostu aurasını yönetir. |
| [Dusman.java](file:///Users/aliandacerdass/SlimeSlayer/src/varliklar/Dusman.java) | `varliklar` | Standart Slime düşmanının can, hız, hasar, hareket yapay zekası ve zıplama animasyonu çizim süreçlerini yönetir. |
| [GolemDusman.java](file:///Users/aliandacerdass/SlimeSlayer/src/varliklar/GolemDusman.java) | `varliklar` | Ağır, yavaş hareket eden ancak yüksek cana ve geri itilme direncine sahip gri renkli boss/golem tipidir. |
| [HizliDusman.java](file:///Users/aliandacerdass/SlimeSlayer/src/varliklar/HizliDusman.java) | `varliklar` | Çok hızlı koşan ama canı az olan kırmızı renkli düşman tipidir. |
| [DusmanUretici.java](file:///Users/aliandacerdass/SlimeSlayer/src/varliklar/DusmanUretici.java) | `varliklar` | Zamanla zorlaşan düşman üretim algoritmasını barındırır. Performansı korumak için culling yapar. |
| [CarpismaDenetleyici.java](file:///Users/aliandacerdass/SlimeSlayer/src/varliklar/CarpismaDenetleyici.java) | `varliklar` | Karakter-Düşman çarpışmasını (sekmeyi) ve Mermi-Düşman hasar/itme fiziğini hesaplar. |
| [Silah.java](file:///Users/aliandacerdass/SlimeSlayer/src/mekanikler/Silah.java) | `mekanikler` | Silahların seviyesini, bekleme süresini (cooldown) ve temel hasar yükseltme şablonunu belirleyen soyut sınıfirt. |
| [AtesTopu.java](file:///Users/aliandacerdass/SlimeSlayer/src/mekanikler/AtesTopu.java) | `mekanikler` | En yakın düşmanın konumunu bulup ona doğru otomatik ateş topları atan yetenektir. |
| [Mermi.java](file:///Users/aliandacerdass/SlimeSlayer/src/mekanikler/Mermi.java) | `mekanikler` | Ateş topu mermisinin hızını günceller. Çizim metoduyla alev topuna parıltılı çok katmanlı çekirdek ve kırmızı alev kuyruğu efekti kazandırır. |
| [DonerBicak.java](file:///Users/aliandacerdass/SlimeSlayer/src/mekanikler/DonerBicak.java) | `mekanikler` | Karakterin etrafında dönen bıçaklar oluşturan ve seviye atladıkça sayılarını birebir artıran yetenektir. |
| [DonerBicakMermi.java](file:///Users/aliandacerdass/SlimeSlayer/src/mekanikler/DonerBicakMermi.java) | `mekanikler` | Oyuncu etrafında trigonometrik olarak dönen shuriken mermisidir. Düşmanları delip geçer ve her düşmana 500 ms'de bir hasar verebilir. |
| [KalkanSilahi.java](file:///Users/aliandacerdass/SlimeSlayer/src/mekanikler/KalkanSilahi.java) | `mekanikler` | Oyuncunun etrafında dairesel bir koruma kalkanı ve dönen 3 koruyucu küre çizer. İçeri giren düşmanları hafifçe iter ve düzenli hasar verir. |
| [DeneyimKristali.java](file:///Users/aliandacerdass/SlimeSlayer/src/mekanikler/DeneyimKristali.java) | `mekanikler` | Yerdeki yeşil elmas şeklindeki tecrübe puanıdır. Oyuncu yaklaştığında mıknatıs gibi ivmelenerek oyuncuya çekilir. |
| [HasarSayisi.java](file:///Users/aliandacerdass/SlimeSlayer/src/mekanikler/HasarSayisi.java) | `mekanikler` | Düşmanlar darbe aldığında ortaya çıkan, yukarı kayıp saydamlaşarak kaybolan hasar sayılarını yönetir. |
| [GucMeyvesi.java](file:///Users/aliandacerdass/SlimeSlayer/src/mekanikler/GucMeyvesi.java) | `mekanikler` | Toplandığında oyuncuya geçici hız kazandıran, yapraklı kırmızı elma görseline sahip nadir collectible nesnesidir. |
| [SeviyeArayuzu.java](file:///Users/aliandacerdass/SlimeSlayer/src/mekanikler/SeviyeArayuzu.java) | `mekanikler` | Seviye atlandığında oyunu dondurarak 3 adet geliştirme kartını cam efekti (hover etkileşimli) ve özel ikonlarla ekrana çizer. |

---

## 3. Detaylı Oyun Mekanikleri ve Kod Tasarımı

### A. Karakter Hareketi ve Çapraz Hız Normalizasyonu
Oyuncu `WASD` veya Yön tuşlarıyla 8 yönlü hareket edebilir. Klasik 2D oyunlardaki en büyük sorunlardan biri, hem yatay hem dikey tuşa aynı anda basıldığında (çapraz hareket) karakterin normalden $\sqrt{2} \approx 1.414$ kat daha hızlı gitmesidir. Bu durum Pisagor Teoreminden kaynaklanır.

Projede bu durum [Oyuncu.java](file:///Users/aliandacerdass/SlimeSlayer/src/varliklar/Oyuncu.java) sınıfında hareket vektörünün normalizasyonu ile çözülmüştür:

```java
// Çapraz harekette hızın artmasını engellemek için normalizasyon (sin(45) = 0.707)
if (hareketX != 0 && hareketY != 0) {
    hareketX *= 0.707;
    hareketY *= 0.707;
}
double toplamHiz = hiz + ekstraHiz;
x += hareketX * toplamHiz;
y += hareketY * toplamHiz;
```

### B. Otomatik Düşman Dalgaları ve Zorluk Ölçeklemesi
[DusmanUretici.java](file:///Users/aliandacerdass/SlimeSlayer/src/varliklar/DusmanUretici.java) sınıfı, oyuncu etrafındaki ekran sınırlarının hemen dışındaki 4 kenardan (+60px ofset) rastgele düşman spawn eder. Zaman geçtikçe zorluk katlanarak artar:
* **İlk 30 saniye:** Yalnızca sıradan yeşil slime zombiler.
* **30-60 saniye arası:** Hızlı kırmızı slime'lar (%30 ihtimalle) eklenir.
* **60 saniyeden sonra:** Yüksek cana ve geri itilme direncine sahip ağır Golem Boss'lar (%10 ihtimalle) dahil olur.

Zorluk derecesi, ana menüden seçilen `zorlukModu` çarpanı ile çarpılarak düşmanların can ve hasarlarına etki eder:
$$\text{Zorluk Çarpanı} = \left( 1.0 + \frac{\text{Geçen Süre (ms)}}{120000.0} \right) \times \text{zorlukModu}$$

```java
// Standart Düşman Oluşturma
double can = 40.0 * zorlukCarpani;
double hiz = 1.6 * Math.min(2.0, 1.0 + (zorlukCarpani - 1.0) * 0.3);
double hasar = 12.0 * zorlukCarpani;
return new Dusman(x, y, can, hiz, hasar, 16.0);
```

### C. Mıknatıs Çekimli Tecrübe Kristalleri
Düşmanlar elendiğinde yere tecrübe kristalleri düşer. [DeneyimKristali.java](file:///Users/aliandacerdass/SlimeSlayer/src/mekanikler/DeneyimKristali.java) sınıfı, oyuncu 120 piksel toplama menziline girdiğinde mıknatıs çekim algoritmasını tetikler. Mesafe azaldıkça çekim hızı ivmelenir:

```java
double dx = oyuncu.x - this.x;
double dy = oyuncu.y - this.y;
double uzaklik = Math.sqrt(dx * dx + dy * dy);

if (uzaklik <= this.toplamaMenzili) {
    double yonX = dx / uzaklik;
    double yonY = dy / uzaklik;
    // Çekim hızını her karede ivmelendirir (Maks 12.0px/frame)
    this.cekmeHizi = Math.min(12.0, this.cekmeHizi + 0.25);
    this.x += yonX * this.cekmeHizi;
    this.y += yonY * this.cekmeHizi;
}
```

---

## 4. Grafik, Görsel Efektler ve Çizim Yönetimi

### A. Görsellerin Diskten Okunması ve Programatik Renklendirme
Görseller diskten `ImageIO.read` yöntemi ile okunur. Oyunda spritesheet üzerindeki aynı slime animasyon şablonunun rengi programatik olarak değiştirilerek Hızlı Slime (kırmızı) ve Golem Slime (gri kaya) oluşturulmuştur. Bu işlem [GorselYukleyici.java](file:///Users/aliandacerdass/SlimeSlayer/src/motor/GorselYukleyici.java) içinde şu şekilde yapılır:

```java
public static BufferedImage gorseliGriyap(BufferedImage kaynak) {
    BufferedImage sonuc = new BufferedImage(kaynak.getWidth(), kaynak.getHeight(), BufferedImage.TYPE_INT_ARGB);
    for (int x = 0; x < kaynak.getWidth(); x++) {
        for (int y = 0; y < kaynak.getHeight(); y++) {
            int rgb = kaynak.getRGB(x, y);
            int a = (rgb >> 24) & 0xff;
            int r = (rgb >> 16) & 0xff;
            int g = (rgb >> 8) & 0xff;
            int b = rgb & 0xff;
            // Gri tonlama parlaklık formülü
            int gri = (int) (r * 0.299 + g * 0.587 + b * 0.114);
            int yeniRgb = (a << 24) | (gri << 16) | (gri << 8) | gri;
            sonuc.setRGB(x, y, yeniRgb);
        }
    }
    return sonuc;
}
```

### B. Zemin Tiling Kaplama ve Frustum Culling Performans Optimizasyonu
Büyük haritalarda (3000x3000px) tüm zemini çizmek ağır performans kaybına yol açar. Projede sadece kameranın gördüğü alandaki karolar taranıp çizilir. Ayrıca ekran sınırları dışındaki düşmanlar `getClipBounds` ile elenir (**Frustum Culling**):

```java
// Frustum Culling Örneği (Dusman.java)
Rectangle ekranSiniri = g2.getClipBounds();
if (ekranSiniri != null) {
    if (x + yariCap < ekranSiniri.x || x - yariCap > ekranSiniri.x + ekranSiniri.width ||
        y + yariCap < ekranSiniri.y || y - yariCap > ekranSiniri.y + ekranSiniri.height) {
        return; // Ekranın dışındaysa çizimi atla
    }
}
```

### C. Kamera Sarsıntısı (Screen Shake) ve Hasar Ekranı Flaş Efekti
Oyuncu hasar aldığında vuruş hissini (game feel) artırmak adına kamera rastgele sarsılır ve ekrana kırmızı saydam bir perde çekilir. Sarsıntı gücü ve flaşın opasitesi zamanla azalır:

```java
// OyunPaneli.java paintComponent metodu
double nihaiKameraX = kameraX;
double nihaiKameraY = kameraY;

if (sarsintiSuresi > 0) {
    Random rand = new Random();
    nihaiKameraX += (rand.nextDouble() - 0.5) * 2.0 * sarsintiGucu;
    nihaiKameraY += (rand.nextDouble() - 0.5) * 2.0 * sarsintiGucu;
}
g2.translate(-nihaiKameraX, -nihaiKameraY);
// ... Çizimler ...
g2.translate(nihaiKameraX, nihaiKameraY); // HUD öncesi geri al

// Hasar Flaş perdesi
if (hasarFlasiSuresi > 0) {
    int alfa = (int) (110.0 * ((double) hasarFlasiSuresi / 8.0));
    g2.setColor(new Color(255, 0, 0, alfa));
    g2.fillRect(0, 0, EKRAN_GENISLIGI, EKRAN_YUKSEKLIGI);
}
```

---

## 5. Retro Ses Efekti Sentezleyici Motoru

Ses sentezleyici motoru, diskteki `.wav` dosyalarının kaybolması veya işletim sisteminin ses sürücülerinin hata vermesi risklerini bertaraf etmek amacıyla tamamen **Java Sound API** (`javax.sound.sampled`) kullanılarak sıfırdan yazılmıştır. Sentezlenen 16-bit PCM dalgalar ayrı bir thread (iş parçacığı) üzerinden asenkron olarak çalınarak FPS düşüşleri engellenir.

### A. Matematiksel Ses Dalgası Sentezleme
Formül olarak basit Sinüs Dalgaları ($y = A \sin(2\pi f t)$) ve zamanla genliğin sıfıra yaklaşmasını sağlayan sönümleme (Fade-out) kullanılmıştır.

```java
// 16-bit PCM Mono Ses Sentezleme ve Çalma İşlemi (SesSentezleyici.java)
private static void sesCal(float[] data) {
    new Thread(() -> {
        try {
            byte[] byteData = new byte[data.length * 2];
            for (int i = 0; i < data.length; i++) {
                short val = (short) (data[i] * 32767); // Genliği 16-bit short'a dönüştür
                byteData[i * 2] = (byte) (val & 0xff);       // Little-endian alt byte
                byteData[i * 2 + 1] = (byte) ((val >> 8) & 0xff); // High byte
            }
            AudioFormat format = new AudioFormat(44100, 16, 1, true, false);
            SourceDataLine line = AudioSystem.getSourceDataLine(format);
            line.open(format);
            line.start();
            line.write(byteData, 0, byteData.length);
            line.drain();
            line.close();
        } catch (Exception e) {
            System.out.println("Ses calinirken hata: " + e.getMessage());
        }
    }).start();
}
```

### B. Frekans Modülasyonu Sinyalleri
* **Ateş Topu Sesi:** Frekansı 750 Hz'den 250 Hz'e doğru doğrusal olarak düşürerek fırlatılma (Pew!) efekti oluşturur.
* **Hasar Sesi:** 60 Hz ile 140 Hz arasında rastgele beyaz gürültü (white noise) karıştırılmış düşük frekanslı patlama sesi oluşturur.
* **Meyve Sesi:** Frekansı 350 Hz'den 950 Hz'e doğru hızla fırlatarak (Blip!) alma efekti oluşturur.
* **Seviye Atlama Sesi:** C-Major Arpej tonlarını (Do, Mi, Sol, Do) sırayla tınlatarak retro bir melodi çalar.

---

## 6. Önemli Kod Blokları ve Teknik Analiz

### A. Vektörel Çarpışma Çözücü ve Geri Sekme (Knockback)
Daire tabanlı çarpışmalarda iki nesnenin iç içe girmesini önlemek amacıyla çarpışan alanların yarıçap toplamı kadar nesneler zıt yönde itilir. Golem Boss'unun direnç katsayısı (`geriItmeCarpani = 0.15`) ağır kütleyi başarıyla simüle eder:

```java
// CarpismaDenetleyici.java
double dx = oyuncu.x - dusman.x;
double dy = oyuncu.y - dusman.y;
double mesafeKaresi = dx * dx + dy * dy;
double yariCapToplam = oyuncu.yariCap + dusman.yariCap;

if (mesafeKaresi < yariCapToplam * yariCapToplam) {
    double mesafe = Math.sqrt(mesafeKaresi);
    if (mesafe > 0) {
        double overlap = yariCapToplam - mesafe;
        double itmeX = (dx / mesafe) * overlap;
        double itmeY = (dy / mesafe) * overlap;
        
        // Oyuncuyu %60 oranında geriye iter
        oyuncu.x += itmeX * 0.6;
        oyuncu.y += itmeY * 0.6;
        
        // Düşmanı kendi kütle direncine göre geriye iter
        dusman.x -= itmeX * 0.4 * dusman.geriItmeCarpani;
        dusman.y -= itmeY * 0.4 * dusman.geriItmeCarpani;
    }
}
```

### B. Döner Bıçakların Trigonometrik Yörüngesi ve Düşman Hasar Cooldown Sistemi
Döner bıçak mermileri oyuncu etrafında trigonometrik yörüngede döner. Düşmanların bıçakla temas ettiğinde saniyede 60 kez hasar alıp hemen yok olmasını engellemek için `HashMap` tabanlı vuruş bekleme süresi entegre edilmiştir:

```java
// DonerBicakMermi.java güncelleme mantığı
this.aci += this.donerHizi;
this.x = oyuncu.x + Math.cos(this.aci) * this.yorungeYaricapi;
this.y = oyuncu.y + Math.sin(this.aci) * this.yorungeYaricapi;

// CarpismaDenetleyici içindeki temas kontrolü
if (mermi instanceof DonerBicakMermi) {
    DonerBicakMermi dbMermi = (DonerBicakMermi) mermi;
    long suAn = System.currentTimeMillis();
    
    // Düşmanın hasar bekleme süresi (500ms) dolduysa hasar ver
    if (dbMermi.dusmanaVurabilirMi(dusman, suAn)) {
        dusman.can -= mermi.hasar;
        dbMermi.vurulanDusmaniEkle(dusman, suAn); // HashMap'e kaydet
        
        // Bıçak temasında düşmanı merkezden dışarı doğru fırlat
        double mesafe = Math.sqrt(mesafeKaresi);
        if (mesafe > 0) {
            dusman.geriIt(dx / mesafe, dy / mesafe, 15.0);
        }
    }
}
```

---

## 7. Kullanılan Java Yapıları ve Tercih Nedenleri

### A. Nesne Yönelimli Programlama (OOP)
* **Kalıtım (Inheritance):** `GolemDusman` ve `HizliDusman` sınıfları `Dusman` sınıfından türetilerek tüm hareket ve çizim kodlarını miras almıştır. Kod tekrarı engellenmiştir.
* **Soyut Sınıflar (Abstract Classes):** `Silah` sınıfı soyut yapılarak silahlar için kurumsal standartlar konulmuş, `saldir()` metodu her silahta özelleştirilmiştir.
* **Çok Biçimlilik (Polymorphism):** `Silah` türündeki listeler içinde barındırılan `AtesTopu`, `DonerBicak` ve `KalkanSilahi` nesneleri döngülerde aynı imza ile çağrılmış fakat kendi metotlarını tetiklemiştir.

### B. Koleksiyon Yapısı (Collections Framework)
* **ArrayList:** Haritadaki dinamik varlık listelerini (mermiler, kristaller, düşmanlar) yönetmek için hızlı indis erişimine sahip `ArrayList` yapısı seçilmiştir.
* **HashMap:** Donen bıçak hasar cooldown takibinde düşman referanslarını anahtar (`key`), son vuruş zaman damgalarını değer (`value`) olarak saklayan `HashMap<Dusman, Long>` yapısı kullanılarak $O(1)$ karmaşıklığında hızlı sorgulama yapılmıştır.

### C. Multi-threading (Asenkron İş Parçacıkları)
* Java AWT Event Dispatch Thread (EDT) üzerinde çalışan çizim işlemlerinin donmaması ve ses çalınırken oyunun donmasını (FPS drop) engellemek amacıyla tüm ham ses dalgası çalma eylemleri ayrı birer `Thread` üzerinden yürütülmüştür.

---

## 8. Kullanılan Kaynaklar ve Kaynakça

Oyunda kullanılan tüm görsel pikseller ve ses tasarımları için proje şablonundaki lisanslı retro varlık paketlerinden yararlanılmıştır:

1. **Kahraman Karakter Spritesheet'i:**
   * **Kaynak:** `assets/Heroes99_free 23.13.22/character_spritesheet.png`
   * **İçerik:** Oyuncu karakterinin yürüme ve bekleme (idle) animasyon kareleri.
2. **Düşman Slime Animasyonları:**
   * **Kaynak:** `assets/FreeCharactersAnimationsAssetPack 23.13.22/SpriteSheets(96x96)/Monster_Slime/With_Shadows/`
   * **İçerik:** Düşman slime zıplama/yürüme animasyon sayfaları.
3. **Bataklık Zemin Kaplama Tileset'i:**
   * **Kaynak:** `assets/Dark_Swamp_Starter_Pack_v1.0 23.13.22/RawAssets/GroundTileset.png`
   * **İçerik:** Bataklık haritasının taban karoları (tiling grid).
4. **Kullanıcı Arayüzü (UI):**
   * **Kaynak:** `assets/Pixel UI pack 3 23.13.22/`
   * **İçerik:** HUD çerçeveleri, XP bar tasarımı ve yazı tipleri için ilham alınan piksel şablonları.
5. **Referans Dokümantasyonlar:**
   * Oracle Java Sound API - Java Sampled Sound References.
   * "Vampire Survivors" Design Architecture & Math Algorithms Guides.
