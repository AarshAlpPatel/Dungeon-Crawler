package main.backend.rooms;

import java.util.*;

public class RoomManager {
    private static int MAX_ROOMS = 16;
    private static Room current;
    private static ArrayList<Room> rooms;

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
        types.add("shop");
        types.add("potion");
        types.add("weapon");
        types.add("weapon");
        types.add("empty");
        types.add("empty");
        for(int i = 6; i < types.size(); ++i) {
            types.add(returnRandomType(level));
        }
        Collections.shuffle(types);

        Room tmp = current;
        for(int i = 0; i < 6; ++i) {
            Room newRoom = createRoom(types.get(i));
            tmp = setConnection(tmp, newRoom);
        }
        for(int i = 6; i < 9; ++i) {
            Room newRoom = createRoom(types.get(i));
            setConnection(current, newRoom);
        }
        for(int i = 9; i < types.size(); ++i) {
            boolean added = false;
            while(!added) {
                Room newRoom = createRoom(types.get(i));
                int randomRoom = (int)(Math.random() * rooms.size());
                if(setConnection(rooms.get(randomRoom), newRoom) != null) {
                    break;
                }
            }
        }
    }

    public static void changeRoom() {
        //implement logic for changing rooms
    }

    public static void checkDoor(int x, int y) {
        //implement logic to check for a door on a wall
    }
}
