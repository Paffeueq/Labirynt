import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

// Klasa odpowiedzialna za wizualizację pliku labiryntu
public class FileGridVisualizer extends JFrame {
    private DataLoader dataLoader = new DataLoader(); // Obiekt do ładowania danych
    private VisualizationFrame visualizationFrame = new VisualizationFrame(); // Ramka do wizualizacji
    private JButton loadButton, displayButton, saveButton; // Przyciski do interakcji z programem

    // Konstruktor klasy FileGridVisualizer
    public FileGridVisualizer() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        initializeButtons();
        setSize(600, 600);
        setLocationRelativeTo(null);
    }

    // Inicjalizacja przycisków i ich akcji
    private void initializeButtons() {
        loadButton = new JButton("Wybierz plik .txt do wczytania");
        displayButton = new JButton("Stwórz wizualizację labiryntu");
        saveButton = new JButton("Zapisz wizualizację do pliku .png");

        // Konfiguracja przycisków i ich akcji
        setupButton(loadButton, 300, 150, e -> {
            dataLoader.loadDataFile(this);
            if (dataLoader.getDataArray() != null) {
                visualizationFrame.resetVisualization(dataLoader.getDataArray(), this);
            }
        });
        setupButton(displayButton, 300, 150, e -> visualizationFrame.displayVisualization(this));
        setupButton(saveButton, 300, 150, e -> ImageSaver.saveVisualization(visualizationFrame, this));

        // Dodanie przycisków do interfejsu użytkownika
        addComponents(loadButton, displayButton, saveButton);
    }

    // Konfiguracja przycisku
    private void setupButton(JButton button, int width, int height, ActionListener action) {
        button.setPreferredSize(new Dimension(width, height));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(action);
    }

    // Dodanie komponentów do interfejsu użytkownika
    private void addComponents(JButton... buttons) {
        for (JButton button : buttons) {
            add(button);
            add(Box.createRigidArea(new Dimension(0, 50)));
        }
    }
}
