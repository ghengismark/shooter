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

import javafx.scene.Group;
import javafx.scene.paint.Color;

/**
 * An enemy that moves side to side.
 * @author Mark Knapp
 */
public class EnemySimpleSidewinder extends Enemy {
    
    // How wide (distance) the sidewinder swings back and forth
    protected   double  swingDistance   = 4;
    
    // How fast (speed) the sidewinder swings back and forth
    protected   double  swingSpeed      = 4;
    
    private     int     moveCycle       = 0;
    
    protected   double  xCycleVel       = 0;
    // Used to select the image based on the side movement
    // 0 = center, 1 = move left, 2 = move right
    protected   int     sideImage       = 0;
    
   
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
        
        xCycleVel = Math.cos(Math.toRadians(moveCycle)) * swingDistance;
        if ((xCycleVel/swingDistance) > 0.3)
            sideImage = 2;
        else if ((xCycleVel/swingDistance) < -0.3)
            sideImage = 1;
        else
            sideImage = 0;
                
        moveTo(xTopLeftLoc+xVel+xCycleVel, yTopLeftLoc+yVel);
    }    
        
}
