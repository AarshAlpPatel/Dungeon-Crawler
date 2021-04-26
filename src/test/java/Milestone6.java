package test.java;

import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import main.backend.Controller;
import main.backend.characters.Enemy;
import main.backend.characters.Player;
import main.backend.characters.StatTracker;
import main.backend.rooms.*;
import main.frontend.GameManager;
import main.frontend.MainScreen;
import main.frontend.WelcomeScreen;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.testfx.api.FxToolkit.registerPrimaryStage;
import static org.testfx.api.FxToolkit.setupApplication;
import static org.testfx.api.FxAssert.verifyThat;

public class Milestone6 extends ApplicationTest {
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
        StatTracker.reset();
    }

    private void startGame() {
        clickOn("#toGame");
        clickOn("#nameField");
        type(KeyCode.N);
        clickOn("#weaponRight");
        clickOn("Start Game");
    }

    @Test
    public void testWinGame() {
        startGame();
        press(KeyCode.E);
        release(KeyCode.E);
        verifyThat("You Won!", NodeMatchers.isVisible());
        verifyThat("Congratulations! You defeated the boss and made it out alive!",
                NodeMatchers.isVisible());
        verifyThat(new ImageView("main/design/images/exit.png"), NodeMatchers.isVisible());
        verifyThat(new ImageView("main/design/images/endScreen/backArrow.png"),
                NodeMatchers.isVisible());
        verifyThat(new ImageView("main/design/images/endScreen/summary.png"),
                NodeMatchers.isVisible());
        verifyThat(new ImageView("main/design/images/endScreen/dancing.gif"),
                NodeMatchers.isVisible());
        clickOn("#toSum");
        verifyThat("SCORE", NodeMatchers.isVisible());
        clickOn("#closeSum");
        clickOn("#back");
        verifyThat("Warrior's Legacy", NodeMatchers.isVisible());
    }

    @Test
    public void testLoseGame() {
        startGame();
        press(KeyCode.Q);
        release(KeyCode.Q);
        verifyThat("Game Over!", NodeMatchers.isVisible());
        verifyThat("I have no idea how the hell ya died but ya did it. Congrats?",
                NodeMatchers.isVisible());
        verifyThat(new ImageView("main/design/images/exit.png"), NodeMatchers.isVisible());
        verifyThat(new ImageView("main/design/images/endScreen/backArrow.png"),
                NodeMatchers.isVisible());
        verifyThat(new ImageView("main/design/images/endScreen/summary.png"),
                NodeMatchers.isVisible());
        verifyThat(new ImageView("main/design/images/endScreen/gSkull.gif"),
                NodeMatchers.isVisible());
        clickOn("#toSum");
        verifyThat("SCORE", NodeMatchers.isVisible());
        clickOn("#closeSum");
        clickOn("#back");
        verifyThat("Warrior's Legacy", NodeMatchers.isVisible());
    }

    @Test
    public void testMonstersKilledStat() {
        startGame();
        press(KeyCode.Q);
        release(KeyCode.Q);
        clickOn("#toSum");
        verifyThat("Monsters Killed:0", NodeMatchers.isVisible());
        clickOn("#closeSum");
        clickOn("#back");
        startGame();
        findEnemyRoom1();
        int startingNumAlive = RoomManager.getCurrentEnemies().getEnemyCounter();
        killEnemy(RoomManager.getCurrentEnemies().getEnemyCounter() - 1);
        int enemiesAlive = RoomManager.getCurrentEnemies().getEnemyCounter();
        press(KeyCode.Q);
        release(KeyCode.Q);
        clickOn("#toSum");
        verifyThat("Monsters Killed:" + (startingNumAlive - enemiesAlive),
                NodeMatchers.isVisible());
    }

    @Test
    public void testDamageTakenStat() {
        startGame();
        press(KeyCode.Q);
        release(KeyCode.Q);
        clickOn("#toSum");
        verifyThat("Damage Taken:0", NodeMatchers.isVisible());
        clickOn("#closeSum");
        clickOn("#back");
        startGame();
        findEnemyRoom1();
        double startingHealth = Player.getInstance().getHealth();
        getHit();
        double healthLeft = Player.getInstance().getHealth();
        int healthLost = (int) (startingHealth - healthLeft);
        press(KeyCode.Q);
        release(KeyCode.Q);
        clickOn("#toSum");
        verifyThat("Damage Taken:" + healthLost, NodeMatchers.isVisible());
    }

    @Test
    public void testDamageDealtStat() {
        startGame();
        press(KeyCode.Q);
        release(KeyCode.Q);
        clickOn("#toSum");
        verifyThat("Damage Dealt:0", NodeMatchers.isVisible());
        clickOn("#closeSum");
        clickOn("#back");
        startGame();
        findEnemyRoom1();
        killEnemy(RoomManager.getCurrentEnemies().getEnemyCounter() - 1);
        press(KeyCode.Q);
        release(KeyCode.Q);
        clickOn("#toSum");
        verifyThat("Damage Dealt:" + (int) (StatTracker.getDamageDealt()),
                NodeMatchers.isVisible());
    }

    @Test
    public void testScore() {
        startGame();
        press(KeyCode.Q);
        release(KeyCode.Q);
        clickOn("#toSum");
        verifyThat("SCORE", NodeMatchers.isVisible());
        clickOn("#closeSum");
        clickOn("#back");
        startGame();
        findEnemyRoom1();
        killEnemy(RoomManager.getCurrentEnemies().getEnemyCounter() - 1);
        press(KeyCode.Q);
        release(KeyCode.Q);
        clickOn("#toSum");
        verifyThat("" + (int) (StatTracker.getMonstersKilled() * 20 - StatTracker.getDamageTaken()),
                NodeMatchers.isVisible());
    }

    @Test
    public void testTime() {
        startGame();
        goNorth();
        goSouth();
        goNorth();
        goSouth();
        press(KeyCode.Q);
        release(KeyCode.Q);
        clickOn("#toSum");
        verifyThat(String.format("%02d:%02d", StatTracker.getElapsedTime()[1],
                StatTracker.getElapsedTime()[2]), NodeMatchers.isVisible());
    }

    @Test
    public void testHTP() {
        clickOn("#toHelp");
        verifyThat("#helpRect", NodeMatchers.isVisible());
        verifyThat("#helpClose", NodeMatchers.isVisible());
        verifyThat("How To Play", NodeMatchers.isVisible());
        verifyThat("Use WASD to move.", NodeMatchers.isVisible());
        verifyThat("Left click to attack.", NodeMatchers.isVisible());
        verifyThat("Press G to open the shop.", NodeMatchers.isVisible());
        verifyThat("Press H to open the inventory.", NodeMatchers.isVisible());
        verifyThat("Defeat the monster at the \nend of the labyrinth to win!",
                NodeMatchers.isVisible());
    }

    @Test
    public void testShop() {
        startGame();
        press(KeyCode.G);
        release(KeyCode.G);
        verifyThat("Welcome to the shop!", NodeMatchers.isVisible());
        verifyThat("Choose an item.", NodeMatchers.isVisible());
        verifyThat("#potionPanel", NodeMatchers.isVisible());
        verifyThat("#weaponsPanel", NodeMatchers.isVisible());
        verifyThat("#checkoutPanel", NodeMatchers.isVisible());
    }

    @Test
    public void testBuy() {
        startGame();
        press(KeyCode.G);
        release(KeyCode.G);
        clickOn("#slot1");
        verifyThat("Buy", NodeMatchers.isVisible());
        clickOn("Buy");
        press(KeyCode.G);
        release(KeyCode.G);
        press(KeyCode.H);
        press(KeyCode.H);
        verifyThat(new ImageView("main/design/images/potions/health.png"),
                NodeMatchers.isVisible());
    }

    public Door findEnemyRoom1() {
        Room current = RoomManager.getCurrent();
        if (!(current.getNextRoom(Door.WEST) instanceof TreasureRoom
                || current.getNextRoom(Door.WEST) instanceof WeaponRoom)) {
            goWest();
            return Door.WEST;
        } else if (!(current.getNextRoom(Door.NORTH) instanceof TreasureRoom
                || current.getNextRoom(Door.NORTH) instanceof WeaponRoom)) {
            goNorth();
            return Door.NORTH;
        } else if (!(current.getNextRoom(Door.EAST) instanceof TreasureRoom
                || current.getNextRoom(Door.EAST) instanceof WeaponRoom)) {
            goEast();
            return Door.EAST;
        } else {
            goSouth();
            return Door.SOUTH;
        }
    }

    public void killEnemy(int index) {
        if (RoomManager.getCurrentEnemies().getEnemies()[index].getPosition().getY()
                < MainScreen.getMidY()) {
            Player.getInstance().setPosition(RoomManager.getCurrentEnemies().getEnemies()[index]
                    .getPosition().add(0, 100));
        } else {
            Player.getInstance().setPosition(RoomManager.getCurrentEnemies().getEnemies()[index]
                    .getPosition().subtract(0, 100));
        }
        RoomManager.getCurrentEnemies().getEnemies()[index].changeHealth(-99);
        clickOn(RoomManager.getCurrentEnemies().getEnemies()[index].getRawImage());









        /*
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
            int startingTime = (int) System.nanoTime();
            int i = 0;
            while (System.nanoTime() < (startingTime + 1000000000)) {
                i++;
            }
            //problem child
            clickOn(RoomManager.getCurrentEnemies().getEnemies()[index].getRawImage());
            startingTime = (int) System.nanoTime();
            i = 0;
            while (System.nanoTime() < (startingTime + 1000000000)) {
                i++;
            }
            numAlive = 0;
            for (Enemy e : RoomManager.getCurrentEnemies().getEnemies()) {
                if (!e.isDead()) {
                    numAlive++;
                }
            }
            //assertSame(numAlive, RoomManager.getCurrentEnemies().getEnemyCounter());
        }
        numAlive = 0;
        for (Enemy e : RoomManager.getCurrentEnemies().getEnemies()) {
            if (!e.isDead()) {
                numAlive++;
            }
        }
        return directionKilled; */
    }

    public void getHit() {
        while (Player.getInstance().getHealth() > 80) {
            Player.getInstance().setPosition(RoomManager.getCurrentEnemies()
                    .getEnemies()[0].getPosition());
        }
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
