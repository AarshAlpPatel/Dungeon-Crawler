package main.frontend;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import main.backend.Controller;
import main.backend.characters.StatTracker;

public abstract class EndGame {
    protected static Scene endScene;
    protected static StackPane screenHolder;
    protected static BorderPane screen;

    //add an image to the background
    protected static StackPane createBackground(String mainText, String subText) {
        StackPane backgroundStack = new StackPane();
        Pane background = new Pane();
        background.getStyleClass().add("background_end");
        VBox subB = new VBox(5);
        subB.getStyleClass().add("center");
        Label main = new Label(mainText);
        main.getStyleClass().add("main");
        Label sub = new Label(subText);
        sub.getStyleClass().add("sub");
        subB.getChildren().addAll(main, sub);
//        ImageView gOver = new ImageView(new Image("main/design/images/endScreen/gameOver.png", 300, 300, true, false));
//        backgroundStack.getChildren().addAll(gOver);
        backgroundStack.getChildren().addAll(background, subB);
        return backgroundStack;
    }

    protected static VBox createBottomPane() {
        VBox bottom = new VBox(40); //has stats and return button
        bottom.setPadding(new Insets(10, 0, 0, 0));
        bottom.getStyleClass().add("center");

        HBox bottomButtons = new HBox(250);
        bottomButtons.getStyleClass().add("center");

        //toSummary Button
        StackPane toSum = createSumButton();

        //return to welcome screen
        StackPane backToMenu = createBackButton();

        //exit game
        StackPane exitGame = createExitButton();

        bottomButtons.getChildren().addAll(backToMenu, exitGame, toSum);
        //stats.getChildren().addAll(damageDealt, damageTaken, monstersKilled, time, backToMenu);
        bottom.getChildren().addAll(bottomButtons);
        return bottom;
    }

    protected static StackPane createSumButton() {
        StackPane toSum = new StackPane();
        ImageView sum = new ImageView(new Image("main/design/images/endScreen/summary.png", 75, 75, true, false));
        toSum.getChildren().add(sum);
        toSum.setId("toSum");
        toSum.setMaxSize(sum.getFitHeight(), sum.getFitWidth());
        toSum.setOnMouseEntered(event -> {
            endScene.setCursor(Cursor.HAND);
        });
        toSum.setOnMouseExited(event -> {
            endScene.setCursor(Cursor.DEFAULT);
        });
        toSum.setOnMouseReleased(event -> {
            screenHolder.getChildren().add(new Summary());
            screenHolder.getChildren().get(0).setOpacity(0.5);
        });
        return toSum;
    }

    protected static StackPane createBackButton() {
        StackPane back = new StackPane();
        ImageView backToMenu = new ImageView(new Image("main/design/images/endScreen/backArrow.png", 60, 60, true, false));
        back.getChildren().add(backToMenu);
        back.setId("back");
        back.setMaxSize(backToMenu.getFitHeight(), backToMenu.getFitWidth());
        back.setOnMouseReleased(e -> {
            MainScreen.setScene(WelcomeScreen.getScene());
            StatTracker.reset();
        });
        back.setOnMouseEntered(e -> endScene.setCursor(Cursor.HAND));
        back.setOnMouseExited(e -> endScene.setCursor(Cursor.DEFAULT));
        return back;
    }

    protected static StackPane createExitButton() {
        StackPane exit = new StackPane();
        ImageView exitApp = new ImageView(new Image("main/design/images/exit.png", 60, 60, true, false));
        exit.getChildren().add(exitApp);
        exit.setMaxSize(exitApp.getFitHeight(), exitApp.getFitWidth());
        exit.setOnMouseReleased(e -> {
            MainScreen.close();
            StatTracker.reset();
        });
        exit.setOnMouseEntered(e -> endScene.setCursor(Cursor.HAND));
        exit.setOnMouseExited(e -> endScene.setCursor(Cursor.DEFAULT));
        return exit;
    }

    protected static HBox createTopPane(Image image) {
        HBox top = new HBox();
        top.getStyleClass().add("center");
        top.getChildren().add(new ImageView(image));
        return top;
    }

    protected static Scene getEndScene(String mainText, String subText, Image image, String cssScreen) {
        Controller.stopTimer();
        screenHolder = new StackPane();
        screen = new BorderPane();
        screen.getStyleClass().add(cssScreen);
        HBox top = createTopPane(image);
        top.setPadding(new Insets(30, 0, 30, 0));
        StackPane background = createBackground(mainText, subText);
        VBox bottom = createBottomPane();
        bottom.setPadding(new Insets(50, 0, 20, 0));
        screen.setTop(top);
        screen.setCenter(background);
        screen.setBottom(bottom);
        screenHolder.getChildren().add(screen);

        endScene = new Scene(screenHolder, MainScreen.getLength(), MainScreen.getHeight());
        endScene.getStylesheets().add("main/design/EndGame.css");
        return endScene;
    }

