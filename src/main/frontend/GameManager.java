package main.frontend;

import java.util.*;

import javafx.event.*;
import javafx.geometry.Point2D;
import javafx.scene.input.*;
import javafx.animation.*;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import main.backend.Controller;

public class GameManager {
    private static AnimationTimer timer;
    private static Pane screen = new StackPane();
    private static Scene scene;
    private static Point2D mousePosition;

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
        scene.setOnMouseMoved(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                mousePosition = new Point2D(event.getSceneX(), event.getSceneY());
            }
        });
    }

    public static void setScreen(Pane screen, Scene scene) {
        mousePosition = new Point2D(400, 400);
        GameManager.screen = screen;
        GameManager.scene = scene;
        screen.getChildren().addAll(Controller.getPlayerImage());
        Controller.setPlayerPosition(400, 400);
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
        screen.getChildren().addAll(Controller.generateEnemies(
                enemies,
                MainScreen.getLength(),
                MainScreen.getHeight())
        );
    }

    public static void gameLoop() {
        timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                Controller.run(mousePosition);
            }
        };
        timer.start();
    }
}
