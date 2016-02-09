package gui.game;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;

import gui.Carder;
import gui.ScreenPanel;
import gui.utilities.MappedSelector;
import gui.utilities.MenuNavigator;
import gui.utilities.SelectableButton;
import gui.utilities.Selector;

/**
 *  Panel shown when game is paused
 *
 *  @author  Nathan Man-ho Lui
 *  @version Feb 8, 2016
 */
public class GamePausePanel extends ScreenPanel
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    public GamePausePanel( Carder carder )
    {
        this( carder, GamePanel.GAME_PANEL );
    }

    public GamePausePanel( Carder carder, String back )
    {
        super( carder, new Color( 0, 0, 0, 64 ), back );
        int marginX = 8 * gapX;
        int marginY = 2 * gapY;
        
        this.setLayout( new GridLayout( 0, 1, 0, marginY ) );
        this.setBorder( BorderFactory.createEmptyBorder( marginY, marginX, 3 * marginY / 2, marginX) );
        
        SelectableButton resumeButton = new SelectableButton ( "RESUME" );
        SelectableButton inventoryButton = new SelectableButton( "INVENTORY" );
        SelectableButton settingsButton = new SelectableButton( GamePanel.SETTINGS_PANEL );
        SelectableButton returnButton = new SelectableButton( "RETURN TO MAIN MENU" );

        resumeButton.addActionListener( this );
        inventoryButton.addActionListener( this );
        settingsButton.addActionListener( this );
        returnButton.addActionListener( this );
        
        this.add( resumeButton );
        this.add( inventoryButton );
        this.add( settingsButton );
        this.add( returnButton );
        
        navigator = new MenuNavigator(1,4);
        navigator.addMenuItem( GamePanel.GAME_PANEL );
        navigator.addMenuItem( GamePanel.ITEM_PANEL );
        navigator.addMenuItem( GamePanel.SETTINGS_PANEL );
        navigator.addMenuItem( GamePanel.EXIT_PANEL );
        Selector selector = new MappedSelector();
        selector.addSelectable( GamePanel.GAME_PANEL, resumeButton );
        selector.addSelectable( GamePanel.ITEM_PANEL, inventoryButton );
        selector.addSelectable( GamePanel.SETTINGS_PANEL, settingsButton );
        selector.addSelectable( GamePanel.EXIT_PANEL, returnButton );
        navigator.setSelector( selector );
    }
    
    @Override
    public void act( String selected )
    {
        super.act( selected );
        if ( selected.equals( "P" ) )
        {
            back();
        }
        else if ( !selected.equals( SPACE ) && !selected.equals( ENTER ) 
         && !selected.equals( ESCAPE ) && !selected.equals( BACKSPACE ) )
        {
            carder.switchTo( selected );
        }
    }

}
