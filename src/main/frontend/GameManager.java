package main.frontend;

import java.util.*;

import javafx.event.*;
import javafx.geometry.Point2D;
import javafx.scene.input.*;
import javafx.animation.*;
import javafx.scene.*;
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

    public static void clearScreen() {
        Node p = screen.getChildren().get(0);
        screen.getChildren().clear();
        screen.getChildren().add(p);
    }

    public static void initializeRoom(Pane screen, Scene scene) {
        mousePosition = new Point2D(400, 400);
        GameManager.screen = screen;
        GameManager.scene = scene;
        setKeybinds();
    }

    public static void destroyImage(ArrayList<ImageView> images) {
        screen.getChildren().removeAll(images);
    }

    public static void initializeLevel() {
        screen.getChildren().addAll(Controller.getPlayerImage());
        Controller.initializeLevel();
    }

    public static void gameLoop() {
        initializeLevel();
        timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                Controller.run(mousePosition);
            }
        };
        timer.start();
    }
}
