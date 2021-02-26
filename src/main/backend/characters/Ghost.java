package main.backend.characters;

import main.backend.weapons.Weapon;
import main.backend.weapons.WeaponManager;

public class Ghost extends Enemy {
    public Ghost(double x, double y, int id) {
        super(x, y, 1, 1, 10, 0, null, "ghost", "char2.gif", id);
        Weapon weapon = WeaponManager.create("dagger", x, y, false);
        this.mainWeapon = weapon;
    }
}
