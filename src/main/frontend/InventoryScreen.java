package main.frontend;

import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import main.backend.Controller;
import main.backend.characters.Player;
import main.backend.exceptions.IllegalDropException;
import main.backend.potions.Potion;
import main.backend.weapons.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class InventoryScreen {

    //scene to be returned
    private static Scene inventoryScreen;

    //main screen
    private static VBox screen;

    //keeps track of selected slot for slot actions
    private static Slot selected;

    //drop button
    private static Button drop;

    //use button
    private static Button use;

    //holds messages
    private static HBox emptyPaneTop;

    //all of the potions used that need to be applied
    private static List<Potion> potionsUsed = new ArrayList<>(5);

    //slots
    private static Slot mainWeapon;
    private static Slot backupWeapon;

    //list of the item slots
    private static ArrayList<Slot> itemsList;

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

        Image main = weaponImage(Player.getInstance().getInventory().getWeapon(0));
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
        itemsList = new ArrayList<>();
        HBox items = new HBox(10);
        items.getStyleClass().add("center");
        Slot item1 = Player.getInstance().getInventory().getPotion(0) == null ?
                new Slot("Item", 1) :
                new Slot(Player.getInstance().getInventory()
                        .getPotion(0).getRawImage(), "Item", 1);

        Slot item2 = Player.getInstance().getInventory().getPotion(1) == null ?
                new Slot("Item", 2) :
                new Slot(Player.getInstance().getInventory()
                        .getPotion(1).getRawImage(), "Item", 2);

        Slot item3 = Player.getInstance().getInventory().getPotion(2) == null ?
                new Slot("Item", 3) :
                new Slot(Player.getInstance().getInventory()
                        .getPotion(2).getRawImage(), "Item", 3);

        Slot item4 = Player.getInstance().getInventory().getPotion(3) == null ?
                new Slot("Item", 4) :
                new Slot(Player.getInstance().getInventory()
                        .getPotion(3).getRawImage(), "Item", 4);

        Slot item5 = Player.getInstance().getInventory().getPotion(4) == null ?
                new Slot("Item", 5) :
                new Slot(Player.getInstance().getInventory()
                        .getPotion(4).getRawImage(), "Item", 5);

        itemsList.add(item1);
        itemsList.add(item2);
        itemsList.add(item3);
        itemsList.add(item4);
        itemsList.add(item5);

        items.getChildren().addAll(item1, item2, item3, item4, item5);

        return items;

//        VBox cash = new VBox(10);
//        cash.getStyleClass().addAll("center");
//        Label cashNum = new Label(Player.getInstance().getCash().toString());
//        cashNum.getStyleClass().addAll("cash_num");
//        cash.getChildren().addAll(
//                new ImageView(
//                        new Image("/main/design/images/coin.png", 40, 40, false, false)),
//                cashNum);
//        item1.getChildren().add(cash)
    }

    //exit button
    private static HBox createBottomButtons() {
        HBox bottomButtons = new HBox(40);

        use = new Button("Use");
        use.getStyleClass().addAll("use_button");
        if (selected == null || selected.type.equals("Weapon")) {
            use.setDisable(true);
        }
        use.setOnAction(event -> {
            selected.handleUse();
        });
        bottomButtons.getStyleClass().add("center");

        drop = new Button("Drop");
        drop.setId("drop");
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
        bottomButtons.getChildren().addAll(use, drop);

        return bottomButtons;
    }

    private static void handleExit() {
        MainScreen.setScene(GameManager.getScene());
        if (selected != null)
            selected.handleDeselect();
        GameManager.unpauseGameLoop();
        if (!potionsUsed.isEmpty()) {
            Potion current = potionsUsed.get(0);
            for (Potion potion : potionsUsed) {
                Player.getInstance().applyPotion(potion);
            }
            potionsUsed.clear();
        }
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

    private static void resetPotionSlots() {
        //sliding things down
        for (Slot slot : itemsList) {
            if (!slot.isEmpty())
                slot.getChildren().remove(slot.getChildren().size() - 1);
        }

        for (int i = 0; i < 5; i++) {
            if (Player.getInstance().getInventory().getPotion(i) == null) {
                break;
            } else {
                itemsList.get(i).add(Player.getInstance().getInventory().getPotion(i).getRawImage());
            }
        }
    }

    private static void setKeyBinds() {
        inventoryScreen.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.H) {
                System.out.println("Exit handled");
                handleExit();
            }
        });
    }

    public static Slot getSelected() {
        return selected;
    }

    public static String getSelectedID() {
        return selected.getId();
    }

    public static Rectangle getSelectedRectangle() {
        return selected.rect;
    }

    public static Button getUse() {
        return use;
    }

    public static Scene getScene() {

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
        setKeyBinds();
        inventoryScreen.getStylesheets().add("/main/design/Inventory.css");
        return inventoryScreen;
    }

    public static class Slot extends StackPane {
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
//            if (this.child == null) {
//                child = new Pane();
//                add(child);
//            }
            this.type = type;
            this.num = num;
            this.setId(type + num);
            System.out.println(this.getId());
        }

        public Slot(Node child, String type, int num) {
            this(type, num);
            this.child = child;
            add(child);
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
            if (!selected.type.equals("Weapon") && !selected.isEmpty())
                use.setDisable(false);
        }

        private void handleDeselect() {
            if (emptyPaneTop.getChildren().size() == 1) {
                emptyPaneTop.getChildren().remove(0);
                emptyPaneTop.setPadding(new Insets(25, 0, 10, 0));
            }

            selected.reset();
            selected = null;
            drop.setDisable(true);
            use.setDisable(true);
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
                resetPotionSlots();
            }
            selected.handleDeselect();
        }

        private void handleUse() { //only for potions
            int index = switch (num) {
                case 2 -> 1;
                case 3 -> 2;
                case 4 -> 3;
                case 5 -> 4;
                default -> 0;
            };
            potionsUsed.add(Player.getInstance().getInventory().getPotion(index));
            selected.getChildren().remove(selected.getChildren().size() - 1);
            Player.getInstance().getInventory().dropPotion(index);
            selected.handleDeselect();
            resetPotionSlots();
        }
    }
}
