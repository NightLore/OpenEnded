package sprites;

import game.InputManager;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

/**
 *  Player class takes input based on an int array in order to determine how to 
 *  move
 *
 *  @author  Nathan Man-ho Lui
 *  @version Nov 1, 2015
 *
 *  @author  Sources: none
 */
public class Player extends FightingSprite
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    public static boolean FRIENDLY_FIRE = false;
    
    public static final int WASD = 0;
    public static final int ARROWKEYS = 1;
    public static final int IJKL = 2;
    public static final int NUMPADKEYS = 3;
    public static final int NUMDEFAULT = 4;
    
    public static final int UP = 0;
    public static final int RIGHT = 1;
    public static final int DOWN = 2;
    public static final int LEFT = 3;
    public static final int PRIMARY = 4;
    public static final int SECONDARY = 5;
    
    public static final int[] WASDVB = new int[]{KeyEvent.VK_W, KeyEvent.VK_D, 
        KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_V, KeyEvent.VK_B };
    
    public static final int[] ARROWS = new int[]{KeyEvent.VK_UP, KeyEvent.VK_RIGHT, 
        KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_COMMA, KeyEvent.VK_PERIOD };
    
    public static final int[] IJKLTY = new int[]{KeyEvent.VK_I, KeyEvent.VK_L, 
        KeyEvent.VK_K, KeyEvent.VK_J, KeyEvent.VK_T, KeyEvent.VK_Y };
    
    public static final int[] NUMPAD = new int[]{KeyEvent.VK_NUMPAD8, KeyEvent.VK_NUMPAD6, 
        KeyEvent.VK_NUMPAD5, KeyEvent.VK_NUMPAD4, KeyEvent.VK_MULTIPLY, KeyEvent.VK_SUBTRACT };

    private static final int[][] defaultCtrls = new int[][]{ WASDVB, ARROWS, IJKLTY, NUMPAD };
    
    private int[] controls;
    
    public Player( BufferedImage img, Weapon[] weapons )
    {
        super( img, weapons, "PLAYER" );
        this.setDefaultControls( 0 );
        this.setSpeed( 5 );
    }
    
    @Override
    protected int drawDir()
    {
        return this.getSpriteData().getDirFacing();
    }
    
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
        setAttack( -1 );
        if ( InputManager.keyboardKeyState( controls[4] ) )
        {
            setAttack( 0 );
        }
        if ( InputManager.keyboardKeyState( controls[5] ) )
        {
            setAttack( 1 ); // TODO note: if both attack buttons are pressed
        }
        return ( dx == 0 && dy == 0 ) ? -1.0 : (Math.toDegrees( Math.atan2( dy, dx ))+360)%360;
    }

    @Override
    public Weapon attack1()
    {
        return new Weapon( this, weapons[0] );
    }


    @Override
    public Weapon attack2()
    {
        return new Weapon( this, weapons[1], getSpriteData().getDirFacing() );
    }
    
    public int[] getControls()
    {
        return controls.clone();
    }
    
    public void setControls( int... newCtrls )
    {
        if ( controls == null || newCtrls.length > controls.length )
            controls = new int[newCtrls.length];
        System.arraycopy( newCtrls, 0, controls, 0, newCtrls.length );
    }
    
    public void setDefaultControls( int control )
    {
        this.setControls( defaultCtrls[control] );
    }

    @Override
    public boolean friendlyFire()
    {
        return Player.FRIENDLY_FIRE;
    }
}
