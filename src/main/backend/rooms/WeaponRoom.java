package main.backend.rooms;

import main.backend.weapons.Weapon;
import main.backend.weapons.WeaponManager;
import main.backend.Controller;

public class WeaponRoom extends Room {
    private Weapon generatedWeapon;

    public WeaponRoom() {
        super("empty");
        generatedWeapon = WeaponManager.create("key", 400, 400, true, 2.5, 10, -1);
        collectables.add(generatedWeapon);
    }

    public void trigger(Weapon w) {
        if (w == generatedWeapon && generatedWeapon != null) {
            generatedWeapon = null;
            createEnemies(8, 4);
            Controller.destroyImage(w.getImage());
        }
    }
}
