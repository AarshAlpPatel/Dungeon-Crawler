package main.frontend;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SetUpPlayerScreen {

    public static Scene getScene() {

        //top is the customization panel and bottom are the start and back buttons
        VBox screen = new VBox();

        //contains the entryFields and the character chooser
        HBox customization_panel = new HBox(15);

        VBox entryFields = new VBox();
        entryFields.getStyleClass().add("entry_fields");
        TextField name = new TextField();
        name.getStyleClass().add("name");

        ComboBox<String> weapons = new ComboBox<>();
        weapons.getStyleClass().add("weapons");
        weapons.getItems().addAll(
                "Weapon 1", "Weapon 2", "Weapon 2"
        );

        ComboBox<String> diff = new ComboBox<>();
        diff.getStyleClass().add("diff");
        diff.getItems().addAll(
                "Easy", "Medium", "Hard"
        );

        entryFields.getChildren().addAll(name, weapons, diff);

        VBox chooseChar = new VBox();
        chooseChar.getStyleClass().add("choose_character");

        StackPane character = new StackPane();
        ImageView flash = new ImageView(new Image("/main/design/images/flash.png"));
        character.getChildren().add(flash);
        character.getStyleClass().add("character");

        HBox navButtons = new HBox(5);
        Button forward = new Button(">");
        forward.getStyleClass().add("forward");

        Button backward = new Button("<");
        backward.getStyleClass().add("backward");

        Button choose = new Button("Choose");
        choose.getStyleClass().add("choose");
        navButtons.getChildren().addAll(backward, choose, forward);
        chooseChar.getChildren().addAll(character, navButtons);

        customization_panel.getChildren().addAll(chooseChar, entryFields);

        screen.getChildren().add(customization_panel);

        HBox bottom_buttons = new HBox(50);
        bottom_buttons.getStyleClass().add("bottom_buttons");
        Button toMainScreen = new Button("back");
        toMainScreen.getStyleClass().add("back_button");
        toMainScreen.setOnAction(event -> {
            MainScreen.setScene(WelcomeScreen.getScene());
        });

        Button start = new Button("Start Game");
        start.getStyleClass().add("start");
        start.setOnAction(event -> {
            //send to start of dungeon (room 1)
        });

        bottom_buttons.getChildren().addAll(toMainScreen, start);

        screen.getChildren().add(bottom_buttons);

        Scene playerSetUp = new Scene(screen, MainScreen.length, MainScreen.height);
        playerSetUp.getStylesheets().add("/main/design/PlayerSetup.css");

        return playerSetUp;
    }

}
