# Andaç - Gelişim Adımları

Bu dosya Andaç'ın yapması gereken adımları ve güncel ilerlemesini takip eder. Her adımdan sonra yapay zeka tarafından güncellenmelidir.

---

## 📋 İlerleme Durumu

- [x] **Adım 1: Temel Motor ve Pencere Kurulumu**
  - **Yapay Zeka Görevi:** `Pencere.java`, `OyunPaneli.java`, `OyunDurumu.java` ve `GorselYukleyici.java` sınıflarını oluşturmak. Panel çizim metotlarını (`paintComponent`) hazırlamak.
  - **Andaç'ın Görevi:** Kodu test etmek, pencerenin ekranda doğru boyutta açıldığını doğrulamak.
  - *Durum:* Yapay zeka görevi tamamlandı. Andaç'ın test edip onaylaması bekleniyor (AI Blocker).

- [x] **Adım 2: Klavye ve Fare Kontrol Mekanizması**
  - **Yapay Zeka Görevi:** `TusKontrolcu.java` ve `FareKontrolcu.java` sınıflarını oluşturmak. Boolean dizi (`tuslar[256]`) kullanarak tuş basışlarını kaydetmek, fare tıklamalarını saklamak.
  - **Andaç'ın Görevi:** Tuş girdilerinin (WASD) ve fare tıklamalarının gecikmesiz algılandığını kontrol etmek.
  - *Durum:* Yapay zeka görevi tamamlandı. Andaç'ın test edip onaylaması bekleniyor (AI Blocker).

- [x] **Adım 3: Swing Timer Tabanlı Oyun Döngüsü**
  - **Yapay Zeka Görevi:** `OyunDongusu.java` sınıfını (60 FPS hızında çalışan `javax.swing.Timer` ile) kurmak ve panel güncelleme tetikleyicilerini yazmak.
  - **Andaç'ın Görevi:** Oyunun stabil 60 FPS çalıştığını ve CPU'yu aşırı tüketmediğini doğrulamak.
  - *Durum:* Tamamlandı ve onaylandı.

- [x] **Adım 4: Kayan Kamera Sistemi ve Harita Sınırları**
  - **Yapay Zeka Görevi:** Oyuncunun konumuna göre ekranı kaydıran kamera koordinat sistemini yazmak, `Graphics2D.translate(-kameraX, -kameraY)` yöntemini oyun paneline entegre etmek. 3000x3000px boyutlarında harita sınırları tanımlamak ve oyuncunun bu sınırların dışına çıkmasını engellemek.
  - **Andaç'ın Görevi:** Oyuncu hareket ettikçe ekranın pürüzsüzce kaydığını ve harita sınırlarına çarptığında durduğunu test etmek.
  - *Durum:* Tamamlandı ve onaylandı.

- [x] **Adım 5: Daire Tabanlı Çarpışma Denetleyici ve Yeniden Başlatma (Restart)**
  - **Yapay Zeka Görevi:** `CarpismaDenetleyici.java` sınıfını yazmak (daire çarpışma formülüyle). Oyuncu öldüğünde (canı sıfırlandığında) oyunu durduran ve klavyeden `R` tuşuna basıldığında düşman listesini, mermileri, oyuncu canını ve süreyi sıfırlayan `oyunuSifirla()` yapısını kurmak.
  - **Andaç'ın Görevi:** Çarpışmaların doğru çalıştığını ve öldükten sonra `R` tuşuyla oyunun sorunsuz sıfırlanıp baştan başladığını test etmek.
  - *Durum:* Tamamlandı ve onaylandı.

- [/] **Adım 6: Pixel Arka Plan Çizimi, Efektler ve Ortak Entegrasyon**
  - **Yapay Zeka Görevi:** Haritanın zeminini `assets/` içerisindeki piksel bataklık/yer karoları (`Dark_Swamp_Starter_Pack_v1.0`) ile kaplayarak (tiling) çizmek, piksel netliğini (Interpolation rendering hints) korumak. `GorselYukleyici.java` içine programatik gri tonlama ve renklendirme filtreleri eklemek.
  - **Andaç'ın Görevi:** Zemin kaplaması ve renklendirme efektlerinin sorunsuz çalıştığını doğrulamak.
  - *Durum:* Görsel yükleyici ve zemin çizimleri güncellendi, efektler kodlanıyor (AI Blocker).
