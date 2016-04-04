package gui;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.Action;
import javax.swing.KeyStroke;

/**
 *  Base Panel for Navigatable Panels
 *  
 *  <br> Extends ClearPanel; Implements Carder, ControlListener, ActionListener
 *
 *  @author  Nathan M. Lui
 *  @version Jan 10, 2016
 *  @author  Assignment: OpenEnded
 */
public abstract class NavigatablePanel extends ClearPanel implements Carder, ControlListener, ActionListener
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    protected HashMap<String,ScreenPanel> navigatables;


    protected Action upAction, leftAction, downAction, rightAction, confirmAction, cancelAction;
    protected String currentPanel;
    protected CardLayout cardLayout;

    public NavigatablePanel()
    {
        this.navigatables = new HashMap<String,ScreenPanel>();
        this.cardLayout = new CardLayout();
        this.setLayout( cardLayout );
        this.setFocusable( true );
        
        upAction = new ControlAction( this, UP );
        leftAction = new ControlAction( this, LEFT );
        downAction = new ControlAction( this, DOWN );
        rightAction = new ControlAction( this, RIGHT );
        confirmAction = new ControlAction( this, CONFIRM );
        cancelAction = new ControlAction( this, CANCEL );
    }
    
    public void putKeyAction( int key, String identifier, Action action )
    {
        putKeyAction( key, 0, identifier, action );
    }
    
    public void putKeyAction( int key, int modifier, String identifier, Action action )
    {
        this.getInputMap( WHEN_IN_FOCUSED_WINDOW ).put( KeyStroke.getKeyStroke( key, modifier ), identifier );
        this.getActionMap().put( identifier, action );
    }
    
    @Override
    public void switchTo( String to )
    {
        navigatables.get( currentPanel ).cover();
        String prev = currentPanel;
        currentPanel = to; // note: code specifically in this order so that currentPanel may be adjusted before method return
        switchTo( prev, to );
        navigatables.get( currentPanel ).shown();
    }

    @Override
    public void act( String action )
    {
        ControlListener navigator = navigatables.get( currentPanel );
        if ( navigator == null ) return;
        if ( action.equals( UP ) ) // note: enums?
        {
            navigator.up();
        }
        else if ( action.equals( LEFT ) )
        {
            navigator.left();
        }
        else if ( action.equals( DOWN ) )
        {
            navigator.down();
        }
        else if ( action.equals( RIGHT ) )
        {
            navigator.right();
        }
        else if ( action.equals( CONFIRM ) )
        {
            navigator.confirm();
        }
        else if ( action.equals( CANCEL ) )
        {
            navigator.cancel();
        }
        else
        {
            navigator.act( action );
        }
    }

    @Override
    public void up()
    {
        act( UP );
    }

    @Override
    public void left()
    {
        act( LEFT );
    }

    @Override
    public void down()
    {
        act( DOWN );
    }

    @Override
    public void right()
    {
        act( RIGHT );
    }

    @Override
    public void confirm()
    {
        act( CONFIRM );
    }

    @Override
    public void cancel()
    {
        act( CANCEL );
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        this.switchTo( e.getActionCommand().toUpperCase() );
    }
}
