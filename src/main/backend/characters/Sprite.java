package main.backend.characters;

import main.backend.weapons.Weapon;

public abstract class Sprite {
    protected double x, y;
    protected double attackMultiplier, speed;
    protected int health, regeneration;
    protected Weapon mainWeapon;
    protected String name;
    protected String imagePath;

    protected Sprite(double x, double y, double attackMultiplier, double speed,
                     int health, int regeneration, Weapon weapon, String name, String imagePath) {
        this.x = x;
        this.y = y;
        this.attackMultiplier = attackMultiplier;
        this.speed = speed;
        this.health = health;
        this.regeneration = regeneration;
        this.mainWeapon = weapon;
        this.name = name;
        this.imagePath = imagePath;
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

    public String getImagePath() {
        return imagePath;
    }

    public String getName() {
        return name;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
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

    public void move(double x, double y) {
        this.x += x*speed;
        this.y += y*speed;
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
