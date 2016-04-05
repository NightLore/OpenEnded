package gui;

import game.Window;
import gui.utilities.MappedSelector;
import gui.utilities.MenuNavigator;
import gui.utilities.SelectableButton;
import gui.utilities.Selector;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import constants.MainScreenConstants;

/**
 *  Main Menu Screen
 *
 *  @author  Nathan Man-ho Lui
 *  @version Oct 28, 2015
 *  @author  Assignment: OpenEnded
 *
 *  @author  Sources: none
 */
public class MainMenuScreen extends ScreenPanel implements MainScreenConstants
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    public MainMenuScreen( Carder frame, Image image )
    {
        this( frame, image, INITIAL );
    }
    
    public MainMenuScreen( Carder frame, Image image, String back )
    {
        super( frame, image, back );
        this.setLayout( new BorderLayout() );
        
        GridLayout gridLayout = new GridLayout( 0, 1 );
        gridLayout.setVgap( gapY );
        
        JPanel titlePanel = new ClearPanel();
        JPanel buttonPanel = new ClearPanel();
        JLabel title = new JLabel( Window.getGameName() );
        JLabel version = new JLabel( "                " + Window.getVersion() );
        SelectableButton playButton = new SelectableButton( "PLAY GAME" );
        SelectableButton stgsButton = new SelectableButton( SETTINGS );
        SelectableButton backButton = new SelectableButton( BACK );
        SelectableButton exitButton = new SelectableButton( "EXIT GAME" );
        
        titlePanel.setLayout( new BoxLayout( titlePanel, BoxLayout.Y_AXIS ) );
        buttonPanel.setLayout( gridLayout );
        buttonPanel.setBorder( BorderFactory.createEmptyBorder( 2*gapY, 2*gapX, 4*gapY, 2*gapX ) );

        title.setFont( new Font( title.getFont().getFontName(), Font.BOLD, 72 ) );
        version.setFont( new Font( version.getFont().getFontName(), Font.BOLD, 20 ) );

        // -------------------- Setting Listeners ----------------------- //
        playButton.addActionListener( this );
        stgsButton.addActionListener( this );
        backButton.addActionListener( this );
        exitButton.addActionListener( this );

        // -------------------- Setting Sizes     ----------------------- //
        buttonPanel.setPreferredSize( new Dimension( windowSize.width / 3, windowSize.height / 3 ) );

        // -------------------- Setting Alignment ----------------------- //
        title.setAlignmentX( CENTER_ALIGNMENT );
        version.setAlignmentX( LEFT_ALIGNMENT );

        // -------------------- Adding Components ----------------------- //
        this.add( titlePanel, BorderLayout.NORTH );
        this.add( buttonPanel, BorderLayout.EAST );

        titlePanel.add( title );
        titlePanel.add( version );
        buttonPanel.add( playButton );
        buttonPanel.add( stgsButton );
        buttonPanel.add( backButton );
        buttonPanel.add( exitButton );
        
        navigator = new MenuNavigator(1,4);
        navigator.addMenuItem( LISTGAME );
        navigator.addMenuItem( SETTINGS );
        navigator.addMenuItem( INITIAL );
        navigator.addMenuItem( "EXIT GAME" );
        Selector selector = new MappedSelector();
        selector.addSelectable( LISTGAME, playButton );
        selector.addSelectable( SETTINGS, stgsButton );
        selector.addSelectable( INITIAL, backButton );
        selector.addSelectable( "EXIT GAME", exitButton );
        navigator.setSelector( selector );
    }

    @Override
    public void act( String selected )
    {
        if ( check( selected ) ) return;
        String p = null;
        
        if ( selected.equals( "PLAY GAME" ) || selected.equals( LISTGAME ) ) p = LISTGAME;
         else if ( selected.equals( SETTINGS ) )                          p = SETTINGS;
        else if ( selected.equals( BACK ) || selected.equals( INITIAL ) ) p = INITIAL;
        else if ( selected.equals( "EXIT GAME" ) ) System.exit( 0 );
        
        if ( p != null ) carder.switchTo( p );
    }
}
