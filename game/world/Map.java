package game.world;

import game.Assets;
import game.sprites.Sprite;
import game.world.Generator.Generation;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.HashMap;

/**
 *  Infinite map design
 *
 *  @author  Nathan Man-ho Lui
 *  @version Nov 8, 2015
 *  @author  Assignment: OpenEnded
 *
 *  @author  Sources: 
 */
public class Map extends TileCoordinator
{
    private static final int MAP_TILE_SIZE = 3;
    
    // ------------------------------- Class --------------------------- //
    private HashMap<Point,LargeTile> prevTiles;
    
    private Assets assets;
    private LargeTile[][] tiles;
    private Rectangle frame;
    
    public Map( Assets assets, Point center, int frameWidth, int frameHeight )
    {
        super( center.x, center.y, LargeTile.frameToTilePixelSize( Math.max( frameWidth, frameHeight ) ) ); // round size to nearest TILE_SIZE
        this.tiles = new LargeTile[MAP_TILE_SIZE][MAP_TILE_SIZE];
        frame = new Rectangle();
        frame.setSize( frameWidth, frameHeight );
        this.update( center );
        this.assets = assets;
    }
    
    /**
     * Creates this map. Removes all memory of the previous map
     */
    @Override
    public void create()
    {
        this.prevTiles = new HashMap<Point,LargeTile>();
        int size = this.getSize();
        for ( int i = 0; i < MAP_TILE_SIZE; i++ )
        {
            for ( int j = 0; j < MAP_TILE_SIZE; j++ )
            {
                int tileX = i * size - size;
                int tileY = j * size - size;
                tiles[i][j] = newLargeTile( tileX, tileY, size );
            }
        }
    }
    
    private LargeTile newLargeTile( int tileX, int tileY, int size )
    {
        LargeTile t = new LargeTile( tileX, tileY, size );
        t.changeBiome( assets.getFloor( Generator.randInt( Assets.NUMBIOMES ) ), 
                       assets.getBlock( Generator.randInt( Assets.NUMBIOMES ) ) );
        t.create();
        return t;
    }

    @Override
    public void generate()
    {
        int x = this.getX();
        int y = this.getY();
        int x1 = Generator.randInt( MAP_TILE_SIZE );
        int y1 = x1 == MAP_TILE_SIZE / 2?
            Generator.nextBoolean()?0:MAP_TILE_SIZE-1:
                Generator.randInt( MAP_TILE_SIZE );
        for ( int i = 0; i < MAP_TILE_SIZE; i++ )
        {
            for ( int j = 0; j < MAP_TILE_SIZE; j++ )
            {
                LargeTile t = tiles[i][j];
                // TODO generation formula/biome determination
                if ( i == MAP_TILE_SIZE / 2 && j == MAP_TILE_SIZE / 2 )
                {
                    t.generate( Generation.DEFAULT, 
                                Tile.toTileSize( x ), 
                                Tile.toTileSize( y ), 5, 5 );
                }
                else if ( i == x1 && j == y1 )
                     t.generate( Generation.DEFAULT );
                else t.generate();
            }
        }
    }

    @Override
    public void load()
    {
        // TODO Auto-generated method stub
        
    }
    
    public void draw( Graphics2D g2d, boolean debug )
    {
        int x = this.getX();
        int y = this.getY();
        g2d.translate( x, y );
        for ( LargeTile[] row : tiles ) // TODO check efficiency (draw only if in screen)
        {
            for ( LargeTile t : row )
            {
                Rectangle bounds = getFrame();
                bounds.setLocation( toTileCoords( frame.getLocation() ) );
                if ( t.intersects( bounds ) )
                    t.draw( g2d, bounds, debug );
            }
        }
        g2d.translate( -x, -y );
        if ( debug )
            g2d.drawRect( frame.x, frame.y, frame.width, frame.height );
    }
    
