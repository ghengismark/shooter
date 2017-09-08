/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shooter;

import javafx.animation.AnimationTimer;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 *
 * @author Mark Knapp
 */
public class Text {
    public  Label           scoreLabel;
    public  Label           loseLabel;
    private AnimationTimer  loserTimer;
    private int             score       =   0;
    
    public Text() {
    } 
    
    /**
     * Create instructions on screen
     * @param   xLoc    The x coordinate of the top-left corner
     * @param   yLoc    The y coordinate of the top-left corner
     * @return              The JavaFX label object
     * @see                 Label
     */   
    public Label createInstructions(double xLoc, double yLoc) {
        
        
        Label instructions = new Label(
            "Use A and D to rotate the gun.\n" + 
            //"Use Q and E to quick rotate.\n" +
            "Use SPACE to fire.\n" +
            "Use P to pause and O to reset."
        );
        instructions.setTextFill(Color.WHITE);
        instructions.setTranslateX(xLoc);
        instructions.setTranslateY(yLoc);
        return instructions;
    } 
    
    /**
     * Create YOU LOSE on screen
     * @param   xLoc    The x coordinate of the top-left corner
     * @param   yLoc    The y coordinate of the top-left corner
     * @return              The JavaFX label object
     * @see                 Label
     */   
    public Label createLoseText(double xLoc, double yLoc) {
        loseLabel = new Label("GAME OVER");
        loseLabel.setFont(new Font("Arial", 50));
        loseLabel.setTextFill(Color.WHITE);
        loseLabel.setTranslateX(xLoc);
        loseLabel.setTranslateY(yLoc);
        
        loserTimer = new AnimationTimer() {
            long lastFlash = 0;
            boolean lastColor = true;
            @Override
            public void handle(long timestamp) {
                if (lastFlash == 0)
                    lastFlash = timestamp;
                if ((timestamp-lastFlash >= 0.25*(long)1000000000)) {
                    if (lastColor) {
                        loseLabel.setTextFill(Color.RED);
                    } else {
                        loseLabel.setTextFill(Color.WHITE);
                    }
                    lastColor = !lastColor;
                    lastFlash = timestamp;
                }
            }
        };
        loserTimer.start();
        
        return loseLabel;
    } 
    
    /**
     * Create YOU LOSE on screen
     * @param   xLoc    The x coordinate of the top-left corner
     * @param   yLoc    The y coordinate of the top-left corner
     * @return              The JavaFX label object
     * @see                 Label
     */ 
    public Label createScore(double xLoc, double yLoc) {
        
        scoreLabel = new Label(
            "Score: " + score 
        );
        scoreLabel.setTextFill(Color.WHITE);
        scoreLabel.setFont(new Font("Arial", 30));
        scoreLabel.setTranslateX(xLoc);
        scoreLabel.setTranslateY(yLoc);
        return scoreLabel;
    }
    
    /**
     * Simple setter
     * @param   sScore    The new score
     */ 
    public void setScore(int sScore) {       
        score = sScore;
        scoreLabel.setText("Score: " + score);
    }    

    /**
     * An additive setter
     * @param   sScore    The increment to be added to the score
     */ 
    public void addScore(int sScore) {     
        setScore(score+sScore);
    } 
    
    /**
     * Simple getter
     * @return    The current score
     */ 
    public int getScore() {       
        return score;
    }     
}
