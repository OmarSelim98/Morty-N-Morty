package sample;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;


public class Healthbar extends Group {
    private Rectangle Health;
    private Rectangle frame;
    private Player player;
    private Label PlayerTag;
    public Healthbar(String Name,Player player)
    {
        this.player= player;

        this.Health= new Rectangle(player.getHealth()*1.5f,10);
        this.Health.setFill(Color.GREEN);
        this.Health.setArcHeight(10);
        this.Health.setArcWidth(10);
        this.frame= new Rectangle(player.getHealth()*1.5f,10);
        this.frame.setStroke(Color.BLACK);
        this.frame.setFill(Color.LIGHTGRAY);
        this.frame.setArcHeight(10);
        this.frame.setArcWidth(10);

        this.Health.setLayoutX(this.player.imgView.getTranslateX());
        this.Health.setLayoutY(this.player.imgView.getTranslateY());
        this.frame.setLayoutX(this.player.imgView.getTranslateX());
        this.frame.setLayoutY(this.player.imgView.getTranslateY());
        this.PlayerTag= new Label(Name);
        this.frame.setStrokeWidth(3);
        this.frame.setStrokeType(StrokeType.OUTSIDE);
        this.PlayerTag.setLayoutX(Health.getLayoutX());
        this.PlayerTag.setLayoutY(Health.getLayoutY()-30);
        this.PlayerTag.setTextFill(Color.WHITE);
        this.setVisible(true);
        this.getChildren().addAll(PlayerTag,this.frame,this.Health);





    }
    public void UpdateHealthbar()
    {
        if(this.Health.getWidth()<=120)
        {
            this.Health.setFill(Color.YELLOWGREEN);
        }
        if(this.Health.getWidth()<=90)
        {
            this.Health.setFill(Color.YELLOW);
        }
        if(this.Health.getWidth()<=60)
        {
            this.Health.setFill(Color.RED);
        }
        if(this.Health.getWidth()<=30)
        {
            this.Health.setFill(Color.DARKRED);
        }
        Health.setWidth(this.player.getHealth()*1.5);


        this.Health.setLayoutX(this.player.imgView.getTranslateX()-40);
        this.Health.setLayoutY(this.player.imgView.getTranslateY()-30);
        this.frame.setLayoutX(this.player.imgView.getTranslateX()-40);
        this.frame.setLayoutY(this.player.imgView.getTranslateY()-30);
        this.PlayerTag.setLayoutX(Health.getLayoutX());
        this.PlayerTag.setLayoutY(Health.getLayoutY()-30);

    }
}
