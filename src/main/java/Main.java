import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Main extends Application {

    // Players
    private Rectangle player2, player1;

    // Ball
    private Circle ball;

    // Window size
    private final int W = 1000, H = 400;

    // Ball's speed and direction in 2D
    private int speedX = 3, speedY = 3, dv = speedX, dy = speedY;

    // Stop
    private boolean stop = false;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Pong Game");
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();

        // Set Controller
        primaryStage.getScene().setOnKeyPressed(e -> {
            // Player 1:
            // W - up
            // S - down
            if (e.getCode() == KeyCode.W) player1.setLayoutY(player1.getLayoutY() - 30);
            if (e.getCode() == KeyCode.S) player1.setLayoutY(player1.getLayoutY() + 30);

            // Player 2:
            // UP - up
            // DOWN - down
            if (e.getCode() == KeyCode.UP) player2.setLayoutY(player2.getLayoutY() - 30);
            if (e.getCode() == KeyCode.DOWN) player2.setLayoutY(player2.getLayoutY() + 30);

            // Stop game
            if (e.getCode() == KeyCode.SPACE)
                stopGame();
        });

    }

    private Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(W, H);
        root.setStyle("-fx-background-color: black");

        Line line = new Line(W / 2, 0, W / 2, H);
        line.setStroke(Color.WHITE);

        // Set player 1 on the left
        player1 = new Rectangle(10, 80, Color.WHITE);
        player1.setLayoutX(0);
        player1.setLayoutY(H/2 - 40);

        // Set player 2 on the right
        player2 = new Rectangle(10, 80, Color.WHITE);
        player2.setLayoutX(W - 10);
        player2.setLayoutY(H/2 - 40);

        // Set ball in the middle
        ball = new Circle(5);
        ball.setFill(Color.WHITE);
        ball.setLayoutX(W/2);
        ball.setLayoutY(H/2);

        root.getChildren().addAll(line, player1, player2, ball);

        AnimationTimer timer = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (now - lastUpdate >= 15_000_000 && !stop) {
                    gameUpdate();
                    lastUpdate = now;
                }
            }
        };

        timer.start();

        return root;
    }

    private void gameUpdate() {
        double x = ball.getLayoutX(),  y = ball.getLayoutY();

        if (x <= 10 && y > player1.getLayoutY() && y < player1.getLayoutY() + 80) dv = speedX;
        if (x >= W - 12.5 && y > player2.getLayoutY() && y < player2.getLayoutY() + 80) {
            speedX++;
            dv = -speedX;
        }
        if (y <= 0) dy = speedY;
        if (y >= H) dy = -speedY;

        ball.setLayoutX(ball.getLayoutX() + dv);
        ball.setLayoutY(ball.getLayoutY() + dy);

        // player1 in test
//        if (x < W/2 && player1.getLayoutY() > y) player1.setLayoutY(player1.getLayoutY() - 5);
//        if (x < W/2 && player1.getLayoutY() + 80 < y) player1.setLayoutY(player1.getLayoutY() + 5);

    }

    private void stopGame() {
        stop = !stop;
    }

    public static void main (String args[]) {
        launch(args);
    }
}
