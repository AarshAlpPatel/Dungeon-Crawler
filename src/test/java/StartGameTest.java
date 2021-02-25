package test.java;

import javafx.scene.Scene;
import main.frontend.*;

import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.util.WaitForAsyncUtils;

import static org.testfx.api.FxAssert.verifyThat;

public class StartGameTest extends ApplicationTest {

    private Stage stage;
    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        MainScreen mainScreen = new MainScreen();
        mainScreen.start(primaryStage);
    }

//    @BeforeEach
//    public void setup() {
//        MainScreen mainScreen = new MainScreen();
//        mainScreen.start(stage);
//    }

    @Test
    public void testStartGame() {
//        FxRobot robot = new FxRobot();
        clickOn("Start Game");
        //WaitForAsyncUtils.waitForFxEvents();
        verifyThat("Enter Name", NodeMatchers.isNotNull());
    }

    @Test
    public void testBlankName() {

    }

    @Test
    public void testNewGame() {

    }

    //do we want to add character customization rn?
}
