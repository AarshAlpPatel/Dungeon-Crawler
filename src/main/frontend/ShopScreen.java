package main.frontend;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class ShopScreen {
    private static Scene shopScene;
    private static Pane screen;

    public static Scene getScene() {
        screen = new Pane();

        shopScene = new Scene(screen, MainScreen.getLength(), MainScreen.getHeight());
        return shopScene;
    }
}
