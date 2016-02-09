package gui.game;

import game.Settings;
import gui.NavigatablePanel;
import gui.ScreenPanel;
import gui.SettingsScreen;
import gui.game.player.PlayerManagerPanel;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;

/**
 *  Manages the User Interface in game
 *
 *  @author  Nathan Man-ho Lui
 *  @version Dec 30, 2015
 *  @author  Assignment: OpenEnded
 *
 *  @author  Sources: none
 */
public class GamePanel extends NavigatablePanel
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    public static final String LOAD_PANEL = "LOAD";
    public static final String GAME_PANEL = "GAME";
    public static final String ITEM_PANEL = "ITEM";
    public static final String PAUSE_PANEL = "PAUSE";
    public static final String SETTINGS_PANEL = "SETTINGS";
    public static final String EXIT_PANEL = "EXIT";
    public static final String OVER_PANEL = "OVER";
    
    private GameScreen game;
    private SettingsScreen settingsPanel;
    private PlayerManagerPanel itemPanel;
    private InGamePanel gamePanel;
    
    private Action pauseAction;
    
    public GamePanel( GameScreen game, Settings settings )
    {
        this.game = game;
        this.currentPanel = LOAD_PANEL;

        ScreenPanel loadPanel = new GameLoadPanel( this );
        
        gamePanel = new InGamePanel( this, settings.numLives );
        gamePanel.setPreferredSize( getPreferredSize() );
        
        itemPanel = new PlayerManagerPanel( this, PAUSE_PANEL );
        
        ScreenPanel pausePanel = new GamePausePanel( this );

        settingsPanel = new SettingsScreen( this, PAUSE_PANEL, settings );
        settingsPanel.setBackground( new Color( 0, 0, 0, 192 ) );
        
        ScreenPanel areYouSurePanel = new GameExitPanel( this );
            
        ScreenPanel gameOverPanel = new GameOverPanel( this );
            
        this.add( loadPanel, LOAD_PANEL );
        this.add( gamePanel, GAME_PANEL );
        this.add( itemPanel, ITEM_PANEL );
        this.add( pausePanel, PAUSE_PANEL );
        this.add( settingsPanel, SETTINGS_PANEL );
        this.add( areYouSurePanel, EXIT_PANEL );
        this.add( gameOverPanel, OVER_PANEL );
        
        navigatables.put( LOAD_PANEL, loadPanel );
        navigatables.put( GAME_PANEL, gamePanel );
        navigatables.put( ITEM_PANEL, itemPanel );
        navigatables.put( PAUSE_PANEL, pausePanel );
        navigatables.put( SETTINGS_PANEL, settingsPanel );
        navigatables.put( EXIT_PANEL, areYouSurePanel );
        navigatables.put( OVER_PANEL, gameOverPanel );
        
        this.switchTo( GAME_PANEL );
        
        pauseAction = new AbstractAction() {
            
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed( ActionEvent e )
            {
                act( "P" );
            }
        };
        this.getInputMap( WHEN_IN_FOCUSED_WINDOW ).put( KeyStroke.getKeyStroke( KeyEvent.VK_P, 0 ), PAUSE_PANEL );
        this.getActionMap().put( PAUSE_PANEL, pauseAction );
    }
    
    public void reset()
    {
        itemPanel.initialize( game.getGame() );
    }
    
    public void update( int numLives )
    {
        gamePanel.update( numLives );
    }

    @Override
    public void switchTo( String from, String to )
    {
        if ( from.equals( SETTINGS_PANEL ) ) {
            game.settingsUpdate();
            update( game.getGame().numLives() );
        }
        
        
        if ( to.equalsIgnoreCase( "RESUME" ) || to.equalsIgnoreCase( GAME_PANEL ) ) {
            to = GAME_PANEL;
        }
        else if ( to.equalsIgnoreCase( "INVENTORY" ) || to.equalsIgnoreCase( ITEM_PANEL ) ) {
            itemPanel.shown();
            to = ITEM_PANEL;
        }
        else if ( to.equalsIgnoreCase( SETTINGS_PANEL ) ) {
            settingsPanel.shown();
            to = SETTINGS_PANEL;
        }
        else if ( to.equalsIgnoreCase( "Return to Main Menu" ) || to.equalsIgnoreCase( EXIT_PANEL ) ) {
            to = EXIT_PANEL;
        }
        else if ( to.equalsIgnoreCase( "Yes" ) ) {
            game.returnToMainMenu(); // TODO quit game
            return;
        }
        else if ( to.equalsIgnoreCase( "No" ) || to.equalsIgnoreCase( PAUSE_PANEL ) ) {
            to = PAUSE_PANEL;
        }
        game.inGame = to.equalsIgnoreCase( GAME_PANEL ) || to.equalsIgnoreCase( OVER_PANEL );
        this.cardLayout.show( this, to.toUpperCase() );
        currentPanel = to;
    }
    
}
