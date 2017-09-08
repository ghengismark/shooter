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
import javafx.scene.Group;
import javafx.scene.transform.Rotate;

/**
 * This is the base-class for the Gun.
 * Extend it for specific guns.
 * @author Mark Knapp
 */
abstract class Gun extends Group {
    
    // Angle in degrees. '0' is 12 o'clock. Can be negative.
    protected int     gunAngle;
    
    // The farthest the gun can move, positive or negative
    protected int     gunAngleLimit;
    
    // The speed per frame in degrees that the gun rotates
    protected int     keyboardMovementDelta;
    
    // The distance from the base (fulcrum) of the gun to the end where the bullets should come out
    protected double  lengthOfBarrel;
    
    // The desired X,Y size of the sprite
    protected double  xSize;
    protected double  ySize;     
    
    // Delay between gun shots, in seconds. The lower, the faster.
    protected double  delayBetweenShots;
    
    // True is paused, false is unpaused
    protected boolean paused = false;    
    
    protected double            pivotPointX;
    protected double            pivotPointY;
    protected Group             gunBodyGroup;
    protected Group             bullets;
    protected ArrayList<Bullet> bulletList;
    protected long              lastFired = 0;

    public Gun (Group gBullets, ArrayList<Bullet> gBulletList, double baseX, double baseY) {
        pivotPointX = baseX;
        pivotPointY = baseY;
        bulletList = gBulletList;
        bullets = gBullets;
        gunBodyGroup = new Group();
        
        init();
        
        draw();
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
    abstract void init ();
    
    /**
     * Create the image of the gun.
     */ 
    abstract void draw ();  

    /**
     * Reset the gun for a new game
     */ 
    abstract void reset ();  
    
    /**
     * Get the bullet sub-class that this gun fires.
     * @param gRoot The bullet group that the new bullet should join
     * @param xBottomCenterStart The X,Y coord
     * @param yBottomCenterStart The X,Y coord 
     * @param angleStart The angle of the bullet path
     * @return   This should be a specific sub-class of Bullet
     */  
    abstract Bullet getNewBullet (Group gRoot, double xBottomCenterStart, double yBottomCenterStart, int angleStart);    

    /**
    /**
     * Potentially fire the gun, assuming the gun is ready based on fire rate.
     * @param timestamp   A ns timestamp provided by an AnimationTimer
     */  
    public void fireGun (long timestamp){
        if ((timestamp-lastFired >= getFireRate()*(long)1000000000)) {
            bulletList.add(getNewBullet(bullets, getFiringStartPointX(), getFiringStartPointY(), getAngle()));
            lastFired = timestamp;
        }
    };     
    
    /**
     * Rotate the gun a fixed amount based on settings
     *
     * @param   clockwise    The direction of rotation. clockwise or counter
     */  
    public void rotateGun (boolean clockwise) {
        if (!paused)
            setAngle(gunAngle + keyboardMovementDelta * (clockwise ? 1 : -1));
    }    
    
    /**
     * Simple getter of what angle the gun in pointing to
     *
     * @return     Angle in degrees. '0' is 12 o'clock. Can be negative.
     */   
    public int getAngle() {
        return gunAngle;
    }
    
    /**
     * Set the angle the gun should be pointing to. Limited by gunAngleLimit.
     *
     * @param   angle    Angle in degrees. '0' is 12 o'clock. Can be negative.
     */  
    public void setAngle(int angle) {
        if (angle < -gunAngleLimit) {
            angle = -gunAngleLimit;
        }
        if (angle > gunAngleLimit) {
            angle = gunAngleLimit;
        }
        gunBodyGroup.getTransforms().add(new Rotate(angle-gunAngle, pivotPointX, pivotPointY));
        gunAngle = angle;
    }

    /**
     * A simple getter
     * @return              Gun angle limit
     */  
    public int getAngleLimit() {
        return gunAngleLimit;
    }
    
    /**
     * A simple setter. 
     * Moves the gun if it is out-of-bounds of the new value.
     * @param   angle    Angle in degrees. '0' is 12 o'clock. Can be negative.
     */  
    public void setAngleLimit(int angle) {
        if ((angle > -gunAngle) || (angle < gunAngle)) {
            setAngle(angle);
        }
        gunAngleLimit = angle;
    }      

    /**
     * Calculate the X location of the tip of the gun barrel
     * @return              The X coordinate
     */  
    public double getFiringStartPointX() {
        return pivotPointX + Math.cos(Math.toRadians(gunAngle-90)) * lengthOfBarrel;
    }
    
    /**
     * Calculate the Y location of the tip of the gun barrel
     * @return              The Y coordinate
     */   
    public double getFiringStartPointY() {
        return pivotPointY + Math.sin(Math.toRadians(gunAngle-90)) * lengthOfBarrel;
    } 
  
    /**
     * A simple getter
     * @return              Delay between gun shots, in seconds. The lower, the faster.
     */  
    public double getFireRate() {
        return delayBetweenShots;
    }
    
    /**
     * A simple setter
     * @param   rate    Delay between gun shots, in seconds. The lower, the faster.
     */  
    public void setFireRate(double rate) {
        delayBetweenShots = rate;
    }     
    
    /**
     * A simple getter
     * @return              The speed per frame in degrees that the gun rotates
     */ 
    public int getKeyboardMovementDelta() {
        return keyboardMovementDelta;
    }
    
    /**
     * A simple setter
     * @param   delta    The speed per frame in degrees that the gun rotates
     */    
    public void setKeyboardMovementDelta(int delta) {
        keyboardMovementDelta = delta;
    } 
    
    /**
     * Sets pause or unpause
     * @param sPause True for paused, False for unpaused
     */   
    public void setPause(boolean sPause) {
        if (sPause && !paused) {
            paused = true;
        }
        if (!sPause && paused) {
            paused = false;            
        }
    } 
    
    /**
     * Get pause or unpause
     * @return True for paused, False for unpaused
     */   
    public boolean getPause() {
        return(paused);
    }     
    
}
