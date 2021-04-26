package main.backend.inventory;

import java.util.ArrayList;
import java.util.List;

import main.backend.collidables.Collidable;
import main.backend.exceptions.TooManyPotions;
import main.backend.exceptions.TooManyWeapons;
import main.backend.potions.Potion;
import main.backend.rooms.RoomManager;
import main.backend.rooms.WeaponRoom;
import main.backend.weapons.Weapon;
import main.backend.characters.Bag;
import main.backend.characters.Player;

public class Inventory {
    private static final int MAX_WEAPONS = 2;
    private static final int MAX_POTIONS = 5;

    //backingArray for weapons
    private ArrayList<Weapon> weapons;

    //backingArray for potions
    private ArrayList<Potion> potions;

    private int numWeapons;

    private int numPotions;

    public Inventory() {
        weapons = new ArrayList<>(MAX_WEAPONS);
        potions = new ArrayList<>(MAX_POTIONS);
    }

    public boolean addCollectable(Collidable c) {
        try {
            if (c instanceof Weapon) {
                if (RoomManager.getCurrent() instanceof WeaponRoom) {
                    WeaponRoom r = (WeaponRoom) RoomManager.getCurrent();
                    r.trigger((Weapon) c);
                } else {
                    addWeapon((Weapon) c);
                }
            } else if (c instanceof Potion) {
                addPotion((Potion) c);
                printInventory();
            } else if (c instanceof Bag){
                Player.getInstance().setCash(Player.getInstance().getCash() + 200);
                System.out.println(Player.getInstance().getCash());
                //add line to update room's cash label
            } else {
                throw new IllegalArgumentException("Invalid collectable");
            }
            return true;
        } catch (TooManyWeapons e) {
            System.out.println("Cannot add weapon, inventory full");
            return false;
        } catch (TooManyPotions e) {
            System.out.println("Cannot add potion, inventory full");
            return false;
        }
    }

    private void printInventory() {
        System.out.print("\nWeapons: ");
        for (int i = 0; i < 2; i++) {
            System.out.print(getWeapon(i) + ", ");
        }
        System.out.println("\nPotions: ");
        for (int i = 0; i < 5; i++) {
            System.out.print(getPotion(i) + ", ");
        }
    }

    public void addPotion(Potion potion) {
        if (potion == null) {
            throw new IllegalArgumentException("Cannot add null weapons.");
        }
        if (potions.size() >= MAX_POTIONS) { //this means its full of potions
            throw new TooManyPotions("Too many potions being added. Please drop one.");
        }
        potions.add(potion);
    }

    public void setPotion(Potion potion, int index) {
        if (potion == null) {
            throw new IllegalArgumentException("Cannot add null potions.");
        }
        if (index == MAX_POTIONS) {
            throw new TooManyWeapons("You tried adding a sixth potion. Tough luck kid.");
        }
        potions.set(index, potion);
    }

    public Potion dropPotion(int index) {
        return dropPotion(potions.get(index));
    }

    public Potion dropPotion(Potion potion) {
        if (potion == null) {
            throw new IllegalArgumentException("No null potions available.");
        }
        Potion removed = null;
        int i = 0;
        while (i < MAX_POTIONS) {
            if (potions.get(i).equals(potion)) {
                removed = potions.remove(i);
                break;
            }
            i++;
        }
        printInventory();
        return removed;
    }

    public void addWeapon(Weapon weapon) {
        if (weapon == null) {
            throw new IllegalArgumentException("Cannot add null weapons.");
        }
        if (weapons.size() >= MAX_WEAPONS) { //this means its full of weapons
            throw new TooManyWeapons("Too many weapons being added. Please drop one.");
        }
        weapons.add(weapon);
    }

    public void setWeapon(Weapon weapon, int index) {
        if (weapon == null) {
            throw new IllegalArgumentException("Cannot add null weapons.");
        }
        if (index == MAX_POTIONS) {
            throw new TooManyWeapons("You tried adding a third weapon. Tough luck kid.");
        }
        weapons.set(index, weapon);
    }

    public Weapon dropWeapon(int index) {
        return dropWeapon(weapons.get(index));
    }

    public Weapon dropWeapon(Weapon weapon) {
        if (weapon == null) {
            throw new IllegalArgumentException("No null weapons available.");
        }
        Weapon removed = null;
        int i = 0;
        while (i < MAX_WEAPONS) {
            if (weapons.get(i).equals(weapon)) {
                removed = weapons.remove(i);
                break;
            }
            i++;
        }
        return removed;
    }

    public int getNumWeapons() {
        numWeapons = weapons.size();
        return numWeapons;
    }

    public int getNumPotions() {
        numPotions = potions.size();
        return numPotions;
    }

    public Weapon getWeapon(int i) {
        return (i < weapons.size()) ? weapons.get(i) : null;
    }

    public Potion getPotion(int i) {
        return (i < potions.size()) ? potions.get(i) : null;
    }

    public boolean isEmpty() {
        return weapons.isEmpty() && potions.isEmpty();
    }

    public List<Weapon> getWeapons() {
        return this.weapons;
    }

    public List<Potion> getPotions() {
        return this.potions;
    }
}
