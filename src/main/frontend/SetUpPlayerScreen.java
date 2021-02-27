package main.frontend;

import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.shape.*;

import main.backend.Controller;

public class SetUpPlayerScreen {

    private static int indexC = 0; //character
    private static int indexW = 0; //weapon
    private static String[] characters = {
        "/main/design/images/char1.gif",
        "/main/design/images/char2.gif", 
        "/main/design/images/char3.gif"
    };
    private static String[] weapons = {
        "dagger",
        "spear",
        "axe"
    };

    private static String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public static Scene getScene() {

        //top is the customization panel and bottom are the start and back buttons
        VBox screen = new VBox();
        Scene playerSetUp = new Scene(screen, MainScreen.getLength(), MainScreen.getHeight());
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
        nameField.setId("nameField");
        nameField.setOnMouseClicked(event -> nameField.setStyle("-fx-background-color: #ffffff;"));
        nameField.setMaxWidth(250);
        nameField.setPromptText("Enter Player Name");
        nameField.getStyleClass().add("name_field");
        nameVBox.getChildren().addAll(nameLabel, nameField);

        StackPane weaponsPane = new StackPane();
        weaponsPane.getStyleClass().add("weapons");
        Rectangle weaponsRect = new Rectangle(450, 200);
        weaponsRect.getStyleClass().addAll("rect", "weapons_rect");

        Image backArrow = new Image("/main/design/images/arrow left.png", 16, 24, false, false);
        ImageView arrowLeft = new ImageView(backArrow);
        arrowLeft.setId("backwardWeapon");
        arrowLeft.setOnMouseEntered(e -> {
            playerSetUp.setCursor(Cursor.HAND);
            arrowLeft.setFitWidth(backArrow.getWidth() * 1.5);
            arrowLeft.setFitHeight(backArrow.getHeight() * 1.5);
        });
        arrowLeft.setOnMouseExited(e -> {
            playerSetUp.setCursor(Cursor.DEFAULT);
            arrowLeft.setFitWidth(backArrow.getWidth());
            arrowLeft.setFitHeight(backArrow.getHeight());
        });
        Image frontArrow = new Image("/main/design/images/arrow right.png", 16, 24, false, false);
        ImageView arrowRight = new ImageView(frontArrow);
        arrowRight.setId("forwardWeapon");
        arrowRight.setOnMouseEntered(e -> {
            playerSetUp.setCursor(Cursor.HAND);
            arrowRight.setFitWidth(frontArrow.getWidth() * 1.5);
            arrowRight.setFitHeight(frontArrow.getHeight() * 1.5);
        });
        arrowRight.setOnMouseExited(e -> {
            playerSetUp.setCursor(Cursor.DEFAULT);
            arrowRight.setFitWidth(frontArrow.getWidth());
            arrowRight.setFitHeight(frontArrow.getHeight());
        });

        HBox weaponAndArrows = new HBox(0, arrowLeft, arrowRight);
        weaponAndArrows.getStyleClass().addAll("center");
        weaponsPane.getChildren().addAll(weaponsRect, weaponAndArrows);
        //dagger

        HBox[] weaponBoxes = new HBox[weapons.length];
        for (int i = 0; i < weapons.length; ++i) {
            HBox weaponBox = new HBox(5);
            weaponBox.setPadding(new Insets(0, 30, 0, 0));
            weaponBox.getStyleClass().addAll("center");
            VBox dChar = new VBox(10);
            dChar.getStyleClass().addAll("center");
            StackPane dSprite = new StackPane();
            dSprite.getChildren().add(new ImageView(new Image("/main/design/images/" + weapons[i] + ".png")));
            Label dName = new Label(capitalize(weapons[i]));
            dName.getStyleClass().addAll("dName", "weapon_description");
            Label dAttack = new Label("Attack: 20");
            dAttack.getStyleClass().addAll("dAttack", "weapon_description");
            Label dSpeed = new Label("Speed: 30");
            dSpeed.getStyleClass().addAll("dSpeed", "weapon_description");
            dChar.getChildren().addAll(dName, dAttack, dSpeed);
            weaponBox.getChildren().addAll(dSprite, dChar);
            weaponBoxes[i] = weaponBox;
        }

        weaponAndArrows.getChildren().add(1, weaponBoxes[0]);
        arrowLeft.setOnMouseClicked(e -> {
            indexW--;
            weaponAndArrows.getChildren().remove(1);
            if (indexW < 0) {
                indexW = 2;
            }
            weaponAndArrows.getChildren().add(1, weaponBoxes[indexW]);
        });
        arrowRight.setOnMouseClicked(e -> {
            indexW++;
            weaponAndArrows.getChildren().remove(1);
            if (indexW > 2) {
                indexW = 0;
            }
            weaponAndArrows.getChildren().add(1, weaponBoxes[indexW]);
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
        diffCombo.setId("diffCombo");
        diffCombo.setPromptText("Default (Easy)");
        diffCombo.getStyleClass().add("diff_combo");
        diffCombo.getItems().addAll(
                "Easy", "Medium", "Hard"
        );
        diffVBox.getChildren().addAll(diffLabel, diffCombo);

        entryFields.getChildren().addAll(name, diff, weaponsPane);

        //vbox for choosing character look
        VBox chooseChar = new VBox(15);
        chooseChar.setMinWidth(320);
        chooseChar.setPadding(new Insets(0, 0, 0,10));
        chooseChar.getStyleClass().addAll("choose_character", "center");

        StackPane character = new StackPane();

        character.getChildren().add(new ImageView(new Image(characters[indexC])));
        character.getStyleClass().add("character");

        StackPane navButtons = new StackPane();
        Rectangle rectNav = new Rectangle(150, 50);
        rectNav.getStyleClass().addAll("rect", "center");
        HBox navButtons_hbox = new HBox(15); //flip through character skins
        navButtons.getChildren().addAll(rectNav, navButtons_hbox);
        navButtons_hbox.getStyleClass().addAll("nav_buttons_hbox", "center");

        Image forwardChar = new Image("/main/design/images/arrow right.png", 16, 24, false, false);
        ImageView forward = new ImageView(forwardChar);
        forward.setId("forwardChar");
        forward.setOnMouseEntered(e -> playerSetUp.setCursor(Cursor.CLOSED_HAND));
        forward.setOnMouseExited(e -> playerSetUp.setCursor(Cursor.DEFAULT));
        forward.setOnMouseClicked(e -> {
            indexC++;
            character.getChildren().remove(0);
            if (indexC > 2) {
                indexC = 0;
            }
            character.getChildren().add(new ImageView(new Image(characters[indexC])));
        });

        Image backwardChar = new Image("/main/design/images/arrow left.png", 16, 24, false, false);
        ImageView backward = new ImageView(backwardChar);
        backward.setId("backwardChar");
        backward.setOnMouseEntered(e -> playerSetUp.setCursor(Cursor.CLOSED_HAND));
        backward.setOnMouseExited(e -> playerSetUp.setCursor(Cursor.DEFAULT));
        backward.setOnMouseClicked(e -> {
            indexC--;
            character.getChildren().remove(0);
            if (indexC < 0) {
                indexC = 2;
            }
            character.getChildren().add(new ImageView(new Image(characters[indexC])));
        });

        Button choose = new Button("Choose");
        choose.setId("choose");
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
        toMainScreen.setOnAction(event -> MainScreen.setScene(WelcomeScreen.getScene()));

        Button start = new Button("Start Game");
        start.setId("start");
        start.getStyleClass().addAll("start", "bottom_buttons");
        start.setOnAction(event -> {
            if (nameField.getText().isEmpty()) {
                nameField.setStyle("-fx-background-color: #8a0202;");
                nameField.clear();
                nameField.setPromptText("No Name Entered");
                //throw new NoNameException("No name entered");
            } else if (nameField.getText().charAt(0) == ' ') {
                nameField.setStyle("-fx-background-color: #8a0202;");
                nameField.clear();
                nameField.setPromptText("Name can't Start with a Space");
                //throw new IllegalArgumentException("Name cannot start with a space.");
            } else {
                //updates name, weapon, and difficulty for player
                Controller.createPlayer(400, 400, nameField.getText(), weapons[indexW], characters[indexC]);
                if (diffCombo.getValue() == null) {
                    Controller.setDifficultyLevel("Easy");
                } else {
                    Controller.setDifficultyLevel(diffCombo.getValue());
                }
                MainScreen.setScene(Room.getScene("empty"));
            }
        });

        bottom_buttons.getChildren().addAll(toMainScreen, start);

        screen.getChildren().add(bottom_buttons);

        playerSetUp.getStylesheets().add("/main/design/PlayerSetup.css");

        return playerSetUp;
    }

}
