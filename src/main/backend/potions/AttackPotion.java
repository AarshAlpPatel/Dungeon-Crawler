package main.backend.potions;

import main.backend.characters.Player;

public class AttackPotion extends Potion {
    //number of swings it's good for
    private int time;

    public AttackPotion(String rarity) {
        super("attack.png", 1, "Attack Potion", rarity);
        this.time = 5; //adjust based on rarity too?
        setPower();
    }

    @Override
    public void use() {
        Player.getInstance().getMainWeapon().setDamage(
                Player.getInstance().getMainWeapon().getDamage() + power);
    }

    public void resetDamage() {
        Player.getInstance().getMainWeapon().setDamage(
                Player.getInstance().getMainWeapon().getDamage() - power
        );
    }

    @Override
    public void setPower() {
        switch (rarity) {
            case "common" :
                this.power = 5;
            case "rare" :
                this.power = 10;
            case "epic" :
                this.power = 20;
        }
    }
}
