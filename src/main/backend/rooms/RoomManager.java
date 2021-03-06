package main.backend.rooms;

import java.util.*;

import javafx.scene.image.ImageView;
import main.backend.Controller;

public class RoomManager {
    private static int MAX_ROOMS = 16;
    private static double DOOR_WIDTH = 100;
    private static Room current;
    private static ArrayList<Room> rooms = new ArrayList<>();

    public static double getDoorWidth() {
        return DOOR_WIDTH;
    }

    private static String returnRandomType(int level) {
        if(Math.random() < 0.8/level) {
            return "easy";
        } else if(Math.random() < 1-(level/10)) {
            return "medium";
        } else {
            return "hard";
        }
    }

    private static Room createRoom(String type) {
        if (type.equals("empty")) {
            return new EmptyRoom();
        } else if (type.equals("shop")) {
            return new ShopRoom();
        } else if (type.equals("potion")) {
            return new PotionRoom();
        } else if (type.equals("weapon")) {
            return new WeaponRoom();
        } else if (type.equals("boss")) {
            return new BossRoom(); 
        } else {
            return new EnemyRoom(type);
        }
    }

    private static Room setConnection(Room from, Room newRoom) {
        if(!from.setRandomConnection(newRoom)) {
            return null;
        } else {
            rooms.add(newRoom);
            return newRoom;
        }
    }

    public static void createRooms(int level) {
        ArrayList<String> types = new ArrayList<>(MAX_ROOMS-2); //not including start room and boss room
        current = createRoom("empty");
        current.enter();
        types.add("shop");
        types.add("potion");
        types.add("weapon");
        types.add("weapon");
        types.add("empty");
        types.add("empty");
        for(int i = 6; i < MAX_ROOMS-2; ++i) {
            types.add(returnRandomType(level));
        }
        Collections.shuffle(types);
        Room tmp = current;
        for(int i = 0; i < 6; ++i) {
            Room newRoom = createRoom(types.get(i));
            tmp = setConnection(tmp, newRoom);
        }
        Room newRoom = createRoom("boss");
        setConnection(tmp, newRoom);

        for(int i = 6; i < 9; ++i) {
            newRoom = createRoom(types.get(i));
            setConnection(current, newRoom);
        }

        for(int i = 9; i < types.size(); ++i) {
            boolean added = false;
            while(!added) {
                newRoom = createRoom(types.get(i));
                int randomRoom = (int)(Math.random() * rooms.size());
                if(setConnection(rooms.get(randomRoom), newRoom) != null) {
                    break;
                }
            }
        }
    }

    public static boolean validMove(double x, double y) {
        return current.validMove(x, y);
    }

    public static ArrayList<ImageView> getCurrentRoomImages() {
        return current.getImages();
    }

    public static void checkEdge(double x, double y) {
        Door direction = current.checkEdge(x, y);
        if (direction != null && current.getClear()) {
            Room next = current.getNextRoom(direction);
            current = next;
            Controller.changeRoom(direction);
            current.enter();
        }
    }

    public static boolean[] getConnections() {
        return current.getConnections();
    }
}
