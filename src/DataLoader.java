import javax.swing.*;
import java.io.*;

// Klasa do wczytywania danych z pliku
public class DataLoader {
    private char[][] dataArray; // Tablica przechowująca wczytane dane z pliku

    // Metoda do wczytywania pliku z danymi
    public void loadDataFile(JFrame frame) {
        JFileChooser fileChooser = new JFileChooser(); // Okno wyboru pliku
        if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile(); // Wybrany plik
            dataArray = loadFileData(file.getAbsolutePath()); // Wczytanie danych z pliku
        }
    }

    // Metoda do wczytywania danych z pliku o podanej ścieżce
    public char[][] loadFileData(String fileName) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName)); // Strumień do czytania danych z pliku
            StringBuilder fileContent = new StringBuilder(); // Budowniczy dla zawartości pliku
            String line = reader.readLine(); // Wczytanie pierwszej linii
            int rows = 0; // Liczba wierszy
            while (line != null) {
                fileContent.append(line).append("\n"); // Dodanie linii do zawartości pliku
                rows++; // Zwiększenie licznika wierszy
                line = reader.readLine(); // Wczytanie kolejnej linii
            }
            reader.close(); // Zamknięcie czytnika

            int columns = fileContent.toString().split("\n")[0].length(); // Liczba kolumn (długość pierwszej linii)
            char[][] dataArray = new char[rows][columns]; // Tablica na dane z pliku
            String[] lines = fileContent.toString().split("\n"); // Podział zawartości pliku na linie
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    dataArray[i][j] = lines[i].charAt(j); // Wczytanie znaków do tablicy
                }
            }

            System.out.println("Data loaded with rows: " + rows + ", columns: " + columns); // Komunikat debugujący
            return dataArray; // Zwrócenie wczytanych danych
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Nie można wczytać podanego pliku: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); // Komunikat o błędzie
            return new char[0][0]; // Zwrócenie pustej tablicy w przypadku błędu
        }
    }

    // Metoda zwracająca wczytane dane z pliku
    public char[][] getDataArray() {
        return dataArray;
    }
}
