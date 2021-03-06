package main.backend;

import java.util.*;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import main.backend.characters.*;
import main.backend.collidables.Collidable;
import main.backend.potions.Potion;
import main.backend.potions.PotionManager;
import main.backend.rooms.RoomManager;
import main.backend.weapons.*;
import main.frontend.GameManager;
import main.frontend.MainScreen;
import main.backend.rooms.*;
import main.backend.inventory.*;

/**
 * All communication from the frontend to the backend should come through the Controller.
 */
public class Controller {
    private static String difficultyLevel = "Easy";   //chosen difficulty of the game
    private static int level = 1;    //records what level the game is on

    /**
     * Gets the current level of the game.
     * @return current level
     */
    public static int getLevel() {
        return level;
    }

    /**
     * Gets the selected difficulty level of the game.
     * @return difficulty level
     */
    public static String getDifficultyLevel() {
        return difficultyLevel;
    }

    /**
     * Sets difficulty level of the game.
     * @param val difficulty level
     */
    public static void setDifficultyLevel(String val) {
        difficultyLevel = val;
    }
    
    /**
     * Sets the player's attributes.
     * @param x the player's x-position
     * @param y the player's y-position
     * @param name the player's name
     * @param weaponName the player's weapon
     * @param imagePath path to image file
     * @return the Player object
     */
    public static Player createPlayer(double x, double y, String name, String weaponName,
                                      String imagePath) {
        Player.resetPlayer();
        Player player = Player.getInstance();
        Weapon weapon = WeaponManager.create(weaponName, x, y, false, 2.5, 10, -1);
        weapon.setAttackInterval(weapon.getROF());
        player.setName(name);
        player.setWeapon(weapon);
        player.setPosition(new Point2D(x, y));
        player.setImage(imagePath);
        if (Player.getInstance().getInventory().isEmpty()) {
            player.getInventory().addWeapon(weapon);
        }
        return player;
    }

    /**
     * Gets all sprites and structures in the current room 
     *     for the GameManager to display.
     * @return an arraylist of all the images of the sprites
     */
    public static ArrayList<Node> getCurrentRoomImages() {
        ArrayList<Node> images = new ArrayList<>();
        images.addAll(Player.getInstance().getImage());
        images.addAll(RoomManager.getCurrentRoomImages());
        return images;
    }

    public static ArrayList<Node> getCurrentRoomWalls() {
        return RoomManager.getCurrentRoomWalls();
    }

    /**
     * Sets the player's (x, y) position.
     * @param x horizontal position
     * @param y vertical position
     */
    public static void setPlayerPosition(double x, double y) {
        Player.getInstance().setPosition(new Point2D(x, y));
    }

    public static void setDirection(String key, boolean b) {
        if (key.equals("W")) {
            Player.getInstance().setMoveNorth(b);
        } else if (key.equals("A")) {
            Player.getInstance().setMoveWest(b);
        } else if (key.equals("S")) {
            Player.getInstance().setMoveSouth(b);
        } else if (key.equals("D")) {
            Player.getInstance().setMoveEast(b);
        }
    }

    public static void startAttack() {
        Player.getInstance().startAttack();
    }

    public static void run(Point2D mousePosition) {
        Player.getInstance().move();
        int res = Player.getInstance().getMainWeapon().rotate(mousePosition);
        if (res == 3) {
            RoomManager.getCurrentEnemies().resetHits();
        } else if (res > 0) {
            RoomManager.getCurrentEnemies().checkHits();
        }
        RoomManager.getCurrentEnemies().attackPlayer();
    }

    public static void initializeLevel() {
        setPlayerPosition(400, 400);
        RoomManager.createRooms(level);
    }

    public static void destroyImage(ArrayList<Node> images) {
        GameManager.destroyImage(images);
    }

    public static void addImage(ArrayList<Node> images) {
        GameManager.addImage(images);
    }

    public static void changeRoom(Door direction) {
        switch (direction) {
        case NORTH:
            setPlayerPosition(MainScreen.getLength() / 2,
                    getMaxPlayerY() - 50);
            break;
        case WEST:
            setPlayerPosition(getMaxPlayerX() - 50,
                    (MainScreen.getHeight() + MainScreen.getMinY()) / 2);
            break;
        case SOUTH:
            setPlayerPosition(MainScreen.getLength() / 2,
                    getMinPlayerY() + 50);
            break;
        case EAST:
            setPlayerPosition(getMinPlayerX() + 50,
                    (MainScreen.getHeight() + MainScreen.getMinY()) / 2);
            break;
        default:
            break;
        }
        GameManager.changeRoom();
    }

