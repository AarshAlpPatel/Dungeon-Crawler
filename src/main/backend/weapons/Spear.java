package main.backend.weapons;

public class Spear extends Weapon {
    public Spear(double x, double y, int id, boolean dropped) {
        super(x, y, 0, 10, 30, 0, id, "/main/design/images/spearh.png", dropped, 120, 15);
    }

    @Override
    public void attack() {
        if (attackCounter == rof) {
            attackCounter = -1;
            this.offset.setX(this.offset.getX()-this.range);
            return;
        }
        this.offset.setX(this.offset.getX() + range/rof);
        attackCounter++;
    }
}