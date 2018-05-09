package sample.multiplayer;

import sample.Game;
import sample.Healthbar;
import sample.Player;

public class MultiPlayerGame extends Game {


    public MultiPlayerGame(){
        super();
        player1Body = "gameres/body.png";
        player1Arm = "gameres/arm.png";
        player2Body = "gameres/rickBody.png";
        player2Arm = "gameres/rickGun.png";
        player1 = new Player(player1Body, player1Arm,this,100,200,"player1");
        player2 = new Player(player2Body, player2Arm,this,900,200,"player2");
        healthbar1 = new Healthbar("Morty",player1);
        healthbar2 = new Healthbar("Rick",player2);
        root.getChildren().addAll(player1.imgView,player1.armView,player2.imgView,player2.armView,healthbar1,healthbar2);
    }
}
