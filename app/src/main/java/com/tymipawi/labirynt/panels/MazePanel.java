package com.tymipawi.labirynt.panels;

import javax.imageio.ImageIO;
import javax.swing.*;

import com.tymipawi.labirynt.utils.DataLoader;
import com.tymipawi.labirynt.utils.InputModifier;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MazePanel extends JPanel {
    public static char[][] mazeData;
    private int cellWidth = -1, cellHeight = -1;
    private InputModifier inputModifier = new InputModifier(this);
    public static boolean modified = false;

    // Zmienne do przechowywania punktów wejścia i wyjścia
    public static int entryX, entryY, exitX, exitY;

    public MazePanel() {
        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                inputModifier.updateCell(e.getX(), e.getY(), cellWidth, cellHeight);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawPixels(g);
    }

    public void drawPixels(Graphics g){
        if(g==null)
            return;

        int panelWidth = getWidth(), panelHeight = getHeight();
        Color emptyColor = new Color(54, 69, 79),
              pathColor = new Color(54,140,150);
        g.setColor(emptyColor);
        g.fillRect(0, 0, panelWidth, panelHeight);

        if(mazeData == null)
            return;

        int labWidth = mazeData[0].length, labHeight = mazeData.length;
        cellWidth = panelWidth / labWidth;
        cellHeight = panelHeight / labHeight;

        int compX = 0, compY = 0;
        while(compY < labHeight){
            compX = 0;
            while(compX < labWidth){
                if(mazeData[compY][compX] == 'X')
                    g.setColor(Color.BLACK);
                else if(mazeData[compY][compX] == 'P' && (Math.abs(DataLoader.getEntryX()-compX) <= 1 && Math.abs(DataLoader.getEntryY()-compY) <= 1))
                    g.setColor(Color.GREEN);
                else if(mazeData[compY][compX] == 'K')
                    g.setColor(Color.RED);
                else if(mazeData[compY][compX] == '^' && !(modified==true && compY == 1 && compX == 1))
                    g.setColor(pathColor);
                else 
                    g.setColor(Color.WHITE);
                
                g.fillRect(compX*cellWidth, compY*cellHeight, cellWidth, cellHeight);
                compX++;
            }
            compY++;
        }
    }

    public void visualize() {
        repaint();
    }

    public void savePanelAsImage() {
        BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();
        this.paint(g2d);
        g2d.dispose();
        try {
            ImageIO.write(image, "png", new File("data/output/" + DataLoader.getSelectedFile().replace(".txt", ".png")));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // Metody do ustawiania punktów wejścia i wyjścia
    public static void setEntryPoint(int x, int y) {
        entryX = x;
        entryY = y;
    }

    public static void setExitPoint(int x, int y) {
        exitX = x;
        exitY = y;
    }
}