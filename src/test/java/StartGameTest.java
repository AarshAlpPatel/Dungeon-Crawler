package test.java;

import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import main.backend.Controller;
import main.frontend.*;

import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;

import org.testfx.api.FxRobot;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.api.FxToolkit.registerPrimaryStage;
import static org.testfx.api.FxToolkit.setupApplication;

public class StartGameTest extends ApplicationTest {

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
    public void testNewGame() throws Exception {
        clickOn("#toGame");
        verifyThat("Enter Name", NodeMatchers.isVisible());
        verifyThat("Difficulty", NodeMatchers.isVisible());
        verifyThat(new ImageView("/main/design/images/dagger.png"), NodeMatchers.isVisible());
        verifyThat(new ImageView("/main/design/images/char1.gif"), NodeMatchers.isVisible());
        verifyThat("Dagger", NodeMatchers.isVisible());
        verifyThat("Choose", NodeMatchers.isVisible());
        verifyThat("Main Menu", NodeMatchers.isVisible());
        verifyThat("Start Game", NodeMatchers.isVisible());
        verifyThat("#forwardChar", NodeMatchers.isVisible());
        verifyThat("#backwardChar", NodeMatchers.isVisible());
        verifyThat("#forwardWeapon", NodeMatchers.isVisible());
        verifyThat("#backwardWeapon", NodeMatchers.isVisible());
    }

    @Test
    public void testSettings() throws Exception {
        clickOn("#toSettings");
        verifyThat("Settings", NodeMatchers.isVisible());
        clickOn("#difficulty");
        clickOn("Easy");
        assertEquals("Easy", Controller.getDifficultyLevel());
        clickOn("#difficulty");
        clickOn("Medium");
        assertEquals("Medium", Controller.getDifficultyLevel());
        clickOn("#difficulty");
        clickOn("Hard");
        assertEquals("Hard", Controller.getDifficultyLevel());
        clickOn("#toMainScreen");
        verifyThat("#gameTitle", NodeMatchers.isVisible());
    }

    @Test
    public void testBlankName() {
        clickOn("New Game");
        clickOn("Start Game");
        verifyThat("No Name Entered", NodeMatchers.isVisible());
        clickOn("#nameField");
        type(KeyCode.SPACE);
        clickOn("Start Game");
        verifyThat("Name can't Start with a Space", NodeMatchers.isVisible());
    }

    @Test
    public void testDefaultDifficulty() throws Exception {
        clickOn("#toGame");
        clickOn("#nameField");
        type(KeyCode.N);
        clickOn("#start");
        verifyThat("500", NodeMatchers.isNotNull());
        clickOn("#roomToSetup");
    }

    @Test
    public void testEasyDiff() {
        clickOn("#toGame");
        clickOn("#nameField");
        type(KeyCode.N);
        robot.clickOn("#diffCombo");
        clickOn("Easy");
        clickOn("#start");
        verifyThat("500", NodeMatchers.isNotNull());
        verifyThat(new ImageView("/main/design/images/coin.png"), NodeMatchers.isVisible());
        for (int i = 0; i < 8; i++) {
            verifyThat("#heart" + i, NodeMatchers.isVisible());
        }
    }

    @Test
    public void testMediumDiff() {
        clickOn("#toGame");
        clickOn("#nameField");
        type(KeyCode.N);
        robot.clickOn("#diffCombo");
        clickOn("Medium");
        clickOn("#start");
        verifyThat("400", NodeMatchers.isNotNull());
        for (int i = 0; i < 7; i++) {
            verifyThat("#heart" + i, NodeMatchers.isVisible());
        }
    }

    @Test
    public void testHardDiff() {
        clickOn("#toGame");
        clickOn("#nameField");
        type(KeyCode.N);
        robot.clickOn("#diffCombo");
        clickOn("Hard");
        clickOn("#start");
        verifyThat("300", NodeMatchers.isNotNull());
        for (int i = 0; i < 6; i++) {
            verifyThat("#heart" + i, NodeMatchers.isVisible());
        }
    }

    @Test
    public void testCharacterToggle() {
        clickOn("#toGame");
        verifyThat(new ImageView("main/design/images/char1.gif"), NodeMatchers.isVisible());
        clickOn("#forwardChar");
        verifyThat(new ImageView("main/design/images/char2.gif"), NodeMatchers.isVisible());
        clickOn("#forwardChar");
        verifyThat(new ImageView("main/design/images/char3.gif"), NodeMatchers.isVisible());
        clickOn("#backwardChar");
        verifyThat(new ImageView("main/design/images/char2.gif"), NodeMatchers.isVisible());
        clickOn("#backwardChar");
        verifyThat(new ImageView("main/design/images/char1.gif"), NodeMatchers.isVisible());
        clickOn("#backwardChar");
        verifyThat(new ImageView("main/design/images/char3.gif"), NodeMatchers.isVisible());
        clickOn("#choose");
        verifyThat(new ImageView("main/design/images/check.png"), NodeMatchers.isVisible());
    }

    @Test
    public void testWeaponToggle() {
        clickOn("#toGame");
        verifyThat(new ImageView("main/design/images/dagger.png"), NodeMatchers.isVisible());
        clickOn("#forwardWeapon");
        verifyThat(new ImageView("main/design/images/spear.png"), NodeMatchers.isVisible());
        clickOn("#forwardWeapon");
        verifyThat(new ImageView("main/design/images/axe.png"), NodeMatchers.isVisible());
        clickOn("#backwardWeapon");
        verifyThat(new ImageView("main/design/images/spear.png"), NodeMatchers.isVisible());
        clickOn("#backwardWeapon");
        verifyThat(new ImageView("main/design/images/dagger.png"), NodeMatchers.isVisible());
        clickOn("#backwardWeapon");
        verifyThat(new ImageView("main/design/images/axe.png"), NodeMatchers.isVisible());
    }

    @Test
    public void testExits() {
        clickOn("#toGame");
        clickOn("#nameField");
        type(KeyCode.N);
        clickOn("Start Game");
        int i = 0;
        while (i < 100) {
            press(KeyCode.D);
            i++;
        }
        verifyThat("#won", NodeMatchers.isVisible());
    }
}
