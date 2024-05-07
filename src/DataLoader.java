import javax.swing.*;
import java.io.*;
//klasy odpowiadajace za wczytanie danych z pliku
public class DataLoader {
    private char[][] dataArray;

    public void loadDataFile(JFrame frame) {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            dataArray = loadFileData(file.getAbsolutePath());
        }
    }
//wczytanie ilsoci kolumn i wierszy, co wazne ilosc kolumn jest zapiswyana
//jedynie na podstawie elementow pierwszego weirsza
    private char[][] loadFileData(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            StringBuilder fileContent = new StringBuilder();
            String line = reader.readLine();
            int rows = 0;
            while (line != null) {
                fileContent.append(line).append("\n");
                rows++;
                line = reader.readLine();
            }

            int columns = fileContent.toString().split("\n")[0].length();
            char[][] dataArray = new char[rows][columns];
            String[] lines = fileContent.toString().split("\n");
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    dataArray[i][j] = lines[i].charAt(j);
                }
            }

            return dataArray;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Nie można wczytać podanego pliku: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return new char[0][0];
        }
    }

    public char[][] getDataArray() {
        return dataArray;
    }
}

