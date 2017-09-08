/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shooter;

import javafx.scene.Group;
import javafx.scene.paint.Color;

/**
 *
 * @author mknapp
 */
public class EnemySimpleStopper extends Enemy {
    
    private             int         moveCycle   = 0;
    
    public EnemySimpleStopper(Group gRoot, double xTopLeftStart, double yTopLeftStart, int angle) {
        super(gRoot, xTopLeftStart, yTopLeftStart, angle);
    }
    
    /**
     * Set up defaults
     */
    @Override
    public void init () { 
        radius = 15;
        xSize = ySize = radius*2;
        timeToLive = 12;
        color = Color.BLUE;
    }
    
    /**
     * Move the sprite on it's set path and velocity.
     */ 
    @Override
    public void move () { 
        moveCycle += 1;
        if (moveCycle > 100)
                moveCycle = 0;
        if (moveCycle < 70)
            moveTo(xTopLeftLoc+xVel, yTopLeftLoc+yVel);
    }  
}
