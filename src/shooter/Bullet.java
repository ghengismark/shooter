/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shooter;

import javafx.scene.Group;
import javafx.scene.transform.Rotate;

/**
 * Serves as the base for player-fired elements.
 * Extend this class for specialized bullets.
 *
 * @author Mark Knapp
 */
abstract class Bullet extends Sprite {
        
    public Bullet(Group gRoot, double xBottomCenterStart, double yBottomCenterStart, int angleStart) {
        super(gRoot, xBottomCenterStart, yBottomCenterStart, false, angleStart, (double) 10);
        rotateBullet();
        
    }       
     
    /**
     * Set up defaults.
     */  
    abstract void init ();
    
    /**
     * Create the image of the sprite.
     */  
    abstract void draw ();
    
    /**
     * Set up defaults
     */
    public void rotateBullet () { 
        this.getTransforms().add(new Rotate(angle, xBottomCenterLoc, yBottomCenterLoc));
    }     
    
}   
