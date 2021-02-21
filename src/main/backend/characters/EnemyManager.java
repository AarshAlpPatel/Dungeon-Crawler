package main.backend.characters;

import java.util.HashMap;

public class EnemyManager {
    private static HashMap<Integer, Sprite> enemies = new HashMap<>();
    private static int enemiesCounter = 0;

    public static void destroy(int id) {
        enemies.remove(id);
    }
}
