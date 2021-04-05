package main.backend.potions;

import main.backend.characters.Player;

public class HealthPotion extends Potion {
    public HealthPotion(String rarity) {
        super("health.png", 1, "Health Potion", rarity);
    }

    @Override
    public void use() {
        Player.getInstance().setHealth(Player.getInstance().getHealth() + power);
    }

    @Override
    public void setPower() {
        switch (rarity) {
            case "common" :
                this.power = 20;
            case "rare" :
                this.power = 40;
            case "epic" :
                this.power = 60;
        }
    }
}
