package main.backend.characters;

import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;
import main.backend.Controller;
import main.backend.exceptions.EdgeOfScreen;
import main.backend.exceptions.WallCollision;
import main.backend.rooms.RoomManager;
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
    private Integer cash;
    private Text healthTextBox;

    private Player() {
        this(400, 400, 1, 4.0, 100, 5, null, null, "char1.gif");
    }

    private Player(double x, double y, double attackMultiplier, double speed,
            int health, int regeneration, Weapon weapon, String name, String imagePath) {
        super(x, y, attackMultiplier, speed, health, regeneration, weapon, name, imagePath, 100);
    }

    public void setCash(int cash) {
        this.cash = cash;
    }

    public Integer getCash() {
        return cash;
    }

    public void setBackupWeapon(Weapon backupWeapon) {
        this.backupWeapon = backupWeapon;
    }

    public Weapon getBackupWeapon() {
        return backupWeapon;
    }

    public void setMoveNorth(boolean b) {
        moveNorth = b;
    }

    public void setMoveWest(boolean b) {
        moveWest = b;
    }

    public void setMoveSouth(boolean b) {
        moveSouth = b;
    }

    public void setMoveEast(boolean b) {
        moveEast = b;
    }

    public void setHealthBox(ProgressBar healthBar, Text healthVal) {
        this.healthBar = healthBar;
        this.healthTextBox = healthVal;
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
        } catch (EdgeOfScreen e) {
            System.out.println(e);
            RoomManager.checkEdge(this.position.getX() + dx, this.position.getY() + dy);
        } catch (WallCollision e) {
            System.out.println(e);
        }
    }

    public void startAttack() {
        mainWeapon.startAttack();
    }

    public void destroy() {
        this.mainWeapon.destroyWeapon();
        Controller.endGame();
    }
}
