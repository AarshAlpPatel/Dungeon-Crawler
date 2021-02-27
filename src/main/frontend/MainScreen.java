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
    private static int length = 800;
    private static int height = 800;
    
    public static void main(String[] args) {
        launch(args);
    }

    public static void setScene(Scene s) {
        stage.setScene(s);
    }

    public static int getLength() {
        return length;
    }

    public static int getHeight() {
        return height;
    }

    //protected static Button toGame;

    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        Image icon = new Image("/main/design/images/icon.png");
        stage.getIcons().add(icon);
        setScene(WelcomeScreen.getScene());
        stage.show();
    }
}
