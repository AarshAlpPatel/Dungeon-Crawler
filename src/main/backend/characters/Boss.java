package main.backend.characters;

import java.util.Random;

import main.backend.weapons.Weapon;
import main.backend.weapons.WeaponManager;

public class Boss extends Enemy {
    public Boss(double x, double y, int id) {
        super(x, y, 1, 1, 100, 0, null, "boss",
                "boss/boss.png", id, 50, "boss/boss.png");
        Random rand = new Random();
        int index = rand.nextInt(3);
        Weapon weapon = WeaponManager.create(weapons[0], x, y, false, 4, 8, 180);
        this.mainWeapon = weapon;
    }
}
