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
 * An enemy that stops and starts
 * @author Mark Knapp
 */
public class EnemyChopper extends EnemySimpleStopper {

    public final static String      CHOPPER1        = "resources/images/chopper1.png";
    public final static String      CHOPPER2        = "resources/images/chopper2.png";
    public final static String      CHOPPER3        = "resources/images/chopper3.png";
    
    public final static double      CHOPPER_IMAGE_X_SIZE  = 28;
    public final static double      CHOPPER_IMAGE_Y_SIZE  = 28;
    
    protected           ArrayList<ImageView>   imgChopper;
    
    protected           Group       chopper;
    
    protected           Timeline    chopperAnimation;
    
    private             int         imgIndex        = 0;
    
    public EnemyChopper(Group gRoot, double xTopLeftStart, double yTopLeftStart, int angle) {
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
        
        imgChopper = new ArrayList<ImageView>();
        imgChopper.add(new ImageView(new Image(getClass().getClassLoader().getResource(CHOPPER1).toString())));
        imgChopper.add(new ImageView(new Image(getClass().getClassLoader().getResource(CHOPPER3).toString())));
        imgChopper.add(new ImageView(new Image(getClass().getClassLoader().getResource(CHOPPER2).toString())));
        imgChopper.add(new ImageView(new Image(getClass().getClassLoader().getResource(CHOPPER3).toString())));
        
        chopper = new Group(imgChopper.get(0));
        chopper.setTranslateX(xTopLeftLoc);
        chopper.setTranslateY(yTopLeftLoc);
        chopper.setScaleX(xSize / CHOPPER_IMAGE_X_SIZE);
        chopper.setScaleY(ySize / CHOPPER_IMAGE_Y_SIZE);
        this.getChildren().add(chopper);
        
        EventHandler<ActionEvent> chopperEventHandler = e -> {
            if (imgIndex > 3) 
                imgIndex = 0;
            chopper.getChildren().setAll(imgChopper.get(imgIndex++));
        };
            
        chopperAnimation = new Timeline(new KeyFrame(Duration.millis(30), chopperEventHandler));
        chopperAnimation.setCycleCount(Timeline.INDEFINITE);
        chopperAnimation.play();
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
