package com.tymipawi.labirynt.utils;

import com.tymipawi.labirynt.panels.AppPanel;
import com.tymipawi.labirynt.panels.MazePanel;

public class InputModifier {
    static DataLoader dataLoader = new DataLoader();

    MazePanel panel;
    public InputModifier(MazePanel panel){
        this.panel = panel;
    }

    public void updateCell(int mouseX, int mouseY, int width, int height){
        int cellX = mouseX / width;
        int cellY = mouseY / height;

        char[][] maze = dataLoader.getData();
        if(maze == null)
            return;
        if(maze[cellY][cellX] == 'X' || maze[cellY][cellX] == ' '){
            if(AppPanel.startMod.isSelected())
                dataLoader.setStart(cellX, cellY);
            if(AppPanel.endMod.isSelected())
                dataLoader.setEnd(cellX, cellY);
            
                panel.visualize();
        }
    }
}
