package sprites;

import game.InputManager;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class Player extends FightingSprite
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private int[] controls = new int[]{KeyEvent.VK_W, KeyEvent.VK_D, 
        KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_V, KeyEvent.VK_B };
    private Weapon[] weapons;
    
    public Player( BufferedImage img, Weapon[] weapons )
    {
        super( img );
        this.weapons = weapons;
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
    public Weapon attack( int attack )
    {
        Weapon weapon = null;
        switch ( attack )
        {
            case 0:
                weapon = new Weapon( this, weapons[attack] );
                break;
            case 1:
                weapon = new Weapon( this, weapons[attack], getSpriteData().getDirFacing() );
                break;
        }
        if ( weapon != null )
            weapon.setPosition( getX(), getY() );
        
        return weapon;
    }

    @Override
    public boolean additionalCollisions( Sprite s )
    {
        return s instanceof Weapon ? ((Weapon)s).getSprite() != this : true;
    }
    
    public void setControls( int... newCtrls )
    {
        if ( newCtrls.length > controls.length )
            controls = new int[newCtrls.length];
        System.arraycopy( newCtrls, 0, controls, 0, newCtrls.length );
    }
    
    public int getSpeed()
    {
        return 5;
    }
    
}
