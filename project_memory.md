# Proje Belleği (Project Memory)

Bu dosya, projedeki tüm geliştiricilerin ve onların kullandığı yapay zeka yardımcılarının (AI coding assistants) aynı kurallara, mimariye ve görev dağılımına uymasını sağlamak amacıyla oluşturulmuştur. Lütfen bu dosyayı güncel tutun ve geliştirmelere başlamadan önce mutlaka okuyun.

---

## 1. Proje Tanımı ve Hedef
- **Oyun Türü:** Vampire Survivors benzeri, yukarıdan aşağıya (top-down) bakış açısına sahip bir hayatta kalma (Survival) oyunu.
- **Kullanılacak Dil ve Teknolojiler:** Yalnızca saf Java SE (Java Standard Edition) kullanılacaktır. Grafik arayüzü ve pencere yönetimi için **Java Swing** ve **AWT** kütüphaneleri tercih edilecektir.
- **Süre:** Maksimum 7 gün.

---

## 2. Temel Proje Kuralları

### Kural 1: Görev Sınırları ve Saygı
- Hiçbir geliştirici, diğer ekip arkadaşlarının görev alanındaki kod bloklarını izinsiz değiştirmeyecek veya bozmayacaktır. 
- Ortak alanlarda (örn. ana oyun paneli entegrasyonu) değişiklik yapılmadan önce mutlaka iletişim kurulacaktır.

### Kural 2: Kod Sadeligi ve Türkçe Yorum Satırları
- Proje üniversite 1. sınıf öğrencileri tarafından sunulacağından, anlatılması ve savunulması kolay kodlar yazılacaktır.
- Karmaşık üçüncü parti kütüphaneler (LibGDX vb.) kesinlikle kullanılmayacaktır.
- **Yazılan her satırın veya kod bloğunun işlevi, satır üzerinde veya yanında TÜRKÇE yorum satırlarıyla açıklanacaktır.** Yapay zeka araçları kod üretirken her satıra açıklama eklemelidir.

### Kural 3: Türkçe Değişken ve Sınıf İsimleri (İngilizce Karakterlerle)
- Değişken, metot, sınıf ve paket isimleri kesinlikle **Türkçe** olarak adlandırılacaktır.
- Ancak kodun derleme hatası almaması ve evrensel uyumluluk için Türkçe özel karakterler (**ç, ş, ğ, ı, ö, ü, Ç, Ş, Ğ, İ, Ö, Ü**) **KESİNLİKLE KULLANILMAYACAKTIR**.
- *Örnek Doğru Kullanım:* `oyuncuHizi`, `dusmanSayisi`, `carpismaVarMi`, `mermiHasari`, `Oyuncu`, `Dusman`, `OyunPaneli`.
- *Örnek Yanlış Kullanım:* `oyuncuHızı`, `düşmanSayısı`, `çarpışmaVarMı`, `mermiHasarı`.

### Kural 4: Projeyi Zorlaştıracak ve Eklenmeyecek Hususlar (Yapay Zekalar Kesinlikle Eklememeli)
Projenin 1. sınıf seviyesinde kalması ve 7 gün içinde sorunsuz bitmesi için yapay zekalar ve geliştiriciler şu özellikleri **asla kodlamayacaktır**:
- **Harici Fizik ve Çarpışma Motorları:** Box2D gibi kütüphaneler yerine sadece AWT'nin `Rectangle.intersects()` metodu veya iki nokta arasındaki uzaklık formülü kullanılmalıdır.
- **Ses/Müzik Entegrasyonu:** İşletim sistemi (Windows/Mac) uyumluluk sorunları ve thread kilitlenmelerini önlemek için ses çalma kütüphaneleri eklenmeyecektir.
- **Veritabanı / Kayıt (Save/Load) Sistemi:** Oyun içi skorlar veya ilerlemeler veritabanına ya da dosyalara kaydedilmeyecek, sadece bellek üzerinde (RAM) anlık olarak tutulacaktır.
- **Gelişmiş Animasyon Sistemleri:** Karakter yürüyüş animasyonları, state machine yapıları kurulmayacaktır. Sadece yönlere göre dönebilen statik resimler (`BufferedImage`) veya renkli şekiller çizilecektir.
- **Çok Oyunculu Arayüz (Multiplayer):** Oyun tamamen tek oyunculu olacaktır.

---

## 3. Roller ve Görev Dağılımı (3 Kişi)

### **Andaç (Geliştirici A): Motor, Grafikler ve Çarpışma Denetimi (Core Engine, Graphics & Collision)**
- **Görevler:**
  - Oyun döngüsünün (`OyunDongusu`) kurulması (Sabit FPS/UPS yönetimi).
  - Grafik ekranının (`OyunPaneli` ve `Pencere`) tasarlanması, tuş girdi kontrolü (`TusKontrolcu`).
  - Çarpışma algılama sisteminin (`CarpismaDenetleyici`) yazılması (Oyuncu-Düşman çarpışması ve Mermi-Düşman çarpışması). Bu sistemin performanslı çalışmasını sağlamak.
