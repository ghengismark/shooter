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
import javafx.scene.media.AudioClip;
import javafx.util.Duration;

/**
 * An animation of an explosion happening.
 * @author Mark Knapp
 */
public class Explosion extends Effect {

    public final static String                  EFFECT1      = "resources/images/explosion1.png";
    public final static String                  EFFECT2      = "resources/images/explosion2.png";
    public final static String                  EFFECT3      = "resources/images/explosion3.png";
    public final static String                  EFFECT4      = "resources/images/explosion4.png";
    public final static String                  EFFECT5      = "resources/images/explosion5.png";
    public final static String                  EFFECT6      = "resources/images/explosion6.png";
    public final static String                  EFFECT7      = "resources/images/explosion7.png";
    public final static String                  EFFECT8      = "resources/images/explosion8.png";
    
    public final static double                  EFFECT_X_SIZE   = 32;
    public final static double                  EFFECT_Y_SIZE   = 32;
    
    protected           ArrayList<ImageView>    imgEffect;
    
    //Sound Effects
    protected           AudioClip   soundEffect;
    public final static String      SOUND_EFFECT_FILE   = "resources/audio/Explosion.mp3";
 
     
    public Explosion (Group gRoot, double xTopLeftStart, double yTopLeftStart, double xESize, double yESize) { 
        super(gRoot, xTopLeftStart, yTopLeftStart, xESize, yESize);
    }
    
    public Explosion (Group gRoot, double xStart, double yStart) { 
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
        imgEffect.add(new ImageView(new Image(getClass().getClassLoader().getResource(EFFECT6).toString())));
        imgEffect.add(new ImageView(new Image(getClass().getClassLoader().getResource(EFFECT7).toString())));
        imgEffect.add(new ImageView(new Image(getClass().getClassLoader().getResource(EFFECT8).toString())));
        
        this.getChildren().add(imgEffect.get(0));
        this.setTranslateX(xLoc);
        this.setTranslateY(yLoc);
        this.setScaleX(xSize/EFFECT_X_SIZE);
        this.setScaleY(ySize/EFFECT_Y_SIZE);
        
        EventHandler<ActionEvent> effectEventHandler = e -> {
            if (imgIndex > 7) {
                effectDone();
            } else {
                this.getChildren().setAll(imgEffect.get(imgIndex++));
            }
        };
            
        effectAnimation = new Timeline(new KeyFrame(Duration.millis(50), effectEventHandler));
        effectAnimation.setCycleCount(9);
        effectAnimation.play();
        
        soundEffect = new AudioClip(getClass().getClassLoader().getResource(SOUND_EFFECT_FILE).toString());
        soundEffect.play();
    }  
}
