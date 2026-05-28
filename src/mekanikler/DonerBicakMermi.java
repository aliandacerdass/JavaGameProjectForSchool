package mekanikler;

import varliklar.Oyuncu;
import java.awt.Graphics2D;
import java.awt.Color;

// DonerBicakMermi sinifi, normal mermilerden farkli olarak oyuncunun etrafinda trigonometrik donus yapar
public class DonerBicakMermi extends Mermi {
    // Bagli oldugu ve etrafinda donecegi oyuncu referansi
    private Oyuncu oyuncu;
    
    // Merminin oyuncu merkezine olan uzakligi (donus yariçapi)
    private double yorungeYaricapi;
    
    // Merminin radyan cinsinden mevcut acisi (0 - 2*PI arasinda degisir)
    private double aci;
    
    // Her guncellemede acinin ne kadar artacagini belirleyen donus hizi (radyan cinsinden)
    private double donusHizi;
    
    // Kurucu metot: Donen bicak mermisini oyuncu baglantisi ve parametreleriyle baslatir
    public DonerBicakMermi(Oyuncu oyuncu, double yorungeYaricapi, double donusHizi, double hasar, double yariCap, double maksimumMenzil, double baslangicAcisi) {
        // Ust sinifin kurucu metodunu cagirir (yon ve mermi hizi donus formuluyle hesaplanacagindan ilk etapta 0 verilir)
        super(oyuncu.x, oyuncu.y, 0.0, 0.0, 0.0, hasar, yariCap, maksimumMenzil);
        
        // Oyuncu baglantisini saklar
        this.oyuncu = oyuncu;
        // Donus yorunge yariçapini atar
        this.yorungeYaricapi = yorungeYaricapi;
        // Donus hizini atar
        this.donusHizi = donusHizi;
        // Merminin baslangic acisini atar (ornegin coklu bicaklarda farkli acilar vermek icin)
        this.aci = baslangicAcisi;
    }
    
    // Donen bicagin konumunu oyuncuya gore trigonometrik olarak gunceller
    @Override
    public void guncelle() {
        // Eger mermi aktif degilse hicbir guncelleme yapmaz
        if (!aktif) {
            return;
        }
        
        // Acıyı donus hizina gore artirir
        this.aci += this.donusHizi;
        
        // Acı 360 dereceyi (2*PI) gecerse degeri sifirlayarak tasmayi onler
        if (this.aci >= Math.PI * 2) {
            this.aci -= Math.PI * 2;
        }
        
        // Trigonometrik formullerle oyuncunun etrafindaki dairesel X ve Y koordinatlarini hesaplar
        // x = oyuncu_merkez_x + cos(aci) * yaricap
        // y = oyuncu_merkez_y + sin(aci) * yaricap
        this.x = oyuncu.x + Math.cos(this.aci) * this.yorungeYaricapi;
        this.y = oyuncu.y + Math.sin(this.aci) * this.yorungeYaricapi;
        
        // Donus esnasinda kat edilen hayali mesafeyi artirir
        // Bu mesafe maksimum menzile ulastiginda bicak yok olacaktir
        this.katEdilenMesafe += Math.abs(this.donusHizi) * 40.0;
        
        // Menzil asildiysa mermiyi pasif hale getirir
        if (this.katEdilenMesafe >= this.maksimumMenzil) {
            this.aktif = false;
        }
    }
    
    // Donen bicagin cizim metodu (Piksel tarzi metalik bir bicak rengi: Mavi-Gri)
    @Override
    public void ciz(Graphics2D g2) {
        // Eger aktif degilse cizim yapmaz
        if (!aktif) {
            return;
        }
        
        // Donen bicagin rengini Cyan (Turkuaz) yapar (oyunda belirgin durmasi icin)
        g2.setColor(Color.CYAN);
        
        // Bicagin sol ust cizim koordinatlarini hesaplar
        int cizimX = (int) (this.x - this.yariCap);
        int cizimY = (int) (this.y - this.yariCap);
        int cap = (int) (this.yariCap * 2);
        
        // Oval biciminde (daire) cizer
        g2.fillOval(cizimX, cizimY, cap, cap);
        
        // Metalik efekt katmak icin icine daha kucuk beyaz bir daire cizer
        g2.setColor(Color.WHITE);
        int icCap = (int) (this.yariCap);
        int icCizimX = (int) (this.x - (this.yariCap / 2));
        int icCizimY = (int) (this.y - (this.yariCap / 2));
        g2.fillOval(icCizimX, icCizimY, icCap, icCap);
    }
}
