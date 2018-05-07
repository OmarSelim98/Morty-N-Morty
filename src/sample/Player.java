package sample;

import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.shapes.*;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.jbox2d.dynamics.contacts.Contact;

public class Player  {
    Game game;
    public static final float RESTITUTION = 0.2f;
    Body playerBody;

    private String name;

    private final int meterToPixel = 30;

    private int health = 100;
    private final int width = 75;
    private final int height = 150;
    private final int arm_width = 16;
    private final int arm_height = 75;
    private final int arm_x = 23;
    private final int arm_y = 60;
    Rotate arm_rotate = new Rotate(0,arm_width/2,7);
    ImageView imgView = new ImageView( new Image("sample/body.png",75,135,false,false));
    ImageView armView = new ImageView(new Image("sample/arm.png",arm_width,arm_height,false,false));

    BodyDef playerDef;
    PolygonShape playerShape;
    FixtureDef playerFixtureDef;

    public Player(Game game, int playerStartX, int playerStartY, String name){
        this.name = name;
        this.game = game;

        playerDef = new BodyDef();
        playerDef.position.set(playerStartX/meterToPixel,-((playerStartY)/meterToPixel));
        playerDef.type = BodyType.DYNAMIC;
        //Body
        playerBody = game.getWorld().createBody(playerDef);
        playerBody.setBullet(true);
        playerBody.setFixedRotation(true);
        //Dynamic Shape
        //playerShape = new;
        playerShape = new PolygonShape();
        playerShape.setAsBox((width/meterToPixel)/2,(height/meterToPixel)/2);
        //playerShape.m_radius = ballRadius/meterToPixel; //RADIUS IN METERS (30 PIXELS)
        //Fixture


        playerFixtureDef = new FixtureDef();
        playerFixtureDef.shape = playerShape;
        playerFixtureDef.restitution = RESTITUTION;
        playerFixtureDef.density = 5; // Density * Area = Mass
        playerFixtureDef.friction = 2f;//MAKE IT LESS SLOPPY
        playerFixtureDef.userData = this;
        playerFixtureDef.filter.categoryBits = 0x0004;
        //WE CAN ATTACH MULTIPLE FIXTURES TO ONE BODY , THE RESULT IS A COMPLEX SHAPE THAT HAS COMPLEX PHYSICS.
        playerBody.createFixture(playerFixtureDef); //HERE WE ATTACH THE BALL BODY ONLY TO THE BALL FIXTURE



        imgView.setTranslateX(playerBody.getPosition().x*meterToPixel);
        imgView.setTranslateY(-playerBody.getPosition().y*meterToPixel);
        armView.setTranslateX(imgView.getTranslateX()+arm_x);
        armView.setTranslateY(imgView.getTranslateY()+arm_y);
        armView.getTransforms().add(arm_rotate);
    }


    public void update(){
        imgView.setTranslateX(playerBody.getPosition().x*meterToPixel  - (width/2));
        imgView.setTranslateY((-(playerBody.getPosition().y*meterToPixel))-(height/2));
        if(playerBody.getPosition().y<-18){
            playerDef.position.y =-18;
        }
        if(this.health<=0){
            //this.playerBody.destroyFixture(playerBody.getFixtureList());
            game.root.getChildren().removeAll(this.imgView);
            game.getWorld().destroyBody(playerBody);
        }
        //System.out.println(playerBody.getMass());
    }

    public void update_arm(){
        armView.setTranslateX(imgView.getTranslateX()+arm_x);
        armView.setTranslateY(imgView.getTranslateY()+arm_y);
    }
    public void startArmRotation(double rotation){
        arm_rotate.setAngle(rotation);
    }
    //Getters And Setters

    public void move(int moveAmount){
        playerBody.applyLinearImpulse(new Vec2(moveAmount,0),playerBody.getPosition());
        System.out.println(playerBody.getPosition().toString());
    }


    //Getters And Setters
    public ImageView getImgView() {
        return imgView;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void inflictDamage(int damage){
        this.health-=damage;
    }

    public void setUserData(Object data){
        playerFixtureDef.userData = data;
    }
    public double getWidth(){
        return this.width;
    }
    public double getHeight(){
        return this.height;
    }
    public double getArmHeight(){
        return this.arm_height;
    }
    public double getArmWidth(){
        return this.arm_width;
    }


    @Override
    public String toString(){
        return this.getName();
    }

    public String getName() {
        return name;
    }
}
