package world;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Random;

import sprites.Sprite;

/**
 *  Holds all manageable similar code between LargeTile and Map
 *
 *  @author  Nathan Man-ho Lui
 *  @version Nov 27, 2015
 *  @author  Assignment: OpenEnded
 *
 *  @author  Sources: none
 */
public abstract class TileCoordinator
{
    protected static final Random RAND = new Random();
    
    private int x;
    private int y;
    private int size;
    
    public TileCoordinator( int x, int y, int size )
    {
        this.x = x;
        this.y = y;
        this.size = size;
    }
    
    public abstract void create();
    public abstract boolean isColliding( Sprite sprite );
    /**
     * Randomly Generates this Coordinator based on Generator
     * @see world.Generator
     */
    public abstract void generate();
    public abstract void load();

    public boolean inTileBounds( int x, int y )
    {
        return x >= 0 && x < size && y >= 0 && y < size;
    }
    
    public int getSize()
    {
        return size;
    }
    
    public void setPosition( Point p )
    {
        this.x = p.x;
        this.y = p.y;
    }
    public void setX( int x )
    {
        this.x = x;
    }
    public void setY( int y )
    {
        this.y = y;
    }
    
    public Point getPosition()
    {
        return new Point( x, y );
    }
    public int getX()
    {
        return x;
    }
    public int getY()
    {
        return y;
    }
    
    public Rectangle getBounds()
    {
        int pixelSize = this.getSize();
        return new Rectangle( x - pixelSize / 2 - Tile.TILE_SIZE / 2, 
                              y - pixelSize / 2 - Tile.TILE_SIZE / 2, 
                              pixelSize, pixelSize );
    }

    public Point toThisCoords( Point p )
    {
        return toThisCoords( p.x, p.y );
    }
    public Point toThisCoords( int x, int y )
    {
        return new Point( toThisCoordX( x ), toThisCoordY( y ) );
    }
    public int toThisCoordX( int x ) { return x + this.x; }
    public int toThisCoordY( int y ) { return y + this.y; }

    public Point toTileCoords( Point p )
    {
        return toTileCoords( p.x, p.y );
    }
    public Point toTileCoords( int x, int y )
    {
        return new Point( toTileCoordX( x ), toTileCoordY( y ) );
    }
    public int toTileCoordX( int x ) { return x - this.x; }
    public int toTileCoordY( int y ) { return y - this.y; }
    
    @Override
    public String toString()
    {
        return getClass().toString().substring( 6 ) + "[" + x + "," + y + ";" + size + "]";
    }
}
