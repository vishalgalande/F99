package com.F99;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public boolean upPressed, downPressed, rightPressed, leftPressed;
    public boolean resetPressed;
    private int gas,brk,left,right;
    KeyHandler(int player){
    if(player == 0){

        gas = KeyEvent.VK_W;
        brk = KeyEvent.VK_S;
        left = KeyEvent.VK_A;
        right = KeyEvent.VK_D;
    }else{
        gas = KeyEvent.VK_UP;
        brk = KeyEvent.VK_DOWN;
        left = KeyEvent.VK_LEFT;
        right = KeyEvent.VK_RIGHT;  
    }
    }


    @Override
    public void keyPressed(KeyEvent arg0) {

    
       int code = arg0.getKeyCode();

        if(code == gas){
            upPressed = true;
        }

        if(code == left){
            leftPressed = true;    
        }

        if(code == brk){
            downPressed = true;
        }

        if(code == right){
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
