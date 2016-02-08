package gui.game;

import gui.Carder;
import gui.MenuNavigator;
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
    
    public static final String STORY_MODE = "STORY MODE";
    public static final String ENDLESS_MODE = "ENDLESS MODE";
    public static final String LOAD_MODE = "LOAD GAME";
    
    public ListGameScreen( Carder frame )
    {
        this( frame, MAINMENU );
    }

    public ListGameScreen( Carder frame, String back )
    {
        super( frame, "GrassyBackground.png", back );
        this.setLayout( new BoxLayout( this, BoxLayout.Y_AXIS ) );
        
        Dimension buttonSize = Window.buttonSize;
        JButton stryButton = new JButton( STORY_MODE );
        JButton freeButton = new JButton( ENDLESS_MODE );
        JButton loadButton = new JButton( LOAD_MODE );
        JButton backButton = new JButton( BACK );

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
        
        navigator = new MenuNavigator(1,4);
        navigator.addMenuItem( STORYGAME );
        navigator.addMenuItem( FREEGAME );
        navigator.addMenuItem( LOADGAME );
        navigator.addMenuItem( MAINMENU );
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        act( e.getActionCommand() );
    }
    
    @Override
    public void act( String selected )
    {
        super.act( selected );
        String p = null;
        if ( selected.equals( STORY_MODE ) || selected.equals( STORYGAME ) ) p = STORYGAME;
        else if ( selected.equals( ENDLESS_MODE ) ) p = FREEGAME;
        else if ( selected.equals( LOAD_MODE ) ) p = LOADGAME;
        else if ( selected.equals( BACK ) ) back();

        if ( p != null ) carder.switchTo( p );
    }
}
