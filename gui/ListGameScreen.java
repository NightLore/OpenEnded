package gui;

import gui.Window.Panel;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;

public class ListGameScreen extends ScreenPanel implements ActionListener
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public ListGameScreen( Window frame, Panel panel )
    {
        super( frame, panel, "GrassyBackground.png" );
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
        Panel p = null;
        if ( e.getActionCommand().equals( "Story Mode" ) ) p = Panel.STORYGAME;
        else if ( e.getActionCommand().equals( "Endless Mode" ) ) p = Panel.FREEGAME;
        else if ( e.getActionCommand().equals( "Load Game" ) ) p = Panel.LOADGAME;
        else if ( e.getActionCommand().equals( "Back" ) ) p = Panel.MAINMENU;

        if ( p != null ) window.switchTo( screen, p );
    }
}
