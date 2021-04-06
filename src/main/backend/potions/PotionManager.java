package main.backend.potions;

public class PotionManager {
    private Potion[] potions;
    private int potionCounter = 0;

    public PotionManager(int potionCount) {
        this.potions = new Potion[potionCount];
    }

    public static Potion create(String name, double x, double y) {
        String rarity = Potion.getRandomRarity();
        if (name.equals("attack")) {
            return new AttackPotion(x, y, rarity);
        } else if (name.equals("health")) {
            return new HealthPotion(x, y, rarity);
        } else if (name.equals("speed")) {
            return new SpeedPotion(x, y, rarity);
        } else {
            throw new IllegalArgumentException("Weapon name not recognized");
        }
    }


//
//    public void addWeapon(Weapon weapon) {
//        if (weapons != null && weaponsCounter >= weapons.length) {
//            throw new RuntimeException("Creating too many weapons. "
//                    + "You initialized WeaponManager wrong.");
//        }
//        weapon.setID(weaponsCounter);
//        weapons[weaponsCounter] = weapon;
//    }
}
