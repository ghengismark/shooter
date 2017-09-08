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
 * An enemy that moves side to side.
 * @author Mark Knapp
 */
public class EnemyJet extends EnemySimpleSidewinder {
    
    public final static String[]                JET_FILES =     {"resources/images/Jet1.png", 
                                                                "resources/images/Jet2.png", 
                                                                "resources/images/Jet3.png"};
    
    public final static double                  IMAGE_X_SIZE    = 17;
    public final static double                  IMAGE_Y_SIZE    = 25;
    
    // The buffer on the sides of the screen for the enemies start. 
    public final static double      SIDE_BUFFER   = 150;
    
    protected           ArrayList<ImageView>    imgEnemy;
    protected           Group                   enemy;   
    protected           Timeline                enemyAnimation;
    private             int                     imgIndex            = 0;
   
    public EnemyJet(Group gRoot, double xTopLeftStart, double yTopLeftStart, int angle) {
        super(gRoot, xTopLeftStart, yTopLeftStart, angle);
    }
    
    /**
     * Set up defaults
     */
    @Override
    public void init () { 
        xSize = IMAGE_X_SIZE*2;
        ySize = IMAGE_Y_SIZE*2;
        radius = ((IMAGE_X_SIZE > IMAGE_Y_SIZE) ? IMAGE_X_SIZE : IMAGE_Y_SIZE)/2;
        timeToLive = 30;
        setSpeed(0.5);
    }

   /**
     * Create the image of the sprite.
     */  
    @Override
    public void draw () { 
        
        imgEnemy = new ArrayList<ImageView>();
        for(String file : JET_FILES)
            imgEnemy.add(new ImageView(new Image(getClass().getClassLoader().getResource(file).toString())));   
        
        enemy = new Group(imgEnemy.get(0));
        enemy.setTranslateX(xTopLeftLoc);
        enemy.setTranslateY(yTopLeftLoc);
        enemy.setScaleX(xSize / IMAGE_X_SIZE);
        enemy.setScaleY(ySize / IMAGE_Y_SIZE);
        this.getChildren().add(enemy);
        
        EventHandler<ActionEvent> batEventHandler = e -> {
            if (imgIndex > 5) 
                imgIndex = 0;
            enemy.getChildren().setAll(imgEnemy.get(imgIndex++));
        };  
        enemyAnimation = new Timeline(new KeyFrame(Duration.millis(100), batEventHandler));
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
        new Explosion(root, xTopLeftLoc, yTopLeftLoc, 40, 40);
        finishDeath();
    }        
    
}
