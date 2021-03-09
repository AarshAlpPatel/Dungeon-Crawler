package test.java;

import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import main.backend.Controller;
import main.backend.characters.Player;
import main.frontend.MainScreen;
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

public class Milestone3 extends ApplicationTest {
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
    public void testEastRoom() {
        clickOn("#toGame");
        clickOn("#nameField");
        type(KeyCode.N);
        clickOn("Start Game");
        while (Player.getInstance().getPosition().getX() > Controller.getMidX() - 1) {
            press(KeyCode.D);
        }
        release(KeyCode.D);
        verifyThat(new ImageView("main/design/images/dungeon_floor.png"), NodeMatchers.isVisible());
        verifyThat(new ImageView("main/design/images/heart.png"), NodeMatchers.isVisible());
        verifyThat(new ImageView("main/design/images/coin.png"), NodeMatchers.isVisible());
    }

    @Test
    public void testNorthRoom() {
        clickOn("#toGame");
        clickOn("#nameField");
        type(KeyCode.N);
        clickOn("Start Game");
        while (Player.getInstance().getPosition().getY() < Controller.getMidY() - 1) {
            press(KeyCode.W);
        }
        release(KeyCode.W);
        verifyThat(new ImageView("main/design/images/dungeon_floor.png"), NodeMatchers.isVisible());
        verifyThat(new ImageView("main/design/images/heart.png"), NodeMatchers.isVisible());
        verifyThat(new ImageView("main/design/images/coin.png"), NodeMatchers.isVisible());
    }

    @Test
    public void testWestRoom() {
        clickOn("#toGame");
        clickOn("#nameField");
        type(KeyCode.N);
        clickOn("Start Game");
        while (Player.getInstance().getPosition().getX() < Controller.getMidX() + 1) {
            press(KeyCode.A);
        }
        release(KeyCode.A);
        verifyThat(new ImageView("main/design/images/dungeon_floor.png"), NodeMatchers.isVisible());
        verifyThat(new ImageView("main/design/images/heart.png"), NodeMatchers.isVisible());
        verifyThat(new ImageView("main/design/images/coin.png"), NodeMatchers.isVisible());
    }

    @Test
    public void testSouthRoom() {
        clickOn("#toGame");
        clickOn("#nameField");
        type(KeyCode.N);
        clickOn("Start Game");
        while (Player.getInstance().getPosition().getY() > Controller.getMidY() / 2) {
            press(KeyCode.S);
        }
        release(KeyCode.S);
        verifyThat(new ImageView("main/design/images/dungeon_floor.png"), NodeMatchers.isVisible());
        verifyThat(new ImageView("main/design/images/heart.png"), NodeMatchers.isVisible());
        verifyThat(new ImageView("main/design/images/coin.png"), NodeMatchers.isVisible());
    }

    @Test
    public void checkForFourExits() {
        clickOn("#toGame");
        clickOn("#nameField");
        type(KeyCode.N);
        clickOn("Start Game");
        for (int i = 0; i < 4; i++) {
            verifyThat("#door" + i, NodeMatchers.isVisible());
        }
    }

    @Test
    public void testStopAtWall() {
        clickOn("#toGame");
        clickOn("#nameField");
        type(KeyCode.N);
        clickOn("Start Game");
        int index = 0;
        while (index < 50) {
            press(KeyCode.D);
            press(KeyCode.S);
            index++;
        }
        release(KeyCode.D);
        release(KeyCode.S);
        assertTrue(Player.getInstance().getPosition().getY() < MainScreen.getHeight());
        assertTrue(Player.getInstance().getPosition().getX() < MainScreen.getLength());
        for (int i = 0; i < 4; i++) {
            verifyThat("#door" + i, NodeMatchers.isVisible());
        }
    }

    @Test
    public void testReturnToPreviousRoomEastDoor() {
        clickOn("#toGame");
        clickOn("#nameField");
        type(KeyCode.N);
        clickOn("Start Game");
        int index = 0;
        while (Player.getInstance().getPosition().getX() > Controller.getMidX() - 1) {
            press(KeyCode.D);
            index++;
        }
        release(KeyCode.D);
        index = 0;
        while (Player.getInstance().getPosition().getX() < Controller.getMidX()) {
            press(KeyCode.A);
            index++;
        }
        release(KeyCode.A);
        for (int i = 0; i < 4; i++) {
            verifyThat("#door" + i, NodeMatchers.isVisible());
        }
    }

    @Test
    public void testReturnToPreviousRoomNorthDoor() {
        clickOn("#toGame");
        clickOn("#nameField");
        type(KeyCode.N);
        clickOn("Start Game");
        while (Player.getInstance().getPosition().getY() < Controller.getMidY() - 1) {
            press(KeyCode.W);
        }
        release(KeyCode.W);
        while (Player.getInstance().getPosition().getY() > Controller.getMidY()) {
            press(KeyCode.S);
        }
        release(KeyCode.S);
        for (int i = 0; i < 4; i++) {
            verifyThat("#door" + i, NodeMatchers.isVisible());
        }
    }

    @Test
    public void testReturnToPreviousRoomWestDoor() {
        clickOn("#toGame");
        clickOn("#nameField");
        type(KeyCode.N);
        clickOn("Start Game");
        while (Player.getInstance().getPosition().getX() < Controller.getMidX() + 1) {
            press(KeyCode.A);
        }
        release(KeyCode.A);
        while (Player.getInstance().getPosition().getX() > Controller.getMidY()) {
            press(KeyCode.D);
        }
        release(KeyCode.D);
        for (int i = 0; i < 4; i++) {
            verifyThat("#door" + i, NodeMatchers.isVisible());
        }
    }

    @Test
    public void testReturnToPreviousRoomSouthDoor() {
        clickOn("#toGame");
        clickOn("#nameField");
        type(KeyCode.N);
        clickOn("Start Game");
        while (Player.getInstance().getPosition().getY() > Controller.getMidY() / 2) {
            press(KeyCode.S);
        }
        release(KeyCode.S);
        while (Player.getInstance().getPosition().getY() < Controller.getMidY()) {
            press(KeyCode.W);
        }
        release(KeyCode.W);
        for (int i = 0; i < 4; i++) {
            verifyThat("#door" + i, NodeMatchers.isVisible());
        }
    }
}
