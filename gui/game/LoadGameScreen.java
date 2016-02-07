package gui.game;

import gui.Carder;
import gui.ScreenPanel;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

/**
 *  Screen for loading a presaved game
 *
 *  @author  Nathan Man-ho Lui
 *  @version Oct 28, 2015
 *  @author  Assignment: OpenEnded
 *
 *  @author  Sources: none
 */
public class LoadGameScreen extends ScreenPanel implements ActionListener
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    public LoadGameScreen( Carder frame )
    {
        this( frame, LISTGAME );
    }

    public LoadGameScreen( Carder frame, String back )
    {
        super( frame, Color.BLACK, back );
        JButton backButton = new JButton( "BACK" );
        backButton.addActionListener( this );
        this.add( backButton );
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        act( e.getActionCommand() );
    }
    
    @Override
    public void act( String selected )
    {
        if ( selected.equalsIgnoreCase( "BACK" ) )
        {
            back();
        }
    }
}
