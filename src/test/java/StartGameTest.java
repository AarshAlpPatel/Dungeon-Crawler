package test.java;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import main.frontend.*;

import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.util.WaitForAsyncUtils;

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
    public void testStartGame() throws Exception {
        robot.clickOn("#toGame");
        verifyThat("Enter Name", NodeMatchers.isNotNull());
    }

    @Test
    public void testBlankName() throws Exception {
        robot.clickOn("#toGame");
        clickOn("#start");
        verifyThat("Enter Name", NodeMatchers.isNotNull());
    }

    @Test
    public void testNewGameDefaultDifficulty() throws Exception {
        robot.clickOn("#toGame");
        robot.clickOn("#nameField");
        type(KeyCode.N);
        robot.clickOn("#start");
        verifyThat("500", NodeMatchers.isNotNull());
    }

    //do we want to add character customization rn?
}
