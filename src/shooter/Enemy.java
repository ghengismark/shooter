/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
