package main.backend.rooms;

import java.util.ArrayList;

import javafx.scene.image.ImageView;
import main.backend.characters.Player;

public class WeaponRoom extends Room {
    @Override
    public ArrayList<ImageView> getImages() {
        return Player.getInstance().getImage();
    }

    @Override
    public void enter() {
        setClearTrue();
    }
}
