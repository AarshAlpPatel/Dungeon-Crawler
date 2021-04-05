package main.backend.inventory;

import java.util.ArrayList;

import main.backend.exceptions.TooManyPotions;
import main.backend.exceptions.TooManyWeapons;
import main.backend.potions.Potion;
import main.backend.weapons.Weapon;

public class Inventory {
    private static final int MAX_WEAPONS = 2;
    private static final int MAX_POTIONS = 5;

    //backingArray for weapons
    private ArrayList<Weapon> weapons;

    //backingArray for potions
    private ArrayList<Potion> potions;

    public void addPotion(Potion potion) {
        if (potion == null) {
            throw new IllegalArgumentException("Cannot add null weapons.");
        }
        if (potions.size() >= MAX_POTIONS) { //this means its full of potions
            throw new TooManyPotions("Too many potions being added. Please drop one.");
        } else {
            potions.add(potion);
        }
    }

    public Potion removePotion(int index) {
        if (index >= potions.size()) {
            throw new IllegalArgumentException("No potions found.");
        }
        Potion removed = potions.get(index);
        potions.remove(index);
        return removed;
    }

    public void addWeapon(Weapon weapon) {
        if (weapon == null) {
            throw new IllegalArgumentException("Cannot add null weapons.");
        }
        if (weapons.size() > MAX_WEAPONS) { //this means its full of weapons
            throw new TooManyWeapons("Too many weapons being added. Please drop one.");
        }
        weapons.add(weapon);
    }

    public Weapon removeWeapon(int index) {
        if (index >= weapons.size()) {
            throw new IllegalArgumentException("No weapons found.");
        }
        Weapon removed = weapons.get(index);
        weapons.remove(index);
        return removed;
    }

    public Weapon getWeapon(int i) {
        return weapons.get(i);
    }

    public Potion getPotion(int i) {
        return potions.get(i);
    }
}
