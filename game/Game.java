package game;

import gui.GameScreen;
import gui.Window;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import sprites.Enemy;
import sprites.Player;
import sprites.Sprite;
import sprites.SpriteGroup;
import sprites.Weapon;
import world.Map;

/**
 * Actual game.
 * 
 * @author www.gametutorial.net for the framework
 * @author Nathan Man-ho Lui
 */
public class Game {
//    private HashMap<String,SpriteData> data = new HashMap<String,SpriteData>();
//    private String[] choosable;
    private static final Assets ASSETS = new Assets();
    private static boolean hasLoaded = false;
    private synchronized static void loadAssets()
    {
        if ( !hasLoaded )
        {
            ASSETS.loadFiles();
            ASSETS.loadAssets();
            hasLoaded = true;
        }
    }
    
    private SpriteGroup<Sprite> sprites;
    private SpriteGroup<Player> players;
    private Window window;
    private Map map;
    private boolean debug;
    private BufferedImage enemyImg, projImg;
    private int numEnemies;
    private int maxEnemies;
    private Weapon[] defaultWeapons;

    public Game( Window frame )
    {
        window = frame;
        maxEnemies = 40;
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
        sprites = new SpriteGroup<Sprite>();
        players = new SpriteGroup<Player>();
        Game.loadAssets();
//        Map.loadFileNames();
//        Map.loadAssets();
    }
    
    /**
     * Load game files - images, sounds, ...
     */
    private void loadContent()
    {
        while ( !hasLoaded ); // hold until assets loaded
        enemyImg = ASSETS.getSkin( Assets.REDCIRCLE );
        projImg = ASSETS.getSkin( Assets.SMALLCIRCLE );
        Weapon w = new Weapon( projImg );
        w.setRefPixel( w.getWidth() / 2, w.getHeight() / 2 );
        defaultWeapons = new Weapon[2];
        defaultWeapons[0] = w;
        defaultWeapons[1] = w;
        Player player;
        player = new Player( ASSETS.getSkin( Assets.GREYCIRCLE ), defaultWeapons );
        player.splitSprite( 2, 3 );
        player.setRefPixel( player.getWidth() / 2, player.getHeight() / 2 );
        player.setPosition( 0, 0 );
        sprites.add( player );
        players.add( player );
        player = new Player( ASSETS.getSkin( Assets.GREYCIRCLE ), defaultWeapons );
        player.splitSprite( 2, 3 );
        player.setRefPixel( player.getWidth() / 2, player.getHeight() / 2 );
        player.setPosition( 100, 0 );
        player.setControls( KeyEvent.VK_UP, KeyEvent.VK_RIGHT, 
                KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_COMMA, KeyEvent.VK_PERIOD );
        sprites.add( player );
        players.add( player );
        Point p = players.getCenter();
        map = new Map( ASSETS, p.x, p.y, window.getWidth(), window.getHeight() );
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
     * @param gameTime total time of game.
     * @param mousePosition current mouse position.
     * @see gui.GameScreen#gameLoop
     */
    public void updateGame( long gameTime, Point mousePosition )
    {
        map.update( players.getCenter() ); // TODO
        sprites.moveAll( gameTime, map );
        for ( Sprite s : sprites )
        {
            if ( !map.inMap( s ) || s.isDead() )
            {
                sprites.remove( s );
                if ( s instanceof Enemy )
                    numEnemies--;
                else if ( s instanceof Player )
                    players.remove( s );
                break;
            }
        }
        spawnEnemies();
    }
    private void spawnEnemies()
    {
        while ( numEnemies < maxEnemies )
        {
            sprites.add( newSprite( enemyImg, Enemy.class ) );
            numEnemies++;
        }
    }
    private Sprite newSprite( BufferedImage img, Class<? extends Sprite> c )
    {
        Sprite sprite = new Enemy( img );
        sprite.setRefPixel( sprite.getWidth() / 2, sprite.getHeight() / 2 );
        setSpriteSpawn( sprite );
        return sprite;
    }
    private void setSpriteSpawn( Sprite sprite )
    {
        do {
            Point p = map.getSpawnableLocation();
            sprite.setPosition( p.x, p.y );
        } while ( sprite.isColliding( sprites ) );
    }
    
    /**
     * Draw the game to the screen.
     * 
     * @param g2d Graphics2D
     * @param mousePosition current mouse position.
     */
    public void draw( Graphics2D g2d, Point mousePosition )// TODO note: drawing is not on same thread as updating
    {
        if ( GameScreen.gameState == GameScreen.GameState.PLAYING )
        {
            Point pCenter = players.getCenter();
            int originX = window.getWidth() / 2 - pCenter.x;
            int originY = window.getHeight() / 2 - pCenter.y;
            g2d.translate( originX, originY );
            map.draw( g2d, debug );
            sprites.paintAll( g2d );
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
