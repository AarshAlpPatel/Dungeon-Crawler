package main.backend.characters;

import main.backend.weapons.Weapon;
import main.backend.weapons.WeaponManager;
import java.util.Random;

public class Snake extends Enemy {

    public Snake(double x, double y, int id) {
        super(x, y, 1, 1, 100, 0, null, "snake",
                "snake/base/snake_base.gif", id, 50, "snake/dead/snake-dead-frame0.png");
        Random rand = new Random();
        int index = rand.nextInt(3);
        Weapon weapon = WeaponManager.create(weapons[index], x, y, false, 4, 8, 180);
        this.mainWeapon = weapon;
    }
}
