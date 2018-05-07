package sample;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;

public class Game{

    public static final int MOVE_AMOUNT = 100;

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

    //ArrayList<Ball> ballsList;
    Ball ball;

    //Turns Stuff
    private int player1Balls = 10
            , player2Balls = 10
            ,currentTurn = 1
            ,player1Moves = 4
            ,player2Moves = 4;

    private boolean canPlay = true;



    public Game(){


        Vec2 gravity = new Vec2(0,-10);
        world = new World(gravity,false);
        sample.ContactListener listener = new sample.ContactListener(this);
        world.setContactListener(listener);

        //ballsList = new ArrayList<>();

        player1 = new Player(this,300,200,"player1");
        player2 = new Player(this,600,200,"player2");
        root.getChildren().addAll(player1.imgView,player1.armView,player2.imgView,player2.armView);
        //Wall
        //Def
        BodyDef wallDef = new BodyDef();
        wallDef.position.set(0f,-30f);
        //wallDef.type = BodyType.STATIC;
        //Body
        Body wallBody = world.createBody(wallDef);

        //player1.playerFixtureDef.filter.groupIndex=7;
        System.out.println(player1.playerFixtureDef.filter.groupIndex);

        //Fixture
        PolygonShape wallShape = new PolygonShape();
        wallShape.setAsBox(500,10);
        //Attach Fixture to body
        //wallBody.createFixture(wallFixture,5);
        FixtureDef wallFixture = new FixtureDef();
        wallFixture.shape = wallShape;
        wallFixture.filter.categoryBits = 0x0005;
        //wallFixture.density = 5;
        wallFixture.restitution = 0.5f;
        wallBody.createFixture(wallFixture);
        //wallFixture.isSensor;




        c.setWidth(1000);
        c.setHeight(1000);

        gc = c.getGraphicsContext2D();


        new AnimationTimer(){
            int cycleCount = 0;
            @Override
            public void handle(long now) {
                gc.clearRect(0, 0, 1000, 1000);
                world.step(1f / 60f, 10, 10);

                gc.setFill(Color.BLACK);
                gc.fillRect(wallX, wallY, wallW, wallH);
                gc.setStroke(Color.RED);

                gc.fillText("Current Turn " +String.valueOf(currentTurn),50,50);
                gc.fillText("Player 1 Balls  " + String.valueOf(player1Balls),50,70);
                gc.fillText("Player 2 Balls " +String.valueOf(player2Balls),50,90);
                gc.fillText("Player 1 Moves " +String.valueOf(player1Moves),200,70);
                gc.fillText("Player 2 Moves " +String.valueOf(player2Moves),200,90);
                //Distance distance = new Distance();


                if (ball != null) {
                    if (ball.ballBody.isActive()) {
                        ball.update();

                    } else {
                        root.getChildren().removeAll(ball.imgView);
                        world.destroyBody(ball.ballBody);
                    }
                }
                checkTurn();

/*                    for(Ball ball : ballsList){
                      if(ball!= null){
                          if(ball.ballBody.isActive())
                                ball.update();
                          else{
                              root.getChildren().removeAll(ball.imgView);
                              world.destroyBody(ball.ballBody);
                          }
                      }
                    }*/
/*                for (Ball ball:
                        ballsList) {
                        ball.ballFixture.filter.groupIndex=-7;
                    ball.ballFixture.filter.categoryBits=0x004;
                    ball.ballFixture.isSensor =true;

                    }*/

/*                    if(player1.playerBody.getContactList().other.m_fixtureList.m_isSensor){
                        System.out.println("player2 touched a ball");
                    }*/


                if (player1 != null) {
                    player1.update();
                    player1.update_arm();

                }

                if (player2 != null){
                    player2.update();
                    player2.update_arm();
                }

                   // System.out.println(sceneAngle);
            }
        }.start();

        scene.setOnMousePressed(e->{
            this.mouseStartX = e.getSceneX();
            this.mouseStartY = e.getSceneY();
        });

        scene.setOnMouseMoved(e -> {
            mouseSceneX = e.getSceneX();
            mouseSceneY = e.getSceneY();
            if (currentTurn==1) {
                this.sceneAngle = Math.toDegrees(Math.atan2(mouseSceneY - (player1.imgView.getTranslateY()), mouseSceneX - player1.imgView.getTranslateX()) - Math.PI / 2);
                player1.startArmRotation(sceneAngle);
            }
            else if(currentTurn==2){
                this.sceneAngle = Math.toDegrees(Math.atan2(mouseSceneY - (player2.imgView.getTranslateY()), mouseSceneX - player2.imgView.getTranslateX()) - Math.PI / 2);
                player2.startArmRotation(sceneAngle);
            }
            if(sceneAngle > 0){ // it breaks between 270 - 360 , as it becomes +ve
                sceneAngle = -(360 - sceneAngle);
            }
        });

        scene.setOnMouseDragged(e->{


            //System.out.println(this.sceneAngle);
        });

        scene.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.RIGHT){
                if(currentTurn==1) {
                    if(player1Moves>0) {
                        player1.move(MOVE_AMOUNT);
                        player1Moves--;
                    }
                }
                if(currentTurn==2) {
                    if(player2Moves>0) {
                        player2.move(MOVE_AMOUNT);
                        player2Moves--;
                    }
                }
            }
            else if(event.getCode() == KeyCode.LEFT){
                if(currentTurn==1) {
                    if(player1Moves>0) {
                        player1.move(-MOVE_AMOUNT);
                        player1Moves--;
                    }
                }
                if(currentTurn==2) {
                    if(player2Moves>0) {
                        player2.move(-MOVE_AMOUNT);
                        player2Moves--;
                    }
                }
            }
            if(event.getCode() == KeyCode.UP){
                //ballBody.applyLinearImpulse(new Vec2(0,50),ballBody.getWorldCenter());
            }
        });

        scene.setOnMouseReleased(event -> {
            this.mouseEndX = event.getSceneX();
            this.mouseEndY = event.getSceneY();
            if(canPlay) {
                if (this.currentTurn == 1) {
                    float ballX = (float) (player1.armView.getTranslateX() - (player1.getArmHeight() * Math.sin(Math.toRadians(sceneAngle))) + 10);
                    float ballY = (float) ((player1.armView.getTranslateY()) + (player1.getArmHeight() * Math.cos(Math.toRadians(sceneAngle))) + 10);
                    System.out.println("Ball X : "+ballX+" | Ball Y : "+ballY);
                    ball = new Ball(this, ballX, ballY, (float) (mouseEndX - mouseStartX), (float) (mouseStartY - mouseEndY), player1.armView, "player2");
                    ball.ballBody.setUserData(ball);
                    root.getChildren().add(ball.imgView);
                } else if (this.currentTurn == 2) {
                    float ballX = (float) (player2.armView.getTranslateX() - (player2.getArmHeight() * Math.sin(Math.toRadians(sceneAngle))) - 10);
                    float ballY = (float) ((player2.armView.getTranslateY()) + (player2.getArmHeight() * Math.cos(Math.toRadians(sceneAngle))) - 10);
                    System.out.println("Ball X : "+ballX+" | Ball Y : "+ballY);
                    ball = new Ball(this, ballX, ballY, (float) (mouseEndX - mouseStartX), (float) (mouseStartY - mouseEndY), player2.armView, "player1");
                    ball.ballBody.setUserData(ball);
                    root.getChildren().add(ball.imgView);
                }
            }
            canPlay =false;
        });
    }

    private void checkTurn() {
        if(ball!=null) {
            //this one was hard to figure out
            if (Math.abs(ball.ballBody.getLinearVelocity().x) < 10) {
                ball.ballBody.setActive(false);
                changeTurn();
            }
        }

    }

    void changeTurn(){
        if(this.currentTurn == 1){
            this.currentTurn = 2;
            this.player1Balls--;
        }
        else if(this.currentTurn ==2){
            this.currentTurn = 1;
            this.player2Balls--;
        }
        canPlay = true;
    }


    public float getXpx(Body body){
        return body.getPosition().x*meterToPixel;
    }
    public float getYpx(Body body){
        return -body.getPosition().y*meterToPixel;
    }




/*    public float calculateXImpulse(Body body,float x){
        x = x - body.getPosition().x*meterToPi xel;
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
