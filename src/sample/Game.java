package sample;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Game{

    public static final int MOVE_AMOUNT = 100;

    private final int meterToPixel = 30
            ,wallX = 0
            ,wallY = 600
            ,wallW = 1000
            ,wallH = 400;
            private double planetsAnimationAngle = 0;
    private double mouseX
            ,mouseY
            ,mouseSceneX = 0 //For Angle Calculations
            , mouseSceneY = 0 //For Angle Calculations
            ,sceneAngle = 0 //For Angle Of Player Body To mouse
            ,mouseStartX = 0 //For Impulse Calculations
            ,mouseStartY = 0 //For Impulse Calculations
            ,mouseVarX = 0  //For Impulse Calculations
            ,mouseVarY = 0 //For Impulse Calculations
            ,mouseEndX = 0  //For Impulse Calculations
            ,mouseEndY = 0; //For Impulse Calculations

    private World world;

    private Timer turnTimer;

    private PowerSlider powerslider;
    static int balls_num = 0;

    AudioClip wubba = new AudioClip(this.getClass().getResource("../../audio/wubba.mp3").toString())
            ,worldisreal = new AudioClip(this.getClass().getResource("../../audio/worldisreal.mp3").toString())
            ,woah = new AudioClip(this.getClass().getResource("../../audio/woah.mp3").toString());
    Random rnd = new Random();
    GraphicsContext gc;
    Canvas c = new Canvas();
    public Group root = new Group();
    Scene scene = new Scene(root, 1000, 800);
    Label turnLbl = new Label("Morty's Turn!");
    public Player player1,player2;
    public Healthbar healthbar1;
    public Healthbar healthbar2;
    //ArrayList<Ball> ballsList;
    Ball ball;

    public Image player1Body,
        player1Arm,
        player2Body,
        player2Arm;

    public String player1Name = "Morty"
            ,player2Name = "Rick";



    ImageView background
            ,ground
            ,planet1
            ,planet2;


    //Turns Stuff
    private int player1Balls = 10
            , player2Balls = 10
            ,currentTurn = 1
            ,player1Moves = 4
            ,player2Moves = 4;

    private boolean canPlay = true;



    public Game(){


        Vec2 gravity = new Vec2(0,-8);
        world = new World(gravity,false);
        sample.ContactListener listener = new sample.ContactListener(this);
        world.setContactListener(listener);

        //ballsList = new ArrayList<>();
        background = new ImageView(new Image("gameres/bck.png"));
        ground = new ImageView(new Image("gameres/ground.png"));
        planet1 = new ImageView(new Image("gameres/planet1.png"));
        planet2 = new ImageView(new Image("gameres/planet2.png"));
        background.setTranslateX(0);
        background.setTranslateY(0);
        ground.setTranslateX(wallX);
        ground.setTranslateY(wallY);
        planet1.setTranslateX(100);
        planet1.setTranslateY(150);
        planet2.setTranslateX(700);
        planet2.setTranslateY(150);



        turnLbl.setFont(Font.font("sans",22));
        turnLbl.setTextFill(Color.WHITE);
        turnLbl.setTranslateX(400);
        turnLbl.setTranslateY(10);
        root.getChildren().addAll(background,c,ground,planet1,planet2,turnLbl);

        this.powerslider = new PowerSlider();
        //Wall
        //Def
        BodyDef wallDef = new BodyDef();
        wallDef.position.set(0f,-30f);
        //wallDef.type = BodyType.STATIC;
        //Body
        Body wallBody = world.createBody(wallDef);

        //player1.playerFixtureDef.filter.groupIndex=7;
        //System.out.println(player1.playerFixtureDef.filter.groupIndex);

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



        startTimer(); // Here we start the turns timer.

        //Action Handlers
        handlePlayerMovement();

        handleMouseClicks();

        handleMouseMovement();

        handleDragging();

        handleMouseRelease();
    }


    public void gameLoop() {
        new AnimationTimer(){
            int cycleCount = 0;
            @Override
            public void handle(long now) {
                drawFrame();

                changeLabel();
                if (ball != null) {
                    if (ball.ballBody.isActive()) {
                        ball.update();
                        if(ball.getImgView().getTranslateX()<0||ball.getImgView().getTranslateX()>1000){
                            root.getChildren().removeAll(ball.imgView);
                            world.destroyBody(ball.ballBody);
                            ball=null;
                            changeTurn();
                        }
                    } else {
                        root.getChildren().removeAll(ball.imgView);
                        world.destroyBody(ball.ballBody);
                    }
                }

                healthbar1.UpdateHealthbar();
                healthbar2.UpdateHealthbar();

                if(planetsAnimationAngle >= 360){
                    planetsAnimationAngle = 0;
                }else {
                    planetsAnimationAngle += 0.1;
                }

                planet1.setTranslateX(100+(Math.sin(planetsAnimationAngle)*25));
                planet2.setTranslateX(670-(Math.sin(planetsAnimationAngle)*25));
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
    }

    private void drawFrame() {
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
    }

    //Action Listeners
    private void handleMouseRelease() {
        scene.setOnMouseReleased(event -> {
            this.mouseEndX = event.getSceneX();
            this.mouseEndY = event.getSceneY();
            double impPower = this.mouseVarX/10;
            if(impPower <= 1){
                impPower = 1;
            }else if(impPower >= 30){
                impPower = 30;
            }
            if(canPlay) {
                if (this.currentTurn == 1) {
                    float ballX = (float) (player1.armView.getTranslateX() - (player1.getArmHeight() * Math.sin(Math.toRadians(sceneAngle))) + 10);
                    float ballY = (float) ((player1.armView.getTranslateY()) + (player1.getArmHeight() * Math.cos(Math.toRadians(sceneAngle))) + 10);
                    //System.out.println("Ball X : "+ballX+" | Ball Y : "+ballY);
                    Vec2 imp = new Vec2((float)(Math.sin(Math.toRadians(sceneAngle))) , (float)(Math.cos(Math.toRadians(sceneAngle))));

                    imp.set((float)(-imp.x*impPower),(float)(-imp.y*impPower));
                    ball = new Ball(this, ballX, ballY,imp, player1.armView, player2Name);
                    ball.ballBody.setUserData(ball);
                    root.getChildren().add(ball.imgView);
                    int audiornd = rnd.nextInt(3);
                    System.out.println(audiornd);
                    if(audiornd == 0){
                        //woah.play();
                    }else if(audiornd == 1){

                    }
                } else if (this.currentTurn == 2) {
                    float ballX = (float) (player2.armView.getTranslateX() - (player2.getArmHeight() * Math.sin(Math.toRadians(sceneAngle))) - 10);
                    float ballY = (float) ((player2.armView.getTranslateY()) + (player2.getArmHeight() * Math.cos(Math.toRadians(sceneAngle))) - 10);
                    //System.out.println("Ball X : "+ballX+" | Ball Y : "+ballY);
                    Vec2 imp = new Vec2((float)(Math.sin(Math.toRadians(sceneAngle))) , (float)(Math.cos(Math.toRadians(sceneAngle))));
                    imp.set((float)(-imp.x*(this.mouseVarX/10)),(float)(-imp.y*(this.mouseVarX/10)));
                    ball = new Ball(this, ballX, ballY, imp, player2.armView, player1Name);
                    ball.ballBody.setUserData(ball);
                    root.getChildren().add(ball.imgView);
                    int audiornd = rnd.nextInt(3);
                    System.out.println(audiornd);
                    if(audiornd == 0){
                        wubba.play();
                    }else if(audiornd == 1){
                        worldisreal.play();
                    }
                }
            }
            canPlay =false;
            this.powerslider.updatePowerSlider(0);
            this.root.getChildren().remove(this.powerslider);
        });
    }
    private void handleDragging() {
        scene.setOnMouseDragged(e->{
            this.mouseEndX = e.getSceneX();
            this.mouseVarX = Math.abs(mouseEndX - mouseStartX);
            if(this.mouseVarX >= 300){
                this.mouseVarX = 300;
            }else if(this.mouseVarX <= 10){
                this.mouseVarX = 10;
            }
            this.powerslider.updatePowerSlider(this.mouseVarX);
            System.out.println("Impulse power : "+this.mouseVarX);
        });
    }
    private void handleMouseMovement() {
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
    }
    private void handleMouseClicks() {
        scene.setOnMousePressed(e->{
            this.mouseStartX = e.getSceneX();
            this.mouseStartY = e.getSceneY();
            if(canPlay)
                this.root.getChildren().add(this.powerslider);
        });
    }
    private void handlePlayerMovement() {
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
    }



    /*private void checkTurn() {
        if(ball!=null) {
            //this one was hard to figure out
            if (Math.abs(ball.ballBody.getLinearVelocity().x) < 10) {
                ball.ballBody.setActive(false);
                changeTurn();
            }
        }

    }*/

    /**
     * Check Turn , Then Change it.
     * */
    void changeTurn(){
        if(this.currentTurn == 1){
            this.currentTurn = 2;

        }
        else if(this.currentTurn ==2){
                this.currentTurn = 1;
        }
        canPlay = true;
    }
    void changeLabel(){
        if(this.currentTurn == 1){
            this.turnLbl.setText(player1Name + "'s Turn!");
        }else if(this.currentTurn ==2){
            this.turnLbl.setText(player2Name + "'s Turn!");
        }
    }
    /**
     * Decerment the number of balls , depending on the current turn.
     * */
    void decrementBalls(){
        if(this.currentTurn == 1){
            this.player1Balls--;
        }
        else if(this.currentTurn ==2){
            this.player2Balls--;
        }
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
    public int getCurrentTurn(){
        return this.currentTurn;
    }

    /**
     * Initiate the task timer . Change the turn after 5 seconds.
     */
    public void startTimer(){
        turnTimer = new Timer();
        turnTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                endTimer();
                if(ball != null)
                    ball.ballBody.setActive(false);
                changeTurn();

                startTimer();
                this.cancel();
            }
        },5000);

        System.out.println("Timer Started!");
    }
    /**.
     * Cancel the current task
     * */
    public void endTimer(){
        this.turnTimer.cancel();
        this.turnTimer.purge();
        System.out.println("Timer Ended");
    }

    public void stopAudio(){
        wubba.stop();
        worldisreal.stop();
        woah.stop();
    }
}
