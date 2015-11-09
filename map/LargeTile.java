package map;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import map.Generator.Generation;

public class LargeTile
{
    private BufferedImage[] floors;
    private BufferedImage[] blocks;
    private Tile[][] tiles;
    private Point position;
    private int size;
    public LargeTile( int x, int y, int frameSize )
    {
        this( new Point( x, y ), frameSize );
    }
    public LargeTile( Point p, int frameSize )
    {
        this.position = p;
        size = frameToTileSize( frameSize );
        tiles = new Tile[size][size];
    }
    
    public void create()
    {
        int adjust = getSize() / 2;
        for ( int i = 0; i < size; i++ )
        {
            for ( int j = 0; j < size; j++ )
            {
                int tileX = Tile.toPixelSize( i ) - adjust;
                int tileY = Tile.toPixelSize( j ) - adjust;
                tiles[i][j] = new Tile( tileX, tileY );
            }
        }
    }
    
    /**
     * Randomly Generates this LargeTile based on Generator
     * @see map.Generator
     */
    public void generate()
    {
        generate( Generation.randomType() );
    }
    public void generate( Generation g )
    {
        if ( floors == null || blocks == null )
            System.err.println( "Biomes not initialized" );
        boolean[][] map = Generator.generate( g, size );
        for ( int i = 0; i < size; i++ )
        {
            for ( int j = 0; j < size; j++ )
            {
                BufferedImage floor = floors[Generator.randInt( floors.length )];
                BufferedImage block = map[i][j]?null:blocks[Generator.randInt( blocks.length )];
                tiles[i][j].setTileImages( floor, block );
            }
        }
    }
    
    /**
     * NOT IMPLEMENTED
     * Intended to load a saved generation of map, for story purposes
     */
    public void load()
    {
        // TODO pre-load
    }
    
    /**
     * Changes this tile's biome generation based on parameters. Must call
     * generate() afterwards in order to create the new biome
     * @param floors
     * @param blocks
     */
    public void changeBiome( BufferedImage[] floors, BufferedImage[] blocks )
    {
        this.floors = floors;
        this.blocks = blocks;
    }
    
    public void draw( Graphics2D g2d )
    {
        g2d.translate( position.x, position.y );
        for ( Tile[] row : tiles )
        {
            for ( Tile t : row )
            {
                t.draw( g2d );
            }
        }
        g2d.translate( -position.x, -position.y );
    }
    
    public boolean isColliding( Rectangle rect )
    {
        int x = Tile.toTileSize( rect.x ) - 1;
        int y = Tile.toTileSize( rect.y ) - 1;
        for ( int i = 0; i < 3; i++ )
        {
            for ( int j = 0; j < 3; j++ )
            {
                if ( inTileBounds( x + i, y + j ) && tiles[x + i][y + j].isColliding( rect ) )
                    return true;
            }
        } // TODO collision
        return false;
    }
    private boolean inTileBounds( int x, int y )
    {
        return x >= 0 && x < size && y >= 0 && y < size;
    }
    
    public int getSize()
    {
        return Tile.toPixelSize( size );
    }
    
    public void setPosition( Point p )
    {
        this.position = p.getLocation();
//        position.x = p.x;
//        position.y = p.y;
    }
    
    public Point getPosition()
    {
        return position.getLocation();
    }
    
    public Rectangle getBounds()
    {
        int pixelSize = this.getSize();
        return new Rectangle( position.x - pixelSize / 2, 
                              position.y - pixelSize / 2, 
                              pixelSize, pixelSize );
    }
    
    public static int frameToTileSize( int frameSize )
    {
        return Tile.toTileSize( frameSize ) + 1;
    }
    
    public static int frameToTilePixelSize( int frameSize )
    {
        return Tile.toPixelSize( frameToTileSize( frameSize ) );
    }
}
