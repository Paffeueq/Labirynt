import javax.swing.*;
import java.awt.*;
//wizualizacja labiryntu czytanego z pliku tekstowego

public class VisualizationFrame extends JFrame {
    public void displayVisualization(char[][] dataArray, JFrame frame) {
        if (dataArray == null) {
            JOptionPane.showMessageDialog(frame, "Plik nie został podany.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        setLayout(new GridLayout(dataArray.length, dataArray[0].length));
        setPreferredSize(new Dimension(800, 800));  // Ustawienie stałego rozmiaru wizualizacji

        int panelSize = 800 / Math.max(dataArray.length, dataArray[0].length);  // Obliczenie rozmiaru każdego panelu
        fillPanels(dataArray, panelSize);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
//wypełnianie danego kawałka kolorem
    private void fillPanels(char[][] dataArray, int panelSize) {
        for (int i = 0; i < dataArray.length; i++) {
            for (int j = 0; j < dataArray[0].length; j++) {
                JPanel panel = new JPanel();
                panel.setPreferredSize(new Dimension(panelSize, panelSize));  // Ustawienie rozmiaru panelu
                panel.setBackground(getColorFromChar(dataArray[i][j]));
                add(panel);
            }
        }
    }
//ustalenie koloru do poszczególnej wartości tablicy
    private Color getColorFromChar(char c) {
        switch (c) {
            case 'X': return Color.black;
            case ' ': return Color.white;
            case 'P':
            case 'K': return Color.green;
            default:  return Color.gray;
        }
    }
}
