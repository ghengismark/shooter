/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shooter;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Generic sprite object to be used as the base for all other moving objects
 * @author Mark Knapp
 */
abstract class Sprite extends Group {
 
    // The top "0,0" X,Y current location of the sprite
    protected double  xTopLeftLoc;
    protected double  yTopLeftLoc;    
    
    // The center X,Y current location of the sprite
    protected double  xCenterLoc;
    protected double  yCenterLoc;    

    // The bottom (y) middle (x) current location of the sprite
    protected double  xBottomCenterLoc;
    protected double  yBottomCenterLoc;      
    
    // The X,Y delta that the sprite will move in a frame. This is calculated based on angle and speed.
    protected double  xVel;
    protected double  yVel;
    
    // The desired X,Y size of the sprite
    protected double  xSize;
    protected double  ySize;    
    
    // The distance in points the sprite will move per frame.
    protected double  speed;
    
    // The radius of space the sprite occupies. This determines collision space.
    protected double  radius;
    
    // Angle in degrees of sprite path. '0' is 12 o'clock. Can be negative.
    protected int     angle;
    
    // Number of seconds until the sprite dies, to save memory. Should be off-screen when this happens.
    protected double  timeToLive;
    
    // If the sprite should stop moving for any reason.
    protected boolean stop = false;

    // If the sprite is considered dead and ready for clean-up.
    protected boolean isDead  = false;
    protected boolean isDying = false;
    
    // Basic color
    protected Color color;
    
    // True is paused, false is unpaused
    protected boolean paused = false;
    
    protected Group   root;
    protected MyTimer spriteTimer;
    protected Circle  circle;
     
    public Sprite (Group gRoot, double xStart, double yStart, boolean topLeftXY, int angleStart, double speedStart) {         
        angle = angleStart;
        root = gRoot;
        setSpeed(speedStart);
        
        init();
        
        if (topLeftXY)
            setXYBasedOnTopLeft(xStart, yStart);
        else
            setXYBasedOnBottomCenter(xStart, yStart);
                
        draw();
        root.getChildren().add(this);
        
        // Class defined at bottom of this class.
        spriteTimer = new MyTimer();
        spriteTimer.start();
    }

    /**
     * This method should be overriden to set up radius, size, timeToLive, etc.
     * 
     */  
    abstract void init ();
    
    /**
     * Create the image of the sprite.
     */  
    abstract void draw ();

    /**
     * Move the sprite to a new X,Y (TopLeft)
     *
     * @param   xTopLeft    The X coordinate of the desired location
     * @param   yTopLeft    The Y coordinate of the desired location
     */  
    public void moveTo (double xTopLeft, double yTopLeft) { 
        this.setTranslateX(xTopLeft - xTopLeftLoc + this.getTranslateX());
        this.setTranslateY(yTopLeft - yTopLeftLoc + this.getTranslateY());
        setXYBasedOnTopLeft(xTopLeft, yTopLeft);
    }

    /**
     * Calculate XY coordinates assuming we are given x,y for top-left.
     */  
    public void setXYBasedOnTopLeft (double x, double y) { 
        xTopLeftLoc = x;
        yTopLeftLoc = y;
        xBottomCenterLoc = x + (xSize/2);
        yBottomCenterLoc = y + ySize;
        xCenterLoc =  x + (xSize/2);
        yCenterLoc =  y + (ySize/2);
    }    

    /**
     * Calculate XY coordinates assuming we are given x,y for bottom-center.
     */  
    public void setXYBasedOnBottomCenter (double x, double y) { 
        xBottomCenterLoc = x;
        yBottomCenterLoc = y;
        xTopLeftLoc = x - (xSize/2);
        yTopLeftLoc = y - ySize;
        xCenterLoc = x;
        yCenterLoc = y - (ySize/2);
    }     
    
    /**
     * Move the sprite on it's set path and velocity.
     */  
    public void move () { 
        moveTo(xTopLeftLoc+xVel, yTopLeftLoc+yVel);
    }
 
