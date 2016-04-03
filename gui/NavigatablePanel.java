package gui;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.AbstractAction;
import javax.swing.Action;

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
        
        upAction = new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed( ActionEvent e )
            {
                up();
            }
        };
        leftAction = new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed( ActionEvent e )
            {
                left();
            }
        };
        downAction = new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed( ActionEvent e )
            {
                down();
            }
        };
        rightAction = new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed( ActionEvent e )
            {
                right();
            }
        };
        confirmAction = new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed( ActionEvent e )
            {
                confirm();
            }
        };
        cancelAction = new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed( ActionEvent e )
            {
                cancel();
            }
        };
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
