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
import javafx.scene.shape.Circle;

/**
 * A simple circle bullet
 *
 * @author Mark Knapp
 */
public class BulletSimple extends Bullet {
        
    public BulletSimple(Group gRoot, double xBottomCenterStart, double yBottomCenterStart, int angleStart) {
        super(gRoot, xBottomCenterStart, yBottomCenterStart, angleStart);
    }
     
    /**
     * Set up defaults.
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
