package main.backend.rooms;

import java.util.ArrayList;

import javafx.scene.image.ImageView;
import main.backend.exceptions.EdgeOfScreen;
import main.frontend.MainScreen;

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

    public Room getNextRoom(Door direction) {
        return connections[direction.ordinal()];
    }
    public Door checkEdge(double x, double y) {
        if (x <= MainScreen.getMinX()+5 
            && y >= MainScreen.getMidY()-RoomManager.getDoorWidth()/2
            && y <= MainScreen.getMidY()+RoomManager.getDoorWidth()/2) {
            return Door.WEST;
        } else if (x >= MainScreen.getMaxX()-5
                   && y >= MainScreen.getMidY()-RoomManager.getDoorWidth()/2
                   && y <= MainScreen.getMidY()+RoomManager.getDoorWidth()/2) {
            return Door.EAST;
        } else if (y <= MainScreen.getMinY()+5 
                   && x >= MainScreen.getMidX()-RoomManager.getDoorWidth()/2
                   && x <= MainScreen.getMidX()+RoomManager.getDoorWidth()/2) {
            return Door.NORTH;
        } else if (y >= MainScreen.getMaxY()-5
                   && x >= MainScreen.getMidX()-RoomManager.getDoorWidth()/2
                   && x <= MainScreen.getMidX()+RoomManager.getDoorWidth()/2) {
            return Door.SOUTH;
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
        int direction = open.get((int)(Math.random() * open.size()));
        this.connections[direction] = next;
        switch(direction) {
            case 0:
                next.connections[Door.SOUTH.ordinal()] = this;
                break;
            case 1:
                next.connections[Door.EAST.ordinal()] = this;
                break;
            case 2:
                next.connections[Door.NORTH.ordinal()] = this;
                break;
            case 3:
                next.connections[Door.WEST.ordinal()] = this;
                break;
            default:
                break;
        }
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
