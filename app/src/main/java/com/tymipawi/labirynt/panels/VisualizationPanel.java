package com.tymipawi.labirynt.panels;

import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import com.tymipawi.labirynt.utils.*;;

public class VisualizationPanel extends JPanel{
    Color bgColor = new Color(96, 96, 96);
    Color btnColor = new Color(200, 130, 130);

    private JPanel cardPanel;
    DataLoader dataLoader;
    MazePanel mazePanel;

    private MazeSolver solver = new MazeSolver();

    public VisualizationPanel(JPanel cardPanel, DataLoader dataLoader){
        mazePanel = new MazePanel();
        this.cardPanel = cardPanel;
        this.dataLoader = dataLoader;

        setup();
        setupBtns();
    }

    private void setup(){
        setSize(1920, 1080);
        setBackground(bgColor);
        setLayout(null);

        mazePanel.setBounds(400, 0, 1025, 1025);
        add(mazePanel);
    }

    private void setupBtns(){
        JButton homeBtn = new JButton();

        homeBtn.setBounds(10, 10, 100, 40);
        homeBtn.setText("home");
        homeBtn.setBackground(btnColor);
        homeBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                /* TODO: multiple cardLayout */
                CardLayout cardLayout = (CardLayout) cardPanel.getLayout();
                
                cardLayout.show(cardPanel, "main");
                
            }
            
        });

        JButton solveBtn = new JButton();
        solveBtn.setBounds(1600, 100, 100, 40);
        solveBtn.setText("solve");
        solveBtn.setBackground(btnColor);
        solveBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                char[][] maze = dataLoader.getData();
                int sizeX = maze[0].length, sizeY = maze.length;
                int startX = 1, startY = 1;
                int endX = sizeX- 2;
                int endY = sizeY - 2;

                char[][] data = solver.getPath(maze, startX, startY, endX, endY, sizeX, sizeY);
                
                mazePanel.mazeData = data;
                mazePanel.visualize();
            }
            
        });

        add(homeBtn);
        add(solveBtn);
    }

    public void visualize(){
        mazePanel.visualize();
    }
}