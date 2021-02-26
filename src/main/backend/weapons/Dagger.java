package main.backend.weapons;

import java.lang.Math;

public class Dagger extends Weapon {
    public Dagger(double x, double y, int id, boolean dropped) {
        super(x, y, 0, 10, 5, Math.PI/6, id, "/main/design/images/red_dagger.png", dropped);
    }
}
