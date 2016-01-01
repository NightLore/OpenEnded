package gui;

import java.awt.BorderLayout;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.Timer;

import game.Assets;
import game.Game;
import game.InputManager;
import game.Settings;

public class GameScreen extends ScreenPanel implements ActionListener
{
    /**
     * Time of one second in milliseconds.
     * 1 second = 1 000 milliseconds
     */
    public static final int secInMillisec = 1000;
    
    /**
     * Time of one millisecond in nanoseconds.
     * 1 millisecond = 1 000 000 nanoseconds
     */
    public static final long milisecInNanosec = 1000000L;
    
    /**
     * FPS - Frames per second
     * How many times per second the game should update?
     */
    private final int GAME_FPS = 60;
    /**
     * Pause between updates. It is in nanoseconds.
     */
    private final int GAME_UPDATE_PERIOD = secInMillisec / GAME_FPS;
    /**
     * Possible states of the game
     */
    public static enum GameState{
        STARTING, PLAYING, PAUSED, RESUMED, VISUALIZING
    }
//    /**
//     * Current state of the game
//     */
//    public static GameState gameState;
    
    /**
     * Elapsed game time in nanoseconds.
     */
    private long gameTime;
    // It is used for calculating elapsed time.
    private long lastTime;
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    public boolean inGame;

    private Game game;
    private InputManager input;
    private Settings settings;
    private Assets assets;
    private GamePanel ui;
    private Timer updater;
    public GameScreen( Cards frame, String panel, Settings settings, Assets assets )
    {
        super( frame, panel );
        input = new InputManager( this );
        this.settings = settings;
        this.assets = assets;
        this.ui = new GamePanel( this, settings );
        
        // Adds the keyboard listener to JPanel to receive key events from this component.
        this.addKeyListener( input );
        // Adds the mouse listener to JPanel to receive mouse events from this component.
        this.addMouseListener( input );
        
        // If you will draw your own mouse cursor or if you just want that mouse cursor disapear, 
        // insert "true" into if condition and mouse cursor will be removed.
//        if( false )
//        {
//            BufferedImage blankCursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
//            Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(blankCursorImg, new Point(0, 0), null);
//            this.setCursor(blankCursor);
//        }
        
        this.setLayout( new BorderLayout() );
        this.add( ui, BorderLayout.CENTER );
        
        updater = new Timer( GAME_UPDATE_PERIOD, new ActionListener(){ // TODO fix stuttering
            @Override
            public void actionPerformed( ActionEvent arg0 )
            {
                if ( inGame ) {
                    if ( InputManager.keyboardKeyState( KeyEvent.VK_ESCAPE ) )
                        ui.switchTo( "PAUSE" );
                    gameTime += System.nanoTime() - lastTime;
                    game.updateGame( gameTime, mousePosition() );
                    lastTime = System.nanoTime();
                }
                repaint();
            }
        } );
    }
    
    @Override
    public void shown()
    {
        this.requestFocusInWindow();
        this.newGame();
        updater.start();
        ui.switchTo( "GAME" );
    }
    
    @Override
    public void cover()
    {
        updater.stop();
        InputManager.reset();
        ui.switchTo( "LOAD" );
    }
    
    /**
     * Starts new game.
     */
    public void newGame()
    {
        // We set gameTime to zero and lastTime to current time for later calculations.
        gameTime = 0;
        lastTime = System.nanoTime();
        
        game = new Game( carder, settings, assets );
    }
    
    
    /**
     * Returns the position of the mouse pointer in game frame/window.
     * If mouse position is null than this method return 0,0 coordinate.
     * 
     * @return Point of mouse coordinates.
     */
    private Point mousePosition()
    {
        try
        {
            Point mp = this.getMousePosition();
            
            if(mp != null)
                return this.getMousePosition();
            else
                return new Point(0, 0);
        }
        catch (Exception e)
        {
            return new Point(0, 0);
        }
    }
    
    
    /**
     * This method is called when keyboard key is released.
     * 
     * @param e KeyEvent
     */
    public void keyReleasedFramework(KeyEvent e)
    {
        
    }
    
    @Override
    public void draw( Graphics2D g )
    {
        game.draw( g, mousePosition() );
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        if ( e.getActionCommand().equals( "Back" ) )
        {
            carder.switchTo( screen, "LISTGAME" );
        }
    }
       
    public void returnToMainMenu()
    {
        // TODO destroy current game session
        carder.switchTo( screen, "MAINMENU" );
    }
    
}
