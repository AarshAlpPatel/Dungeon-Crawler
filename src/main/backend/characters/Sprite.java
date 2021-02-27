package main.backend.characters;

import javafx.scene.image.*;
import main.backend.weapons.Weapon;
import main.frontend.MainScreen;

public abstract class Sprite {
    protected double x, y;
    protected double attackMultiplier, speed;
    protected int health, regeneration;
    protected Weapon mainWeapon;
    protected String name;
    protected ImageView image;

    protected Sprite(double x, double y, double attackMultiplier, double speed, 
                     int health, int regeneration, Weapon weapon, String name, 
                     String imagePath, int maxsize) {
        this.x = x;
        this.y = y;
        this.attackMultiplier = attackMultiplier;
        this.speed = speed;
        this.health = health;
        this.regeneration = regeneration;
        this.mainWeapon = weapon;
        this.name = name;
        setImage(imagePath, maxsize);
    }

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

    public ImageView getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public void setImage(String imagePath, int maxsize) {
        this.image = new ImageView(new Image(imagePath));
        this.image.setPreserveRatio(true);
        this.image.setFitHeight(maxsize);
        this.image.setFitWidth(maxsize);
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
        return Math.hypot(Math.abs(s.getY()-this.y), Math.abs(s.getX()-this.x));
    }

    public void move(double xDelta, double yDelta) {
        double newX = this.x + xDelta*speed;
        double newY = this.y + yDelta*speed;
        if(newX < 0 || newX > MainScreen.length || newY < 0 || newY > MainScreen.height) {
            return;
        }

        this.x = newX;
        this.y = newY;
        this.image.setX(this.x);
        this.image.setY(this.y);
    }

    public void hit(Sprite s) {
        s.health -= this.attackMultiplier * this.mainWeapon.getDamage();
        if(s.health < 0) {
            s.destroy();
        }
    }

    public void destroy() {
        this.mainWeapon.destroy();
    }
}
