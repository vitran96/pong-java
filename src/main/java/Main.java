import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

    }

    private Parent createContent() {
        root = new Pane();
        root.setPrefSize(W, H);
        root.setStyle("-fx-background-color: black");

        line = new Line(W / 2, 0, W / 2, H);
        line.setStroke(Color.WHITE);

        bot = new Rectangle(10, 80);
        bot.setLayoutX(0);


        return root;
    }
}