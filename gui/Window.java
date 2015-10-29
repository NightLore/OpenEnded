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
    static final Dimension WINDOW_SIZE = new Dimension( 1280, 720 );
    static final Dimension buttonSize = new Dimension( WINDOW_SIZE.width / 10, WINDOW_SIZE.height / 20 );
    
    public enum Panel {
        INIT, MENU, GAME
    }
    private JPanel main;
    private CardLayout cards;
    private EnumMap<Panel, Screen> screens;

    public Window()
    {
        this.setTitle( GAME_TITLE + " V" + serialVersionUID );
        this.setName( GAME_TITLE );
        this.setSize( WINDOW_SIZE );
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
        
        screens.put( Panel.INIT, init );
        screens.put( Panel.MENU, menu );
        screens.put( Panel.GAME, game );
        
        main.add( init, Panel.INIT.toString() );
        main.add( menu, Panel.MENU.toString() );
        main.add( game, Panel.GAME.toString() );
        
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
