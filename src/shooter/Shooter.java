/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shooter;

import java.util.*;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import static javafx.scene.input.KeyCode.SPACE;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

/**
 *
 * @author Mark Knapp
 */
public class Shooter extends Application {   
    
    // Total screen size of game
    protected final double              screenX                 = 800;
    protected final double              screenY                 = 600;
    
    // The background image
    public final static String          BACKGROUND_IMG          = "resources/images/night_background.jpeg";
    public final static double          BACKGROUND_SIZE_X       = 1920;
    public final static double          BACKGROUND_SIZE_Y       = 1200;
    public final        double          backgroundScaleX        = screenX / BACKGROUND_SIZE_X;
    public final        double          backgroundScaleY        = screenY / BACKGROUND_SIZE_Y;
    protected           ImageView       imgBackground;    

    //Background Music
    protected           AudioClip       backgroundMusic;
    public final static String          BACKGROUND_MUSIC_FILE   = "resources/audio/BackgroundMusic.mp3";

    // The starting enemy spawn rate. The number of seconds delay between spawns.
    protected       double              startingSpawnRate       = 1;
    
    // Changes over time based on the spawnRateProgression
    protected       double              currentSpawnRate        = startingSpawnRate;
    
    // The multiplier to decrease spawn delay (increase spawn rate) with each spawn
    protected       double              spawnRateProgression    = 0.995; 

    // The percentage chance of a specific type of enemy spawning
    protected       double              enemyBatChance          = 30;
    protected       double              enemyChopperChance      = 30;
    protected       double              enemyBeeChance          = 100 - enemyBatChance - enemyChopperChance;
    
    // The buffer on the sides of the screen for the enemies start. 
    protected       double              enemyBatXBuffer          = 150;
    protected       double              enemyChopperXBuffer      = 80;
    protected       double              enemyBeeXBuffer          = 80;
    
    // The max angle deviation from straight down
    protected       int                 enemyMaxAngle            = 15;
    
    protected       BooleanProperty     aPressed                = new SimpleBooleanProperty();
    protected       BooleanProperty     qPressed                = new SimpleBooleanProperty();
    protected       BooleanProperty     dPressed                = new SimpleBooleanProperty();
    protected       BooleanProperty     ePressed                = new SimpleBooleanProperty();
    protected       BooleanProperty     pPressed                = new SimpleBooleanProperty();
    protected       BooleanProperty     oPressed                = new SimpleBooleanProperty();
    protected       BooleanProperty     spacePressed            = new SimpleBooleanProperty();
    protected       BooleanBinding      anyPressed              = aPressed.or(qPressed).or(dPressed).or(ePressed).or(spacePressed);
    
    protected       ArrayList<Enemy>    enemyList;
    protected       ArrayList<Bullet>   bulletList; 
    protected       Scene               scene;
    protected       Text                text;
    protected       Stage               mainStage;    
    protected       Group               root;
    protected       Group               bullets;
    protected       Group               enemies;
    protected       Gun                 gun;
    
    protected       Random              diceRoller              = new Random();
    
    protected       boolean             paused                  = false;
    protected       boolean             youLose                 = false;
    
    protected       AnimationTimer      statusTimer;
    protected       AnimationTimer      keyPressTimer;    
    protected       AnimationTimer      spawnTimer;    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {   
        
        mainStage = primaryStage;
        mainStage.setTitle("Mark's Shooter");
        
        root = new Group();
        scene = new Scene(root, screenX, screenY, Color.BLACK);
        mainStage.setScene(scene);
        enemyList = new ArrayList<Enemy>();
        bulletList = new ArrayList<Bullet>();
        bullets = new Group();
        enemies = new Group();
        text = new Text();
        gun = new GreenEnergyGun(bullets, bulletList, screenX/2, screenY);
        
        imgBackground = new ImageView(new Image(getClass().getClassLoader().getResource(BACKGROUND_IMG).toString()));
        imgBackground.getTransforms().add(new Scale(backgroundScaleX, backgroundScaleY, 0, 0));
        
        root.getChildren().add(imgBackground);
        root.getChildren().add(gun);
        root.getChildren().add(bullets);
        root.getChildren().add(enemies);        
        root.getChildren().add(text.createInstructions(20,20));
        root.getChildren().add(text.createScore(screenX-200,20));
        
        backgroundMusic = new AudioClip(getClass().getClassLoader().getResource(BACKGROUND_MUSIC_FILE).toString());
        backgroundMusic.play();
        
        
        setupKeyPresses(scene);
        handleKeyPress(gun);
        statusUpdate();
        spawnEnemies();
        
        mainStage.show();
    }

