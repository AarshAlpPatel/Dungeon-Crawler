package main.backend.characters;

import java.util.*;

import javafx.scene.Node;
import main.backend.rooms.RoomManager;
import main.backend.weapons.WeaponManager;

public class EnemyManager {
    private Enemy[] enemies;
    private boolean[] enemiesHit;
    private boolean[] playerHits;
    private int enemyCounter = 0;
    private static double rangeOffset = 40;
    WeaponManager weaponManager;

    public EnemyManager(int enemyCount, int difficulty) {
        this.enemies = new Enemy[enemyCount];
        this.enemiesHit = new boolean[enemyCount];
        this.playerHits = new boolean[enemyCount];
        this.enemyCounter = 0;
        weaponManager = new WeaponManager(enemyCount);
        generateEnemies(enemyCount, difficulty);
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

    public boolean clear() {
        return enemyCounter == 0;
    }

    public ArrayList<Node> getImages() {
        ArrayList<Node> images = new ArrayList<>(enemyCounter * 2);
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
                                        getRandomPosition(), this.enemyCounter++);
            } else if (Math.random() < 1/difficulty) {
                this.enemies[i] = new Ghost(getRandomPosition(),
                                        getRandomPosition(), this.enemyCounter++);
            } else {
                this.enemies[i] = new Ghost(getRandomPosition(),
                                        getRandomPosition(), this.enemyCounter++);
            }
            weaponManager.addWeapon(this.enemies[i].getMainWeapon());
        }
    }

    public void checkHits() {
        for (int i = 0; i < enemies.length; ++i) {
            if (!enemies[i].isDead() && !enemiesHit[i]
                && Player.getInstance().getMainWeapon().collidesWith(enemies[i])) {
                Player.getInstance().hit(enemies[i]);
                enemiesHit[i] = true;
                if (enemies[i].isDead()) {
                    enemyCounter--;
                }
            }
        }
        if (clear()) {
            RoomManager.setCurrentRoomStatusTrue();
        }
    }

    public void resetHits() {
        enemiesHit = new boolean[enemies.length];
    }

    public void attackPlayer() {
        for (int i = 0; i < enemies.length; ++i) {
            System.out.println(i + ": " + enemies[i].getMainWeapon().distance(Player.getInstance()) + ", " + enemies[i].getMainWeapon().isAttacking());
            if (enemies[i].getMainWeapon().isAttacking()) {
                int res = enemies[i].getMainWeapon().animate();
                if (enemies[i].getMainWeapon().collidesWith(Player.getInstance())
                    && !playerHits[i]) {
                    enemies[i].hit(Player.getInstance());
                    playerHits[i] = true;
                }
                if (res == 3) {
                    playerHits[i] = false;
                } else if (res == 1) {
                    enemies[i].setWeaponDirection();
                }
            } else {
                if (enemies[i].getMainWeapon().inRange(Player.getInstance(), rangeOffset)) {
                    enemies[i].getMainWeapon().startAttack();
                } else {
                    enemies[i].setWeaponDirection();
                }
            }
        }
        System.out.println();
    }

    public Enemy[] getEnemies() {
        return enemies;
    }

    public void setEnemyCounter(int enemyCounter) {
        this.enemyCounter = enemyCounter;
    }
}
