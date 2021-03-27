package main.backend.characters;

import main.backend.Controller;
import main.backend.weapons.Weapon;

import java.util.ArrayList;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Enemy extends Sprite {
    protected int id;
    protected double healthBarYOffset;
    protected String deathImagePath;

    protected Enemy(double x, double y, double attackMultiplier, double speed, int health, int regeneration,
                    Weapon weapon, String name, String imagePath, int id, int maxsize, String deathImagePath) {
        super(x, y, attackMultiplier, speed, health, regeneration, weapon, name, "enemies/" + imagePath, maxsize);
        this.id = id;
        this.healthBar.setPrefHeight(10);
        this.healthBar.setPrefWidth(this.image.getBoundsInParent().getWidth());
        this.healthBarYOffset = this.image.getBoundsInParent().getHeight()/2+5;
        setHealthBarPosition(this.position);
        this.deathImagePath = "enemies/" + deathImagePath;
    }

    protected void setHealthBarPosition(Point2D position) {
        this.healthBar.setTranslateX(position.getX() - Controller.getLength() / 2);
        this.healthBar.setTranslateY(position.getY() - Controller.getHeight() / 2 - this.healthBarYOffset);
    }

    @Override
    public void move(double dx, double dy) {
        super.move(dx, dy);
        setHealthBarPosition(this.position);
    }

    @Override
    public ArrayList<Node> getImage() {
        ArrayList<Node> images = super.getImage();
        images.add(this.healthBar);
        return images;
    }

    public boolean isDead() {
        return health == 0;
    }

    @Override
    public void destroy() {
        ArrayList<Node> images = this.getImage();
        Controller.destroyImage(images);
        images.clear();

        setImage(deathImagePath);
        images.add(this.image);
        Controller.addImage(images);
    }
}
