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
