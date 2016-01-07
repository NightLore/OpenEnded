package sprites;

import java.awt.image.BufferedImage;
import java.util.Random;

public class Enemy extends FightingSprite
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private static final Random RANDOM = new Random();
    private static final int AGGRODISTANCE = 500;
    private static final int DEAGGRODISTANCE = 1000;
    
    private long wait;
    private long prevTime;
    private double dir;
    private int chanceMove;
    private Sprite target;

    public Enemy( BufferedImage img, Weapon[] weapons )
    {
        super( img, weapons );
        chanceMove = 75;
        this.weapons = weapons;
//        prevTime = 0; // note: may need to be initialized
//        wait = 0;
    }


    @Override
    public double getDirection( long gameTime )
    {
        if ( gameTime > prevTime + wait )
        {
            boolean shouldMove = RANDOM.nextInt( 100 ) < chanceMove; // 75% chance to move
            dir = shouldMove ? RANDOM.nextInt( 360 ) : -1; // 360 degrees
            wait = RANDOM.nextInt();
            prevTime = gameTime;
        }
        if ( target != null )
        {
            if ( Math.abs( this.distance( target ) ) > DEAGGRODISTANCE )
            {
                target = null;
            }
            setAttack( 1 );
        }
        return dir;
    }
    
    @Override
    public boolean additionalCollisions( Sprite sprite ) // temporary for seeSprite until works out code
    {
        seeSprite( sprite );
        return super.additionalCollisions( sprite );
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
//        System.out.println( this + "__" + distance + "_" + sprite + AGGRODISTANCE + "_" + Math.abs( distance ) );
        if ( sprite instanceof Player && Math.abs( distance ) < AGGRODISTANCE )
        {
//            System.out.println( "ATTACK!!!!!!!!" );
            target = sprite;
        }
    }


    @Override
    public void hitWall( int dir )
    {
        
    }
    
    @Override
    public Weapon attack1()
    {
        return new Weapon( this, weapons[0] );
    }
    
    @Override
    public Weapon attack2()
    {
        return new Weapon( this, weapons[1], this.direction( target ) );
    }
    
    
    @Override
    public int getSpeed()
    {
        return 3;
    }

}
