package main.backend.rooms;

import java.util.ArrayList;

import javafx.scene.image.ImageView;

public class WeaponRoom extends EnemyRoom {
    public WeaponRoom() {
        super("hard");
    }

    @Override
    public ArrayList<ImageView> getImages() {
        return new ArrayList<>();
    }

}