    public static Scene getWinScene() {
        return getEndScene(
                "You Won!",
                "Congratulations! You defeated the boss and made it out alive!",
                new Image("main/design/images/endScreen/dancing.gif", 300, 300, true, false),
                "win_screen");
    }

    public static Scene getLoseScene() {
        return getEndScene(
                "Game Over!",
                Controller.getDeathReason(),
                new Image("/main/design/images/endScreen/gSkull.gif", 300, 300, true, false),
                "lose_screen");
    }

    public static class Summary extends StackPane {
        //protected StackPane screenHolder;
        protected VBox screen;
        protected VBox stats;
        protected Rectangle rect;
        protected ImageView close;

        protected Summary() {
            initSummary();
        }

        protected void initSummary() {
            //screenHolder = new StackPane();
            rect = new Rectangle(MainScreen.getLength() - 200, MainScreen.getHeight() - 200);
            rect.getStyleClass().add("summaryRect");
            this.getChildren().add(rect);
            createStatsBox();
            screen = new VBox();
            screen.getStyleClass().add("center");
            HBox closePane = new HBox();
            closePane.setMaxWidth(rect.getWidth());
            closePane.setPadding(new Insets(0, 20, 25, 0));
            closePane.setAlignment(Pos.CENTER_RIGHT);
            close = new ImageView("main/design/images/exit.png");
            close.setId("closeSum");
            close.setOnMouseReleased(event -> {
                screenHolder.getChildren().remove(this);
                screenHolder.getChildren().get(0).setOpacity(1);
            });
            close.setOnMouseEntered(event -> {
                endScene.setCursor(Cursor.HAND);
            });
            close.setOnMouseExited(event -> {
                endScene.setCursor(Cursor.DEFAULT);
            });
            closePane.getChildren().add(close);
            screen.getChildren().addAll(closePane, stats);
            this.getChildren().add(screen);
        }

        protected void createStatsBox() {
            stats = new VBox(20);
            stats.setPadding(new Insets(0, 0, 30, 0));
            stats.getStyleClass().addAll("center");
            VBox score = createScoreBox();
            HBox monstersKilled = createStatLine(
                    new Image("main/design/images/enemies/bat/base/bat-base.gif", 50, 50, true, false),
                    "Monsters Killed:" + (int)((double)Controller.getPlayerStats().get(2)));
            HBox damageDealt = createStatLine(
                    new Image("main/design/images/endScreen/pow.png", 50, 50, true, false),
                    "Damage Taken:" + (int)((double)Controller.getPlayerStats().get(1)));
            HBox damageTaken = createStatLine(
                    new Image("main/design/images/endScreen/pow.png", 50, 50, true, false),
                    "Damage Dealt:" + (int)((double)Controller.getPlayerStats().get(0)));
            String timeString = createTimeString(Controller.getTimeTaken());
            Label time = new Label(timeString);
            time.getStyleClass().add("time");
            stats.getChildren().addAll(score, damageDealt, damageTaken, monstersKilled, time);
        }

        protected static VBox createScoreBox() {
            VBox score = new VBox(5);
            score.setPadding(new Insets(0, 0, 25, 0));
            Label scoreT = new Label("SCORE");
            scoreT.getStyleClass().add("score");
            Label scoreV = new Label("" + (int)((double)Controller.getPlayerStats().get(3)));
            scoreV.getStyleClass().add("score");
            score.getChildren().addAll(scoreT, scoreV);
            score.getStyleClass().add("center");
            return score;
        }

        protected static HBox createStatLine(Image image, String text) {
            HBox statLine = new HBox(5);
            statLine.getStyleClass().add("center");
            Label statText = new Label(text);
            statText.getStyleClass().add("stat");
            ImageView imageView = new ImageView(image);
            statLine.getChildren().addAll(imageView, statText);
            return statLine;
        }

        protected static String createTimeString(Integer[] timeNums) {
            if (timeNums[0] == 0) {
                //without the hours
                return String.format("%02d:%02d", timeNums[1], timeNums[2]);
            }
            //with the hours
            return String.format("%02d:%02d:%02d", timeNums[0], timeNums[1], timeNums[2]);
        }
    }
}
