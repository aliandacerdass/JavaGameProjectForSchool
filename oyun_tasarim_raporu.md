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

## 3. Kullanılan Java Yapıları ve Tercih Nedenleri

Projenin geliştirilmesinde Java dilinin temel yapısal avantajlarından yararlanılmıştır:

### 1. Nesne Yönelimli Programlama (OOP) Yaklaşımı
* **Kalıtım (Inheritance):** Düşman türleri (`GolemDusman`, `HizliDusman`) ortak hareket ve animasyon kodlarını paylaşabilmek için `Dusman` sınıfından türetilmiştir. Aynı şekilde tüm mermi türleri `Mermi` sınıfından, tüm yetenekler de `Silah` sınıfından kalıtım almıştır. Bu sayede kod tekrarı engellenmiştir.
* **Çok Biçimlilik (Polymorphism):** `Silah` üst sınıfında tanımlanan soyut `saldir(..)` ve `ciz(..)` metotları, her alt sınıfta (Ateş topunun hedefe mermi atması, kalkanın oyuncu etrafında daire çizmesi) kendilerine özgü şekilde ezilmiştir (method overriding).
* **Soyut Sınıflar (Abstract Classes):** `Silah` sınıfı soyut yapılarak doğrudan türetilmesi (instantiation) engellenmiş, yeni eklenecek silahlar için kurumsal bir şablon oluşturulmuştur.

### 2. Grafik ve UI Yönetimi (Swing & AWT)
* Harici bir oyun motoru (LibGDX, Slick2D vb.) kullanılmadan, Java'nın kendi bünyesindeki hafif bileşen mimarisine sahip **Java Swing** (`JPanel`, `JFrame`) ve temel grafik kütüphanesi **AWT** (`Graphics2D`, `BufferedImage`, `Color`, `Font`, `Polygon`) kullanılmıştır.
* **Graphics2D Dönüşümleri (Transformations):** Dönen bıçaklar ve kalkan küreleri için `g2.translate()` ve `g2.rotate()` kullanılarak karmaşık trigonometrik açısal koordinat çevrimleri grafik bağlamına yaptırılmış, çizim performansı optimize edilmiştir.
* **Metin Alfa Blending (Saydamlaşma):** `HasarSayisi` sınıfında `new Color(r, g, b, alpha)` yapısı kullanılarak darbe sayılarının yukarı süzülürken yavaşça şeffaflaşması sağlanmıştır.
* **GC Dostu Çizim:** `getSubimage()` çağrılarının her karede (saniyede 60 kez) bellek üzerinde çöp nesne üretmesi engellenmiş, 9 parametreli `drawImage` koordinat sınırlandırmasıyla bellek verimliliği sağlanmıştır.

### 3. Java Sound API (javax.sound.sampled)
* Harici MP3 veya WAV dosyalarına bağımlı kalmamak ve platform bağımsız kararlılığı korumak için Java'nın ham ses sentezleme altyapısı kullanılmıştır. `SesSentezleyici` sınıfında oluşturulan float veri dizileri 16-bit PCM Mono dalgalara dönüştürülerek ayrı bir Thread üzerinde çalınır. Bu sayede oyun döngüsü bloke edilmeden 8-bit retro sesler elde edilir.

### 4. Java Koleksiyonları (Collections Framework)
* **ArrayList:** Haritada dinamik olarak sürekli artıp azalan düşmanlar, mermiler, kristaller, meyveler ve hasar sayıları gibi nesneleri tutmak için esnek `ArrayList` yapısı kullanılmıştır.
* **HashMap:** `DonerBicakMermi` sınıfında, her düşmanın bu bıçaktan en son ne zaman hasar aldığını milisaniye bazında tutmak için `HashMap<Dusman, Long>` yapısı kullanılmıştır. Bu sayede düşman başına vuruş bekleme süresi (hit cooldown) $O(1)$ karmaşıklığında hızlıca denetlenmiştir.

### 5. Matematiksel Hesaplamalar
* **Trigonometri:** Döner bıçağın oyuncunun etrafında dairesel yörüngede dönmesi için $\cos(\theta)$ ve $\sin(\theta)$ formüllerinden yararlanılmıştır:
  $$\text{x} = \text{oyuncu.x} + \cos(\text{aci}) \times \text{yorungeYaricapi}$$
  $$\text{y} = \text{oyuncu.y} + \sin(\text{aci}) \times \text{yorungeYaricapi}$$
* **Pisagor Teoremi:** Daire tabanlı çarpışma tespiti ve mıknatıs alan kontrolü için iki nokta arasındaki Öklid uzaklığı hesaplanmıştır:
  $$\text{mesafe} = \sqrt{(x_1 - x_2)^2 + (y_1 - y_2)^2}$$

---

## 4. Kullanılan Kaynaklar ve Kaynakça

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
