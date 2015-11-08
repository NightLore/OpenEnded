package sprites;

import game.InputManager;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

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
    public void move( long gameTime )
    {
        Point p = getDirections( gameTime );
        translate( p.x * getSpeed(), p.y * getSpeed() );
    }
    
    private Point getDirections( long gameTime )
    {
        int dx = 0;
        int dy = 0;
//        System.out.println( GameScreen.keyboardKeyState( controls[0] ) );
        if ( InputManager.keyboardKeyState( controls[0] ) )
        {
            dy--;
        }
//        System.out.println( GameScreen.keyboardKeyState( controls[1] ) );
        if ( InputManager.keyboardKeyState( controls[1] ) )
        {
            dx++;
        }

//        System.out.println( GameScreen.keyboardKeyState( controls[2] ) );
        if ( InputManager.keyboardKeyState( controls[2] ) )
        {
            dy++;
        }
//        System.out.println( GameScreen.keyboardKeyState( controls[3] ) );
        if ( InputManager.keyboardKeyState( controls[3] ) )
        {
            dx--;
        }
//        dx *= gameTime / GameScreen.secInNanosec;
//        dy *= gameTime / GameScreen.secInNanosec;
        return new Point( dx, dy );
    }
    
    public int getSpeed()
    {
        return 5;
    }
    
}
