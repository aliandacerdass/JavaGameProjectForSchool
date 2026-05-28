# Plan

Bu plan, Andaç, Emre ve Gizem'den oluşan 3 kişilik üniversite 1. sınıf ekibinin, Java Swing ve AWT kütüphanelerini kullanarak 7 gün içinde "Vampire Survivors" benzeri bir hayatta kalma oyunu geliştirmesini hedeflemektedir. Görevler, ekip üyelerinin birbirlerinin kodunu bozmadan bağımsız çalışabilmesi için üç modüle ayrılmıştır.

## Scope
- In:
  - Java Swing ve AWT tabanlı grafik ve pencere yönetimi.
  - Oyuncuyu merkeze alan ve kayan kamera sistemi (`Graphics.translate` yöntemiyle basit ekran kaydırma).
  - Sonsuz oyun yapısı (Zaman sınırlaması yok, zamanla düşmanların can, hız ve hasar statları yüzdelik olarak artarak oyun zorlaşacak).
  - Türkçe değişken isimleri (sadece İngilizce karakterlerle: oyuncuHizi, dusmanHizi vb.).
  - Her kod bloğu ve satır için detaylı Türkçe yorum satırları.
  - Basit bir ana menü, oyun ekranı, seviye atlama kart seçim menüsü ve oyun bitti ekranı.
- Out:
  - Üçüncü parti oyun kütüphaneleri (LibGDX, Slick2D, Box2D fizik motoru vb.).
  - Ağaç yapılı karmaşık algoritmalar veya yapay zeka kütüphaneleri.
  - Türkçe özel karakter içeren değişken veya sınıf isimleri (ı, ş, ç, ğ, ö, ü).
  - Çok oyunculu (multiplayer) modlar.
  - Ses ve müzik dosyaları/çalma mekanizmaları (Mac/Windows uyumsuzluklarını önlemek için).
  - Veritabanı ve oyun kaydetme (Save/Load) sistemleri.
  - Karmaşık sprite animasyon durum makineleri (sadece statik görseller veya basit şekiller).

## Action items
[x] Temel Proje Yapısının Kurulması (Dosya dizinleri, gitignore ve paket tanımları)
[ ] Modül 1 (Andaç): Oyun Penceresi, Paneli, Kayan Kamera ve Harita Sınırları (Pencere, Panel, TusKontrolcu, OyunDongusu)
[x] Modül 2 (Emre): Oyuncu Karakter Sınıfının Oluşturulması (Oyuncu: konum, can, hiz, deneyim)
[ ] Modül 2 (Emre): Düşman Sınıfının, Yapay Zekasının ve Geri İtme (Knockback) Mekaniğinin Kodlanması
[x] Modül 3 (Gizem): Temel Silah, Mermi ve Otomatik Saldırı Sisteminin Yazılması (Silah, Mermi)
[x] Modül 3 (Gizem): Döner Bıçak ve Ateş Topu Silahlarının Kodlanması (DonerBicak, AtesTopu)
[ ] Modül 1 (Andaç): Daire Tabanlı Çarpışma Kontrolleri ve Yeniden Başlatma (Restart) Mekanizması
[ ] Modül 3 (Gizem): Deneyim Kristali, Çekim Mıknatısı ve Seviye Atlama Arayüzünün Yapılması
[ ] Tüm Modüllerin Entegrasyonu ve Oyun Dengesi Ayarları
[ ] Kodların Türkçe Yorum Satırları ile Tam Belgelenmesi ve Test Edilmesi

## Open questions
- Grafik olarak hazır 2D sprite resimleri mi kullanacağız, yoksa basit geometrik şekillerle (daire, kare) mi render edeceğiz? (Geometrik şekiller anlatım ve çizim açısından en kolayıdır).
- Oyunun zorluk artış hızı nasıl olacak (örneğin her 30 saniyede bir yeni düşman türü veya daha fazla düşman)?
- Seviye atlandığında oyuncuya sunulacak geliştirmeler (hız artışı, can yenileme, yeni silah) neler olacak?
