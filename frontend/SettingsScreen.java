package frontend;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.scene.control.*;

import backend.Controller;

public class SettingsScreen {

    public static Scene getScene() {
        BorderPane screen = new BorderPane();
        screen.getStyleClass().add("screen");

        Button toMainScreen = new Button("back");
        toMainScreen.getStyleClass().add("back_button");
        toMainScreen.setOnAction(event -> {
            MainScreen.setScene(WelcomeScreen.getScene());
        });

        Text settingsTitle = new Text("Settings");
        settingsTitle.getStyleClass().add("settings_title");

        Text chooseDifficulty = new Text("Difficulty:");
        chooseDifficulty.getStyleClass().add("choice");

        ComboBox<String> difficulty = new ComboBox<>();
        difficulty.getItems().addAll(
            "Easy",
            "Medium",
            "Hard"
        );
        difficulty.setValue(Controller.difficultyLevel);
        difficulty.setOnAction(event -> {
            Controller.difficultyLevel = difficulty.getValue();
        });

        HBox top = new HBox();
        top.setAlignment(Pos.CENTER_LEFT);
        top.getChildren().add(toMainScreen);

        HBox difficultyBox = new HBox();
        difficultyBox.setAlignment(Pos.CENTER);
        difficultyBox.getChildren().addAll(chooseDifficulty, difficulty);
        difficultyBox.getStyleClass().add("box");

        VBox middle = new VBox();
        middle.setAlignment(Pos.CENTER);
        middle.getChildren().addAll(settingsTitle, difficultyBox);
        middle.getStyleClass().add("box");

        screen.setTop(top);
        screen.setCenter(middle);
        
        Scene welcomeScreen = new Scene(screen, MainScreen.length, MainScreen.height);
        welcomeScreen.getStylesheets().add("/design/SettingsScreen.css");

        return welcomeScreen;
    }
}