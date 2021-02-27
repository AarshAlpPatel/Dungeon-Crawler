package main.backend.characters;

import main.backend.weapons.Weapon;

public class Enemy extends Sprite {
    protected int id;

    protected Enemy(double x, double y, double attackMultiplier, double speed, int health,
                    int regeneration, Weapon weapon, String name, String imagePath, int id) {
        super(x, y, attackMultiplier, speed, health, regeneration, weapon, name, imagePath, 100);
        this.id = id;
    }

    @Override
    public void destroy() {
        EnemyManager.destroy(id);
        super.destroy();
    }

    public void attack(Player player) {
        double dist = getDistance(player);
        if(dist < this.mainWeapon.getRange()) {
            this.hit(player);
        }
        this.move((player.getX()-this.x)/dist, (player.getY()-this.y)/dist);
    }
}
