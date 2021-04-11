package main.frontend;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.*;
import javafx.scene.*;

/**
 * MainScreen contains the main window and changes between scenes.
 */
public class MainScreen extends Application {
    private static Stage stage;
    private static Scene currentScene;
    private static final double LENGTH = 800;
    private static final double HEIGHT = 800;
    private static final double MIN_X = 0;
    private static final double MIN_Y = 50;
    private static final double WALL_WIDTH = 10;
    private static final double DOOR_WIDTH = 250;
    
    public static void main(String[] args) {
        launch(args);
    }

    public static void setScene(Scene s) {
        currentScene = s;
        stage.setScene(s);
    }

    public static Scene getCurrentScene() {
        return currentScene;
    }

    public static double getWallWidth() {
        return WALL_WIDTH;
    }

    public static double getLength() {
        return LENGTH;
    }

    public static double getHeight() {
        return HEIGHT;
    }

    public static double getMinX() {
        return MIN_X;
    }

    public static double getMinY() {
        return MIN_Y;
    }

    public static double getMidX() {
        return (LENGTH - MIN_X) / 2 + MIN_X;
    }

    public static double getMidY() {
        return (HEIGHT - MIN_Y) / 2 + MIN_Y;
    }

    public static double getDoorWidth() {
        return DOOR_WIDTH;
    }

    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        stage.setResizable(true);
        Image icon = new Image("/main/design/images/icon.png");
        stage.getIcons().add(icon);
        setScene(WelcomeScreen.getScene());
        stage.show();
    }

    public static void close() {
        stage.close();
    }
}
