# Emre - Gelişim Adımları

Bu dosya Emre'nin yapası gereken adımları ve güncel ilerlemesini takip eder. Her adımdan sonra yapay zeka tarafından güncellenmelidir.

---

## 📋 İlerleme Durumu

- [x] **Adım 1: Oyuncu Karakter Sınıfı**
  - **Yapay Zeka Görevi:** `Oyuncu.java` sınıfını oluşturmak. Can (Sağlık), Hız, Seviye, Deneyim ve Konum (x, y) değişkenlerini tanımlamak. Andaç'ın yazdığı klavye kontrolcüsüne göre hareket metodunu yazmak. Çapraz hareket hızı dengelemesini (`hiz * 0.707`) eklemek.
  - **Emre'nin Görevi:** Oyuncu karakterinin ekrandaki hareketini kontrol etmek ve hareket sınırlarını test etmek.
  - *Durum:* Yapay zeka görevi tamamlandı. Emre'nin test edip onaylaması bekleniyor (AI Blocker).

- [x] **Adım 2: Temel Düşman Sınıfı, Takip ve Geri İtme (Knockback)**
  - **Yapay Zeka Görevi:** `Dusman.java` sınıfını yazmak. Düşmanın oyuncuya doğru doğrudan yürümesini sağlayan basit vektörel takip kodunu hazırlamak. Hasar aldığında merminin geliş yönünün tersine doğru geriye itilmesini sağlayan `geriIt(double yonX, double yonY, double miktar)` mekanizmasını eklemek.
  - **Emre'nin Görevi:** Düşmanların oyuncuyu takip ettiğini ve mermiyle vurulduklarında hafifçe geriye savrulduklarını test etmek.
  - *Durum:* Yapay zeka görevi tamamlandı. Emre'nin test edip onaylaması bekleniyor (AI Blocker).

- [x] **Adım 3: Düşman Çeşitlerinin Oluşturulması**
  - **Yapay Zeka Görevi:** Hızlı Düşman (`HizliDusman.java`) ve Golem Boss (`GolemDusman.java`) sınıflarını `Dusman` sınıfından kalıtım alarak yazmak. Hız, can ve geri itilme direnci değerlerini ayarlamak (örn. Golem çok az geri itilecek).
  - **Emre'nin Görevi:** Farklı düşman türlerinin hız ve boyutlarının oyunda doğru yansıdığını test etmek.
  - *Durum:* Yapay zeka görevi tamamlandı. Emre'nin test edip onaylaması bekleniyor (AI Blocker).

- [x] **Adım 4: Düşman Dalga Üreticisi**
  - **Yapay Zeka Görevi:** `DusmanUretici.java` sınıfını yazmak. Oyuncunun o anki görüş alanının hemen dışında (kamera sınırlarına bağlı olarak) zamanla artan sayılarda düşman doğmasını (spawn) sağlamak. Zamanla düşman statlarını (can ve hız) yüzdelik olarak artırmak.
  - **Emre'nin Görevi:** Düşmanların ekran dışında doğup doğmadığını ve zaman geçtikçe düşmanların güçlendiğini gözlemlemek.
  - *Durum:* Yapay zeka görevi tamamlandı. Emre'nin test edip onaylaması bekleniyor (AI Blocker).

- [/] **Adım 5: Animasyonlu Karakter Görselleri ve Düşman Renklendirmeleri**
  - **Yapay Zeka Görevi:** `Heroes99` spritesheet'inden yönlü ve hareket durumuna göre (Idle/Walk) animasyon kareleri kesip oynatacak kodu yazmak. `Monster_Slime` spritesheet'inden hareket animasyonu hazırlamak. Golem (Gri taş rengi) ve Hızlı Düşman (Ateş kırmızısı) için programatik renklendirme uygulayarak görsel varyasyonları oluşturmak.
  - **Emre'nin Görevi:** Oyuncu ve düşmanların yürüme/bekleme animasyonlarını ve Golem/Hızlı Düşman renk varyasyonlarını test edip onaylamak.
  - *Durum:* Görseller yüklendi, animasyonlar ve programatik renklendirme filtreleri kodlanıyor (AI Blocker).
