package main.backend.potions;

import main.backend.characters.Sprite;

public class HealthPotion extends Potion {
    public HealthPotion(double x, double y, String rarity, int duration) {
        super(x, y, "health.png", "Health Potion", rarity, duration);
        setPower();
    }

    @Override
    public void setPower() {
        switch (rarity) {
        case "common" :
            this.power = 20;
            break;
        case "rare" :
            this.power = 40;
            break;
        case "epic" :
            this.power = 60;
            break;
        default:
            throw new RuntimeException("Invalid rarity for health potion");
        }
    }

    @Override
    public void use(Sprite s) {
        s.changeHealth(power);
    }

    @Override
    public void remove(Sprite s) {
        //do nothing
    }
}
