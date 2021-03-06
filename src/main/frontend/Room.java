package main.frontend;

import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.Rectangle;
import main.backend.Controller;

public class Room {
    private static Pane screen = null;
    private static BorderPane bPane = null;
    private static Paint wallColor = Color.web("rgba(0,0,255,1.0)");
    private static Paint doorColor = Color.web("rgba(255,0,0,1.0)");
    private static Rectangle[] walls = {
        new Rectangle(MainScreen.getLength()-MainScreen.getMinX(), 
                      MainScreen.getWallWidth(), wallColor),
        new Rectangle(MainScreen.getWallWidth(), MainScreen.getHeight()-
                      MainScreen.getMinY(), wallColor),
        new Rectangle(MainScreen.getLength()-MainScreen.getMinX(),
                      MainScreen.getWallWidth(), wallColor),
        new Rectangle(MainScreen.getWallWidth(), MainScreen.getHeight()-
                      MainScreen.getMinY(), wallColor)
    };
    private static Rectangle[] doors = {
        new Rectangle(MainScreen.getDoorWidth(), MainScreen.getWallWidth(), doorColor),
        new Rectangle(MainScreen.getWallWidth(), MainScreen.getDoorWidth(), doorColor),
        new Rectangle(MainScreen.getDoorWidth(), MainScreen.getWallWidth(), doorColor),
        new Rectangle(MainScreen.getWallWidth(), MainScreen.getDoorWidth(), doorColor),
    };

    private static void initializeWallsAndDoors() {
        walls[0].setTranslateY(MainScreen.getMinY()-MainScreen.getHeight()/2);
        doors[0].setTranslateY(MainScreen.getMinY()-MainScreen.getHeight()/2);

        walls[1].setTranslateX(MainScreen.getMinX()-MainScreen.getLength()/2);
        walls[1].setTranslateY(MainScreen.getMinY()/2);
        doors[1].setTranslateX(MainScreen.getMinX()-MainScreen.getLength()/2);
        doors[1].setTranslateY(MainScreen.getMinY()/2);

        walls[2].setTranslateY(MainScreen.getHeight()/2);
        doors[2].setTranslateY(MainScreen.getHeight()/2);

        walls[3].setTranslateX(MainScreen.getLength()/2);
        walls[3].setTranslateY(MainScreen.getMinY()/2);
        doors[3].setTranslateX(MainScreen.getLength()/2);
        doors[3].setTranslateY(MainScreen.getMinY()/2);
    }

    public static void drawDoors(boolean[] connections) {
        for(int i = 0; i < connections.length; ++i) {
            if (connections[i]) {
                screen.getChildren().add(doors[i]);
            }
        }
    }

    public static void drawWalls() {
        screen.getChildren().addAll(walls);
    }

    public static void reset() {
        if(screen != null) {
            screen.getChildren().clear();
            screen.getChildren().add(bPane);
            screen.getChildren().addAll(walls);
        }
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

        StackPane back = new StackPane();
        back.getChildren().add(new Rectangle(33, 34));
        ImageView backImage = new ImageView("/main/design/images/power.png");
        back.setId("roomToSetup");
        back.setOnMouseClicked(event -> MainScreen.setScene(SetUpPlayerScreen.getScene()));
        back.setOnMouseEntered(e -> roomScene.setCursor(Cursor.CLOSED_HAND));
        back.setOnMouseExited(e -> roomScene.setCursor(Cursor.DEFAULT));
        back.getChildren().add(backImage);

        bPane.setTop(healthAndCash);
        bPane.setBottom(back);
        roomScene.getStylesheets().add("/main/design/Room.css");

        Room.screen = screen;
        Room.bPane = bPane;
        
        initializeWallsAndDoors();
        drawWalls();

        GameManager.initializeRoom(screen, roomScene);
        GameManager.gameLoop();

        return roomScene;
    }
}
