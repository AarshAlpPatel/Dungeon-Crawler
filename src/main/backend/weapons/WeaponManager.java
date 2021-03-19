package main.backend.weapons;

import java.util.HashMap;

public class WeaponManager {
    private static Weapon[] weapons;
    private static int weaponsCounter = 0;

    public WeaponManager(int weaponsCount) {
        weapons = new Weapon[weaponsCount];
    }

    public static Weapon create(String weapon, double x, double y, boolean dropped,
                                double translateX, double translateY) {
        if (weapons != null && weaponsCounter >= weapons.length) {
            throw new RuntimeException("Creating too many weapons. You initialized WeaponManager wrong.");
        }

        Weapon newWeapon = null;
        if (weapon.equals("dagger")) {
            newWeapon = new Dagger(x, y, weaponsCounter, dropped, translateX, translateY);
        } else if (weapon.equals("spear")) {
            newWeapon = new Spear(x, y, weaponsCounter, dropped, translateX, translateY);
        } else if (weapon.equals("axe")) {
            newWeapon = new Axe(x, y, weaponsCounter, dropped, translateX, translateY);
        } else {
            throw new IllegalArgumentException("Weapon name not recognized");
        }

        return newWeapon;
    }

    public void addWeapon(Weapon weapon) {
        weapons[weaponsCounter] = weapon;
    }
}
