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
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 * A simply draw gun, shooting simple ball bullets.
 * @author Mark Knapp
 */
public class GunSimple extends Gun {
    
    public GunSimple (Group gBullets, ArrayList<Bullet> gBulletlist, double baseX, double baseY) {
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
        lengthOfBarrel          = 120;
        delayBetweenShots       = 0.4;
        keyboardMovementDelta   = 2;
        gunAngleLimit           = 80;
        gunAngle                = 0;
        // Not really used for simple Gun. Can fix later if I care.
        xSize                   = 40;
        // Not really used for simple Gun. Can fix later if I care.
        ySize                   = 120;
    }
    
    /**
     * Get the bullet sub-class that this gun fires.
     * @param gRoot The bullet group that the new bullet should join
     * @param xBottomCenterStart The X,Y coord
     * @param yBottomCenterStart The X,Y coord 
     * @param angleStart The angle of the bullet path
     * @return   This should be a specific sub-class of Bullet
     */   
    public BulletSimple getNewBullet (Group gRoot, double xBottomCenterStart, double yBottomCenterStart, int angleStart) {
        return new BulletSimple(gRoot, xBottomCenterStart, yBottomCenterStart, angleStart);
    }; 

    /**
     * Reset the gun for a new game
     */ 
    public void reset (){
        setAngle(0);
    };      
    
    /**
     * Create the image of the gun.
     */ 
    @Override
    public void draw () { 
        final Circle gunBase = new Circle(pivotPointX, pivotPointY, 50, Color.GREY);
        
        final Rectangle gunMouth = new Rectangle(pivotPointX-20, pivotPointY-lengthOfBarrel, 40, 20);
        gunMouth.setFill(Color.DARKGREY);       
 
        final Rectangle gunBarrel = new Rectangle(pivotPointX-10, pivotPointY-lengthOfBarrel, 20, lengthOfBarrel);
        gunBarrel.setStroke(Color.DARKGREY);
        gunBarrel.setFill(Color.GREY);
        gunBarrel.setStrokeWidth(3);
        
        gunBodyGroup.getChildren().add(gunBarrel); 
        gunBodyGroup.getChildren().add(gunMouth);
        gunBodyGroup.getChildren().add(gunBase);
        
        this.getChildren().add(gunBodyGroup); 
    }  
}