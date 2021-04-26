package main.backend.characters;

import main.backend.collidables.Collidable;

public class Bag extends Collidable {
    public Bag(double x, double y, double length, double height, String imagePath,
               double translateX, double translateY) {
        super(x, y, length, height, imagePath, translateX, translateY);
    }
}
