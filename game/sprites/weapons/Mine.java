package game.sprites.weapons;

import game.sprites.Sprite;

import java.awt.image.BufferedImage;

/**
 *  A Stationary Weapon that lasts throughout the game
 *
 *  @author  Nathan M. Lui
 *  @version Mar 28, 2016
 *  @author  Assignment: OpenEnded
 */
public class Mine extends Weapon
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public Mine( BufferedImage img, String skillClass )
    {
        super( img, skillClass );
    }
    
    @Override
    public Mine clone()
    {
        return (Mine)super.clone();
    }
    
    @Override
    public Mine clone( Sprite s )
    {
        return (Mine)super.clone( s, -1 );
    }

}
