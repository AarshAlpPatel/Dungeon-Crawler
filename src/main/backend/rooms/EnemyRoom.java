package main.backend.rooms;

import java.util.ArrayList;

import javafx.scene.image.ImageView;
import main.backend.characters.EnemyManager;

public class EnemyRoom extends Room {
    private EnemyManager enemies;

    public EnemyRoom(String difficulty) {
        if (difficulty.equals("easy")) {
            this.enemies = new EnemyManager(4, 1);
        } else if (difficulty.equals("medium")) {
            this.enemies = new EnemyManager(5, 2);
        } else if (difficulty.equals("hard")) {
            this.enemies = new EnemyManager(6, 3);
        } else if (difficulty.equals("boss")) {
            this.enemies = new EnemyManager(8, 4);
        } else {
            throw new IllegalArgumentException("Invalid difficulty");
        }
    }

    @Override
    public ArrayList<ImageView> getImages() {
        ArrayList<ImageView> images = new ArrayList<>();
        images.addAll(enemies.getImages());
        return images;
    }

    @Override
    public void enter() {
        //should generate enemies upon entering. this is temporary
        setClearTrue();
    }
}
