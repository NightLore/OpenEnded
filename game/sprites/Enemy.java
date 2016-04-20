package game.sprites;

import java.awt.image.BufferedImage;
import java.util.Random;

/**
 *  Enemy class for the game, manages AI of the enemy and timers
 *
 *  @author  Nathan Man-ho Lui
 *  @version Nov 1, 2015
 *  @author  Assignment: OpenEnded
 *
 *  @author  Sources: none
 */
public class Enemy extends FightingSprite
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private static final Random RANDOM = new Random();
    public static boolean FRIENDLY_FIRE = false;
    private static final int AGGRODISTANCE = 250;
    private static final int DEAGGRODISTANCE = 500;
    
    private long wait;
    private long prevTime;
    private double dir;
    private int chanceMove;
    private Sprite target;

    public Enemy( BufferedImage img )
    {
        super( img, "ENEMY" );
        chanceMove = 75;
//        prevTime = 0; // note: may need to be initialized
//        wait = 0;
        delay[1] = 1000;
        this.setSpeed( 3 );
    }
    
    @Override
    public Enemy clone()
    {
        return (Enemy)super.clone();
    }

    @Override
    protected int drawDir()
    {
        return (int)dir;
    }

    @Override
    public double getDirection( long gameTime )
    {
        if ( gameTime > prevTime + wait )
        {
            if ( target == null )
            {
                boolean shouldMove = RANDOM.nextInt( 100 ) < chanceMove; // 75% chance to move
                dir = shouldMove ? RANDOM.nextInt( 360 ) : -1; // 360 degrees
                wait = RANDOM.nextInt();
            }
            else // if ( target != null )
            {
                dir = this.direction( target ) + RANDOM.nextInt( 90 ) - 45;
                dir = ( dir + 360 ) % 360;
                wait = RANDOM.nextInt() / 2;
            }
            prevTime = gameTime;
        }
        if ( target != null )
        {
            if ( target.isDead() || Math.abs( this.distance( target ) ) > DEAGGRODISTANCE )
            {
                target = null;
            }
            else setAttack( 1 );
        }
        return dir;
    }


    @Override
    public void hitSprite( Sprite sprite )
    {
        if ( sprite instanceof Player )
        {
            target = sprite;
        }
    }

    @Override
    public void seeSprite( Sprite sprite ) 
    {
        double distance = this.distance( sprite );
        if ( !this.isSameTeam( sprite ) && Math.abs( distance ) < AGGRODISTANCE )
        {
            target = sprite;
        }
    }


    @Override
    public void hitWall( int dir )
    {
        if ( target == null )
            wait = 0;
    }
    
    @Override
    public int getWeaponDirection()
    {
        return this.direction( target );
    }

    @Override
    public boolean friendlyFire()
    {
        return Enemy.FRIENDLY_FIRE;
    }
}
