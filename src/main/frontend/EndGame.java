package main.frontend;

import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import main.backend.characters.Player;

public class EndGame {
    public static Scene getScene() {
        BorderPane screen = new BorderPane();
        Scene endGame = new Scene(screen, MainScreen.length, MainScreen.height);

        Player.getPlayerObj().setDirection("A", true);
        Player.getPlayerObj().move();

        Label won = new Label("Congrats You Won!");
        won.setId("won");
        won.getStyleClass().add("wonLabel");

        ImageView wonToMain = new ImageView("/main/design/images/power.png");
        wonToMain.setId("wonToWelcome");
        wonToMain.setOnMouseClicked(event -> MainScreen.setScene(WelcomeScreen.getScene()));
        wonToMain.setOnMouseEntered(e -> endGame.setCursor(Cursor.CLOSED_HAND));
        wonToMain.setOnMouseExited(e -> endGame.setCursor(Cursor.DEFAULT));
        
        screen.setCenter(won);
        screen.setTop(wonToMain);

        endGame.getStylesheets().add("/main/design/EndGame.css");
        return endGame;
    }
}
