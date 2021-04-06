package main.frontend;

import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
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

    //finding the closest rectangle to the item being dragged
    private static List<Rectangle> rectangles = new ArrayList<>();

    //keeps track of selected slot for slot actions
    private static Slot selected;

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
        Image main = weaponImage(Player.getInstance().getInventory().getWeapon(0));
        if (Player.getInstance().getInventory().getWeapon(1) == null)
            Player.getInstance().getInventory().addWeapon(new Axe(0, 0, false, 0, 0, 2));
        Image backup = weaponImage(Player.getInstance().getInventory().getWeapon(1));

        //main weapon
        ImageView mainV = new ImageView(main);
        Slot mainWeapon = new Slot(mainV, "Weapon");

        //backup weapon
        Slot backupWeapon;
        if (backup != null) {
            ImageView backupV = new ImageView(backup);
            backupWeapon = new Slot(backupV, "Weapon");
        } else {
            backupWeapon = new Slot("Weapon");
        }
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
        Slot item1 = new Slot("Item");
        VBox cash = new VBox(10);
        cash.getStyleClass().addAll("center");
        Label cashNum = new Label(Player.getInstance().getCash().toString());
        cashNum.getStyleClass().addAll("cash_num");
        cash.getChildren().addAll(
                new ImageView(
                        new Image("/main/design/images/coin.png", 40, 40, false, false)),
                cashNum);
        item1.getChildren().add(cash);
        Slot item2 = new Slot("Item");
        Slot item3 = new Slot("Item");
        Slot item4 = new Slot("Item");
        Slot item5 = new Slot("Item");
        items.getChildren().addAll(item1, item2, item3, item4, item5);

        return items;
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

    public static Scene getScene() {
        HBox emptyPaneTop = new HBox();
        emptyPaneTop.setPadding(new Insets(0, 0, 50, 0));
        screenHolder = new StackPane();
        screen = new VBox(25);
        screen.getStyleClass().addAll("screen", "center");
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
        inventoryScreen.getStylesheets().add("/main/design/Inventory.css");
        return inventoryScreen;
    }

    private static class Slot extends StackPane {
        int clickCount = 0;
        Node child;
        String type;
        Rectangle rect;

        public Slot(String type) {
            //set up stackpane and rectangles
            if (type.equals("Weapon"))
                initSlot(300, 300);
            else
                initSlot(100, 100);
            this.handleMouseActions();
            if (this.child == null) {
                child = new Pane();
                add(child);
            }
            this.type = type;
        }

        public Slot(Node child, String type) {
            this(type);
            add(child);
            this.child = child;
        }

        public void initSlot(int d1, int d2) {
            rect = new Rectangle(d1, d2);
            rect.getStyleClass().add("rectangle_slot");
            this.getChildren().add(rect);
        }

        public void add(Node child) {
            if (child != null)
                this.getChildren().add(child);
        }

        public void remove() {
            this.getChildren().remove(this.getChildren().size() - 1);
        }

        public void remove(Node child) {
            if (child == null)
                this.getChildren().remove(child);
        }

        public void handleMouseActions() {
            this.setOnMousePressed(e -> {
                clickCount++;
            });
            this.setOnMouseReleased(e -> {
                System.out.println(clickCount);
                if (selected != null) {
                    if (selected == this) {
                        //deselect
                        handleDeselect();
                    } else if (this.type.equals(selected.type)){
                        //swap items
                        handleSwap();
                    } else {
                        selected.handleDeselect();
                        selected = this;
                        handleSelect();
                    }
                } else {
                    selected = this;
                    handleSelect();
                }
            });
            this.setOnMouseEntered(e -> {
                inventoryScreen.setCursor(Cursor.HAND);
                if (this != selected) {
                    int i = 0;
                    while (!(this.getChildren().get(i) instanceof Rectangle)) {
                        i++;
                    }
                    ((Rectangle) this.getChildren().get(i)).setFill(Color.RED);
                    this.getChildren().get(i).setOpacity(0.7);
                }
            });
            this.setOnMouseExited(e -> {
                inventoryScreen.setCursor(Cursor.DEFAULT);
                if (this != selected) {
                    reset();
                }
            });
        }

        private void reset() {
            int i = 0;
            while (!(this.getChildren().get(i) instanceof Rectangle)) {
                i++;
            }
            ((Rectangle) this.getChildren().get(i)).setFill(Color.rgb(50, 48, 48));
            this.getChildren().get(i).setOpacity(1);
        }

        private void handleSelect() {
            rect.setOpacity(0.5);
        }

        private void handleDeselect() {
            selected = null;
            this.reset();
        }

        private void handleSwap() {
            Node item1 = selected.getChildren().get(selected.getChildren().size() - 1);
            Node item2 = this.child;
            selected.remove(item1);
            this.remove(item2);
            selected.add(item2);
            this.add(item1);
            selected.handleDeselect();
        }
    }
}
