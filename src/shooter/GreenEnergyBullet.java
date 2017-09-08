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
 * Serves as the base for player-fired elements.
 * Extend this class for specialized bullets.
 *
 * @author Mark Knapp
 */
public class GreenEnergyBullet extends Bullet {
    
    public final static String[]                BULLET_FILES = {"resources/images/GreenEnergyBullet1.png", 
                                                                "resources/images/GreenEnergyBullet2.png", 
                                                                "resources/images/GreenEnergyBullet3.png", 
                                                                "resources/images/GreenEnergyBullet4.png"};
    
    public final static double                  BULLET_IMAGE_X_SIZE = 22;
    public final static double                  BULLET_IMAGE_Y_SIZE = 38;
    public final static double                  SIZE_MODIFIER = 1.5;
    
    protected           ArrayList<ImageView>    imgBullet;
    protected           Group                   bullet;
    protected           Timeline                bulletAnimation;   
    private             int                     imgIndex             = 0;
    
    public GreenEnergyBullet(Group gRoot, double xBottomCenterStart, double yBottomCenterStart, int angleStart) {
        super(gRoot, xBottomCenterStart, yBottomCenterStart, angleStart);
    }
     
    /**
     * Set up defaults
     */
    @Override
    public void init () { 
        
        xSize = BULLET_IMAGE_X_SIZE * SIZE_MODIFIER;
        ySize = BULLET_IMAGE_Y_SIZE * SIZE_MODIFIER;
        radius = BULLET_IMAGE_X_SIZE * SIZE_MODIFIER;
        timeToLive = 5;
    }

    /**
     * Create the image of the sprite.
     */  
    @Override
    public void draw () { 
        
        // Create a barrel flash effect from when the bullet is first fired
        new GreenEnergyGunFlash(root, xBottomCenterLoc-((xSize/2)), yBottomCenterLoc-xSize, xSize, xSize, angle);
        
        // Load up our image files
        // The bullet frames cycle, so we want 1,2,3,4,3,2,etc.
        imgBullet = new ArrayList<ImageView>();
        for(String file : BULLET_FILES)
            imgBullet.add(new ImageView(new Image(getClass().getClassLoader().getResource(file).toString())));
        imgBullet.add(new ImageView(new Image(getClass().getClassLoader().getResource(BULLET_FILES[2]).toString())));
        imgBullet.add(new ImageView(new Image(getClass().getClassLoader().getResource(BULLET_FILES[1]).toString())));
        
        // Draw, move and size the initial frame
        bullet = new Group(imgBullet.get(0));
        bullet.setTranslateX(xTopLeftLoc);
        bullet.setTranslateY(yTopLeftLoc);
        bullet.setScaleX(xSize / BULLET_IMAGE_X_SIZE);
        bullet.setScaleY(ySize / BULLET_IMAGE_Y_SIZE);
        this.getChildren().add(bullet);
        
        // Animate it
        EventHandler<ActionEvent> bulletEventHandler = e -> {
            if (imgIndex > 5) 
                imgIndex = 0;
            bullet.getChildren().setAll(imgBullet.get(imgIndex++));
        };            
        bulletAnimation = new Timeline(new KeyFrame(Duration.millis(10), bulletEventHandler));
        bulletAnimation.setCycleCount(Timeline.INDEFINITE);
        bulletAnimation.play();
    }  

    /**
     * Start the death process.
     * Start death animations, housekeeping, etc.
     */ 
    @Override
    public void startDeath () { 
        isDying = true;
        new GreenEnergyGunExplosion(root, xTopLeftLoc, yTopLeftLoc, xSize*1.5, xSize*1.5);
        finishDeath();
    }   
    
}   
