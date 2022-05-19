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
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {

    // Players
    private Rectangle player2, player1;

    // Ball
    private Circle ball;

    // Scores
    private Text scoreField1, scoreField2;
    private int score1, score2;
    // Window size
    private final int W = 1000, H = 400;

    // Ball's speed and direction in 2D
    private int speed = 3, dx = speed, dy = speed;

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

    // Set up and start game
    private Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(W, H);
        root.setStyle("-fx-background-color: black");

        Line line = new Line(W / 2, 0, W / 2, H);
        line.setStroke(Color.WHITE);

        // Set player 1 on the left
        player1 = new Rectangle(10, 80, Color.WHITE);
        player1.setLayoutX(10);
        player1.setLayoutY(H/2 - 40);

        // Set player 2 on the right
        player2 = new Rectangle(10, 80, Color.WHITE);
        player2.setLayoutX(W - 20);
        player2.setLayoutY(H/2 - 40);

        // Set ball in the middle
        ball = new Circle(5);
        resetBall();

        // Set player 1 score
        scoreField1 = new Text(String.valueOf(score1));
        scoreField1.setLayoutX(W/2 - 50);
        scoreField1.setLayoutY(40);
        scoreField1.setScaleX(5);
        scoreField1.setScaleY(5);
        scoreField1.setFill(Color.WHITE);

        // Set player 2 score
        scoreField2 = new Text(String.valueOf(score2));
        scoreField2.setLayoutX(W/2 + 45);
        scoreField2.setLayoutY(40);
        scoreField2.setScaleX(5);
        scoreField2.setScaleY(5);
        scoreField2.setFill(Color.WHITE);

        root.getChildren().addAll(line, player1, player2, ball, scoreField1, scoreField2);
        System.out.println(player2.getLayoutX());

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
        double x = ball.getLayoutX(),  y = ball.getLayoutY(),
                p1Y = player1.getLayoutY(), p2Y = player2.getLayoutY();

        if (x <= 5) {
            score2++;
            scoreField2.setText(String.valueOf(score2));
            nextGame();
            return;
        } else if (x >= W - 5) {
            score1++;
            scoreField1.setText(String.valueOf(score1));
            nextGame();
            return;
        }

        // Check X pos
        final boolean changeXDirection = x <= 20 && x >= 19 && y > p1Y && y < p1Y + 80 ||
            x >= W - 30 && x <= W - 29 && y > p2Y && y < p2Y + 80;
        if (changeXDirection)
            dx = -dx;

        // Check Y pos
        final boolean changeYDirection = y <= 5 || y >= H - 5;
        if (changeYDirection)
            dy = -dy;

        ball.setLayoutX(x + dx);
        ball.setLayoutY(y + dy);
    }

    private void nextGame() {
        resetBall();
        resetDirection();
    }

    private void resetBall() {
        ball.setFill(Color.WHITE);
        ball.setLayoutX(W/2);
        ball.setLayoutY(H/2);
    }

    private void resetDirection() {
        dx = dy = speed;
    }

    private void stopGame() {
        stop = !stop;
    }

    public static void main (String[] args) {
        launch(args);
    }
}
