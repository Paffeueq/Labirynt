package com.tymipawi.labirynt.frames;

import javax.swing.JFrame;

import com.tymipawi.labirynt.panels.AppPanel;

public class MainFrame extends JFrame{

    AppPanel appPanel = new AppPanel();

    public MainFrame(){
        setup();

        add(appPanel);

        setVisible(true);
    }

    private void setup(){
        setSize(1920, 1080);
        setTitle("System wykorzystujacy innowacyjne algorytmy w celu wyznaczenia prawdziwie" +
                 " najkrotszej sciezki we wszelakiej masci labiryntach");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        
    }
}