package main.backend.rooms;

import java.util.ArrayList;

public class RoomManager {
    private static Room current;
    private static ArrayList<Room> rooms;

    public static void createRooms(int level) {
        current = createRoom(-1);
        current.set
        for(int i = 0; i < 4; ++i) {
            current.setRandomConnection(createRoom(level));
        }
        for(int i = 0; i < 4; ++i) {
            int randomRoom = (int)(Math.random() * rooms.size());
            rooms.get(randomRoom).setRandomConnection(createRoom(level));
        }
        int randomRoom = (int)(Math.random() * rooms.size());
        rooms.get(randomRoom).setRandomConnection(createRoom(0));
    }

    public static Room createRoom(int level) {
        String type;
        if(level == -1) {
            type = "empty";
        } else if (level == 0) {
            type = "boss";
        } else if(Math.random() < 1/level) {
            type = "easy";
        } else if(Math.random() < 1-(level/10)) {
            type = "medium";
        } else {
            type = "hard";
        }
        Room newRoom = new Room(type);
        rooms.add(newRoom);
        return newRoom;
    }

    public static void changeRoom() {
        //implement logic for changing rooms
    }

    public static void checkDoor(int x, int y) {
        
    }
}
