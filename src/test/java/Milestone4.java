package test.java;

import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import main.backend.Controller;
import main.backend.characters.*;
import main.backend.exceptions.EdgeOfScreen;
import main.backend.rooms.Door;
import main.backend.rooms.RoomManager;
import main.frontend.LoseGame;
import main.frontend.MainScreen;
import main.backend.rooms.Room;
import main.frontend.WelcomeScreen;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;

import static junit.framework.TestCase.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.api.FxToolkit.registerPrimaryStage;
import static org.testfx.api.FxToolkit.setupApplication;

public class Milestone4 extends ApplicationTest {
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
        setupApplication(MainScreen.class);
    }

    @Test
    public void testStartRoomNotLocked() {
        startGame();
        Room start = RoomManager.getCurrent();
        goEast();
        assertTrue(Player.getInstance().getPosition().getY() < MainScreen.getHeight());
        assertTrue(Player.getInstance().getPosition().getX() < MainScreen.getLength());
        assertSame(start.getNextRoom(Door.EAST), RoomManager.getCurrent());
    }

    @Test
    public void checkForEnemies() {
        startGame();
        goEast();
        assertTrue(RoomManager.getCurrentEnemies().getEnemies().length > 0);
        verifyThat(RoomManager.getCurrentEnemies().getEnemies()[0].getRawImage(), NodeMatchers.isVisible());
    }

    @Test
    public void testThreeTypes() {
        startGame();
        goEast();
        verifyThat(new ImageView("main/design/images/enemies/bat/base/bat-base.gif"), NodeMatchers.isVisible());
        verifyThat(new ImageView("main/design/images/enemies/snake/base/snake_base.gif"), NodeMatchers.isVisible());
        verifyThat(new ImageView("main/design/images/enemies/ghost/base/ghost_base.gif"), NodeMatchers.isVisible());
    }

    @Test
    public void checkLockedDoor() {
        startGame();
        Room start = RoomManager.getCurrent();
        Door lastRoomDirection;
        if (start.getNextRoom(Door.EAST).hasConnections()) {
            goEast();
            lastRoomDirection = Door.WEST;
        } else if (start.getNextRoom(Door.WEST).hasConnections()) {
            goWest();
            lastRoomDirection = Door.EAST;
        } else if (start.getNextRoom(Door.NORTH).hasConnections()) {
            goNorth();
            lastRoomDirection = Door.SOUTH;
        } else {
            goSouth();
            lastRoomDirection = Door.NORTH;
        }
        Room current = RoomManager.getCurrent();
        findOpenDoorSetAmount(current, lastRoomDirection);
        assertTrue(current.equals(RoomManager.getCurrent())); //makes sure you didn't leave the room
    }

    @Test
    public void testPlayerAttack() {
        startGame();
        goEast();
        Player.getInstance().setPosition(RoomManager.getCurrentEnemies().getEnemies()[0].getPosition().subtract(100, 0));
        for (int j = 0; j < 10; j++) {
            clickOn(RoomManager.getCurrentEnemies().getEnemies()[0].getRawImage());
        }
        if (RoomManager.getCurrentEnemies().getEnemies()[0] instanceof Bat) {
            verifyThat(new ImageView("main/design/images/enemies/bat/dead/bat-dead.png"), NodeMatchers.isVisible());
        } else if (RoomManager.getCurrentEnemies().getEnemies()[0] instanceof Ghost) {
            verifyThat(new ImageView("main/design/images/enemies/ghost/dead/ghost_dead.png"), NodeMatchers.isVisible());
        } else if (RoomManager.getCurrentEnemies().getEnemies()[0] instanceof Snake) {
            verifyThat(new ImageView("main/design/images/enemies/snake/dead/snake_dead_frame0.png"), NodeMatchers.isVisible());
        }
    }

    @Test
    public void testEnemyCounter() {
        startGame();
        assertTrue(RoomManager.getCurrentEnemies().getEnemyCounter() == 0);
        goWest();
        assertTrue(RoomManager.getCurrentEnemies().getEnemies().length == RoomManager.getCurrentEnemies().getEnemyCounter());
        killAllEnemies();
        assertTrue(RoomManager.getCurrentEnemies().getEnemyCounter() == 0);
    }

    @Test
    public void testUnlockingFunction() {
        startGame();
        Room current = RoomManager.getCurrent();
        Door lastRoomDirection;
        if (current.getNextRoom(Door.EAST).hasConnections()) {
            goEast();
            lastRoomDirection = Door.WEST;
        } else if (current.getNextRoom(Door.WEST).hasConnections()) {
            goWest();
            lastRoomDirection = Door.EAST;
        } else if (current.getNextRoom(Door.NORTH).hasConnections()) {
            goNorth();
            lastRoomDirection = Door.SOUTH;
        } else {
            goSouth();
            lastRoomDirection = Door.NORTH;
        }
        killAllEnemies();
        Room nextToStart = RoomManager.getCurrent();
        Door direction = findOpenDoor(nextToStart, lastRoomDirection);
        if (direction == null) direction = Door.EAST;
        assertSame(RoomManager.getCurrent(), nextToStart.getNextRoom(direction));
    }

    @Test
    public void testEnemyAttack() {
        startGame();
        goNorth();
        //verifyThat(new Label("100"), NodeMatchers.isVisible());
        while (Player.getInstance().getHealth() > 80) {
            Player.getInstance().setPosition(RoomManager.getCurrentEnemies().getEnemies()[0].getPosition());
        }
        assertFalse(main.frontend.Room.getHealthVal().getText().equals("100"));
    }

    @Test
    public void testLoseGame() {
        startGame();
        goEast();
        while (Player.getInstance().getHealth() > 0) {
            Player.getInstance().setPosition(RoomManager.getCurrentEnemies().getEnemies()[0].getPosition());
        }
        verifyThat(new ImageView("main/design/images/Dead.gif"), NodeMatchers.isVisible());
    }

    @Test
    public void testPreviousRoomUnlocked() {
        startGame();
        Room start = RoomManager.getCurrent();
        goEast();
        goWest();
        assertSame(RoomManager.getCurrent(), start);
    }

    public void startGame() {
        clickOn("#toGame");
        clickOn("#nameField");
        type(KeyCode.N);
        clickOn("Start Game");
    }

    public void killAllEnemies() {
        int i = 0;
        Enemy[] enemies = RoomManager.getCurrentEnemies().getEnemies();
        while (i < enemies.length) {
            Player.getInstance().setPosition(RoomManager.getCurrentEnemies().getEnemies()[i].getPosition().subtract(100, 0));
            for (int j = 0; j < 10; j++) {
                clickOn(RoomManager.getCurrentEnemies().getEnemies()[i].getRawImage());
            }
            i++;
        }
    }

    public void goNorth() {
        while (Player.getInstance().getPosition().getY() < Controller.getMidY() - 1) {
            press(KeyCode.W);
        }
        release(KeyCode.W);
    }

    public void goSouth() {
        while (Player.getInstance().getPosition().getY() > Controller.getMidY() / 2) {
            press(KeyCode.S);
        }
        release(KeyCode.S);
    }

    public void goEast() {
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
        Player.getInstance().setPosition(new Point2D(MainScreen.getMidX(), MainScreen.getMidY()));
        switch (directions[i]) {
            case EAST:
                goEast();
                return Door.EAST;
            case WEST:
                goWest();
                return Door.WEST;
            case NORTH:
                goNorth();
                return Door.NORTH;
            case SOUTH:
                goSouth();
                return Door.SOUTH;
        }
        return null;
    }

    public Door findOpenDoorSetAmount(Room current, Door lastRoomDirection) {
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
        }
        return null;
    }
}
