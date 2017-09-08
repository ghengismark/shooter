/*
 * Copyright (C) 2017 mark.knapp
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package shooter;

import javafx.animation.Timeline;
import javafx.scene.Group;

/**
 * An effect is a sprite with less functionality. 
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
