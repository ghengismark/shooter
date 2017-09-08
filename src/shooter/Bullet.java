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

/**
 * Serves as the base for player-fired elements.
 * Extend this class for specialized bullets.
 *
 * @author Mark Knapp
 */
abstract class Bullet extends Sprite {
        
    public Bullet(Group gRoot, double xBottomCenterStart, double yBottomCenterStart, int angleStart) {
        super(gRoot, xBottomCenterStart, yBottomCenterStart, false, angleStart, (double) 10);
        rotate();
        
    }       
     
    /**
     * Set up defaults.
     */  
    abstract void init ();
    
    /**
     * Create the image of the sprite.
     */  
    abstract void draw ();   
}   
