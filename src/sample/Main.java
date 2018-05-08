package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    static Stage stage;
    public Game game;


    @Override
    public void start(Stage primaryStage){
        this.stage = primaryStage;



        primaryStage.setScene(new GameMenu().getScene());
        primaryStage.setOnCloseRequest(e ->{
            Platform.exit();
            System.exit(0);
        });
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    public static void ChangeScene(Scene scene){
        stage.setScene(scene);
    }
}