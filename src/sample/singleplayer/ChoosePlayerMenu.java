package sample.singleplayer;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import sample.GameMenu;
import sample.Main;
import sample.Player;


public class ChoosePlayerMenu extends GameMenu{
    private final Button singlePlayer;
    public Image player2Body
            ,player2Arm
            ,player1Arm
            ,player1Body;
    public String player1Name,player2Name;

    private Group root = new Group();// group to hold all elements in scene
    private Scene scene = new Scene(root, 1000, 721);// scene to show all components
    private VBox Images;//vbox for images to be in the same place of invisible vbox

    Image rick = new Image("gameres/rickBody.png");
    Image morty = new Image("gameres/body.png");
    Image Background = new Image("menures/menuBck2_preview.png");
    Image Single = new Image("menures/singleplayer_preview.png");

    ImageView player = new ImageView();
    ImageView BackgroundImage = new ImageView(Background);
    ImageView SingleImage = new ImageView(Single);

    public ChoosePlayerMenu(){
        Main.ChangeScene(this.getScene());
        player.setCache(true);
        SingleImage.setCache(true);
        BackgroundImage.setCache(true);
        player.setImage(rick);

        Button region = new Button();
        region.setMinHeight(rick.getHeight());
        region.setMinWidth(rick.getWidth());

        singlePlayer = new Button();
        singlePlayer.setMinWidth(MIN_WIDTH);
        singlePlayer.setMinHeight(MIN_HEIGHT);
        InvisibleButtons = new VBox(50, region, singlePlayer);
        InvisibleButtons.setAlignment(Pos.TOP_CENTER);
        InvisibleButtons.setLayoutX(1000/2-302/2);
        InvisibleButtons.setLayoutY(300);
        InvisibleButtons.setOpacity(0);


        Images = new VBox(50,player,SingleImage);
        Images.setAlignment(Pos.TOP_CENTER);
        Images.setLayoutX(1000/2-302/2);
        Images.setLayoutY(300);

        this.root.getChildren().addAll(BackgroundImage,Images,InvisibleButtons);

        region.setOnMouseClicked(event -> {
            if(player.getImage()==rick){
                player.setImage(morty);
            }
            else{
                player.setImage(rick);
            }
        });

        this.singlePlayer.setOnMouseClicked(event -> {
            System.out.println("Button clicked");
            if(player.getImage()==rick){
                player1Body = new Image("gameres/rickBody.png", Player.playerWidth,Player.playerHeight,false,false);
                player1Arm =  new Image("gameres/rickGun.png",Player.arm_width,Player.arm_height,false,false);
                player2Body  = new Image( "gameres/body.png", Player.playerWidth,Player.playerHeight,false,false);
                player2Arm  = new Image( "gameres/arm.png",Player.arm_width,Player.arm_height,false,false);
                player1Name = "Rick";
                player2Name = "Morty";
            }
            else {
                player2Body = new Image( "gameres/rickBody.png", Player.playerWidth,Player.playerHeight,false,false);
                player2Arm = new Image( "gameres/rickGun.png",Player.arm_width,Player.arm_height,false,false);
                player1Body  = new Image( "gameres/body.png", Player.playerWidth,Player.playerHeight,false,false);
                player1Arm  = new Image( "gameres/arm.png",Player.arm_width,Player.arm_height,false,false);
                player1Name = "Morty";
                player2Name = "Rick";
            }
            SinglePlayerGame game = new SinglePlayerGame(this);
            Main.ChangeScene(game.getScene());
        });
    }

    public Scene getScene(){
        return this.scene;
    }

}
