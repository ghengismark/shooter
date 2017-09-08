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
 * Serves as the base for player-fired elements.
 * Extend this class for specialized bullets.
 *
 * @author Mark Knapp
 */
public class GreenEnergyBullet extends Bullet {
    
    public final static String                  BULLET1             = "resources/images/GreenEnergyBullet1.png";
    public final static String                  BULLET2             = "resources/images/GreenEnergyBullet2.png";
    public final static String                  BULLET3             = "resources/images/GreenEnergyBullet3.png";
    public final static String                  BULLET4             = "resources/images/GreenEnergyBullet4.png";
    
    public final static double                  BULLET_IMAGE_X_SIZE = 22;
    public final static double                  BULLET_IMAGE_Y_SIZE = 38;
    public final static double                  SIZE_MODIFIER = 1.5;
    
    protected           ArrayList<ImageView>    imgBullet;
    protected           Group                   bullet;
    protected           Timeline                bulletAnimation;   
    private             int                     imgIndex             = 0;
    
    public GreenEnergyBullet(Group gRoot, double xBottomCenterStart, double yBottomCenterStart, int angleStart) {
        super(gRoot, xBottomCenterStart, yBottomCenterStart, angleStart);
    }
     
    /**
     * Set up defaults
     */
    @Override
    public void init () { 
        
        xSize = BULLET_IMAGE_X_SIZE * SIZE_MODIFIER;
        ySize = BULLET_IMAGE_Y_SIZE * SIZE_MODIFIER;
        radius = BULLET_IMAGE_X_SIZE * SIZE_MODIFIER;
        timeToLive = 5;
    }

    /**
     * Create the image of the sprite.
     */  
    @Override
    public void draw () { 
        
        new GreenEnergyGunFlash(root, xBottomCenterLoc-((xSize/2)), yBottomCenterLoc-xSize, xSize, xSize, angle);
        
        imgBullet = new ArrayList<ImageView>();
        imgBullet.add(new ImageView(new Image(getClass().getClassLoader().getResource(BULLET1).toString())));
        imgBullet.add(new ImageView(new Image(getClass().getClassLoader().getResource(BULLET2).toString())));
        imgBullet.add(new ImageView(new Image(getClass().getClassLoader().getResource(BULLET3).toString())));
        imgBullet.add(new ImageView(new Image(getClass().getClassLoader().getResource(BULLET4).toString())));
        imgBullet.add(new ImageView(new Image(getClass().getClassLoader().getResource(BULLET3).toString())));
        imgBullet.add(new ImageView(new Image(getClass().getClassLoader().getResource(BULLET2).toString())));
        
        bullet = new Group(imgBullet.get(0));
        bullet.setTranslateX(xTopLeftLoc);
        bullet.setTranslateY(yTopLeftLoc);
        bullet.setScaleX(xSize / BULLET_IMAGE_X_SIZE);
        bullet.setScaleY(ySize / BULLET_IMAGE_Y_SIZE);
        this.getChildren().add(bullet);
        
        EventHandler<ActionEvent> bulletEventHandler = e -> {
            if (imgIndex > 5) 
                imgIndex = 0;
            bullet.getChildren().setAll(imgBullet.get(imgIndex++));
        };
            
        bulletAnimation = new Timeline(new KeyFrame(Duration.millis(10), bulletEventHandler));
        bulletAnimation.setCycleCount(Timeline.INDEFINITE);
        bulletAnimation.play();
    }  

    /**
     * Start the death process.
     * Start death animations, housekeeping, etc.
     */ 
    @Override
    public void startDeath () { 
        isDying = true;
        new GreenEnergyGunExplosion(root, xTopLeftLoc, yTopLeftLoc, xSize*1.5, xSize*1.5);
        finishDeath();
    }   
    
}   
