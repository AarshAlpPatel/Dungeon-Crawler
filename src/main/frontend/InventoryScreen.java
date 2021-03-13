package main.frontend;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import main.backend.characters.Player;
import main.backend.weapons.*;

import java.util.ArrayList;
import java.util.List;

public class InventoryScreen {

    //scene to be returned
    private static Scene inventoryScreen;

    //main screen
    private static VBox screen;

    //what the item moves on when being dragged (Pane uses x and y)
    private static Pane dragBox;

    //finding the closest rectangle to the item being dragged
    private static List<Rectangle> rectangles = new ArrayList<>();

    //sets up the blue, translucent background and StackPane for holding all of the slots
    private static StackPane createBackground() {
        StackPane inventory = new StackPane();
        Pane inventoryBack = new Pane();
        inventoryBack.getStyleClass().add("inventoryBack");
        inventory.getChildren().add(inventoryBack);
        return inventory;
    }

    //holds main weapons
    private static HBox createWeaponHBox() {
        HBox weapons = new HBox(30);

        //Weapon playerMain = Player.getInstance().getMainWeapon();
        Image main = weaponImage(Player.getInstance().getMainWeapon());
        Image backup = weaponImage(Player.getInstance().getBackupWeapon());

        //main weapon
        StackPane mainWeapon = new StackPane();
        Rectangle mainRect = new Rectangle(300, 300);
        mainRect.getStyleClass().addAll("rectangle_slot");
        rectangles.add(mainRect);
        ImageView mainImage = new ImageView(main);
        mainWeapon.getChildren().addAll(mainRect, mainImage);

        //backup weapon
        StackPane backupWeapon = new StackPane();
        Rectangle backupRect = new Rectangle(300, 300);
        backupRect.getStyleClass().addAll("rectangle_slot");
        rectangles.add(backupRect);
        backupWeapon.getChildren().addAll(backupRect);
        if (backup != null) {
            ImageView backupImage = new ImageView(backup);
            backupWeapon.getChildren().addAll(backupImage);
        }
        handleSlotActions(mainWeapon, backupWeapon);
        //handleImageActions(mainImage, mainWeapon);

        weapons.getChildren().addAll(mainWeapon, backupWeapon);
        weapons.getStyleClass().add("center");

        return weapons;
    }

    //determines what weapon to show based on Player's main and backup
    private static Image weaponImage(Weapon wpn) {
        Image weapon;
        if (wpn instanceof Axe) {
            weapon = new Image("/main/design/images/axe-angle.png", 300, 300, false, false);
        } else if (wpn instanceof Spear) {
            weapon = new Image("/main/design/images/spear-angle.png", 300, 300, false, false);
        } else if (wpn instanceof Dagger){
            weapon = new Image("/main/design/images/dagger-angle.png", 300, 300, false, false);
        } else {
            weapon = null;
        }
        return weapon;
    }

    //creates slots for items, first item is money
    private static HBox createItemsHBox() {
        HBox items = new HBox(10);
        items.getStyleClass().add("center");
        StackPane slot1 = createItemPane();
        VBox cash = new VBox(10);
        cash.getStyleClass().addAll("center");
        Label cashNum = new Label(Player.getInstance().getCash().toString());
        cashNum.getStyleClass().addAll("cash_num");
        cash.getChildren().addAll(
                new ImageView(
                        new Image("/main/design/images/coin.png", 40, 40, false, false)),
                cashNum);
        slot1.getChildren().add(cash);
        StackPane slot2 = createItemPane();
        StackPane slot3 = createItemPane();
        StackPane slot4 = createItemPane();
        StackPane slot5 = createItemPane();
        items.getChildren().addAll(slot1, slot2, slot3, slot4, slot5);

        return items;
    }

    //sets up the rectangle inside the pane
    private static StackPane createItemPane() {
        StackPane slot = new StackPane();
        slot.getStyleClass().addAll("center");
        Rectangle rect = new Rectangle(100, 100);
        rect.getStyleClass().addAll("rectangle_slot");
        rectangles.add(rect);
        //slot.setMinSize(80, 80);
        slot.getStyleClass().add("center");
        slot.getChildren().add(rect);
        handleSlotActions(slot);

        return slot;
    }

