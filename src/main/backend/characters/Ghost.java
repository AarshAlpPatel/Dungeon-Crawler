package main.backend.characters;

import main.backend.weapons.Weapon;
import main.backend.weapons.WeaponManager;
import java.util.Random;

public class Ghost extends Enemy {
    public Ghost(double x, double y, int id) {
        super(x, y, 1, 1, 100, 0, null, "ghost",
                "ghost/base/ghost_base.gif", id, 50, "ghost/dead/ghost_dead.png");
        Random rand = new Random();
        int index = rand.nextInt(3);
        Weapon weapon = WeaponManager.create(weapons[index], x, y, false, 4, 8, 180);
        this.mainWeapon = weapon;
    }
}
