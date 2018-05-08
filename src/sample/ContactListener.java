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
            if (contact.isEnabled()) {
                //System.out.println("collision yabaaaaaaaa");
                System.out.println(contact.m_fixtureB.m_userData.toString()+" | "+contact.m_fixtureB.m_userData.toString());
                if (contact.m_fixtureB.m_userData.toString() == contact.m_fixtureA.m_userData.toString()) {
                    System.out.println(contact.m_fixtureB.m_userData.toString()+" | "+contact.m_fixtureB.m_userData.toString());
                    game.endTimer();
                    contact.m_fixtureB.getBody().setActive(false);
                    contact.m_fixtureB.destroy();
                    //Here we check the current turns , update health , apply impulse
                    if(game.getCurrentTurn() == 1) {
                        game.player2.inflictDamage(50);
                        contact.m_fixtureA.getBody().applyLinearImpulse(new Vec2(50,0),contact.m_manifold.localPoint);
                    }else if(game.getCurrentTurn() == 2){
                        game.player1.inflictDamage(50);
                        contact.m_fixtureA.getBody().applyLinearImpulse(new Vec2(-50,0),contact.m_manifold.localPoint);
                    }
                    game.decrementBalls();
                    game.changeTurn();
                    game.startTimer();
                    if(game.player1.getHealth() <= 0 || game.player2.getHealth() <= 0){
                        game.endTimer();
                        Main.ChangeScene(new GameMenu().getScene());
                    }
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
