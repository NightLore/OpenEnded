package game;

import gui.GameScreen;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import sprites.Enemy;
import sprites.Player;
import sprites.PlayerGroup;
import sprites.Sprite;
import sprites.SpriteGroup;
import sprites.Weapon;
import world.Map;
import world.Tile;

/**
 * Game Manager class
 * 
 * @author Nathan Man-ho Lui
 * @version Oct 28, 2015
 * 
 * @author Sources: www.gametutorial.net for the framework, modified greatly
 */
public class Game {
//    private HashMap<String,SpriteData> data = new HashMap<String,SpriteData>();
//    private String[] choosable;
    public static final int SPAWN_OFFSET = 128;
    
    private SpriteGroup sprites;
    private PlayerGroup players;
    private GameScreen screen;
    private Map map;
    private BufferedImage enemyImg, projImg;
    private int numEnemies;
    private Weapon[] defaultWeapons;
    private boolean gameOver;
    private Point center;
    
    private Assets assets;
    private Settings settings;

    public Game( GameScreen screen, Settings settings, Assets assets )
    {
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
        sprites = new SpriteGroup();
        players = new PlayerGroup();
    }
    
    /**
     * Load game files - images, sounds, ...
     */
    private void loadContent()
    {
        enemyImg = assets.getSkin( Assets.REDCIRCLE );
        projImg = assets.getSkin( Assets.SMALLCIRCLE );
        Weapon w = new Weapon( projImg );
        defaultWeapons = new Weapon[2];
        defaultWeapons[0] = w;
        defaultWeapons[1] = w;
        map = new Map( assets, new Point(), screen.getWidth(), screen.getHeight() );
        map.create();
        map.generate();
        spawnPlayers();
        center = players.getCenter();
    }
    
    public void updateSettings()
    {
        Player.FRIENDLY_FIRE = settings.playerFriendlyFire;
        Enemy.FRIENDLY_FIRE = settings.enemyFriendlyFire;
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
            if ( shouldDespawn( s ) )
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
                    else settings.numPlayers--;
                    players.remove( s );
                }
                break;
            }
        }
        spawnEnemies();
    }
    private boolean shouldDespawn( Sprite s )
    {
        return !map.inMap( s ) || s.isDead();
    }
    private void spawnPlayers()
    {
        if ( gameOver ) return;
        while ( players.size() < settings.numPlayers )
        {
            Player player = new Player( assets.getSkin( Assets.GREYCIRCLE ), defaultWeapons );
            player.splitSprite( 2, 3 );
            setPlayerSpawn( player );
            player.setDefaultControls( players.size() );
            sprites.add( player );
            players.add( player );
        }
        while ( players.size() > settings.numPlayers )
        {
            sprites.remove( players.remove( players.size() - 1 ) );
        }
    }
    private void setPlayerSpawn( Sprite sprite )
    {
        boolean adjustX = true;
        int offset = Tile.TILE_SIZE;
        Point p = players.getCenter();
        if ( p == null ) p = new Point();
        do {
            sprite.setPosition( p.x, p.y );
            if ( adjustX ) // note: better replaced with array of "predetermined" locations
            {
                p.x += offset;
            }
            else
            {
                p.y += offset;
            }
            adjustX = !adjustX;
        } while ( isSpawnColliding( sprite ) );
    }
    private void spawnEnemies()
    {
        if ( numEnemies < settings.numEnemies )
        {
            sprites.add( newSprite( enemyImg, Enemy.class ) );
            numEnemies++;
        }
        for ( int i = 0; numEnemies > settings.numEnemies && i < sprites.size(); i++ )
        {
            if ( sprites.get( i ) instanceof Enemy )
            {
                sprites.remove( i );
                numEnemies--;
                break;
            }
        }
    }
    private Sprite newSprite( BufferedImage img, Class<? extends Sprite> c )
    {
        Sprite sprite = new Enemy( img, defaultWeapons );
        sprite.setRefPixel( sprite.getWidth() / 2, sprite.getHeight() / 2 );
        setSpriteSpawn( sprite );
        return sprite;
    }
    private void setSpriteSpawn( Sprite sprite )
    {
        List<Point> points = new ArrayList<Point>();
        Rectangle frame = map.getFrame();
        int offset = Tile.TILE_SIZE;
        int x1 = frame.x - offset;
        int y1 = frame.y - offset;
        int w = frame.width + offset;
        int h = frame.height + offset;
        int x2 = x1 + w + offset;
        int y2 = y1 + h + offset;
        for ( int i = 0; i < w; i++ )
        {
            points.add( new Point( i + x1, y1 ) );
            points.add( new Point( i+offset+x1, y2 ) );
        }
        for ( int i = 0; i < h; i++ )
        {
            points.add( new Point( x1, i+offset+y1 ) );
            points.add( new Point( x2, i+y1 ) );
        }
        Point p;
        do {
            p = points.remove( Map.randInt( points.size() ) );
            sprite.setPosition( p.x, p.y );
        } while ( isSpawnColliding( sprite ) );
    }
    
    private boolean isSpawnColliding( Sprite sprite )
    {
        return sprite.isColliding( sprites, false ) || map.isColliding( sprite ); // TODO note: may hit and lose hp, etc
    }
    
    public PlayerGroup getPlayers()
    {
        return players;
    }
    
    /**
     * Draw the game to the screen.
     * 
     * @param g2d Graphics2D
     * @param mousePosition current mouse position.
     */
    public void draw( Graphics2D g2d, Point mousePosition )// TODO note: drawing is not on same thread as updating
    {
        int originX = screen.getWidth() / 2 - center.x;
        int originY = screen.getHeight() / 2 - center.y;
        g2d.translate( originX, originY );
        map.draw( g2d, settings.debug );
        sprites.paintAll( g2d, map.getFrame(), settings.debug );
        if ( settings.debug )
        {
            g2d.setColor( new Color( 200, 100, 0 ) );
            for ( Sprite s : players )
            {
                Point sPoint = s.getPosition();
                int halfX = Math.min( center.x, sPoint.x ) + Math.abs( center.x - sPoint.x ) / 2;
                int halfY = Math.min( center.y, sPoint.y ) + Math.abs( center.y - sPoint.y ) / 2;
                g2d.drawLine( center.x, center.y, sPoint.x, sPoint.y );
                g2d.drawString( "" + (int)(s.distance( center ) * 100) / 100.0, halfX, halfY );
            }
        }
        g2d.translate( -originX, -originY );
        g2d.setColor( Color.WHITE );
        g2d.drawString( center.x + ", " + center.y, 0, screen.getHeight() - 5 );
    }
}
