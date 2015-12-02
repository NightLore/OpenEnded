package sprites;

import game.InputManager;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import sprites.weapons.Weapon;

public class Player extends FightingSprite
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private int[] controls = new int[]{KeyEvent.VK_W, KeyEvent.VK_D, KeyEvent.VK_S, KeyEvent.VK_A };
    
    public Player( BufferedImage img )
    {
        super( img );
    }
    
//    @Override
//    public void move( long gameTime, Map map )
//    {
//        super.move( gameTime, map );
//    }
    
    @Override
    public double getDirection( long gameTime )
    {
        int dx = 0;
        int dy = 0;
        if ( InputManager.keyboardKeyState( controls[0] ) )
        {
            dy--;
        }
        if ( InputManager.keyboardKeyState( controls[1] ) )
        {
            dx++;
        }

        if ( InputManager.keyboardKeyState( controls[2] ) )
        {
            dy++;
        }
        if ( InputManager.keyboardKeyState( controls[3] ) )
        {
            dx--;
        }
        return ( dx == 0 && dy == 0 ) ? -1.0 : Math.toDegrees( Math.atan2( dy, dx ) );
    }

    @Override
    public void hitSprite( Sprite sprite ) {}

    @Override
    public void seeSprite( Sprite sprite ) {}

    @Override
    public Weapon attack( int attack )
    {
        return null;
    }
    
    public int getSpeed()
    {
        return 5;
    }
    
}
