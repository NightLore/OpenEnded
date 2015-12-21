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

    public Weapon( BufferedImage img )
    {
        super( img );
    }
    
    public Weapon( Sprite s, Weapon w )
    {
        this( s, w, -1 );
    }
    
    public Weapon( Sprite s, Weapon w, int dir ) // TODO copy SpriteData
    {
        this( w.getImage() );
        setCollidable( w.canCollide() );
        setRefPixel( w.getRefX(), w.getRefY() );
        setPosition( w.getX(), w.getY() );
        if ( w.getCols() != 0 && w.getRows() != 0 )
            splitSprite( w.getCols(), w.getRows() );
        direction = dir;
        mySprite = s;
    }

    @Override
    public boolean additionalCollisions( Sprite sprite )
    {
//        System.out.println( s + "; " + mySprite );
        Sprite s = sprite;
        if ( sprite instanceof Weapon )
            s = ((Weapon)sprite).mySprite;
        return s != mySprite;
    }

    @Override
    public double getDirection( long gameTime )
    {
        return direction;
    }

    @Override
    public void hitSprite( Sprite sprite )
    {
//        System.out.println( this + "hit Sprite: " + sprite );
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

    @Override
    public int getSpeed()
    {
        return 6;
    }

}
