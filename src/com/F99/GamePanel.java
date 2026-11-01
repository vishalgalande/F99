package com.F99;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;



public class GamePanel extends JPanel implements Runnable,Panel{

    public static final int WINDOW_WIDTH = 1920; //960
    public static final int WINDOW_HEIGHT = 1080;//540

    final int FPS = 60;
    
    private Thread gameThread;
    
    KeyHandler keyH = new KeyHandler();
    Track track1 = new Track();
    Hud hud = new Hud();
    
    public GamePanel(){
        this.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        this.setBackground(Color.DARK_GRAY);
        
        //this.setDoubleBuffered(true);
        
        this.addKeyListener(keyH);
        
        this.setFocusable(true);
        
        
        this.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                requestFocusInWindow();
            }
        });
    }
    
    Car player1 = new Car(keyH, this,track1);
    
    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }


    @Override
    public void run() {


        double drawInterval = 1_000_000_000.0/FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;

        //debug
        //long last = System.nanoTime();  

        while(gameThread != null){
            update();
            repaint();

            //Debug
            //long now = System.nanoTime();
            //System.out.println((now - last) / 1_000_000.0);
            //last = now;

            try {
            long remaningTime = (long)nextDrawTime - System.nanoTime();
            if(remaningTime < 0) remaningTime = 0;

                Thread.sleep((long) remaningTime/1_000_000);
                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }    
    }

    

    private void update(){
        player1.update();
    }

    @Override 
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        double scaleX = (double) WINDOW_WIDTH / Panel.WIDTH;
        double scaleY = (double) WINDOW_HEIGHT / Panel.HEIGHT;

        g2.scale(scaleX, scaleY);


        track1.draw(g2);
        player1.draw(g2);

        hud.draw(g2,player1);

        g.dispose();
    }
}
