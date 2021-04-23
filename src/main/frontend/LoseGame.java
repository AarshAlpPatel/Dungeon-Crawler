package main.frontend;

import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import main.backend.Controller;

import java.util.List;

public class LoseGame {
    public static Scene getScene() {
        BorderPane screen = new BorderPane();
        Scene loseGame = new Scene(screen, MainScreen.getLength(), MainScreen.getHeight());

        Controller.stopTimer();

        VBox loseBox = new VBox(10);
        loseBox.setId("wonBox");
        ImageView dead = new ImageView("main/design/images/Dead.gif");
        Label lose = new Label("LOl you lost!");
        lose.setId("won");
        lose.getStyleClass().add("wonLabel");

        Label restart = new Label("Restart:");
        restart.getStyleClass().add("wonLabel");

        System.out.println(" ====== Player stats: ====== ");
        System.out.println("Hours: " + Controller.getTimeTaken()[0] +
                ", Minutes: " + Controller.getTimeTaken()[1] +
                ", Seconds: " + Controller.getTimeTaken()[2]);
        List<Double> stats = Controller.getPlayerStats();
        System.out.println("Dealt: " + stats.get(0).toString());
        System.out.println("Taken: " + stats.get(1).toString());
        System.out.println("Killed: " + stats.get(2).toString());


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
