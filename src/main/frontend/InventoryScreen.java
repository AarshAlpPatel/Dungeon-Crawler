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
import main.backend.Controller;
import main.backend.characters.Player;
import main.backend.exceptions.IllegalDropException;
import main.backend.potions.Potion;
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

    //drop button
    private static Button drop;

    //holds messages
    private static HBox emptyPaneTop;

    //slots
    private static Slot mainWeapon;
    private static Slot backupWeapon;
    private static Slot item1;
    private static Slot item2;
    private static Slot item3;
    private static Slot item4;
    private static Slot item5;

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
//        if (Player.getInstance().getInventory().getNumWeapons() == 1)
//            Player.getInstance().getInventory().addWeapon(new Axe(0, 0, false, 0, 0, 2));
        Image backup = null;
        if (Player.getInstance().getInventory().getNumWeapons() == 2) {
            backup = weaponImage(Player.getInstance().getInventory().getWeapon(1));
        }

        //main weapon
        ImageView mainV = new ImageView(main);
        mainWeapon = new Slot(mainV, "Weapon", 1);

        //backup weapon
        if (backup != null) {
            ImageView backupV = new ImageView(backup);
            backupWeapon = new Slot(backupV, "Weapon", 2);
        } else {
            backupWeapon = new Slot("Weapon", 2);
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
        item1 = new Slot("Item", 1);
//        VBox cash = new VBox(10);
//        cash.getStyleClass().addAll("center");
//        Label cashNum = new Label(Player.getInstance().getCash().toString());
//        cashNum.getStyleClass().addAll("cash_num");
//        cash.getChildren().addAll(
//                new ImageView(
//                        new Image("/main/design/images/coin.png", 40, 40, false, false)),
//                cashNum);
//        item1.getChildren().add(cash);
        item2 = new Slot("Item", 2);
        item3 = new Slot("Item", 3);
        item4 = new Slot("Item", 4);
        item5 = new Slot("Item", 5);
        items.getChildren().addAll(item1, item2, item3, item4, item5);

        return items;
    }

    //exit button
    private static HBox createBottomButtons() {
        HBox bottomButtons = new HBox(40);

        Button exitInventory = new Button("Exit");
        exitInventory.getStyleClass().addAll("exit_button");
        exitInventory.setOnAction(event -> {
            MainScreen.setScene(GameManager.getScene());
            if (selected != null)
                selected.handleDeselect();
            GameManager.unpauseGameLoop();
        });
        bottomButtons.getStyleClass().add("center");

        drop = new Button("Drop");
        drop.getStyleClass().addAll("drop_button");
        if (selected == null) {
            drop.setDisable(true);
        }
        drop.setOnAction(event -> {
            Label dropMessage = new Label();
            dropMessage.getStyleClass().add("message");
            try {
                selected.handleDrop();
            } catch (IllegalDropException ide) {
                System.out.println(ide.getMessage());
                dropMessage.setText(ide.getMessage());
                emptyPaneTop.getChildren().add(dropMessage);
                emptyPaneTop.setPadding(new Insets(6, 0, 2, 0));
            }
        });
        bottomButtons.getChildren().addAll(exitInventory, drop);

        return bottomButtons;
    }

    private static void setUpWeaponSlots() {
        mainWeapon.getChildren().add(Player.getInstance().getInventory().getWeapon(0).getRawImage());
        if (Player.getInstance().getInventory().getNumWeapons() == 2)
            backupWeapon.getChildren().add(Player.getInstance().getInventory().getWeapon(1).getRawImage());
    }

    private static void setUpPotionSlots(Slot first, Slot sec) {
        first.getChildren().add(Player.getInstance().getInventory().getPotion(first.num - 1).getRawImage());
        sec.getChildren().add(Player.getInstance().getInventory().getPotion(sec.num - 1).getRawImage());
    }

    public static Scene getScene() {

        //check player inventory
//        System.out.print("\nWeapons: ");
//        for (Weapon weapon : Player.getInstance().getInventory().getWeapons()) {
//            System.out.print(weapon.toString() + " ");
//        }
//        System.out.println();
//        System.out.print("Potions: ");
//        for (Potion potion : Player.getInstance().getInventory().getPotions()) {
//            System.out.print(potion.toString() + " ");
//        }
//        System.out.println();

        emptyPaneTop = new HBox();
        emptyPaneTop.getStyleClass().add("center");
        emptyPaneTop.setPadding(new Insets(25, 0, 10, 0));
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
        int num;

        public Slot(String type, int num) {
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
            this.num = num;
        }

        public Slot(Node child, String type, int num) {
            this(type, num);
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

        private boolean isEmpty() {
            return !(this.getChildren().get(this.getChildren().size() - 1) instanceof ImageView);
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
                        if (this.getChildren().get(this.getChildren().size() - 1) instanceof ImageView
                            && this.getChildren().size() == selected.getChildren().size()) {
                            handleSwap(this);
                        } else {
                            handleDeselect();
                        }
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
            rect.setFill(Color.RED);
            rect.setOpacity(0.5);
            if (!selected.isEmpty())
                drop.setDisable(false);
        }

        private void handleDeselect() {
            if (emptyPaneTop.getChildren().size() == 1) {
                emptyPaneTop.getChildren().remove(0);
                emptyPaneTop.setPadding(new Insets(25, 0, 10, 0));
            }

            selected.reset();
            selected = null;
            drop.setDisable(true);
            this.reset();
        }

        private void handleSwap(Slot other) {
            if (selected.type.equals("Weapon")) {
                //swapping weapons
                Weapon main = Player.getInstance().getInventory().getWeapon(0);
                Weapon sec = Player.getInstance().getInventory().getWeapon(1);

                Player.getInstance().getInventory().setWeapon(main, 1);
                Player.getInstance().getInventory().setWeapon(sec, 0);
                other.getChildren().remove(other.getChildren().size() - 1);
                selected.getChildren().remove(selected.getChildren().size() - 1);
                setUpWeaponSlots();
                selected.handleDeselect();
                Player.getInstance().switchWeapon(Player.getInstance().getInventory().getWeapon(0));
            } else {
                //swapping potions
                Potion selPotion = Player.getInstance().getInventory().getPotion(selected.num - 1);
                Potion thisPotion = Player.getInstance().getInventory().getPotion(other.num - 1);

                Player.getInstance().getInventory().setPotion(selPotion, other.num - 1);
                Player.getInstance().getInventory().setPotion(thisPotion, selected.num - 1);
                other.getChildren().remove(other.getChildren().size() - 1);
                selected.getChildren().remove(selected.getChildren().size() - 1);
                setUpPotionSlots(other, selected);
                selected.handleDeselect();
            }
        }

        private void handleDrop() {
            if (selected.type.equals("Weapon")) {
                //weapon drops
                if (selected.num == 1) {
                    throw new IllegalDropException("Cannot drop main weapon.");
                }
                if (emptyPaneTop.getChildren().size() == 1) {//remove message
                    emptyPaneTop.getChildren().remove(0);
                    emptyPaneTop.setPadding(new Insets(25, 0, 10, 0));
                }
                selected.getChildren().remove(selected.getChildren().size() - 1);
                //this.getChildren().remove(this.getChildren().size() - 1);
                Controller.dropCollectable(1, "weapon");
            } else {
                //potion drops
                selected.getChildren().remove(selected.getChildren().size() - 1);
                Controller.dropCollectable(selected.num - 1, "potion");
            }
            selected.handleDeselect();
        }
    }
}
