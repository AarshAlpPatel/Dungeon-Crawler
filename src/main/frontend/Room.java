package main.frontend;

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
        Scene roomScene = new Scene(screen, MainScreen.length, MainScreen.height);
        screen.getStyleClass().add("screen");
        roomScene.getStylesheets().addAll("/main/design/Room.css");

        int numHearts = 0;
        Integer amountCash = 0;

        VBox healthAndCash = new VBox(5);
        healthAndCash.getStyleClass().add("stats");
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
            hearts.getChildren().add(new ImageView("/main/design/images/heart.png"));
        }

        Label cashValue = new Label(amountCash.toString());
        cashValue.getStyleClass().add("cash_value");

        cash.getChildren().addAll(cashValue);

        bPane.setTop(healthAndCash);

        if(type.equals("empty")) {
            GameManager.initializeEmptyRoom(screen, roomScene);
        } else {
            throw new IllegalArgumentException("Room type not supported.");
        }

        return roomScene;
    }
}
