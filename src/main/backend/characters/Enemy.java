package main.backend.characters;

import main.backend.weapons.Weapon;

public class Enemy extends Sprite {
    protected int id;

    protected Enemy(double x, double y, double attackMultiplier, double speed, int health,
                    int regeneration, Weapon weapon, String name, String imagePath, int id, int maxsize) {
        super(x, y, attackMultiplier, speed, health, regeneration, weapon, name, "enemies/" + imagePath, maxsize);
        this.id = id;
    }
}
