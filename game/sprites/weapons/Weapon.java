package game.sprites.weapons;

import game.sprites.FightingSprite;
import game.sprites.Sprite;

import java.awt.image.BufferedImage;

/**
 *  Weapon class, manages how every weapon works
 *
 *  @author  Nathan Man-ho Lui
 *  @version Nov 1, 2015
 *  @author  Assignment: OpenEnded
 *
 *  @author  Sources: none
 */
public class Weapon extends Sprite
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private int direction;
    private Sprite mySprite;

    public Weapon( BufferedImage img, String skillClass )
    {
        super( img );
        this.setSpeed( 6 );
        this.setSkillClass( skillClass );
    }
    
    @Override
    public Weapon clone()
    {
        return (Weapon)super.clone();
    }
    
    /** 
     * Subclasses should override this method. 
     * Returns a copy of this sprite with direction set to the new sprite's 
     * dirFacing
     * @param s the Sprite that spawns this new weapon
     */
    public Weapon clone( Sprite s )
    {
        return this.clone( s, s.getSpriteData().getDirFacing() );
    }
    
    protected Weapon clone( Sprite s, int dir )
    {
        Weapon w = this.clone();
        w.mySprite = s;
        w.direction = dir;
        w.setPosition( s.getX(), s.getY() );
        return w;
    }

    @Override
    public boolean additionalCollisions( Sprite sprite )
    {
        Sprite s = sprite;
        if ( sprite instanceof Weapon )
            s = ((Weapon)sprite).mySprite;
        return mySprite.friendlyFire() ? s != mySprite : !((FightingSprite)mySprite).isSameTeam( s );
    }

    @Override
    public double getDirection( long gameTime )
    {
        return direction;
    }

    @Override
    public void hitSprite( Sprite sprite )
    {
        if ( !isDead() )
            sprite.takeDamage( this.getSpriteData().getAtk() );
        dying();
    }

    @Override
    public void hitWall( int dir )
    {
        dying();
    }
    
    @Override
    public void takeDamage( int damage )
    {
        dying();
    }

    @Override
    public boolean friendlyFire()
    {
        return mySprite.friendlyFire();
    }
    
    public Sprite getSprite()
    {
        return mySprite;
    }
}
