package main.backend.collidables;

import main.backend.Controller;

public class Wall extends Collidable {
    public Wall(double x, double y, double length, double height) {
        super(x, y, length, height, "wall.png",
              (Controller.getLength() - length) / 2,
              (Controller.getHeight() - height) / 2);
    }
}
