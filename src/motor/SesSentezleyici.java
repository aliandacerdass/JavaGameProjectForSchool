package motor;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;

// SesSentezleyici sinifi, harici ses dosyalarina ihtiyac duymadan
// Java Sound API'si ile tamamen retro 8-bit sesleri anlik sentezleyip calar (Andac)
public class SesSentezleyici {
    
    // Global ses seviyesi (0.0 ile 1.0 arasinda) (Andaç)
    public static float sesSeviyesi = 0.5f;
    
    // Ham ses verisini byte formatina cevirip ses kartina gonderen yardimci thread metodu
    private static void sesCal(float[] data) {
        new Thread(() -> {
            try {
                // 16-bit PCM Mono için her float veri 2 byte yer kaplar
                byte[] byteData = new byte[data.length * 2];
                for (int i = 0; i < data.length; i++) {
                    // Genligi 16-bit short sinirlarina olcekler (-32768 ile 32767 arasi)
                    short val = (short) (data[i] * 32767);
                    // Little-endian formatinda byte dizisine yazar
                    byteData[i * 2] = (byte) (val & 0xff);
                    byteData[i * 2 + 1] = (byte) ((val >> 8) & 0xff);
                }
                
                // 44.1kHz ornekleme hizi, 16-bit cozunurluk, mono kanal, signed, little-endian format tanimi
                AudioFormat format = new AudioFormat(44100, 16, 1, true, false);
                AudioInputStream ais = new AudioInputStream(
                        new ByteArrayInputStream(byteData), format, data.length);
                
                DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
                SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
                line.open(format);
                line.start();
                
                byte[] buffer = new byte[1024];
                int read;
                while ((read = ais.read(buffer)) != -1) {
                    line.write(buffer, 0, read);
                }
                
                line.drain();
                line.close();
            } catch (Exception e) {
                // Ses aygiti bulunamamasi durumunda oyunun cökmesini engeller
                System.out.println("Ses calinirken hata: " + e.getMessage());
            }
        }).start();
    }
    
    // Ates topu firlatilma sesi (Aşagi dogru frekans suzulmesi - Pew!)
    public static void atesTopu() {
        int orneklemeHizi = 44100;
        float sure = 0.12f; // 120 milisaniye
        int ornekSayisi = (int) (orneklemeHizi * sure);
        float[] veri = new float[ornekSayisi];
        
        for (int i = 0; i < ornekSayisi; i++) {
            float t = (float) i / orneklemeHizi;
            // Frekans 750 Hz'den 250 Hz'e dogru duser
            float frekans = 750.0f - (t / sure) * 500.0f;
            float faz = 2.0f * (float) Math.PI * frekans * t;
            // Sinus dalgasi kullanilir ve sonuna dogru yavasca silinir (fade out) - sesSeviyesi ile carpilir (Andaç)
            veri[i] = (float) Math.sin(faz) * 0.12f * sesSeviyesi * (1.0f - (float) i / ornekSayisi);
        }
        sesCal(veri);
    }
    
    // Karakterin hasar alma sesi (Alcak frekansli gurultulu patlama)
    public static void hasar() {
        int orneklemeHizi = 44100;
        float sure = 0.14f; // 140 milisaniye
        int ornekSayisi = (int) (orneklemeHizi * sure);
        float[] veri = new float[ornekSayisi];
        java.util.Random rand = new java.util.Random();
        
        for (int i = 0; i < ornekSayisi; i++) {
            float t = (float) i / orneklemeHizi;
            // 60 Hz ile 140 Hz arasinda degisen alcak gurultu frekansi
            float frekans = 60.0f + rand.nextFloat() * 80.0f;
            float faz = 2.0f * (float) Math.PI * frekans * t;
            // Gurultulu etki icin sinus dalgasina beyaz gurultu karistirilir - sesSeviyesi ile carpilir (Andaç)
            veri[i] = ((float) Math.sin(faz) + (rand.nextFloat() - 0.5f) * 0.6f) * 0.15f * sesSeviyesi * (1.0f - (float) i / ornekSayisi);
        }
        sesCal(veri);
    }
    
    // Guc meyvesi toplama sesi (Yukari dogru hizli suzulme - Blip!)
    public static void gucMeyvesi() {
        int orneklemeHizi = 44100;
        float sure = 0.15f;
        int ornekSayisi = (int) (orneklemeHizi * sure);
        float[] veri = new float[ornekSayisi];
        
        for (int i = 0; i < ornekSayisi; i++) {
            float t = (float) i / orneklemeHizi;
            // Frekans 350 Hz'den 950 Hz'e dogru firlar
            float frekans = 350.0f + (t / sure) * 600.0f;
            float faz = 2.0f * (float) Math.PI * frekans * t;
            // sesSeviyesi ile carpilir (Andaç)
            veri[i] = (float) Math.sin(faz) * 0.10f * sesSeviyesi * (1.0f - (float) i / ornekSayisi);
        }
        sesCal(veri);
    }
    
    // Seviye atlama kutlama sesi (Retro 8-bit 4 notalik arpej)
    public static void seviyeAtla() {
        int orneklemeHizi = 44100;
        float sure = 0.45f;
        int ornekSayisi = (int) (orneklemeHizi * sure);
        float[] veri = new float[ornekSayisi];
        
        // Do4, Mi4, Sol4, Do5 notalarinin frekanslari (C Major arpej)
        float[] notalar = { 261.63f, 329.63f, 392.00f, 523.25f };
        
        for (int i = 0; i < ornekSayisi; i++) {
            float t = (float) i / orneklemeHizi;
            // Zaman icinde sırayla 4 notayi calar
            int notaIndis = (int) ((t / sure) * notalar.length);
            notaIndis = Math.min(notalar.length - 1, notaIndis);
            float frekans = notalar[notaIndis];
            float faz = 2.0f * (float) Math.PI * frekans * t;
            // sesSeviyesi ile carpilir (Andaç)
            veri[i] = (float) Math.sin(faz) * 0.12f * sesSeviyesi * (1.0f - (float) i / ornekSayisi);
        }
        sesCal(veri);
    }
    
    // Oyun bitti sesi (Sad descending arpeggio)
    public static void oyunBitti() {
        int orneklemeHizi = 44100;
        float sure = 0.60f;
        int ornekSayisi = (int) (orneklemeHizi * sure);
        float[] veri = new float[ornekSayisi];
        
        // La3, Fa3, Re3 (Sad descending minor chord)
        float[] notalar = { 220.00f, 174.61f, 146.83f };
        
        for (int i = 0; i < ornekSayisi; i++) {
            float t = (float) i / orneklemeHizi;
            int notaIndis = (int) ((t / sure) * notalar.length);
            notaIndis = Math.min(notalar.length - 1, notaIndis);
            float frekans = notalar[notaIndis];
            float faz = 2.0f * (float) Math.PI * frekans * t;
            // sesSeviyesi ile carpilir (Andaç)
            veri[i] = (float) Math.sin(faz) * 0.15f * sesSeviyesi * (1.0f - (float) i / ornekSayisi);
        }
        sesCal(veri);
    }
}
