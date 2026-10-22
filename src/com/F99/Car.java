package com.F99;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Car {
    //Default Values
    double x = 200;
    double y = 100;

    int length = 50;
    int width = 25;

    double velocityX = 0;
    double velocityY = 0;

    double accX = 0;
    double accY = 0;


    double weight = 100;
    
    
    double steerRate = 0.06;
    double wheelBase = length-length*0.4;  //length b/w front and back wheels
    double angle = 0;
    double wheelAng = 0; //in radians
    
    double minTurn = Math.toRadians(5);
    double maxTurn = Math.toRadians(25);
    
    double power = 0.25;
    double brakePower = 0.25; 
    double reversePower = 0.07;
    double engineDrag = 0.98;
    double grip = 0.5;

    double topSpeed = power * engineDrag / (1 - engineDrag);

    double frictionConst = 0.8;



    KeyHandler keyH;
    GamePanel gp;
    Track track;


    //sprites

    private BufferedImage body, wheel;
    

    public Car(KeyHandler keyH, GamePanel gp, Track track){
        this.keyH = keyH;
        this.gp = gp;
        this.track = track;

        try {
            body = ImageIO.read(getClass().getResourceAsStream("/sprites/car_red.png"));
            wheel = ImageIO.read(getClass().getResourceAsStream("/sprites/wheelAxel.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void gas(){
        accY += +Math.sin(Math.toRadians(angle))*power;
        accX += +Math.cos(Math.toRadians(angle))*power;
    }

    private void brake(){
        accY -= Math.sin(Math.toRadians(angle))*brakePower;
        accX -= Math.cos(Math.toRadians(angle))*brakePower;

    }

    private void reverse(){
        accY -= Math.sin(Math.toRadians(angle))*reversePower;
        accX -= Math.cos(Math.toRadians(angle))*reversePower;
    }

    private void drag(){
        double rad = Math.toRadians(angle);

        double forward = velocityX * Math.cos(rad) + velocityY * Math.sin(rad);
        double lateral = velocityX * Math.sin(rad) - velocityY * Math.cos(rad);

        lateral *= (1-grip);

        velocityY = forward * Math.sin(rad) - lateral * Math.cos(rad);
        velocityX = forward * Math.cos(rad) + lateral * Math.sin(rad);

        //drag
        velocityX *= engineDrag;
        velocityY *= engineDrag;
    }

    private boolean detectCollision(double x, double y,double angle){
        int grace = 1;
        double[] corners = {
            (x+grace)               ,  (y+grace),
            (x-grace) + length      ,  (y+grace),
            (x-grace) + length      ,  (y-grace) + width,
            (x+grace)               ,  (y-grace) + width
        };

        AffineTransform spinner = AffineTransform.getRotateInstance(Math.toRadians(angle),x+length/2.0,y+width/2.0);

        spinner.transform(corners, 0, corners, 0, 4);

        for(int i = 0; i<8; i += 2){
            if(track.checkCollision((int)corners[i], (int)corners[i+1]))
                return true;
        }
        return false;
    }

    private boolean collided(){
        boolean collided = false;
        if(detectCollision(x + velocityX, y,angle)){
            x -= Math.signum(velocityX)*0.5;
            velocityX = 0;

            collided = true;
        }

        if(detectCollision(x, y + velocityY,angle)){
            y -= Math.signum(velocityY)*0.5;
            velocityY = 0;
            
            collided = true;
        }
        return collided;
    }
    private void move(){
        
        
        velocityX += accX;
        velocityY += accY;
        
        drag();
        
        if(collided()) return ;
        y += velocityY;
        x += velocityX;
    }

    private void steering(double forwardSpeed){

        //steering without steering lock
        /*
        double rad = Math.toRadians(angle);
        double forwardSpeed = velocityX * Math.cos(rad) + velocityY * Math.sin(rad);
        double steer = steerRate * Math.max(-1.0, Math.min(1.0, forwardSpeed / 3.0));

        if (keyH.leftPressed)  angle -=  steer;
        if (keyH.rightPressed) angle += steer; */

        //System.out.println(forwardSpeed + "   :    " + topSpeed);

        double steerLockFactor = Math.min(1.0, Math.abs(forwardSpeed)/topSpeed);
        double maxLock = maxTurn + (minTurn - maxTurn)*steerLockFactor;
        
        double targetAngle = 0;

        if (keyH.leftPressed) targetAngle -=maxLock;
        if (keyH.rightPressed) targetAngle +=maxLock;

        wheelAng += (targetAngle - wheelAng)*steerRate;

        double turnRate = forwardSpeed /wheelBase * Math.tan(wheelAng);

        double backOffDeg = 0.5;

        if(detectCollision(x, y,angle + Math.toDegrees(turnRate))){
            angle -= Math.signum(Math.toDegrees(turnRate))*backOffDeg;   
            return;
        }

        angle += Math.toDegrees(turnRate);

    }
    public void update(){
        //debug
        //System.out.println(detectCollision(x, y,angle) + "  v=" + Math.hypot(velocityX, velocityY));

        //debug

        //Accelerating
        double rad = Math.toRadians(angle);
        double forwardSpeed = velocityX * Math.cos(rad) + velocityY * Math.sin(rad);

                
        accX = 0;
        accY = 0;

        
        if(keyH.upPressed) gas();
        if(keyH.downPressed){
            if(forwardSpeed <=0)
                reverse();
            else
                brake();
        }

        if(keyH.resetPressed){
            x = 200; 
            y = 100;
        }



        steering(forwardSpeed);

        move();

    }

    public void draw(Graphics2D g2){
        //g2.setColor(Color.red);

        AffineTransform old = g2.getTransform();
        
        g2.rotate(Math.toRadians(angle),(int)x+length/2, (int)y + width/2);    //roate car in its own transform
        AffineTransform carState = g2.getTransform();               //save car transform

        g2.rotate(wheelAng,(int)x + 38, (int)y + width/2);         //rotate wheel axel in car + wheel transform
        g2.drawImage(wheel,(int)x+33,(int)y,10,width,null);
        g2.setTransform(carState);                                 //reset tranform to car to draw car


        g2.drawImage(body,(int)x,(int)y,length,width,null);


        g2.setTransform(old);

        

    }


}
