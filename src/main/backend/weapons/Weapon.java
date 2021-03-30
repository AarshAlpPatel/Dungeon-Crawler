package main.backend.weapons;

import java.util.ArrayList;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import main.backend.Controller;
import main.backend.characters.Sprite;
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

    protected double attackInterval;

    //a count of how many frames have passed attacking so far
    protected double attackCounter = -1;

    //id is the key stored in the dictionary with the weapon being its value
    protected int id;

    //whether or not any sprite is carrying the weapon or not
    protected boolean dropped;

    protected Weapon(double x, double y, double r, int damage, double range, double aoe,
                     String imagePath, boolean dropped, int maxsize, double rof,
                     double translateX, double translateY, double attackInterval) {
        super(x, y, maxsize, imagePath, translateX, translateY);
        this.position = new Point2D(x, y);
        this.r = r;
        this.damage = damage;
        this.range = range;
        this.aoe = aoe;
        this.dropped = dropped;
        this.rof = rof;
        this.attackInterval = attackInterval;
    }

    public Point2D getPosition() {
        return this.position;
    }

    public double getRange() {
        return this.range;
    }

    public double getROF() {
        return this.rof;
    }

    public int getDamage() {
        return this.damage;
    }

    public void setID(int id) {
        this.id = id;
    }

    public void setAttackInterval(double attackInterval) {
        this.attackInterval = attackInterval;
    }

    public void move(Point2D position) {
        this.position = position;
        super.setImagePosition(position);
    }

    public int rotate(Point2D target) {
        if (attackCounter != -1) {
            return animate();
        }
        double angle = target.subtract(this.position).angle(positiveX);
        if (target.getY() > this.position.getY()) {
            r = angle;
        } else {
            r = -angle;
        }
        super.setRotate(this.r);
        return 0;
    }

    public void startAttack() {
        if (attackCounter == -1) {
            attackCounter = 0;
            this.r -= aoe / 2;
            super.setRotate(this.r);
        }
    }

    public void dropWeapon() {
        dropped = true;
    }

    public void destroyWeapon() {
        ArrayList<Node> image = new ArrayList<>();
        image.add(this.image);
        Controller.destroyImage(image);
    }

    public int animate() {
        if (attackCounter == attackInterval) {
            finishAttack();
            return 3;
        } else if (attackCounter < rof) {
            this.attack();
            ++attackCounter;
            return 2;
        }
        ++attackCounter;
        return 1;
    }

    public void finishAttack() {
        attackCounter = -1;
    }

    public boolean isAttacking() {
        return attackCounter != -1;
    }

    public abstract void attack();

    public boolean inRange(Sprite s, double offset) {
        return this.distance(s) <= this.range + offset;
    }
}
