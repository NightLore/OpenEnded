package map;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

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
                int tileX = i * Tile.TILE_SIZE - adjust;
                int tileY = j * Tile.TILE_SIZE - adjust;
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
        if ( floors == null || blocks == null )
            System.err.println( "Biomes not initialized" );
        boolean[][] map = Generator.generate( size );
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
        // TODO
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
    
    public int getSize()
    {
        return size * Tile.TILE_SIZE;
    }
    
    public void setPosition( Point p )
    {
        this.position = (Point)p.clone();
//        position.x = p.x;
//        position.y = p.y;
    }
    
    public Point getPosition()
    {
        return position;
    }
    
    public static int frameToTileSize( int frameSize )
    {
        return frameSize / Tile.TILE_SIZE + 1;
    }
    
    public static int frameToTilePixelSize( int frameSize )
    {
        return frameToTileSize( frameSize ) * Tile.TILE_SIZE;
    }
}
