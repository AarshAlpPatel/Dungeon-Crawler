package main.backend.characters;

public class Enemy extends Sprite {
    protected int id;

    @Override
    public void destroy() {
        super.destroy();
        EnemyManager.destroy(id);
    }

    public void attack(Player player) {
        double dist = getDistance(player);
        if(dist < weapon.getRange()) {
            this.hit(player);
        }
        this.move((player.getX()-this.x)/dist, (player.getY()-this.y)/dist);
    }
}
