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

    private int hp = 10;
    public FightingSprite( BufferedImage img )
    {
        super( img );
        delay = 500;// in milliseconds
    }

    /**
     * @see sprites.Sprite#move(long, world.Map, sprites.SpriteGroup)
     */
    @Override
    public void move( long gameTime, Map map, SpriteGroup<? extends Sprite> sprites )
    {
        super.move( gameTime, map, sprites );
        if ( attackTime == 0 )
            attackTime = gameTime;
//        System.out.println( "Time: " + gameTime + "; dt: " + (gameTime - attackTime) );
        if ( attack >= 0 && gameTime  > attackTime + delay * 1000000L ) // TODO note: comparing long with int
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

    @Override
    public void hitSprite( Sprite sprite ) {}

    public void seeSprite( Sprite sprite ) {}

    @Override
    public void hitWall( int dir ) {}

    /**
     * Returns a weapon based on the attack type
     * @param attack int representing type of attack (must be >= 0)
     * @return if attack type is >= 0 then return corresponding weapon else return null
     */
    public abstract Weapon attack( int attack );

    @Override
    public void takeDamage( int damage )
    {
        hp -= damage;
        if ( hp <= 0 )
            dying();
    }
    
    public void setAttack( int attack )
    {
        this.attack = attack;
    }
}
