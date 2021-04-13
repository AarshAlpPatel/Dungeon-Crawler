package main.backend.potions;
import main.backend.characters.Sprite;

public class AttackPotion extends Potion {

    public AttackPotion(double x, double y, String rarity, int duration) {
        super(x, y, "attack.png", "Attack Potion", rarity, duration);
        setPower();
    }

    public void setPower() {
        switch (rarity) {
            case "common" -> this.power = 5;
            case "rare" -> this.power = 10;
            case "epic" -> this.power = 20;
            default -> throw new RuntimeException("Invalid rarity for attack potion");
        }
    }

    @Override
    public void use(Sprite s) {
        s.changeAttackMultiplier(power);
    }

    @Override
    public void remove(Sprite s) {
        s.changeAttackMultiplier(-power);
    }
}
