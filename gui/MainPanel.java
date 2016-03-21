package gui;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;

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
public class MainPanel extends NavigatablePanel
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
        
        ScreenPanel initial = new InitialScreen( this );
        ScreenPanel mainMenu = new MainMenuScreen( this );
        ScreenPanel settings = new SettingsScreen( this, MAINMENU, this.settings );
        ScreenPanel listGame = new ListGameScreen( this );
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
        
        spaceAction = new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed( ActionEvent arg0 )
            {
                act( SPACE );
            }
        };
        escapeAction = new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed( ActionEvent arg0 )
            {
                act( ESCAPE );
            }
        };
        enterAction = new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed( ActionEvent arg0 )
            {
                act( ENTER );
            }
        };
        backspaceAction = new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed( ActionEvent arg0 )
            {
                act( BACKSPACE );
            }
        };
        

        this.getInputMap( WHEN_IN_FOCUSED_WINDOW ).put( KeyStroke.getKeyStroke( KeyEvent.VK_W, 0 ), UP );
        this.getInputMap( WHEN_IN_FOCUSED_WINDOW ).put( KeyStroke.getKeyStroke( KeyEvent.VK_A, 0 ), LEFT );
        this.getInputMap( WHEN_IN_FOCUSED_WINDOW ).put( KeyStroke.getKeyStroke( KeyEvent.VK_S, 0 ), DOWN );
        this.getInputMap( WHEN_IN_FOCUSED_WINDOW ).put( KeyStroke.getKeyStroke( KeyEvent.VK_D, 0 ), RIGHT );
        this.getInputMap( WHEN_IN_FOCUSED_WINDOW ).put( KeyStroke.getKeyStroke( KeyEvent.VK_V, 0 ), CONFIRM );
        this.getInputMap( WHEN_IN_FOCUSED_WINDOW ).put( KeyStroke.getKeyStroke( KeyEvent.VK_B, 0 ), CANCEL );
        this.getInputMap( WHEN_IN_FOCUSED_WINDOW ).put( KeyStroke.getKeyStroke( KeyEvent.VK_UP, 0 ), UP );
        this.getInputMap( WHEN_IN_FOCUSED_WINDOW ).put( KeyStroke.getKeyStroke( KeyEvent.VK_LEFT, 0 ), LEFT );
        this.getInputMap( WHEN_IN_FOCUSED_WINDOW ).put( KeyStroke.getKeyStroke( KeyEvent.VK_DOWN, 0 ), DOWN );
        this.getInputMap( WHEN_IN_FOCUSED_WINDOW ).put( KeyStroke.getKeyStroke( KeyEvent.VK_RIGHT, 0 ), RIGHT );
        this.getInputMap( WHEN_IN_FOCUSED_WINDOW ).put( KeyStroke.getKeyStroke( KeyEvent.VK_COMMA, 0 ), CONFIRM );
        this.getInputMap( WHEN_IN_FOCUSED_WINDOW ).put( KeyStroke.getKeyStroke( KeyEvent.VK_PERIOD, 0 ), CANCEL );
        this.getInputMap( WHEN_IN_FOCUSED_WINDOW ).put( KeyStroke.getKeyStroke( KeyEvent.VK_SPACE, 0 ), SPACE );
        this.getInputMap( WHEN_IN_FOCUSED_WINDOW ).put( KeyStroke.getKeyStroke( KeyEvent.VK_ESCAPE, 0 ), ESCAPE );
        this.getInputMap( WHEN_IN_FOCUSED_WINDOW ).put( KeyStroke.getKeyStroke( KeyEvent.VK_ENTER, 0 ), ENTER );
        this.getInputMap( WHEN_IN_FOCUSED_WINDOW ).put( KeyStroke.getKeyStroke( KeyEvent.VK_BACK_SPACE, 0 ), BACKSPACE );
        this.getActionMap().put( UP, upAction );
        this.getActionMap().put( LEFT, leftAction );
        this.getActionMap().put( DOWN, downAction );
        this.getActionMap().put( RIGHT, rightAction );
        this.getActionMap().put( CONFIRM, confirmAction );
        this.getActionMap().put( CANCEL, cancelAction );
        this.getActionMap().put( SPACE, spaceAction );
        this.getActionMap().put( ESCAPE, escapeAction );
        this.getActionMap().put( ENTER, enterAction );
        this.getActionMap().put( BACKSPACE, backspaceAction );
    }
    
    @Override
    public void switchTo( String from, String to )
    {
        cardLayout.show( this, to );
    }
}
