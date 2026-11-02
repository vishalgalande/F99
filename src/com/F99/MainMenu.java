package com.F99;

import javax.swing.JPanel;

public class MainMenu extends JPanel implements Runnable{

    private int FPS = 30;
    private void update(){

    }

    @Override
    public void run() {


        double drawInterval = 1_000_000_000.0/FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;

        //debug
        //long last = System.nanoTime();  
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
