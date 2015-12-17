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
    
    private long wait;
    private long prevTime;
    private double dir;
    private int chanceMove;

    public Enemy( BufferedImage img )
    {
        super( img );
        chanceMove = 75;
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
        return dir;
    }


    @Override
    public void hitSprite( Sprite sprite )
    {

    }

    @Override
    public void seeSprite( Sprite sprite ) 
    {
        
    }


    @Override
    public void hitWall( int dir )
    {
        
    }


    @Override
    public Weapon attack( int attack )
    {
        // TODO Auto-generated method stub
        return null;
    }
    
    
    @Override
    public int getSpeed()
    {
        return 3;
    }

}
