package main.backend.potions;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import main.backend.characters.Sprite;

public class PotionManager {
    private static String[] allPotionTypes = {"attack", "health", "speed"};
    private static String[] allRarityTypes = {"common", "rare", "epic"};

    private ArrayList<Potion> potions;
    private Sprite s;

    public PotionManager(Sprite s) {
        this.potions = new ArrayList<>();
        this.s = s;
    }

    private static String getRandomType() {
        return allPotionTypes[(int)(Math.random()*allPotionTypes.length)];
    }

    private static String getRandomRarity() {
        return allRarityTypes[(int)(Math.random()*allRarityTypes.length)];
    }

    public static Potion createRandom(double x, double y, int duration) {
        return create(getRandomType(), getRandomRarity(), x, y, duration);
    }

    public static Potion create(String name, String rarity, double x, double y, int duration) {
        if (name.equals("attack")) {
            return new AttackPotion(x, y, rarity, duration);
        } else if (name.equals("health")) {
            return new HealthPotion(x, y, rarity, duration);
        } else if (name.equals("speed")) {
            return new SpeedPotion(x, y, rarity, duration);
        } else {
            throw new IllegalArgumentException("Weapon name not recognized");
        }
    }

    public void setSprite(Sprite s) {
        this.s = s;
    }

    public void add(Potion p) {
        this.potions.add(p);
        if (s != null) {
            p.use(s);
            new Timer().schedule( 
                new TimerTask() {
                    @Override
                    public void run() {
                        p.remove(s);
                    }
                }, 
                p.duration
            );
        }
    }

    public void remove(Potion p) {
        this.potions.remove(p);
        if (s != null) {
            p.remove(s);
        }
    }
}
