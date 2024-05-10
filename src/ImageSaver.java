import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

// Klasa do zapisywania wizualizacji jako obrazu
public class ImageSaver {
    // Metoda do zapisywania wizualizacji z ramki JFrame jako obrazu PNG
    public static void saveVisualization(JFrame frame, JFrame parent) {
        // Sprawdzenie, czy ramka jest wyświetlana
        if (frame == null || !frame.isVisible()) {
            JOptionPane.showMessageDialog(parent, "Wizualizacja może być zapisana tylko gdy jest wyświetlana.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Utworzenie okna dialogowego do wyboru pliku i ustawienie filtru na pliki PNG
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG files (*.png)", "png");
        fileChooser.setFileFilter(filter);
        fileChooser.setAcceptAllFileFilterUsed(false);

        // Wyświetlenie okna dialogowego i obsługa wybranego pliku
        if (fileChooser.showSaveDialog(parent) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile(); // Pobranie wybranego pliku
            String filePath = file.getAbsolutePath(); // Pobranie ścieżki do pliku
            if (!filePath.toLowerCase().endsWith(".png")) {
                filePath += ".png"; // Dodanie rozszerzenia .png, jeśli nie zostało podane
            }
            saveFrameAsImage(frame, filePath); // Zapisanie wizualizacji jako obrazu
        }
    }

    // Metoda do zapisywania zawartości ramki JFrame jako obrazu PNG o podanej ścieżce pliku
    private static void saveFrameAsImage(JFrame frame, String fileName) {
        try {
            Rectangle rect = frame.getBounds(); // Pobranie wymiarów ramki
            BufferedImage image = new BufferedImage(rect.width, rect.height, BufferedImage.TYPE_INT_RGB); // Utworzenie obrazu buforowanego
            frame.paint(image.getGraphics()); // Narysowanie zawartości ramki na obrazie
            ImageIO.write(image, "PNG", new File(fileName)); // Zapisanie obrazu do pliku PNG
            JOptionPane.showMessageDialog(null, "Wizualizacja zapisana jako " + fileName, "Saved", JOptionPane.INFORMATION_MESSAGE); // Komunikat o pomyślnym zapisie
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Błąd zapisu wizualizacji: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); // Komunikat o błędzie zapisu
        }
    }
}
