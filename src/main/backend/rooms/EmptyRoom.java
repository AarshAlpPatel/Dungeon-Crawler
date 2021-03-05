package main.backend.rooms;

import javafx.scene.image.ImageView;
import main.backend.characters.Player;

import java.util.ArrayList;

public class EmptyRoom extends Room {
    @Override
    public ArrayList<ImageView> getImages() {
        return Player.getInstance().getImage();
    }

    @Override
    public void enter() {
        setClearTrue();
    }
}
