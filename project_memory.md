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
5. **Otomatik Git Akışı (Git Push/Pull):** Projede yapılan güncellemelerin kaybolmaması ve diğer ekip üyelerinin kodlarının güncel kalması için, başarılı her kodlama adımından veya dosya güncellenmesinden sonra değişiklikleri otomatik olarak commit edin ve uzak sunucuya push yapın.
   - Değişiklikleri push etmeden önce mutlaka `git pull` yaparak güncel kodları alın.
   - Commit mesajlarını kısa ve net Türkçe yazın (örn: `git commit -m "Oyuncu sinifi ve temel hareket mekanigi eklendi"`).
   - Çakışmaları önlemek için sadece kendi görev paketleriniz üzerinde çalışın ve ana dosyaları (`AnaGiris.java` veya `OyunPaneli.java` gibi ortak entegrasyon dosyalarını) değiştirmeden önce yereldeki değişiklikleri diğer arkadaşlarınızla koordine edin.
