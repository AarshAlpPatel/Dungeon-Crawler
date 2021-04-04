package main.backend.characters;

import main.backend.potions.Potion;
import main.backend.weapons.Weapon;

public class Inventory {
    //backingArray for weapons
    Weapon[] weapons = new Weapon[2];

    public boolean isFullW() {
        for (Weapon weapon : weapons) {
            if (weapon == null) {
                return false;
            }
        }
        return true;
    }

    //backingArray for potions
    Potion[] potions = new Potion[5];

    public boolean isFullP() {
        for (Potion potion : potions) {
            if (potion == null) {
                return false;
            }
        }
        return true;
    }

    public void addPotion(Potion potion) {
        if (!isFullP()) {

        }
    }

    public Potion removePotion() {
        return null;
    }

    public Weapon removePotion(Potion potion) {
        return null;
    }

    public void addWeapon(Weapon weapon) {

    }

    public Weapon removeWeapon() {
        return null;
    }

    public Weapon removeWeapon(Weapon weapon) {
        return null;
    }

    public Weapon getWeapon(int i) {
        return weapons[i];
    }

    public Potion getPotion(int i) {
        return potions[i];
    }
}
