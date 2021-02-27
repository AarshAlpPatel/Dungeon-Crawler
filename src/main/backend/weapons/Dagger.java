package main.backend.weapons;

public class Dagger extends Weapon {
    public Dagger(double x, double y, int id, boolean dropped) {
        super(x, y, 0, 10, 5, Math.PI / 6, id, "/main/design/images/dagger.png", dropped, 0.4);
    }
}