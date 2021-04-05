package main.backend.characters;

public interface Upgradeable {
    default double getAttackMultiplier() { return 1; };
    default double getSpeed() { return 1; };
    public void changeHealth(double hdiff);
}
