package main.backend.weapons;

public class Spear extends Weapon {
    public Spear(double x, double y, int id, boolean dropped) {
        super(x, y, 0, 10, 15, Math.PI / 12, id, "/main/design/images/spearh.png", dropped, 0.8);
    }
}