package main.backend.characters;

import main.backend.weapons.Weapon;
import main.backend.weapons.WeaponManager;

public class Bat extends Enemy {
    public Bat(double x, double y, int id) {
        super(x, y, 1, 1, 100, 0, null, "bat", "bat/base/bat-base.gif", id, 50, "bat/dead/bat-dead.png");
        Weapon weapon = WeaponManager.create("dagger", x, y, false, 4, 8, 180);
        this.mainWeapon = weapon;
    }
}
