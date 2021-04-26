package main.frontend;

import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import main.backend.Controller;
import main.backend.characters.Player;

public class Room {
    private static Pane screen = null;
    private static BorderPane bPane = null;
    private static Text healthVal;
    private static Label cashValue;

    public static void reset() {
        if (screen != null) {
            screen.getChildren().clear();
            screen.getChildren().add(bPane);
        }
    }

    public static Text getHealthVal() {
        return healthVal;
    }

    public static Label getCashValue() {
        return cashValue;
    }

    public static Scene getScene() {
        StackPane screen = new StackPane();
        BorderPane bPane = new BorderPane();
        screen.getChildren().addAll(bPane);
        Scene roomScene = new Scene(screen, MainScreen.getLength(), MainScreen.getHeight());
        screen.getStyleClass().add("screen");
        roomScene.getStylesheets().addAll("/main/design/Room.css");

        int numHearts = 0;
        Integer amountCash = 0;

        VBox healthAndCash = new VBox(5);
        healthAndCash.getStyleClass().add("stats");
        HBox hearts = new HBox(2);
        //buy upgrades or we can add more weapons later or something
        HBox cash = new HBox(10, new ImageView("/main/design/images/coin.png"));
        if (Controller.getDifficultyLevel().equals("Easy")) {
            numHearts = 8;
            amountCash = 500;
        } else if (Controller.getDifficultyLevel().equals("Medium")) {
            numHearts = 7;
            amountCash = 400;
        } else {
            numHearts = 6;
            amountCash = 300;
        }

        ProgressBar healthBar = new ProgressBar();
        healthBar.setScaleShape(false);
        healthBar.setPrefWidth(200);
        healthBar.setProgress(1.0);

        healthVal = new Text(10, 50, "100");
        HBox health = new HBox();
        health.getChildren().addAll(healthBar, healthVal);
        healthAndCash.getChildren().addAll(health, cash);
        Player.getInstance().setHealthBox(healthBar, healthVal);
        Player.getInstance().setCash(amountCash);

        for (int i = 0; i < numHearts; i++) {
            ImageView heart = new ImageView("/main/design/images/heart.png");
            heart.setId("heart" + i);
            hearts.getChildren().add(heart);
        }

        cashValue = new Label(Player.getInstance().getCash().toString());
        cashValue.getStyleClass().add("cash_value");

        cash.getChildren().addAll(cashValue);

        StackPane back = new StackPane();
        back.getChildren().add(new Rectangle(33, 34));
        ImageView backImage = new ImageView("/main/design/images/power.png");
        back.setId("roomToSetup");
        back.setOnMouseClicked(event -> {
            MainScreen.setScene(SetUpPlayerScreen.getScene());
            GameManager.stopGameLoop();
        });
        back.setOnMouseEntered(e -> roomScene.setCursor(Cursor.CLOSED_HAND));
        back.setOnMouseExited(e -> roomScene.setCursor(Cursor.DEFAULT));
        back.getChildren().add(backImage);

        bPane.setTop(healthAndCash);
        bPane.setBottom(back);
        roomScene.getStylesheets().add("/main/design/Room.css");

        Room.screen = screen;
        Room.bPane = bPane;

        GameManager.initializeGame(screen, roomScene);
        GameManager.gameLoop();

        return roomScene;
    }
}
