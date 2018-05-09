package sample;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;

public class PowerSlider extends Group {
    private Rectangle power;
    private Rectangle frame;
    private Label nameLbl;
    public PowerSlider(){
        this.frame = new Rectangle(300,25);
        this.frame.setStroke(Color.WHITE);
        this.frame.setStrokeWidth(6);
        this.frame.setStrokeType(StrokeType.OUTSIDE);
        this.frame.setArcHeight(10);
        this.frame.setArcWidth(10);
        this.power = new Rectangle(0,25, Color.RED);
        this.power.setArcWidth(10);
        this.power.setArcHeight(10);
        this.nameLbl = new Label("Power");
        this.nameLbl.setFont(Font.font("sans",18));


        this.nameLbl.setTranslateX((this.frame.getWidth()/2)-20);
        this.nameLbl.setTranslateY((this.frame.getHeight()/2)-10);

        this.getChildren().addAll(this.frame,this.power,this.nameLbl);

        this.setTranslateX(500-150);
        this.setTranslateY(100);
    }
    public void updatePowerSlider(double width){
        this.power.setWidth(width);
    }
}
