package main.frontend;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import main.backend.Controller;
import main.backend.characters.StatTracker;

public class LoseGame extends EndGame {

    public static Scene getScene() {
        return getEndScene(
                "Game Over!",
                Controller.getDeathReason(),
                new Image("/main/design/images/endScreen/gSkull.gif", 300, 300, true, false),
                "lose_screen");
    }

}
