package backend.weapons;

import backend.characters.Sprite;

public abstract class Weapon {
    //x for the x-axis position
    //y for the y-axis position
    //r for the angle (in radians) between it and the positive x-axis
    protected double x, y, r;

    //damage is the amount of damage the weapon does
    protected int damage;

    //range is the range of the weapon
    //aoe is the angle (in radians) which the weapon effects (+- its current r)
    protected double range, aoe;

    //id is the key stored in the dictionary with the weapon being its value
    protected int id;

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getRange() {
        return this.range;
    }

    public int getDamage() {
        return this.damage;
    }

    public boolean checkCollision(Sprite s) {
        double angle = Math.atan2((double)(s.getY()-this.y), (double)(s.getX()-this.x));
        double anglediff = (r - angle + 180) % 360 - 180;
        if(anglediff > aoe || anglediff < aoe) {
            return false;
        }
        return Math.hypot(Math.abs(s.getY()-this.y), Math.abs(s.getX()-this.x)) > range;
    }

    public void destroy() {
        WeaponManager.destroy(id);
    }
}
