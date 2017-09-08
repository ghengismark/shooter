/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shooter;

import java.util.ArrayList;
import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * A large mechanical bee.
 * Moves consistantly and easy to hit.
 * @author Mark Knapp
 */
public class EnemyBee extends Enemy {
    
    public final static String                  BEE1                = "resources/images/bee1.png";
    public final static String                  BEE2                = "resources/images/bee2.png";
    public final static String                  BEE3                = "resources/images/bee3.png";
    
    public final static double                  BEE_IMAGE_X_SIZE    = 125;
    public final static double                  BEE_IMAGE_Y_SIZE    = 88;
    
    protected           ArrayList<ImageView>    imgBee;
    protected           Group                   bee;
    protected           Timeline                beeAnimation;
    protected           Timeline                beeDeathAnimation;    
    private             int                     imgIndex            = 0;
    private             int                     imgDeathIndex       = 0;
    protected           Random                  diceRoller          = new Random();
    
    
    public EnemyBee(Group gRoot, double xTopLeftStart, double yTopLeftStart, int angle) {
        super(gRoot, xTopLeftStart, yTopLeftStart, angle);
        setSpeed(1);
    }
    
    /**
     * Set up defaults
     */
    @Override
    public void init () { 
        xSize = BEE_IMAGE_X_SIZE;
        ySize = BEE_IMAGE_Y_SIZE;
        radius = ((BEE_IMAGE_X_SIZE > BEE_IMAGE_Y_SIZE) ? BEE_IMAGE_X_SIZE : BEE_IMAGE_Y_SIZE)/2;
        timeToLive = 30;
    }

    /**
     * Create the image of the sprite.
     */  
    @Override
    public void draw () { 
        
        imgBee = new ArrayList<ImageView>();
        imgBee.add(new ImageView(new Image(getClass().getClassLoader().getResource(BEE1).toString())));
        imgBee.add(new ImageView(new Image(getClass().getClassLoader().getResource(BEE2).toString())));
        imgBee.add(new ImageView(new Image(getClass().getClassLoader().getResource(BEE3).toString())));
        imgBee.add(new ImageView(new Image(getClass().getClassLoader().getResource(BEE2).toString())));
        
        bee = new Group(imgBee.get(0));
        bee.setTranslateX(xTopLeftLoc);
        bee.setTranslateY(yTopLeftLoc);
        bee.setScaleX(xSize / BEE_IMAGE_X_SIZE);
        bee.setScaleY(ySize / BEE_IMAGE_Y_SIZE);
        this.getChildren().add(bee);
        
        EventHandler<ActionEvent> beeEventHandler = e -> {
            if (imgIndex > 3) 
                imgIndex = 0;
            bee.getChildren().setAll(imgBee.get(imgIndex++));
        };
            
        beeAnimation = new Timeline(new KeyFrame(Duration.millis(30), beeEventHandler));
        beeAnimation.setCycleCount(Timeline.INDEFINITE);
        beeAnimation.play();
    }  

    /**
     * Start the death process.
     * Start death animations, housekeeping, etc.
     */ 
    @Override
    public void startDeath () { 
        isDying = true;
        stop = true;
        EventHandler<ActionEvent> beeDeathEventHandler = e -> {
            if (imgDeathIndex > 6) 
                finishDeath();
            if (imgDeathIndex < 4) 
                new Explosion(root, xTopLeftLoc + diceRoller.nextInt((int)xSize) - 10, yTopLeftLoc + diceRoller.nextInt((int)ySize) - 10);
            this.setOpacity(1-(0.15*imgDeathIndex++));
        };
            
        beeDeathAnimation = new Timeline(new KeyFrame(Duration.millis(100), beeDeathEventHandler));
        beeDeathAnimation.setCycleCount(8);
        beeDeathAnimation.play();
    }  
}
