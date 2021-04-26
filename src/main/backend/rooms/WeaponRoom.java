package main.backend.rooms;

import main.backend.weapons.Weapon;
import main.backend.weapons.WeaponManager;
import main.backend.Controller;
import main.backend.characters.Bag;
import javafx.geometry.Point2D;

public class WeaponRoom extends Room {
    private Weapon generatedWeapon;

    public WeaponRoom() {
        super("empty");
        generatedWeapon = WeaponManager.create("key", 400, 400, true, 2.5, 10, -1);
        collectables.add(generatedWeapon);
    }

    public void trigger(Weapon w) {
        if (w == generatedWeapon && generatedWeapon != null) {
            generatedWeapon = null;
            createEnemies(8, 4);
            collectables.remove(w);
            Controller.destroyImage(w.getImage());
        }

    }
    boolean first = true;
    public void onClear() {
        if (this.isClear() && first) {
            first = false;
            Bag bag = new Bag(400, 400, 75, 75, "bag.png", 0, 0);
            this.addCollectable(bag, new Point2D(400, 400));
            collectables.add(bag);
        }
    }


}
