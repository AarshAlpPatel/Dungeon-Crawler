package main.backend.characters;

import java.util.ArrayList;

import javafx.geometry.Point2D;
import javafx.scene.image.*;
import main.backend.exceptions.EdgeOfScreen;
import main.backend.weapons.Weapon;
import main.frontend.EndGame;
import main.frontend.MainScreen;

public abstract class Sprite {
    protected Point2D position;
    protected double attackMultiplier;
    protected double speed;
    protected int regeneration;
    protected int health;
    protected Weapon mainWeapon;
    protected String name;
    protected ImageView image;

    protected Sprite(double x, double y, double attackMultiplier, double speed, 
                     int health, int regeneration, Weapon weapon, String name, 
                     String imagePath, int maxsize) {
        this.position = new Point2D(x, y);
        this.attackMultiplier = attackMultiplier;
        this.speed = speed;
        this.health = health;
        this.regeneration = regeneration;
        this.mainWeapon = weapon;
        this.name = name;
        setImage(imagePath, maxsize);
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

    public ArrayList<ImageView> getImage() {
        ArrayList<ImageView> images = new ArrayList<>();
        images.add(this.image);
        images.add(this.mainWeapon.getImage());
        return images;
    }

    public String getName() {
        return name;
    }

    public Weapon getMainWeapon() {
        return mainWeapon;
    }

    public void setPosition(Point2D position) {
        this.position = position;
        this.image.setTranslateX(this.position.getX() - (double) MainScreen.getLength() / 2);
        this.image.setTranslateY(this.position.getY() - (double) MainScreen.getHeight() / 2);
        mainWeapon.setPosition(position);
    } 

    public void setImage(String imagePath, int maxsize) {
        this.image = new ImageView(new Image(imagePath));
        this.image.setPreserveRatio(true);
        this.image.setFitHeight(maxsize);
        this.image.setFitWidth(maxsize);
        this.image.setTranslateX(this.position.getX() - (double) MainScreen.getLength() / 2);
        this.image.setTranslateY(this.position.getY() - (double) MainScreen.getHeight() / 2);
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
        if (this.position.getX() + dx < 20 || this.position.getX() + dx > MainScreen.getLength()
                || this.position.getY() + dy < 100
                || this.position.getY() + dy > MainScreen.getHeight()) {
            throw new EdgeOfScreen("Edge of screen");
        }

        this.position = this.position.add(dx, dy);
        this.image.setTranslateX(this.position.getX() - (double) MainScreen.getLength() / 2);
        this.image.setTranslateY(this.position.getY() - (double) MainScreen.getHeight() / 2);
        mainWeapon.move(dx, dy);
    }

    public void destroy() {
        this.mainWeapon.destroy();
    }
}
