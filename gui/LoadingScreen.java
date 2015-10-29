package gui;

import gui.Window.Panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class LoadingScreen extends ScreenPanel implements ActionListener
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public LoadingScreen( Window frame )
    {
        super( frame );
        JButton backButton = new JButton( "Back" );
        backButton.addActionListener( this );
        this.add( backButton );
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        if ( e.getActionCommand().equals( "Back" ) )
        {
            window.switchTo( this, Panel.MENU );
        }
    }
}
