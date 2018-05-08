package  sample;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;



public class GameMenu {
    public static final int MIN_WIDTH = 302; //AlL buttons width
    public static final int MIN_HEIGHT = 77; // All buttons height
    //invisible buttons set under image views
    private Button s;
    private Button m;
    private Button h;
    private Button e;
    
    VBox InvisibleButtons;//invisible vbox to hold all buttons





    private Group root = new Group();// group to hold all elements in scene
    private Scene scene = new Scene(root, 1000, 721);// scene to show all components
    private VBox Images;//vbox for images to be in the same place of invisible vbox
    //all images for the menu
    Image Single = new Image("Menu res/singleplayer_preview.png");
    Image Multiplayer = new Image("Menu res/multiplayer_preview.png");
    Image Highscore = new Image("Menu res/highscore_preview.png");
    Image Exit = new Image("Menu res/exit_preview.png");
    Image Background = new Image("Menu res/menuBck2_preview.png");
    //image views to add elements to the scene

    ImageView SingleImage = new ImageView(Single);
    ImageView MultiplayerImage = new ImageView(Multiplayer);
    ImageView HighscoreImage = new ImageView(Highscore);
    ImageView ExitImage = new ImageView(Exit);
    ImageView BackgroundImage = new ImageView(Background);

    public GameMenu() {
        //sets the primary stage's scene to this.
        Main.ChangeScene(this.getScene());


        BackgroundImage.setCache(true);
        s = new Button();
        m= new Button();
        h = new Button();
        e= new Button();
        s.setMinWidth(MIN_WIDTH);
        s.setMinHeight(MIN_HEIGHT);
        m.setMinWidth(MIN_WIDTH);
        m.setMinHeight(MIN_HEIGHT);
        h.setMinWidth(MIN_WIDTH);
        h.setMinHeight(MIN_HEIGHT);
        e.setMinWidth(MIN_WIDTH);
        e.setMinHeight(MIN_HEIGHT);
        InvisibleButtons = new VBox(30,s,m,h,e);
        InvisibleButtons.setAlignment(Pos.TOP_CENTER);
        InvisibleButtons.setLayoutX(1000/2-302/2);
        InvisibleButtons.setLayoutY(300);
        InvisibleButtons.setOpacity(0);




        Images = new VBox(30,SingleImage,MultiplayerImage,HighscoreImage,ExitImage);
      m.setOnMouseClicked(e->{

         Main.ChangeScene(new Game().getScene());
      });
      e.setOnMouseClicked(e->{
          Main.stage.close();
      });
        Images.setAlignment(Pos.TOP_CENTER);
        Images.setLayoutX(1000/2-302/2);
        Images.setLayoutY(300);

        this.root.getChildren().addAll(BackgroundImage,Images,InvisibleButtons);



    }
    public Scene getScene()
    {
        return this.scene;
    }
}
