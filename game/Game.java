package game;

import gui.GameScreen;
import gui.Window;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import sprites.Player;
import sprites.Sprite;
import sprites.SpriteGroup;
import world.Map;

/**
 * Actual game.
 * 
 * @author www.gametutorial.net for the framework
 * @author Nathan Man-ho Lui
 */
public class Game {

    private List<Sprite> sprites;
    private SpriteGroup players;
    private Window window;
    private Map map;
    private boolean debug;

    public Game( Window frame )
    {
        window = frame;
        setDebug( false );
//        Framework.gameState = Framework.GameState.GAME_CONTENT_LOADING;
        Thread threadForInitGame = new Thread() {
            @Override
            public void run(){
                initialize(); // Sets variables and objects for the game.
                loadContent(); // Load game files (images, sounds, ...)
                GameScreen.gameState = GameScreen.GameState.VISUALIZING;
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
    }
    
    /**
     * Load game files - images, sounds, ...
     */
    private void loadContent()
    {
        Player player;
        player = new Player( "player.png" );
        player.splitSprite( 2, 3 );
        player.setRefPixel( player.getWidth() / 2, player.getHeight() / 2 );
        player.setPosition( 0, 0 );
        sprites.add( player );
        players.add( player );
        Point p = players.getCenter();
        map = new Map( p.x, p.y, window.getWidth(), window.getHeight() );
        map.loadAssets();
        map.create();
        map.generate();
    }    
    
    
    /**
     * Restart game - reset some variables.
     */
    public void restartGame()
    {
        map.generate();
    }
    
    
    /**
     * Update game logic.
     * 
     * @param gameTime total time of game. // TODO see if need to change 
     * @param mousePosition current mouse position.
     * @see gui.GameScreen#gameLoop
     */
    public void updateGame( long gameTime, Point mousePosition )
    {
        map.update( players.getCenter() ); // TODO
        for ( Sprite s : sprites )
        {
            s.move( gameTime, map );
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
        if ( GameScreen.gameState == GameScreen.GameState.PLAYING )
        {
            
            Point pCenter = players.getCenter();
            int originX = window.getWidth() / 2 - pCenter.x;
            int originY = window.getHeight() / 2 - pCenter.y;
            g2d.translate( originX, originY );
            map.draw( g2d, debug );
            for( Sprite s : sprites )
            {
                s.paint( g2d );
            }
            g2d.translate( -originX, -originY );
            g2d.setColor( Color.WHITE );
            g2d.drawString( pCenter.x + ", " + pCenter.y, 0, window.getHeight() - 50 );
        }
    }
    
    public void setDebug( boolean debug )
    {
        this.debug = debug;
    }
}
