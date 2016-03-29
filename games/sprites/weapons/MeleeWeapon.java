package games.sprites.weapons;

import game.sprites.Sprite;
import game.sprites.groups.SpriteGroup;
import game.world.Map;

import java.awt.image.BufferedImage;

/**
 *  Represents a Melee Weapon
 *
 *  @author  Nathan M. Lui
 *  @version Mar 28, 2016
 *  @author  Assignment: OpenEnded
 */
public class MeleeWeapon extends Mine
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private long attackTime;
    private int delay;

    public MeleeWeapon( BufferedImage img, String skillClass, int delay )
    {
        super( img, skillClass );
        this.delay = delay;
    }
    
    @Override
    public MeleeWeapon clone( Sprite s )
    {
        MeleeWeapon w = (MeleeWeapon)super.clone( s ); // relies on Mine
        return w;
    }
    
    @Override
    public void move( long gameTime, Map map, SpriteGroup sprites )
    {
        if ( attackTime == 0 )
            attackTime = gameTime;
        if ( gameTime > attackTime + delay * 1000000L )
            this.dying();
        this.adjustPosition();
        super.move( gameTime, map, sprites );
    }
    
    private void adjustPosition()
    {
        Sprite s = this.getSprite();
        int offset = s.getWidth() / 2;
        int dir = s.getSpriteData().getDirFacing();
        
        this.setPosition(s.getX()+(int)(offset*Math.cos(Math.toRadians(dir))),
                         s.getY()+(int)(offset*Math.sin(Math.toRadians(dir))));
    }

}
