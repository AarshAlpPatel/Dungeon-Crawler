package main.backend.rooms;

import java.util.ArrayList;

import javafx.scene.image.ImageView;
import main.backend.exceptions.EdgeOfScreen;
import main.backend.exceptions.WallCollision;
import main.backend.Controller;
import main.backend.characters.Sprite;
import main.backend.collidables.WallManager;

public abstract class Room {
    protected static final int MAX_CONNECTIONS = 4;
    protected Room[] connections;
    protected WallManager walls;

    protected boolean status;

    protected Room() {
        this.connections = new Room[MAX_CONNECTIONS];
        this.status = false;
    }

    public boolean getLockStatus() {
        return status;
    }

    public Room getNextRoom(Door direction) {
        return connections[direction.ordinal()];
    }

    public Door checkEdge(double x, double y) {
        if (x <= Controller.getMinPlayerX() + 5
            && y >= Controller.getMidY() - RoomManager.getDoorWidth() / 2
            && y <= Controller.getMidY() + RoomManager.getDoorWidth() / 2
            && connections[Door.WEST.ordinal()] != null
            && connections[Door.WEST.ordinal()].getLockStatus()) {
            return Door.WEST;
        } else if (x >= Controller.getMaxPlayerX() - 5
                   && y >= Controller.getMidY() - RoomManager.getDoorWidth() / 2
                   && y <= Controller.getMidY() + RoomManager.getDoorWidth() / 2
                   && connections[Door.EAST.ordinal()] != null
                   && connections[Door.EAST.ordinal()].getLockStatus()) {
            return Door.EAST;
        } else if (y <= Controller.getMinPlayerY() + 5
                   && x >= Controller.getMidX() - RoomManager.getDoorWidth() / 2
                   && x <= Controller.getMidX() + RoomManager.getDoorWidth() / 2
                   && connections[Door.NORTH.ordinal()] != null
                   && connections[Door.NORTH.ordinal()].getLockStatus()) {
            return Door.NORTH;
        } else if (y >= Controller.getMaxPlayerY() - 5
                   && x >= Controller.getMidX() - RoomManager.getDoorWidth() / 2
                   && x <= Controller.getMidX() + RoomManager.getDoorWidth() / 2
                   && connections[Door.SOUTH.ordinal()] != null
                   && connections[Door.SOUTH.ordinal()].getLockStatus()) {
            return Door.SOUTH;
        } else {
            return null;
        }
    }

    public boolean setRandomConnection(Room next) {
        ArrayList<Integer> open = new ArrayList<>();
        for (int i = 0; i < 4; ++i) {
            if (connections[i] == null) {
                open.add(i);
            }
        }
        if (open.isEmpty()) {
            return false;
        }
        int direction = open.get((int) (Math.random() * open.size()));
        this.connections[direction] = next;
        switch (direction) {
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

    public void setStatusTrue() {
        this.status = true;
        for (Room r: connections) {
            if (r != null) {
                r.status = true;
                System.out.println("Set room status to true");
            }
        }
    }

    public ArrayList<ImageView> getWalls() {
        return walls.getWalls();
    }

    //in anticipation of walls
    public boolean validMove(double x, double y, Sprite s) {
        if (walls.checkCollisions(s)) {
            throw new WallCollision("Hit a wall");
        } else if (x < Controller.getMinX() || x > Controller.getLength()
            || y < Controller.getMinY() || y > Controller.getHeight()) {
            throw new EdgeOfScreen("Moving past edge of screen");
        }
        return true;
    }

    public boolean[] getConnections() {
        boolean[] conns = new boolean[connections.length];
        for (int i = 0; i < connections.length; ++i) {
            conns[i] = connections[i] != null;
        }
        return conns;
    }

    public void setWalls() {
        walls = new WallManager(4, getConnections());
    }

    public abstract ArrayList<ImageView> getImages();

    public abstract void enter();
}
