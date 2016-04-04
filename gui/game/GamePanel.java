package gui.game;

import game.Game;
import game.Settings;
import gui.NavigatablePanel;
import gui.ScreenPanel;
import gui.SettingsScreen;
import gui.game.player.PlayerManagerPanel;

import java.awt.Color;
import java.awt.event.KeyEvent;

import javax.swing.Action;

import constants.GamePanelConstants;

/**
 *  Manages the User Interface in game
 *
 *  @author  Nathan Man-ho Lui
 *  @version Dec 30, 2015
 *  @author  Assignment: OpenEnded
 *
 *  @author  Sources: none
 */
public class GamePanel extends NavigatablePanel implements GamePanelConstants
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private GameScreen gameScreen;
    private SettingsScreen settingsPanel;
    private PlayerManagerPanel itemPanel;
    private GameOverlay overlay;
    
    private Action pauseAction;
    
    public GamePanel( GameScreen g, Settings settings )
    {
        this.gameScreen = g;
        this.currentPanel = LOAD_PANEL;

        ScreenPanel loadPanel = new GameLoadPanel( this );
        
        InGamePanel gamePanel = new InGamePanel( this, g.getAssets() );
        gamePanel.setPreferredSize( getPreferredSize() );
        overlay = gamePanel;
        
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
        
        pauseAction = new ControlAction( this, "P" );
        this.putKeyAction( KeyEvent.VK_P, PAUSE_PANEL, pauseAction );
    }
    
    public void reset()
    {
        itemPanel.initialize( gameScreen.getGame() );
    }
    
    public void updateSettings()
    {
        gameScreen.getGame().updateSettings();
    }
    
    public void updateOverlay()
    {
        Game g = gameScreen.getGame();
        update( g.numLives(), g.numKilled() );
    }
    
    private void update( int numLives, int numKilled )
    {
        overlay.updateLives( numLives );
        overlay.updateKilled( numKilled );
    }

    @Override
    public void switchTo( String from, String to )
    {
        if ( from.equals( SETTINGS_PANEL ) ) {
            this.updateSettings();
            this.updateOverlay();
        }
        
        if ( to.equalsIgnoreCase( LOAD_PANEL ) ) {
            gameScreen.returnToMainMenu(); // TODO quit game
        }
        gameScreen.inGame = to.equalsIgnoreCase( GAME_PANEL ) || to.equalsIgnoreCase( OVER_PANEL );
        this.cardLayout.show( this, to );
        gameScreen.requestFocusInWindow(); // update gameScreen in case of mouse clicks
    }
    
    public GameOverlay getOverlay()
    {
        return overlay;
    }
    
}
