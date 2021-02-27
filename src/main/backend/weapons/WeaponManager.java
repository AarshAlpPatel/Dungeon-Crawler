package main.backend.weapons;

import java.util.HashMap;

import main.frontend.GameManager;

public class WeaponManager {
    private static HashMap<Integer, Weapon> weapons = new HashMap<>();
    private static int weaponsCounter = 0;

    public static Weapon create(String weapon, double x, double y, boolean dropped) {
        Weapon newWeapon = null;
        if(weapon.equals("dagger")) {
            newWeapon = new Dagger(x, y, weaponsCounter, dropped);
        } else if (weapon.equals("spear")) {
            newWeapon = new Spear(x, y, weaponsCounter, dropped);
        } else if (weapon.equals("axe")) {
            newWeapon = new Axe(x, y, weaponsCounter, dropped);
        } else {
            throw new IllegalArgumentException("Weapon name not recognized");
        }
        weapons.put(weaponsCounter, newWeapon);
        ++weaponsCounter;
        return newWeapon;
    }
    public static void destroy(int id) {
        GameManager.destroyImage(weapons.get(id).getImage());
        weapons.remove(id);
    }
}
