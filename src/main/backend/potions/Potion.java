package main.backend.potions;

import javafx.scene.image.ImageView;

public abstract class Potion {
    //image of potion
    protected ImageView image;
    protected String name;
    protected int count;

    //health or attack power
    protected int power;

    //common, rare, epic
    protected String rarity;

    public Potion(String imagePath, int count, String name, String rarity) {
        image = new ImageView("main/design/images/" + imagePath);
        this.name = name;
        this.count = count;
        this.rarity = rarity;
        setPower();
    }

    public ImageView getImage() {
        return this.image;
    }

    public abstract void use();

    public abstract void setPower();
}
