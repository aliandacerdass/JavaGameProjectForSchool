# Gizem - Gelişim Adımları

Bu dosya Gizem'in yapması gereken adımları ve güncel ilerlemesini takip eder. Her adımdan sonra yapay zeka tarafından güncellenmelidir.

---

## 📋 İlerleme Durumu

- [x] **Adım 1: Temel Silah ve Mermi Sınıfları**
  - **Yapay Zeka Görevi:** `Silah.java` ve `Mermi.java` sınıflarını tasarlamak. Merminin uçuş açısı, hızı ve hasar değişkenlerini atamak.
  - **Gizem'in Görevi:** Kodların genel yapısını incelemek ve projenin geri kalanıyla olan bağımlılıklarını kontrol etmek.
  - *Durum:* Yapay zeka görevi tamamlandı. Gizem'in kod yapısını inceleyip onaylaması bekleniyor (AI Blocker).

- [x] **Adım 2: Ateş Topu ve Döner Bıçak Mekanikleri**
  - **Yapay Zeka Görevi:** `AtesTopu.java` (en yakın düşmanı hedef alan otomatik fırlatma) ve `DonerBicak.java` (oyuncu etrafında trigonometrik olarak dönen) silahlarını `Silah` sınıfından türeterek yazmak.
  - **Gizem'in Görevi:** Silahların otomatik olarak tetiklendiğini ve mermilerin doğru yönde hareket ettiğini test etmek.
  - *Durum:* Yapay zeka görevi tamamlandı. Gizem'in test edip onaylaması bekleniyor (AI Blocker).

- [x] **Adım 3: Deneyim Kristali ve Mıknatıs Sistemi**
  - **Yapay Zeka Görevi:** `DeneyimKristali.java` sınıfını yazmak. Düşman öldüğünde doğmasını ve oyuncu belirli bir menzile yaklaştığında (`toplamaMenzili`) oyuncuya doğru hızlanarak çekilmesini sağlamak.
  - **Gizem'in Görevi:** Kristallerin doğru şekilde düştüğünü ve oyuncuya pürüzsüzce çekilip toplandığını doğrulamak.
  - *Durum:* Yapay zeka görevi tamamlandı. Gizem'in test edip onaylaması bekleniyor (AI Blocker).

- [x] **Adım 4: Seviye Atlama Menüsü (Kart Arayüzü)**
  - **Yapay Zeka Görevi:** Seviye atlandığında oyunu duraklatıp ekranda 3 adet rastgele geliştirme seçeneği (Silah geliştir, Can doldur, Hızlan vb.) sunan `SeviyeArayuzu.java` kart arayüzünü kodlamak.
  - **Gizem'in Görevi:** Kartlara tıklandığında oyunun kaldığı yerden devam ettiğini ve seçilen yükseltmenin oyuncuya uygulandığını test etmek.
  - *Durum:* Yapay zeka görevi tamamlandı. Gizem'in test edip onaylaması bekleniyor (AI Blocker).

- [x] **Adım 5: Arayüz (UI) ve Pixel Detayları**
  - **Yapay Zeka Görevi:** Ekranın üst kısmında pixel tarzı Can Barı, Seviye Çubuğu ve kronometre çizimlerini kodlamak. Menü butonlarını piksel temalı tasarlamak.
  - **Gizem'in Görevi:** UI elemanları için piksel fontları veya menü arka plan görsellerini projenin `res/` klasörüne eklemek.
  - *Durum:* Tamamlandı ve onaylandı.

- [ ] **Adım 6: Havada Uçan Hasar Sayıları (Floating Damage Numbers) ve Yeni Savunma Silahı**
  - **Yapay Zeka Görevi:** Düşmanlar hasar aldığında kafalarının üzerinde kırmızı hasar sayılarının havaya doğru yükselip kaybolmasını (floating numbers) sağlayan parçacık sistemini yazmak. Oyuncunun etrafında dönen ve mermileri engelleyen Kalkan (KalkanSilahi) silahını eklemek.
  - **Gizem'in Görevi:** Hasar sayılarının ekranda pürüzsüzce kaybolduğunu doğrulamak ve kalkan yükseltmesini test etmek.
  - *Durum:* Tasarım aşamasında.
