package com.tymipawi.labirynt.panels;

import javax.swing.*;

import com.tymipawi.labirynt.utils.InputModyfier;

import java.awt.*;
import java.awt.event.*;

public class MazePanel extends JPanel {
    public static char[][] mazeData;
    private int cellWidth=-1, cellHeight=-1;
    
    private InputModyfier inputModyfier = new InputModyfier(this);

    public MazePanel(){
        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                inputModyfier.updateCell(e.getX(), e.getY(), cellWidth, cellHeight);            
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g){
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
                else if(mazeData[compY][compX] == 'P')
                    g.setColor(Color.GREEN);
                else if(mazeData[compY][compX] == 'K')
                    g.setColor(Color.RED);
                else if(mazeData[compY][compX] == '^')
                    g.setColor(pathColor);
                else 
                    g.setColor(Color.WHITE);
                
                g.fillRect(compX*cellWidth, compY*cellHeight, cellWidth, cellHeight);
                compX++;
            }
            compY++;
        }
    }

    public void visualize(){
        repaint();
    }
}