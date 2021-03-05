package main.backend.characters;

import main.backend.exceptions.EdgeOfScreen;
import main.backend.weapons.Weapon;

/**
 * the Player class follows the Singleton design pattern
 */
public class Player extends Sprite {
    private static Player playerObj = null;
    private Weapon backupWeapon = null;
    private boolean moveNorth = false;
    private boolean moveWest = false;
    private boolean moveSouth = false;
    private boolean moveEast = false;

    private Player() {
        this(400, 400, 1, 1.0, 100, 5, null, null, "/main/design/images/char1.gif");
    }

    private Player(double x, double y, double attackMultiplier, double speed,
            int health, int regeneration, Weapon weapon, String name, String imagePath) {
        super(x, y, attackMultiplier, speed, health, regeneration, weapon, name, imagePath, 100);
    }

    public void setBackupWeapon(Weapon backupWeapon) {
        this.backupWeapon = backupWeapon;
    }

    public void switchWeapons() {
        if (this.backupWeapon != null) {
            Weapon tmp = this.mainWeapon;
            this.mainWeapon = this.backupWeapon;
            this.backupWeapon = tmp;
        }
    }

    public static Player getInstance() {
        if (playerObj == null) {
            playerObj = new Player();
        }
        return playerObj;
    }

    public void setDirection(String key, boolean b) {
        if (key.equals("W")) {
            moveNorth = b;
        } else if (key.equals("A")) {
            moveWest = b;
        } else if (key.equals("S")) {
            moveSouth = b;
        } else if (key.equals("D")) {
            moveEast = b;
        }
    }

    public void move() {
        double dx = 0;
        double dy = 0;
        if (moveNorth) {
            --dy;
        }
        if (moveSouth) {
            ++dy;
        }
        if (moveWest) {
            --dx;
        }
        if (moveEast) {
            ++dx;
        }
        try {
            super.move(dx, dy);
        } catch(EdgeOfScreen e) {
            //check if door here
        }
    }
}