    public static double getMinX() {
        return MainScreen.getMinX();
    }

    public static double getMinY() {
        return MainScreen.getMinY();
    }

    public static double getMinPlayerX() {
        return MainScreen.getMinX()
                + MainScreen.getWallWidth()
                + Player.getInstance().getWidth() / 2;
    }

    public static double getMinPlayerY() {
        return MainScreen.getMinY()
                + MainScreen.getWallWidth()
                + Player.getInstance().getHeight() / 2;
    }

    public static double getMaxPlayerX() {
        return MainScreen.getLength()
                - MainScreen.getWallWidth()
                - Player.getInstance().getWidth() / 2;
    }

    public static double getMaxPlayerY() {
        return MainScreen.getHeight()
                - MainScreen.getWallWidth()
                - Player.getInstance().getHeight() / 2;
    }

    public static double getMidY() {
        return MainScreen.getMidY();
    }

    public static double getMidX() {
        return MainScreen.getMidX();
    }

    public static double getLength() {
        return MainScreen.getLength();
    }

    public static double getHeight() {
        return MainScreen.getHeight();
    }

    public static double getDoorWidth() {
        return MainScreen.getDoorWidth();
    }

    public static double getWallWidth() {
        return MainScreen.getWallWidth();
    }

    public static boolean[] getConnections() {
        return RoomManager.getConnections();
    }

    public static void winGame() {
        Player.getInstance().setMoveNorth(false);
        Player.getInstance().setMoveWest(false);
        Player.getInstance().setMoveSouth(false);
        Player.getInstance().setMoveEast(false);
        GameManager.winGame();
    }

    public static void loseGame() {
        Player.getInstance().setMoveNorth(false);
        Player.getInstance().setMoveWest(false);
        Player.getInstance().setMoveSouth(false);
        Player.getInstance().setMoveEast(false);
        GameManager.loseGame();
    }

    public static void pickUpCollectable() {
        Collidable c = RoomManager.getCurrent().pickUpCollectable(Player.getInstance());
        if (c == null) {
            System.out.println("Nothing to pick up");
        } else if (!Player.getInstance().addToInventory(c)) { //inventory full
            RoomManager.getCurrent().addCollectable(c, Player.getInstance().getPosition());
        }
    }

    public static void dropCollectable(int index, String type) {
        if (type.equals("weapon")) {
            Player.getInstance().getInventory().getWeapon(index).playerDropWeapon();
            Player.getInstance().getInventory().dropWeapon(index);
        } else {
            //this places it on the main screen again
            Player.getInstance().getInventory().getPotion(index).dropPotion();
            //this deletes it from inventory
            Player.getInstance().getInventory().dropPotion(index);
        }
    }

    public static Weapon createWeapon(String name) {
        return WeaponManager.create(name, Player.getInstance().getPosition().getX(),
                Player.getInstance().getPosition().getY(), false,
                2.5, 10, -1);
    }

    public static Potion createPotion(String name) {
        return PotionManager.create(name, "common", Player.getInstance().getPosition().getX(),
                Player.getInstance().getPosition().getY(), Player.POTION_DURATION);
    }

    public static void startTimer() {
        StatTracker.startTimer();
    }

    public static void stopTimer() {
        StatTracker.finishTimer();
    }

    public static Integer[] getTimeTaken() {
        return StatTracker.getElapsedTime();
    }

    public static String getDeathReason() {
        return StatTracker.getDeathReason();
    }

    /**
     * Returns the player's stats from the game as a list
     * (damageDealt, damageTaken, monstersKilled, score)
     *
     * @return List of stats
     */
    public static List<Double> getPlayerStats() {
        List<Double> list = new ArrayList<>(5);
        list.add(StatTracker.getDamageDealt());
        list.add(StatTracker.getDamageTaken());
        list.add(StatTracker.getMonstersKilled());
        list.add(StatTracker.getScore());
        return list;
    }


}
