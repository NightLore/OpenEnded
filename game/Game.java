package game;

import game.sprites.Enemy;
import game.sprites.Player;
import game.sprites.Sprite;
import game.sprites.groups.Inventory;
import game.sprites.groups.PlayerGroup;
import game.sprites.groups.SpriteGroup;
import game.sprites.weapons.MeleeWeapon;
import game.sprites.weapons.Mine;
import game.sprites.weapons.Projectile;
import game.sprites.weapons.Weapon;
import game.world.Map;
import game.world.Tile;
import gui.game.GameOverlay;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    /** temporary method of storing Weapon's index */
    public static final HashMap<String,Integer> WEAPON_INDEX = new HashMap<String,Integer>();
    public static final String MELEE = "MELEE";
    public static final String SPELL = "SPELL";
    public static final String MINES = "MINES";
    static {
        WEAPON_INDEX.put( MELEE, 0 );
        WEAPON_INDEX.put( SPELL, 1 );
        WEAPON_INDEX.put( MINES, 2 );
    }
    
    private SpriteGroup sprites;
    private PlayerGroup players;
    private GameOverlay overlay;
    private Map map;
    
    public Inventory defaultInventory;
    private Enemy defaultEnemy;
    private Player defaultPlayer;
    
    private int numEnemies;
    private Point center;
    private int numLives;
    private int numKilled;
    
    private Assets assets;
    private Settings settings;
    
    private long spawnTime;
    private int delay = 750; // milliseconds

    public Game( GameOverlay screen, Settings settings, Assets assets )
    {
        this.overlay = screen;
        this.settings = settings;
        this.assets = assets;
        // note: if too intensive use SwingWorker
        initialize(); // Sets variables and objects for the game.
    }
    
    
   /**
     * Set variables and objects for the game.
     */
    private void initialize()
    {
        sprites = new SpriteGroup();
        players = new PlayerGroup( 4 );
        numLives = settings.numLives + 1;
        createDefaults();
        center = new Point();
        map = new Map( assets, center, overlay.getWidth(), overlay.getHeight() );
        map.create();
        map.generate();
    }
    
    /**
     * Initializes the default inventory with all its necessary values
     */
    private void createDefaults()
    {
        BufferedImage projImg = assets.getSkin( Assets.SMALLCIRCLE );
        Weapon[] defaultWeapons = new Weapon[3];
        defaultWeapons[0] = new MeleeWeapon( assets.getSkin( Assets.SWORD ), MELEE, 1000 );
        defaultWeapons[1] = new Projectile( projImg, SPELL );
        defaultWeapons[2] = new Mine( projImg, MINES );
        
        defaultInventory = new Inventory();
        for ( Weapon w : defaultWeapons )
        {
            defaultInventory.addWeapon( w );
        }
        
        defaultEnemy = new Enemy( assets.getSkin( Assets.REDCIRCLE ) );
        defaultEnemy.setWeapons( defaultWeapons[0], defaultWeapons[1] );
        
        defaultPlayer = new Player( assets.getSkin( Assets.GREYCIRCLE ) );
        defaultPlayer.splitSprite( 2, 3 );
        defaultPlayer.setWeapons( defaultWeapons[0], defaultWeapons[1] );
    }
    
    /**
     * Syncs game settings with the SettingsScreen Settings.<br>
     * Currently only updates Player and Enemy Friendly Fire, numerical values 
     * already automatically update
     * 
     * <br><br> TODO this method is probably not needed
     */
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
     * @see gui.game.GameScreen#gameLoop
     */
    public void updateGame( long gameTime, Point mousePosition )
    {
        Point pCenter = players.getCenter();
        if ( pCenter != null )
            center = pCenter;
        map.update( center );
        sprites.moveAll( gameTime, map );
        for ( Sprite s : sprites )
        {
            if ( shouldDespawn( s ) )
            {
                sprites.remove( s );
                if ( s instanceof Enemy )
                {
                    numEnemies--;
                    if ( s.isDead() )
                    {
                        numKilled++;
                        overlay.updateKilled( numKilled );
                    }
                }
                else if ( s instanceof Player )
                {
                    if ( players.numPlayers() == 1 && numLives <= 0 )
                    {
                        overlay.gameOver();
                    }
                    players.remove( (Player)s );
                }
                break;
            }
        }
        if ( gameTime > spawnTime + delay * 1000000L )
        {
            spawnEnemies();
            spawnTime = gameTime;
        }
    }
    private boolean shouldDespawn( Sprite s )
    {
        return !map.inMap( s ) || s.isDead();
    }
    public Player addPlayer( int panel )
    {
        if ( numLives <= 0 )
            return null;
        Player player = newPlayer();
        player.setDefaultControls( panel );
        sprites.add( player );
        players.set( player, panel );
        takeALife();
        return player;
    }
    private Player newPlayer()
    {
        Player player = defaultPlayer.clone();
        setPlayerSpawn( player );
        return player;
    }
    private void setPlayerSpawn( Sprite sprite )
    {
        boolean adjustX = true;
        int offset = Tile.TILE_SIZE;
        Point p = players.getCenter();
        if ( p == null ) p = center;
        sprite.setPosition( p.x, p.y );
        while ( isSpawnColliding( sprite ) )
        {
            if ( adjustX ) // note: better replaced with array of "predetermined" locations
            {
                p.x += offset;
            }
            else
            {
                p.y += offset;
            }
            adjustX = !adjustX;
            sprite.setPosition( p.x, p.y );
        }
    }
    private void spawnEnemies()
    {
        if ( numEnemies < settings.numEnemies )
        {
            sprites.add( newEnemy() );
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
    private Sprite newEnemy()
    {
        Enemy sprite = defaultEnemy.clone();
        setEnemySpawn( sprite );
        return sprite;
    }
    /** Set given Sprite to a randomspot around the map, also checks whether 
     * it collides with the map or another sprite. */
    private void setEnemySpawn( Sprite sprite )
    {
        List<Point> points = new ArrayList<Point>();
        Rectangle frame = map.getFrame();
        // set offset to the tile size
        int offset = Tile.TILE_SIZE;
        // coordinates for top left corner of frame (remain offscreen)
        int x1 = frame.x - offset;
        int y1 = frame.y - offset;
        int w = frame.width + offset;
        int h = frame.height + offset;
        // coordinates for bottom right corner of frame (remain offscreen)
        int x2 = x1 + w + offset;
        int y2 = y1 + h + offset;
        // add all points around the frame to a list (corners are only added once using a spiral method)
        for ( int i = 0; i < w; i++ )
        {
            points.add( new Point( i + x1, y1 ) ); // add top part of frame
            points.add( new Point( i+offset+x1, y2 ) ); // add bottom of frame
        }
        for ( int i = 0; i < h; i++ )
        {
            points.add( new Point( x1, i+offset+y1 ) ); // add left of frame
            points.add( new Point( x2, i+y1 ) ); // add right of frame
        }
        // remove random points from list to spawn in so that points are not repeated
        Point p;
        do {
            p = points.remove( Map.randInt( points.size() ) );
            sprite.setPosition( p.x, p.y );
        } while ( isSpawnColliding( sprite ) ); // check collisions
    }
    
    private boolean isSpawnColliding( Sprite sprite )
    {
        return sprite.isColliding( sprites ) || map.isColliding( sprite ); // TODO note: may hit and lose hp, etc
    }
    
    public PlayerGroup getPlayers()
    {
        return players;
    }
    
    public int numLives()
    {
        return numLives;
    }
    
    public int numKilled()
    {
        return numKilled;
    }
    
    private void takeALife()
    {
        numLives--;
        overlay.updateLives( numLives );
    }
    
    public Assets getAssets()
    {
        return assets;
    }
    
    /**
     * Draw the game to the screen.
     * 
     * @param g2d Graphics2D
     * @param mousePosition current mouse position.
     */
    public void draw( Graphics2D g2d, Point mousePosition )// TODO note: drawing is not on same thread as updating
    {
        int originX = overlay.getWidth() / 2 - center.x;
        int originY = overlay.getHeight() / 2 - center.y;
        g2d.translate( originX, originY );
        map.draw( g2d, settings.debug );
        sprites.paintAll( g2d, map.getFrame(), settings.debug );
        if ( settings.debug )
        {
            g2d.setColor( new Color( 200, 100, 0 ) );
            for ( Sprite s : players.toArray() )
            {
                if ( s == null ) continue;
                Point sPoint = s.getPosition();
                int halfX = Math.min( center.x, sPoint.x ) + Math.abs( center.x - sPoint.x ) / 2;
                int halfY = Math.min( center.y, sPoint.y ) + Math.abs( center.y - sPoint.y ) / 2;
                g2d.drawLine( center.x, center.y, sPoint.x, sPoint.y );
                g2d.drawString( "" + (int)(s.distance( center ) * 100) / 100.0, halfX, halfY );
            }
        }
        g2d.translate( -originX, -originY );
        g2d.setColor( Color.WHITE );
        g2d.drawString( center.x + ", " + center.y, 0, overlay.getHeight() - 5 );
    }
}
