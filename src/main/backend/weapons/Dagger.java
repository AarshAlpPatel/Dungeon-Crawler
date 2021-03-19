package main.backend.weapons;

public class Dagger extends Weapon {
    public Dagger(double x, double y, int id, boolean dropped, double translateX, double translateY) {
        super(x, y, 0, 10, 5, 45, id, "daggerh.png", dropped, 80, 10, translateX, translateY);
    }

    @Override
    public void attack() {
        if (attackCounter == rof) {
            attackCounter = -1;
            return;
        }
        this.r += aoe/rof;
        setRotate(this.r);
        attackCounter++;
    }
}