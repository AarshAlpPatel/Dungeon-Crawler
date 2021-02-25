package main.frontend;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.shape.*;

import javafx.scene.text.Text;
import main.backend.Controller;
import main.backend.characters.Character;
import main.backend.characters.Player;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class SetUpPlayerScreen {

    private static int indexC = 0; //character
    private static int indexW = 0; //weapon
    private static Character playerChar;

    public static Scene getScene() {

        //top is the customization panel and bottom are the start and back buttons
        VBox screen = new VBox();
        Scene playerSetUp = new Scene(screen, MainScreen.length, MainScreen.height);
        screen.getStyleClass().addAll("screen", "center");

        //contains the entryFields and the character chooser
        StackPane customization_panel = new StackPane();
        customization_panel.getStyleClass().addAll("customization_panel", "center");
        Pane customization_panel_back = new Pane();
        customization_panel_back.getStyleClass().addAll("customization_panel_back");
        HBox customization_panel_hbox = new HBox();
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
        nameLabel.getStyleClass().addAll("name_label", "label");
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
        Rectangle weaponsRect = new Rectangle(450, 200);
        weaponsRect.getStyleClass().addAll("rect", "weapons_rect");

        Image backArrow = new Image("/main/design/images/arrow left.png", 16, 24, false, false);
        ImageView arrowLeft = new ImageView(backArrow);
        arrowLeft.setOnMouseEntered(e -> {
            playerSetUp.setCursor(Cursor.HAND);
            arrowLeft.setFitWidth(backArrow.getWidth() * 2);
            arrowLeft.setFitHeight(backArrow.getHeight() * 2);
        });
        arrowLeft.setOnMouseExited(e -> {
            playerSetUp.setCursor(Cursor.DEFAULT);
            arrowLeft.setFitWidth(backArrow.getWidth());
            arrowLeft.setFitHeight(backArrow.getHeight());
        });
        Image frontArrow = new Image("/main/design/images/arrow right.png", 16, 24, false, false);
        ImageView arrowRight = new ImageView(frontArrow);
        arrowRight.setOnMouseEntered(e -> {
            playerSetUp.setCursor(Cursor.HAND);
            arrowRight.setFitWidth(frontArrow.getWidth() * 2);
            arrowRight.setFitHeight(frontArrow.getHeight() * 2);
        });
        arrowRight.setOnMouseExited(e -> {
            playerSetUp.setCursor(Cursor.DEFAULT);
            arrowRight.setFitWidth(frontArrow.getWidth());
            arrowRight.setFitHeight(frontArrow.getHeight());
        });
        Image red_dagger = new Image("/main/design/images/red dagger.png");
        Image blank_axe = new Image("/main/design/images/axe.png");
        Image blank_spear = new Image("/main/design/images/spear.png");
        HBox weaponAndArrows = new HBox(0, arrowLeft, arrowRight);
        weaponAndArrows.getStyleClass().addAll("center");
        weapons.getChildren().addAll(weaponsRect, weaponAndArrows);
        //dagger
        HBox dagger = new HBox(5);
        dagger.setPadding(new Insets(0, 30, 0, 0));
        dagger.getStyleClass().addAll("center");
        VBox dChar = new VBox(10);
        dChar.getStyleClass().addAll("center");
        StackPane dSprite = new StackPane();
        dSprite.getChildren().add(new ImageView(red_dagger));
        Label dName = new Label("Red Dagger");
        dName.getStyleClass().addAll("dName", "weapon_description");
        Label dAttack = new Label("Attack: 20");
        dAttack.getStyleClass().addAll("dAttack", "weapon_description");
        Label dSpeed = new Label("Speed: 30");
        dSpeed.getStyleClass().addAll("dSpeed", "weapon_description");
        dChar.getChildren().addAll(dName, dAttack, dSpeed);
        dagger.getChildren().addAll(dSprite, dChar);
        //axe
        HBox axe = new HBox(20);
        axe.setPadding(new Insets(0, 30, 0, 0));
        axe.getStyleClass().addAll("center");
        VBox aChar = new VBox(10);
        aChar.getStyleClass().addAll("center");
        StackPane aSprite = new StackPane();
        aSprite.getChildren().add(new ImageView(blank_axe));
        Label aName = new Label("Axe");
        aName.getStyleClass().addAll("aName", "weapon_description");
        Label aAttack = new Label("Attack: 40");
        aAttack.getStyleClass().addAll("aAttack", "weapon_description");
        Label aSpeed = new Label("Speed: 10");
        aSpeed.getStyleClass().addAll("aSpeed", "weapon_description");
        aChar.getChildren().addAll(aName, aAttack, aSpeed);
        axe.getChildren().addAll(aSprite, aChar);
        //spear
        HBox spear = new HBox(5);
        spear.setPadding(new Insets(0, 30, 0, 0));
        spear.getStyleClass().addAll("center");
        VBox sChar = new VBox(10);
        sChar.getStyleClass().addAll("center");
        StackPane sSprite = new StackPane();
        sSprite.getChildren().add(new ImageView(blank_spear));
        Label sName = new Label("Spear");
        sName.getStyleClass().addAll("sName", "weapon_description");
        Label sAttack = new Label("Attack: 5");
        sAttack.getStyleClass().addAll("sAttack", "weapon_description");
        Label sSpeed = new Label("Speed: 50");
        sSpeed.getStyleClass().addAll("sSpeed", "weapon_description");
        sChar.getChildren().addAll(sName, sAttack, sSpeed);
        spear.getChildren().addAll(sSprite, sChar);
        weaponAndArrows.getChildren().add(1, dagger);
        arrowLeft.setOnMouseClicked(e -> {
            indexW--;
            weaponAndArrows.getChildren().remove(1);
            if (indexW < 0) {
                indexW = 2;
                weaponAndArrows.getChildren().add(1, spear);
            } else if (indexW == 1) {
                weaponAndArrows.getChildren().add(1, axe);
            } else if (indexW == 0) {
                weaponAndArrows.getChildren().add(1, dagger);
            }
        });
        arrowRight.setOnMouseClicked(e -> {
            indexW++;
            weaponAndArrows.getChildren().remove(1);
            if (indexW > 2) {
                indexW = 0;
                weaponAndArrows.getChildren().add(1, dagger);
            } else if (indexW == 1) {
                weaponAndArrows.getChildren().add(1, axe);
            } else if (indexW == 2) {
                weaponAndArrows.getChildren().add(1, spear);
            }
        });

        StackPane diff = new StackPane();
        VBox diffVBox = new VBox();
        diffVBox.getStyleClass().addAll("diff_vbox", "center", "entry_boxes");
        diff.getStyleClass().add("diff");
        Rectangle rectDiff = new Rectangle(300, 75);
        rectDiff.getStyleClass().add("rect");
        diff.getChildren().addAll(rectDiff, diffVBox);
        Label diffLabel = new Label("Difficulty");
        diffLabel.getStyleClass().addAll("diff_label", "label");
        ComboBox<String> diffCombo = new ComboBox<>();
        diffCombo.setPromptText("Select A Difficulty");
        diffCombo.getStyleClass().add("diff_combo");
        diffCombo.getItems().addAll(
                "Easy", "Medium", "Hard"
        );
        diffVBox.getChildren().addAll(diffLabel, diffCombo);

        entryFields.getChildren().addAll(name, diff, weapons);

        //vbox for choosing character look
        VBox chooseChar = new VBox(15);
        chooseChar.setMinWidth(320);
        chooseChar.setPadding(new Insets(0, 0, 0,10));
        chooseChar.getStyleClass().addAll("choose_character", "center");

        StackPane character = new StackPane();

        Character char1 = new Character("Brute", "/main/design/images/char1.gif");
        Character char2 = new Character("Magma", "/main/design/images/char2.gif");
        Character char3 = new Character("Aqua", "/main/design/images/char3.gif");
        character.getChildren().add(new ImageView(char1.getImagePath()));
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
            indexC++;
            character.getChildren().remove(0);
            if (indexC > 2) {
                indexC = 0;
                character.getChildren().add(new ImageView(char1.getImagePath()));
                playerChar = char1;
            } else if (indexC == 1) {
                character.getChildren().add(new ImageView(char2.getImagePath()));
                playerChar = char2;
            } else if (indexC == 2) {
                character.getChildren().add(new ImageView(char3.getImagePath()));
                playerChar = char3;
            }
        });

        Button backward = new Button("<");
        backward.getStyleClass().add("backward");
        backward.setOnAction(event -> {
            indexC--;
            character.getChildren().remove(0);
            if (indexC < 0) {
                indexC = 2;
                character.getChildren().add(new ImageView(char3.getImagePath()));
                playerChar = char3;
            } else if (indexC == 1) {
                character.getChildren().add(new ImageView(char2.getImagePath()));
                playerChar = char2;
            } else if (indexC == 0) {
                character.getChildren().add(new ImageView(char1.getImagePath()));
                playerChar = char1;
            }
        });

        Button choose = new Button("Choose");
        choose.getStyleClass().add("choose");
        navButtons_hbox.getChildren().addAll(backward, choose, forward);
        choose.setOnAction(event -> {
            //add the checkmark and change text (checks or unchecks based on getText)
            if (choose.getText().equals("Choose")) {
                forward.setDisable(true);
                backward.setDisable(true);
                navButtons_hbox.getChildren().remove(0);
                navButtons_hbox.getChildren().remove(1);
                character.getChildren().get(0).setOpacity(0.7); //change flash to image and have image change between the pictures
                character.getChildren().add(new ImageView("/main/design/images/check.png"));
                choose.setText("Uncheck");
            } else if (choose.getText().equals("Uncheck")) {
                navButtons_hbox.getChildren().add(0, backward);
                navButtons_hbox.getChildren().add(2, forward);
                character.getChildren().remove(1);
                character.getChildren().get(0).setOpacity(1);
                forward.setDisable(false);
                backward.setDisable(false);
                choose.setText("Choose");
            }
        });

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
                //Player.weapon = weaponsCombo.getValue();
                Controller.difficultyLevel = diffCombo.getValue();
                Player.character = playerChar;
                //send to start of dungeon (room 1)
            }
        });

        bottom_buttons.getChildren().addAll(toMainScreen, start);

        screen.getChildren().add(bottom_buttons);

        playerSetUp.getStylesheets().add("/main/design/PlayerSetup.css");

        return playerSetUp;
    }

}
