package game;

import gui.GameScreen;
import gui.Window;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import map.Map;
import sprites.Player;
import sprites.Sprite;
import sprites.SpriteGroup;

/**
 * Actual game.
 * 
 * @author www.gametutorial.net
 */
public class Game {

    private List<Sprite> sprites;
    private SpriteGroup players;
    private Window window;
    private Map map;

    public Game( Window frame )
    {
        window = frame;
//        Framework.gameState = Framework.GameState.GAME_CONTENT_LOADING;
        Thread threadForInitGame = new Thread() {
            @Override
            public void run(){
                // Sets variables and objects for the game.
                initialize();
                // Load game files (images, sounds, ...)
                loadContent();
                System.out.println( "Game Loaded" );
                GameScreen.gameState = GameScreen.GameState.PLAYING;
            }
        };
        threadForInitGame.start();
    }
    
    
   /**
     * Set variables and objects for the game.
     */
    private void initialize()
    {
        sprites = new ArrayList<Sprite>();
        players = new SpriteGroup();
        map = new Map( window.getWidth(), window.getHeight() );
    }
    
    /**
     * Load game files - images, sounds, ...
     */
    private void loadContent()
    {
        Player player;
        player = new Player( "player.png" );
        player.splitSprite( 2, 3 );
        player.setRefPixel( player.getWidth(), player.getHeight() );
        player.setPosition( window.getWidth() / 2, window.getHeight() / 2 );
        sprites.add( player );
        players.add( player );
    }    
    
    
    /**
     * Restart game - reset some variables.
     */
    public void restartGame()
    {
        
    }
    
    
    /**
     * Update game logic.
     * 
     * @param gameTime gameTime of the game.
     * @param mousePosition current mouse position.
     */
    public void updateGame( long gameTime, Point mousePosition )
    {
        for ( Sprite s : sprites )
        {
            s.move( gameTime );
        }
    }
    
    /**
     * Draw the game to the screen.
     * 
     * @param g2d Graphics2D
     * @param mousePosition current mouse position.
     */
    public void draw( Graphics2D g2d, Point mousePosition )
    {
//        System.out.println( g2d.getTransform() + ";\n    " 
//                        + player.getX() + ", " + player.getY()
//                        + ( -player.getX() + window.getWidth() / 2 ) + ", "
//                        + ( -player.getY() + window.getHeight() / 2 ) );
        if ( GameScreen.gameState == GameScreen.GameState.PLAYING )
        {
            map.draw( g2d );
            
            Point pCenter = players.getCenter();
            int originX = window.getWidth() / 2 - pCenter.x;
            int originY = window.getHeight() / 2 - pCenter.y;
            g2d.translate( originX, originY );
            g2d.fillRect( 100, 100, 100, 100 );
//            System.out.println( "draw: " + sprites.size() );
            for( Sprite s : sprites )
            {
                // System.out.println( s );
                s.paint( g2d );
            }
            g2d.translate( -originX, -originY );
        }
    }
}
