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
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;
import javafx.util.Duration;

/**
 *
 * @author Mark Knapp
 */
public class GreenEnergyGun extends Gun {
    
    public final static String  GUN_IMAGE                = "resources/images/GreenEnergyCannon.png";
    
    public final static double  GUN_IMAGE_X_SIZE        = 155;
    public final static double  GUN_IMAGE_Y_SIZE        = 759;
    
    //Sound Effects
    protected           AudioClip   soundEffect;
    public final static String      SOUND_EFFECT_FILE   = "resources/audio/EnergyGun.mp3";
    
    public final static int     MAX_ENERGY              = 100;
    public final static int     ENERGY_TICK_IN_MS       = 500;
    public final static int     ENERGY_ADD_PER_TICK     = 4;
    public final static int     ENERGY_SPENT_PER_SHOT   = 3;
    
    public final static double  INIT_DELAY_BETWEEN_SHOTS    = 0.2;
    public final static int     INIT_LENGTH_OF_BARREL       = 120;
    public final static int     INIT_KEYBOARD_MOVEMENT      = 2;
    public final static int     INIT_GUN_ANGLE_LIMIT        = 75;
    public final static int     INIT_GUN_ANGLE              = 0;
    public final static double  INIT_X_SIZE                 = 60;
    public final static double  INIT_Y_SIZE                 = 200;

    private             ImageView               gunBody;
    private             int                     gunEnergy = MAX_ENERGY;
    private             Timeline                energyRechargeAnimation;
    private             Rectangle               gunEnergyBar;
    private             Scale                   gunEnergyBarScale;
    private             Label                   gunEnergyPercent;
    
    public GreenEnergyGun (Group gBullets, ArrayList<Bullet> gBulletlist, double baseX, double baseY) {
        super(gBullets, gBulletlist, baseX, baseY);
    }
    
    /**
     * This method should be overriden to set up vars.
     * MUST DEFINE:
     * lengthOfBarrel          = 120;
     * delayBetweenShots       = 0.4;
     * keyboardMovementDelta   = 2;
     * gunAngleLimit           = 80;
     * gunAngle                = 0;
     * xSize                   = 40;
     * ySize                   = 120;
     */   
    @Override
    public void init () { 
        lengthOfBarrel          = INIT_LENGTH_OF_BARREL;
        delayBetweenShots       = INIT_DELAY_BETWEEN_SHOTS;
        keyboardMovementDelta   = INIT_KEYBOARD_MOVEMENT;
        gunAngleLimit           = INIT_GUN_ANGLE_LIMIT;
        gunAngle                = INIT_GUN_ANGLE;
        xSize                   = INIT_X_SIZE;
        ySize                   = INIT_Y_SIZE;
        soundEffect             = new AudioClip(getClass().getClassLoader().getResource(SOUND_EFFECT_FILE).toString());
    }

    /**
     * Get the bullet sub-class that this gun fires.
     * @param gRoot The bullet group that the new bullet should join
     * @param xBottomCenterStart The X,Y coord
     * @param yBottomCenterStart The X,Y coord 
     * @param angleStart The angle of the bullet path
     * @return   This should be a specific sub-class of Bullet
     */   
    public GreenEnergyBullet getNewBullet (Group gRoot, double xBottomCenterStart, double yBottomCenterStart, int angleStart) {
        return new GreenEnergyBullet(gRoot, xBottomCenterStart, yBottomCenterStart, angleStart);
    };
    /**  
     * Reset the gun for a new game
     */ 
    public void reset (){
        setAngle(0);
        modifyEnergy (MAX_ENERGY-gunEnergy);
    };          
    /**
     * Create the image of the gun.
     */ 
    @Override
    public void draw () { 
        
        gunBody = new ImageView(new Image(getClass().getClassLoader().getResource(GUN_IMAGE).toString()));
        gunBody.getTransforms().add(new Scale(xSize / GUN_IMAGE_X_SIZE, ySize / GUN_IMAGE_Y_SIZE, 0, 0));
        gunBody.setTranslateX(pivotPointX - (xSize/2));
        gunBody.setTranslateY(pivotPointY - ySize + (ySize - lengthOfBarrel));
        
        gunBodyGroup.getChildren().add(gunBody); 

        Rectangle gunEnergyBox = new Rectangle(pivotPointX-200, pivotPointY-40, 100, 30);
        gunEnergyBox.setStroke(Color.WHITE);
        gunEnergyBox.setFill(Color.GRAY);
        gunEnergyBox.setStrokeWidth(3);
        
        gunEnergyBar = new Rectangle(pivotPointX-197, pivotPointY-37, 94, 24);
        gunEnergyBar.setFill(Color.GREEN);
        
        gunEnergyBarScale = new Scale();
        gunEnergyBarScale.setPivotX(pivotPointX-197);
        gunEnergyBarScale.setPivotY(pivotPointY-37);        
        gunEnergyBar.getTransforms().add(gunEnergyBarScale);        
        
        gunEnergyPercent = new Label(String.valueOf(gunEnergy)+"%");
        gunEnergyPercent.setTextFill(Color.WHITE);
        gunEnergyPercent.setTranslateX(pivotPointX-150);
        gunEnergyPercent.setTranslateY(pivotPointY-35);
        
        EventHandler<ActionEvent> energyRechargerHandler = e -> {
            modifyEnergy(ENERGY_ADD_PER_TICK);
        };
        energyRechargeAnimation = new Timeline(new KeyFrame(Duration.millis(ENERGY_TICK_IN_MS), energyRechargerHandler));
        energyRechargeAnimation.setCycleCount(Timeline.INDEFINITE);
        energyRechargeAnimation.play();
                
        this.getChildren().add(gunEnergyBox);
        this.getChildren().add(gunEnergyBar);
        this.getChildren().add(gunEnergyPercent);
        this.getChildren().add(gunBodyGroup); 
    }  

    /**
    /**
     * Potentially fire the gun, assuming the gun is ready based on fire rate.
     * @param timestamp   A ns timestamp provided by an AnimationTimer
     */  
    @Override
    public void fireGun (long timestamp){
        if ((timestamp-lastFired >= getFireRate()*(long)1000000000) 
                && (gunEnergy >= ENERGY_SPENT_PER_SHOT)) {
            bulletList.add(getNewBullet(bullets, getFiringStartPointX(), getFiringStartPointY(), getAngle()));
            lastFired = timestamp;
            modifyEnergy(-ENERGY_SPENT_PER_SHOT);
        }
        soundEffect.play();
    };  
    
    /**
     * Modify the Gun's energy bar.
     * @param amountToAdd   Can be negative. Add or remove energy
     */ 
    private void modifyEnergy (int amountToAdd) {   
        gunEnergy += amountToAdd;
        if (gunEnergy < 0)
            gunEnergy = 0;
        if (gunEnergy > MAX_ENERGY)
            gunEnergy = MAX_ENERGY;
        gunEnergyPercent.setText(String.valueOf(gunEnergy)+"%");
        gunEnergyBarScale.setX((double)gunEnergy/MAX_ENERGY);
    }      

    /**
     * Sets pause or unpause
     * @param sPause True for paused, False for unpaused
     */   
    public void setPause(boolean sPause) {
        if (sPause && !paused) {
            energyRechargeAnimation.pause();
            paused = true;
        }
        if (!sPause && paused) {
            energyRechargeAnimation.play();
            paused = false;            
        }
    }     
}