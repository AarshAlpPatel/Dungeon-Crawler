package main.backend.weapons;

public class Axe extends Weapon {
    public Axe(double x, double y, int id, boolean dropped) {
        super(x, y, 0, 20, 5, 90, id, "/main/design/images/axeh.png", dropped, 120, 20);
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
