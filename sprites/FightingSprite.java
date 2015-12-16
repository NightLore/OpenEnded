package sprites;

import java.awt.image.BufferedImage;

import world.Map;

public abstract class FightingSprite extends Sprite
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private int attack = -1;
    private long attackTime;
    
    private int delay;

    public FightingSprite( BufferedImage img )
    {
        super( img );
        delay = 20;
    }

    /**
     * @see sprites.Sprite#move(long, world.Map, sprites.SpriteGroup)
     */
    @Override
    public void move( long gameTime, Map map, SpriteGroup<? extends Sprite> sprites )
    {
        super.move( gameTime, map, sprites );
        System.out.println( "dt: " + (gameTime - attackTime) );
        if ( attack >= 0 && gameTime - attackTime > delay * 1000000000L ) // TODO note: comparing long with int
        {
            sprites.add( attack( attack ) );
            attack = -1;
            attackTime = gameTime;
        }
    }

    @Override
    public boolean additionalCollisions( Sprite s )
    {
        return true;
    }

    // public abstract void hitWeapon( Sprite weapon );
    public abstract void seeSprite( Sprite sprite );

    /**
     * Returns a weapon based on the attack type
     * @param attack int representing type of attack (must be >= 0)
     * @return if attack type is >= 0 then return corresponding weapon else return null
     */
    public abstract Weapon attack( int attack );

    @Override
    public void takeDamage( int damage )
    {
        
    }
    
    public void setAttack( int attack )
    {
        this.attack = attack;
    }
}
