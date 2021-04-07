package main.frontend;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

import java.util.Stack;

public class ShopScreen {
    private static Scene shopScreen;
    private static VBox screen;

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
//        Rectangle potions = new Rectangle(200, 500);
//        potions.getStyleClass().add("rect");
//        Rectangle weapons = new Rectangle(200, 500);
//        weapons.getStyleClass().add("rect");
//        Rectangle checkout = new Rectangle(200, 500);
//        checkout.getStyleClass().add("rect");
        Panel potions = new Panel("potion");
        Panel weapons = new Panel("weapon");
        Panel checkout = new Panel("checkout");
        panels.getChildren().addAll(potions, weapons, checkout);
        return panels;
    }

    public static Scene getScene() {
        screen = new VBox();
        HBox emptyTop = new HBox();
        StackPane shop = createBackground();
        HBox panels = createPanels();
        shop.getChildren().add(panels);
        HBox emptyBottom = new HBox();
        screen.getChildren().addAll(emptyTop, shop, emptyBottom);
        screen.getStyleClass().addAll("center", "screen");

        shopScreen = new Scene(screen, MainScreen.getLength(), MainScreen.getHeight());
        shopScreen.getStylesheets().add("main/design/Shop.css");
        return shopScreen;
    }

    private static class Panel extends StackPane {

        //potions, weapons, or checkout
        String type;

        public Panel(String type) {
            this.type = type;
            initPanel();
        }

        private void initPanel() {
            Rectangle rect = new Rectangle(200, 500);
            this.getChildren().add(rect);
            this.getStyleClass().add("center");
            rect.getStyleClass().add("rect");
            switch (type) {
                case "weapon" :
                    this.getChildren().add(
                            createSlots("dagger-angle.png", "axe-angle.png", "spear-angle.png"));
                    break;
                case "potion" :
                    this.getChildren().add(
                            createSlots("potions/health.png", "potions/attack.png", "potions/speed.png"));
                    break;
                case "checkout" :
                    this.getChildren().add(createCheckout());
                    break;
                default :
                    throw new RuntimeException("Unrecognized panel type entered.");
            }
        }

        private VBox createSlots(String path1, String path2, String path3) {
            VBox slots = new VBox(40);
            slots.getStyleClass().add("center");

            StackPane slot1 = createSlot(path1);
            StackPane slot2 = createSlot(path2);
            StackPane slot3 = createSlot(path3);

            slots.getChildren().addAll(slot1, slot2, slot3);
            return slots;
        }

        private StackPane createSlot(String path) {
            StackPane slot1 = new StackPane();
            slot1.getStyleClass().add("center");
            Rectangle rect1 = new Rectangle(100, 100);
            rect1.getStyleClass().addAll("slot_rect");
            ImageView image1 = new ImageView(
                    new Image("/main/design/images/" + path, 100, 100, true, false)
            );
            slot1.getChildren().addAll(rect1, image1);
            return slot1;
        }

        private VBox createCheckout() {
            VBox checkout = new VBox();

            return checkout;
        }
    }
}
