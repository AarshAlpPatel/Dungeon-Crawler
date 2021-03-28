package main.backend.characters;

import main.backend.weapons.Weapon;
import main.backend.weapons.WeaponManager;

public class Ghost extends Enemy {
    public Ghost(double x, double y, int id) {
        super(x, y, 1, 1, 100, 0, null, "ghost", "ghost.png", id, 50, "dead_ghost.png");
        Weapon weapon = WeaponManager.create("dagger", x, y, false, 4, 8, 180);
        this.mainWeapon = weapon;
    }
}
