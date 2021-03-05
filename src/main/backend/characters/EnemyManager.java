package main.backend.characters;

import java.util.*;

import javafx.scene.image.ImageView;
import main.backend.Controller;

public class EnemyManager {
    private Enemy[] enemies;
    private int enemyCounter = 0;

    public EnemyManager(int enemies) {
        this.enemies = new Enemy[enemies];
    }

    public Enemy create(double x, double y, String name) {
        Enemy newEnemy = null;
        if (name.equals("ghost")) {
            newEnemy = new Ghost(x, y, enemyCounter);
        } else {
            throw new IllegalArgumentException("Enemy name not recognized");
        }

        enemies[enemyCounter] = newEnemy;
        ++enemyCounter;
        return newEnemy;
    }

    public void destroy(int id) {
        Controller.destroyImage(enemies[id].getImage());
        enemies[id].destroy();
        enemies[id] = null;
        --enemyCounter;
    }

    public boolean clear() {
        return enemyCounter == 0;
    }

    public ArrayList<ImageView> getImages() {
        ArrayList<ImageView> images = new ArrayList<>(enemyCounter*2);
        for(Enemy enemy : enemies) {
            if (enemy != null) {
                images.addAll(enemy.getImage());
            }
        }
        return images;
    }
}
