package test.java;

import main.frontend.*;

import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;

import static org.testfx.api.FxAssert.verifyThat;

public class StartGameTest extends ApplicationTest {
    @Override
    public void start(Stage primaryStage) throws Exception {
        MainScreen mainScreen = new MainScreen();
        mainScreen.start(primaryStage);
    }

    @Test
    public void testStartGame() {
        clickOn("Start Game");
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
