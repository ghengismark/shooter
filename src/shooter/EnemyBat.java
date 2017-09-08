/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
public class EnemyBat extends EnemySimpleSidewinder {
    
    public final static String                  BAT1                = "resources/images/bat1.png";
    public final static String                  BAT2                = "resources/images/bat2.png";
    public final static String                  BAT3                = "resources/images/bat3.png";
    public final static String                  BAT4                = "resources/images/bat4.png";
    
    public final static double                  BAT_IMAGE_X_SIZE    = 39;
    public final static double                  BAT_IMAGE_Y_SIZE    = 24;
    
    protected           ArrayList<ImageView>    imgBat;
    protected           Group                   bat;   
    protected           Timeline                batAnimation;
    private             int                     imgIndex            = 0;
   
    public EnemyBat(Group gRoot, double xTopLeftStart, double yTopLeftStart, int angle) {
        super(gRoot, xTopLeftStart, yTopLeftStart, angle);
    }
    
    /**
     * Set up defaults
     */
    @Override
    public void init () { 
        xSize = BAT_IMAGE_X_SIZE*2;
        ySize = BAT_IMAGE_Y_SIZE*2;
        radius = ((BAT_IMAGE_X_SIZE > BAT_IMAGE_Y_SIZE) ? BAT_IMAGE_X_SIZE : BAT_IMAGE_Y_SIZE)/2;
        timeToLive = 30;
        setSpeed(0.5);
    }

   /**
     * Create the image of the sprite.
     */  
    @Override
    public void draw () { 
        
        imgBat = new ArrayList<ImageView>();
        imgBat.add(new ImageView(new Image(getClass().getClassLoader().getResource(BAT1).toString())));
        imgBat.add(new ImageView(new Image(getClass().getClassLoader().getResource(BAT2).toString())));
        imgBat.add(new ImageView(new Image(getClass().getClassLoader().getResource(BAT3).toString())));
        imgBat.add(new ImageView(new Image(getClass().getClassLoader().getResource(BAT4).toString())));
        imgBat.add(new ImageView(new Image(getClass().getClassLoader().getResource(BAT3).toString())));
        imgBat.add(new ImageView(new Image(getClass().getClassLoader().getResource(BAT2).toString())));
        
        bat = new Group(imgBat.get(0));
        bat.setTranslateX(xTopLeftLoc);
        bat.setTranslateY(yTopLeftLoc);
        bat.setScaleX(xSize / BAT_IMAGE_X_SIZE);
        bat.setScaleY(ySize / BAT_IMAGE_Y_SIZE);
        this.getChildren().add(bat);
        
        EventHandler<ActionEvent> batEventHandler = e -> {
            if (imgIndex > 5) 
                imgIndex = 0;
            bat.getChildren().setAll(imgBat.get(imgIndex++));
        };
            
        batAnimation = new Timeline(new KeyFrame(Duration.millis(100), batEventHandler));
        batAnimation.setCycleCount(Timeline.INDEFINITE);
        batAnimation.play();
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
