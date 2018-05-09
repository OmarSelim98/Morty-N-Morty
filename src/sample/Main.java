package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;

import static javafx.scene.media.AudioClip.INDEFINITE;

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

        //background music
        try {
            final Task task = new Task() {

                @Override
                protected Object call() {
                    int s = INDEFINITE;
                    AudioClip audio = new AudioClip(getClass().getResource("../audio/music.mp3").toExternalForm());
                    audio.setVolume(0.25f);
                    audio.setCycleCount(s);
                    audio.play();
                    return null;
                }
            };
            Thread thread = new Thread(task);
            thread.start();
        }
        catch(Exception e){
            System.out.println("Error loading background music ");
        }

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    public static void ChangeScene(Scene scene){
        stage.setScene(scene);
    }
    public static void ChangeScene(Scene scene,String name){
        stage.setScene(scene);

    }
}