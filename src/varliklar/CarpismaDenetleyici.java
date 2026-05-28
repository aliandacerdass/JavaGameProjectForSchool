package varliklar;

import mekanikler.Mermi;
import mekanikler.DeneyimKristali;
import java.util.ArrayList;

// CarpismaDenetleyici sinifi, oyundaki nesnelerin birbirleriyle olan temaslarini kontrol eder
public class CarpismaDenetleyici {
    
    // Tum carpismalari denetleyen ana metot
    public static void carpismalariDenetle(Oyuncu oyuncu, ArrayList<Dusman> dusmanlar, ArrayList<Mermi> mermiler, ArrayList<DeneyimKristali> kristaller) {
        
        // 1. OYUNCU - DÜŞMAN ÇARPIŞMASI
        for (Dusman dusman : dusmanlar) {
            // Daire tabanli carpisma formulu: (dx * dx) + (dy * dy) < (r1 + r2) * (r1 + r2)
            double dx = oyuncu.x - dusman.x;
            double dy = oyuncu.y - dusman.y;
            double mesafeKaresi = (dx * dx) + (dy * dy);
            double yariCapToplamKaresi = (oyuncu.yariCap + dusman.yariCap) * (oyuncu.yariCap + dusman.yariCap);
            
            // Eger carpisma gerceklestiyse ve oyuncunun canı varsa
            if (mesafeKaresi < yariCapToplamKaresi && oyuncu.can > 0) {
                // Oyuncu hasar alir (Saniyede 60 tick oldugu icin hasar miktarini kucuk bir oranda azaltiriz)
                oyuncu.can -= dusman.hasar / 60.0;
                
                // Oyuncunun caninin sifirin altina dusmesini engeller
                if (oyuncu.can < 0) {
                    oyuncu.can = 0;
                }
            }
        }
        
        // 2. MERMİ - DÜŞMAN ÇARPIŞMASI
        for (Mermi mermi : mermiler) {
            // Eger mermi aktif degilse digerlerine bakmaya gerek yok
            if (!mermi.aktif) {
                continue;
            }
            
            for (Dusman dusman : dusmanlar) {
                // Dusman olmusse carpisma kontrolu yapma
                if (dusman.can <= 0) {
                    continue;
                }
                
                double dx = mermi.x - dusman.x;
                double dy = mermi.y - dusman.y;
                double mesafeKaresi = (dx * dx) + (dy * dy);
                double yariCapToplamKaresi = (mermi.yariCap + dusman.yariCap) * (mermi.yariCap + dusman.yariCap);
                
                // Eger mermi dusmana çarptiysa
                if (mesafeKaresi < yariCapToplamKaresi) {
                    // Dusman canini azalt
                    dusman.can -= mermi.hasar;
                    // Mermiyi pasif yap (yok et)
                    mermi.aktif = false;
                    
                    // Dusmani merminin gelis yonunde geriye dogru it (Knockback - Geri Itme)
                    // Geri itilme miktari olarak sabit 25 piksel kullanilir
                    dusman.geriIt(mermi.yonX, mermi.yonY, 25.0);
                    
                    // Eger dusman olduysa
                    if (dusman.can <= 0) {
                        // Dusmanin oldugu yere bir deneyim kristali birak (15 Deneyim Puani degerinde)
                        kristaller.add(new DeneyimKristali(dusman.x, dusman.y, 15.0));
                    }
                    
                    // Mermi yok oldugu icin bu mermi icin baska dusman aramayi sonlandir
                    break;
                }
            }
        }
    }
}
