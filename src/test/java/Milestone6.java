package test.java;

import javafx.stage.Stage;
import main.frontend.GameManager;
import main.frontend.MainScreen;
import main.frontend.WelcomeScreen;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit.ApplicationTest;

import static org.testfx.api.FxToolkit.registerPrimaryStage;
import static org.testfx.api.FxToolkit.setupApplication;

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
    }

    @Test
    public void testWinGame() {

    }

    @Test
    public void testLoseGame() {

    }

    @Test
    public void testMonstersKilledStat() {

    }

    @Test
    public void testDamageTakenStat() {

    }

    @Test
    public void testDamageDealtStat() {

    }
}
