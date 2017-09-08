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
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

/**
 * An animation of an explosion happening.
 * @author Mark Knapp
 */
public class GreenEnergyGunFlash extends Effect {
    
    public final static String[]                EFFECT_FILES = {"resources/images/GreenEnergyGunFlash1.png", 
                                                                "resources/images/GreenEnergyGunFlash2.png"};
    
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
