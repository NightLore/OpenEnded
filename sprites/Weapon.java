package sprites;

import java.awt.image.BufferedImage;

public class Weapon extends Sprite
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private int direction;
    private Sprite mySprite;
    private boolean friendlyFire;

    public Weapon( BufferedImage img, boolean friendlyFire )
    {
        super( img );
        this.friendlyFire = friendlyFire;
        this.setSpeed( 6 );
    }
    
    public Weapon( Sprite s, Weapon w, boolean friendlyFire )
    {
        this( s, w, friendlyFire, -1 );
    }
    
    public Weapon( Sprite s, Weapon w, boolean friendlyFire, int dir ) // TODO copy SpriteData
    {
        this( w.getImage(), friendlyFire );
        setCollidable( w.canCollide() );
        setRefPixel( w.getRefX(), w.getRefY() );
        setPosition( w.getX(), w.getY() );
        splitSprite( w.getCols(), w.getRows() );
        direction = dir;
        mySprite = s;
    }

    @Override
    public boolean additionalCollisions( Sprite sprite )
    {
        Sprite s = sprite;
        if ( sprite instanceof Weapon )
            s = ((Weapon)sprite).mySprite;
        return friendlyFire ? s != mySprite : !((FightingSprite)mySprite).isSameTeam( s );
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
    
    public Sprite getSprite()
    {
        return mySprite;
    }
}
