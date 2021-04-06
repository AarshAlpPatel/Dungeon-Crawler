package main.backend.potions;

import main.backend.characters.Upgradeable;

public class HealthPotion extends Potion {
    public HealthPotion(double x, double y, String rarity) {
        super(x, y, "health.png", "Health Potion", rarity);
        setPower();
    }

    @Override
    public Upgradeable use(Upgradeable obj) {
        obj.changeHealth(power);
        return obj;
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
}
