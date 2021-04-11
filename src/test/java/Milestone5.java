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
        assertTrue(Player.getInstance().getInventory().getWeapon(0) instanceof Dagger);
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

    private void pickUpWeapon() {
        clickOn("#toGame");
        clickOn("#nameField");
        type(KeyCode.N);
        clickOn("#weaponRight");
        clickOn("Start Game");

        findEnemyRoom();
        String dir = killLastEnemy();
        KeyCode kTP = dir.equals("North") ? KeyCode.W : KeyCode.S;

        press(kTP);
        press(kTP);
        press(kTP);
        release(kTP);

        press(KeyCode.F);
        release(KeyCode.F);
    }

    public void findEnemyRoom() {
        Room current = RoomManager.getCurrent();
        if (!(current.getNextRoom(Door.WEST) instanceof TreasureRoom)) {
            goWest();
        } else if (!(current.getNextRoom(Door.NORTH) instanceof TreasureRoom)) {
            goNorth();
        } else if (!(current.getNextRoom(Door.EAST) instanceof TreasureRoom)) {
            goEast();
        } else {
            goSouth();
        }
    }

    @Test
    public void testSwitch() {
        pickUpWeapon();

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
        goSouth();
        verifyThat(Player.getInstance().getInventory().getWeapon(0).getRawImage(),
                NodeMatchers.isVisible());
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
        pickUpPotion(400, 400);
        assertNotNull(Player.getInstance().getInventory().getPotion(0));
        assertTrue(Player.getInstance().getInventory().getPotion(0) instanceof HealthPotion);
        press(KeyCode.H);
        release(KeyCode.H);
        verifyThat(Player.getInstance().getInventory().getPotion(0).getRawImage(), NodeMatchers.isVisible());
        assertEquals(1, Player.getInstance().getInventory().getPotions().size());

        pickUpPotion(200, 200);
        assertNotNull(Player.getInstance().getInventory().getPotion(1));
        assertTrue(Player.getInstance().getInventory().getPotion(1) instanceof AttackPotion);
        press(KeyCode.H);
        release(KeyCode.H);
        verifyThat(Player.getInstance().getInventory().getPotion(1).getRawImage(), NodeMatchers.isVisible());
        assertEquals(2, Player.getInstance().getInventory().getPotions().size());

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
        clickOn("Start Game");
    }

    public void goToInventory() {
        startGame();
        press(KeyCode.H);
        release(KeyCode.H);
    }

    public String killLastEnemy() {
        int numAlive = 0;
        String directionKilled;
        int index = RoomManager.getCurrentEnemies().getEnemyCounter() - 1;
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

    public void goNorthSetAmount() {
        int i = 0;
        while (i < 30) {
            press(KeyCode.W);
            i++;
        }
        release(KeyCode.W);
    }

    public void goSouthSetAmount() {
        int i = 0;
        while (i < 30) {
            press(KeyCode.S);
            i++;
        }
        release(KeyCode.S);
    }

    public void goEastSetAmount() {
        for (int i = 0; i < 2; i++) {
            press(KeyCode.S);
        }
        release(KeyCode.S);
        int i = 0;
        while (i < 30) {
            press(KeyCode.D);
            i++;
        }
        release(KeyCode.D);
    }

    public void goWestSetAmount() {
        for (int i = 0; i < 2; i++) {
            press(KeyCode.S);
        }
        release(KeyCode.S);
        int i = 0;
        while (i < 30) {
            press(KeyCode.A);
            i++;
        }
        release(KeyCode.A);
    }

    public Door findOpenDoor(Room current, Door lastRoomDirection) {
        int i = 0;
        Door[] directions = Door.values();
        while (current.getNextRoom(directions[i]) == null || directions[i] == lastRoomDirection) {
            i++;
        }
        Point2D middle = new Point2D(MainScreen.getMidX(), MainScreen.getMidY());
        Player.getInstance().setPosition(middle);
        switch (directions[i]) {
            case EAST:
                goEast();
                return Door.EAST;
            case WEST:
                goWest();
                return Door.WEST;
            case NORTH:
                goNorthSetAmount();
                return Door.NORTH;
            case SOUTH:
                goSouth();
                return Door.SOUTH;
            default:
        }
        goEast();
        return null;
    }

    public Door findOpenDoorSetAmount(@NotNull Room current, Door lastRoomDirection) {
        int i = 0;
        Door[] directions = Door.values();
        while (current.getNextRoom(directions[i]) == null || directions[i] == lastRoomDirection) {
            i++;
        }
        Player.getInstance().setPosition(new Point2D(MainScreen.getMidX(), MainScreen.getMidY()));
        switch (directions[i]) {
            case EAST:
                goEastSetAmount();
                return Door.EAST;
            case WEST:
                goWestSetAmount();
                return Door.WEST;
            case NORTH:
                goNorthSetAmount();
                return Door.NORTH;
            case SOUTH:
                goSouthSetAmount();
                return Door.SOUTH;
            default:
        }
        return null;
    }
}
