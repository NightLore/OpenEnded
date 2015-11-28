package gui;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;

import game.Game;
import game.InputManager;
import gui.Window.Panel;

public class GameScreen extends ScreenPanel implements ActionListener
{
    /**
     * Time of one second in nanoseconds.
     * 1 second = 1 000 000 000 nanoseconds
     */
    public static final long secInNanosec = 1000000000L;
    
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
    private final long GAME_UPDATE_PERIOD = secInNanosec / GAME_FPS;
    /**
     * Possible states of the game
     */
    public static enum GameState{
        STARTING, PLAYING, PAUSED, RESUMED, VISUALIZING
    }
    /**
     * Current state of the game
     */
    public static GameState gameState;
    
    /**
     * Elapsed game time in nanoseconds.
     */
    private long gameTime;
    // It is used for calculating elapsed time.
    private long lastTime;
    
//    private boolean inGame;
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Game game;
    private InputManager input;
    public GameScreen( Window frame, Panel panel )
    {
        super( frame, panel );
        this.setDoubleBuffered( true );
        this.setFocusable( true );
//        inGame = false;
        gameState = GameState.STARTING;
        input = new InputManager( this );
        this.newGame();
        // If you will draw your own mouse cursor or if you just want that mouse cursor disapear, 
        // insert "true" into if condition and mouse cursor will be removed.
//        if( false )
//        {
//            BufferedImage blankCursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
//            Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(blankCursorImg, new Point(0, 0), null);
//            this.setCursor(blankCursor);
//        }
        
        // Adds the keyboard listener to JPanel to receive key events from this component.
        this.addKeyListener( input );
        // Adds the mouse listener to JPanel to receive mouse events from this component.
        this.addMouseListener( input );
        
        JButton backButton = new JButton( "Back" );
        backButton.addActionListener( this );
//        backButton.setAlignmentX( RIGHT_ALIGNMENT ); // TODO
        this.add( backButton );
        Thread game = new Thread()
        {
            @Override
            public void run()
            {
                gameLoop();
            }
        };
        game.start();
    }
    
    @Override
    public void shown()
    {
        this.requestFocusInWindow();
//        window.addKeyListener( this );
        switch( gameState )
        {
            case STARTING:
                break;
            case VISUALIZING:
                this.restartGame();
                break;
            case PAUSED:
                gameState = GameState.PLAYING;
                break;
            case PLAYING:
                break;
            case RESUMED:
                break;
        }
    }
    
    @Override
    public void cover()
    {
        gameState = GameState.PAUSED;
        InputManager.reset();
    }
    
    public void updateGame()
    {
        long beginTime, timeTaken, timeLeft;
        beginTime = System.nanoTime();

        switch (gameState)
        {
            case STARTING:

                break;
            case PLAYING:
                gameTime += System.nanoTime() - lastTime;

                game.updateGame( gameTime, mousePosition() );

                lastTime = System.nanoTime();
                break;
            case PAUSED:
                break;
            case RESUMED:
                break;
            case VISUALIZING:
                break;
        }
        repaint();

        timeTaken = System.nanoTime() - beginTime;
        timeLeft = (GAME_UPDATE_PERIOD - timeTaken) / milisecInNanosec;
        if (timeLeft < 10) 
            timeLeft = 10; //set a minimum
        try {
            //Provides the necessary delay and also yields control so that other thread can do work.
            Thread.sleep(timeLeft);
        } catch (InterruptedException ex) { }
    }
    
    public void gameLoop()
    {

        // This two variables are used in VISUALIZING state of the game. We used them to wait some time so that we get correct frame/window resolution.
//        long visualizingTime = 0, lastVisualizingTime = System.nanoTime();
        
        // This variables are used for calculating the time that defines for how long we should put threat to sleep to meet the GAME_FPS.
        long beginTime, timeTaken, timeLeft;
        
        while(true)
        {
            beginTime = System.nanoTime();
            
            switch (gameState)
            {
                case STARTING:
                    
                    break;
                case PLAYING:
                    gameTime += System.nanoTime() - lastTime;
                    
                    game.updateGame( gameTime, mousePosition() );
                    
                    lastTime = System.nanoTime();
                break;
                case PAUSED:
                    //...
                break;
                case RESUMED:
                    //...
                break;
                case VISUALIZING:
//                    // On Ubuntu OS (when I tested on my old computer) this.getWidth() method doesn't return the correct value immediately (eg. for frame that should be 800px width, returns 0 than 790 and at last 798px). 
//                    // So we wait one second for the window/frame to be set to its correct size. Just in case we
//                    // also insert 'this.getWidth() > 1' condition in case when the window/frame size wasn't set in time,
//                    // so that we although get approximately size.
//                    if(this.getWidth() > 1 && visualizingTime > secInNanosec)
//                    {
//                        frameWidth = this.getWidth();
//                        frameHeight = this.getHeight();
//
//                        // When we get size of frame we change status.
//                        gameState = GameState.STARTING;
//                    }
//                    else
//                    {
//                        visualizingTime += System.nanoTime() - lastVisualizingTime;
//                        lastVisualizingTime = System.nanoTime();
//                    }
                break;
            }
            
            // Repaint the screen.
            repaint();
            
            // Here we calculate the time that defines for how long we should put threat to sleep to meet the GAME_FPS.
            timeTaken = System.nanoTime() - beginTime;
            timeLeft = (GAME_UPDATE_PERIOD - timeTaken) / milisecInNanosec; // In milliseconds
            // If the time is less than 10 milliseconds, then we will put thread to sleep for 10 millisecond so that some other thread can do some work.
            if (timeLeft < 10) 
                timeLeft = 10; //set a minimum
            try {
                 //Provides the necessary delay and also yields control so that other thread can do work.
                 Thread.sleep(timeLeft);
            } catch (InterruptedException ex) { }
        }
    }
    /**
     * Starts new game.
     */
    private void newGame()
    {
        // We set gameTime to zero and lastTime to current time for later calculations.
        gameTime = 0;
        lastTime = System.nanoTime();
        
        game = new Game( window );
    }
    
    /**
     *  Restart game - reset game time and call RestartGame() method of game object so that reset some variables.
     */
    private void restartGame()
    {
        // We set gameTime to zero and lastTime to current time for later calculations.
        gameTime = 0;
        lastTime = System.nanoTime();
        
        game.restartGame();
        
        // We change game status so that the game can start.
        gameState = GameState.PLAYING;
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
            window.switchTo( screen, Panel.LISTGAME );
        }
    }
       
    
}
