package main.backend.characters;

import java.util.ArrayList;

import javafx.geometry.Point2D;
import javafx.scene.image.*;
import main.backend.collidables.Collidable;
import main.backend.rooms.RoomManager;
import main.backend.weapons.Weapon;

public abstract class Sprite extends Collidable {
    protected Point2D position;
    protected double attackMultiplier;
    protected double speed;
    protected int regeneration;
    protected int health;
    protected Weapon mainWeapon;
    protected String name;

    protected Sprite(double x, double y, double attackMultiplier, double speed, 
                     int health, int regeneration, Weapon weapon, String name, 
                     String imagePath, int maxsize) {
        super(x, y, maxsize, imagePath);
        this.position = new Point2D(x, y);
        this.attackMultiplier = attackMultiplier;
        this.speed = speed;
        this.health = health;
        this.regeneration = regeneration;
        this.mainWeapon = weapon;
        this.name = name;
    }

    public Point2D getPosition() {
        return this.position;
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

    public double getHeight() {
        return this.image.boundsInParentProperty().get().getHeight();
    }

    public double getWidth() {
        return this.image.boundsInParentProperty().get().getWidth();
    }

    @Override
    public ArrayList<ImageView> getImage() {
        ArrayList<ImageView> images = super.getImage();
        images.addAll(this.mainWeapon.getImage());
        return images;
    }

    public String getName() {
        return name;
    }

    public Weapon getMainWeapon() {
        return mainWeapon;
    }

    @Override
    public void setPosition(Point2D position) {
        this.position = position;
        super.setPosition(position);
        mainWeapon.move(position);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWeapon(Weapon mainWeapon) {
        this.mainWeapon = mainWeapon;
    }

    @Override
    public String toString() {
        return String.format("Character: %s", name);
    }

    public double getDistance(Sprite s) {
        return this.position.distance(s.position);
    }

    public void move(double dx, double dy) {
        dx *= speed;
        dy *= speed;
        double x = this.position.getX() + dx;
        double y = this.position.getY() + dy;
        super.setPosition(new Point2D(x, y));
        if(!RoomManager.validMove(x, y, this)) {
            System.out.println("INVALID MOVE");
            super.setPosition(this.position);
            return;
        }

        this.position = this.position.add(dx, dy);
        super.setPosition(this.position);
        mainWeapon.move(this.position);
    }

    public void destroy() {
        this.mainWeapon.destroy();
    }
}
