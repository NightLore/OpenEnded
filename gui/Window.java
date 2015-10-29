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
    
    public static final String GAME_TITLE = "OpenEnded";
    private static final Dimension windowSize = new Dimension( 1280, 720 );
    public final Dimension buttonSize = new Dimension( windowSize.width / 10, windowSize.height / 20 );
    
    public enum Panel {
        INIT, MENU, GAME, LOAD, STGS
    }
    private JPanel main;
    private CardLayout cards;
    private EnumMap<Panel, Screen> screens;

    public Window()
    {
        this.setTitle( GAME_TITLE + " V" + serialVersionUID );
        this.setName( GAME_TITLE );
        this.setSize( windowSize );
        this.setLocationRelativeTo( null );
        this.setDefaultCloseOperation( EXIT_ON_CLOSE );
//        this.setResizable( false );
        this.setVisible( true );
        
        screens = new EnumMap<Panel, Screen>( Panel.class );
        main = new JPanel();
        cards = new CardLayout();
        main.setLayout( cards );
        
        ScreenPanel init = new InitialScreen( this );
        ScreenPanel menu = new MainMenu( this );
        ScreenPanel game = new Game( this );
        ScreenPanel load = new LoadingScreen( this );
        ScreenPanel stgs = new SettingsScreen( this );
        
        screens.put( Panel.INIT, init );
        screens.put( Panel.MENU, menu );
        screens.put( Panel.GAME, game );
        screens.put( Panel.LOAD, load );
        screens.put( Panel.STGS, stgs );
        
        main.add( init, Panel.INIT.toString() );
        main.add( menu, Panel.MENU.toString() );
        main.add( game, Panel.GAME.toString() );
        main.add( load, Panel.LOAD.toString() );
        main.add( stgs, Panel.STGS.toString() );
        
        this.add( main );
    }
    
    public void switchTo( Screen from, Panel p )
    {
        from.cover();
        cards.show( main, p.toString() );
        screens.get( p ).shown();
    }
    
//    public static int getWindowWidth() { return WINDOW_SIZE.width; }
//    public static int getWindowHeight() { return WINDOW_SIZE.height; }

    public static void main( String[] args ) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Window();
            }
        });
    }
}