- **Hedef Paketler:** `motor/` ve `varliklar/CarpismaDenetleyici.java`

### **Emre (Geliştirici B): Varlıklar ve Düşman Yapay Zekası (Entities & Enemy AI)**
- **Görevler:**
  - `Oyuncu` sınıfının temel can, hız, seviye ve hareket mekaniğinin yazılması.
  - `Dusman` temel sınıfı ve oyuncuyu takip eden hareket yapay zekasının (basit takip vektörü) yazılması.
  - Farklı düşman türlerinin (`HizliDusman`, `GucluDusman` vb.) kodlanması.
  - Belirli süre aralıklarıyla düşman üreten `DusmanUretici` mekanizmasının yapılması.
- **Hedef Paket:** `varliklar/` (CarpismaDenetleyici hariç)

### **Gizem (Geliştirici C): Silahlar, Gelişim ve Arayüz Ekranları (Weapons, Progression & UI)**
- **Görevler:**
  - `Silah` ve `Mermi` üst sınıflarının yazılması.
  - Döner Bıçak (oyuncu etrafında dönen) ve Ateş Topu (en yakın düşmana giden) mermi hareketlerinin yapılması.
  - Seviye atlama ekranının (`SeviyeArayuzu` - 3 kartlı seçim arayüzü) ve Can/Deneyim barlarının çizimi.
  - Düşman öldüğünde yere düşen ve oyuncuya çekilen deneyim kristallerinin (`DeneyimKristali`) kodlanması.
- **Hedef Paket:** `mekanikler/`

---

## 4. Önerilen Paket ve Klasör Yapısı

Kodların çakışmaması için projenin `src` klasörü altında şu şekilde paketlenmesi önerilir:

```
src/
├── motor/               # Andaç'ın Sorumluluğu
│   ├── Pencere.java     # JFrame ekranı
│   ├── OyunPaneli.java  # Ana çizim ve güncelleme paneli
│   ├── TusKontrolcu.java# Klavye girdi kontrolü
│   ├── FareKontrolcu.java # Fare girdi kontrolü (kart seçimleri vb. için)
│   ├── GorselYukleyici.java # Try-catch korumalı görsel yükleme yardımcısı
│   ├── OyunDurumu.java   # Oyun durumları (MENU, OYUN, GELISIM, OYUN_BITTI)
│   └── OyunDongusu.java # FPS ve UPS kontrolünü sağlayan döngü
│
├── varliklar/           # Emre ve Andaç Sorumluluğu
│   ├── Oyuncu.java      # Oyuncu karakteri (Emre)
│   ├── Dusman.java      # Düşman temel sınıfı (Emre)
│   ├── HizliDusman.java # Farklı düşman türleri (Emre)
│   ├── DusmanUretici.java # Düşman dalgaları üreten sınıf (Emre)
│   └── CarpismaDenetleyici.java # Çarpışmaları yöneten sınıf (Andaç)
│
├── mekanikler/          # Gizem'ın Sorumluluğu
│   ├── Silah.java       # Silah üst sınıfı
│   ├── Mermi.java       # Mermilerin hareketi ve çizimi
│   ├── DonerBicak.java  # Oyuncu etrafında dönen bıçak silahı
│   ├── AtesTopu.java    # Düşmana atılan ateş topu silahı
│   ├── DeneyimKristali.java # Yere düşen tecrübe puanları
│   └── SeviyeArayuzu.java # Yükseltme kartları arayüzü
│
└── AnaGiris.java        # Main metodu (Ortak entegrasyon sınıfı)
```

---

