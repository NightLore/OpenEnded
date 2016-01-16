package gui.game;

import gui.ScreenPanel;
import gui.Window;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;

/**
 *  Screen Lists all game modes for user to play
 *
 *  @author  Nathan Man-ho Lui
 *  @version Oct 28, 2015
 *  @author  Assignment: OpenEnded
 *
 *  @author  Sources: none
 */
public class ListGameScreen extends ScreenPanel implements ActionListener
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public ListGameScreen( Window frame )
    {
        super( frame, "GrassyBackground.png" );
        this.setLayout( new BoxLayout( this, BoxLayout.Y_AXIS ) );
        
        Dimension buttonSize = frame.buttonSize;
        JButton stryButton = new JButton( "Story Mode" );
        JButton freeButton = new JButton( "Endless Mode" );
        JButton loadButton = new JButton( "Load Game" );
        JButton backButton = new JButton( "Back" );

        stryButton.addActionListener( this );
        freeButton.addActionListener( this );
        loadButton.addActionListener( this );
        backButton.addActionListener( this );
        
        stryButton.setPreferredSize( buttonSize );
        freeButton.setPreferredSize( buttonSize );
        loadButton.setPreferredSize( buttonSize );
        backButton.setPreferredSize( buttonSize );
        
        stryButton.setAlignmentX( CENTER_ALIGNMENT );
        freeButton.setAlignmentX( CENTER_ALIGNMENT );
        loadButton.setAlignmentX( CENTER_ALIGNMENT );
        backButton.setAlignmentX( CENTER_ALIGNMENT );

        this.add( Box.createVerticalGlue() );
        this.add( stryButton );
        this.add( Box.createVerticalGlue() );
        this.add( freeButton );
        this.add( Box.createVerticalGlue() );
        this.add( loadButton );
        this.add( Box.createVerticalGlue() );
        this.add( backButton );
        this.add( Box.createVerticalGlue() );
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        String p = null;
        if ( e.getActionCommand().equals( "Story Mode" ) ) p = "STORYGAME";
        else if ( e.getActionCommand().equals( "Endless Mode" ) ) p = "FREEGAME";
        else if ( e.getActionCommand().equals( "Load Game" ) ) p = "LOADGAME";
        else if ( e.getActionCommand().equals( "Back" ) ) p = "MAINMENU";

        if ( p != null ) carder.switchTo( p );
    }
}
