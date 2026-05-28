# Plan

Bu plan, 3 kişilik bir üniversite 1. sınıf ekibinin, Java Swing ve AWT kütüphanelerini kullanarak 7 gün içinde "Vampire Survivors" benzeri bir hayatta kalma oyunu geliştirmesini hedeflemektedir. Görevler, ekip üyelerinin birbirlerinin kodunu bozmadan bağımsız çalışabilmesi için üç modüle (Motor ve Arayüz, Karakterler ve Mekanikler, Silahlar ve Gelişim) ayrılmıştır.

## Scope
- In:
  - Java Swing ve AWT tabanlı grafik ve pencere yönetimi.
  - Bağımsız 3 modül yapısı (Motor, Karakterler/Düşmanlar, Silahlar/Gelişim).
  - Türkçe değişken isimleri (sadece İngilizce karakterlerle: oyuncuHizi, dusmanHizi vb.).
  - Her kod bloğu ve satır için detaylı Türkçe yorum satırları.
  - Basit bir ana menü, oyun ekranı, seviye atlama menüsü ve oyun bitti ekranı.
- Out:
  - Üçüncü parti oyun kütüphaneleri (LibGDX, Slick2D vb.).
  - Ağaç yapılı karmaşık algoritmalar veya yapay zeka kütüphaneleri.
  - Türkçe özel karakter içeren değişken veya sınıf isimleri (ı, ş, ç, ğ, ö, ü).
  - Çok oyunculu (multiplayer) modlar.

## Action items
[ ] Temel Proje Yapısının Kurulması (Dosya dizinleri, gitignore ve paket tanımları)
[ ] Modül 1: Oyun Penceresi, Paneli ve Ana Döngünün Yazılması (Pencere, Panel, TusKontrolcu, OyunDongusu)
[ ] Modül 2: Oyuncu Karakter Sınıfının Oluşturulması (Oyuncu: konum, can, hiz, deneyim)
[ ] Modül 2: Düşman Sınıfının ve Düşman Üreticinin Yazılması (Dusman: takip yapay zekası, DusmanUretici)
[ ] Modül 3: Temel Silah ve Mermi Sınıflarının Yazılması (Silah, Mermi)
[ ] Modül 3: Döner Bıçak ve Ateş Topu Silahlarının Kodlanması (DonerBicak, AtesTopu)
[ ] Modül 2: Çarpışma Kontrollerinin Eklenmesi (CarpismaDenetleyici: oyuncu-düşman ve mermi-düşman)
[ ] Modül 3: Deneyim Kristali ve Seviye Atlama Arayüzünün Yapılması (DeneyimKristali, SeviyeArayuzu)
[ ] Tüm Modüllerin Entegrasyonu ve Oyun Dengesi Ayarları
[ ] Kodların Türkçe Yorum Satırları ile Tam Belgelenmesi ve Test Edilmesi

## Open questions
- Grafik olarak hazır 2D sprite resimleri mi kullanacağız, yoksa basit geometrik şekillerle (daire, kare) mi render edeceğiz? (Geometrik şekiller anlatım ve çizim açısından en kolayıdır).
- Oyunun zorluk artış hızı nasıl olacak (örneğin her 30 saniyede bir yeni düşman türü veya daha fazla düşman)?
- Seviye atlandığında oyuncuya sunulacak geliştirmeler (hız artışı, can yenileme, yeni silah) neler olacak?
