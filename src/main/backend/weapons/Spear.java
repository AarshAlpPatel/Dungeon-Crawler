package main.backend.weapons;

public class Spear extends Weapon {
    public Spear(double x, double y, boolean dropped, double translateX, double translateY) {
        super(x, y, 0, 10, 30, 0, "spearh.png", dropped, 120, 15, translateX, translateY);
    }

    @Override
    public void attack() {
        this.offset.setX(this.offset.getX() + range/rof);
    }

    @Override
    public void finishAttack() {
        super.finishAttack();
        this.offset.setX(this.offset.getX()-this.range);
    }
}