package main.backend.weapons;

import javafx.geometry.Point2D;
import main.backend.collidables.Collidable;

public abstract class Weapon extends Collidable {
    //x for the x-axis position
    //y for the y-axis position
    //r for the angle (in degrees) between it and the positive x-axis
    private static Point2D positiveX = new Point2D(1, 0);
    protected Point2D position;
    protected double r;

    //damage is the amount of damage the weapon does
    protected int damage;

    //range is the range of the weapon
    //aoe is the angle (in degrees) which the weapon effects (+- its current r)
    protected double range;
    protected double aoe;

    //rate of fire, measured in number of frames between separate attacks
    //remember game runs at 60 fps roughly
    protected double rof;

    //a count of how many frames have passed attacking so far
    protected double attackCounter= -1;

    //id is the key stored in the dictionary with the weapon being its value
    protected int id;

    //whether or not any sprite is carrying the weapon or not
    protected boolean dropped;

    protected Weapon(double x, double y, double r, int damage, double range, double aoe,
                     int id, String imagePath, boolean dropped, int maxsize, double rof) {
        super(x, y, maxsize, imagePath);
        this.position = new Point2D(x, y);
        this.r = r;
        this.damage = damage;
        this.range = range;
        this.aoe = aoe;
        this.id = id;
        this.dropped = dropped;
        this.rof = rof;
    }

    public Point2D getPosition() {
        return this.position;
    }

    public double getRange() {
        return this.range;
    }

    public int getDamage() {
        return this.damage;
    }

    public void move(Point2D position) {
        this.position = position;
        super.setImagePosition(position);
    }

    public void rotate(Point2D target) {
        if (attackCounter != -1) {
            attack();
            return;
        }
        double angle = target.subtract(this.position).angle(positiveX);
        if (target.getY() > this.position.getY()) {
            r = angle;
        } else {
            r = -angle;
        }
        super.setRotate(this.r);
    }

    public void startAttack() {
        if (attackCounter == -1) {
            attackCounter = 0;
            this.r -= aoe/2;
            super.setRotate(this.r);
        }
    }

    public abstract void attack();

    public void destroy() {
        WeaponManager.destroy(id);
    }
}
