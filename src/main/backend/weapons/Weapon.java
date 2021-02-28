package main.backend.weapons;

import javafx.geometry.Point2D;
import javafx.scene.image.*;
import main.backend.characters.Sprite;
import main.frontend.MainScreen;

public abstract class Weapon {
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

    //id is the key stored in the dictionary with the weapon being its value
    protected int id;

    //image of weapon
    protected ImageView image;

    //whether or not any sprite is carrying the weapon or not
    protected boolean dropped;

    protected Weapon(double x, double y, double r, int damage, double range, 
                     double aoe, int id, String imagePath, boolean dropped, double scale) {
        this.position = new Point2D(x, y);
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

    public Point2D getPosition() {
        return this.position;
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

    public void setPosition(Point2D position) {
        this.position = position;
        this.image.setTranslateX(this.position.getX() - MainScreen.getLength() / 2);
        this.image.setTranslateY(this.position.getY() - MainScreen.getHeight() / 2);
    }

    public void move(double dx, double dy) {
        this.position = this.position.add(dx, dy);
        this.image.setTranslateX(this.position.getX() - MainScreen.getLength() / 2);
        this.image.setTranslateY(this.position.getY() - MainScreen.getHeight() / 2);
    }

    public void follow(Point2D target) {
        double angle = target.subtract(this.position).angle(positiveX);
        if (target.getY() > this.position.getY()) {
            r = angle;
        } else {
            r = -angle;
        }
        this.image.setRotate(this.r);
    }

    public boolean checkCollision(Sprite s) {
        double angle = s.getPosition().subtract(this.position).angle(positiveX);
        if (angle > aoe || angle < aoe) {
            return false;
        }
        return s.getPosition().distance(this.position) > range;
    }

    public void destroy() {
        WeaponManager.destroy(id);
    }
}
