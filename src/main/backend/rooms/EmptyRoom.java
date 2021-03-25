package main.backend.rooms;

import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class EmptyRoom extends Room {
    @Override
    public ArrayList<ImageView> getImages() {
        return new ArrayList<>();
    }

    @Override
    public void enter() {
        setStatusTrue();
    }


}