    @Override
    public boolean isColliding( Sprite sprite )
    {
        Rectangle rect = sprite.getBounds();
        rect.setLocation( toTileCoords( rect.getLocation() ) );
        int size = MAP_TILE_SIZE;
        int[] tile = this.findTile( rect );
        int length = tile[0];
        for ( int i = 1; i - 1 < length; i++ )
        {
            int j = tile[i];
            Sprite s = Tile.newSprite( sprite.getImage(), sprite.getX(), sprite.getY(), true );
            s.setPosition( toTileCoordX( sprite.getX() ), toTileCoordY( sprite.getY()) );
            if ( tiles[j/size][j%size].isColliding( s ) )
            {
                ((Sprite)sprite).hitWall( 0 ); // TODO get Direction of wall
                return true;
            }
        }
        return false;
    }
    
    
    /**
     * Updates this map with the center of screen position and the width and 
     * height of the screen by shifting all the tiles over one.
     * <br><br>
     * May be better with ArrayList as it does the same thing.
     * 
     * @param centerX x-coordinate of center of screen
     * @param centerY y-coordinate of center of screen
     * @param screenW width of screen
     * @param screenH height of screen
     */
    public void update( Point center ) // TODO update
    {
        this.updateFrame( center );
        int dx = toTileCoordX( center.x );
        int dy = toTileCoordY( center.y );
        int size = this.getSize();
        int adjust = size;
        int distance = size;

        LargeTile temp;
        Point pTemp;
        Point p;
        if ( Math.abs( dx ) > distance ) // if change in x
        {
            if ( dx > 0 ) // if change in x is positive
            {
                for ( int j = 0; j < MAP_TILE_SIZE; j++ )
                {
                    int i = 0;
                    temp = tiles[i][j];
                    pTemp = temp.getPosition();
                    prevTiles.put( this.toThisCoords( pTemp ), temp );
                    for ( ; i < MAP_TILE_SIZE - 1; i++ )
                    {
                        p = tiles[i+1][j].getPosition();
                        tiles[i+1][j].setPosition( pTemp );
                        tiles[i][j] = tiles[i+1][j];
                        pTemp = p;
                    }
                    temp = prevTiles.get( this.toThisCoords( pTemp.x + size, pTemp.y ) );
                    if ( temp == null )
                    {
                        temp = newLargeTile( pTemp.x, pTemp.y, size );
                        temp.generate();
                    }
                    else temp.setPosition( pTemp );
                    tiles[i][j] = temp;
                }
                this.setX( getX() + adjust );
            }
            else // if ( dx < 0 ) // change in x is negative
            {
                for ( int j = 0; j < MAP_TILE_SIZE; j++ )
                {
                    int i = MAP_TILE_SIZE - 1;
                    temp = tiles[i][j];
                    pTemp = temp.getPosition();
                    prevTiles.put( this.toThisCoords( pTemp ), temp );
                    for ( ; i > 0; i-- )
                    {
                        p = tiles[i-1][j].getPosition();
                        tiles[i-1][j].setPosition( pTemp );
                        tiles[i][j] = tiles[i-1][j];
                        pTemp = p;
                    }
                    temp = prevTiles.get( this.toThisCoords( pTemp.x - size, pTemp.y ) );
                    if ( temp == null )
                    {
                        temp = newLargeTile( pTemp.x, pTemp.y, size );
                        temp.generate();
                    }
                    else temp.setPosition( pTemp );
                    tiles[i][j] = temp;
                }
                this.setX( getX() - adjust );
            }
        }
        if ( Math.abs( dy ) > distance )
        {
            if ( dy > 0 )
            {
                for ( int i = 0; i < MAP_TILE_SIZE; i++ )
                {
                    int j = 0;
                    temp = tiles[i][j];
                    pTemp = temp.getPosition();
                    prevTiles.put( this.toThisCoords( pTemp ), temp );
                    for ( ; j < MAP_TILE_SIZE - 1; j++ )
                    {
                        p = tiles[i][j+1].getPosition();
                        tiles[i][j+1].setPosition( pTemp );
                        tiles[i][j] = tiles[i][j+1];
                        pTemp = p;
                    }
                    temp = prevTiles.get( this.toThisCoords( pTemp.x, pTemp.y + size ) );
                    if ( temp == null )
                    {
                        temp = newLargeTile( pTemp.x, pTemp.y, size );
                        temp.generate();
                    }
                    else temp.setPosition( pTemp );
                    tiles[i][j] = temp;
                }
                this.setY( getY() + adjust );
            }
            else
            {
                for ( int i = 0; i < MAP_TILE_SIZE; i++ )
                {
                    int j = MAP_TILE_SIZE - 1;
                    temp = tiles[i][j];
                    pTemp = temp.getPosition();
                    prevTiles.put( this.toThisCoords( pTemp ), temp );
                    for ( ; j > 0; j-- )
                    {
                        p = tiles[i][j-1].getPosition();
                        tiles[i][j-1].setPosition( pTemp );
                        tiles[i][j] = tiles[i][j-1];
                        pTemp = p;
                    }
                    temp = prevTiles.get( this.toThisCoords( pTemp.x, pTemp.y - size ) );
                    if ( temp == null )
                    {
                        temp = newLargeTile( pTemp.x, pTemp.y, size );
                        temp.generate();
                    }
                    else temp.setPosition( pTemp );
                    tiles[i][j] = temp;
                }
                this.setY( getY() - adjust );
            }
        }
        
    }
//    private void shift( int x, int y, int dx, int dy )// TODO compress code for update?
//    {
//        LargeTile temp;
//        Point pTemp;
//        Point p;
//        for ( int j = x; j < MAP_TILE_SIZE; j+=dx )
//        {
//            int i = y;
//            temp = tiles[i][j];
//            pTemp = temp.getPosition();
//            for ( ; i < MAP_TILE_SIZE - 1; i+=dy )
//            {
//                p = tiles[i+1][j].getPosition();
//                tiles[i+1][j].setPosition( pTemp );
//                tiles[i][j] = tiles[i+1][j];
//                pTemp = p;
//            }
//            temp.setPosition( pTemp );
//            tiles[i][j] = temp;
//            temp.generate();
//        }
//    }
    
