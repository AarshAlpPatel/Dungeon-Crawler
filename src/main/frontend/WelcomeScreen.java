package main.frontend;

import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.scene.control.*;

//import static main.frontend.MainScreen.toGame;

public class WelcomeScreen {

    public static Scene getScene() {
        VBox screen = new VBox();
        screen.setAlignment(Pos.CENTER);
        screen.getStyleClass().add("screen");

        Text gameTitle = new Text("Name of the Game");
        gameTitle.getStyleClass().add("game_title");

        Button toGame = new Button("New Game");
        toGame.setId("toGame");
        toGame.getStyleClass().add("button");
        toGame.setOnAction(event -> {
            MainScreen.setScene(SetUpPlayerScreen.getScene());
        });

        Button toSettings = new Button("Settings");
        toSettings.setId("toSettings");
        toSettings.getStyleClass().add("button");
        toSettings.setOnAction(event -> {
            MainScreen.setScene(SettingsScreen.getScene());
        });

        screen.getChildren().addAll(gameTitle, toGame, toSettings);
        
        Scene welcomeScreen = new Scene(screen, MainScreen.length, MainScreen.height);
        welcomeScreen.getStylesheets().add("/main/design/WelcomeScreen.css");

        return welcomeScreen;
    }
}