package com.tymipawi.labirynt.panels;

import javax.swing.*;

import java.awt.*;

public class MazePanel extends JPanel {
    public char[][] mazeData;

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
        int width = panelWidth / labWidth;
        int height = panelHeight / labHeight;
        
        int compX = 0, compY = 0;
        while(compY < labHeight){
            compX = 0;
            while(compX < labWidth){
                if(mazeData[compY][compX] == 'X')
                    g.setColor(Color.BLACK);
                else if(mazeData[compY][compX] == '^')
                    g.setColor(pathColor);
                else 
                    g.setColor(Color.WHITE);
                
                g.fillRect(compX*width, compY*height, width, height);
                compX++;
            }
            compY++;
        }
    }

    public void visualize(){
        repaint();
    }
}