package gui.game;

import gui.Carder;
import gui.MenuNavigator;
import gui.ScreenPanel;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
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
        int marginX = 8 * gapX;
        int marginY = 2 * gapY;
        this.setLayout( new GridLayout( 0, 1, 0, marginY ) );
        this.setBorder( BorderFactory.createEmptyBorder( marginY, marginX, 3 * marginY / 2, marginX ) );
        
        JButton stryButton = new JButton( STORY_MODE );
        JButton freeButton = new JButton( ENDLESS_MODE );
        JButton loadButton = new JButton( LOAD_MODE );
        JButton backButton = new JButton( BACK );

        stryButton.addActionListener( this );
        freeButton.addActionListener( this );
        loadButton.addActionListener( this );
        backButton.addActionListener( this );

        this.add( stryButton );
        this.add( freeButton );
        this.add( loadButton );
        this.add( backButton );
        
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
