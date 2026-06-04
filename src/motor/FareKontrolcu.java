package motor;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// Fare girdilerini kontrol eden ve dinleyen sinif
public class FareKontrolcu extends MouseAdapter {
    
    // Son tiklanan noktanin X koordinati
    private int tiklamaX = -1;
    // Son tiklanan noktanin Y koordinati
    private int tiklamaY = -1;
    // Fare tiklama bilgisini tutan bayrak (flag)
    private boolean tiklandiMi = false;
    
    // Oyun paneli referansi
    private final OyunPaneli panel;
    
    // Kurucu metot: Oyun paneli referansini alir
    public FareKontrolcu(OyunPaneli panel) {
        this.panel = panel;
    }
    
    // Fare tusuna basildiginda tetiklenen metot
    @Override
    public void mousePressed(MouseEvent e) {
        // Tiklanan koordinatlari gunceller
        tiklamaX = e.getX();
        tiklamaY = e.getY();
        // Tiklandi bayragini dogru (true) yapar
        tiklandiMi = true;
        
        // Ekrani yenilemek icin panelin repaint metodunu tetikleriz
        if (panel != null) {
            panel.repaint();
        }
    }
    
    // Tiklama bilgisini sorgulayan ve sorguladiktan sonra sifirlayan metot
    public boolean tiklamayiKontrolEt() {
        boolean sonuc = tiklandiMi;
        // Sorgulama yapildiktan sonra bayragi tekrar yalana (false) cekeriz
        tiklandiMi = false;
        return sonuc;
    }
    
    // Tiklanan X koordinatini getiren metot
    public int getTiklamaX() {
        return tiklamaX;
    }
    
    // Tiklanan Y koordinatini getiren metot
    public int getTiklamaY() {
        return tiklamaY;
    }
    
    // Anlik fare konumu koordinatlari (hover efektleri icin)
    private int fareX = -1;
    private int fareY = -1;

    // Fare hareket ettirildiginde tetiklenen metot (Andaç)
    @Override
    public void mouseMoved(MouseEvent e) {
        fareX = e.getX();
        fareY = e.getY();
        // Hover durumlarinin hemen cizilebilmesi icin repaint tetikleriz
        if (panel != null) {
            panel.repaint();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        fareX = e.getX();
        fareY = e.getY();
        if (panel != null) {
            panel.repaint();
        }
    }

    // Fare X koordinatini getiren metot
    public int getFareX() {
        return fareX;
    }

    // Fare Y koordinatini getiren metot
    public int getFareY() {
        return fareY;
    }

    // Girdileri temizleyen/sifirlayan metot
    public void temizle() {
        tiklamaX = -1;
        tiklamaY = -1;
        tiklandiMi = false;
        fareX = -1;
        fareY = -1;
    }
}