## 5. Yapay Zeka Yardımcılarına Talimatlar (AI Prompt Instructions)
Eğer bu projede bir kod yazıyorsanız veya bir geliştiriciye yardım ediyorsanız, şu talimatlara uyun:
1. **Geliştirici Kimlik Sorgulaması:** Projeyi açtığınızda kullanıcıya ilk olarak **ismini sorun** (Andaç, Emre veya Gizem). Kullanıcının verdiği isme göre yukarıdaki görev dağılımını referans alarak sadece o kişiye ait görev paketleri üzerinde kod yazın. Diğer geliştiricilerin görev alanına izinsiz müdahale etmeyin.
2. Sınıf ve değişken isimlerini her zaman Türkçe yazın, fakat Türkçe karakter kullanmayın (`oyuncu`, `dusman`, `hiz`).
3. Kodları aşırı optimize etmek yerine, 1. sınıf öğrencisinin anlayabileceği ve sunabileceği sadelikte tutun.
4. Her satıra açıklayıcı Türkçe yorumlar ekleyin (`// Oyuncunun x konumunu hizina göre gunceller`).
5. **Otomatik Git Akışı (Git Fetch/Pull/Merge/Push):** Geliştiriciler adına kod yazarken, yerel deponun (local) her zaman güncel olduğundan emin olun ve çakışmaları önlemek için şu adımları izleyin:
   - **Çalışmaya Başlamadan Önce (Fetch & Pull):** Herhangi bir kod yazma veya dosya düzenleme işlemine başlamadan önce mutlaka `git fetch` komutunu çalıştırarak uzak sunucuyu (remote) kontrol edin. Eğer uzak sunucuda yeni commit'ler varsa, kod yazmaya başlamadan önce `git pull` çalıştırarak bu değişiklikleri yerel deponuza çekin ve yerel kodunuzla birleştirin (merge).
   - **Kod Değişikliği Sonrası (Commit & Push):** Değişiklikleri başarıyla tamamladıktan ve test ettikten sonra, kodları göndermeden önce tekrar bir `git pull` yapın (kod yazarken bir başkası push etmiş olabilir). Ardından değişiklikleri stage edin (`git add`), kısa ve net bir Türkçe commit mesajı yazın (örn: `git commit -m "Oyuncu sinifi ve temel hareket mekanigi eklendi"`) ve değişiklikleri uzak sunucuya push edin (`git push`).
   - **Çakışma (Merge Conflict) Yönetimi:** Eğer `git pull` veya merge işlemi sırasında çakışma (conflict) meydana gelirse, **kesinlikle zorla push (`git push --force`) yapmayın**. Çakışan dosyaları inceleyin, çakışmaları mantıklı bir şekilde (Andaç, Emre ve Gizem'in kodlarını koruyarak) yerelde çözün (resolve) veya çözemediğiniz karmaşık durumlarda durup kullanıcıya bilgi vererek onay isteyin.
   - **Görev Sınırları Uyarısı:** Çakışmaları en aza indirmek için sadece kullanıcınızın paket klasörlerinde çalışın. Ortak entegrasyon dosyalarında (`AnaGiris.java`, `OyunPaneli.java`) değişiklik yaparken diğer paketlerin de güncel hallerini çekmiş olduğunuzdan emin olun.
6. **Bireysel İlerleme Takibi (Adım Dosyalarının Güncellenmesi):** Her başarılı kodlama adımından veya dosya güncellenmesinden sonra, ilgili geliştiriciye ait olan `proje_adim_[andac/emre/gizem].md` dosyasındaki görevi `[x]` olarak işaretleyin ve güncelleyin.
7. **Kullanıcı Görevi Bloklaması (AI Blocker):** Bir adım tamamlandığında, yapay zeka bir sonraki adıma otomatik olarak geçmeyecektir. Eğer o adımda kullanıcının yapması gereken bir eylem varsa (örn. resim dosyasını klasöre koymak, kodun çalışmasını test edip onay vermek vb.), yapay zeka bu eylemi kullanıcıya açıkça bildirecektir. **Kullanıcı bu adımı tamamlayıp onay verene kadar yapay zeka diğer adımlara kesinlikle geçmeyecektir.**
8. **Pixel Art Görsel Teması:** Oyun piksel temalı olacaktır. Çizim metotlarında piksel hissini korumak için yumuşatma (anti-aliasing) kapatılacak, görseller piksel piksel ölçeklenecektir. Görsellerin bulunamaması durumunda yapılacak yedek (fallback) çizimler de piksel temalı retro renklerle (örn. blok yapılı çizimler) yapılacaktır.

---

## 6. Teknik Tasarım ve Mimari Tavsiyeler (Technical Design Recommendations)

Yazılacak kodların 1. sınıf müfredatına uygun, kolay açıklanabilir olması ve çökmeleri/performans sorunlarını önlemek için şu mimari kararlar alınmıştır:

### A. Swing Timer ile Güvenli Oyun Döngüsü
- **Karar:** Oyun döngüsü (`OyunDongusu`) için Java `Thread` veya karmaşık `Runnable` yapıları yerine **`javax.swing.Timer`** kullanılacaktır.
- **Neden:** Swing kütüphanesi iş parçacığı güvenli (thread-safe) değildir. Ayrı bir Thread kullanıldığında liste çakışmaları (`ConcurrentModificationException`) yaşanabilir. `javax.swing.Timer` ise doğrudan Event Dispatch Thread (EDT) üzerinde çalışır ve senkronizasyon gerektirmeden 60 FPS (yaklaşık 16ms gecikme) hızında ekranı günceller. Sunumu hocaya açıklamak çok daha kolaydır.

### B. Daire Tabanlı Basit Çarpışma Testi (Collision Detection)
- **Karar:** Oyuncu, Düşman ve Mermi arasındaki çarpışmalar için karmaşık dikdörtgen kesişimleri yerine **daire tabanlı (uzaklık formülü)** çarpışma kontrolü yapılacaktır.
- **Formül:** `(dx * dx) + (dy * dy) < (r1 + r2) * (r1 + r2)`
- **Neden:** İki nesne arasındaki merkez uzaklığının karelerinin toplamı, yarıçaplar toplamının karesinden küçükse çarpışma gerçekleşmiştir. Bu, karekök (`Math.sqrt`) kullanmadığı için hem yüksek performanslıdır hem de sadece temel geometri bilgisiyle hocaya kolayca açıklanabilir.

### C. Düşmanların Kamera Görüş Alanına Göre Doğması (Spawning)
- **Karar:** Düşmanlar haritanın rastgele uzak bir köşesinde değil, oyuncunun o anki kamera ekranının **hemen dış sınırlarında** (örneğin ekran genişliğinin +50 piksel dışında) doğacaktır.
- **Neden:** Harita kayan ekran olacağı için (örn: 2000x2000 piksel), düşmanlar harita sınırında doğarsa oyuncuya ulaşmaları çok uzun sürer. Kamera koordinatlarına göre (ekran sınırının hemen dışında) doğmaları oyunu her an aksiyon dolu tutar.

### D. Çapraz Hareket Hızı Dengelemesi (Normalization)
- **Karar:** Oyuncu hem dikey hem yatay yöne (örneğin W ve D tuşlarına aynı anda) bastığında, hızı doğrudan toplanarak 1.41 kat daha hızlı gitmemelidir.
- **Formül:** Çapraz harekette hız bileşenleri normalize edilmeli ya da hız değeri `hiz * 0.707` ile çarpılarak hareket ettirilmelidir.

### E. Görsel Yükleme Hatalarına Karşı Güvenli Yapı (Asset Fallback)
- **Karar:** Oyundaki tüm resimler (`ImageIO.read`) yüklenirken bir `try-catch` bloğu kullanılacak ve resim yüklenemezse oyun çökmeyecektir.
- **Yöntem:** Resim bulunamazsa sistem otomatik olarak `g.fillOval()` veya `g.fillRect()` kullanarak renkli geometrik şekiller (Oyuncu için Mavi Daire, Zombi için Yeşil Daire vb.) çizecektir. Bu sayede sunum gününde dosya yolu hataları nedeniyle oyunun açılmaması gibi felaketlerin önüne geçilecektir.

### F. Oyun Durum Yönetimi (Game State Machine)
- **Karar:** Oyun, `OyunDurumu` adında bir Enum veya tamsayı (`int`) değişkeni ile yönetilecektir:
  - `MENU` (Ana menü ekranı)
  - `OYUN` (Aktif oynanış)
  - `GELISIM` (Seviye atlandığında oyunun donduğu ve kart seçim arayüzünün açıldığı an)
  - `OYUN_BITTI` (Oyuncunun öldüğü ekran)
- **Neden:** `GELISIM` durumunda oyun döngüsü arka plandaki düşman hareketlerini güncellemeyi durdurur (pause), ancak ekrandaki çizimi ve kart seçim arayüzünü çizmeye devam eder.

### G. Yeniden Başlatma (Restart) ve Durum Sıfırlama
- **Karar:** Oyuncu öldüğünde (`OYUN_BITTI`), klavyeden `R` tuşuna basıldığında oyundaki tüm hareketli nesne listeleri (düşmanlar, mermiler, kristaller) temizlenecek, oyuncu canı, koordinatları (merkezde doğma), seviyesi ve oyun süresi sıfırlanıp oyun `OYUN` durumuna geri getirilecektir.
- **Neden:** Bellekte eski nesnelerden kalan artıkların çakışmalara yol açmaması için temizleme işlemi `Iterator` veya doğrudan listeyi `.clear()` yaparak gerçekleştirilmelidir.

### H. Mermi Vuruşunda Düşman Geri İtme (Knockback)
- **Karar:** Düşmanlar bir mermiyle vurulduğunda, merminin geliş vektörünün tersi yönünde hafifçe geriye doğru savrulacaktır:
  - `dusmanX += mermiYonuX * geriItmeMiktari;`
  - `dusmanY += mermiYonuY * geriItmeMiktari;`
- **Neden:** Düşmanların oyuncunun üstüne anında yığılıp onu saniyeler içinde öldürmesini engelleyerek oynanış konforunu ve oyun zevkini artırmak.

### I. Sabit ve Sınırlandırılmış Büyük Oyun Haritası (Map Boundary)
- **Karar:** Harita boyutu sabit olarak **3000x3000px** olarak ayarlanacaktır. Oyuncunun (ve düşmanların) bu harita dışına çıkışları basit sınır kontrolüyle (`Math.max(0, Math.min(konum, 3000))`) engellenecektir.
- **Neden:** Oyuncunun sonsuz boşlukta kaybolmasını önlemek ve haritanın köşelerine ulaştığında sınırlanmasını sağlamak.

---

## 7. Proje İlerleme Durumu (Güncel Durum)

# Proje Belleği (Project Memory)

Bu dosya, projedeki tüm geliştiricilerin ve onların kullandığı yapay zeka yardımcılarının (AI coding assistants) aynı kurallara, mimariye ve görev dağılımına uymasını sağlamak amacıyla oluşturulmuştur. Lütfen bu dosyayı güncel tutun ve geliştirmelere başlamadan önce mutlaka okuyun.

---

## 1. Proje Tanımı ve Hedef
- **Oyun Türü:** Vampire Survivors benzeri, yukarıdan aşağıya (top-down) bakış açısına sahip bir hayatta kalma (Survival) oyunu.
- **Kullanılacak Dil ve Teknolojiler:** Yalnızca saf Java SE (Java Standard Edition) kullanılacaktır. Grafik arayüzü ve pencere yönetimi için **Java Swing** ve **AWT** kütüphaneleri tercih edilecektir.
- **Süre:** Maksimum 7 gün.

---

## 2. Temel Proje Kuralları

### Kural 1: Görev Sınırları ve Saygı
- Hiçbir geliştirici, diğer ekip arkadaşlarının görev alanındaki kod bloklarını izinsiz değiştirmeyecek veya bozmayacaktır. 
- Ortak alanlarda (örn. ana oyun paneli entegrasyonu) değişiklik yapılmadan önce mutlaka iletişim kurulacaktır.

### Kural 2: Kod Sadeligi ve Türkçe Yorum Satırları
- Proje üniversite 1. sınıf öğrencileri tarafından sunulacağından, anlatılması ve savunulması kolay kodlar yazılacaktır.
- Karmaşık üçüncü parti kütüphaneler (LibGDX vb.) kesinlikle kullanılmayacaktır.
- **Yazılan her satırın veya kod bloğunun işlevi, satır üzerinde veya yanında TÜRKÇE yorum satırlarıyla açıklanacaktır.** Yapay zeka araçları kod üretirken her satıra açıklama eklemelidir.

### Kural 3: Türkçe Değişken ve Sınıf İsimleri (İngilizce Karakterlerle)
- Değişken, metot, sınıf ve paket isimleri kesinlikle **Türkçe** olarak adlandırılacaktır.
- Ancak kodun derleme hatası almaması ve evrensel uyumluluk için Türkçe özel karakterler (**ç, ş, ğ, ı, ö, ü, Ç, Ş, Ğ, İ, Ö, Ü**) **KESİNLİKLE KULLANILMAYACAKTIR**.
- *Örnek Doğru Kullanım:* `oyuncuHizi`, `dusmanSayisi`, `carpismaVarMi`, `mermiHasari`, `Oyuncu`, `Dusman`, `OyunPaneli`.
- *Örnek Yanlış Kullanım:* `oyuncuHızı`, `düşmanSayısı`, `çarpışmaVarMı`, `mermiHasarı`.

### Kural 4: Projeyi Zorlaştıracak ve Eklenmeyecek Hususlar (Yapay Zekalar Kesinlikle Eklememeli)
Projenin 1. sınıf seviyesinde kalması ve 7 gün içinde sorunsuz bitmesi için yapay zekalar ve geliştiriciler şu özellikleri **asla kodlamayacaktır**:
- **Harici Fizik ve Çarpışma Motorları:** Box2D gibi kütüphaneler yerine sadece AWT'nin `Rectangle.intersects()` metodu veya iki nokta arasındaki uzaklık formülü kullanılmalıdır.
- **Ses/Müzik Entegrasyonu:** İşletim sistemi (Windows/Mac) uyumluluk sorunları ve thread kilitlenmelerini önlemek için ses çalma kütüphaneleri eklenmeyecektir.
- **Veritabanı / Kayıt (Save/Load) Sistemi:** Oyun içi skorlar veya ilerlemeler veritabanına ya da dosyalara kaydedilmeyecek, sadece bellek üzerinde (RAM) anlık olarak tutulacaktır.
- **Gelişmiş Animasyon Sistemleri:** Karakter yürüyüş animasyonları, state machine yapıları kurulmayacaktır. Sadece yönlere göre dönebilen statik resimler (`BufferedImage`) veya renkli şekiller çizilecektir.
- **Çok Oyunculu Arayüz (Multiplayer):** Oyun tamamen tek oyunculu olacaktır.

---

## 3. Roller ve Görev Dağılımı (3 Kişi)

### **Andaç (Geliştirici A): Motor, Grafikler ve Çarpışma Denetimi (Core Engine, Graphics & Collision)**
- **Görevler:**
  - Oyun döngüsünün (`OyunDongusu`) kurulması (Sabit FPS/UPS yönetimi).
  - Grafik ekranının (`OyunPaneli` ve `Pencere`) tasarlanması, tuş girdi kontrolü (`TusKontrolcu`).
  - Çarpışma algılama sisteminin (`CarpismaDenetleyici`) yazılması (Oyuncu-Düşman çarpışması ve Mermi-Düşman çarpışması). Bu sistemin performanslı çalışmasını sağlamak.
- **Hedef Paketler:** `motor/` ve `varliklar/CarpismaDenetleyici.java`

### **Emre (Geliştirici B): Varlıklar ve Düşman Yapay Zekası (Entities & Enemy AI)**
- **Görevler:**
  - `Oyuncu` sınıfının temel can, hız, seviye ve hareket mekaniğinin yazılması.
  - `Dusman` temel sınıfı ve oyuncuyu takip eden hareket yapay zekasının (basit takip vektörü) yazılması.
  - Farklı düşman türlerinin (`HizliDusman`, `GucluDusman` vb.) kodlanması.
  - Belirli süre aralıklarıyla düşman üreten `DusmanUretici` mekanizmasının yapılması.
- **Hedef Paket:** `varliklar/` (CarpismaDenetleyici hariç)

### **Gizem (Geliştirici C): Silahlar, Gelişim ve Arayüz Ekranları (Weapons, Progression & UI)**
- **Görevler:**
  - `Silah` ve `Mermi` üst sınıflarının yazılması.
  - Döner Bıçak (oyuncu etrafında dönen) ve Ateş Topu (en yakın düşmana giden) mermi hareketlerinin yapılması.
  - Seviye atlama ekranının (`SeviyeArayuzu` - 3 kartlı seçim arayüzü) ve Can/Deneyim barlarının çizimi.
  - Düşman öldüğünde yere düşen ve oyuncuya çekilen deneyim kristallerinin (`DeneyimKristali`) kodlanması.
- **Hedef Paket:** `mekanikler/`

---

## 4. Önerilen Paket ve Klasör Yapısı

Kodların çakışmaması için projenin `src` klasörü altında şu şekilde paketlenmesi önerilir:

```
src/
├── motor/               # Andaç'ın Sorumluluğu
│   ├── Pencere.java     # JFrame ekranı
│   ├── OyunPaneli.java  # Ana çizim ve güncelleme paneli
│   ├── TusKontrolcu.java# Klavye girdi kontrolü
│   ├── FareKontrolcu.java # Fare girdi kontrolü (kart seçimleri vb. için)
│   ├── GorselYukleyici.java # Try-catch korumalı görsel yükleme yardımcısı
│   ├── OyunDurumu.java   # Oyun durumları (MENU, OYUN, GELISIM, OYUN_BITTI)
│   └── OyunDongusu.java # FPS ve UPS kontrolünü sağlayan döngü
│
├── varliklar/           # Emre ve Andaç Sorumluluğu
│   ├── Oyuncu.java      # Oyuncu karakteri (Emre)
│   ├── Dusman.java      # Düşman temel sınıfı (Emre)
│   ├── HizliDusman.java # Farklı düşman türleri (Emre)
│   ├── DusmanUretici.java # Düşman dalgaları üreten sınıf (Emre)
│   └── CarpismaDenetleyici.java # Çarpışmaları yöneten sınıf (Andaç)
│
├── mekanikler/          # Gizem'ın Sorumluluğu
│   ├── Silah.java       # Silah üst sınıfı
│   ├── Mermi.java       # Mermilerin hareketi ve çizimi
│   ├── DonerBicak.java  # Oyuncu etrafında dönen bıçak silahı
│   ├── AtesTopu.java    # Düşmana atılan ateş topu silahı
│   ├── DeneyimKristali.java # Yere düşen tecrübe puanları
│   └── SeviyeArayuzu.java # Yükseltme kartları arayüzü
│
└── AnaGiris.java        # Main metodu (Ortak entegrasyon sınıfı)
```

---

## 5. Yapay Zeka Yardımcılarına Talimatlar (AI Prompt Instructions)
Eğer bu projede bir kod yazıyorsanız veya bir geliştiriciye yardım ediyorsanız, şu talimatlara uyun:
1. **Geliştirici Kimlik Sorgulaması:** Projeyi açtığınızda kullanıcıya ilk olarak **ismini sorun** (Andaç, Emre veya Gizem). Kullanıcının verdiği isme göre yukarıdaki görev dağılımını referans alarak sadece o kişiye ait görev paketleri üzerinde kod yazın. Diğer geliştiricilerin görev alanına izinsiz müdahale etmeyin.
2. Sınıf ve değişken isimlerini her zaman Türkçe yazın, fakat Türkçe karakter kullanmayın (`oyuncu`, `dusman`, `hiz`).
3. Kodları aşırı optimize etmek yerine, 1. sınıf öğrencisinin anlayabileceği ve sunabileceği sadelikte tutun.
4. Her satıra açıklayıcı Türkçe yorumlar ekleyin (`// Oyuncunun x konumunu hizina göre gunceller`).
5. **Otomatik Git Akışı (Git Fetch/Pull/Merge/Push):** Geliştiriciler adına kod yazarken, yerel deponun (local) her zaman güncel olduğundan emin olun ve çakışmaları önlemek için şu adımları izleyin:
   - **Çalışmaya Başlamadan Önce (Fetch & Pull):** Herhangi bir kod yazma veya dosya düzenleme işlemine başlamadan önce mutlaka `git fetch` komutunu çalıştırarak uzak sunucuyu (remote) kontrol edin. Eğer uzak sunucuda yeni commit'ler varsa, kod yazmaya başlamadan önce `git pull` çalıştırarak bu değişiklikleri yerel deponuza çekin ve yerel kodunuzla birleştirin (merge).
   - **Kod Değişikliği Sonrası (Commit & Push):** Değişiklikleri başarıyla tamamladıktan ve test ettikten sonra, kodları göndermeden önce tekrar bir `git pull` yapın (kod yazarken bir başkası push etmiş olabilir). Ardından değişiklikleri stage edin (`git add`), kısa ve net bir Türkçe commit mesajı yazın (örn: `git commit -m "Oyuncu sinifi ve temel hareket mekanigi eklendi"`) ve değişiklikleri uzak sunucuya push edin (`git push`).
   - **Çakışma (Merge Conflict) Yönetimi:** Eğer `git pull` veya merge işlemi sırasında çakışma (conflict) meydana gelirse, **kesinlikle zorla push (`git push --force`) yapmayın**. Çakışan dosyaları inceleyin, çakışmaları mantıklı bir şekilde (Andaç, Emre ve Gizem'in kodlarını koruyarak) yerelde çözün (resolve) veya çözemediğiniz karmaşık durumlarda durup kullanıcıya bilgi vererek onay isteyin.
   - **Görev Sınırları Uyarısı:** Çakışmaları en aza indirmek için sadece kullanıcınızın paket klasörlerinde çalışın. Ortak entegrasyon dosyalarında (`AnaGiris.java`, `OyunPaneli.java`) değişiklik yaparken diğer paketlerin de güncel hallerini çekmiş olduğunuzdan emin olun.
6. **Bireysel İlerleme Takibi (Adım Dosyalarının Güncellenmesi):** Her başarılı kodlama adımından veya dosya güncellenmesinden sonra, ilgili geliştiriciye ait olan `proje_adim_[andac/emre/gizem].md` dosyasındaki görevi `[x]` olarak işaretleyin ve güncellenmesi.
7. **Kullanıcı Görevi Bloklaması (AI Blocker):** Bir adım tamamlandığında, yapay zeka bir sonraki adıma otomatik olarak geçmeyecektir. Eğer o adımda kullanıcının yapması gereken bir eylem varsa (örn. resim dosyasını klasöre koymak, kodun çalışmasını test edip onay vermek vb.), yapay zeka bu eylemi kullanıcıya açıkça bildirecektir. **Kullanıcı bu adımı tamamlayıp onay verene kadar yapay zeka diğer adımlara kesinlikle geçmeyecektir.**
8. **Pixel Art Görsel Teması:** Oyun piksel temalı olacaktır. Çizim metotlarında piksel hissini korumak için yumuşatma (anti-aliasing) kapatılacak, görseller piksel piksel ölçeklenecektir. Görsellerin bulunamaması durumunda yapılacak yedek (fallback) çizimler de piksel temalı retro renklerle (örn. blok yapılı çizimler) yapılacaktır.

---

## 6. Teknik Tasarım ve Mimari Tavsiyeler (Technical Design Recommendations)

Yazılacak kodların 1. sınıf müfredatına uygun, kolay açıklanabilir olması ve çökmeleri/performans sorunlarını önlemek için şu mimari kararlar alınmıştır:

### A. Swing Timer ile Güvenli Oyun Döngüsü
- **Karar:** Oyun döngüsü (`OyunDongusu`) için Java `Thread` veya karmaşık `Runnable` yapıları yerine **`javax.swing.Timer`** kullanılacaktır.
- **Neden:** Swing kütüphanesi iş parçacığı güvenli (thread-safe) değildir. Ayrı bir Thread kullanıldığında liste çakışmaları (`ConcurrentModificationException`) yaşanabilir. `javax.swing.Timer` ise doğrudan Event Dispatch Thread (EDT) üzerinde çalışır ve senkronizasyon gerektirmeden 60 FPS (yaklaşık 16ms gecikme) hızında ekranı günceller. Sunumu hocaya açıklamak çok daha kolaydır.

### B. Daire Tabanlı Basit Çarpışma Testi (Collision Detection)
- **Karar:** Oyuncu, Düşman ve Mermi arasındaki çarpışmalar için karmaşık dikdörtgen kesişimleri yerine **daire tabanlı (uzaklık formülü)** çarpışma kontrolü yapılacaktır.
- **Formül:** `(dx * dx) + (dy * dy) < (r1 + r2) * (r1 + r2)`
- **Neden:** İki nesne arasındaki merkez uzaklığının karelerinin toplamı, yarıçaplar toplamının karesinden küçükse çarpışma gerçekleşmiştir. Bu, karekök (`Math.sqrt`) kullanmadığı için hem yüksek performanslıdır hem de sadece temel geometri bilgisiyle hocaya kolayca açıklanabilir.

### C. Düşmanların Kamera Görüş Alanına Göre Doğması (Spawning)
- **Karar:** Düşmanlar haritanın rastgele uzak bir köşesinde değil, oyuncunun o anki kamera ekranının **hemen dış sınırlarında** (örneğin ekran genişliğinin +50 piksel dışında) doğacaktır.
- **Neden:** Harita kayan ekran olacağı için (örn: 2000x2000 piksel), düşmanlar harita sınırında doğarsa oyuncuya ulaşmaları çok uzun sürer. Kamera koordinatlarına göre (ekran sınırının hemen dışında) doğmaları oyunu her an aksiyon dolu tutar.

### D. Çapraz Hareket Hızı Dengelemesi (Normalization)
- **Karar:** Oyuncu hem dikey hem yatay yöne (örneğin W ve D tuşlarına aynı anda) bastığında, hızı doğrudan toplanarak 1.41 kat daha hızlı gitmemelidir.
- **Formül:** Çapraz harekette hız bileşenleri normalize edilmeli ya da hız değeri `hiz * 0.707` ile çarpılarak hareket ettirilmelidir.

### E. Görsel Yükleme Hatalarına Karşı Güvenli Yapı (Asset Fallback)
- **Karar:** Oyundaki tüm resimler (`ImageIO.read`) yüklenirken bir `try-catch` bloğu kullanılacak ve resim yüklenemezse oyun çökmeyecektir.
- **Yöntem:** Resim bulunamazsa sistem otomatik olarak `g.fillOval()` veya `g.fillRect()` kullanarak renkli geometrik şekiller (Oyuncu için Mavi Daire, Zombi için Yeşil Daire vb.) çizecektir. Bu sayede sunum gününde dosya yolu hataları nedeniyle oyunun açılmaması gibi felaketlerin önüne geçilecektir.

### F. Oyun Durum Yönetimi (Game State Machine)
- **Karar:** Oyun, `OyunDurumu` adında bir Enum veya tamsayı (`int`) değişkeni ile yönetilecektir:
  - `MENU` (Ana menü ekranı)
  - `OYUN` (Aktif oynanış)
  - `GELISIM` (Seviye atlandığında oyunun donduğu ve kart seçim arayüzünün açıldığı an)
  - `OYUN_BITTI` (Oyuncunun öldüğü ekran)
- **Neden:** `GELISIM` durumunda oyun döngüsü arka plandaki düşman hareketlerini güncellemeyi durdurur (pause), ancak ekrandaki çizimi ve kart seçim arayüzünü çizmeye devam eder.

### G. Yeniden Başlatma (Restart) ve Durum Sıfırlama
- **Karar:** Oyuncu öldüğünde (`OYUN_BITTI`), klavyeden `R` tuşuna basıldığında oyundaki tüm hareketli nesne listeleri (düşmanlar, mermiler, kristaller) temizlenecek, oyuncu canı, koordinatları (merkezde doğma), seviyesi ve oyun süresi sıfırlanıp oyun `OYUN` durumuna geri getirilecektir.
- **Neden:** Bellekte eski nesnelerden kalan artıkların çakışmalara yol açmaması için temizleme işlemi `Iterator` veya doğrudan listeyi `.clear()` yaparak gerçekleştirilmelidir.

### H. Mermi Vuruşunda Düşman Geri İtme (Knockback)
- **Karar:** Düşmanlar bir mermiyle vurulduğunda, merminin geliş vektörünün tersi yönünde hafifçe geriye doğru savrulacaktır:
  - `dusmanX += mermiYonuX * geriItmeMiktari;`
  - `dusmanY += mermiYonuY * geriItmeMiktari;`
- **Neden:** Düşmanların oyuncunun üstüne anında yığılıp onu saniyeler içinde öldürmesini engelleyerek oynanış konforunu ve oyun zevkini artırmak.

### I. Sabit ve Sınırlandırılmış Büyük Oyun Haritası (Map Boundary)
- **Karar:** Harita boyutu sabit olarak **3000x3000px** olarak ayarlanacaktır. Oyuncunun (ve düşmanların) bu harita dışına çıkışları basit sınır kontrolüyle (`Math.max(0, Math.min(konum, 3000))`) engellenecektir.
- **Neden:** Oyuncunun sonsuz boşlukta kaybolmasını önlemek ve haritanın köşelerine ulaştığında sınırlanmasını sağlamak.

---

## 7. Proje İlerleme Durumu (Güncel Durum)

Projedeki her geliştiricinin yaptığı geliştirmeler ve tamamladığı adımlar aşağıda özetlenmiştir.

### Andaç (Geliştirici A):
- **Adım 1: Temel Motor ve Pencere Kurulumu** [TAMAMLANDI] -> `Pencere`, `OyunPaneli`, `OyunDurumu`, `GorselYukleyici` oluşturuldu.
- **Adım 2: Klavye ve Fare Kontrol Mekanizması** [TAMAMLANDI] -> `TusKontrolcu` ve `FareKontrolcu` kodlandı.

### Emre (Geliştirici B):
- **Adım 1: Oyuncu Karakter Sınıfı** [TAMAMLANDI] -> `Oyuncu` temel nitelikleri, hareket dengelemesi, harita sınırı ve yedek çizim mekanizması ile tamamlandı.
- **Adım 2: Temel Düşman Sınıfı, Takip ve Geri İtme (Knockback)** [TAMAMLANDI] -> `Dusman` temel sınıfı, takip yapay zekası ve knockback mekanizması yazıldı.

### Gizem (Geliştirici C):
- **Adım 1: Temel Silah ve Mermi Sınıfları** [TAMAMLANDI] -> `Silah` ve `Mermi` üst sınıfları kodlandı.
- **Adım 2: Ateş Topu ve Döner Bıçak Mekanikleri** [TAMAMLANDI] -> `AtesTopu`, `DonerBicak` ve `DonerBicakMermi` yazıldı.
