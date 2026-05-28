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
    
    // Fare tusuna basildiginda tetiklenen metot
    @Override
    public void mousePressed(MouseEvent e) {
        // Tiklanan koordinatlari gunceller
        tiklamaX = e.getX();
        tiklamaY = e.getY();
        // Tiklandi bayragini dogru (true) yapar
        tiklandiMi = true;
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
    
    // Girdileri temizleyen/sifirlayan metot
    public void temizle() {
        tiklamaX = -1;
        tiklamaY = -1;
        tiklandiMi = false;
    }
}
