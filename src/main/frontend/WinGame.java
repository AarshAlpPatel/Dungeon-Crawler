package main.frontend;

import javafx.scene.Scene;
import javafx.scene.image.Image;

public class WinGame extends EndGame {

    public static Scene getScene() {
        return getEndScene(
                "You Won!",
                "Congratulations! You defeated the boss and made it out alive!",
                new Image("main/design/images/endScreen/dancing.gif", 300, 300, true, false),
                "win_screen");
    }

}