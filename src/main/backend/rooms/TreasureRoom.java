package main.backend.rooms;

import main.backend.characters.Player;
import main.backend.potions.PotionManager;
import main.backend.weapons.WeaponManager;

/**
 * Mainly for testing purposes, not needed in final project!
 */
public class TreasureRoom extends Room {
    public TreasureRoom() {
        super("empty");
        collectables.add(PotionManager.create("health", "common", 200, 200, Player.POTION_DURATION));
        collectables.add(PotionManager.create("attack", "common", 400, 400, Player.POTION_DURATION));
        collectables.add(PotionManager.create("speed", "common", 600, 600, Player.POTION_DURATION));
        collectables.add(WeaponManager.create("dagger", 200, 400, true, 2.5, 10, -1));
        collectables.add(WeaponManager.create("axe", 600, 200, true, 2.5, 10, -1));
    }
}
