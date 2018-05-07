package sample;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.jbox2d.dynamics.contacts.Contact;

import java.util.ArrayList;

public class Game{

    private final int meterToPixel = 30
            ,wallX = 0
            ,wallY = 600
            ,wallW = 1000
            ,wallH = 400;
    private double mouseX
            ,mouseY
            ,mouseSceneX = 0
            , mouseSceneY = 0
            ,sceneAngle = 0
            ,mouseStartX = 0
            ,mouseStartY = 0
            ,mouseEndX = 0
            ,mouseEndY = 0;
    private boolean mouse_pressed = false;

    private World world;

    static int balls_num = 0;

    GraphicsContext gc;
    Canvas c = new Canvas();
    Group root = new Group(c);
    Scene scene = new Scene(root, 1000, 800);


    Player player1,player2;

    ArrayList<Ball> ballsList;

    public Game(){


        Vec2 gravity = new Vec2(0,-10);
        world = new World(gravity,false);

        ballsList = new ArrayList<>();

        player1 = new Player(this,300,200);
        player2 = new Player(this,600,200);
        root.getChildren().addAll(player1.imgView,player1.armView,player2.imgView);
        //Wall
        //Def
        BodyDef wallDef = new BodyDef();
        wallDef.position.set(wallX/meterToPixel,-((wallY/meterToPixel)+(wallH/meterToPixel)/2));
        wallDef.type = BodyType.STATIC;
        //Body
        Body wallBody = world.createBody(wallDef);

        //Fixture
        PolygonShape wallFixture = new PolygonShape();
        wallFixture.setAsBox((wallW/meterToPixel),(wallH/meterToPixel)/2);
        //Attach Fixture to body
        wallBody.createFixture(wallFixture,1);




        c.setWidth(1000);
        c.setHeight(1000);

        gc = c.getGraphicsContext2D();


        new AnimationTimer(){
            int cycleCount = 0;
            @Override
            public void handle(long now) {
                gc.clearRect(0,0,1000,1000);
                world.step(1f/60f,10,10);

                gc.setFill(Color.BLACK);
                gc.fillRect(wallX,wallY,wallW,wallH);
                gc.setStroke(Color.RED);


                    for(int i = 0 ; i < ballsList.size();i++){
                      if(ballsList.get(i) != null){
                          ballsList.get(i).update();
                      }
                    }

                if(player1!=null) {
                    player1.update();
                    player1.update_arm();
                }

                if(player2!=null)
                    player2.update();

                   // System.out.println(sceneAngle);
            }
        }.start();

        scene.setOnMousePressed(e->{
            this.mouseStartX = e.getSceneX();
            this.mouseStartY = e.getSceneY();
        });
        scene.setOnMouseDragged(e->{
            mouseSceneX = e.getSceneX();
            mouseSceneY = e.getSceneY();

            this.sceneAngle = Math.toDegrees(Math.atan2(mouseSceneY-(player1.imgView.getTranslateY()),mouseSceneX-player1.imgView.getTranslateX()) - Math.PI / 2);
            if(sceneAngle > 0){ // it breaks between 270 - 360 , as it becomes +ve
                sceneAngle = -(360 - sceneAngle);
            }
            //System.out.println(this.sceneAngle);
            player1.startArmRotation(sceneAngle);
        });
        scene.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.RIGHT){

            }else if(event.getCode() == KeyCode.LEFT){
                //ballBody.applyForce(new Vec2(-75,0),ballBody.getWorldCenter());
                //ball = new Ball(world,50,50);
                System.out.println("Creating new ball");
            }
            if(event.getCode() == KeyCode.UP){
                //ballBody.applyLinearImpulse(new Vec2(0,50),ballBody.getWorldCenter());
            }
        });

        scene.setOnMouseReleased(event -> {
            this.mouseEndX = event.getSceneX();
            this.mouseEndY = event.getSceneY();
            float ballX = (float)(player1.armView.getTranslateX()-(player1.getArmHeight()*Math.sin(Math.toRadians(sceneAngle))));
            float ballY = (float)((player1.armView.getTranslateY())+(player1.getArmHeight()*Math.cos(Math.toRadians(sceneAngle))));
            //System.out.println("Ball X : "+ballX+" | Ball Y : "+ballY);
            ballsList.add(new Ball(this,ballX,ballY,(float)(mouseEndX-mouseStartX),(float)(mouseStartY-mouseEndY),player1.armView));
            Ball ball = ballsList.get(balls_num);
            ball.ballBody.setUserData(ball);
            root.getChildren().add(ball.imgView);
            balls_num ++;
        });
    }


    public float getXpx(Body body){
        return body.getPosition().x*meterToPixel;
    }
    public float getYpx(Body body){
        return -body.getPosition().y*meterToPixel;
    }



/*    public float calculateXImpulse(Body body,float x){
        x = x - body.getPosition().x*meterToPixel;
        //100 px = 10 imp
        x = x/5;
        System.out.println("X Impulse = " + x);
        return x;
    }
    public float calculateYImpulse(Body body,float y){
        y = (-ballBody.getPosition().y*meterToPixel) - y;
        y = y/5;
        System.out.println("Y Impulse = " + y);
        return y;
    }*/


    public Scene getScene() {
        return scene;
    }

    public World getWorld() {
        return world;
    }


}
