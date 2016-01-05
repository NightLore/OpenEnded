package game;

import gui.Cards;
import gui.GameScreen;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
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
    public static final int SPAWN_OFFSET = 128;
    
    private SpriteGroup<Sprite> sprites;
    private SpriteGroup<Player> players;
    private Cards window;
    private GameScreen screen;
    private Map map;
    private BufferedImage enemyImg, projImg;
    private int numEnemies;
    private Weapon[] defaultWeapons;
    private boolean gameOver;
    private Point center;
    
    private Assets assets;
    private Settings settings;

    public Game( Cards frame, GameScreen screen, Settings settings, Assets assets )
    {
        this.window = frame;
        this.screen = screen;
        this.settings = settings;
        this.assets = assets;
        // note: if too intensive use SwingWorker
        initialize(); // Sets variables and objects for the game.
        loadContent(); // Load game files (images, sounds, ...)
    }
    
    
   /**
     * Set variables and objects for the game.
     */
    private void initialize()
    {
        sprites = new SpriteGroup<Sprite>();
        players = new SpriteGroup<Player>();
    }
    
    /**
     * Load game files - images, sounds, ...
     */
    private void loadContent()
    {
        enemyImg = assets.getSkin( Assets.REDCIRCLE );
        projImg = assets.getSkin( Assets.SMALLCIRCLE );
        Weapon w = new Weapon( projImg );
        w.setRefPixel( w.getWidth() / 2, w.getHeight() / 2 );
        defaultWeapons = new Weapon[2];
        defaultWeapons[0] = w;
        defaultWeapons[1] = w;
        spawnPlayers();
        center = players.getCenter();
        map = new Map( assets, center.x, center.y, window.getWidth(), window.getHeight() );
        map.create();
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
        spawnPlayers();
        Point pCenter = players.getCenter();
        if ( pCenter != null )
            center = pCenter;
        map.update( center ); // TODO
        sprites.moveAll( gameTime, map );
        for ( Sprite s : sprites )
        {
            if ( !map.inMap( s ) || s.isDead() )
            {
                sprites.remove( s );
                if ( s instanceof Enemy )
                    numEnemies--;
                else if ( s instanceof Player )
                {
                    if ( players.size() == 1 )
                    {
                        screen.gameOver();
                        gameOver = true;
                    }
                    players.remove( s );
                }
                break;
            }
        }
        spawnEnemies();
    }
    private void spawnPlayers()
    {
        if ( gameOver ) return;
        while ( players.size() < settings.numPlayers )
        {
            Point p = players.getCenter();
            if ( p == null ) p = new Point();
            Player player = new Player( assets.getSkin( Assets.GREYCIRCLE ), defaultWeapons );
            player.splitSprite( 2, 3 );
            player.setRefPixel( player.getWidth() / 2, player.getHeight() / 2 );
            player.setPosition( p.x + SPAWN_OFFSET, p.y + SPAWN_OFFSET );
            player.setDefaultControls( players.size() );
            sprites.add( player );
            players.add( player );
        }
        while ( players.size() > settings.numPlayers )
        {
            sprites.remove( players.remove( players.size() - 1 ) );
        }
    }
    private void spawnEnemies()
    {
        while ( numEnemies < settings.numEnemies )
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
        int originX = window.getWidth() / 2 - center.x;
        int originY = window.getHeight() / 2 - center.y;
        g2d.translate( originX, originY );
        map.draw( g2d, settings.debug );
        sprites.paintAll( g2d );
        g2d.translate( -originX, -originY );
        g2d.setColor( Color.WHITE );
        g2d.drawString( center.x + ", " + center.y, 0, window.getHeight() - 50 );
    }
}
