package main.backend.rooms;

import java.util.ArrayList;

import javafx.scene.image.ImageView;
import main.backend.characters.EnemyManager;
import main.backend.characters.Player;

public class EnemyRoom extends Room {
    private EnemyManager enemies;

    public EnemyRoom(String difficulty) {
        if(difficulty.equals("easy")) {
            this.enemies = new EnemyManager(0);
        } else if(difficulty.equals("medium")) {
            this.enemies = new EnemyManager(0);
        } else if (difficulty.equals("hard")) {
            this.enemies = new EnemyManager(0);
        } else {
            throw new IllegalArgumentException("Invalid difficulty");
        }
    }

    @Override
    public ArrayList<ImageView> getImages() {
        ArrayList<ImageView> images = new ArrayList<>();
        images.addAll(Player.getInstance().getImage());
        images.addAll(enemies.getImages());
        return images;
    }

    @Override
    public void enter() {
        //should generate enemies upon entering. this is temporary
        setClearTrue();
    }
}
