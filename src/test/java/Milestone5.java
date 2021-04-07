package test.java;

import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import main.backend.characters.Player;
import main.frontend.MainScreen;
import main.frontend.WelcomeScreen;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;

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
        setupApplication(MainScreen.class);
    }

    @Test
    public void testInventoryScreen() {
        startGame();
        press(KeyCode.H);
        release(KeyCode.H);
        verifyThat(Player.getInstance().getMainWeapon().getRawImage(), NodeMatchers.isVisible());
//        if (Player.getInstance().getBackupWeapon() != null)
//            verifyThat(Player.getInstance().getBackupWeapon().getRawImage(), NodeMatchers.isVisible());
        //Player.getInstance().getInventory() matches images in inventory screen
    }

    public void startGame() {
        clickOn("#toGame");
        clickOn("#nameField");
        type(KeyCode.N);
        clickOn("Start Game");
    }
}
