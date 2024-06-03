package com.tymipawi.labirynt.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.*;
import javax.swing.*;

import com.tymipawi.labirynt.utils.*;

public class AppPanel extends JPanel{
    private Color bgColor = new Color(54, 69, 79);
    private Color btnColor = new Color(140, 150, 190);

    private DataLoader dataLoader;
    private MazePanel mazePanel; 
    private MazeSolver solver; 
    
    private JLabel fileLabel, filenameLabel;
    public String loadedFile = "brak";


    public static JRadioButton noMod;
    public static JRadioButton startMod;
    public static JRadioButton endMod;

    public AppPanel(){
        dataLoader = new DataLoader();
        mazePanel = new MazePanel();
        solver = new MazeSolver();

        fileLabel = new JLabel();
        fileLabel.setBounds(60, 100, 300, 50);
        fileLabel.setOpaque(true);
        fileLabel.setBackground(new Color(140, 150, 160));
        fileLabel.setFont(new Font("Arial", Font.BOLD, 25));
        fileLabel.setHorizontalAlignment(SwingConstants.CENTER);
        fileLabel.setVerticalAlignment(SwingConstants.CENTER);
        fileLabel.setText("Wczytany plik:");

        filenameLabel = new JLabel();
        filenameLabel.setBounds(60, 150, 300, 50);
        filenameLabel.setOpaque(true);
        filenameLabel.setBackground(new Color(140, 150, 160));
        filenameLabel.setForeground(new Color(250, 158, 180));
        filenameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        filenameLabel.setVerticalAlignment(SwingConstants.CENTER);
        filenameLabel.setFont(new Font("Arial", Font.BOLD, 25));
        filenameLabel.setText(loadedFile);

        add(fileLabel);
        add(filenameLabel);

        setup();
    }

    private void setup(){
        setSize(1920, 1080);
        setBackground(bgColor);
        setLayout(null);

        mazePanel.setBounds(400, 0, 1025, 1025);
        add(mazePanel);

        initializeButtons();
    }

    private void initializeButtons() {
        JButton loadButton = new JButton("wczytaj labirynt");
 
        loadButton.setBounds(110, 250, 200, 100);
        loadButton.setFont(new Font("Arial", Font.BOLD, 18));
        loadButton.setBackground(btnColor);
        loadButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                dataLoader.selectFile();
                updateSelectedFile(DataLoader.getSelectedFile());
                MazePanel.mazeData = dataLoader.getData();
                mazePanel.visualize();
            }
            
        });

        JButton solveBtn = new JButton();
        solveBtn.setFont(new Font("Arial", Font.BOLD, 25));
        solveBtn.setBounds(1500, 100, 300, 50);
        solveBtn.setText("znajdz sciezke");
        solveBtn.setBackground(btnColor);
        solveBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                char[][] maze = dataLoader.getData();
                int sizeX = maze[0].length, sizeY = maze.length;

                char[][] data = solver.getPath(maze, dataLoader.getEntryX(), dataLoader.getEntryY(),
                                               dataLoader.getExitX(), dataLoader.getExitY(), sizeX, sizeY);
                
                MazePanel.mazeData = data;
                mazePanel.visualize();
            }
            
        });
        
        JLabel modLabel = new JLabel("modyfikacja:");
        modLabel.setHorizontalAlignment(SwingConstants.CENTER);
        modLabel.setVerticalAlignment(SwingConstants.CENTER);
        modLabel.setOpaque(true);
        modLabel.setBounds(1500, 450, 300, 50);
        modLabel.setBackground(btnColor);
        modLabel.setFont(new Font("Arial", Font.BOLD, 18));

        ButtonGroup modifyBtnGrp = new ButtonGroup();
        // Create three JRadioButtons
        noMod = new JRadioButton("brak");
        startMod = new JRadioButton("poczatek");
        endMod = new JRadioButton("koniec");
        noMod.setFont(new Font("Arial", Font.BOLD, 15));
        noMod.setBackground(btnColor);
        startMod.setFont(new Font("Arial", Font.BOLD, 15));
        startMod.setBackground(btnColor);
        endMod.setFont(new Font("Arial", Font.BOLD, 15));
        endMod.setBackground(btnColor);
        noMod.setHorizontalAlignment(SwingConstants.CENTER);
        noMod.setVerticalAlignment(SwingConstants.CENTER);
        startMod.setHorizontalAlignment(SwingConstants.CENTER);
        startMod.setVerticalAlignment(SwingConstants.CENTER);
        endMod.setHorizontalAlignment(SwingConstants.CENTER);
        endMod.setVerticalAlignment(SwingConstants.CENTER);

        noMod.setBounds(1500, 500, 100, 50);
        startMod.setBounds(1600, 500, 100, 50);
        endMod.setBounds(1700, 500, 100, 50);
        // Add the radio buttons to the ButtonGroup
        modifyBtnGrp.add(noMod);
        modifyBtnGrp.add(startMod);
        modifyBtnGrp.add(endMod);
        

        JButton saveButton = new JButton("zapisz wizualizacje");
        saveButton.setFont(new Font("Arial", Font.BOLD, 18));
        saveButton.setBackground(btnColor);
        saveButton.setBounds(1500, 200, 300, 100);
        saveButton.addActionListener(e -> mazePanel.savePanelAsImage());
        add(saveButton);

        add(modLabel);
        add(noMod);
        add(endMod);
        add(startMod);
        add(solveBtn);
        add(loadButton);
    }

    private void updateSelectedFile(String filename){
        loadedFile = filename;
        filenameLabel.setText(loadedFile);
    }

   

}