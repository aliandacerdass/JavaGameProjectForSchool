# Andaç - Gelişim Adımları

Bu dosya Andaç'ın yapması gereken adımları ve güncel ilerlemesini takip eder. Her adımdan sonra yapay zeka tarafından güncellenmelidir.

---

## 📋 İlerleme Durumu

- [ ] **Adım 1: Temel Motor ve Pencere Kurulumu**
  - **Yapay Zeka Görevi:** `Pencere.java` ve `OyunPaneli.java` sınıflarını oluşturmak. Panel çizim metotlarını (`paintComponent`) hazırlamak.
  - **Andaç'ın Görevi:** Kodu test etmek, pencerenin ekranda doğru boyutta açıldığını doğrulamak.
  - *Durum:* Henüz başlanmadı.

- [ ] **Adım 2: Klavye Kontrol Mekanizması**
  - **Yapay Zeka Görevi:** `TusKontrolcu.java` sınıfını oluşturmak. Boolean dizi (`tuslar[256]`) kullanarak tuş basışlarını kaydetmek.
  - **Andaç'ın Görevi:** Tuş girdilerinin (WASD veya Yön Tuşları) gecikmesiz algılandığını kontrol etmek.
  - *Durum:* Henüz başlanmadı.

- [ ] **Adım 3: Swing Timer Tabanlı Oyun Döngüsü**
  - **Yapay Zeka Görevi:** `OyunDongusu.java` sınıfını (60 FPS hızında çalışan `javax.swing.Timer` ile) kurmak ve panel güncelleme tetikleyicilerini yazmak.
  - **Andaç'ın Görevi:** Oyunun stabil 60 FPS çalıştığını ve CPU'yu aşırı tüketmediğini doğrulamak.
  - *Durum:* Henüz başlanmadı.

- [ ] **Adım 4: Kayan Kamera Sistemi**
  - **Yapay Zeka Görevi:** Oyuncunun konumuna göre ekranı kaydıran kamera koordinat sistemini yazmak ve `Graphics2D.translate(-kameraX, -kameraY)` yöntemini oyun paneline entegre etmek. Harita sınırlarını belirlemek (örn: 2000x2000 piksel).
  - **Andaç'ın Görevi:** Oyuncu hareket ettikçe ekranın ve arka planın pürüzsüzce kaydığını test etmek.
  - *Durum:* Henüz başlanmadı.

- [ ] **Adım 5: Daire Tabanlı Çarpışma Denetleyici**
  - **Yapay Zeka Görevi:** `CarpismaDenetleyici.java` sınıfını yazmak. Oyuncu-Düşman ve Mermi-Düşman arasındaki daire çarpışma mantığını (`(dx*dx + dy*dy) < (r1+r2)*(r1+r2)`) kodlamak.
  - **Andaç'ın Görevi:** Çarpışmaların doğru mesafelerde tetiklendiğini doğrulamak.
  - *Durum:* Henüz başlanmadı.

- [ ] **Adım 6: Pixel Arka Plan Çizimi ve Ortak Entegrasyon**
  - **Yapay Zeka Görevi:** Haritanın zeminini retro/pixel dokularla veya grid şeklinde çizmek. Emre ve Gizem'in kodlarını ana sisteme bağlamak.
  - **Andaç'ın Görevi:** `res/` klasörü altına pixel arka plan ve yer karosu (tile) görsellerini yerleştirmek.
  - *Durum:* Henüz başlanmadı.
