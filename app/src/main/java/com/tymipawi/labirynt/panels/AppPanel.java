package com.tymipawi.labirynt.panels;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.tymipawi.labirynt.utils.*;

public class AppPanel extends JPanel{
    private JPanel cardPanel;

    Color bgColor = new Color(96, 96, 96);
    Color btnColor = new Color(200, 130, 130);

    DataLoader dataLoader = new DataLoader();
    VisualizationPanel visualizationPanel;

    public AppPanel(JPanel cardPanel, DataLoader dataLoader, VisualizationPanel visualizationPanel){
        this.cardPanel = cardPanel;
        this.dataLoader = dataLoader;
        this.visualizationPanel = visualizationPanel;

        setup();
    }

    private void setup(){
        setSize(1920, 1080);
        setBackground(bgColor);
        setLayout(null);

        initializeButtons();
    }

    private void initializeButtons() {
        JButton loadButton = new JButton("wczytaj dane");
        JButton displayButton = new JButton("wizualizuj");
 

        loadButton.setBounds(100, 100, 200, 100);
        loadButton.setBackground(btnColor);
        loadButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                dataLoader.selectFile();
            }
            
        });

        displayButton.setBounds(100, 200, 200, 100);
        displayButton.setBackground(btnColor);
        displayButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                CardLayout cardLayout = (CardLayout) cardPanel.getLayout();
                
                char[][] data = dataLoader.getData();

                visualizationPanel.mazePanel.mazeData = data;
                visualizationPanel.visualize();
                cardLayout.show(cardPanel, "vis");
            }
            
        });


        add(loadButton);
        add(displayButton);

    }


}