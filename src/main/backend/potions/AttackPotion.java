package main.backend.potions;

import main.backend.characters.Player;

public class AttackPotion extends Potion {

    public AttackPotion(double x, double y, String rarity) {
        super(x, y, "attack.png", "Attack Potion", rarity);
        setPower();
    }

    @Override
    public double getAttackMultiplier() {
        return obj.getAttackMultiplier() + this.power;
    }

    public void setPower() {
        switch (rarity) {
            case "common" :
                this.power = 5;
                break;
            case "rare" :
                this.power = 10;
                break;
            case "epic" :
                this.power = 20;
                break;
            default:
                throw new RuntimeException("Invalid rarity for attack potion");
        }
    }
}
