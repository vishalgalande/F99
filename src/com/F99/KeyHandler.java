package com.F99;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public boolean upPressed, downPressed, rightPressed, leftPressed;
    public boolean resetPressed;
    @Override
    public void keyPressed(KeyEvent arg0) {
       int code = arg0.getKeyCode();

        if(code == KeyEvent.VK_W){
            upPressed = true;
        }

        if(code == KeyEvent.VK_A){
            leftPressed = true;    
        }

        if(code == KeyEvent.VK_S){
            downPressed = true;
        }

        if(code == KeyEvent.VK_D){
            rightPressed = true;
        }

        if(code == KeyEvent.VK_R){
            resetPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent arg0) {
     int code = arg0.getKeyCode();

        if(code == KeyEvent.VK_W){
            upPressed = false;
        }

        if(code == KeyEvent.VK_A){
            leftPressed = false;  
        }

        if(code == KeyEvent.VK_S){
            downPressed = false;
        }

        if(code == KeyEvent.VK_D){
            rightPressed = false;
        }

        if(code == KeyEvent.VK_R){
            resetPressed = false;
        }
    }
    

    @Override
    public void keyTyped(KeyEvent arg0) {

    }
}
