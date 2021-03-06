package game.sprites;

import game.sprites.groups.SpriteGroup;
import game.sprites.weapons.Weapon;
import game.world.Map;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *  Abstract class for all sprites that can fight
 *
 *  @author  Nathan Man-ho Lui
 *  @version Nov 1, 2015
 *  @author  Assignment: OpenEnded
 *
 *  @author  Sources: none
 */
public abstract class FightingSprite extends Sprite
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private int attack = -1;
    protected Weapon[] weapons;
    protected int[] delay = new int[]{ 500, 500 }; // in milliseconds
    private long[] attackTime = new long[delay.length];
    private String team;
    
    public FightingSprite( BufferedImage img, String team )
    {
        super( img );
        this.weapons = new Weapon[2];
        this.setTeam( team );
        this.setSkillClass( "INEPT" ); // TODO replace with skill chooser
    }
    
    @Override
    public FightingSprite clone()
    {
        return (FightingSprite)super.clone();
    }
    
    @Deprecated
    @Override
    public void paint( Graphics g )
    {
        paint( g, false );
    }
    
    @Override
    public void paint( Graphics g, boolean debug )
    {
        super.paint( g, debug );
        this.getSpriteData().drawHp( g, this, false );
        int r = this.getWidth() / 2;
        int d = this.drawDir();
        if ( d >= 0 )
        {
            int x1 = this.getX();
            int y1 = this.getY();
            int x2 = x1 + (int)Math.round( r*Math.cos( Math.toRadians( d ) ) );
            int y2 = y1 + (int)Math.round( r*Math.sin( Math.toRadians( d ) ) );
            g.setColor( Color.MAGENTA );
            g.drawLine( x1, y1, x2, y2 );
        }
    }
    
    protected abstract int drawDir();

    /**
     * @see game.sprites.Sprite#move(long, game.world.Map, game.sprites.groups.SpriteGroup)
     */
    @Override
    public void move( long gameTime, Map map, SpriteGroup sprites )
    {
        super.move( gameTime, map, sprites );
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
        // if hit a Weapon, return the Weapon's conditions, else this Sprite collided
        return s instanceof Weapon ? ((Weapon)s).additionalCollisions( this ) : true;
    }

    @Override
    public void hitSprite( Sprite sprite ) {} // by default, does not do anything

    @Override
    public void hitWall( int dir ) {} // by default, does not do anything

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
            case 1:
                if ( gameTime > attackTime[attack] + delay[attack] * 1000000L )
                {
                    weapon = attack( attack );
                    attackTime[attack] = gameTime;
                }
        }
        return weapon;
    }
    
    /**
     * Returns a clone of the weapon that represents the indicated attack
     * @param atk
     * @return weapons[atk].clone(this);
     */
    public Weapon attack( int atk )
    {
        return weapons[atk].clone( this );
    }

    @Override
    public void takeDamage( int damage )
    {
        this.getSpriteData().decreaseHp( damage );
    }
    
    public void setPrimaryWeapon( Weapon weapon )
    {
        setWeapon( weapon, 0 );
    }
    
    public void setSecondaryWeapon( Weapon weapon )
    {
        setWeapon( weapon, 1 );
    }
    
    public void setWeapons( Weapon... weapons )
    {
        setPrimaryWeapon( weapons[0] );
        setSecondaryWeapon( weapons[1] );
    }
    
    public void setWeapon( Weapon weapon, int index )
    {
        weapons[index] = weapon;
    }
    
    public Weapon[] getWeapons()
    {
        return weapons.clone();
    }
    
    public abstract int getWeaponDirection();
    
    public void setAttack( int attack )
    {
        this.attack = attack;
    }
    
    public void setTeam( String team )
    {
        this.team = team;
    }
    
    public String getTeam()
    {
        return this.team;
    }
    
    public boolean isSameTeam( Sprite sprite )
    {
        String team = "";
        if ( sprite instanceof FightingSprite )
        {
            team = ((FightingSprite)sprite).team;
        }
        else if ( sprite instanceof Weapon )
        {
            return isSameTeam( ((Weapon)sprite).getSprite() );
        }
        return this.team.equals( team );
    }
}
