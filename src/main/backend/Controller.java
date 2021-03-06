package main.backend;

import java.util.*;

import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import main.backend.characters.*;
import main.backend.rooms.RoomManager;
import main.backend.weapons.*;
import main.frontend.GameManager;
import main.frontend.MainScreen;
import main.backend.rooms.*;

public class Controller {
    private static String difficultyLevel = "Easy";
    private static int level = 1;

    public static int getLevel() {
        return level;
    }

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

    public static ArrayList<ImageView> getCurrentRoomImages() {
        return RoomManager.getCurrentRoomImages();
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

    public static void initializeLevel() {
        setPlayerPosition(400, 400);
        RoomManager.createRooms(level);
    }

    public static void destroyImage(ArrayList<ImageView> images) {
        GameManager.destroyImage(images);
    }

    public static void changeRoom(Door direction) {
        switch(direction) {
            case NORTH:
                setPlayerPosition(MainScreen.getLength()/2,
                                  getMaxY()-50);
                break;
            case WEST:
                setPlayerPosition(getMaxX()-50, 
                    (MainScreen.getHeight()+MainScreen.getMinY())/2);
                break;
            case SOUTH:
                setPlayerPosition(MainScreen.getLength()/2,
                                  getMinY()+50);
                break;
            case EAST:
                setPlayerPosition(getMinX()+50,
                    (MainScreen.getHeight()+MainScreen.getMinY())/2);
                break;
        }
        GameManager.changeRoom();
    }

    public static double getMinX() {
        return MainScreen.getMinX() +
               MainScreen.getWallWidth() +
               Player.getInstance().getWidth()/2;
    }

    public static double getMinY() {
        return MainScreen.getMinY() + 
               MainScreen.getWallWidth() + 
               Player.getInstance().getHeight()/2;
    }

    public static double getMaxX() {
        return MainScreen.getLength() -
               MainScreen.getWallWidth() -
               Player.getInstance().getWidth()/2;
    }

    public static double getMaxY() {
        return MainScreen.getHeight() -
               MainScreen.getWallWidth() -
               Player.getInstance().getHeight()/2;
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

    public static boolean[] getConnections() {
        return RoomManager.getConnections();
    }
}
