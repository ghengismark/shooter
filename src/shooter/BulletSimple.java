/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shooter;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Serves as the base for player-fired elements.
 * Extend this class for specialized bullets.
 *
 * @author Mark Knapp
 */
public class BulletSimple extends Bullet {
        
    public BulletSimple(Group gRoot, double xBottomCenterStart, double yBottomCenterStart, int angleStart) {
        super(gRoot, xBottomCenterStart, yBottomCenterStart, angleStart);
    }
     
    /**
     * Set up defaults
     */  
    @Override
    public void init () { 
        radius = 5;
        xSize = ySize = radius*2;
        timeToLive = 2;
        color = Color.BLUEVIOLET;
    }   
    
    /**
     * Create the image of the sprite.
     */  
    @Override
    public void draw () { 
        circle = new Circle(xCenterLoc, yCenterLoc, radius*2, color);
        this.getChildren().add(circle);
    }
    
}   
