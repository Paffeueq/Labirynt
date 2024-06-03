package com.tymipawi.labirynt.utils;

import javax.swing.*;
import java.io.*;
import com.tymipawi.labirynt.panels.MazePanel;

public class DataLoader {
    private static char[][] dataArray; // Tablica przechowująca wczytane dane z pliku
    private static int entryX, entryY, exitX, exitY;
    private static String selectedFile;

    // Metoda do wczytywania pliku z danymi
    public void selectFile() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            selectedFile = file.getName();
            if (selectedFile.endsWith(".bin")) {
                dataArray = readBinaryFile(file.getAbsolutePath());
            } else {
                dataArray = readFile(file.getAbsolutePath());
            }
            updateMazePanel();
        }
    }

    // Metoda do wczytywania danych z pliku tekstowego o podanej ścieżce
    public char[][] readFile(String fileName) {
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
                    char crt = lines[i].charAt(j);
                    dataArray[i][j] = crt; // Wczytanie znaków do tablicy

                    if(crt == 'P'){
                        if(i == 0)
                            entryY = i + 1;
                        else
                            entryY = i;
                        if(j == 0)
                            entryX = j + 1;
                        else
                            entryX = j;
                    }
                    if(crt == 'K'){
                        if(i == rows-1)
                            exitY = i - 1;
                        else
                            exitY = i;
                        if(j == columns-1)
                            exitX = j - 1;
                        else
                            exitX = j;
                    }
                }
            }

            return dataArray; // Zwrócenie wczytanych danych
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Nie można wczytać podanego pliku: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); // Komunikat o błędzie
            return new char[0][0]; // Zwrócenie pustej tablicy w przypadku błędu
        }
    }

    // Metoda do wczytywania danych z pliku binarnego
    public char[][] readBinaryFile(String fileName) {
        try (DataInputStream dis = new DataInputStream(new FileInputStream(fileName))) {
            // Wczytywanie nagłówka
            long fileId = Binary.readUnsignedIntLittleEndian(dis);
            int escape = dis.readUnsignedByte();
            int columns = Binary.readUnsignedShortLittleEndian(dis);
            int lines = Binary.readUnsignedShortLittleEndian(dis);
            entryX = Binary.readUnsignedShortLittleEndian(dis) - 1;
            entryY = Binary.readUnsignedShortLittleEndian(dis) - 1;
            exitX = Binary.readUnsignedShortLittleEndian(dis) - 1;
            exitY = Binary.readUnsignedShortLittleEndian(dis) - 1;

            dis.skipBytes(12); // Pominięcie 12 bajtów

            // Wczytywanie reszty nagłówka
            long counter = Binary.readUnsignedIntLittleEndian(dis);
            long solutionOffset = Binary.readUnsignedIntLittleEndian(dis);
            int separator = dis.readUnsignedByte();
            int wall = dis.readUnsignedByte();
            int path = dis.readUnsignedByte();

            // Tworzenie i wypełnianie tablicy danych
            char[][] dataArray = new char[lines][columns];
            for (int i = 0; i < lines; i++) {
                for (int j = 0; j < columns; j++) {
                    dataArray[i][j] = ' ';
                }
            }
            dataArray[entryY][entryX] = 'P';
            dataArray[exitY][exitX] = 'K';

            // Wczytywanie układu labiryntu
            int cellsProcessed = 0;
            int currentRow = 0, currentCol = 0;
            while (counter-- > 0 && cellsProcessed < columns * lines) {
                int byteRead = dis.readUnsignedByte();
                if (byteRead != separator) {
                    throw new IOException("Oczekiwano separatora, otrzymano: " + String.format("%02x", byteRead));
                }

                int value = dis.readUnsignedByte();
                int countByte = dis.readUnsignedByte();

                for (int i = 0; i < countByte + 1 && cellsProcessed < columns * lines; i++) {
                    if (currentRow == entryY && currentCol == entryX) {
                        dataArray[currentRow][currentCol] = 'P';
                    } else if (currentRow == exitY && currentCol == exitX) {
                        dataArray[currentRow][currentCol] = 'K';
                    } else {
                        dataArray[currentRow][currentCol] = (value == wall) ? 'X' : ' ';
                    }

                    cellsProcessed++;
                    currentCol++;
                    if (currentCol >= columns) {
                        currentCol = 0;
                        currentRow++;
                    }
                }
            }

            // Wczytywanie sekcji rozwiązania jeśli istnieje
            if (solutionOffset > 0) {
                dis.skipBytes((int) (solutionOffset - dis.available()));
                long solutionId = Binary.readUnsignedIntLittleEndian(dis);
                int steps = Binary.readUnsignedShortLittleEndian(dis);
                for (int i = 0; i < steps; i++) {
                    int direction = dis.readUnsignedByte();
                    int stepCount = dis.readUnsignedByte();
                    // Przetwarzanie direction i stepCount zgodnie z wymaganiami
                }
            }

            if(entryX == 0)
                entryX++;
            if(entryY == 0)
                entryY++;
            if(exitX == columns-1)
                exitX--;
            if(exitY == columns-1)
                exitY = exitY--;

            return dataArray;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Nie można wczytać podanego pliku binarnego: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return new char[0][0];
        }


    }

    // Metoda do aktualizacji panelu z labiryntem
    private void updateMazePanel() {
        MazePanel.mazeData = dataArray;
        MazePanel.setEntryPoint(entryX, entryY);
        MazePanel.setExitPoint(exitX, exitY);
        // Wywołanie metody repaint lub update jeśli potrzebne
    }

    public void setStart(int cellX, int cellY) {
        if ((cellX == 0 || cellY == 0) || (cellX % 2 != 0 && cellY % 2 != 0)) {
            entryX = cellX;
            entryY = cellY;
        }

        for (int i = 0; i < dataArray.length; i++) {
            for (int j = 0; j < dataArray[0].length; j++) {
                if (dataArray[i][j] == 'P')
                    dataArray[i][j] = ' ';
                if (i == entryY && j == entryX)
                    dataArray[i][j] = 'P';
            }
        }
        MazePanel.mazeData = dataArray;
    }

    public void setEnd(int cellX, int cellY) {
        if ((cellX == 0 || cellY == 0) || (cellX % 2 != 0 && cellY % 2 != 0)) {
            exitX = cellX;
            exitY = cellY;
        }

        for (int i = 0; i < dataArray.length; i++) {
            for (int j = 0; j < dataArray[0].length; j++) {
                if (dataArray[i][j] == 'K')
                    dataArray[i][j] = ' ';
                if (i == exitY && j == exitX)
                    dataArray[i][j] = 'K';
            }
        }
        MazePanel.mazeData = dataArray;
    }

    // Metoda zwracająca wczytane dane z pliku
    public char[][] getData() {
        int numRows = dataArray.length;
        int numCols = dataArray[0].length;

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                if (dataArray[i][j] == '^') {
                    dataArray[i][j] = ' ';
                }
            }
    }
        return dataArray;
    }

    public int getEntryX() {
        return entryX;
    }

    public int getEntryY() {
        return entryY;
    }

    public int getExitX() {
        return exitX;
    }

    public int getExitY() {
        return exitY;
    }

    public static String getSelectedFile() {
        return selectedFile;
    }
}