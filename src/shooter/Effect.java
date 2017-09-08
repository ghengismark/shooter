/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shooter;

import javafx.animation.Timeline;
import javafx.scene.Group;

/**
 * An animation of an explosion happening.
 * @author Mark Knapp
 */
abstract class Effect extends Group {
        
    protected           Timeline                effectAnimation;
    
    protected           int                     imgIndex            = 0;
    
    // The Center X,Y current location of the sprite
    protected double  xLoc;
    protected double  yLoc;    
    
    // The desired size of the explosion
    protected double  xSize;
    protected double  ySize;
       
    protected Group   root;
     
    public Effect (Group gRoot, double xTopLeftStart, double yTopLeftStart, double xESize, double yESize) { 
        xLoc = xTopLeftStart;
        yLoc = yTopLeftStart;        
        xSize = xESize;
        ySize = yESize; 
        root = gRoot;
 
        draw();
        root.getChildren().add(this);
    }
    
    /**
     * Create the image of the explosion.
     */  
    abstract void draw ();

    /**
     * Move the sprite to a new X,Y (centered)
     *
     * @param   x    The X coordinate of the desired location
     * @param   y    The Y coordinate of the desired location
     */  
    public void moveTo (double x, double y) { 
        this.setTranslateX(x - xLoc + this.getTranslateX());
        this.setTranslateY(y - yLoc + this.getTranslateY());
        xLoc = x;
        yLoc = y;
    }
    
    /**
     * Finish the death process.
     * Finally terminate the object.
     */  
    public void effectDone () { 
        effectAnimation.pause();
        this.setOpacity(0);
        root.getChildren().remove(this);        
    }
    
    /**
     * A simple getter
     * @return              The current X position of the sprite.
     */  
    public double getTopLeftX() {
        return xLoc;
    }

    /**
     * A simple getter
     * @return              The current Y position of the sprite.
     */  
    public double getTopLeftY() {
        return yLoc;
    }

    /**
     * A simple getter
     * @return              The current X size of the sprite.
     */  
    public double getSizeX() {
        return xSize;
    }

    /**
     * A simple getter
     * @return              The current Y size of the sprite.
     */  
    public double getSizeY() {
        return xSize;
    }    
}
