package gui;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.util.EnumMap;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window extends JFrame// implements KeyListener, MouseListener
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private static final Dimension windowSize = new Dimension( 1280, 720 );
    public final Dimension buttonSize = new Dimension( windowSize.width, windowSize.height / 20 );
    
    public enum Panel {
        INITIAL, MAINMENU, SETTINGS, LISTGAME, STORYGAME, LOADGAME, FREEGAME 
    }
    private JPanel main;
    private CardLayout cards;
    private EnumMap<Panel, Screen> screens;

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
        
        screens = new EnumMap<Panel, Screen>( Panel.class );
        main = new JPanel();
        cards = new CardLayout();
        main.setLayout( cards );
        
        ScreenPanel initial = new InitialScreen( this, Panel.INITIAL );
        ScreenPanel mainMenu = new MainMenuScreen( this, Panel.MAINMENU );
        ScreenPanel settings = new SettingsScreen( this, Panel.SETTINGS );
        ScreenPanel listGame = new ListGameScreen( this, Panel.LISTGAME );
        ScreenPanel storyGame = new GameScreen( this, Panel.STORYGAME );
        ScreenPanel loadGame = new LoadGameScreen( this, Panel.LOADGAME );
        ScreenPanel freeGame = new GameScreen( this, Panel.FREEGAME );
        
        screens.put( Panel.INITIAL, initial );
        screens.put( Panel.MAINMENU, mainMenu );
        screens.put( Panel.SETTINGS, settings );
        screens.put( Panel.LISTGAME, listGame );
        screens.put( Panel.STORYGAME, storyGame );
        screens.put( Panel.LOADGAME, loadGame );
        screens.put( Panel.FREEGAME, freeGame );
        
        main.add( initial, Panel.INITIAL.toString() );
        main.add( mainMenu, Panel.MAINMENU.toString() );
        main.add( settings, Panel.SETTINGS.toString() );
        main.add( listGame, Panel.LISTGAME.toString() );
        main.add( storyGame, Panel.STORYGAME.toString() );
        main.add( loadGame, Panel.LOADGAME.toString() );
        main.add( freeGame, Panel.FREEGAME.toString() );
        
        this.add( main );
    }
    
    public void switchTo( Panel from, Panel p )
    {
        screens.get( from ).cover();
        cards.show( main, p.toString() );
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
        return "V" + serialVersionUID;
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
