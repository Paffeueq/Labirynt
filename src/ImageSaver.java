import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.filechooser.FileNameExtensionFilter;

//plik odpowiadjający za zapisywanie pliku
//wizualizacja moze byc zapisywana jedynei gdy jest widoczna, poniewaz przechywytwany jest interface gui
// obecnie wyswietlany, gdyby tak nie bylo to robi sie zapis menu glowenego
public class ImageSaver {
    public static void saveVisualization(JFrame frame, JFrame parent) {
        if (frame == null || !frame.isVisible()) {
            JOptionPane.showMessageDialog(parent, "Wizualizacja może być zapisana jedynie gdy jest ona wyświetlana.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setApproveButtonText("Save");
        fileChooser.setDialogTitle("Save visualization as PNG");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG files (*.png)", "png");
        fileChooser.setFileFilter(filter);
        fileChooser.setAcceptAllFileFilterUsed(false);

        if (fileChooser.showSaveDialog(parent) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            String filePath = ensurePngExtension(file.getAbsolutePath());
            saveFrameAsImage(frame, filePath);
        }
    }
//dodanie obowiazkowej koncowki png podczas dawania nazwy pliku
    private static String ensurePngExtension(String filePath) {
        if (!filePath.toLowerCase().endsWith(".png")) {
            return filePath + ".png";
        }
        return filePath;
    }
//zapis
    private static void saveFrameAsImage(JFrame frame, String fileName) {
        try {
            Rectangle rect = frame.getBounds();
            BufferedImage image = new BufferedImage(rect.width, rect.height, BufferedImage.TYPE_INT_RGB);
            frame.paint(image.getGraphics());
            ImageIO.write(image, "PNG", new File(fileName));
            JOptionPane.showMessageDialog(null, "Wizualizacja zapisana jako " + fileName, "Saved", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Błąd zapisu wizualizacji: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

