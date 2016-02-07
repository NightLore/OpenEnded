package gui;

import game.Assets;
import game.Settings;
import gui.game.GameScreen;
import gui.game.ListGameScreen;
import gui.game.LoadGameScreen;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *  Main JFrame of the game
 *
 *  @author  Nathan Man-ho Lui
 *  @version Oct 28, 2015
 *  @author  Assignment: OpenEnded
 *
 *  @author  Sources: none
 */
public class Window extends JFrame implements Carder// implements KeyListener, MouseListener
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private static final Dimension windowSize = new Dimension( 1280, 720 );
    public final Dimension buttonSize = new Dimension( windowSize.width, windowSize.height / 20 );
    
    private Assets assets;
    private void loadAssets()
    {
        assets.loadFiles();
        assets.loadAssets();
    }
    
    private JPanel main;
    private CardLayout cards;
    private HashMap<String, Screen> screens;
    private Settings settings;
    private String prevScreen;

    public Window()
    {
        this.setTitle( getGameName() + " " + getVersion() );
        this.setName( getGameName() );
        this.getContentPane().setPreferredSize( windowSize );
        this.pack();
//        this.setMinimumSize( windowSize );
        this.setLocationRelativeTo( null );
        this.setDefaultCloseOperation( EXIT_ON_CLOSE );
//        this.setResizable( false );
        this.setVisible( true );
        
        screens = new HashMap<String, Screen>();
        main = new JPanel();
        cards = new CardLayout();
        main.setLayout( cards );
        settings = new Settings(); // TODO load settings
        assets = new Assets();
        prevScreen = "INITIAL";
        loadAssets();
        
        ScreenPanel initial = new InitialScreen( this );
        ScreenPanel mainMenu = new MainMenuScreen( this );
        ScreenPanel settings = new SettingsScreen( this, "MAINMENU", this.settings );
        ScreenPanel listGame = new ListGameScreen( this );
        ScreenPanel storyGame = new GameScreen( this, this.settings, assets );
        ScreenPanel loadGame = new LoadGameScreen( this );
        ScreenPanel freeGame = new GameScreen( this, this.settings, assets );
        
        screens.put( "INITIAL", initial );
        screens.put( "MAINMENU", mainMenu );
        screens.put( "SETTINGS", settings );
        screens.put( "LISTGAME", listGame );
        screens.put( "STORYGAME", storyGame );
        screens.put( "LOADGAME", loadGame );
        screens.put( "FREEGAME", freeGame );
        
        main.add( initial, "INITIAL" );
        main.add( mainMenu, "MAINMENU" );
        main.add( settings, "SETTINGS" );
        main.add( listGame, "LISTGAME" );
        main.add( storyGame, "STORYGAME" );
        main.add( loadGame, "LOADGAME" );
        main.add( freeGame, "FREEGAME" );
        
        this.add( main );
    }

    @Override
    public void switchTo( String to )
    {
        switchTo( prevScreen, to );
        prevScreen = to;
    }
    
    @Override
    public void switchTo( String from, String p )
    {
        screens.get( from ).cover();
        cards.show( main, p );
        screens.get( p ).shown();
    }
    
//    public static int getWindowWidth() { return WINDOW_SIZE.width; }
//    public static int getWindowHeight() { return WINDOW_SIZE.height; }
    public String getGameName()
    {
        return "OpenEnded";
    }
    
    public String getVersion()
    {
        return "V0.19";
    }

    public static void main( String[] args ) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Window();
            }
        });
    }
}
