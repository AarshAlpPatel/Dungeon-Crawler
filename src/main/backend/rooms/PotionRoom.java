package main.backend.rooms;

import java.util.ArrayList;

import javafx.scene.image.ImageView;

public class PotionRoom extends Room {
    @Override
    public ArrayList<ImageView> getImages() {
        return new ArrayList<>();
    }

    @Override
    public void enter() {
        setClearTrue();
    }
}