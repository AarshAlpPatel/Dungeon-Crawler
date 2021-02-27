package main.frontend;

import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import main.backend.Controller;

public class Room {
    //Current supported types include: empty
    public static Scene getScene(String type) {
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
        healthAndCash.getChildren().addAll(hearts, cash);
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

        for (int i = 0; i < numHearts; i++) {
            ImageView heart = new ImageView("/main/design/images/heart.png");
            heart.setId("heart" + i);
            hearts.getChildren().add(heart);
        }

        Label cashValue = new Label(amountCash.toString());
        cashValue.getStyleClass().add("cash_value");

        cash.getChildren().addAll(cashValue);

        ImageView back = new ImageView("/main/design/images/power.png");
        back.setId("roomToSetup");
        back.setOnMouseClicked(event -> MainScreen.setScene(SetUpPlayerScreen.getScene()));
        back.setOnMouseEntered(e -> roomScene.setCursor(Cursor.CLOSED_HAND));
        back.setOnMouseExited(e -> roomScene.setCursor(Cursor.DEFAULT));

        bPane.setTop(healthAndCash);
        bPane.setBottom(back);
        roomScene.getStylesheets().add("/main/design/Room.css");

        if (type.equals("empty")) {
            GameManager.initializeEmptyRoom(screen, roomScene);
        } else {
            throw new IllegalArgumentException("Room type not supported.");
        }
        GameManager.gameLoop();

        return roomScene;
    }
}