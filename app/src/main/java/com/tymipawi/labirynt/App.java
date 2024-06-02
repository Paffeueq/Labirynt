package com.tymipawi.labirynt;

import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.tymipawi.labirynt.utils.*;
import com.tymipawi.labirynt.panels.*;

public class App {
    private static JFrame mainFrame;
    private static CardLayout cardLayout;
    private static JPanel cardPanel;

    public DataLoader dataLoader;

    public App(){
        dataLoader = new DataLoader();

        createFrame();
        createLayout();

        cardLayout.show(cardPanel, "main");
        mainFrame.setVisible(true);
    }

    private void createFrame(){
        mainFrame = new JFrame();
        mainFrame.setSize(1920, 1080);
        mainFrame.setTitle("UWU");
        mainFrame.getContentPane().setBackground(Color.RED);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        
    }

    private void createLayout(){
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        VisualizationPanel visPanel = new VisualizationPanel(cardPanel, dataLoader);
        AppPanel appPanel = new AppPanel(cardPanel, dataLoader, visPanel);

        cardPanel.add(appPanel, "main");
        cardPanel.add(visPanel, "vis");

        mainFrame.add(cardPanel);
    }
    
}