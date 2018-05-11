package sample;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.*;

public class Wall {
    private World world;
     private BodyDef wallDef = new BodyDef();
    public Wall(World world, int PosX,int PosY)//PosX and PosY in JBox2d world
    {
        this.world=  world;
        wallDef.position.set(PosX,PosY);
        wallDef.type= BodyType.STATIC;
        Body wallBody = world.createBody(wallDef);
        PolygonShape wallShape = new PolygonShape();
        wallShape.setAsBox(0,100);
        FixtureDef wallFixture = new FixtureDef();
        wallFixture.shape = wallShape;
        wallBody.createFixture(wallFixture);


    }
}