    public boolean inMap( Sprite sprite )
    {
        Rectangle rect = sprite.getBounds();
        rect.setLocation( toTileCoords( rect.getLocation() ) );
        int tile = findTile( rect )[0];
        return tile > 0;
    }
    public int[] findTile( Rectangle rect ) // TODO check
    {
        int[] tile = new int[7];
        for ( int i = 0; i < MAP_TILE_SIZE; i++ )
        {
            for ( int j = 0; j < MAP_TILE_SIZE; j++ )
            {
                if ( tiles[i][j].intersects( rect ) )
                {
                    tile[0]++;
                    tile[tile[0]] = i * MAP_TILE_SIZE + j;
                }
            }
        }
        return tile;
    }
    
    private void updateFrame( Point center )
    {
        updateFrame( center.x, center.y );
    }
    
    private void updateFrame( int x, int y )
    {
        frame.setLocation( x - frame.width / 2, y - frame.height / 2 );
    }
    
    public Rectangle getFrame()
    {
        return new Rectangle( frame );
    }
    
    public static int randInt( int bound )
    {
        return RAND.nextInt( bound );
    }
    
//    public static void main( String[] args )
//    {
//        int[][] array = { { 1, 2, 3 }, 
//                          { 4, 5, 6 }, 
//                          { 7, 8, 9 }, };
//        for ( int[] a : array )
//        {
//            System.out.println( Arrays.toString( a ) );
//        }
//        int temp;
//        for ( int i = 0; i < array.length; i++ )
//        {
//            int j = 0;
//            temp = array[i][j];
//            for ( ; j < array[i].length - 1; j++ )
//            {
////                array[i][j+1].setPosition( array[i][j].getPosition() );
//                array[i][j] = array[i][j+1];
//            }
////            temp.setPosition( array[i][j].getPosition() );
//            array[i][j] = temp;
////            temp.generate();
//        }
//        for ( int[] a : array )
//        {
//            System.out.println( Arrays.toString( a ) );
//        }
//    }
}
