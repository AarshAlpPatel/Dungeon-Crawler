package main.backend.rooms;

import java.util.*;

import javafx.scene.image.ImageView;
import main.backend.Controller;
import main.backend.characters.Sprite;

public class RoomManager {
    private static final int MAX_ROOMS = 16;
    private static final double DOOR_WIDTH = 250;
    private static Room current;
    private static ArrayList<Room> rooms = new ArrayList<>();

    public static double getDoorWidth() {
        return DOOR_WIDTH;
    }

    private static String returnRandomType(int level) {
        if (Math.random() < 0.8 / level) {
            return "easy";
        } else if (Math.random() < 1 - (level / 10)) {
            return "medium";
        } else {
            return "hard";
        }
    }

    private static Room createRoom(String type) {
        if (type.equals("empty")) {
            return new EmptyRoom();
        } else if (type.equals("potion")) {
            return new PotionRoom();
        } else if (type.equals("weapon")) {
            return new WeaponRoom();
        } else if (type.equals("boss")) {
            return new BossRoom(); 
        } else if (type.equals("end")) {
            return new EndRoom();
        } else {
            return new EnemyRoom(type);
        }
    }

    private static Room setConnection(Room from, Room newRoom) {
        if (!from.setRandomConnection(newRoom)) {
            return null;
        } else {
            rooms.add(newRoom);
            return newRoom;
        }
    }

    public static void createRooms(int level) {
        ArrayList<String> types = new ArrayList<>(MAX_ROOMS);
        current = createRoom("empty");
        rooms.add(current);
        types.add("potion");
        types.add("weapon");
        types.add("weapon");
        types.add("empty");
        types.add("empty");
        for (int i = 6; i < MAX_ROOMS - 2; ++i) {
            types.add(returnRandomType(level));
        }
        Collections.shuffle(types);
        Room tmp = current;
        for (int i = 0; i < 6; ++i) {
            Room newRoom = createRoom(types.get(i));
            tmp = setConnection(tmp, newRoom);
        }
        Room lastRoom = tmp;

        for (int i = 6; i < 9; ++i) {
            Room newRoom = createRoom(types.get(i));
            setConnection(current, newRoom);
        }

        for (int i = 9; i < types.size(); ++i) {
            boolean added = false;
            while (!added) {
                Room newRoom = createRoom(types.get(i));
                int randomRoom = (int) (Math.random() * rooms.size());
                if (setConnection(rooms.get(randomRoom), newRoom) != null) {
                    break;
                }
            }
        }

        Room bossRoom = createRoom("boss");
        setConnection(lastRoom, bossRoom);
        Room endRoom = createRoom("end");
        setConnection(bossRoom, endRoom);
        
        for (Room r : rooms) {
            r.setWalls();
        }
        current.enter();
    }

    public static boolean validMove(double x, double y, Sprite s) {
        return current.validMove(x, y, s);
    }

    public static ArrayList<ImageView> getCurrentRoomImages() {
        return current.getImages();
    }

    public static ArrayList<ImageView> getCurrentRoomWalls() {
        return current.getWalls();
    }

    public static void checkEdge(double x, double y) {
        Door direction = current.checkEdge(x, y);
        if (direction != null && current.getLockStatus()) {
            Room next = current.getNextRoom(direction);
            current = next;
            Controller.changeRoom(direction);
            current.enter();
            System.out.println(current.getClass());
        }
    }

    public static boolean[] getConnections() {
        return current.getConnections();
    }
}
