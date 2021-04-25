package main.frontend;

import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;
import javafx.scene.control.*;
import main.backend.Controller;

public class WelcomeScreen {

    private static StackPane screenHolder;
    private static Scene welcomeScreen;

    public static Scene getScene() {
        screenHolder = new StackPane();
        VBox screen = new VBox();
        screen.setAlignment(Pos.CENTER);
        screen.getStyleClass().add("screen");

        Text gameTitle = new Text("Warrior's Legacy");
        gameTitle.setId("gameTitle");
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

        Button toHelp = new Button("How To Play");
        toHelp.setId("toHelp");
        toHelp.getStyleClass().add("button");
        toHelp.setOnAction(event -> {
            screenHolder.getChildren().add(new Help());
            screenHolder.getChildren().get(0).setOpacity(0.5);
        });

        screen.getChildren().addAll(gameTitle, toGame, toSettings, toHelp);
        screenHolder.getChildren().add(screen);
        
        welcomeScreen = new Scene(screenHolder, MainScreen.getLength(), MainScreen.getHeight());
        welcomeScreen.getStylesheets().add("/main/design/WelcomeScreen.css");

        return welcomeScreen;
    }

    public static class Help extends StackPane {
        //protected StackPane screenHolder;
        protected VBox screen;
        protected VBox info;
        protected Rectangle rect;
        protected ImageView close;

        protected Help() {
            initHelp();
        }

        protected void initHelp() {
            //screenHolder = new StackPane();
            rect = new Rectangle(MainScreen.getLength() - 200, MainScreen.getHeight() - 200);
            rect.getStyleClass().add("rect");
            this.getChildren().add(rect);
            createInfoBox();
            screen = new VBox();
            screen.setMaxSize(rect.getHeight(), rect.getWidth());
            HBox closePane = new HBox();
            closePane.setMaxSize(rect.getHeight(), rect.getWidth());
            closePane.setPadding(new Insets(20, 20, 0, 0));
            closePane.setAlignment(Pos.CENTER_RIGHT);
            close = new ImageView("main/design/images/exit.png");
            close.setOnMouseReleased(event -> {
                screenHolder.getChildren().remove(this);
                screenHolder.getChildren().get(0).setOpacity(1);
            });
            close.setOnMouseEntered(event -> {
                welcomeScreen.setCursor(Cursor.HAND);
            });
            close.setOnMouseExited(event -> {
                welcomeScreen.setCursor(Cursor.DEFAULT);
            });
            closePane.getChildren().add(close);
            screen.getChildren().addAll(closePane, info);
            this.getChildren().add(screen);
        }

        private void createInfoBox() {
            info = new VBox(20);
            info.getStyleClass().add("center");
            Label title = new Label("How To Play");
            title.setPadding(new Insets(30, 0, 50, 0));
            title.getStyleClass().add("info_title");
            Label move = new Label("Use WASD to move.");
            Label attack = new Label("Left click to attack.");
            Label shopPressG = new Label("Press G to open the shop.");
            Label inventoryPressH = new Label("Press H to open the inventory.");
            Label boss = new Label("Defeat the monster at the \nend of the labyrinth to win!");
            info.getChildren().addAll(title, move, attack, shopPressG, inventoryPressH, boss);
        }
    }
}