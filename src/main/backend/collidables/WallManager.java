package main.backend.collidables;

import java.util.ArrayList;

import javafx.scene.shape.Rectangle;
import main.backend.Controller;
import main.backend.characters.Sprite;

public class WallManager {
    private ArrayList<Wall> walls;

    public WallManager(int number, boolean[] connections) {
        walls = new ArrayList<>(number);
        if (connections[0]) {
            walls.add(new Wall(Controller.getMinX(), Controller.getMinY(),
                    (Controller.getLength() - Controller.getDoorWidth()) / 2,
                    Controller.getWallWidth()));
            walls.add(new Wall((Controller.getLength() + Controller.getDoorWidth()) / 2,
                    Controller.getMinY(), (Controller.getLength() - Controller.getDoorWidth()) / 2,
                    Controller.getWallWidth()));
        } else {
            walls.add(new Wall(Controller.getMinX(), Controller.getMinY(),
                    Controller.getLength(), Controller.getWallWidth()));
        }
        if (connections[1]) {
            walls.add(new Wall(Controller.getMinX(), Controller.getMinY(),
                    Controller.getWallWidth(), (Controller.getHeight()
                    - Controller.getDoorWidth()) / 2));
            walls.add(new Wall(Controller.getMinX(), (Controller.getHeight()
                    + Controller.getDoorWidth()) / 2, Controller.getWallWidth(),
                    (Controller.getHeight() - Controller.getDoorWidth()) / 2));
        } else {
            walls.add(new Wall(Controller.getMinX(), Controller.getMinY(),
                    Controller.getWallWidth(), Controller.getHeight()));
        }
        if (connections[2]) {
            walls.add(new Wall(Controller.getMinX(), Controller.getHeight()
                    - Controller.getWallWidth(), (Controller.getLength()
                    - Controller.getDoorWidth()) / 2, Controller.getWallWidth()));
            walls.add(new Wall((Controller.getLength() + Controller.getDoorWidth()) / 2,
                    Controller.getHeight() - Controller.getWallWidth(),
                    (Controller.getLength() - Controller.getDoorWidth()) / 2,
                    Controller.getWallWidth()));
        } else {
            walls.add(new Wall(Controller.getMinX(),
                    Controller.getHeight() - Controller.getWallWidth(),
                    Controller.getLength(), Controller.getWallWidth()));
        }
        if (connections[3]) {
            walls.add(new Wall(Controller.getLength() - Controller.getWallWidth(),
                    Controller.getMinY(), Controller.getWallWidth(),
                    (Controller.getHeight() - Controller.getDoorWidth()) / 2));
            walls.add(new Wall(Controller.getLength() - Controller.getWallWidth(),
                    (Controller.getHeight() + Controller.getDoorWidth()) / 2,
                    Controller.getWallWidth(), (Controller.getHeight()
                    - Controller.getDoorWidth()) / 2));
        } else {
            walls.add(new Wall(Controller.getLength() - Controller.getWallWidth(),
                    Controller.getMinY(), Controller.getWallWidth(), Controller.getHeight()));
        }
    }

    public ArrayList<Rectangle> getWalls() {
        ArrayList<Rectangle> rects = new ArrayList<>();
        for (Wall w : walls) {
            rects.add(w.getWall());
        }
        return rects;
    }

    public boolean checkCollisions(Sprite s) {
        for (Wall w : walls) {
            if (w.collidesWith(s)) {
                return true;
            }
        }
        return false;
    }
}
