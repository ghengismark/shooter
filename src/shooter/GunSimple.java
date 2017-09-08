/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shooter;

import java.util.ArrayList;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Mark Knapp
 */
public class GunSimple extends Gun {
    
    public GunSimple (Group gBullets, ArrayList<Bullet> gBulletlist, double baseX, double baseY) {
        super(gBullets, gBulletlist, baseX, baseY);
    }
    
    /**
     * This method should be overriden to set up vars.
     * MUST DEFINE:
     * lengthOfBarrel          = 120;
     * delayBetweenShots       = 0.4;
     * keyboardMovementDelta   = 2;
     * gunAngleLimit           = 80;
     * gunAngle                = 0;
     * xSize                   = 40;
     * ySize                   = 120;
     */   
    @Override
    public void init () { 
        lengthOfBarrel          = 120;
        delayBetweenShots       = 0.4;
        keyboardMovementDelta   = 2;
        gunAngleLimit           = 80;
        gunAngle                = 0;
        // Not really used for simple Gun. Can fix later if I care.
        xSize                   = 40;
        // Not really used for simple Gun. Can fix later if I care.
        ySize                   = 120;
    }
    
    /**
     * Get the bullet sub-class that this gun fires.
     * @param gRoot The bullet group that the new bullet should join
     * @param xBottomCenterStart The X,Y coord
     * @param yBottomCenterStart The X,Y coord 
     * @param angleStart The angle of the bullet path
     * @return   This should be a specific sub-class of Bullet
     */   
    public BulletSimple getNewBullet (Group gRoot, double xBottomCenterStart, double yBottomCenterStart, int angleStart) {
        return new BulletSimple(gRoot, xBottomCenterStart, yBottomCenterStart, angleStart);
    }; 

    /**
     * Reset the gun for a new game
     */ 
    public void reset (){
        setAngle(0);
    };      
    
    /**
     * Create the image of the gun.
     */ 
    @Override
    public void draw () { 
        final Circle gunBase = new Circle(pivotPointX, pivotPointY, 50, Color.GREY);
        
        final Rectangle gunMouth = new Rectangle(pivotPointX-20, pivotPointY-lengthOfBarrel, 40, 20);
        gunMouth.setFill(Color.DARKGREY);       
 
        final Rectangle gunBarrel = new Rectangle(pivotPointX-10, pivotPointY-lengthOfBarrel, 20, lengthOfBarrel);
        gunBarrel.setStroke(Color.DARKGREY);
        gunBarrel.setFill(Color.GREY);
        gunBarrel.setStrokeWidth(3);
        
        gunBodyGroup.getChildren().add(gunBarrel); 
        gunBodyGroup.getChildren().add(gunMouth);
        gunBodyGroup.getChildren().add(gunBase);
        
        this.getChildren().add(gunBodyGroup); 
    }  
}