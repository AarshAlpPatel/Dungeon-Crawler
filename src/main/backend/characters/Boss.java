package main.backend.characters;

import java.util.Random;

import main.backend.weapons.Weapon;
import main.backend.weapons.Axe;

public class Boss extends Enemy {
    public Boss(double x, double y, int id) {
        super(x, y, 3, 1, 500, 0, null, "boss",
                "boss/boss.gif", id, 100, "boss/rip.png");
        Weapon weapon = new Axe(x, y, false, 4, 8, 180);
        this.mainWeapon = weapon;
    }
}
