package sprites;

import game.InputManager;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import world.Map;

public class Player extends Sprite
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private int[] controls = new int[]{KeyEvent.VK_W, KeyEvent.VK_D, KeyEvent.VK_S, KeyEvent.VK_A };
    
    public Player( String img )
    {
        super( img );
    }
    public Player( BufferedImage img )
    {
        super( img );
    }
    
    @Override
    public void move( long gameTime, Map map )
    {
        Point p = getDirections( gameTime );
        translate( p.x, 0 );
        if ( map.isColliding( this ) )
        {
            translate( -p.x, 0 );
        }
        translate( 0, p.y );
        if ( map.isColliding( this ) )
        {
            translate( 0, -p.y );
        }
    }
    
    private Point getDirections( long gameTime )
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
        dx *= getSpeed();
        dy *= getSpeed();
//        dx *= gameTime / GameScreen.secInNanosec;
//        dy *= gameTime / GameScreen.secInNanosec;
        return new Point( dx, dy );
    }
    
    public int getSpeed()
    {
        return 5;
    }
    
}
