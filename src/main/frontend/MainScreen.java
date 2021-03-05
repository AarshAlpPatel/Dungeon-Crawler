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
    private static double LENGTH = 800;
    private static double HEIGHT = 800;
    private static double MIN_X = 20;
    private static double MIN_Y = 100;
    private static double WALL_WIDTH = 10;
    
    public static void main(String[] args) {
        launch(args);
    }

    public static void setScene(Scene s) {
        stage.setScene(s);
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

    public static double getMaxX() {
        return LENGTH - WALL_WIDTH;
    }

    public static double getMaxY() {
        return HEIGHT - WALL_WIDTH;
    }

    public static double getMinX() {
        return MIN_X + WALL_WIDTH;
    }

    public static double getMinY() {
        return MIN_Y + WALL_WIDTH;
    }

    public static double getMidX() {
        return (LENGTH-MIN_X)/2 + MIN_X;
    }

    public static double getMidY() {
        return (HEIGHT-MIN_Y)/2 + MIN_Y;
    }

    //protected static Button toGame;

    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        Image icon = new Image("/main/design/images/icon.png");
        stage.getIcons().add(icon);
        setScene(WelcomeScreen.getScene());
        stage.show();
    }

    public static void close() {
        stage.close();
    }
}
