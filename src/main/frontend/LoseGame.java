package main.frontend;

import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class LoseGame {
    public static Scene getScene() {
        BorderPane screen = new BorderPane();
        Scene loseGame = new Scene(screen, MainScreen.getLength(), MainScreen.getHeight());

        VBox loseBox = new VBox(10);
        loseBox.setId("wonBox");
        ImageView dead = new ImageView("main/design/images/Dead.gif");
        Label lose = new Label("LOl you lost!");
        lose.setId("won");
        lose.getStyleClass().add("wonLabel");

        Label restart = new Label("Restart:");
        restart.getStyleClass().add("wonLabel");



        ImageView wonToMain = new ImageView("/main/design/images/power.png");
        wonToMain.setId("wonToWelcome");
        wonToMain.setOnMouseClicked(event -> MainScreen.setScene(WelcomeScreen.getScene()));
        wonToMain.setOnMouseEntered(e -> loseGame.setCursor(Cursor.CLOSED_HAND));
        wonToMain.setOnMouseExited(e -> loseGame.setCursor(Cursor.DEFAULT));

        loseBox.getChildren().addAll(lose, dead, restart, wonToMain);

        screen.setCenter(loseBox);

        loseGame.getStylesheets().add("/main/design/EndGame.css");
        return loseGame;
    }
}
