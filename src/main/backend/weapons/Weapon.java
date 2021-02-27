package main.backend.weapons;

import javafx.scene.image.*;
import main.backend.characters.Sprite;

public abstract class Weapon {
    //x for the x-axis position
    //y for the y-axis position
    //r for the angle (in degrees) between it and the positive x-axis
    protected double x, y, r;

    //damage is the amount of damage the weapon does
    protected int damage;

    //range is the range of the weapon
    //aoe is the angle (in degrees) which the weapon effects (+- its current r)
    protected double range, aoe;

    //id is the key stored in the dictionary with the weapon being its value
    protected int id;

    //image of weapon
    protected ImageView image;

    //whether or not any sprite is carrying the weapon or not
    protected boolean dropped;

    protected Weapon(double x, double y, double r, int damage, double range, 
                     double aoe, int id, String imagePath, boolean dropped, double scale) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.damage = damage;
        this.range = range;
        this.aoe = aoe;
        this.id = id;
        this.image = new ImageView(new Image(imagePath));
        this.image.setScaleX(scale);
        this.image.setScaleY(scale);
        this.dropped = dropped;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public ImageView getImage() {
        return this.image;
    }

    public double getRange() {
        return this.range;
    }

    public int getDamage() {
        return this.damage;
    }

    public void move(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void follow(int x, int y) {
        double angle = Math.atan2(y-this.y, x-this.x);
        double anglediff = (0 - angle + 180) % 360 - 180;
        r = anglediff;
        this.image.setRotate(this.r);
    }

    public boolean checkCollision(Sprite s) {
        double angle = Math.atan2(s.getY()-this.y, s.getX()-this.x);
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
