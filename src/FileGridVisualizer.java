import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

//Plik do głównego interface (guziki do wybrania poszczególnej funkcji)

public class FileGridVisualizer extends JFrame {
    private DataLoader dataLoader = new DataLoader();
    private VisualizationFrame visualizationFrame = new VisualizationFrame();

    public FileGridVisualizer() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        initializeButtons();
        setSize(600, 600);
        setLocationRelativeTo(null);
    }
//inicjacja guzików i ich umiejscowienie
    private void initializeButtons() {
        JButton loadButton = new JButton("Wybierz plik .txt do wczytania");
        JButton displayButton = new JButton("Stwórz wizualizacje labiryntu");
        JButton saveButton = new JButton("Zapisz wizualizację do pliku .png");

        setupButton(loadButton, 300, 150, e -> dataLoader.loadDataFile(this));
        setupButton(displayButton, 300, 150, e -> visualizationFrame.displayVisualization(dataLoader.getDataArray(), this));
        setupButton(saveButton, 300, 150, e -> ImageSaver.saveVisualization(visualizationFrame, this));

        addComponents(loadButton, displayButton, saveButton);
    }

    private void setupButton(JButton button, int width, int height, ActionListener action) {
        button.setPreferredSize(new Dimension(width, height));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(action);
    }

    private void addComponents(JButton... buttons) {
        for (JButton button : buttons) {
            add(button);
            add(Box.createRigidArea(new Dimension(0, 50)));
        }
    }
}
