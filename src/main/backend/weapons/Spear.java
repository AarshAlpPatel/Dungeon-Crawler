package main.backend.weapons;

public class Spear extends Weapon {
    public Spear(double x, double y, boolean dropped, double translateX,
                 double translateY, double attackInterval) {
        super(x, y, 0, 10, 30, 0, "spearh.png",
                dropped, 80, 15, translateX, translateY, attackInterval);
    }

    @Override
    public void attack() {
        this.offset.setX(this.offset.getX() + range / rof);
    }

    @Override
    public void finishAttack() {
        super.finishAttack();
        this.offset.setX(this.offset.getX() - this.range);
    }
}