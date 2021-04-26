package main.backend.rooms;

import java.util.ArrayList;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import main.backend.weapons.Weapon;
import main.backend.exceptions.EdgeOfScreen;
import main.backend.exceptions.WallCollision;
import main.backend.Controller;
import main.backend.characters.Sprite;
import main.backend.collidables.Collidable;
import main.backend.collidables.WallManager;
import main.backend.characters.EnemyManager;
import main.backend.weapons.WeaponManager;

public class Room {
    protected static final int MAX_CONNECTIONS = 4;
    protected Room[] connections;
    protected WallManager walls;
    protected EnemyManager enemies;
    private String difficulty;
    protected ArrayList<Collidable> collectables;

    protected boolean visited;

    protected Room(String difficulty) {
        this.connections = new Room[MAX_CONNECTIONS];
        this.collectables = new ArrayList<>();
        this.visited = false;
        this.difficulty = difficulty;

        if (difficulty.equals("empty")) {
            this.enemies = new EnemyManager(0, 0, false);
        } else if (difficulty.equals("easy")) {
            this.enemies = new EnemyManager(4, 1, false);
        } else if (difficulty.equals("medium")) {
            this.enemies = new EnemyManager(5, 2, false);
        } else if (difficulty.equals("hard")) {
            this.enemies = new EnemyManager(6, 3, false);
        } else if (difficulty.equals("boss")) {
            this.enemies = new EnemyManager(8, 4, true);
        }
    }

    protected void createEnemies(int count, int difficulty) {
        this.enemies = new EnemyManager(count, difficulty, false);
        Controller.addImage(this.enemies.getImages());

    }

    public String getDifficulty() {
        return difficulty;
    }

    public boolean isVisited() {
        return visited;
    }

    public boolean isClear() {
        return enemies.clear();
    }

    public Room getNextRoom(Door direction) {
        return connections[direction.ordinal()];
    }

    public EnemyManager getEnemies() {
        return enemies;
    }

    public Door checkEdge(double x, double y) {
        //challenge room locked door logic
        Weapon generatedWeapon = WeaponManager.create("key", 400, 400, true, 2.5, 10, -1);
        if (this instanceof WeaponRoom && !this.getImages().contains(generatedWeapon.getImage()) && !this.isClear()) {
            return null;
        }
        if (x <= Controller.getMinPlayerX() + 5
            && y >= Controller.getMidY() - RoomManager.getDoorWidth() / 2
            && y <= Controller.getMidY() + RoomManager.getDoorWidth() / 2
            && connections[Door.WEST.ordinal()] != null
            && (connections[Door.WEST.ordinal()].isVisited() || isClear())) {
            return Door.WEST;
        } else if (x >= Controller.getMaxPlayerX() - 5
                   && y >= Controller.getMidY() - RoomManager.getDoorWidth() / 2
                   && y <= Controller.getMidY() + RoomManager.getDoorWidth() / 2
                   && connections[Door.EAST.ordinal()] != null
                   && (connections[Door.EAST.ordinal()].isVisited() || isClear())) {
            return Door.EAST;
        } else if (y <= Controller.getMinPlayerY() + 5
                   && x >= Controller.getMidX() - RoomManager.getDoorWidth() / 2
                   && x <= Controller.getMidX() + RoomManager.getDoorWidth() / 2
                   && connections[Door.NORTH.ordinal()] != null
                   && (connections[Door.NORTH.ordinal()].isVisited() || isClear())) {
            return Door.NORTH;
        } else if (y >= Controller.getMaxPlayerY() - 5
                   && x >= Controller.getMidX() - RoomManager.getDoorWidth() / 2
                   && x <= Controller.getMidX() + RoomManager.getDoorWidth() / 2
                   && connections[Door.SOUTH.ordinal()] != null
                   && (connections[Door.SOUTH.ordinal()].isVisited() || isClear())) {
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

    public ArrayList<Node> getWalls() {
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

    public void addCollectable(Collidable c, Point2D p) {
        this.collectables.add(c);
        c.setImagePosition(p);
        Controller.addImage(c.getImage());
    }

    public Collidable pickUpCollectable(Sprite s) {
        for (Collidable c : collectables) {
            if (c.collidesWith(s)) {
                this.collectables.remove(c);
                Controller.destroyImage(c.getImage());
                return c;
            }
        }
        return null;
    }

    public ArrayList<Node> getImages() {
        ArrayList<Node> images = new ArrayList<>();
        images.addAll(enemies.getImages());
        for (Collidable c : collectables) {
            images.addAll(c.getImage());
        }
        return images;
    }

    public void enter() {
        this.visited = true;
    }

    public boolean hasConnections() {
        boolean found = false;
        int numDoors = 0;
        for (int i = 0; i < 4; i++) {
            if (connections[i] != null) {
                numDoors++;
            }
            if (numDoors > 1) {
                found = true;
            }
        }
        return found;
    }
}
