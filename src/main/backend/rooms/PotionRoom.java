package main.backend.rooms;

import java.util.ArrayList;

import javafx.scene.image.ImageView;

public class PotionRoom extends EnemyRoom {
    public PotionRoom() {
        super("hard");
    }

    @Override
    public ArrayList<ImageView> getImages() {
        return new ArrayList<>();
    }

}