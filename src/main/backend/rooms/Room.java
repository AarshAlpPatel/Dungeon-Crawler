package main.backend.rooms;

import java.util.ArrayList;

import javafx.scene.image.ImageView;

enum Door {
    NORTH, WEST, SOUTH, EAST;
}

public abstract class Room {
    protected static int MAX_CONNECTIONS = 4;
    protected Room[] connections;

    protected boolean clear;

    protected Room() {
        this.connections = new Room[MAX_CONNECTIONS];
        this.clear = false;
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

    public void setClearTrue() {
        this.clear = true;
    }

    public abstract ArrayList<ImageView> getImages();

    public abstract void enter();
}
