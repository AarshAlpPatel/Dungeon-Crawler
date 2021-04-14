package main.backend.weapons;

public class Axe extends Weapon {
    public Axe(double x, double y, boolean dropped, double translateX,
               double translateY, double attackInterval) {
        super(x, y, 0, 20, 5, 90,
                "axeh.png", dropped, 80, 25, translateX, translateY, attackInterval);
        this.range = this.image.getBoundsInParent().getWidth() / 2 + translateX;
    }

    @Override
    public void attack() {
        this.r += aoe / rof;
        setRotate(this.r);
    }
}