    /**
     * Resets game.
     * Basically create everything new. The Java GC will take care of the old
     * after we disconnect it.
     * 
     * NOTE: Currently disabled since it causes crashes. Just using it as init
     * for the moment
     */   
    public void reset() {
        setPause(true);
        currentSpawnRate = startingSpawnRate;
        youLose = false;
        gun.reset();
        root.getChildren().remove(text.loseLabel);
        text.setScore(0);
        clearSprites();
        setPause(false);
    }  
    
    /**
     * Deletes all sprites from lists and nodes
     */   
    public void clearSprites() {
        for (Iterator<Enemy> enemyIterator = enemyList.iterator(); enemyIterator.hasNext();){
            Enemy enemyItem = enemyIterator.next();
            enemies.getChildren().remove(enemyItem);
            enemyIterator.remove();
        }
        for (Iterator<Bullet> bulletIterator = bulletList.iterator(); bulletIterator.hasNext();){
            Bullet bulletItem = bulletIterator.next();
            bullets.getChildren().remove(bulletItem);
            bulletIterator.remove();
        }
    }
    
    /**
     * Registers key press events to the universal booleans.
     * @param   scene    The root scene the keys are harvested from.
     */   
    private void setupKeyPresses(Scene scene) {
        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case P: pPressed.set(true); break; 
                case O: oPressed.set(true); break; 
                case A: aPressed.set(true); break;
                case Q: qPressed.set(true); break;                    
                case E: ePressed.set(true); break;
                case D: dPressed.set(true); break; 
                case SPACE: spacePressed.set(true); break;
            }   
        });

        scene.setOnKeyReleased(e -> {
            switch (e.getCode()) {
                case P: pPressed.set(false); break; 
                case O: oPressed.set(false); break; 
                case A: aPressed.set(false); break;
                case Q: qPressed.set(false); break;                    
                case E: ePressed.set(false); break;
                case D: dPressed.set(false); break; 
                case SPACE: spacePressed.set(false); break;
            }   
        });          
    } 
    
    /**
     * Take action on key presses
     * 
     * @param   gun     We need to pass in the gun object that the keys control
     * @see                 AnimationTimer, anyPressed
     */   
    private void handleKeyPress(Gun gun) {

        keyPressTimer = new AnimationTimer() {
            long lastBullet = 0;
            long lastPause = 0;
            long lastReset = 0;
            @Override
            public void handle(long timestamp) {
                if (aPressed.get() && !paused) {
                    gun.rotateGun(false);
                }
//                if (qPressed.get() && !paused) {
//                    gun.rotateGun(false, true);
//                }
                if (dPressed.get() && !paused) {
                    gun.rotateGun(true);
                }
//                if (ePressed.get() && !paused) {
//                    gun.rotateGun(true, true);
//                }
                if (pPressed.get()) {
                    if ((timestamp-lastPause >= 1*(long)1000000000)) {
                        togglePause();
                        lastPause = timestamp;
                    }
                }
                if (oPressed.get()) {
                    if ((timestamp-lastReset >= 1*(long)1000000000)) {
                        lastReset = timestamp;
                        reset();
                    }                    
                }                
                if (spacePressed.get() && !paused) {
                    gun.fireGun(timestamp);
                }
            }
        };
        keyPressTimer.start();
//        anyPressed.addListener((obs, wasPressed, isNowPressed) -> {
//            if (isNowPressed) {
//                keyPressTimer.start();
//            } else {
//                keyPressTimer.stop();
//            }
//        });
    }

    
    /**
     * A constant timer that does various maintenance of game status.
     * Tracks and updates: Object collisions, game loss conditions, 
     * object cleanup, score maintenance, etc.
     * @see                 AnimationTimer
     */    
    private void statusUpdate() {
        statusTimer = new AnimationTimer() {
            @Override
            public void handle(long timestamp) {
                for (Iterator<Enemy> enemyIterator = enemyList.iterator(); enemyIterator.hasNext();){
                    Enemy enemyItem = enemyIterator.next();
                    
                    // Houseclean so the GC can remove
                    if (enemyItem.isDead()) {
                        enemyIterator.remove();
                        continue;
                    }
                    
                    // Don't let enemies go off-screen
                    if (enemyItem.getTopLeftX() < 5) {
                        enemyItem.moveTo(5, enemyItem.getTopLeftY());
                    }
                    if ((enemyItem.getTopLeftX() + enemyItem.getSizeX()) > (screenX - 5)) {
                        enemyItem.moveTo((screenX - 5 - enemyItem.getSizeX()), enemyItem.getTopLeftY());
                    }
                    
                    // Check if any enemies made it to the bottom. YOU LOSE if so.
                    if ((enemyItem.getCenterY() > screenY) && !youLose) {
                        root.getChildren().add(text.createLoseText(245, 300));
                        setPause(true);
                        youLose = true;
                    }
                    
                    for (Iterator<Bullet> bulletIterator = bulletList.iterator(); bulletIterator.hasNext();){
                        Bullet bulletItem = bulletIterator.next();
                        
                        // Houseclean so the GC can remove
                        if (bulletItem.isDead()) {
                            bulletIterator.remove();
                            continue;
                        }
                        
                        // Check if any bullets hit any enemies
                        if (bulletItem.hit(enemyItem))  {
                            enemyItem.startDeath();
                            enemyIterator.remove();
                            bulletItem.startDeath();
                            bulletIterator.remove();
                            text.addScore(10);
                        } 
                    }
                }
            }
        };
        statusTimer.start();
    }     

    /**
     * Creates new enemies in random locations, at a controlled rate
     * @see                 AnimationTimer
     */   
    private void spawnEnemies() {
        spawnTimer = new AnimationTimer() {
            long lastSpawn = 0;
            int randomNumber;
            @Override
            public void handle(long timestamp) {
                if ((timestamp-lastSpawn >= currentSpawnRate*(long)1000000000)) {
                    
                    // Increment the spawn rate with each pass
                    currentSpawnRate *= spawnRateProgression;
                    
                    // Pre-calc some coords
                    double  xBatStart       = (double)(diceRoller.nextInt((int) (screenX - (enemyBatXBuffer * 2))) + enemyBatXBuffer);
                    double  xChopperStart   = (double)(diceRoller.nextInt((int) (screenX - (enemyChopperXBuffer * 2))) + enemyChopperXBuffer);
                    double  xBeeStart       = (double)(diceRoller.nextInt((int) (screenX - (enemyBeeXBuffer * 2))) + enemyBeeXBuffer);
                    int     xAngleStart     = diceRoller.nextInt(enemyMaxAngle * 2) + 180 - enemyMaxAngle;
                    
                    // Randomize which enemies we get
                    randomNumber = diceRoller.nextInt(100);
                    if (randomNumber >= (100 - enemyBatChance)) {
                        enemyList.add(new EnemyBat(enemies, xBatStart, -30, xAngleStart));
                    } else if (randomNumber >= (100 - enemyBatChance - enemyChopperChance)) {    
                        enemyList.add(new EnemyChopper(enemies, xChopperStart, -25, xAngleStart));
                    } else {
                        enemyList.add(new EnemyBee(enemies, xBeeStart, -45, xAngleStart));
                    }
                    lastSpawn = timestamp;
                }
            }
        };
        spawnTimer.start();
    } 

    /**
     * Toggles pause or unpause
     */   
    public void togglePause() {
        setPause(!paused);
    }
    
    /**
     * Sets pause or unpause
     * @param sPause True for paused, False for unpaused
     */   
    public void setPause(boolean sPause) {
        if (sPause)
            spawnTimer.stop();
        else
            spawnTimer.start();
        paused = sPause;
        gun.setPause(sPause);
        for (Iterator<Enemy> enemyIterator = enemyList.iterator(); enemyIterator.hasNext();){
            Enemy enemyItem = enemyIterator.next();
            enemyItem.setPause(sPause);
        }
        for (Iterator<Bullet> bulletIterator = bulletList.iterator(); bulletIterator.hasNext();){
            Bullet bulletItem = bulletIterator.next();
            bulletItem.setPause(sPause);
        }
    }       
    
}