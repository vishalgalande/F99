package com.F99;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;


public class Hud {


    public void draw(Graphics2D g2, Car car,int y){
        
        g2.setFont(new Font("Arial", Font.BOLD, 24));
        g2.setColor(Color.white);

        g2.drawString("Speed: " + car.getSpeed() + " KM/Hr" , 20, 50+y);
        
        g2.drawString("Angle: " + car.getAngle() + "\u00B0" , 20, 90+y);
    }
}