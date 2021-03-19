package main.backend.characters;

import main.backend.weapons.Weapon;
import main.backend.weapons.WeaponManager;

public class Ghost extends Enemy {
    public Ghost(double x, double y, int id) {
        super(x, y, 1, 1, 1, 0, null, "ghost", "ghost.png", id, 50);
        Weapon weapon = WeaponManager.create("dagger", x, y, false);
        this.mainWeapon = weapon;
    }

    @Override
    public void destroy() {
        setImage("enemies/dead_ghost.png");
        super.destroy();
    }
}
