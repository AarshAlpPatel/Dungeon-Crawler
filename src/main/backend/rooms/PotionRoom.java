package main.backend.rooms;

import java.util.ArrayList;

import javafx.scene.Node;

public class PotionRoom extends Room {
    public PotionRoom() {
        super("hard");
    }

    @Override
    public ArrayList<Node> getImages() {
        return new ArrayList<>();
    }

}