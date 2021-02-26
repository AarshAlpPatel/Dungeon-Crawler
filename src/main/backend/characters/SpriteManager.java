package main.backend.characters;

import java.util.HashMap;

import main.backend.weapons.Weapon;
import main.backend.weapons.WeaponManager;

public class SpriteManager {
    private static HashMap<Integer, Enemy> enemies = new HashMap<>();
    private static Player player;
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

    public static Player createPlayer(double x, double y, String name, String weaponName,
                                      String imagePath) {
        player = Player.getInstance();
        Weapon weapon = WeaponManager.create(weaponName, x, y, false);
        player.setName(name);
        player.setWeapon(weapon);
        player.setImagePath(imagePath);
        return player;
    }

    public static void destroy(int id) {
        enemies.remove(id);
    }
}
