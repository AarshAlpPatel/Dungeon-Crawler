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
    private boolean clear;
    private EnemyManager enemies;

    public Room(String type) {
        this.connections = new Room[MAX_CONNECTIONS];
        this.type = type;
        this.clear = false;
        this.enemies = new EnemyManager();
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

    public boolean getClear() {
        return clear;
    }

    public boolean setRandomConnection(Room next) {
        ArrayList<Integer> open = new ArrayList<>();
        for(int i = 0; i < 4; ++i) {
            if(connections[i] == null) {
                open.add(i);
            }
        }
        if(open.isEmpty()) {
            return false;
        }
        int direction = (int)(Math.random() * open.size());
        this.connections[open.get(direction)] = next;
        return true;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setClearTrue() {
        this.clear = true;
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
