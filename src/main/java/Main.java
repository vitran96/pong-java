import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.awt.*;

public class Main extends Application {
    Pane root;
    Rectangle player, bot;
    Circle ball;
    Line line;
    AnimationTimer timer;
    private final int W = 1000, H = 400;
    private int speedX = 3, speedY = 3, dv = speedX, dy = speedY;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Pong Game");
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();

        primaryStage.getScene().setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.UP) player.setLayoutY(player.getLayoutY() - 30);
            if (e.getCode() == KeyCode.DOWN) player.setLayoutY(player.getLayoutY() + 30);
        });

    }

    private Parent createContent() {
        root = new Pane();
        root.setPrefSize(W, H);
        root.setStyle("-fx-background-color: black");

        line = new Line(W/2, 0, W/2, H);
        line.setStroke(Color.WHITE);

        bot = new Rectangle(10, 80, Color.WHITE);
        bot.setLayoutX(0);
        bot.setLayoutY(H/2 - 40);

        player = new Rectangle(10, 80, Color.WHITE);
        player.setLayoutX(W - 10);
        player.setLayoutY(H/2 - 40);

        ball = new Circle(5);
        ball.setFill(Color.WHITE);
        ball.setLayoutX(W/2);
        ball.setLayoutY(H/2);

        root.getChildren().addAll(line, bot, player, ball);

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                gameUpdate();
            }
        };

        timer.start();

        return root;
    }

    private void gameUpdate() {
        double x = ball.getLayoutX(),  y = ball.getLayoutY();

        if (x <= 10 && y > bot.getLayoutY() && y < bot.getLayoutY() + 80) dv = speedX;
        if (x >= W - 12.5 && y > player.getLayoutY() && y < player.getLayoutY() + 80) {
            //speedX++;
            dv = -speedX;
        }
        if (y <= 0) dy = speedY;
        if (y >= H - 5) dy = -speedY;

        ball.setLayoutX(ball.getLayoutX() + dv);
        ball.setLayoutY(ball.getLayoutY() + dy);

        if (x < W/2 && bot.getLayoutY() > y) bot.setLayoutY(bot.getLayoutY() - 5);
        if (x < W/2 && bot.getLayoutY() + 80 < y) bot.setLayoutY(bot.getLayoutY() + 5);

    }


    public static void main (String args[]) {
        launch(args);
    }
}
