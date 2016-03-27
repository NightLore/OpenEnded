package gui.game;

import gui.Carder;
import gui.ScreenPanel;
import gui.utilities.MappedSelector;
import gui.utilities.MenuNavigator;
import gui.utilities.SelectableButton;
import gui.utilities.Selector;

import java.awt.GridLayout;

import javax.swing.BorderFactory;

import constants.MainScreenConstants;

/**
 *  Screen Lists all game modes for user to play
 *
 *  @author  Nathan Man-ho Lui
 *  @version Oct 28, 2015
 *  @author  Assignment: OpenEnded
 *
 *  @author  Sources: none
 */
public class ListGameScreen extends ScreenPanel implements MainScreenConstants
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
        
        SelectableButton stryButton = new SelectableButton( STORY_MODE );
        SelectableButton freeButton = new SelectableButton( ENDLESS_MODE );
        SelectableButton loadButton = new SelectableButton( LOAD_MODE );
        SelectableButton backButton = new SelectableButton( BACK );

        stryButton.addActionListener( this );
        freeButton.addActionListener( this );
        loadButton.addActionListener( this );
        backButton.addActionListener( this );
        
        stryButton.setActionCommand( STORYGAME );
        freeButton.setActionCommand( FREEGAME );
        loadButton.setActionCommand( LOADGAME );
        backButton.setActionCommand( back );

        this.add( stryButton );
        this.add( freeButton );
        this.add( loadButton );
        this.add( backButton );
        
        navigator = new MenuNavigator(1,4);
        navigator.addMenuItem( STORYGAME );
        navigator.addMenuItem( FREEGAME );
        navigator.addMenuItem( LOADGAME );
        navigator.addMenuItem( MAINMENU );
        Selector selector = new MappedSelector();
        selector.addSelectable( STORYGAME, stryButton );
        selector.addSelectable( FREEGAME, freeButton );
        selector.addSelectable( LOADGAME, loadButton );
        selector.addSelectable( back, backButton );
        navigator.setSelector( selector );
    }
    
    @Override
    public void act( String selected )
    {
        if ( check( selected ) ) return;

        carder.switchTo( selected );
    }
}
