package main.frontend;

import java.util.*;

import com.sun.tools.javac.Main;
import javafx.event.*;
import javafx.geometry.Point2D;
import javafx.scene.input.*;
import javafx.animation.*;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import main.backend.Controller;

/**
 * Manages the game loop.
 * All communication from the backend to the frontend comes through the GameManager.
 */
public class GameManager {
    private static AnimationTimer timer;    //controls the game loop, running at 60 fps
    private static Pane screen = new StackPane();    //reference to the current room screen
    private static Scene scene;    //reference to the current room scene
    private static Point2D mousePosition;    //monitors where the mouse is located on the screen

    /**
     * Sets all the keybinds for player actions.
     */
    private static void setKeybinds() {
        //event for when a key is pressed and/or held down
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.H) {
                    MainScreen.setScene(InventoryScreen.getScene());
                } else if (event.getCode() == KeyCode.G) {
                    MainScreen.setScene(ShopScreen.getScene());
                } else {
                    Controller.setDirection(event.getCode().toString(), true);
                }
                //set the direction which the player is traveling
                Controller.setDirection(event.getCode().toString(), true);
            }
        });

        //event for when a key being held down is released
        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                //set the direction which the player is traveling
                Controller.setDirection(event.getCode().toString(), false);
            }
        });

        //event for when the mouse moves
        scene.setOnMouseMoved(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                //keep track of the mouse's current position
                mousePosition = new Point2D(event.getSceneX(), event.getSceneY());
            }
        });
    }

    /**
     * Initializes the game, updating class variables and setting the keybinds.
     * @param screen The current room screen
     * @param scene The current room scene
     */
    public static void initializeGame(Pane screen, Scene scene) {
        mousePosition = new Point2D(400, 400);
        GameManager.screen = screen;
        GameManager.scene = scene;
        setKeybinds();
    }

    /**
     * Removes sprites from the screen.
     * Used primarily to remove dead enemy sprites.
     * @param images the ImageViews of sprites to remove
     */
    public static void destroyImage(ArrayList<ImageView> images) {
        screen.getChildren().removeAll(images);
    }

    /**
     * Initialize a new level.
     */
    public static void initializeLevel() {
        //initializes the backend to a new level
        Controller.initializeLevel();
        //initialize sprites on screen
        screen.getChildren().addAll(Controller.getCurrentRoomImages());
        screen.getChildren().addAll(Controller.getCurrentRoomWalls());
    }

    /**
     * Move player sprite to a new room.
     */
    public static void changeRoom() {
        //clear all the sprites in the current room from the screen
        Room.reset();
        //initialize sprites on screen
        screen.getChildren().addAll(Controller.getCurrentRoomImages());
        screen.getChildren().addAll(Controller.getCurrentRoomWalls());
    }

    /**
     * Stops the game loop once user has exited.
     */
    public static void stopGameLoop() {
        if (timer == null) {
            return;
        }
        timer.stop();
    }

    /**
     * Display the game's win condition.
     */
    public static void endGame() {
        stopGameLoop();
        MainScreen.setScene(EndGame.getScene());
    }

    /**
     * Starts the game loop, allowing the player to interact with the game
     * by moving, killing enemies, etc.
     */
    public static void gameLoop() {
        initializeLevel();
        if (timer == null) {
            //runs at 60 fps
            timer = new AnimationTimer() {
                @Override
                public void handle(long l) {
                    Controller.run(mousePosition);   //runs every frame
                }
            };
        }
        timer.start();
    }
}
