package backend.characters;

import backend.weapons.Weapon;

public abstract class Sprite {
    protected double x, y;
    protected double attackMultiplier, speed;
    protected int health, regeneration;
    protected Weapon weapon;

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getAttackMultiplier() {
        return this.attackMultiplier;
    }

    public double getSpeed() {
        return this.speed;
    }

    public int getRegeneration() {
        return this.regeneration;
    }

    public double getDistance(Sprite s) {
        return Math.hypot(Math.abs(s.getY()-this.y), Math.abs(s.getX()-this.x));
    }

    public void move(double x, double y) {
        this.x += x*speed;
        this.y += y*speed;
    }

    public void hit(Sprite s) {
        s.health -= this.attackMultiplier * this.weapon.getDamage();
        if(s.health < 0) {
            s.destroy();
        }
    }

    public void destroy() {
        this.weapon.destroy();
    }
}
