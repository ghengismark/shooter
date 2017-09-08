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
import javafx.scene.media.AudioClip;
import javafx.util.Duration;

/**
 * An animation of an explosion happening.
 * @author Mark Knapp
 */
public class Explosion extends Effect {

    public final static String[]                EFFECT_FILES = {"resources/images/explosion1.png", 
                                                                "resources/images/explosion2.png", 
                                                                "resources/images/explosion3.png", 
                                                                "resources/images/explosion4.png", 
                                                                "resources/images/explosion5.png", 
                                                                "resources/images/explosion6.png",
                                                                "resources/images/explosion7.png",
                                                                "resources/images/explosion8.png"};
    
    public final static double                  EFFECT_X_SIZE   = 32;
    public final static double                  EFFECT_Y_SIZE   = 32;
    
    protected           ArrayList<ImageView>    imgEffect;
    
    //Sound Effects
    protected           AudioClip               soundEffect;
    public final static String                  SOUND_EFFECT_FILE   = "resources/audio/Explosion.mp3";
 
     
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
        // Load up our image files
        imgEffect = new ArrayList<ImageView>();
        for(String file : EFFECT_FILES)
            imgEffect.add(new ImageView(new Image(getClass().getClassLoader().getResource(file).toString())));
        
        // Draw, move and size the initial frame
        this.getChildren().add(imgEffect.get(0));
        this.setTranslateX(xLoc);
        this.setTranslateY(yLoc);
        this.setScaleX(xSize/EFFECT_X_SIZE);
        this.setScaleY(ySize/EFFECT_Y_SIZE);
        
        // Animate it
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
        
        // KAPOW! Sound effects
        soundEffect = new AudioClip(getClass().getClassLoader().getResource(SOUND_EFFECT_FILE).toString());
        soundEffect.play();
    }  
}
