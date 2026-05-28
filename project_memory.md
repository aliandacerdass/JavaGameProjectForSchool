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

### **Geliştirici 1 (Geliştirici A): Motor ve Arayüz (Core Engine & GUI)**
- **Görevler:**
  - Oyun penceresinin (`JFrame`) ve oyun panelinin (`JPanel`) oluşturulması.
  - Oyun döngüsünün (`Game Loop` - Thread veya Swing Timer kullanarak) kurulması.
  - Klavye/Fare girdilerinin (`KeyListener` / `MouseListener`) dinlenmesi.
  - Ekran yönetimi (Ana Menü, Oyun Ekranı, Oyun Bitti, Duraklatma Ekranı).
- **Hedef Paket:** `motor`

### **Geliştirici 2 (Geliştirici B): Karakterler ve Çarpışma (Entities & Collisions)**
- **Görevler:**
  - `Oyuncu` sınıfının yazılması (Can, konum, hız, deneyim seviyesi, hareket metotları).
  - `Dusman` sınıfının ve farklı düşman türlerinin tanımlanması.
  - Düşmanların oyuncuyu takip etmesini sağlayan basit yapay zeka algoritmasının yazılması.
  - Çarpışma algılama (`CarpismaDenetleyici`) sisteminin kurulması (oyuncu-düşman çarpışması, mermi-düşman çarpışması).
- **Hedef Paket:** `varliklar`

### **Geliştirici 3 (Geliştirici C): Silahlar, Gelişim ve Eşyalar (Weapons & Progression)**
- **Görevler:**
  - Temel `Silah` ve `Mermi` (veya Proje) sınıflarının yazılması.
  - Döner Bıçak (oyuncunun etrafında dönen) ve Ateş Topu (en yakın düşmana atılan) gibi farklı silah mekaniklerinin yapılması.
  - Seviye atlama sistemi ve seviye atlandığında çıkan yükseltme seçim ekranı (`SeviyeArayuzu`).
  - Düşmanlar öldüğünde yere düşen Deneyim Kristallerinin (`DeneyimKristali`) oluşturulması ve toplanması.
- **Hedef Paket:** `mekanikler`

---

## 4. Önerilen Paket ve Klasör Yapısı

Kodların çakışmaması için projenin `src` klasörü altında şu şekilde paketlenmesi önerilir:

```
src/
├── motor/               # Geliştirici A
│   ├── Pencere.java     # JFrame ekranı
│   ├── OyunPaneli.java  # Ana çizim ve güncelleme paneli
│   ├── TusKontrolcu.java# Klavye girdi kontrolü
│   └── OyunDongusu.java # FPS ve UPS kontrolünü sağlayan döngü
│
├── varliklar/           # Geliştirici B
│   ├── Oyuncu.java      # Oyuncu karakteri
│   ├── Dusman.java      # Düşman temel sınıfı
│   ├── HızlıDusman.java # Farklı düşman türleri
│   └── CarpismaDenetleyici.java # Çarpışmaları yöneten sınıf
│
├── mekanikler/          # Geliştirici C
│   ├── Silah.java       # Silah üst sınıfı
│   ├── Mermi.java       # Mermilerin hareketi ve çizimi
│   ├── DonerBicak.java  # Oyuncu etrafında dönen bıçak silahı
│   ├── AtesTopu.java    # Düşmana atılan ateş topu silahı
│   ├── DeneyimKristali.java # Yere düşen tecrübe puanları
│   └── SeviyeArayuzu.java # Yükseltme kartları arayüzü
│
└── AnaGiris.java        # Main metodu (Oyunu başlatan sınıf)
```

---

## 5. Yapay Zeka Yardımcılarına Talimatlar (AI Prompt Instructions)
Eğer bu projede bir kod yazıyorsanız veya bir geliştiriciye yardım ediyorsanız, şu talimatlara uyun:
1. Sınıf ve değişken isimlerini her zaman Türkçe yazın, fakat Türkçe karakter kullanmayın (`oyuncu`, `dusman`, `hiz`).
2. Kodları aşırı optimize etmek yerine, 1. sınıf öğrencisinin anlayabileceği ve sunabileceği sadelikte tutun.
3. Her satıra açıklayıcı Türkçe yorumlar ekleyin (`// Oyuncunun x konumunu hizina göre gunceller`).
4. Sadece kendi geliştiricinizin modül paketindeki (`motor`, `varliklar`, `mekanikler`) dosyalarda değişiklik yapın. Diğer paketlerdeki dosyaları değiştirmek için geliştiriciden onay isteyin.
5. **Otomatik Git Akışı (Git Push/Pull):** Projede yapılan güncellemelerin kaybolmaması ve diğer ekip üyelerinin kodlarının güncel kalması için, başarılı her kodlama adımından veya dosya güncellenmesinden sonra değişiklikleri otomatik olarak commit edin ve uzak sunucuya push yapın.
   - Değişiklikleri push etmeden önce mutlaka `git pull` yaparak güncel kodları alın.
   - Commit mesajlarını kısa ve net Türkçe yazın (örn: `git commit -m "Oyuncu sinifi ve temel hareket mekanigi eklendi"`).
   - Çakışmaları önlemek için sadece kendi görev paketleriniz üzerinde çalışın ve ana dosyaları (`AnaGiris.java` veya `OyunPaneli.java` gibi ortak entegrasyon dosyalarını) değiştirmeden önce yereldeki değişiklikleri diğer arkadaşlarınızla koordine edin.
