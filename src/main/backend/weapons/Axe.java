package main.backend.weapons;

public class Axe extends Weapon {
    public Axe(double x, double y, int id, boolean dropped) {
        super(x, y, 0, 20, 5, Math.PI/6, id, "/main/design/images/axe.png", dropped, 0.8);
    }
}
