package main.backend.potions;

import javafx.geometry.Point2D;
import main.backend.characters.Player;
import main.backend.characters.Sprite;
import main.backend.collidables.Collidable;
import main.backend.rooms.RoomManager;

public abstract class Potion extends Collidable {
    //image of potion
    protected String name;
    protected Point2D position;
    protected int duration;

    //common, rare, epic
    protected String rarity;

    protected double power;

    protected Potion(double x, double y, String imagePath, String name, String rarity, int duration) {
        super(x, y, 50, "potions/" + imagePath, 0, 0);
        this.name = name;
        this.rarity = rarity;
        this.duration = duration;
    }

    public void dropPotion() {
        RoomManager.getCurrent().addCollectable(this, Player.getInstance().getPosition());
    }

    public abstract void setPower();
    public abstract void use(Sprite s);
    public abstract void remove(Sprite s);
}
