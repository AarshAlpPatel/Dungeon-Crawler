package main.backend.rooms;

import java.util.ArrayList;

import main.backend.characters.EnemyManager;

enum Door {
    NORTH, WEST, SOUTH, EAST;
}

public class Room {
    private static int MAX_CONNECTIONS = 4;
    private Room[] connections;

    private String type;
    private boolean visited;
    private EnemyManager enemies;

    public Room(String type, EnemyManager enemies) {
        this.connections = new Room[MAX_CONNECTIONS];
        this.type = type;
        this.visited = false;
        this.enemies = enemies;
    }

    public Room getConnection(Door direction) {
        return connections[direction.ordinal()];
    }

    public boolean[] getConnections() {
        boolean[] hasConnection = new boolean[connections.length];
        for(int i = 0; i < connections.length; ++i) {
            hasConnection[i] = (connections[i] != null);
        }
        return hasConnection;
    }

    public String getType() {
        return this.type;
    }

    public boolean setRandomConnection(Room next) {
        ArrayList<Integer> open = new ArrayList<>();
        for(int i = 0; i < 4; ++i) {
            if(connections[i] == null) {
                open.add(i);
            }
        }
        if(open.size() == 0) {
            return false;
        }
        int direction = Math.random() * open.size();
        connections[open[direction]] = next;
    }

    public void setConnection(Door direction, Room next) {
        connections[direction.ordinal()] = next;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean fullyConnected() {
        for(Room room : connections) {
            if(room == null) {
                return false;
            }
        }
        return true;
    }
}
