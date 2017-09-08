/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shooter;

import javafx.scene.Group;
import javafx.scene.paint.Color;

/**
 * An enemy that moves side to side.
 * @author Mark Knapp
 */
public class EnemySimpleSidewinder extends Enemy {
    
    // How wide (distance) the sidewinder swings back and forth
    protected   double  swingDistance = 4;
    
    // How fast (speed) the sidewinder swings back and forth
    protected   double  swingSpeed = 4;
    
    private     int     moveCycle=0;
   
    public EnemySimpleSidewinder(Group gRoot, double xTopLeftStart, double yTopLeftStart, int angle) {
        super(gRoot, xTopLeftStart, yTopLeftStart, angle);
    }
    
    /**
     * Set up defaults
     */
    @Override
    public void init () { 
        radius=25;
        xSize = ySize = radius*2;
        timeToLive = 12;
        color = Color.PURPLE;
    }
    
    /**
     * Move the sprite on it's set path and velocity.
     */ 
    @Override
    public void move () { 
        moveCycle += swingSpeed;
        if (moveCycle > 360)
                moveCycle = 0;
        double xCycleVel = Math.cos(Math.toRadians(moveCycle)) * swingDistance;
        moveTo(xTopLeftLoc+xVel+xCycleVel, yTopLeftLoc+yVel);
    }    
        
}
