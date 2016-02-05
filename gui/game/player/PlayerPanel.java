package gui.game.player;

import java.awt.Color;

import gui.Carder;
import gui.MenuNavigator;
import gui.ScreenPanel;

/**
 *  Abstract class that serves as the base for all Player menu Panels
 *
 *  @author  Nathan Man-ho Lui
 *  @version Feb 4, 2016
 */
public abstract class PlayerPanel extends ScreenPanel implements ControlListener
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String back;
    protected MenuNavigator navigator;
    
    public PlayerPanel( Carder frame )
    {
        this( frame, STATS_PANEL );
    }
    
    public PlayerPanel( Carder frame, String to )
    {
        this( frame, to, Color.BLACK );
        this.setOpaque( false );
    }
    
    public PlayerPanel( Carder frame, String to, Color background )
    {
        super( frame, background );
        this.back = to;
    }
    
    public void back()
    {
        carder.switchTo( back );
    }

    @Override
    public void up()
    {
        if ( navigator != null )
            navigator.up();
    }

    @Override
    public void left()
    {
        if ( navigator != null )
            navigator.left();
    }

    @Override
    public void down()
    {
        if ( navigator != null )
            navigator.down();
    }

    @Override
    public void right()
    {
        if ( navigator != null )
            navigator.right();
    }
}
