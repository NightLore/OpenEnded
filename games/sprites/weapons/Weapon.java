package games.sprites.weapons;

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
    
    public Weapon( Sprite s, Weapon w )
    {
        this( s, w, -1 );
    }
    
    private Weapon( Sprite s, Weapon w, int dir ) // TODO copy SpriteData
    {
        this( w.getImage(), w.getSkillClass() );
        setCollidable( w.canCollide() );
        setRefPixel( w.getRefX(), w.getRefY() );
        setPosition( w.getX(), w.getY() );
        splitSprite( w.getCols(), w.getRows() );
        direction = dir;
        mySprite = s;
    }
    
    @Override
    public Weapon clone()
    {
        return (Weapon)super.clone();
    }
    
    /** Must be overriden to function */
    public Weapon clone( Sprite s )
    {
        Weapon w = this.clone();
        w.mySprite = s;
        w.setSpriteData( w.getSpriteData() ); // copies spriteData
        return w;
    }
    
    protected Weapon clone( Sprite s, int dir )
    {
        Weapon w = this.clone();
        w.mySprite = s;
        w.direction = dir;
        w.setSpriteData( w.getSpriteData() ); // copies spriteData
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
