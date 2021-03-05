package main.backend.rooms;

public class RoomManager {
    private static Room current;
    private static ArrayList<Room> rooms;

    public static void createRooms(int level) {
        for(int i = 0; i < 10; ++i) {
            
        }
    }

    public static Room createRoom(String type) {
        Room newRoom = new Room(type);
        rooms.add(newRoom);
        return newRoom;
    }
}
