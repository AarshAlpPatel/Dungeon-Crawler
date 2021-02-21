package main.backend.weapons;
import java.util.HashMap;

public class WeaponManager {
    private static HashMap<Integer, Weapon> weapons = new HashMap<>();
    private static int weaponsCounter = 0;

    public static void destroy(int id) {
        weapons.remove(id);
    }
}
