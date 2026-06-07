package varliklar;

import mekanikler.Mermi;
import mekanikler.DeneyimKristali;
import mekanikler.HasarSayisi;
import motor.OyunPaneli;
import java.awt.Color;
import java.util.ArrayList;

// CarpismaDenetleyici sinifi, oyundaki nesnelerin birbirleriyle olan temaslarini kontrol eder
public class CarpismaDenetleyici {
    
    // Tum carpismalari denetleyen ana metot (Andac refaktoruyla OyunPaneli context'i uzerinden calisir)
    public static void carpismalariDenetle(OyunPaneli panel) {
        Oyuncu oyuncu = panel.oyuncu;
        ArrayList<Dusman> dusmanlar = panel.dusmanlar;
        ArrayList<Mermi> mermiler = panel.mermiler;
        ArrayList<DeneyimKristali> kristaller = panel.kristaller;
        
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
                
                // Hasar sesini calar, ekran sarsintisini ve kirmizi flasi tetikler (Andaç)
                if (panel.oyunSuresiKareSayisi % 22 == 0) {
                    motor.SesSentezleyici.hasar();
                    panel.hasarFlasiTetikle();
                }
                
                // --- İÇ İÇE GİRMEYİ ENGELLEME VE GERİ SEKME (PUSHBACK) ---
                double mesafe = Math.sqrt(mesafeKaresi);
                if (mesafe > 0) {
                    double overlap = (oyuncu.yariCap + dusman.yariCap) - mesafe;
                    // Birbirlerinden uzaklasma yonunde itme vektorunu hesaplariz
                    double itmeX = (dx / mesafe) * overlap;
                    double itmeY = (dy / mesafe) * overlap;
                    
                    // Oyuncuyu geriye sekme hissiyati vermek icin %60 oraninda geriye iteriz
                    oyuncu.x += itmeX * 0.6;
                    oyuncu.y += itmeY * 0.6;
                    
                    // Dusmani da geriye dogru %40 oraninda ve kendi geri itilme direncine gore iteriz (Andac)
                    dusman.x -= itmeX * 0.4 * dusman.geriItmeCarpani;
                    dusman.y -= itmeY * 0.4 * dusman.geriItmeCarpani;
                    
                    // Harita sinirlari disina tasmalarini engelleriz (Andaç)
                    oyuncu.x = Math.max(0, Math.min(oyuncu.x, 3000));
                    oyuncu.y = Math.max(0, Math.min(oyuncu.y, 3000));
                    dusman.x = Math.max(0, Math.min(dusman.x, 3000));
                    dusman.y = Math.max(0, Math.min(dusman.y, 3000));
                } else {
                    // Tam olarak ust uste bindilerse ufak bir itisle ayiririz
                    oyuncu.x += 1;
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
                
                double dx = dusman.x - mermi.x; // Mermiden dusmana dogru olan vektor (Andaç)
                double dy = dusman.y - mermi.y;
                double mesafeKaresi = (dx * dx) + (dy * dy);
                double yariCapToplamKaresi = (mermi.yariCap + dusman.yariCap) * (mermi.yariCap + dusman.yariCap);
                
                // Eger mermi dusmana çarptiysa
                if (mesafeKaresi < yariCapToplamKaresi) {
                    // Dönen bıçak kontrolü (Andaç)
                    if (mermi instanceof mekanikler.DonerBicakMermi) {
                        mekanikler.DonerBicakMermi dbMermi = (mekanikler.DonerBicakMermi) mermi;
                        long suAn = System.currentTimeMillis();
                        
                        // Düşman bu bıçaktan hasar alabilir durumdaysa (cooldown dolduysa)
                        if (dbMermi.dusmanaVurabilirMi(dusman, suAn)) {
                            dusman.can -= mermi.hasar;
                            dbMermi.vurulanDusmaniEkle(dusman, suAn);
                            
                            // Ekrana hasar sayisi firlatir (Turkuaz renkli bicak hasari - Andaç)
                            panel.hasarSayilari.add(new HasarSayisi(dusman.x, dusman.y - 12, String.format("-%.0f", mermi.hasar), Color.CYAN));
                            
                            // Düşmanı bıçağın çarptığı yönde (mermidir dışarı doğru) geriye it (Andaç)
                            double mesafe = Math.sqrt(mesafeKaresi);
                            if (mesafe > 0) {
                                dusman.geriIt(dx / mesafe, dy / mesafe, 15.0);
                            }
                        }
                    } else {
                        // Normal mermi (Ateş topu vb.): Çarptığında yok olur
                        dusman.can -= mermi.hasar;
                        mermi.aktif = false;
                        
                        // Ekrana hasar sayisi firlatir (Turuncu renkli ates topu hasari - Andaç)
                        panel.hasarSayilari.add(new HasarSayisi(dusman.x, dusman.y - 12, String.format("-%.0f", mermi.hasar), Color.ORANGE));
                        
                        // Düşmanı merminin gidiş yönünde geriye it (Knockback)
                        dusman.geriIt(mermi.yonX, mermi.yonY, 25.0);
                        
                        // Mermi yok olduğu için aramayı kes
                        break;
                    }
                }
            }
        }
    }
}
