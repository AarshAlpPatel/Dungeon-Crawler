package main.backend.characters;

import java.util.*;

import javafx.scene.Node;
import main.backend.rooms.BossRoom;
import main.backend.rooms.WeaponRoom;
import main.backend.rooms.RoomManager;

public class EnemyManager {
    private Enemy[] enemies;
    private boolean[] enemiesHit;
    private boolean[] playerHits;
    private int enemyCounter = 0;
    private static double rangeOffset = 40;
    private int difficulty;

    public EnemyManager(int enemyCount, int difficulty, boolean boss) {
        this.enemies = new Enemy[enemyCount];
        this.enemiesHit = new boolean[enemyCount];
        this.playerHits = new boolean[enemyCount];
        this.enemyCounter = 0;
        this.difficulty = difficulty;
        generateEnemies(enemyCount, difficulty, boss);
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
        return Math.random() * 500 + 150;
    }

    //private Enemy randomlyChooseEnemy() { //use eventually
    //    Random rand = new Random();
    //    int n = rand.nextInt(99) + 1;
    //    if (n < 33) {
    //        return new Ghost(getRandomPosition(), getRandomPosition(), this.enemyCounter++);
    //    } else if (n < 66) {
    //        return new Bat(getRandomPosition(), getRandomPosition(), this.enemyCounter++);
    //    } else {
    //        return new Snake(getRandomPosition(), getRandomPosition(), this.enemyCounter++);
    //    }
    //}

    public void generateEnemies(int enemies, int difficulty, boolean boss) {
        //will customize with different level enemies at a later time

        //added for testing purposes to verify that all types show up,
        //eventually replace with randomly generated
        //Enemy[] enemyTypes = { //delete eventually
        //    new Bat(getRandomPosition(), getRandomPosition(), this.enemyCounter++),
        //    new Ghost(getRandomPosition(), getRandomPosition(), this.enemyCounter++),
        //    new Snake(getRandomPosition(), getRandomPosition(), this.enemyCounter++)
        //};
        if (boss) {
            enemies--;

            // if (Math.random() < (0.9/difficulty)*(0.9/difficulty)) {
            //     this.enemies[i] = randomlyChooseEnemy();
            // } else if (Math.random() < 1 / difficulty) {
            //     this.enemies[i] = randomlyChooseEnemy();
            // } else {
            //     this.enemies[i] = randomlyChooseEnemy();
            // }
        }

        for (int i = 0, j = 0; i < enemies; ++i, j++) {
            if (j == 3) { //delete eventually
                j = 0;
            }
            if (j == 0) {
                this.enemies[i] = new Bat(getRandomPosition(), getRandomPosition(),
                        this.enemyCounter++);
            } else if (j == 1) {
                this.enemies[i] = new Ghost(getRandomPosition(), getRandomPosition(),
                        this.enemyCounter++);
            } else if (j == 2) {
                this.enemies[i] = new Snake(getRandomPosition(), getRandomPosition(),
                        this.enemyCounter++);
            }
            // if (Math.random() < (0.9/difficulty)*(0.9/difficulty)) {
            //     this.enemies[i] = randomlyChooseEnemy();
            // } else if (Math.random() < 1 / difficulty) {
            //     this.enemies[i] = randomlyChooseEnemy();
            // } else {
            //     this.enemies[i] = randomlyChooseEnemy();
            // }
        }
        if (boss) {
            this.enemies[enemies] = new Boss(400, 400, this.enemyCounter++);
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
                    if (clear()) {
                        StatTracker.changeScore(100);
                        if (RoomManager.getCurrent() instanceof BossRoom) {
                            StatTracker.changeScore(400);
                        }
                        if (RoomManager.getCurrent() instanceof WeaponRoom) {
                            ((WeaponRoom) RoomManager.getCurrent()).onClear();
                        }
                    }
                }
            }
        }
    }

    public void resetHits() {
        enemiesHit = new boolean[enemies.length];
    }

    public void attackPlayer() {
        for (int i = 0; i < enemies.length; ++i) {
            //System.out.println(i + ": " + enemies[i].getMainWeapon().distance
            // (Player.getInstance()) + ", " + enemies[i].getMainWeapon().isAttacking());
            if (enemies[i].isDead()) {
                continue;
            } else if (enemies[i].getMainWeapon().isAttacking()) {
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
                if (enemies[i].getMainWeapon().inRange(Player.getInstance(), rangeOffset)
                        && !enemies[i].isDead()) {
                    enemies[i].getMainWeapon().startAttack();
                } else {
                    enemies[i].setWeaponDirection();
                }
            }
        }
        //System.out.println();
    }

    public Enemy[] getEnemies() {
        return enemies;
    }

    public int getEnemyCounter() {
        return enemyCounter;
    }

    public int getDifficulty() {
        return this.difficulty;
    }
}
