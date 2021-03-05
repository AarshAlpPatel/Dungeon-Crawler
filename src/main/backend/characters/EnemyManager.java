package main.backend.characters;

import java.util.HashMap;

import main.frontend.GameManager;

public class EnemyManager {
    private HashMap<Integer, Enemy> enemies = new HashMap<>();
    private int enemyCounter = 1;

    public Enemy create(double x, double y, String name) {
        Enemy newEnemy = null;
        if (name.equals("ghost")) {
            newEnemy = new Ghost(x, y, enemyCounter);
        } else {
            throw new IllegalArgumentException("Enemy name not recognized");
        }

        enemies.put(enemyCounter, newEnemy);
        ++enemyCounter;
        return newEnemy;
    }

    public void destroy(int id) {
        GameManager.destroyImage(enemies.get(id).getImage());
        enemies.get(id).destroy();
        enemies.remove(id);
    }

    public boolean clear() {
        return enemies.size() == 0;
    }
}
