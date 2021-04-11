package main.backend.rooms;

import main.backend.characters.Player;
import main.backend.potions.PotionManager;

/**
 * Mainly for testing purposes, not needed in final project!
 */
public class TreasureRoom extends Room {
    public TreasureRoom() {
        super("empty");
        collectables.add(PotionManager.create("health", "common", 200, 200, Player.POTION_DURATION));
        collectables.add(PotionManager.create("attack", "common", 400, 400, Player.POTION_DURATION));
        collectables.add(PotionManager.create("speed", "common", 600, 600, Player.POTION_DURATION));
    }
}
