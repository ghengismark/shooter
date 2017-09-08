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

import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

/**
 * Serves as the base for enemies.
 * Extend this class for specialized enemies.
 * 
 * @author Mark Knapp
 */
public class Enemy extends Sprite {
    
    double currentRadius;
    
    public Enemy(Group gRoot, double xTopLeftStart, double yTopLeftStart, int angle) {
        super(gRoot, xTopLeftStart, yTopLeftStart, true, angle, 1.5);
    }
    
    /**
     * Set up defaults
     */
    @Override
    public void init () { 
        radius = 20;
        xSize = ySize = radius*2;
        timeToLive = 8;
        color = Color.ORANGE;
        currentRadius = radius;
    }

    /**
     * Create the image of the sprite.
     */  
    @Override
    public void draw () { 
        circle = new Circle(xCenterLoc, yCenterLoc, radius*2, color);
        this.getChildren().add(circle);
    }
    
    /**
     * Start the death process.
     * Start death animations, housekeeping, etc.
     */  
    @Override
    public void startDeath () { 
        isDying = true;
        final Animation animation = new Transition() {
            {
                setCycleDuration(Duration.millis(250));
            }

            protected void interpolate(double frac) {
                final int n = Math.round((float) radius * (float) frac);
                circle.setRadius(radius - n);
                if (n >= radius) {
                    finishDeath();
                    this.stop();
                }
            }

        };

        animation.play();
        
        
    }
        
}
