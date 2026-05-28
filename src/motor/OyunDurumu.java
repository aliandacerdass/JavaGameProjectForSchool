package motor;

// Oyunun anlik durumunu belirten Enum yapisi
public enum OyunDurumu {
    MENU,       // Ana menu ekrani
    OYUN,       // Aktif oynanis ekrani
    GELISIM,    // Seviye atlandiginda secim ekrani (oyun duraklatilir)
    OYUN_BITTI  // Oyuncunun caninin bittigi ekran
}
