package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import gui.Window.Panel;

public class GameScreen extends ScreenPanel implements ActionListener
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public GameScreen( Window frame, Panel panel )
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
            window.switchTo( screen, Panel.LISTGAME );
        }
    }
    
    

}
