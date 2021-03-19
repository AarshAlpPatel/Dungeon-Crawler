package main.frontend;

import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
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
        "char1.gif",
        "char2.gif", 
        "char3.gif"
    };
    private static String[] weapons = {
        "dagger",
        "spear",
        "axe"
    };

//    private static ImageCursor defaultCursor = new ImageCursor(new Image("/main/design/images/cursors/default_cursor.png"));
//    private static ImageCursor clickCursor = new ImageCursor(new Image("/main/design/images/cursors/custom_click.png"));

    private static String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    private static ImageView setArrow(String imagePath, String id, Scene playerSetUp) {
        Image arrow = new Image(imagePath, 16, 24, false, false);
        ImageView direction = new ImageView(arrow);
        direction.setId(id);
        direction.setOnMouseEntered(e -> {
            playerSetUp.setCursor(Cursor.HAND);
        });
        direction.setOnMouseExited(e -> {
            playerSetUp.setCursor(Cursor.DEFAULT);
        });
        return direction;
    }

    private static Pane setWeaponsPane(Scene playerSetUp) {
        StackPane weaponsPane = new StackPane();
        weaponsPane.getStyleClass().add("weapons");
        Rectangle weaponsRect = new Rectangle(450, 200);
        weaponsRect.getStyleClass().addAll("rect", "weapons_rect");

        ImageView arrowLeft = setArrow("/main/design/images/arrow left.png",
                                       "backwardWeapon", playerSetUp);
        ImageView arrowRight = setArrow("/main/design/images/arrow right.png",
                                        "forwardWeapon", playerSetUp);

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
            dSprite.getChildren().add(
                    new ImageView(new Image("/main/design/images/" + weapons[i] + "-angle.png"))
            );
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

        return weaponsPane;
    }

    private static ImageView setChar(String imagePath, 
                Scene playerSetUp, StackPane character, int add, String id) {
        Image imageChar = new Image(imagePath, 16, 24, false, false);
        ImageView direction = new ImageView(imageChar);
        direction.setId(id);
        direction.setOnMouseEntered(e -> {
            playerSetUp.setCursor(Cursor.HAND);
        });
        direction.setOnMouseExited(e -> playerSetUp.setCursor(Cursor.DEFAULT));
        direction.setOnMouseClicked(e -> {
            indexC += add;
            character.getChildren().remove(0);
            if (indexC > 2) {
                indexC = 0;
            }
            if (indexC < 0) {
                indexC = 2;
            }
            character.getChildren().add(new ImageView(new Image("/main/design/images/" + characters[indexC])));
        });
        return direction;
    }

    private static Pane setDiffPane(ComboBox<String> diffCombo) {
        StackPane diff = new StackPane();
        VBox diffVBox = new VBox();
        diffVBox.getStyleClass().addAll("diff_vbox", "center", "entry_boxes");
        diff.getStyleClass().add("diff");
        Rectangle rectDiff = new Rectangle(300, 75);
        rectDiff.getStyleClass().add("rect");
        diff.getChildren().addAll(rectDiff, diffVBox);
        Label diffLabel = new Label("Difficulty");
        diffLabel.getStyleClass().addAll("diff_label", "label");
        diffVBox.getChildren().addAll(diffLabel, diffCombo);
        return diff;
    }
    
    private static Pane setNamePane(TextField nameField) {
        StackPane name = new StackPane();
        name.getStyleClass().addAll("name", "center");
        VBox nameVBox = new VBox();
        Rectangle rectName = new Rectangle(300, 75);
        rectName.getStyleClass().add("rect");
        name.getChildren().addAll(rectName, nameVBox);
        nameVBox.getStyleClass().addAll("name_vbox", "center", "entry_boxes");
        Label nameLabel = new Label("Enter Name");
        nameLabel.getStyleClass().addAll("name_label", "label");
        nameVBox.getChildren().addAll(nameLabel, nameField);
        return name;
    }

    public static Scene getScene() {
        //top is the customization panel and bottom are the start and back buttons
        VBox screen = new VBox();
        Scene playerSetUp = new Scene(screen, MainScreen.getLength(), MainScreen.getHeight());
        screen.getStyleClass().addAll("screen", "center");

        //contains the entryFields and the character chooser
        StackPane customizationPanel = new StackPane();
        customizationPanel.getStyleClass().addAll("customization_panel", "center");
        Pane customizationPanelBack = new Pane();
        customizationPanelBack.getStyleClass().addAll("customization_panel_back");
        HBox customizationPanelHbox = new HBox();
        customizationPanelHbox.getStyleClass().addAll("customization_panel_hbox", "center");
        customizationPanel.getChildren().addAll(customizationPanelBack, customizationPanelHbox);

        //entry fields for name, weapon, and difficulty
        VBox entryFields = new VBox();
        entryFields.getStyleClass().addAll("entry_fields", "center");

        //enter name -- Start Game button checks if its blank
        TextField nameField = new TextField();
        nameField.setId("nameField");
        nameField.setOnMouseClicked(event -> nameField.setStyle("-fx-background-color: #ffffff;"));
        nameField.setMaxWidth(250);
        nameField.setPromptText("Enter Player Name");
        nameField.getStyleClass().add("name_field");

        ComboBox<String> diffCombo = new ComboBox<>();
        diffCombo.setId("diffCombo");
        diffCombo.setPromptText("Default (" + Controller.getDifficultyLevel() + ")");
        diffCombo.getStyleClass().add("diff_combo");
        diffCombo.getItems().addAll(
                "Easy", "Medium", "Hard"
        );

        entryFields.getChildren().addAll(setNamePane(nameField),
                                         setDiffPane(diffCombo),
                                         setWeaponsPane(playerSetUp));

        //vbox for choosing character look
        VBox chooseChar = new VBox(15);
        chooseChar.setMinWidth(320);
        chooseChar.setPadding(new Insets(0, 0, 0, 10));
        chooseChar.getStyleClass().addAll("choose_character", "center");

        StackPane character = new StackPane();

        character.getChildren().add(new ImageView(new Image("/main/design/images/" + characters[indexC])));
        character.getStyleClass().add("character");

        StackPane navButtons = new StackPane();
        Rectangle rectNav = new Rectangle(150, 50);
        rectNav.getStyleClass().addAll("rect", "center");
        HBox navButtonsHbox = new HBox(15); //flip through character skins
        navButtons.getChildren().addAll(rectNav, navButtonsHbox);
        navButtonsHbox.getStyleClass().addAll("nav_buttons_hbox", "center");

        ImageView forward = setChar("/main/design/images/arrow right.png",
                                    playerSetUp, character, 1, "forwardChar");
        ImageView backward = setChar("/main/design/images/arrow left.png",
                                     playerSetUp, character, -1, "backwardChar");

        Button choose = new Button("Choose");
        choose.setId("choose");
        choose.getStyleClass().add("choose");
        navButtonsHbox.getChildren().addAll(backward, choose, forward);
        choose.setOnAction(event -> {
            //add the checkmark and change text (checks or unchecks based on getText)
            if (choose.getText().equals("Choose")) {
                forward.setDisable(true);
                backward.setDisable(true);
                navButtonsHbox.getChildren().remove(0);
                navButtonsHbox.getChildren().remove(1);
                //change flash to image and have image change between the pictures
                character.getChildren().get(0).setOpacity(0.7);
                character.getChildren().add(new ImageView("/main/design/images/check.png"));
                choose.setText("Uncheck");
            } else if (choose.getText().equals("Uncheck")) {
                navButtonsHbox.getChildren().add(0, backward);
                navButtonsHbox.getChildren().add(2, forward);
                character.getChildren().remove(1);
                character.getChildren().get(0).setOpacity(1);
                forward.setDisable(false);
                backward.setDisable(false);
                choose.setText("Choose");
            }
        });

        chooseChar.getChildren().addAll(character, navButtons);

        customizationPanelHbox.getChildren().addAll(chooseChar, entryFields);

        screen.getChildren().add(customizationPanel);

        HBox bottomButtons = new HBox(50);
        bottomButtons.getStyleClass().addAll("bottom_buttons_hbox", "center");
        Button toMainScreen = new Button("Main Menu");
        toMainScreen.setId("mainMenu");
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
                Controller.createPlayer(
                        400, 400, nameField.getText(), weapons[indexW], characters[indexC]
                );
                if (diffCombo.getValue() != null) {
                    Controller.setDifficultyLevel(diffCombo.getValue());
                }
                MainScreen.setScene(Room.getScene());
            }
        });

        bottomButtons.getChildren().addAll(toMainScreen, start);

        screen.getChildren().add(bottomButtons);

        playerSetUp.getStylesheets().add("/main/design/PlayerSetup.css");

        return playerSetUp;
    }

}
