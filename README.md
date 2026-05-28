# 🎮 Java 2D Piksel Hayatta Kalma Oyunu (Vampire Survivors Benzeri)

Bu proje, üniversite 1. sınıf oyun geliştirme dersi kapsamında hazırlanan, **Java Swing ve AWT** kütüphaneleri kullanılarak geliştirilmiş 2D piksel sanat (pixel art) tarzında yukarıdan aşağıya (top-down) bir hayatta kalma oyunudur.

---

## 👥 Ekip ve Rol Dağılımı

Projede 3 geliştirici bağımsız ama entegre paketler halinde çalışmaktadır:

1.  **Andaç (Geliştirici A) - Motor, Grafikler ve Çarpışma Denetimi:**
    *   Oyun penceresi (`Pencere`), oyun paneli (`OyunPaneli`), klavye kontrolleri (`TusKontrolcu`) ve oyun döngüsü (`OyunDongusu`).
    *   Nesneler arası çarpışmaları kontrol eden `CarpismaDenetleyici`.
2.  **Emre (Geliştirici B) - Varlıklar ve Düşman Yapay Zekası:**
    *   Oyuncu sınıfı (`Oyuncu`), düşman sınıfları (`Dusman`, `HizliDusman` vb.) ve oyuncuyu kovalayan yapay zeka.
    *   Kamera dışı düşman spawn sistemi (`DusmanUretici`).
3.  **Gizem (Geliştirici C) - Silahlar, Gelişim ve Arayüz:**
    *   Ateş Topu (`AtesTopu`) ve Döner Bıçak (`DonerBicak`) gibi otomatik silah mekanikleri.
    *   Deneyim kristali (`DeneyimKristali`) toplama ve seviye atlama kart arayüzü (`SeviyeArayuzu`).

---

## 🛠️ Teknolojiler ve Kapsam

*   **Dil:** Saf Java SE
*   **Grafik Arayüzü:** Java Swing & AWT (Harici oyun motoru veya karmaşık fizik kütüphaneleri kullanılmamıştır).
*   **Görsel Tema:** Retro Piksel Sanatı (Pixel Art).
*   **Döngü Yapısı:** İş parçacığı güvenliği (thread-safety) ve sunum kolaylığı açısından **`javax.swing.Timer`** tabanlı 60 FPS oyun döngüsü.

---

## 📌 Temel Kurallar ve Geliştirme Standartları

Projenin temiz ve çakışmasız ilerlemesi için aşağıdaki kurallar belirlenmiştir:

1.  **Görev Sınırlarına Saygı:** Hiç kimse diğer ekip arkadaşının sorumluluk paketindeki dosyalara izinsiz müdahale etmez.
2.  **Kod Sadeligi ve Yorumlar:** Tüm kod blokları ve satırları ne işe yaradığını açıklayan **Türkçe yorum satırları** içermek zorundadır.
3.  **Değişken İsimleri:** Değişken, metot ve sınıf adları tamamen Türkçe seçilir, ancak derleme hatası yaşanmaması için Türkçe özel karakterler (`ı, ş, ç, ğ, ö, ü`) kullanılmaz (örn: `oyuncuHizi`, `dusmanHasari`).
4.  **Bireysel İlerleme Dosyaları:** Her geliştirici için hazırlanan `proje_adim_[isim].md` dosyaları, yapay zekalar tarafından adım adım tamamlandıkça güncellenir.
5.  **Yapay Zeka Bloklaması:** Yapay zeka yardımcıları, tamamlanan adımda kullanıcının yapması gereken bir eylem varsa (örn. kod testi, görselleri klasöre ekleme), kullanıcı onay verene kadar bir sonraki adıma geçemez.

---

## 🗂️ Proje Yapısı

Kodlar `src/` klasörü altında aşağıdaki paket yapısında tutulmaktadır:

*   [`motor/`](file:///Users/aliandacerdass/JavaGameProjectForSchool/src/motor): Ekran, çizim paneli, girdiler ve ana döngü.
*   [`varliklar/`](file:///Users/aliandacerdass/JavaGameProjectForSchool/src/varliklar): Karakter, düşmanlar, yapay zekalar ve çarpışma kontrolü.
*   [`mekanikler/`](file:///Users/aliandacerdass/JavaGameProjectForSchool/src/mekanikler): Silahlar, mermiler, deneyim kristalleri ve seviye arayüzleri.
*   [`AnaGiris.java`](file:///Users/aliandacerdass/JavaGameProjectForSchool/src/AnaGiris.java): Oyunu başlatan ana sınıf.

---

## 🚀 Çalıştırma

Projeyi herhangi bir Java IDE'sinde (VS Code, IntelliJ, Eclipse) açarak `AnaGiris.java` dosyasını çalıştırabilirsiniz.

Alternatif olarak terminal üzerinden derlemek ve çalıştırmak için:

```bash
# Derleme
javac -d bin src/*.java src/*/*.java

# Çalıştırma
java -cp bin AnaGiris
```

---

## 📝 Proje Takip ve Yapay Zeka Dosyaları

*   [Proje Planı](file:///Users/aliandacerdass/JavaGameProjectForSchool/project_plan.md) - Proje genel yol haritası.
*   [Proje Hafızası](file:///Users/aliandacerdass/JavaGameProjectForSchool/project_memory.md) - Yapay zekalar için teknik talimatlar ve Git kuralları.
*   [Andaç Adımları](file:///Users/aliandacerdass/JavaGameProjectForSchool/proje_adim_andac.md) - Andaç için ilerleme listesi.
*   [Emre Adımları](file:///Users/aliandacerdass/JavaGameProjectForSchool/proje_adim_emre.md) - Emre için ilerleme listesi.
*   [Gizem Adımları](file:///Users/aliandacerdass/JavaGameProjectForSchool/proje_adim_gizem.md) - Gizem için ilerleme listesi.
