package main.backend.rooms;

import java.util.ArrayList;

import javafx.scene.image.ImageView;
import main.backend.exceptions.EdgeOfScreen;
import main.frontend.MainScreen;

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

    public Room checkEdge(double x, double y) {
        if (x <= MainScreen.getMinX()+5 
            && y >= MainScreen.getMidY()-RoomManager.getDoorWidth()/2
            && y <= MainScreen.getMidY()+RoomManager.getDoorWidth()/2) {
            return connections[Door.WEST.ordinal()];
        } else if (x >= MainScreen.getMaxX()-5
                   && y >= MainScreen.getMidY()-RoomManager.getDoorWidth()/2
                   && y <= MainScreen.getMidY()+RoomManager.getDoorWidth()/2) {
            return connections[Door.EAST.ordinal()];
        } else if (y <= MainScreen.getMinY()+5 
                   && x >= MainScreen.getMidX()-RoomManager.getDoorWidth()/2
                   && x <= MainScreen.getMidX()+RoomManager.getDoorWidth()/2) {
            return connections[Door.NORTH.ordinal()];
        } else if (y >= MainScreen.getMaxY()-5
                   && x >= MainScreen.getMidX()-RoomManager.getDoorWidth()/2
                   && x <= MainScreen.getMidX()+RoomManager.getDoorWidth()/2) {
        return connections[Door.SOUTH.ordinal()];
        } else {
            return null;
        }
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

    //in anticipation of walls
    public boolean validMove(double x, double y) {
        if (x < MainScreen.getMinX() || x > MainScreen.getLength()
            || y < MainScreen.getMinY() || y > MainScreen.getHeight()) {
            throw new EdgeOfScreen("Edge of screen");
        }
        return true;
    }

    public abstract ArrayList<ImageView> getImages();

    public abstract void enter();
}
