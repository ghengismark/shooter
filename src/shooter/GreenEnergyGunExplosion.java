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
 * An animation of an explosion happening.
 * @author Mark Knapp
 */
public class GreenEnergyGunExplosion extends Effect {

    public final static String                  EFFECT1      = "resources/images/GreenEnergyBulletExplosion1.png";
    public final static String                  EFFECT2      = "resources/images/GreenEnergyBulletExplosion2.png";
    public final static String                  EFFECT3      = "resources/images/GreenEnergyBulletExplosion3.png";
    public final static String                  EFFECT4      = "resources/images/GreenEnergyBulletExplosion4.png";
    public final static String                  EFFECT5      = "resources/images/GreenEnergyBulletExplosion5.png";
    
    public final static double                  EFFECT_X_SIZE   = 24;
    public final static double                  EFFECT_Y_SIZE   = 24;
    
    protected           ArrayList<ImageView>    imgEffect;
     
    public GreenEnergyGunExplosion (Group gRoot, double xTopLeftStart, double yTopLeftStart, double xESize, double yESize) { 
        super(gRoot, xTopLeftStart, yTopLeftStart, xESize, yESize);
    }
    
    public GreenEnergyGunExplosion (Group gRoot, double xStart, double yStart) { 
        this(gRoot, xStart, yStart, EFFECT_X_SIZE, EFFECT_Y_SIZE);
    }
    
    /**
     * Create the image of the explosion.
     */ 
    @Override
    public void draw () { 
        imgEffect = new ArrayList<ImageView>();
        imgEffect.add(new ImageView(new Image(getClass().getClassLoader().getResource(EFFECT1).toString())));
        imgEffect.add(new ImageView(new Image(getClass().getClassLoader().getResource(EFFECT2).toString())));
        imgEffect.add(new ImageView(new Image(getClass().getClassLoader().getResource(EFFECT3).toString())));
        imgEffect.add(new ImageView(new Image(getClass().getClassLoader().getResource(EFFECT4).toString())));
        imgEffect.add(new ImageView(new Image(getClass().getClassLoader().getResource(EFFECT5).toString())));
        
        this.getChildren().add(imgEffect.get(0));
        this.setTranslateX(xLoc);
        this.setTranslateY(yLoc);
        this.setScaleX(xSize/EFFECT_X_SIZE);
        this.setScaleY(ySize/EFFECT_Y_SIZE);
        
        EventHandler<ActionEvent> effectEventHandler = e -> {
            if (imgIndex > 4) {
                effectDone();
            } else {
                this.getChildren().setAll(imgEffect.get(imgIndex++));
            }
        };
            
        effectAnimation = new Timeline(new KeyFrame(Duration.millis(50), effectEventHandler));
        effectAnimation.setCycleCount(6);
        effectAnimation.play();
    }  
}
