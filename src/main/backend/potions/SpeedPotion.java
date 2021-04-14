package main.backend.potions;

import main.backend.characters.Sprite;

public class SpeedPotion extends Potion {
    public SpeedPotion(double x, double y, String rarity, int duration) {
        super(x, y, "speed.png", "Speed Potion", rarity, duration);
        setPower();
    }

    @Override
    public void setPower() {
        switch (rarity) {
        case "common" :
            this.power = 1.0;
            break;
        case "rare" :
            this.power = 2.0;
            break;
        case "epic" :
            this.power = 3.0;
            break;
        default:
            throw new RuntimeException("Invalid rarity for speed potion");
        }
    }

    @Override
    public void use(Sprite s) {
        s.changeSpeed(power);
        
    }

    @Override
    public void remove(Sprite s) {
        s.changeSpeed(-power);
    }
}
