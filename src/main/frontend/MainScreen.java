package main.frontend;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.*;
import javafx.scene.*;

/**
 * MainScreen contains the main window and changes between scenes.
 */
public class MainScreen extends Application {
    public static Stage stage;
    public static int length = 800, height = 800;
    public static void main(String[] args) {
        launch(args);
    }

    public static void setScene(Scene s) {
        stage.setScene(s);
    }

    public void start(Stage primaryStage) {
        stage = primaryStage;
        Image icon = new Image("/main/design/images/icon.png");
        stage.getIcons().add(icon);
        setScene(WelcomeScreen.getScene());
        stage.show();
    }
}
