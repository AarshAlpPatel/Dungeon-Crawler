package main.backend;

import java.util.*;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.ProgressBar;
import main.backend.characters.*;
import main.backend.rooms.RoomManager;
import main.backend.weapons.*;
import main.frontend.GameManager;
import main.frontend.MainScreen;
import main.backend.rooms.*;

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
        Player player = Player.getInstance();
        Weapon weapon = WeaponManager.create(weaponName, x, y, false, 2.5, 10);
        player.setName(name);
        player.setWeapon(weapon);
        player.setPosition(new Point2D(x, y));
        player.setImage(imagePath);
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
        Player.getInstance().getMainWeapon().rotate(mousePosition, RoomManager.getCurrentEnemies());
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

    public static void endGame() {
        Player.getInstance().setMoveNorth(false);
        Player.getInstance().setMoveWest(false);
        Player.getInstance().setMoveSouth(false);
        Player.getInstance().setMoveEast(false);
        GameManager.endGame();
    }
}
