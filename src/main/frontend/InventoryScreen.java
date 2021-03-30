package main.frontend;

import javafx.geometry.Insets;
import javafx.scene.Cursor;
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

    /*
     * when the image starts getting dragged, this gets covered by the dragBox,
     * effectively rendering all of the other mouseEntering and exiting actions
     * for the slots underneath useless because they are no longer visible to the mouse
     */
    private static StackPane screenHolder;

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
            handleImageActions(backupImage, backupWeapon);
        }
        handleSlotActions(mainWeapon, backupWeapon);
        //handleImageActions(mainImage, mainWeapon);

        weapons.getChildren().addAll(mainWeapon, backupWeapon);
        weapons.getStyleClass().add("center");
        System.out.println(mainImage.getX());
        handleImageActions(mainImage, mainWeapon);

        return weapons;
    }

    //determines what weapon to show based on Player's main and backup
    private static Image weaponImage(Weapon wpn) {
        Image weapon;
        if (wpn instanceof Axe) {
            weapon = new Image("/main/design/images/axe-angle.png", 300, 300, false, false);
        } else if (wpn instanceof Spear) {
            weapon = new Image("/main/design/images/spear-angle.png", 300, 300, false, false);
        } else if (wpn instanceof Dagger) {
            weapon = new Image("/main/design/images/dagger-drag.png", 200, 200, false, false);
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
                inventoryScreen.setCursor(Cursor.HAND);
                //shape.setWidth(shape.getWidth() + 5);
                //shape.setWidth(shape.getHeight() + 5);
            });
            pane.setOnMouseExited(event -> {
                int i = 0;
                while (!(pane.getChildren().get(i) instanceof Rectangle)) {
                    i++;
                }
                ((Rectangle) pane.getChildren().get(i)).setFill(Color.rgb(50, 48, 48));
                pane.getChildren().get(i).setOpacity(1);
                inventoryScreen.setCursor(Cursor.DEFAULT);
                //shape.setWidth(shape.getWidth() - 5);
                //shape.setWidth(shape.getHeight() - 5);
            });
        }
    }

    private static double startX;
    private static double startY;
    private static double distX;
    private static double distY;
    private static int i = 1;

    //for dragging pictures
    private static void handleImageActions(ImageView image, Pane pane) {
        //corner is 0, 0
        //pane.setOnMouseEntered(event -> {
        //    inventoryScreen.setCursor(Cursor.HAND);
        //});
        //pane.setOnMouseExited(event -> {
        //    inventoryScreen.setCursor(Cursor.DEFAULT);
        //});
        image.setOnMousePressed(event -> {
            //int i = 0;
            //while (pane.getChildren().get(i) instanceof ImageView == false) {
            //    i++;
            //}
            pane.getChildren().remove(image);
            screenHolder.getChildren().add(dragBox);
            dragBox.getChildren().add(image);
            //image.set
            System.out.println(image.getX() + " " + image.getY());
            System.out.println(event.getSceneX() + " " + event.getSceneY());
            //image.setX(event.getSceneX());
            //image.setY(event.getSceneY());
            //System.out.println(image.getX());
            //image.setFitWidth(image.getFitHeight() / 2);
            //image.setFitHeight(image.getFitHeight() / 2);
            //startX = event.getSceneX();
            //startY = event.getSceneY();
        });
        image.setOnMouseDragged(event -> {
            image.setX(event.getSceneX() - startX);
            image.setY(event.getSceneY() - startY);
        });
        image.setOnMouseReleased(event -> {
            //image.setFitWidth(image.getFitHeight() * 2);
            //image.setFitHeight(image.getFitHeight() * 2);
            System.out.println(event.getSceneX() + " " + event.getSceneY());
            screenHolder.getChildren().remove(dragBox);
            dragBox.getChildren().remove(image);
            pane.getChildren().add(image);
        });
    }

    public static Scene getScene() {
        HBox emptyPaneTop = new HBox();
        emptyPaneTop.setPadding(new Insets(0, 0, 50, 0));
        screenHolder = new StackPane();
        screen = new VBox(25);
        screen.getStyleClass().addAll("screen", "center");
        dragBox = new Pane();
        dragBox.getStyleClass().addAll("drag_box");
        dragBox.setPrefHeight(MainScreen.getHeight());
        dragBox.setPrefWidth(MainScreen.getLength());
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
        screen.getChildren().addAll(emptyPaneTop, inventory, bottomButtons);
        screenHolder.getChildren().addAll(screen);

        inventoryScreen = new Scene(screenHolder, MainScreen.getLength(), MainScreen.getHeight());
        //for dragging the weapons
        dragBox.setPrefWidth(inventoryScreen.getWidth());
        dragBox.setPrefHeight(inventoryScreen.getHeight());
        inventoryScreen.getStylesheets().add("/main/design/Inventory.css");
        return inventoryScreen;
    }
}
