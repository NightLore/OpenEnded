package gui;

import game.Settings;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window extends JFrame implements Cards// implements KeyListener, MouseListener
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private static final Dimension windowSize = new Dimension( 1280, 720 );
    public final Dimension buttonSize = new Dimension( windowSize.width, windowSize.height / 20 );
    
    private JPanel main;
    private CardLayout cards;
    private HashMap<String, Screen> screens;
    private Settings settings;

    public Window()
    {
        this.setTitle( getGameName() + " " + getVersion() );
        this.setName( getGameName() );
        this.setSize( windowSize );
        this.setMinimumSize( windowSize );
        this.setLocationRelativeTo( null );
        this.setDefaultCloseOperation( EXIT_ON_CLOSE );
//        this.setResizable( false );
        this.setVisible( true );
        
        screens = new HashMap<String, Screen>();
        main = new JPanel();
        cards = new CardLayout();
        main.setLayout( cards );
        settings = new Settings(); // TODO load settings
        
        ScreenPanel initial = new InitialScreen( this, "INITIAL" );
        ScreenPanel mainMenu = new MainMenuScreen( this, "MAINMENU" );
        ScreenPanel settings = new SettingsScreen( this, "SETTINGS", "MAINMENU", this.settings );
        ScreenPanel listGame = new ListGameScreen( this, "LISTGAME" );
        ScreenPanel storyGame = new GameScreen( this, "STORYGAME", this.settings );
        ScreenPanel loadGame = new LoadGameScreen( this, "LOADGAME" );
        ScreenPanel freeGame = new GameScreen( this, "FREEGAME", this.settings );
        
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
        return "V0.15";
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