    //exit button
    private static HBox createBottomButtons() {
        HBox bottomButtons = new HBox();
        Button exitInventory = new Button("Exit");
        exitInventory.getStyleClass().addAll("exit_button");
        exitInventory.setOnAction(event -> MainScreen.setScene(Room.getScene()));
        bottomButtons.getStyleClass().add("center");
        bottomButtons.getChildren().add(exitInventory);

        return bottomButtons;
    }

    //rects change colors when hovered over and change cursor
    private static void handleSlotActions(Pane...panes) {
        for (Pane pane : panes) {
            pane.setOnMouseEntered(event -> {
                int i = 0;
                while (!(pane.getChildren().get(i) instanceof Rectangle)) {
                    i++;
                }
                ((Rectangle) pane.getChildren().get(i)).setFill(Color.RED);
                pane.getChildren().get(i).setOpacity(0.7);
                inventoryScreen.setCursor(new ImageCursor(new Image("/main/design/images/cursors/custom_click.png")));
//                shape.setWidth(shape.getWidth() + 5);
//                shape.setWidth(shape.getHeight() + 5);
            });
            pane.setOnMouseExited(event -> {
                int i = 0;
                while (!(pane.getChildren().get(i) instanceof Rectangle)) {
                    i++;
                }
                ((Rectangle) pane.getChildren().get(i)).setFill(Color.BLACK);
                pane.getChildren().get(i).setOpacity(1);
                inventoryScreen.setCursor(new ImageCursor(new Image("/main/design/images/cursors/default_cursor.png")));
//                shape.setWidth(shape.getWidth() - 5);
//                shape.setWidth(shape.getHeight() - 5);
            });
        }
    }

    private static double startX;
    private static double startY;
    private static double distX;
    private static double distY;

//    static void setStartX(double value) {
//        startX = value;
//    }
//
//    static void setStartY(double value) {
//        startY = value;
//    }
//
//    static void setDistX(double value) {
//        distX = value;
//    }
//
//    static void setDistY(double value) {
//        distY = value;
//    }

    //for dragging pictures
    private static void handleImageActions(ImageView image, Pane pane) {
        //corner is 0, 0
        pane.setOnMouseEntered(event -> {
            inventoryScreen.setCursor(Cursor.HAND);
        });
        pane.setOnMouseExited(event -> {
            inventoryScreen.setCursor(Cursor.DEFAULT);
        });
        pane.setOnMousePressed(event -> {
            pane.getChildren().remove(1);
            dragBox.getChildren().add(image);
            image.setFitWidth(image.getFitHeight() / 2);
            image.setFitHeight(image.getFitHeight() / 2);
            startX = event.getX();
            startY = event.getY();
            image.setX(startX);
            image.setY(startY);
            distX = startX - image.getX();
            distY = startY - image.getY();
        });
        pane.setOnMouseDragged(event -> {
//            System.out.println(image.getX());
//            System.out.println(event.getX());
            image.setX(event.getX());
            image.setY(event.getY());
        });
        pane.setOnMouseReleased(event -> {
            image.setFitWidth(image.getFitHeight() * 2);
            image.setFitHeight(image.getFitHeight() * 2);
            dragBox.getChildren().remove(image);
            pane.getChildren().add(image);
        });
    }

    public static Scene getScene() {
        HBox emptyPaneTop = new HBox();
        emptyPaneTop.setPadding(new Insets(0, 0, 50, 0));
        screen = new VBox(25);
        screen.getStyleClass().addAll("screen", "center");
        dragBox = new Pane();
        StackPane inventory = createBackground();
        VBox itemHolders = new VBox(20);
        itemHolders.getStyleClass().add("center");
        HBox weapons = createWeaponHBox();
        weapons.setPadding(new Insets(75, 0, 20, 0));
        HBox items = createItemsHBox();
        items.setPadding(new Insets(20, 0, 75, 0));
        itemHolders.getChildren().addAll(weapons, items);
        inventory.getChildren().add(itemHolders);
        HBox bottomButtons = createBottomButtons();
        screen.getChildren().addAll(emptyPaneTop, inventory, bottomButtons, dragBox);

        inventoryScreen = new Scene(screen, MainScreen.getLength(), MainScreen.getHeight());
        //for dragging the weapons
        dragBox.setPrefWidth(inventoryScreen.getWidth());
        dragBox.setPrefHeight(inventoryScreen.getHeight());
        inventoryScreen.getStylesheets().add("/main/design/Inventory.css");
        return inventoryScreen;
    }
}
