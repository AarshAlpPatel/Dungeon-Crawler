package main.backend.rooms;

import main.backend.characters.Player;
import main.backend.potions.PotionManager;

public class PotionRoom extends Room {
    public PotionRoom() {
        super("hard");
        collectables.add(PotionManager.createRandom(400, 400, Player.POTION_DURATION));
    }
}