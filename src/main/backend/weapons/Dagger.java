package main.backend.weapons;

public class Dagger extends Weapon {
    public Dagger(double x, double y, boolean dropped, double translateX, double translateY) {
        super(x, y, 0, 10, 5, 45, "daggerh.png", dropped, 80, 10, translateX, translateY);
    }

    @Override
    public void attack() {
        this.r += aoe/rof;
        setRotate(this.r);
    }
}