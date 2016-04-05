package gui;

import java.awt.Image;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

import constants.KeyConstants;
import constants.MainScreenConstants;
import game.Assets;
import game.Settings;
import gui.game.GameScreen;
import gui.game.ListGameScreen;
import gui.game.LoadGameScreen;

/**
 *  The main panel that loads all the other panels
 *
 *  @author  Nathan M. Lui
 *  @version Jan 10, 2016
 *  @author  Assignment: OpenEnded
 */
public class MainPanel extends NavigatablePanel implements MainScreenConstants, KeyConstants
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private Assets assets;
    private void loadAssets()
    {
        assets.loadFiles();
        assets.loadAssets();
    }

    private Settings settings;
    private Action spaceAction, escapeAction, enterAction, backspaceAction;
    
    public MainPanel()
    {
        assets = new Assets();
        loadAssets();
        
        settings = new Settings(); // TODO load settings
        currentPanel = INITIAL;
        
        Image background = assets.getSkin( Assets.BACKGROUND );
        ScreenPanel initial = new InitialScreen( this, background );
        ScreenPanel mainMenu = new MainMenuScreen( this, background );
        ScreenPanel settings = new SettingsScreen( this, MAINMENU, this.settings );
        ScreenPanel listGame = new ListGameScreen( this, background );
        ScreenPanel storyGame = new GameScreen( this, this.settings, assets );
        ScreenPanel loadGame = new LoadGameScreen( this );
        ScreenPanel freeGame = new GameScreen( this, this.settings, assets );
        
        navigatables.put( INITIAL, initial );
        navigatables.put( MAINMENU, mainMenu );
        navigatables.put( SETTINGS, settings );
        navigatables.put( LISTGAME, listGame );
        navigatables.put( STORYGAME, storyGame );
        navigatables.put( LOADGAME, loadGame );
        navigatables.put( FREEGAME, freeGame );
        
        this.add( initial, INITIAL );
        this.add( mainMenu, MAINMENU );
        this.add( settings, SETTINGS );
        this.add( listGame, LISTGAME );
        this.add( storyGame, STORYGAME );
        this.add( loadGame, LOADGAME );
        this.add( freeGame, FREEGAME );

        spaceAction = new ControlAction( this, SPACE );
        escapeAction = new ControlAction( this, ESCAPE );
        enterAction = new ControlAction( this, ENTER );
        backspaceAction = new ControlAction( this, BACKSPACE );
        
        this.putKeyAction( KeyEvent.VK_W, UP, upAction );
        this.putKeyAction( KeyEvent.VK_A, LEFT, leftAction );
        this.putKeyAction( KeyEvent.VK_S, DOWN, downAction );
        this.putKeyAction( KeyEvent.VK_D, RIGHT, rightAction );
        this.putKeyAction( KeyEvent.VK_V, CONFIRM, confirmAction );
        this.putKeyAction( KeyEvent.VK_B, CANCEL, cancelAction );
        this.putKeyAction( KeyEvent.VK_SPACE, SPACE, spaceAction );
        this.putKeyAction( KeyEvent.VK_ESCAPE, ESCAPE, escapeAction );
        this.putKeyAction( KeyEvent.VK_ENTER, ENTER, enterAction );
        this.putKeyAction( KeyEvent.VK_BACK_SPACE, BACKSPACE, backspaceAction );
        // treat the following the same as WASD VB
        this.getInputMap( WHEN_IN_FOCUSED_WINDOW ).put( KeyStroke.getKeyStroke( KeyEvent.VK_UP, 0 ), UP );
        this.getInputMap( WHEN_IN_FOCUSED_WINDOW ).put( KeyStroke.getKeyStroke( KeyEvent.VK_LEFT, 0 ), LEFT );
        this.getInputMap( WHEN_IN_FOCUSED_WINDOW ).put( KeyStroke.getKeyStroke( KeyEvent.VK_DOWN, 0 ), DOWN );
        this.getInputMap( WHEN_IN_FOCUSED_WINDOW ).put( KeyStroke.getKeyStroke( KeyEvent.VK_RIGHT, 0 ), RIGHT );
        this.getInputMap( WHEN_IN_FOCUSED_WINDOW ).put( KeyStroke.getKeyStroke( KeyEvent.VK_COMMA, 0 ), CONFIRM );
        this.getInputMap( WHEN_IN_FOCUSED_WINDOW ).put( KeyStroke.getKeyStroke( KeyEvent.VK_PERIOD, 0 ), CANCEL );
    }
    
    @Override
    public void switchTo( String from, String to )
    {
        cardLayout.show( this, to );
    }
}
