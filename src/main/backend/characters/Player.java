package main.backend.characters;

import main.backend.weapons.Weapon;

/**
 * the Player class follows the Singleton design pattern
 */
public class Player extends Sprite {
    private static Player playerObj = null;

    private Player() {
        this(0, 0, 0, 0, 0, 0, null, null, null);
    }

    private Player(double x, double y, double attackMultiplier, double speed,
            int health, int regeneration, Weapon weapon, String name, String imagePath) {
        super(x, y, attackMultiplier, speed, health, regeneration, weapon, name, imagePath);
    }

    public static Player getInstance() {
        if(playerObj == null) {
            playerObj = new Player();
        }
        return playerObj;
    }
}
