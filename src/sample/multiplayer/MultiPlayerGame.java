package sample.multiplayer;

import javafx.scene.image.Image;
import sample.Game;
import sample.Healthbar;
import sample.Player;

public class MultiPlayerGame extends Game {


    public MultiPlayerGame(){
        super();
        player1Body = new Image("gameres/body.png");
        player1Arm = new Image("gameres/arm.png");
        player2Body = new Image("gameres/rickBody.png");
        player2Arm = new Image("gameres/rickGun.png");
        player1 = new Player(player1Body, player1Arm,this,100,200,player1Name);
        player2 = new Player(player2Body, player2Arm,this,900,200,player2Name);
        healthbar1 = new Healthbar("Morty",player1);
        healthbar2 = new Healthbar("Rick",player2);
        root.getChildren().addAll(player1.imgView,player1.armView,player2.imgView,player2.armView,healthbar1,healthbar2);
        gameLoop();

    }
}
