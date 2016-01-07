package sprites;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import world.Map;

public abstract class FightingSprite extends Sprite
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private int attack = -1;
    protected Weapon[] weapons;
    private int[] delay = new int[]{ 500, 500 }; // in milliseconds
    private long[] attackTime = new long[delay.length];
    
    public FightingSprite( BufferedImage img, Weapon[] weapons )
    {
        super( img );
        this.weapons = weapons;
    }
    
    @Override
    public void paint( Graphics g )
    {
        super.paint( g );
        this.getSpriteData().drawHp( g, this, false );
    }

    /**
     * @see sprites.Sprite#move(long, world.Map, sprites.SpriteGroup)
     */
    @Override
    public void move( long gameTime, Map map, SpriteGroup<? extends Sprite> sprites )
    {
        super.move( gameTime, map, sprites );
        for ( int i = 0; i < attackTime.length; i++ )
        {
            if ( attackTime[i] == 0 )
                attackTime[i] = gameTime;
        }
        if ( attack >= 0 ) // TODO note: comparing long with int
        {
            Weapon w = attack( gameTime, attack );
            if ( w != null )
                sprites.add( w );
            attack = -1;
        }
    }

    @Override
    public boolean additionalCollisions( Sprite s )
    {
        return s instanceof Weapon ? ((Weapon)s).getSprite() != this : true;
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
    public Weapon attack( long gameTime, int attack )
    {
        Weapon weapon = null;
        switch ( attack )
        {
            case 0:
                if ( gameTime > attackTime[attack] + delay[attack] * 1000000L )
                {
                    weapon = attack1();
                    attackTime[attack] = gameTime;
                }
                break;
            case 1:
                if ( gameTime > attackTime[attack] + delay[attack] * 1000000L )
                {
                    weapon = attack2();
                    attackTime[attack] = gameTime;
                }
                break;
        }
        if ( weapon != null )
            weapon.setPosition( getX(), getY() );
        
        return weapon;
    }
    
    public abstract Weapon attack1();
    
    public abstract Weapon attack2();

    @Override
    public void takeDamage( int damage )
    {
        this.getSpriteData().decreaseHp( damage );
    }
    
    public void setAttack( int attack )
    {
        this.attack = attack;
    }
}
