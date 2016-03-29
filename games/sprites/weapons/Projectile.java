package games.sprites.weapons;

import game.sprites.FightingSprite;
import game.sprites.Sprite;

import java.awt.image.BufferedImage;

/**
 *  A Moving Weapon that moves according to its Sprite's direction
 *
 *  @author  Nathan
 *  @version Mar 28, 2016
 *  @author  Period: TODO
 *  @author  Assignment: OpenEnded
 *
 *  @author  Sources: TODO
 */
public class Projectile extends Weapon
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public Projectile( BufferedImage img, String skillClass )
    {
        super( img, skillClass );
    }
    
    @Override
    public Projectile clone()
    {
        return (Projectile)super.clone();
    }
    
    @Override
    public Projectile clone( Sprite s )
    {
        return (Projectile)super.clone( s, ( (FightingSprite)s ).getWeaponDirection() );
    }

}
