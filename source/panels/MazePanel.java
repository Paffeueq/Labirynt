package source.panels;

import javax.swing.*;

import source.utils.DataLoader;

import java.awt.*;

public class MazePanel extends JPanel {
    DataLoader dataLoader;
    
    public MazePanel(DataLoader dataLoader) {
        this.dataLoader = dataLoader;
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        drawPixels(g);
    }

    private void drawPixels(Graphics g){
        char[][] dataArray = dataLoader.getDataArray();
        int panelWidth = getWidth(), panelHeight = getHeight();
        int labWidth = dataArray[0].length, labHeight = dataArray.length;
        

        int width = panelWidth / labWidth;
        int height = panelHeight / labHeight;
        
        int compX = 0, compY = 0;
        while(compY < labHeight){
            compX = 0;
            while(compX < labWidth){
                if(dataArray[compY][compX] == 'X')
                    g.setColor(Color.BLACK);
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
