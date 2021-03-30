package main.backend.characters;

import java.util.ArrayList;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.ProgressBar;
import main.backend.collidables.Collidable;
import main.backend.rooms.RoomManager;
import main.backend.weapons.Weapon;

public abstract class Sprite extends Collidable {
    protected Point2D position;
    protected double attackMultiplier;
    protected double speed;
    protected int regeneration;
    protected Double health;
    protected double maxHealth;
    protected Weapon mainWeapon;
    protected String name;
    protected ProgressBar healthBar;

    protected Sprite(double x, double y, double attackMultiplier, double speed, 
                     double health, int regeneration, Weapon weapon, String name, 
                     String imagePath, int maxsize) {
        super(x, y, maxsize, imagePath, 0, 0);
        this.position = new Point2D(x, y);
        this.attackMultiplier = attackMultiplier;
        this.speed = speed;
        this.health = health;
        this.maxHealth = health;
        this.regeneration = regeneration;
        this.mainWeapon = weapon;
        this.name = name;
        this.healthBar = new ProgressBar();
        this.healthBar.setProgress(1.0);
        this.healthBar.setPrefWidth(this.image.getBoundsInParent().getWidth());
        this.healthBar.setScaleShape(false);
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
    public ArrayList<Node> getImage() {
        ArrayList<Node> images = super.getImage();
        images.addAll(this.mainWeapon.getImage());
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
        super.setImagePosition(position);
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
        Point2D newPos = new Point2D(x, y);
        super.setImagePosition(newPos);
        if (!RoomManager.validMove(x, y, this)) {
            super.setImagePosition(this.position);
            return;
        }

        setPosition(newPos);
    }

    public void hit(Sprite s) {
        s.takeDamage(mainWeapon.getDamage() * this.attackMultiplier);
    }

    public void takeDamage(double damage) {
        if (this.health <= damage) {
            this.health = 0.0;
            this.destroy();
        } else {
            this.health -= damage;
            this.healthBar.setProgress(this.health / this.maxHealth);
        }
        if (this instanceof Enemy) {
            System.out.println(health);
        }
    }

    public abstract Double getHealth();

    public abstract void destroy();
}
