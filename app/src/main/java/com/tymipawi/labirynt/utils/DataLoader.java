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
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            StringBuilder fileContent = new StringBuilder();
            String line = reader.readLine();
            int rows = 0;
            while (line != null) {
                fileContent.append(line).append("\n");
                rows++;
                line = reader.readLine();
            }
            reader.close();

            int columns = fileContent.toString().split("\n")[0].length();
            char[][] dataArray = new char[rows][columns];
            String[] lines = fileContent.toString().split("\n");
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    char crt = lines[i].charAt(j);
                    dataArray[i][j] = crt;
                    if (crt == 'P') {
                        entryY = i;
                        entryX = j;
                    }
                    if (crt == 'K') {
                        exitY = i;
                        exitX = j;
                    }
                }
            }
            return dataArray;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Nie można wczytać podanego pliku: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return new char[0][0];
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