package main.backend.characters;

import main.backend.weapons.Weapon;

/**
 * the Player class follows the Singleton design pattern
 */
public class Player extends Sprite {
    private static Player playerObj = null;
    private Weapon backupWeapon = null;
    public enum Orientation {
        NORTH, EAST, SOUTH, WEST;
    }
    private Orientation direction;

    private Player() {
        this(400, 400, 1, 1.0, 100, 5, null, null, null);
    }

    private Player(double x, double y, double attackMultiplier, double speed,
            int health, int regeneration, Weapon weapon, String name, String imagePath) {
        super(x, y, attackMultiplier, speed, health, regeneration, weapon, name, imagePath, 100);
    }

    public void setBackupWeapon(Weapon backupWeapon) {
        this.backupWeapon = backupWeapon;
    }

    public void switchWeapons() {
        if(this.backupWeapon != null) {
            Weapon tmp = this.mainWeapon;
            this.mainWeapon = this.backupWeapon;
            this.backupWeapon = tmp;
        }
    }

    public static Player getInstance() {
        if(playerObj == null) {
            playerObj = new Player();
        }
        return playerObj;
    }

    public void setDirection(Orientation direction) {
        this.direction = direction;
    }

    public void move() {
        switch(direction) {
            case NORTH:
                super.move(0, -1);
                break;
            case EAST:
                super.move(1, 0);
                break;
            case SOUTH:
                super.move(0, 1);
                break;
            case WEST:
                super.move(-1, 0);
                break;
        }
    }
}
