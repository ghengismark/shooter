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

import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * An enemy that stops and starts
 * @author Mark Knapp
 */
public class EnemyBlade extends Enemy {

    public final static String[]    BLADE_FILES = {"resources/images/Blade1.png", 
                                                   "resources/images/Blade2.png",
                                                   "resources/images/Blade3.png",
                                                   "resources/images/Blade4.png"};
    public final static double      IMAGE_X_SIZE  = 49;
    public final static double      IMAGE_Y_SIZE  = 49;
    
    // The buffer on the sides of the screen for the enemies start. 
    public final static double      SIDE_BUFFER   = 80;
    
    protected           ArrayList<ImageView>   imgEnemy;
    
    protected           Group       enemy;
    
    protected           Timeline    enemyAnimation;
    
    private             int         imgIndex        = 0;
    
    public EnemyBlade(Group gRoot, double xTopLeftStart, double yTopLeftStart, int angle) {
        super(gRoot, xTopLeftStart, yTopLeftStart, angle);
    }
    
    /**
     * Set up defaults
     */
    @Override
    public void init () { 
        radius = 28;
        xSize = ySize = radius*2;
        timeToLive = 30;
    }

    /**
     * Create the image of the sprite.
     */  
    @Override
    public void draw () { 
        
        // Load up our image files
        imgEnemy = new ArrayList<ImageView>();
        for(String file : BLADE_FILES)
            imgEnemy.add(new ImageView(new Image(getClass().getClassLoader().getResource(file).toString())));        
        
        // Draw, move and size the initial frame
        enemy = new Group(imgEnemy.get(0));
        enemy.setTranslateX(xTopLeftLoc);
        enemy.setTranslateY(yTopLeftLoc);
        enemy.setScaleX(xSize / IMAGE_X_SIZE);
        enemy.setScaleY(ySize / IMAGE_Y_SIZE);
        this.getChildren().add(enemy);
        
        // Animate it
        EventHandler<ActionEvent> chopperEventHandler = e -> {
            if (imgIndex > 3) 
                imgIndex = 0;
            enemy.getChildren().setAll(imgEnemy.get(imgIndex++));
        };
        enemyAnimation = new Timeline(new KeyFrame(Duration.millis(30), chopperEventHandler));
        enemyAnimation.setCycleCount(Timeline.INDEFINITE);
        enemyAnimation.play();
    }  

    /**
     * Start the death process.
     * Start death animations, housekeeping, etc.
     */ 
    @Override
    public void startDeath () { 
        isDying = true;
        new Explosion(root, xTopLeftLoc, yTopLeftLoc, xSize, ySize);
        finishDeath();
    }    
}
