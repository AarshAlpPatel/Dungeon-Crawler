package main.backend.characters;

import main.backend.weapons.Weapon;
import main.backend.weapons.WeaponManager;

public class Snake extends Enemy {
    public Snake (double x, double y, int id) {
        super(x, y, 1, 1, 100, 0, null, "snake", "snake/base/snake_base.gif", id, 50, "snake/dead/snake-dead-frame0.png");
        Weapon weapon = WeaponManager.create("dagger", x, y, false, 4, 8, 180);
        this.mainWeapon = weapon;
    }
}
