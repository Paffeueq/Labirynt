import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// Klasa do obsługi paneli w ramce wizualizacji
public class PanelHandler {
    private VisualizationFrame frame; // Ramka wizualizacji, do której należą panele

    // Konstruktor przyjmujący ramkę wizualizacji jako argument
    public PanelHandler(VisualizationFrame frame) {
        this.frame = frame; // Inicjalizacja ramki wizualizacji
    }

    // Metoda do tworzenia panelu z danymi
    public JPanel createPanel(char cellType, int row, int col) {
        JPanel panel = new JPanel(); // Utworzenie nowego panelu
        updatePanelColor(panel, cellType); // Ustawienie koloru panelu na podstawie typu komórki
        // Dodanie nasłuchiwacza zdarzeń dla kliknięcia myszą
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handlePanelClick(row, col); // Obsługa kliknięcia na panel
            }
        });
        return panel; // Zwrócenie utworzonego panelu
    }

    // Metoda do obsługi kliknięcia na panel
    private void handlePanelClick(int i, int j) {
        char currentType = frame.getDataArray()[i][j]; // Pobranie typu komórki z danych wizualizacji
        Point pLocation = findPoint('P'); // Znalezienie pozycji 'P' w danych
        Point kLocation = findPoint('K'); // Znalezienie pozycji 'K' w danych

        // Logika obsługi kliknięcia w zależności od aktualnego typu komórki
        if (currentType == ' ' && (pLocation == null || kLocation == null)) {
            if (pLocation == null) {
                frame.updatePanel(i, j, 'P');  // Ustaw 'P', jeśli nie istnieje
            } else if (kLocation == null) {
                frame.updatePanel(i, j, 'K');  // Ustaw 'K', jeśli 'P' już istnieje, ale nie 'K'
            }
        } else if (currentType == 'P' || currentType == 'K') {
            frame.updatePanel(i, j, ' ');  // Usuń 'P' lub 'K' po kliknięciu na nich
        }
    }

    // Metoda do znajdowania pozycji danego typu komórki w danych wizualizacji
    private Point findPoint(char type) {
        for (int i = 0; i < frame.getDataArray().length; i++) {
            for (int j = 0; j < frame.getDataArray()[i].length; j++) {
                if (frame.getDataArray()[i][j] == type) {
                    return new Point(i, j); // Zwrócenie pozycji komórki
                }
            }
        }
        return null; // Zwrócenie null, jeśli komórka danego typu nie została znaleziona
    }

    // Metoda do aktualizacji koloru panelu na podstawie typu komórki
    public void updatePanelColor(JPanel panel, char cellType) {
        switch (cellType) {
            case 'X':
                panel.setBackground(Color.black); // Ustawienie koloru na czarny dla ściany
                break;
            case 'P':
                panel.setBackground(Color.green); // Ustawienie koloru na zielony dla punktu startowego
                break;
            case 'K':
                panel.setBackground(Color.red); // Ustawienie koloru na czerwony dla punktu końcowego
                break;
            default:
                panel.setBackground(Color.white); // Domyślnie ustawienie koloru na biały
                break;
        }
    }
}
