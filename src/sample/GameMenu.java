package  sample;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import sample.multiplayer.MultiPlayerGame;
import sample.singleplayer.ChoosePlayerMenu;


public class GameMenu {
    public static final int MIN_WIDTH = 302; //AlL buttons playerWidth
    public static final int MIN_HEIGHT = 77; // All buttons playerHeight
    //invisible buttons set under image views
    private Button singlePlayer;
    private Button multiPlayer;
    private Button highScores;
    private Button exitGame;
    
    protected VBox InvisibleButtons;//invisible vbox to hold all buttons

    private Group root = new Group();// group to hold all elements in scene
    private Scene scene = new Scene(root, 1000, 721);// scene to show all components
    private VBox Images;//vbox for images to be in the same place of invisible vbox
    //all images for the menu
    Image Single = new Image("menures/singleplayer_preview.png");
    Image Multiplayer = new Image("menures/multiplayer_preview.png");
    Image Highscore = new Image("menures/highscore_preview.png");
    Image Exit = new Image("menures/exit_preview.png");
    Image Background = new Image("menures/menuBck2_preview.png");
    //image views to add elements to the scene

    ImageView SingleImage = new ImageView(Single);
    ImageView MultiplayerImage = new ImageView(Multiplayer);
    ImageView HighscoreImage = new ImageView(Highscore);
    ImageView ExitImage = new ImageView(Exit);
    ImageView BackgroundImage = new ImageView(Background);

    public GameMenu() {
        //sets the primary stage'singlePlayer scene to this.
        Main.ChangeScene(this.getScene());


        BackgroundImage.setCache(true);
        singlePlayer = new Button();
        multiPlayer = new Button();
        highScores = new Button();
        exitGame = new Button();
        singlePlayer.setMinWidth(MIN_WIDTH);
        singlePlayer.setMinHeight(MIN_HEIGHT);
        multiPlayer.setMinWidth(MIN_WIDTH);
        multiPlayer.setMinHeight(MIN_HEIGHT);
        highScores.setMinWidth(MIN_WIDTH);
        highScores.setMinHeight(MIN_HEIGHT);
        exitGame.setMinWidth(MIN_WIDTH);
        exitGame.setMinHeight(MIN_HEIGHT);
        InvisibleButtons = new VBox(30, singlePlayer, multiPlayer, highScores, exitGame);
        InvisibleButtons.setAlignment(Pos.TOP_CENTER);
        InvisibleButtons.setLayoutX(1000/2-302/2);
        InvisibleButtons.setLayoutY(300);
        InvisibleButtons.setOpacity(0);




        Images = new VBox(30,SingleImage,MultiplayerImage,HighscoreImage,ExitImage);

        singlePlayer.setOnMouseClicked(event -> {
            Main.ChangeScene(new ChoosePlayerMenu().getScene());
        });

      multiPlayer.setOnMouseClicked(e->{

         Main.ChangeScene(new MultiPlayerGame().getScene());
      });
      exitGame.setOnMouseClicked(e->{
          Main.stage.close();
      });
        Images.setAlignment(Pos.TOP_CENTER);
        Images.setLayoutX(1000/2-302/2);
        Images.setLayoutY(300);

        this.root.getChildren().addAll(BackgroundImage,Images,InvisibleButtons);



    }
    public Scene getScene(){
        return this.scene;
    }
}
