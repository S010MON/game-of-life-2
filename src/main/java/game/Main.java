package game;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application
{
    private Stage stage;
    private Game game;

    @Override
    public void start(Stage stage) throws IOException
    {
        this.stage = stage;
        stage.setTitle("Game Of Life 2");
        game = new Game();
        Scene scene = new Scene(game, Screen.getWidth(), Screen.getHeight());
        scene.setOnKeyPressed(e -> game.handleKey(e));
        scene.setOnMouseMoved(e -> game.handleMouseMoved(e));
        scene.setOnMousePressed(e -> game.handleMousePressed(e));
        scene.setFill(Color.BLACK);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}