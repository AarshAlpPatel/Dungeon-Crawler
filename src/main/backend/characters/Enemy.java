package main.backend.characters;

import main.backend.Controller;
import main.backend.weapons.Weapon;

import java.util.ArrayList;

import javafx.geometry.Point2D;
import javafx.scene.Node;

public class Enemy extends Sprite {
    protected int id;
    protected double healthBarYOffset;
    protected String deathImagePath;
    protected boolean direction;
    protected static String[] weapons = {"dagger", "spear", "axe"};

    protected Enemy(double x, double y, double attackMultiplier, double speed,
                    int health, int regeneration, Weapon weapon, String name,
                    String imagePath, int id, int maxsize, String deathImagePath) {
        super(x, y, attackMultiplier, speed, health, regeneration, weapon, name,
                "enemies/" + imagePath, maxsize);
        this.id = id;
        this.healthBar.setPrefHeight(10);
        this.healthBar.setPrefWidth(this.image.getBoundsInParent().getWidth());
        this.healthBarYOffset = this.image.getBoundsInParent().getHeight() / 2 + 5;
        setHealthBarPosition(this.position);
        this.deathImagePath = "enemies/" + deathImagePath;
        this.direction = true;
    }

    protected void setHealthBarPosition(Point2D position) {
        this.healthBar.setTranslateX(position.getX() - Controller.getLength() / 2);
        this.healthBar.setTranslateY(position.getY()
                - Controller.getHeight() / 2 - this.healthBarYOffset);
    }

    @Override
    public void move(double dx, double dy) {
        if (dx < 0) {
            direction = false;
        } else {
            direction = true;
        }

        super.move(dx, dy);
        setHealthBarPosition(this.position);
    }

    @Override
    public ArrayList<Node> getImage() {
        ArrayList<Node> images = super.getImage();
        if (!isDead()) {
            images.add(this.healthBar);
        }
        return images;
    }

    public boolean isDead() {
        return health == 0;
    }

    @Override
    public void destroy() {
        ArrayList<Node> images = this.getImage();
        images.add(this.healthBar);
        Controller.destroyImage(images);
        images.clear();
        setImage(deathImagePath);
        images.add(this.image);
        Controller.addImage(images);
        Player.getInstance().getStats().addMonster();

        this.mainWeapon.dropWeapon();
        this.mainWeapon = null;
    }

    public void setWeaponDirection() {
        if (direction) {
            mainWeapon.rotate(this.position.add(1, 0));
        } else {
            mainWeapon.rotate(this.position.add(-1, 0));
        }
    }

    @Override
    public Double getHealth() {
        return this.health;
    }

    @Override
    public String toString() {
        return String.format("%s at position: x %f, y %f", this.name,
                this.position.getX(), this.position.getY());
    }
}
