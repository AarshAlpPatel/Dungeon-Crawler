package test.java;

import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import main.backend.Controller;
import main.backend.characters.Player;
import main.backend.rooms.Door;
import main.backend.rooms.RoomManager;
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
        clickOn("#toGame");
        clickOn("#nameField");
        type(KeyCode.N);
        clickOn("Start Game");
        for (int i = 0; i < 2; i++) {
            press(KeyCode.S);
        }
        release(KeyCode.S);
        while (Player.getInstance().getPosition().getX() > Controller.getMidX() - 1) {
            press(KeyCode.D);
        }
        release(KeyCode.D);
        assertTrue(Player.getInstance().getPosition().getY() < MainScreen.getHeight());
        assertTrue(Player.getInstance().getPosition().getX() < MainScreen.getLength());
    }

    @Test
    public void checkForEnemiesEasy() {
        clickOn("#toGame");
        clickOn("#nameField");
        type(KeyCode.N);
        clickOn("Start Game");
        for (int i = 0; i < 2; i++) {
            press(KeyCode.S);
        }
        release(KeyCode.S);
        while (Player.getInstance().getPosition().getX() > Controller.getMidX() - 1) {
            press(KeyCode.D);
        }
        release(KeyCode.D);
        verifyThat(new ImageView("main/design/images/enemies/bat/base/bat-base.gif"), NodeMatchers.isVisible());
    }

    @Test
    public void checkLockedDoor() {
        clickOn("#toGame");
        clickOn("#nameField");
        type(KeyCode.N);
        clickOn("Start Game");
        goEast();
        Room current = RoomManager.getCurrent();
        findOpenDoor(current);
        assertTrue(current.equals(RoomManager.getCurrent())); //makes sure you didn't leave the room
    }

    @Test
    public void testUnlockingFunction() {
        clickOn("#toGame");
        clickOn("#nameField");
        type(KeyCode.N);
        clickOn("Start Game");
        Door directionCurr = Door.NORTH;
        int i = 0;
        boolean found = false;
        while (i < 4 && found == false) {
            Door directionNext = Door.values()[i];
            Room next = RoomManager.getCurrent().getNextRoom(directionNext);
            int numConnections = 0;
            int j = 0;
            while (j < next.getConnections().length) {
                if (next.getConnections()[j] != false) {
                    numConnections++;
                    if (numConnections > 1) {
                        found = true;
                        directionCurr = directionNext;
                        break;
                    }
                }
            }
        }
        switch (directionCurr) {
            case NORTH:
                goNorth();
            case EAST:
                goEast();
            case SOUTH:
                goSouth();
            case WEST:
                goWest();
        }
//        EnemyManager currentRoomEnemies = RoomManager.getCurrentEnemies();
//        int i = 0;
//        while (i < currentRoomEnemies.getEnemies().length) {
//            currentRoomEnemies.destroy(i);
//            i++;
//        }
        RoomManager.getCurrent().getCurrentEnemies().setEnemyCounter(0);
        Room current = RoomManager.getCurrent();
        Door direction = findOpenDoor(current);
        assertTrue(RoomManager.getCurrent() == current.getNextRoom(direction));
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
        for (int i = 0; i < 3; i++) {
            press(KeyCode.S);
        }
        release(KeyCode.S);
        while (Player.getInstance().getPosition().getX() > Controller.getMidX() - 1) {
            press(KeyCode.D);
        }
    }

    public void goWest() {
        for (int i = 0; i < 3; i++) {
            press(KeyCode.S);
        }
        release(KeyCode.S);
        while (Player.getInstance().getPosition().getX() < Controller.getMidX() + 1) {
            press(KeyCode.A);
        }
    }

    public Door findOpenDoor(Room current) {
        int i = 0;
        Door[] directions = Door.values();
        while (current.getNextRoom(directions[i]) == null) {
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
}
