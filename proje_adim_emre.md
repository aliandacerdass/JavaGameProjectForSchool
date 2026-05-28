# Emre - Gelişim Adımları

Bu dosya Emre'nin yapası gereken adımları ve güncel ilerlemesini takip eder. Her adımdan sonra yapay zeka tarafından güncellenmelidir.

---

## 📋 İlerleme Durumu

- [ ] **Adım 1: Oyuncu Karakter Sınıfı**
  - **Yapay Zeka Görevi:** `Oyuncu.java` sınıfını oluşturmak. Can (Sağlık), Hız, Seviye, Deneyim ve Konum (x, y) değişkenlerini tanımlamak. Andaç'ın yazdığı klavye kontrolcüsüne göre hareket metodunu yazmak. Çapraz hareket hızı dengelemesini (`hiz * 0.707`) eklemek.
  - **Emre'nin Görevi:** Oyuncu karakterinin ekrandaki hareketini kontrol etmek ve hareket sınırlarını test etmek.
  - *Durum:* Henüz başlanmadı.

- [ ] **Adım 2: Temel Düşman Sınıfı ve Takip Yapay Zekası**
  - **Yapay Zeka Görevi:** `Dusman.java` sınıfını yazmak. Düşmanın oyuncuya doğru doğrudan yürümesini sağlayan basit vektörel takip kodunu hazırlamak.
  - **Emre'nin Görevi:** Düşmanların oyuncuya doğru yönelip yönelmediğini ve oyuncunun peşini bırakıp bırakmadığını doğrulamak.
  - *Durum:* Henüz başlanmadı.

- [ ] **Adım 3: Düşman Çeşitlerinin Oluşturulması**
  - **Yapay Zeka Görevi:** Hızlı Düşman (`HizliDusman.java`) ve Golem Boss (`GolemDusman.java`) sınıflarını `Dusman` sınıfından kalıtım alarak yazmak. Hız ve can değerlerini ayarlamak.
  - **Emre'nin Görevi:** Farklı düşman türlerinin hız ve boyutlarının oyunda doğru yansıdığını test etmek.
  - *Durum:* Henüz başlanmadı.

- [ ] **Adım 4: Düşman Dalga Üreticisi**
  - **Yapay Zeka Görevi:** `DusmanUretici.java` sınıfını yazmak. Oyuncunun o anki görüş alanının hemen dışında (kamera sınırlarına bağlı olarak) zamanla artan sayılarda düşman doğmasını (spawn) sağlamak. Zamanla düşman statlarını (can ve hız) yüzdelik olarak artırmak.
  - **Emre'nin Görevi:** Düşmanların ekran dışında doğup doğmadığını ve zaman geçtikçe düşmanların güçlendiğini gözlemlemek.
  - *Durum:* Henüz başlanmadı.

- [ ] **Adım 5: Karakter Görsellerinin Pixel Tasarımları**
  - **Yapay Zeka Görevi:** Karakterlerin ve düşmanların çizim metotlarını pixel art tarzı resimleri (`BufferedImage`) yükleyip çizecek şekilde güncellemek. Resim yüklenemezse çalışacak geometrik yedek çizim mekanizmasını yazmak.
  - **Emre'nin Görevi:** Oyuncu ve düşmanlar için 2D pixel görselleri (png) hazırlayıp projenin `res/` klasörüne yerleştirmek.
  - *Durum:* Henüz başlanmadı.
