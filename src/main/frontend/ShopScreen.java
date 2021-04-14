package main.frontend;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import main.backend.Controller;
import main.backend.characters.Player;
import main.backend.collidables.Collidable;
import main.backend.exceptions.NotEnoughFundsException;
import main.backend.exceptions.TooManyPotions;
import main.backend.exceptions.TooManyWeapons;
import main.backend.weapons.*;
import main.backend.potions.*;

public class ShopScreen {
    private static Scene shopScreen;
    private static VBox screen;
    private static Slot selected;
    private static Panel checkoutPanel;
    private static Label cashValue = new Label(Player.getInstance().getCash().toString());
    private static Label message;

    private static StackPane createBackground() {
        StackPane shop = new StackPane();
        Pane shopBack = new Pane();
        shopBack.getStyleClass().add("shopBack");
        shop.getChildren().add(shopBack);
        shopBack.setPadding(new Insets(300, 0, 300, 0));
        shop.getStyleClass().add("center");
        return shop;
    }

    private static HBox createPanels() {
        HBox panels = new HBox(48);
        panels.getStyleClass().addAll("center");
        Panel potions = new Panel("potion");
        Panel weapons = new Panel("weapon");
        checkoutPanel = new Panel("checkout");
        panels.getChildren().addAll(potions, weapons, checkoutPanel);
        return panels;
    }

