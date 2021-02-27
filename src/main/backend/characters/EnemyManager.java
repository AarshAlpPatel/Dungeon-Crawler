package main.backend.characters;

import java.util.HashMap;

import main.frontend.GameManager;

public class EnemyManager {
    private static HashMap<Integer, Enemy> enemies = new HashMap<>();
    private static int enemyCounter = 1;

    public static Enemy create(double x, double y, String name) {
        Enemy newEnemy = null;
        if(name.equals("ghost")) {
            newEnemy = new Ghost(x, y, enemyCounter);
        } else {
            throw new IllegalArgumentException("Enemy name not recognized");
        }

        enemies.put(enemyCounter, newEnemy);
        ++enemyCounter;
        return newEnemy;
    }

    public static void destroy(int id) {
        GameManager.destroyImage(enemies.get(id).getImage());
        enemies.remove(id);
    }
}
