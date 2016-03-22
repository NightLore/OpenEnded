package game.world;

import game.sprites.Sprite;

import java.awt.image.BufferedImage;

/**
 *  Sprite used by Tile to manage the Image and minor collisions
 *
 *  @author  Nathan Man-ho Lui
 *  @version Jan 6, 2016
 *  @author  Assignment: OpenEnded
 *
 *  @author  Sources: none
 */
public class TileSprite extends Sprite
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;


    public TileSprite( BufferedImage img )
    {
        super( img );
    }


    @Override
    public boolean additionalCollisions( Sprite s )
    {
        return true;
    }


    @Override
    public double getDirection( long gameTime )
    {
        return 0;
    }


    @Override
    public void hitWall( int dir ) {}


    @Override
    public void hitSprite( Sprite sprite ) {}


    @Override
    public void takeDamage( int damage ) {}


    @Override
    public int getSpeed()
    {
        return 0;
    }


    @Override
    public boolean friendlyFire()
    {
        return false;
    }

}
