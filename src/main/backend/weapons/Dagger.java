package main.backend.weapons;

public class Dagger extends Weapon {
    public Dagger(double x, double y, int id, boolean dropped) {
        super(x, y, 0, 10, 5, 45, id, "/main/design/images/daggerh.png", dropped, 80, 10);
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