import javax.swing.*;
import java.awt.*;

// Klasa reprezentująca ramkę do wizualizacji danych
public class VisualizationFrame extends JFrame {
    private char[][] dataArray; // Tablica przechowująca dane do wizualizacji
    private PanelHandler panelHandler = new PanelHandler(this); // Obiekt do obsługi paneli

    // Metoda do zresetowania wizualizacji z nowymi danymi
    public void resetVisualization(char[][] dataArray, JFrame frame) {
        this.dataArray = dataArray; // Ustawienie nowych danych
        if (dataArray != null) {
            displayVisualization(frame); // Wyświetlenie wizualizacji
        }
    }

    // Metoda do wyświetlenia wizualizacji
    public void displayVisualization(JFrame frame) {
        if (dataArray == null || dataArray.length == 0) {
            JOptionPane.showMessageDialog(frame, "Plik nie został podany lub jest pusty.", "Error", JOptionPane.ERROR_MESSAGE); // Komunikat o braku danych
            return;
        }

        getContentPane().removeAll(); // Usunięcie poprzedniej zawartości
        setLayout(new GridLayout(dataArray.length, dataArray[0].length)); // Ustawienie siatki o odpowiednim rozmiarze
        fillPanels(); // Wypełnienie panelami z danymi

        pack(); // Dostosowanie rozmiaru ramki do zawartości
        setLocationRelativeTo(null); // Wyśrodkowanie ramki na ekranie
        setVisible(true); // Wyświetlenie ramki
    }

    // Metoda do wypełnienia ramki panelami z danymi
    private void fillPanels() {
        int minPanelSize = 20;  // Minimalna wielkość panelu
        int panelWidth = Math.max(minPanelSize, 800 / dataArray[0].length); // Obliczenie szerokości panelu
        int panelHeight = Math.max(minPanelSize, 800 / dataArray.length); // Obliczenie wysokości panelu

        for (int i = 0; i < dataArray.length; i++) {
            for (int j = 0; j < dataArray[0].length; j++) {
                JPanel panel = panelHandler.createPanel(dataArray[i][j], i, j); // Utworzenie panelu z danymi
                panel.setPreferredSize(new Dimension(panelWidth, panelHeight)); // Ustawienie preferowanej wielkości panelu
                add(panel); // Dodanie panelu do ramki
            }
        }
        validate(); // Walidacja komponentów
        repaint(); // Odrysowanie ramki
    }

    // Metoda do aktualizacji danych w konkretnym panelu
    public void updatePanel(int i, int j, char type) {
        dataArray[i][j] = type; // Aktualizacja danych w tablicy
        int index = i * dataArray[0].length + j; // Obliczenie indeksu panelu w kontenerze
        JPanel panel = (JPanel) getContentPane().getComponent(index); // Pobranie panelu na podstawie indeksu
        panelHandler.updatePanelColor(panel, type); // Aktualizacja koloru panelu
        panel.repaint();  // Odśwież tylko zmodyfikowany panel
    }

    // Metoda zwracająca dane wizualizacji
    public char[][] getDataArray() {
        return dataArray;
    }
}
