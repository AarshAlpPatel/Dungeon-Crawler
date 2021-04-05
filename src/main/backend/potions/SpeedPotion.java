package main.backend.potions;

public class SpeedPotion extends Potion {
    public SpeedPotion(double x, double y, String rarity) {
        super(x, y, "speed.png", "Speed Potion", rarity);
        setPower();
    }

    @Override
    public double getSpeed() {
        return obj.getSpeed() + this.power;
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
}
