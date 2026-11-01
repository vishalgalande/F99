package com.F99;

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
            //collider = ImageIO.read(getClass().getResourceAsStream("/tracks/c_schoridingarsCow.png"));
            collider = ImageIO.read(getClass().getResourceAsStream("/tracks/c_track1.png"));
            map = ImageIO.read(getClass().getResourceAsStream("/tracks/m_track1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean checkCollision(int x,int y){
        if(x<=0 || x>= Panel.WIDTH || y<=0 || y>= Panel.HEIGHT )
            return true;

        if(collider.getRGB(x, y)==-1)
        return false;
        return true;
    }

    public void draw(Graphics2D g2){


        g2.drawImage(map, 0, 0, Panel.WIDTH, Panel.HEIGHT, null);
        //g2.drawImage(collider, 0, 0, Panel.WIDTH, Panel.HEIGHT, null);
    }
}