    private static void setKeyBinds() {
        shopScreen.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.G) {
                System.out.println("Exit handled");
                handleExit();
            }
        });
    }

    private static void handleExit() {
        MainScreen.setScene(GameManager.getScene());
        if (selected != null)
            selected.handleDeselect();
        GameManager.unpauseGameLoop();
    }

    public static Scene getScene() {
        screen = new VBox();
        HBox emptyTop = new HBox();
        message = new Label("Welcome to the shop!");
        emptyTop.getStyleClass().add("center");
        emptyTop.getChildren().add(message);
        emptyTop.setPadding(new Insets(10, 0, 25, 0));
        StackPane shop = createBackground();
        HBox panels = createPanels();
        shop.getChildren().add(panels);
        HBox cashBottom = new HBox();
        cashBottom.getChildren().addAll(new ImageView("/main/design/images/coin.png"), cashValue);
        cashBottom.getStyleClass().add("center");
        cashBottom.setPadding(new Insets(25, 0, 0, 0));
        screen.getChildren().addAll(emptyTop, shop, cashBottom);
        screen.getStyleClass().addAll("center", "screen");

        shopScreen = new Scene(screen, MainScreen.getLength(), MainScreen.getHeight());
        setKeyBinds();
        shopScreen.getStylesheets().add("main/design/Shop.css");
        return shopScreen;
    }

    private static class Panel extends StackPane {

        //potions, weapons, or checkout
        String type;

        VBox checkoutSlot = new VBox(20);
        VBox checkout = new VBox(20);

        Integer quantity = 1;

        Panel(String type) {
            this.type = type;
            initPanel();
        }

        private void initPanel() {
            Rectangle rect = new Rectangle(200, 500);
            this.getChildren().add(rect);
            this.getStyleClass().add("center");
            rect.getStyleClass().add("rect");
            switch (type) {
                case "weapon" -> this.getChildren().add(
                        createSlots("Dagger", "Axe", "Spear"));
                case "potion" -> this.getChildren().add(
                        createSlots("Health Potion", "Attack Potion", "Speed Potion"));
                case "checkout" -> this.getChildren().add(createBaseCheckout());
                default -> throw new RuntimeException("Unrecognized panel type entered.");
            }
        }

        private VBox createSlots(String name1, String name2, String name3) {
            VBox slots = new VBox(30);
            slots.getStyleClass().add("center");

            Slot slot1 = new Slot(name1, type);
            //System.out.println(name1);
            Slot slot2 = new Slot(name2, type);
            Slot slot3 = new Slot(name3, type);

            slots.getChildren().addAll(slot1, slot2, slot3);
            return slots;
        }

        private VBox createBaseCheckout() {
            checkout.getChildren().clear();
            checkout.getStyleClass().add("center");

            //update upon image being clicked
            checkout.getChildren().add(new Label("Choose an item."));
            return checkout;
        }

        private void updateCheckout(ImageView image, String name) {
            this.checkoutSlot.getChildren().clear();
            checkout.getChildren().clear();
            this.checkoutSlot.getChildren().add(image);
            this.checkoutSlot.getStyleClass().add("center");
            //description.getChildren().clear();
            String power = switch (name) {
                case "Dagger" -> "Damage: 7\nSpeed: 20";
                case "Spear" -> "Damage: 10\nSpeed: 15";
                case "Axe" -> "Damage: 20\nSpeed: 7";
                case "Health Potion" -> "20 health";
                case "Attack Potion" -> "+5 for 10 seconds";
                case "Speed Potion" -> "+1 for 10 seconds";
                default -> "null";
            };
            VBox description = new VBox(10);
            description.getStyleClass().add("center");
            Label nameL = new Label(name);
            Label powerL = new Label(power);
            powerL.getStyleClass().add("center");
            description.getChildren().addAll(nameL, powerL);
            //this.description = description;
            this.checkoutSlot.getChildren().add(description);
            Button buy = createBuyButton();
            checkout.getChildren().addAll(checkoutSlot, buy);
        }

        private HBox createQuantifier() {
            //VBox quantifier = new VBox();
            Label quantityLabel = new Label(quantity.toString());

            Image add = new Image("main/design/images/addButton.png", 25, 26, true, false);
            ImageView addButton = new ImageView(add);
            addButton.setOnMouseClicked(event -> {
                if (quantity < 5) {
                    quantity++;
                }
                quantityLabel.setText(quantity.toString());
                System.out.println(quantity);
            });

            Image subtract = new Image("main/design/images/subtractButton.png", 25, 26, true, false);
            ImageView subtractButton = new ImageView(subtract);
            subtractButton.setOnMouseClicked(event -> {
                if (quantity > 1) {
                    quantity--;
                }
                quantityLabel.setText(quantity.toString());
                System.out.println(quantity);
            });

            HBox quantifier = new HBox(10);
            quantifier.getStyleClass().add("center");
            quantifier.getChildren().addAll(subtractButton, quantityLabel, addButton);
            return quantifier;
        }

        private Button createBuyButton() {
            Button buy = new Button("Buy");
            buy.setOnAction(event -> {
                try {
                    handleBuy();
                } catch (TooManyPotions | TooManyWeapons | NotEnoughFundsException e) {
                    System.out.println(e.getMessage());
                    message.setText(e.getMessage());
                    message.setTextFill(Color.WHITE);
                    Player.getInstance().setCash(Player.getInstance().getCash() + selected.price);
                }
            });
            return buy;
        }

        private void handleBuy() {
            if (Player.getInstance().getCash() < 0) {
                throw new NotEnoughFundsException("Out of money!");
            }
            Player.getInstance().setCash(Player.getInstance().getCash() - selected.price);
            if (Player.getInstance().getCash() >= 0) {
                if (selected.type.equals("weapon")) {
                    Player.getInstance().getInventory().addWeapon((Weapon) getCollidable(selected.name));
                } else {
                    Player.getInstance().getInventory().addPotion((Potion) getCollidable(selected.name));
                }
                Room.cashValue.setText(Player.getInstance().getCash().toString());
                cashValue.setText(Player.getInstance().getCash().toString());
                message.setText("Purchase Confirmed!");
                message.setTextFill(Color.CYAN);
            } else {
                throw new NotEnoughFundsException("Insufficient funds for purchase.");
            }
            System.out.println(selected.price);
            System.out.println(Player.getInstance().getCash());
        }

        private Collidable getCollidable(String name) {
            return switch (name) {
                case "Dagger" -> Controller.createWeapon("dagger");
                case "Axe" -> Controller.createWeapon("axe");
                case "Spear" -> Controller.createWeapon("spear");
                case "Health Potion" -> Controller.createPotion("health");
                case "Attack Potion" -> Controller.createPotion("attack");
                case "Speed Potion" -> Controller.createPotion("speed");
                default -> throw new IllegalArgumentException("Invalid name given.");
            };
        }
    }

    private static class Slot extends StackPane {
        private String name;
        private String path;
        private String type;
        private ImageView item;
        private int price;

        Slot(String name, String type) {
            System.out.println("Name: " + name);
            this.name = name;
            setPath();
            setPrice();
            //System.out.println(path);
            this.type = type;
            int x;
            int y;
            if (this.type.equals("weapon")) {
                x = 120;
                y = 120;
            } else {
                x = 51;
                y = 76;
            }
            initSlot(x, y);
            this.handleMouseActions();
        }

        private void setPrice() {
            this.price = switch (name) {
                case "Axe" -> 200;
                case "Spear" -> 150;
                case "Dagger" -> 100;
                default -> 50;
            };
        }

        private void setPath() {
            System.out.println(name);
            path = switch (name) {
                case "Axe" -> "axe-angle.png";
                case "Spear" -> "spear-angle.png";
                case "Dagger" -> "dagger-angle.png";
                case "Health Potion" -> "potions/health.png";
                case "Attack Potion" -> "potions/attack.png";
                case "Speed Potion" -> "potions/speed.png";
                default -> "dagger-drag.png";
            };
        }

        private void initSlot(int x, int y) {
            this.getStyleClass().add("center");
            Rectangle rect1 = new Rectangle(120, 120);
            rect1.getStyleClass().addAll("slot_rect");
            this.item = new ImageView(
                    new Image("main/design/images/" + path, x, y, true, false)
            );
            this.getChildren().addAll(rect1, item);
        }

        private void handleMouseActions() {
            this.setOnMouseEntered(event -> {
                //change color, opacity, same as inventory
            });
            this.setOnMouseExited(event -> {
                //reset
            });
            this.setOnMouseReleased(e -> {
                if (selected != null) {
                    if (selected == this) {
                        //deselect
                        handleDeselect();
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
        }

        private void handleSelect() {
            checkoutPanel.updateCheckout(new ImageView(getImage()), name);
        }

        private void handleDeselect() {
            selected = null;
            checkoutPanel.createBaseCheckout();
            message.setText("Welcome to the shop!");
            message.setTextFill(Color.WHITE);
        }

        private Image getImage() {
            return switch (name) {
                case "Dagger" -> new Image("/main/design/images/dagger-drag.png", 150, 150, false, false);
                case "Axe" -> new Image("/main/design/images/axe-angle.png", 150, 150, false, false);
                case "Spear" -> new Image("/main/design/images/spear-drag.png", 150, 150, false, false);
                case "Health Potion" -> new Image("/main/design/images/potions/health.png", 51, 76, false, false);
                case "Attack Potion" -> new Image("/main/design/images/potions/attack.png", 51, 76, false, false);
                case "Speed Potion" -> new Image("/main/design/images/potions/speed.png", 51, 76, false, false);
                default -> new Image("/main/design/images/dagger-drag.png");
            };
        }
    }
}
