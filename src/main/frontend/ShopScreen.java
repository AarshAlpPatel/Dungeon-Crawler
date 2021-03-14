package main.frontend;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import org.assertj.core.error.ShouldContainNull;

public class ShopScreen {
    private static Scene shopScene;
    private static Pane screen;

    public static Scene getScene() {
        screen = new Pane();

        shopScene = new Scene(screen, MainScreen.getLength(), MainScreen.getHeight());
        return shopScene;
    }
}
