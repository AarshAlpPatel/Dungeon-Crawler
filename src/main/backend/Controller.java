package main.backend;

import java.util.*;

import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import main.backend.characters.*;
import main.backend.weapons.*;

public class Controller {
    private static String difficultyLevel = "Easy";

    public static String getDifficultyLevel() {
        return difficultyLevel;
    }

    public static void setDifficultyLevel(String val) {
        difficultyLevel = val;
    }
    
    public static Player createPlayer(double x, double y, String name, String weaponName,
                                      String imagePath) {
        Player player = Player.getInstance();
        Weapon weapon = WeaponManager.create(weaponName, x, y, false);
        player.setName(name);
        player.setWeapon(weapon);
        player.setPosition(new Point2D(x, y));
        player.setImage(imagePath, 100);
        return player;
    }

    public static ArrayList<ImageView> getPlayerImage() {
        ArrayList<ImageView> images = new ArrayList<>();
        images.add(Player.getInstance().getImage());
        images.add(Player.getInstance().getMainWeapon().getImage());
        return images;
    }

    public static ArrayList<ImageView> generateEnemies(HashMap<String,
                                                        Integer> enemies, int maxX, int maxY) {
        ArrayList<ImageView> images = new ArrayList<>();
        for (Map.Entry<String, Integer> enemy : enemies.entrySet()) {
            for (int i = 0; i < enemy.getValue(); i++) {
                double x = Math.random() * maxX;
                double y = Math.random() * maxY;
                Enemy e = EnemyManager.create(x, y, enemy.getKey());
                images.add(e.getImage());
                images.add(e.getMainWeapon().getImage());
            }
        }

        return images;
    }

    public static void setPlayerPosition(double x, double y) {
        Player.getInstance().setPosition(new Point2D(x, y));
    }

    public static void setDirection(String key, boolean b) {
        Player.getInstance().setDirection(key, b);
    }

    public static void run(Point2D mousePosition) {
        Player.getInstance().move();
        Player.getInstance().getMainWeapon().follow(mousePosition);
    }
}
