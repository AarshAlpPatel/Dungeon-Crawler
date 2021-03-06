package main.frontend;

import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.scene.control.*;

import main.backend.Controller;

public class SettingsScreen {

    public static Scene getScene() {
        BorderPane screen = new BorderPane();
        screen.getStyleClass().add("screen");

        Button toMainScreen = new Button("Return Home");
        toMainScreen.setId("toMainScreen");
        toMainScreen.getStyleClass().add("back_button");
        toMainScreen.setOnAction(event -> {
            MainScreen.setScene(WelcomeScreen.getScene());
        });

        Text settingsTitle = new Text("Settings");
        settingsTitle.getStyleClass().add("settings_title");

        Text chooseDifficulty = new Text("Difficulty:");
        chooseDifficulty.getStyleClass().add("choice");

        ComboBox<String> difficulty = new ComboBox<>();
        difficulty.setId("difficulty");
        difficulty.getItems().addAll(
            "Easy",
            "Medium",
            "Hard"
        );
        difficulty.setValue(Controller.getDifficultyLevel());
        difficulty.setOnAction(event -> {
            Controller.setDifficultyLevel(difficulty.getValue());
        });
        difficulty.getStyleClass().add("combo-box-base");

        HBox difficultyBox = new HBox();
        difficultyBox.setAlignment(Pos.CENTER);
        difficultyBox.getChildren().addAll(chooseDifficulty, difficulty);
        difficultyBox.getStyleClass().add("box");

        VBox middle = new VBox();
        middle.setAlignment(Pos.CENTER);
        middle.getChildren().addAll(settingsTitle, difficultyBox, toMainScreen);
        middle.getStyleClass().add("box");

        screen.setCenter(middle);
        
        Scene welcomeScreen = new Scene(screen, MainScreen.getLength(), MainScreen.getHeight());
        welcomeScreen.getStylesheets().add("/main/design/SettingsScreen.css");

        return welcomeScreen;
    }
}
