package sample;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;

public class Ball {
    public static final float RESTITUTION = 0.75f;
    Body ballBody;

    private final float ballRadius = 30f;

    private final int meterToPixel = 30;

    private final float degToRad = (float)(Math.PI/180)
            ,radToDeg = (float)(180/Math.PI);

    private ImageView arm_view;

    ImageView imgView =new ImageView( new Image("sample/circle.png",30,30,false,false));

    BodyDef ballDef;
    CircleShape ballShape;
    FixtureDef ballFixture;

    public Ball(Game game,float ballStartX,float ballStartY,float mouseX,float mouseY,ImageView arm_view){
        // Ball
        //Def
        ballDef = new BodyDef();
        ballDef.position.set(ballStartX/meterToPixel,-(ballStartY)/meterToPixel);
        ballDef.type = BodyType.DYNAMIC;
        //Body
        ballBody = game.getWorld().createBody(ballDef);
        //Dynamic Shape
        ballShape = new CircleShape();
        ballShape.m_p.set(0,0); // SET SHAPE'S POSITION RELATIVE TO BODY'S POSITION
        ballShape.m_radius = (ballRadius/meterToPixel)/2; //RADIUS IN METERS (30 PIXELS)
        //Fixture
        ballFixture = new FixtureDef();
        ballFixture.shape = ballShape;
        ballFixture.restitution = RESTITUTION;
        ballFixture.density = 1.5f; // Density * Area = Mass
        ballFixture.friction = 0.1f;//MAKE IT LESS SLOPPY
        //WE CAN ATTACH MULTIPLE FIXTURES TO ONE BODY , THE RESULT IS A COMPLEX SHAPE THAT HAS COMPLEX PHYSICS.
        ballFixture.userData = this;
        ballBody.createFixture(ballFixture); //HERE WE ATTACH THE BALL BODY ONLY TO THE BALL FIXTURE
        this.arm_view = arm_view;
        ballBody.applyLinearImpulse(new Vec2(CalculateXImpulse(mouseX),CalculateYImpulse(mouseY)),ballBody.getWorldCenter());
        imgView.setTranslateX(ballBody.getPosition().x*meterToPixel);
        imgView.setTranslateY(-ballBody.getPosition().y*meterToPixel);
        imgView.setCache(true);
    }


    public void update(){
        imgView.setTranslateX((ballBody.getPosition().x*meterToPixel)-ballRadius);
        imgView.setTranslateY((-ballBody.getPosition().y*meterToPixel)-ballRadius);
        imgView.setRotate(-ballBody.getAngle()*radToDeg);
    }



    //Getters And Setters

    public ImageView getImgView() {
        return imgView;
    }

    public float CalculateXImpulse(float x){
        float imp_x = (float)(x/5);
        System.out.println(imp_x);
        if(imp_x>20){
            return 20;
        }
        else if(imp_x < -20){
            return -20;
        }else{
            return imp_x;
        }
    }
    public float CalculateYImpulse(float y){
        float imp_y = (float)(y/5);
        System.out.println(imp_y);
        if(imp_y>20){
            return 20;
        }else if(imp_y<-20){
            return -20;
        }else {
            return imp_y;
        }
    }
}
