package com.tymipawi.labirynt.utils;

import javax.swing.*;

import com.tymipawi.labirynt.panels.MazePanel;

import java.io.*;

public class DataLoader {
    private static char[][] dataArray; // Tablica przechowująca wczytane dane z pliku
    private static int entryX, entryY, exitX, exitY;
    private String selectedFile;

    // Metoda do wczytywania pliku z danymi
    public void selectFile() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);
        
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            selectedFile = file.getName();
            dataArray = readFile(file.getAbsolutePath());
        }
    }

    // Metoda do wczytywania danych z pliku o podanej ścieżce
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

    public void setStart(int cellX, int cellY){
        if((cellX == 0 || cellY == 0) || (cellX % 2 != 0 && cellY % 2 != 0)){
            entryX = cellX;
            entryY = cellY;
        }

        for (int i = 0; i < dataArray.length; i++) {
            for (int j = 0; j < dataArray[0].length; j++) {
                if(dataArray[i][j] == 'P')
                    dataArray[i][j] = ' ';
                if(i == entryY && j == entryX)
                    dataArray[i][j] = 'P';
            }
        }
        MazePanel.mazeData = dataArray;
    }

    public void setEnd(int cellX, int cellY){
        if((cellX == 0 || cellY == 0) || (cellX % 2 != 0 && cellY % 2 != 0)){
            exitX = cellX;
            exitY = cellY;
        }

        for (int i = 0; i < dataArray.length; i++) {
            for (int j = 0; j < dataArray[0].length; j++) {
                if(dataArray[i][j] == 'K')
                    dataArray[i][j] = ' ';
                if(i == exitY && j == exitX)
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

    // Getter for entryY
    public int getEntryY() {
        return entryY;
    }

    // Getter for exitX
    public int getExitX() {
        return exitX;
    }

    // Getter for exitY
    public int getExitY() {
        return exitY;
    }

    public String getSelectedFile(){
        return selectedFile;
    }

}