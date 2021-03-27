package main.backend.rooms;

import java.util.ArrayList;

import javafx.scene.Node;

public class WeaponRoom extends Room {
    public WeaponRoom() {
        super("hard");
    }

    @Override
    public ArrayList<Node> getImages() {
        return new ArrayList<>();
    }

}
