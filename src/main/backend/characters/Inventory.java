package main.backend.characters;

import main.backend.exceptions.TooManyPotions;
import main.backend.exceptions.TooManyWeapons;
import main.backend.potions.Potion;
import main.backend.weapons.Weapon;

public class Inventory {
    private int sizeW;
    private int sizeP;

    public Inventory(Weapon weapon) {
        addWeapon(weapon);
    }

    //backingArray for weapons
    Weapon[] weapons = new Weapon[2];

    //backingArray for potions
    Potion[] potions = new Potion[5];

    public void addPotion(Potion potion) {
        if (sizeP == 5) { //this means its full of weapons
            throw new TooManyPotions("Too many potions being added. Please drop one.");
        } else {
            potions[sizeP] = potion;
            sizeP++;
        }
    }

    public Potion removePotion() {
        if (sizeP == 0) {
            throw new IllegalArgumentException("No potions found.");
        }
        Potion removed = potions[sizeP - 1];
        potions[sizeP - 1] = null;
        sizeP--;
        return removed;
    }

    public Potion removePotion(Potion potion) {
        if (sizeP == 0) {
            throw new IllegalArgumentException("No potions found.");
        }
        if (potion == null) {
            throw new IllegalArgumentException("Cannot add null potions.");
        }
        int i = 0;
        while (i < sizeP) {
            if (potions[i].equals(potion))
                break;
            i++;
        }

        Potion removed = potions[i];
        potions[i] = null;
        sizeP--;
        return removed;
    }

    public void addWeapon(Weapon weapon) {
        if (weapon == null) {
            throw new IllegalArgumentException("Cannot add null weapons.");
        }
        if (sizeW == 2) { //this means its full of weapons
            throw new TooManyWeapons("Too many weapons being added. Please drop one.");
        }
        weapons[sizeW] = weapon;
        sizeW++;
    }

    public Weapon removeWeapon() {
        if (sizeW == 0) {
            throw new IllegalArgumentException("No weapons found.");
        }
        Weapon removed = weapons[sizeW - 1];
        weapons[sizeW - 1] = null;
        sizeW--;
        return removed;
    }

    public Weapon removeWeapon(Weapon weapon) {
        if (sizeW == 0) {
            throw new IllegalArgumentException("No weapons found.");
        }
        if (weapon == null) {
            throw new IllegalArgumentException("Cannot remove null weapons.");
        }
        int i = 0;
        while (i < sizeW) {
            if (weapons[i].equals(weapon))
                break;
            i++;
        }

        Weapon removed = weapons[i];
        weapons[i] = null;
        sizeW--;
        return removed;
    }

    public Weapon getWeapon(int i) {
        return weapons[i];
    }

    public Potion getPotion(int i) {
        return potions[i];
    }
}
