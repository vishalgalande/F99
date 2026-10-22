package com.F99;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Track {



    private BufferedImage map, collider;


    Track(){
        loadImages();
    }

    private void loadImages(){
        try {
            collider = ImageIO.read(getClass().getResourceAsStream("/tracks/c_schoridingarsCow.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean checkCollision(int x,int y){
        if(x<=0 || x>= GamePanel.WIDTH || y<=0 || y>= GamePanel.HEIGHT )
            return true;

        if(collider.getRGB(x, y)==-1)
        return false;
        return true;
    }

    public void draw(Graphics2D g2){

        g2.drawImage(collider, 0, 0, GamePanel.WIDTH, GamePanel.HEIGHT, null);
    }
}
