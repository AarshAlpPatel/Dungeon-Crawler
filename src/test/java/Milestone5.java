package test.java;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import junit.framework.TestCase;
import main.backend.Controller;
import main.backend.characters.Enemy;
import main.backend.characters.Player;
import main.backend.exceptions.IllegalDropException;
import main.backend.potions.AttackPotion;
import main.backend.potions.HealthPotion;
import main.backend.potions.SpeedPotion;
import main.backend.rooms.Door;
import main.backend.rooms.Room;
import main.backend.rooms.RoomManager;
import main.backend.rooms.TreasureRoom;
import main.backend.weapons.Dagger;
import main.backend.weapons.Spear;
import main.backend.weapons.Weapon;
import main.frontend.GameManager;
import main.frontend.InventoryScreen;
import main.frontend.MainScreen;
import main.frontend.WelcomeScreen;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;

import static junit.framework.TestCase.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.api.FxToolkit.registerPrimaryStage;
import static org.testfx.api.FxToolkit.setupApplication;

public class Milestone5 extends ApplicationTest {
    private Stage stage;
    private static FxRobot robot;

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        stage.setScene(WelcomeScreen.getScene());
        stage.show();
    }

    @BeforeAll
    public static void setupSpec() throws Exception {
        robot = new FxRobot();
        registerPrimaryStage();
    }

    @BeforeEach
    public void setUp() throws Exception {
        GameManager.unpauseGameLoop();
        setupApplication(MainScreen.class);
    }

    @Test
    public void testInventorySetup() {
        startGame();

        assertEquals(1, Player.getInstance().getInventory().getWeapons().size());
        assertTrue(Player.getInstance().getInventory().getWeapon(0) instanceof Spear);
        assertTrue(Player.getInstance().getInventory().getPotions().isEmpty());
    }

    @Test
    public void testInventoryScreen() {
        goToInventory();

        //verifies all slots are visible and main weapon is there
        verifyThat("#Weapon1", NodeMatchers.isVisible());
        verifyThat("#Weapon2", NodeMatchers.isVisible());
        verifyThat("#Item1", NodeMatchers.isVisible());
        verifyThat("#Item2", NodeMatchers.isVisible());
        verifyThat("#Item3", NodeMatchers.isVisible());
        verifyThat("#Item4", NodeMatchers.isVisible());
        verifyThat("#Item5", NodeMatchers.isVisible());
        verifyThat(Player.getInstance().getInventory().getWeapon(0).getRawImage(),
                NodeMatchers.isVisible());
    }

    @Test
    public void testSelectionAndDeselection() {
        goToInventory();

        clickOn("#Weapon1");
        assertEquals("Weapon1", InventoryScreen.getSelectedID());
        assertEquals(0.5, InventoryScreen.getSelectedRectangle().getOpacity());
        moveTo("#Weapon2");
        //makes sure the slot stays selected when mouse moves away
        assertEquals(0.5, InventoryScreen.getSelectedRectangle().getOpacity());
        clickOn("#Weapon1");
        assertNull(InventoryScreen.getSelected());
    }

    @Test
    public void testWeaponPickUp() {
        pickUpWeapon();
        assertNotNull(Player.getInstance().getInventory().getWeapon(1));
        assertTrue(Player.getInstance().getInventory().getWeapon(1) instanceof Dagger);
        press(KeyCode.H);
        release(KeyCode.H);
        verifyThat(Player.getInstance().getInventory().getWeapon(1).getRawImage(),
                NodeMatchers.isVisible());
    }

    @Test
    public void testSwitch() {
        Door dirTraveled = pickUpWeapon();

        assertNotNull(Player.getInstance().getInventory().getWeapon(1));
        press(KeyCode.H);
        release(KeyCode.H);
        Weapon startingWeapon = Player.getInstance().getInventory().getWeapon(0);
        clickOn("#Weapon1");
        clickOn("#Weapon2");
        assertTrue(Player.getInstance().getInventory().getWeapon(0) instanceof Dagger);
        assertEquals(startingWeapon.getClass(),
                Player.getInstance().getInventory().getWeapon(1).getClass());
        press(KeyCode.H);
        release(KeyCode.H);
        Player.getInstance().setPosition(new Point2D(MainScreen.getMidX(), MainScreen.getMidY()));
        findStartRoom(dirTraveled);
        verifyThat(Player.getInstance().getInventory().getWeapon(0).getRawImage(),
                NodeMatchers.isVisible());
    }

    private void findStartRoom(Door dirTraveled) {
        switch (dirTraveled) {
            case EAST:
                goWest();
                break;
            case NORTH:
                goSouth();
                break;
            case WEST:
                goEast();
                break;
            case SOUTH:
                goNorth();
                break;
            default :
                throw new IllegalArgumentException("Unknown Direction");
        }
    }

    @Test
    public void checkForPotions() {
        startGame();
        findTreasureRoom();

        ImageView speed = new ImageView("main/design/images/potions/speed.png");
        ImageView health = new ImageView("main/design/images/potions/health.png");
        ImageView attack = new ImageView("main/design/images/potions/attack.png");

        verifyThat(speed, NodeMatchers.isVisible());
        verifyThat(health, NodeMatchers.isVisible());
        verifyThat(attack, NodeMatchers.isVisible());
    }

    @Test
    public void pickUpPotions() {
        startGame();
        findTreasureRoom();
        Player.getInstance().setPosition(new Point2D(200, 200));
        press(KeyCode.F);
        release(KeyCode.F);
        assertNotNull(Player.getInstance().getInventory().getPotion(0));
        assertTrue(Player.getInstance().getInventory().getPotion(0) instanceof HealthPotion);
        press(KeyCode.H);
        release(KeyCode.H);
        verifyThat(Player.getInstance().getInventory().getPotion(0).getRawImage(), NodeMatchers.isVisible());
        assertEquals(1, Player.getInstance().getInventory().getPotions().size());
        press(KeyCode.H);
        release(KeyCode.H);

        pickUpPotion(400, 400);
        assertNotNull(Player.getInstance().getInventory().getPotion(1));
        assertTrue(Player.getInstance().getInventory().getPotion(1) instanceof AttackPotion);
        press(KeyCode.H);
        release(KeyCode.H);
        verifyThat(Player.getInstance().getInventory().getPotion(1).getRawImage(), NodeMatchers.isVisible());
        assertEquals(2, Player.getInstance().getInventory().getPotions().size());
        press(KeyCode.H);
        release(KeyCode.H);

        pickUpPotion(600, 600);
        assertNotNull(Player.getInstance().getInventory().getPotion(2));
        assertTrue(Player.getInstance().getInventory().getPotion(2) instanceof SpeedPotion);
        press(KeyCode.H);
        release(KeyCode.H);
        verifyThat(Player.getInstance().getInventory().getPotion(2).getRawImage(), NodeMatchers.isVisible());
        assertEquals(3, Player.getInstance().getInventory().getPotions().size());
    }

    @Test
    public void testDrops() {
        pickUpWeapon();
        press(KeyCode.H);
        release(KeyCode.H);
        clickOn("#Weapon1");
        clickOn("Drop");
        assertEquals(2, Player.getInstance().getInventory().getWeapons().size()); //means not dropped
        verifyThat(Player.getInstance().getInventory().getWeapon(0).getRawImage(), NodeMatchers.isVisible());
        verifyThat("Cannot drop main weapon.", NodeMatchers.isVisible());
        clickOn("#Weapon1"); //deselect

        clickOn("#Weapon2");
        clickOn("Drop");
        assertEquals(1, Player.getInstance().getInventory().getWeapons().size()); //means dropped

        clickOn("#Weapon2");
        verifyThat("#drop", NodeMatchers.isDisabled());
    }

    @Test
    public void testPotionDrop() {
        startGame();
        findTreasureRoom();
        pickUpPotion(400, 400);
        press(KeyCode.H);
        release(KeyCode.H);
        assertEquals(1, Player.getInstance().getInventory().getPotions().size());
        clickOn("#Item1");
        clickOn("Drop");
        assertEquals(0, Player.getInstance().getInventory().getPotions().size());
        clickOn("#Item1");
        verifyThat("Drop", NodeMatchers.isDisabled());
    }

    @Test
    public void testHealthPotionUse() {
        startGame();
        getHit();
        double startingHealth = Player.getInstance().getHealth();
        Player.getInstance().setPosition(new Point2D(400, 400));
        findTreasureRoom();
        //test Health Potion
        Player.getInstance().setPosition(new Point2D(200, 200));
        pickupAndUseFirstPotion();
        TestCase.assertEquals(startingHealth + 20, Player.getInstance().getHealth());
    }

    @Test
    public void testAttackPotionUse() {
        startGame();
        findTreasureRoom();
        double startingAM = Player.getInstance().getAttackMultiplier();
        Player.getInstance().setPosition(new Point2D(400, 400));
        pickupAndUseFirstPotion();
        TestCase.assertEquals(startingAM + 5, Player.getInstance().getAttackMultiplier());
    }

    @Test
    public void testSpeedPotionUse() {
        startGame();
        findTreasureRoom();
        double startingSpeed = Player.getInstance().getThisSpeed();
        Player.getInstance().setPosition(new Point2D(600, 600));
        pickupAndUseFirstPotion();
        TestCase.assertEquals(startingSpeed + 1, Player.getInstance().getThisSpeed());
    }

    @Test
    public void testOverfill() {
        startGame();
        findEnemyRoom();
        String dirKilled = killEnemy(RoomManager.getCurrentEnemies().getEnemyCounter() - 1);
        KeyCode ktp;
        if (dirKilled.equals("North")) {
            ktp = KeyCode.W;
        } else {
            ktp = KeyCode.S;
        }

        press(ktp);
        press(ktp);
        press(ktp);
        release(ktp);

        press(KeyCode.F);
        release(KeyCode.F);
        assertEquals(2, Player.getInstance().getInventory().getNumWeapons());
        
        //make sure inventory doesnt change when trying to pick up another weapon
        String dirKilled2 = killEnemy(RoomManager.getCurrentEnemies().getEnemyCounter() - 1);
        if (dirKilled.equals("North")) {
            ktp = KeyCode.W;
        } else {
            ktp = KeyCode.S;
        }

        press(ktp);
        press(ktp);
        press(ktp);
        release(ktp);
        press(KeyCode.F);
        release(KeyCode.F);
        assertEquals(2, Player.getInstance().getInventory().getNumWeapons());
    }

    private Door pickUpWeapon() {
        startGame();

        Door dirTraveled = findEnemyRoom();
        String dir = killEnemy(RoomManager.getCurrentEnemies().getEnemyCounter() - 1);
        KeyCode kTP = dir.equals("North") ? KeyCode.W : KeyCode.S;

        press(kTP);
        press(kTP);
        press(kTP);
        release(kTP);

        press(KeyCode.F);
        release(KeyCode.F);

        return dirTraveled;
    }

    public Door findEnemyRoom() {
        Room current = RoomManager.getCurrent();
        if (!(current.getNextRoom(Door.WEST) instanceof TreasureRoom)) {
            goWest();
            return Door.WEST;
        } else if (!(current.getNextRoom(Door.NORTH) instanceof TreasureRoom)) {
            goNorth();
            return Door.NORTH;
        } else if (!(current.getNextRoom(Door.EAST) instanceof TreasureRoom)) {
            goEast();
            return Door.EAST;
        } else {
            goSouth();
            return Door.SOUTH;
        }
    }

    private void pickupAndUseFirstPotion() {
        press(KeyCode.F);
        release(KeyCode.F);
        press(KeyCode.H);
        release(KeyCode.H);
        assertTrue(InventoryScreen.getUse().isDisabled());
        clickOn("#Item1");
        assertFalse(InventoryScreen.getUse().isDisabled());
        clickOn("Use");
        press(KeyCode.H);
        release(KeyCode.H);
    }

    public void getHit() {
        Room start = RoomManager.getCurrent();
        Door dirTraveled = findEnemyRoom();
        while (Player.getInstance().getHealth() > 80)
            Player.getInstance().setPosition(RoomManager.getCurrentEnemies().getEnemies()[0].getPosition());
        //back to start
        Player.getInstance().setPosition(new Point2D(400, 400));
        switch (dirTraveled) {
            case EAST -> goWest();
            case WEST -> goEast();
            case NORTH -> goSouth();
            case SOUTH -> goNorth();
        }
    }

    public void pickUpPotion(int x, int y) {
        //Room current = RoomManager.getCurrent();
        Player.getInstance().setPosition(new Point2D(x, y));
        press(KeyCode.F);
        release(KeyCode.F);
    }

    public void findTreasureRoom() {
        Room start = RoomManager.getCurrent();
        if (start.getNextRoom(Door.NORTH) instanceof TreasureRoom) {
            goNorth();
        } else if (start.getNextRoom(Door.SOUTH) instanceof TreasureRoom) {
            goSouth();
        } else if (start.getNextRoom(Door.EAST) instanceof TreasureRoom) {
            goEast();
        } else if (start.getNextRoom(Door.WEST) instanceof TreasureRoom) {
            goWest();
        }
    }

    public void startGame() {
        clickOn("#toGame");
        clickOn("#nameField");
        type(KeyCode.N);
        clickOn("#weaponRight");
        clickOn("Start Game");
    }

    public void goToInventory() {
        startGame();
        press(KeyCode.H);
        release(KeyCode.H);
    }

    public String killEnemy(int index) {
        int numAlive = 0;
        String directionKilled;
        if (RoomManager.getCurrentEnemies().getEnemies()[index].getPosition().getY()
                < MainScreen.getMidY()) {
            Player.getInstance().setPosition(RoomManager.getCurrentEnemies().getEnemies()[index]
                    .getPosition().add(0, 100));
            directionKilled = "North";
        } else {
            Player.getInstance().setPosition(RoomManager.getCurrentEnemies().getEnemies()[index]
                    .getPosition().subtract(0, 100));
            directionKilled = "South";
        }
        while (!RoomManager.getCurrentEnemies().getEnemies()[index].isDead()) {
            clickOn(RoomManager.getCurrentEnemies().getEnemies()[index].getRawImage());
            numAlive = 0;
            for (Enemy e : RoomManager.getCurrentEnemies().getEnemies()) {
                if (!e.isDead()) {
                    numAlive++;
                }
            }
            assertSame(numAlive, RoomManager.getCurrentEnemies().getEnemyCounter());
        }
        numAlive = 0;
        for (Enemy e : RoomManager.getCurrentEnemies().getEnemies()) {
            if (!e.isDead()) {
                numAlive++;
            }
        }
        return directionKilled;
    }

    public void goNorth() {
        release(KeyCode.S);
        System.out.println("going");
        while (Player.getInstance().getPosition().getY() < Controller.getMidY() + 50) {
            press(KeyCode.W);
        }
        release(KeyCode.W);
    }

    public void goSouth() {
        System.out.println("going");
        while (Player.getInstance().getPosition().getY() > Controller.getMidY() / 2) {
            press(KeyCode.S);
        }
        release(KeyCode.S);
    }

    public void goEast() {
        System.out.println("going");
        for (int i = 0; i < 2; i++) {
            press(KeyCode.S);
        }
        release(KeyCode.S);
        while (Player.getInstance().getPosition().getX() > Controller.getMidX() - 1) {
            press(KeyCode.D);
        }
        release(KeyCode.D);
    }

    public void goWest() {
        System.out.println("going");
        for (int i = 0; i < 2; i++) {
            press(KeyCode.S);
        }
        release(KeyCode.S);
        while (Player.getInstance().getPosition().getX() < Controller.getMidX() + 1) {
            press(KeyCode.A);
        }
        release(KeyCode.A);
    }
}
