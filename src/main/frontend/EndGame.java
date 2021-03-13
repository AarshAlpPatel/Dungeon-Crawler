package main.frontend;

import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class EndGame {
    public static Scene getScene() {
        BorderPane screen = new BorderPane();
        Scene endGame = new Scene(screen, MainScreen.getLength(), MainScreen.getHeight());

        VBox wonBox = new VBox(10);
        wonBox.setId("wonBox");
        ImageView phelps = new ImageView("main/design/images/spongebob strong.gif");
        Label won = new Label("Congrats You Won!");
        won.setId("won");
        won.getStyleClass().add("wonLabel");

        wonBox.getChildren().addAll(phelps, won);

        ImageView wonToMain = new ImageView("/main/design/images/power.png");
        wonToMain.setId("wonToWelcome");
        wonToMain.setOnMouseClicked(event -> MainScreen.setScene(WelcomeScreen.getScene()));
        wonToMain.setOnMouseEntered(e -> endGame.setCursor(Cursor.CLOSED_HAND));
        wonToMain.setOnMouseExited(e -> endGame.setCursor(Cursor.DEFAULT));
        
        screen.setCenter(wonBox);
        screen.setTop(wonToMain);

        endGame.getStylesheets().add("/main/design/EndGame.css");
        return endGame;
    }
}
