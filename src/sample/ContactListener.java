package sample;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.collision.Manifold;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.contacts.Contact;

public class ContactListener implements org.jbox2d.callbacks.ContactListener{

    private Game game;
    private Player player1,player2;

    public ContactListener(Game game){
        this.game = game;
        this.player1 = game.player1;
        this.player2 = game.player2;

    }

    @Override
    public void beginContact(Contact contact) {
        try {
            if (contact.isTouching()) {
                //System.out.println("collision yabaaaaaaaa");
                if (contact.m_fixtureB.m_userData.toString() == contact.m_fixtureA.m_userData.toString()) {
                    System.out.println("hopa");
                    contact.m_fixtureA.getBody().applyLinearImpulse(new Vec2(50,0),contact.m_manifold.localPoint);
                    contact.m_fixtureB.getBody().setActive(false);
                    contact.m_fixtureB.destroy();
                    game.player2.inflictDamage(10);
                    game.changeTurn();

                    //contact.getFixtureA().getUserData().equals()
                }
            }
        }
        catch (Exception e){

        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
