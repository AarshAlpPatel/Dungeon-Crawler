package main.backend.rooms;

import java.util.ArrayList;
import javafx.scene.image.ImageView;
import main.backend.Controller;

public class EndRoom extends Room {
    @Override
    public ArrayList<ImageView> getImages() {
        return new ArrayList<>();
    }

    @Override
    public void enter() {
        setClearTrue();
        Controller.endGame();
    }
}
