package sample.singleplayer;

import sample.Game;
import sample.Healthbar;
import sample.Player;

public class SinglePlayerGame extends Game {

    public SinglePlayerGame(ChoosePlayerMenu menu){
        super();

        player1Name = menu.player1Name;
        player2Name = menu.player2Name;
        player1Body = menu.player1Body;
        player1Arm = menu.player1Arm;
        player2Body  = menu.player2Body;
        player2Arm  = menu.player2Arm;
        player1 = new Player(player1Body, player1Arm,this,100,200,player1Name);
        player2 = new Player(player2Body, player2Arm,this,900,200,player2Name);
        healthbar1 = new Healthbar("Morty",player1);
        healthbar2 = new Healthbar("Rick",player2);
        root.getChildren().addAll(player1.imgView,player1.armView,player2.imgView,player2.armView,healthbar1,healthbar2);
        gameLoop();
    }
}
