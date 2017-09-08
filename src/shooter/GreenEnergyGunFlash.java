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
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

/**
 * An animation of an explosion happening.
 * @author Mark Knapp
 */
public class GreenEnergyGunFlash extends Effect {

    public final static String                  EFFECT1      = "resources/images/GreenEnergyGunFlash1.png";
    public final static String                  EFFECT2      = "resources/images/GreenEnergyGunFlash2.png";
    
    public final static double                  EFFECT_X_SIZE   = 24;
    public final static double                  EFFECT_Y_SIZE   = 24;
    
    protected           ArrayList<ImageView>    imgEffect;

     
    public GreenEnergyGunFlash (Group gRoot, double xTopLeftStart, double yTopLeftStart, double xESize, double yESize, int angle) { 
        super(gRoot, xTopLeftStart, yTopLeftStart, xESize, yESize);
        this.getTransforms().add(new Rotate(angle, (xSize/2), ySize));
    }
    
    /**
     * Create the image of the explosion.
     */ 
    @Override
    public void draw () { 
        imgEffect = new ArrayList<ImageView>();
        imgEffect.add(new ImageView(new Image(getClass().getClassLoader().getResource(EFFECT1).toString())));
        imgEffect.add(new ImageView(new Image(getClass().getClassLoader().getResource(EFFECT2).toString())));
        
        this.getChildren().add(imgEffect.get(0));
        this.setTranslateX(xLoc);
        this.setTranslateY(yLoc);
        this.setScaleX(xSize/EFFECT_X_SIZE);
        this.setScaleY(ySize/EFFECT_Y_SIZE);
        
        EventHandler<ActionEvent> explosionEventHandler = e -> {
            if (imgIndex > 1) {
                effectDone();
            } else {
                this.getChildren().setAll(imgEffect.get(imgIndex++));
            }
        };
            
        effectAnimation = new Timeline(new KeyFrame(Duration.millis(20), explosionEventHandler));
        effectAnimation.setCycleCount(6);
        effectAnimation.play();
    }  
}
