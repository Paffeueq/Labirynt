package source.panels;

import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import source.utils.DataLoader;

public class VisualizationPanel extends JPanel{
    Color bgColor = new Color(96, 96, 96);
    Color btnColor = new Color(200, 130, 130);

    private JPanel cardPanel;
    DataLoader dataLoader;
    MazePanel mazePanel;

    public VisualizationPanel(JPanel cardPanel, DataLoader dataLoader){
        mazePanel = new MazePanel(dataLoader);
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

        add(homeBtn);
    }

    public void visualize(){
        mazePanel.visualize();
    }
}
