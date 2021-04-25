package main.backend.weapons;

public class Key extends Weapon {
    public Key(double x, double y, boolean dropped, double translateX,
                  double translateY, double attackInterval) {
        super(x, y, 0, 7, 5, 45,
                "key.png", dropped, 80, 10, translateX, translateY, attackInterval);
        this.range = this.image.getBoundsInParent().getWidth() / 2 + translateX;
    }

    @Override
    public void attack() {
        this.r += aoe / rof;
        setRotate(this.r);
    }
}
