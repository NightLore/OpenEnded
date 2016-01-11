package gui;

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

    public LoadGameScreen( Cards frame, String panel )
    {
        super( frame, panel );
        JButton backButton = new JButton( "Back" );
        backButton.addActionListener( this );
        this.add( backButton );
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        if ( e.getActionCommand().equals( "Back" ) )
        {
            carder.switchTo( screen, "LISTGAME" );
        }
    }
}
