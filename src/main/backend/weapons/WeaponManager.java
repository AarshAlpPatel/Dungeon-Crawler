package main.backend.weapons;

public class WeaponManager {
    private Weapon[] weapons;
    private int weaponsCounter = 0;

    public WeaponManager(int weaponsCount) {
        this.weapons = new Weapon[weaponsCount];
    }

    public static Weapon create(String weapon, double x, double y, boolean dropped,
                                double translateX, double translateY, double attackInterval) {
        Weapon newWeapon = null;
        if (weapon.equals("dagger")) {
            newWeapon = new Dagger(x, y, dropped, translateX, translateY, attackInterval);
        } else if (weapon.equals("spear")) {
            newWeapon = new Spear(x, y, dropped, translateX, translateY, attackInterval);
        } else if (weapon.equals("axe")) {
            newWeapon = new Axe(x, y, dropped, translateX, translateY, attackInterval);
        } else {
            throw new IllegalArgumentException("Weapon name not recognized");
        }

        return newWeapon;
    }

    public void addWeapon(Weapon weapon) {
        if (weapons != null && weaponsCounter >= weapons.length) {
            throw new RuntimeException("Creating too many weapons. You initialized WeaponManager wrong.");
        }
        weapon.setID(weaponsCounter);
        weapons[weaponsCounter] = weapon;
    }
}