    /**
     * Start the death process.
     * Start death animations, housekeeping, etc.
     */  
    public void startDeath () { 
        isDying = true;
        stop = true;
        finishDeath();
    }
    
    /**
     * Finish the death process.
     * Finally terminate the object.
     */  
    public void finishDeath () { 
        this.setOpacity(0);
        root.getChildren().remove(this);        
        isDead = true;
    }

    /**
     * A simple getter
     * @return    If the sprite is in the process of dying but not dead yet.
     */  
    public boolean isDying () { 
        return isDying;
    }    
    
    /**
     * A simple getter
     * @return    If the sprite is considered dead and ready for clean-up.
     */  
    public boolean isDead () { 
        return isDead;
    }    
    
    /**
     * A simple setter.
     * Calculates velocity too.
     *
     * @param   sSpeed    The distance in points the sprite will move per frame.
     */  
    public void setSpeed (double sSpeed) { 
        speed = sSpeed;
        xVel = Math.cos(Math.toRadians(angle-90)) * speed;
        yVel = Math.sin(Math.toRadians(angle-90)) * speed;
    }    
    
    /**
     * A simple getter
     * @return              The current X position of the sprite.
     */  
    public double getCenterX() {
        return xCenterLoc;
    }

    /**
     * A simple getter
     * @return              The current Y position of the sprite.
     */  
    public double getCenterY() {
        return yCenterLoc;
    }

    /**
     * A simple getter
     * @return              The current X position of the sprite.
     */  
    public double getTopLeftX() {
        return xTopLeftLoc;
    }

    /**
     * A simple getter
     * @return              The current Y position of the sprite.
     */  
    public double getTopLeftY() {
        return yTopLeftLoc;
    }    
    
    /**
     * A simple getter
     * @return              The current radius of the sprite.
     */  
    public double getRadius() {
        return radius;
    }
    
    /**
     * A simple getter
     * @return              The current X size of the sprite.
     */  
    public double getSizeX() {
        return xSize;
    }

    /**
     * A simple getter
     * @return              The current Y size of the sprite.
     */  
    public double getSizeY() {
        return ySize;
    }  
    
    /**
     * Does the math to see if this sprite collides with another.
     * @param   target    The Enemy object to check
     * @return            True/False if it IS a hit.
     */   
    public boolean hit(Sprite target) {
        double dx = target.getCenterX() - this.getCenterX();
        double dy = target.getCenterY() - this.getCenterY();
        double distance = Math.sqrt(dx * dx + dy * dy);
        boolean inRange = ((target.getRadius() + this.getRadius()) >= distance);
        boolean bothAlive = (!this.isDying() && !this.isDead() && !target.isDying() && !target.isDead());
        return (inRange && bothAlive);
    }
    
    /**
     * Sets pause or unpause
     * @param sPause True for paused, False for unpaused
     */   
    public void setPause(boolean sPause) {
        if (sPause && !paused) {
            spriteTimer.stop();
            paused = true;
        }
        if (!sPause && paused) {
            if (!isDead())
                    spriteTimer.start();
            spriteTimer.setTimerPaused(true);
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
    
    // We need to make a custom timer since we need to add some vars to mess with
    // the pause/unpause feature
    public class MyTimer extends AnimationTimer {

        private long    timealive   = 0;
        private long    lasttime    = 0;
        private boolean timerPaused = false;

        public MyTimer() {
            super();
        }           

        public void setTimerPaused(boolean stp) {
            timerPaused = stp;
        }

        @Override
        public void handle(long timestamp) {
            // Move along it's path.
            if (!stop)
                    move();
            // We need to do fancy math to stop the clock during a pause
            if (lasttime == 0) {
                lasttime = timestamp;
            } else { 
                if (!timerPaused) {
                    timealive += (timestamp - lasttime);
                } else {
                    timerPaused = false;
                }
                lasttime = timestamp;
            }
            // Kill the sprite after it has lived for a certain number of seconds.
            // It should be off-screen by then, and we want to avoid memory issues.
            if (timealive > timeToLive*1000000000) {
               this.stop();
               startDeath();
            }

        }
    }    
}

