package main.frontend;

import java.util.*;

import javafx.event.*;
import javafx.scene.input.*;
import javafx.animation.*;
import javafx.scene.*;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import main.backend.Controller;

public class GameManager {
    private static AnimationTimer timer;
    private static Pane screen = new StackPane();
    private static Scene scene;

    private static void setKeybinds() {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                Controller.setDirection(event.getCode().toString(), true);
            }
        });
        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                Controller.setDirection(event.getCode().toString(), false);
            }
        });
    }

    public static void setScreen(Pane screen, Scene scene) {
        GameManager.screen = screen;
        GameManager.scene = scene;
        screen.getChildren().addAll(Controller.getPlayerImage());
        setKeybinds();
    }

    public static void clearScreen() {
        screen.getChildren().clear();
    }

    public static void destroyImage(ImageView image) {
        screen.getChildren().remove(image);
    }

    public static void initializeEmptyRoom(Pane screen, Scene scene) {
        setScreen(screen, scene);
    }

    public static void generateEnemies() {
        HashMap<String, Integer> enemies = new HashMap<>();
        enemies.put("ghost", 1);
        screen.getChildren().addAll(Controller.generateEnemies(enemies, MainScreen.getLength(), MainScreen.getHeight()));
    }

    public static void gameLoop() {
        timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                Controller.run();
            }
        };
        timer.start();
    }
}
