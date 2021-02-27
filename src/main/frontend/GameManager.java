package main.frontend;

import javafx.animation.*;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import main.backend.characters.*;

public class GameManager {
    private static AnimationTimer timer;
    private static Pane screen = new StackPane();
    private static Scene scene;

    public static void setScreen(Pane screen, Scene scene) {
        clearScreen();
        GameManager.screen = screen;
        GameManager.scene = scene;
        addImage(Player.getInstance().getImage());
    }

    public static void clearScreen() {
        screen.getChildren().clear();
    }

    public static void addImage(ImageView image) {
        screen.getChildren().add(image);
    }

    public static void destroyImage(ImageView image) {
        screen.getChildren().remove(image);
    }

    public static void gameLoop() {
        timer = new AnimationTimer() {
            @Override
            public void handle(long l) {

            }
        };
        timer.start();
    }
}
