package com.F99;

import javax.swing.JFrame;


class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame("F99");
        GamePanel gamePanel = new GamePanel();

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        
        window.add(gamePanel);
        window.pack();
        
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        gamePanel.requestFocusInWindow();

        gamePanel.startGameThread();
    }   
}