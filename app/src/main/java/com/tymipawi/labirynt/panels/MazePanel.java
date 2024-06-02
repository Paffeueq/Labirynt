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
        
        int panelWidth = getWidth(), panelHeight = getHeight();
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
                    g.setColor(Color.GREEN);
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