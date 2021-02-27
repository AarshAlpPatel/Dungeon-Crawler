package main.frontend;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.*;
import javafx.scene.layout.*;

import javafx.scene.shape.Rectangle;
import main.backend.Controller;

public class Room {
    public static Scene getScene() {
        StackPane screen = new StackPane();
        BorderPane bPane = new BorderPane();
        ImageView floor = new ImageView("/main/design/images/dungeon_floor.png");
        floor.setOpacity(0.5);
        screen.getChildren().addAll(floor, bPane);
        Scene roomScene = new Scene(screen);

        int numHearts = 0;
        Integer amountCash = 0;

        VBox healthAndCash = new VBox(5);
        HBox hearts = new HBox(2);
        HBox cash = new HBox(10, new ImageView("/main/design/images/coin.png")); //buy upgrades or we can add more weapons later or something
        healthAndCash.getChildren().addAll(hearts, cash);
        if (Controller.difficultyLevel.equals("Easy")) {
            numHearts = 8;
            amountCash = 500;
        } else if (Controller.difficultyLevel.equals("Medium")) {
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
        roomScene.getStylesheets().add("/main/design/FirstRoom.css");

        return roomScene;
    }
}
