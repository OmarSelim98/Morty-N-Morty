package sample;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {


    public Game game;


    @Override
    public void start(Stage primaryStage){
        game = new Game();

        primaryStage.setScene(game.getScene());
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

}