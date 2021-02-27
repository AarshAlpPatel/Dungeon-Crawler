package main.backend;

import main.backend.characters.*;
import main.backend.weapons.*;

public class Controller {
    public static String difficultyLevel = "Easy";
    
    public static Player createPlayer(double x, double y, String name, String weaponName,
                                      String imagePath) {
        Player player = Player.getInstance();
        Weapon weapon = WeaponManager.create(weaponName, x, y, false);
        player.setName(name);
        player.setWeapon(weapon);
        player.setImage(imagePath, 100);
        return player;
    }
}
