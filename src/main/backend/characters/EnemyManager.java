package main.backend.characters;

import java.util.*;

import javafx.scene.image.ImageView;
import main.backend.Controller;

public class EnemyManager {
    private Enemy[] enemies;
    private int enemyCounter = 0;

    public EnemyManager(int enemies, int difficulty) {
        this.enemies = new Enemy[enemies];
        generateEnemies(enemies, difficulty);
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
        --enemyCounter;
    }

    public boolean clear() {
        return enemyCounter == 0;
    }

    public ArrayList<ImageView> getImages() {
        ArrayList<ImageView> images = new ArrayList<>(enemyCounter * 2);
        for (Enemy enemy : enemies) {
            if (enemy != null) {
                images.addAll(enemy.getImage());
            }
        }
        return images;
    }

    private double getRandomPosition() {
        return Math.random()*500 + 150;
    }

    public void generateEnemies(int enemies, int difficulty) {
        //will customize with different level enemies at a later time
        for(int i = 0; i < enemies; ++i) {
            if (Math.random() < (0.9/difficulty)*(0.9/difficulty)) {
                this.enemies[i] = new Ghost(getRandomPosition(),
                                        getRandomPosition(), i);
            } else if (Math.random() < 1/difficulty) {
                this.enemies[i] = new Ghost(getRandomPosition(),
                                        getRandomPosition(), i);
            } else {
                this.enemies[i] = new Ghost(getRandomPosition(),
                                        getRandomPosition(), i);
            }
        }
    }
}
