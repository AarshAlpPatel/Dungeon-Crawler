package main.frontend;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.shape.*;

import main.backend.Controller;
import main.backend.characters.Character;
import main.backend.characters.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;

public class SetUpPlayerScreen {

    private static int index = 0;

    public static Scene getScene() {

        //top is the customization panel and bottom are the start and back buttons
        VBox screen = new VBox();
        screen.getStyleClass().addAll("screen", "center");

        //contains the entryFields and the character chooser
        StackPane customization_panel = new StackPane();
        customization_panel.getStyleClass().addAll("customization_panel", "center");
        Pane customization_panel_back = new Pane();
        customization_panel_back.getStyleClass().addAll("customization_panel_back");
        HBox customization_panel_hbox = new HBox(100);
        customization_panel_hbox.getStyleClass().addAll("customization_panel_hbox", "center");
        customization_panel.getChildren().addAll(customization_panel_back, customization_panel_hbox);

        //entry fields for name, weapon, and difficulty
        VBox entryFields = new VBox();
        entryFields.getStyleClass().addAll("entry_fields", "center");

            //enter name -- Start Game button checks if its blank
        StackPane name = new StackPane();
        name.getStyleClass().addAll("name", "center");
        VBox nameVBox = new VBox();
        Rectangle rectName = new Rectangle(300, 75);
        rectName.getStyleClass().add("rect");
        name.getChildren().addAll(rectName, nameVBox);
        nameVBox.getStyleClass().addAll("name_vbox", "center", "entry_boxes");
        Label nameLabel = new Label("Enter Name");
        nameLabel.getStyleClass().add("name_label");
        TextField nameField = new TextField();
        nameField.setOnMouseClicked(event -> {
            nameField.setStyle("-fx-background-color: #ffffff;");
        });
        nameField.setMaxWidth(250);
        nameField.setPromptText("Enter Player Name");
        nameField.getStyleClass().add("name_field");
        nameVBox.getChildren().addAll(nameLabel, nameField);

        StackPane weapons = new StackPane();
        weapons.getStyleClass().add("weapons");
        VBox weaponsVBox = new VBox();
        weaponsVBox.getStyleClass().addAll("weapons_vbox", "center", "entry_boxes");
        Rectangle rectWeapons = new Rectangle(300, 75);
        rectWeapons.getStyleClass().add("rect");
        weapons.getChildren().addAll(rectWeapons, weaponsVBox);
        Label weaponsLabel = new Label("Starting Weapon");
        weaponsLabel.getStyleClass().add("weapons_label");
        ComboBox<String> weaponsCombo = new ComboBox<>();
        weaponsCombo.getStyleClass().add("weapons_combo");
        weaponsCombo.setPromptText("Select A Starting Weapon");
        weaponsCombo.getItems().addAll(
                "Weapon 1", "Weapon 2", "Weapon 3"
        );
        weaponsVBox.getChildren().addAll(weaponsLabel, weaponsCombo);

        StackPane diff = new StackPane();
        VBox diffVBox = new VBox();
        diffVBox.getStyleClass().addAll("diff_vbox", "center", "entry_boxes");
        diff.getStyleClass().add("diff");
        Rectangle rectDiff = new Rectangle(300, 75);
        rectDiff.getStyleClass().add("rect");
        diff.getChildren().addAll(rectDiff, diffVBox);
        Label diffLabel = new Label("Difficulty");
        diffLabel.getStyleClass().add("diff_label");
        ComboBox<String> diffCombo = new ComboBox<>();
        diffCombo.setPromptText("Select A Difficulty");
        diffCombo.getStyleClass().add("diff_combo");
        diffCombo.getItems().addAll(
                "Easy", "Medium", "Hard"
        );
        diffVBox.getChildren().addAll(diffLabel, diffCombo);

        entryFields.getChildren().addAll(name, weapons, diff);

        //vbox for choosing character look
        VBox chooseChar = new VBox(15);
        chooseChar.getStyleClass().addAll("choose_character", "center");

        StackPane character = new StackPane();

        ImageView char1 = new ImageView(new Image("/main/design/images/char1.gif"));
        ImageView char2 = new ImageView(new Image("/main/design/images/char2.gif"));
        ImageView char3 = new ImageView(new Image("/main/design/images/char3.gif"));
        character.getChildren().add(char1);
        character.getStyleClass().add("character");

        StackPane navButtons = new StackPane();
        Rectangle rectNav = new Rectangle(150, 50);
        rectNav.getStyleClass().addAll("rect", "center");
        HBox navButtons_hbox = new HBox(5); //flip through character skins
        navButtons.getChildren().addAll(rectNav, navButtons_hbox);
        navButtons_hbox.getStyleClass().addAll("nav_buttons_hbox", "center");

        Button forward = new Button(">");
        forward.getStyleClass().add("forward");
        forward.setOnAction(event -> {
            index++;
            character.getChildren().remove(0);
            if (index > 2) {
                index = 0;
                character.getChildren().add(char1);
            } else if (index == 1) {
                character.getChildren().add(char2);
            } else if (index == 2) {
                character.getChildren().add(char3);
            }
        });

        Button backward = new Button("<");
        backward.getStyleClass().add("backward");
        backward.setOnAction(event -> {
            index--;
            character.getChildren().remove(0);
            if (index < 0) {
                index = 2;
                character.getChildren().add(char3);
            } else if (index == 1) {
                character.getChildren().add(char2);
            } else if (index == 0) {
                character.getChildren().add(char1);
            }
        });

        Button choose = new Button("Choose");
        choose.getStyleClass().add("choose");
        choose.setOnAction(event -> {
            //add the checkmark and change text (checks or unchecks based on getText)
            if (choose.getText().equals("Choose")) {
                forward.setDisable(true);
                backward.setDisable(true);
                character.getChildren().get(0).setOpacity(0.7); //change flash to image and have image change between the pictures
                character.getChildren().add(new ImageView(new Image("/main/design/images/check.png")));
                choose.setText("Uncheck");
            } else if (choose.getText().equals("Uncheck")) {
                character.getChildren().remove(1);
                character.getChildren().get(0).setOpacity(1);
                forward.setDisable(false);
                backward.setDisable(false);
                choose.setText("Choose");
            }
        });
        navButtons_hbox.getChildren().addAll(backward, choose, forward);
        chooseChar.getChildren().addAll(character, navButtons);

        customization_panel_hbox.getChildren().addAll(chooseChar, entryFields);

        screen.getChildren().add(customization_panel);

        HBox bottom_buttons = new HBox(50);
        bottom_buttons.getStyleClass().addAll("bottom_buttons_hbox", "center");
        Button toMainScreen = new Button("Main Menu");
        toMainScreen.getStyleClass().addAll("back_button", "bottom_buttons");
        toMainScreen.setOnAction(event -> {
            MainScreen.setScene(WelcomeScreen.getScene());
        });

        Button start = new Button("Start Game");
        start.getStyleClass().addAll("start", "bottom_buttons");
        start.setOnAction(event -> {
            if (nameField.getText().isEmpty()) {
                nameField.setStyle("-fx-background-color: red;");
            } else {
                //updates name, weapon, and difficulty for player
                Player.name = nameField.getText();
                Player.weapon = weaponsCombo.getValue();
                Controller.difficultyLevel = diffCombo.getValue();
                //send to start of dungeon (room 1)
            }
        });

        bottom_buttons.getChildren().addAll(toMainScreen, start);

        screen.getChildren().add(bottom_buttons);

        Scene playerSetUp = new Scene(screen, MainScreen.length, MainScreen.height);
        playerSetUp.getStylesheets().add("/main/design/PlayerSetup.css");

        return playerSetUp;
    }

}
