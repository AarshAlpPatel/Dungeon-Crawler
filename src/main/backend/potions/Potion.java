package main.backend.potions;

import javafx.geometry.Point2D;
import main.backend.characters.Upgradeable;
import main.backend.collidables.Collidable;
import main.backend.inventory.Collectable;

public abstract class Potion extends Collidable implements Upgradeable, Collectable {
    //image of potion
    protected String name;
    protected Point2D position;

    private static String[] rarityChoices = {"common", "rare", "epic"};
    
    Upgradeable obj;

    //common, rare, epic
    protected String rarity;

    protected double power;

    protected Potion(double x, double y, String imagePath, String name, String rarity) {
        super(x, y, 50, "main/design/images/potions/" + imagePath, 0, 0);
        this.name = name;
        this.rarity = rarity;
        obj = null;
    }

    public static String getRandomRarity() {
        return rarityChoices[(int)(Math.random()*rarityChoices.length)];
    }

    @Override
    public double getAttackMultiplier() {
        return obj.getAttackMultiplier();
    }

    @Override
    public double getSpeed() {
        return obj.getSpeed();
    }
    
    @Override
    public void changeHealth(double health) {
        obj.changeHealth(health);
    }

    public Upgradeable use(Upgradeable obj) {
        this.obj = obj;
        return this;
    };

    public abstract void setPower();
}
