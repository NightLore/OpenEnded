package map;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class LargeTile
{
    private BufferedImage[] floors;
    private BufferedImage[] blocks;
    private Tile[][] tiles;
    private int x;
    private int y;
    private int size;
    public LargeTile( int x, int y, int frameSize )
    {
        this.x = x;
        this.y = y;
        size = frameSize / Tile.TILE_SIZE;
        tiles = new Tile[size][size];
    }
    
    public void create()
    {
        for ( int i = 0; i < size; i++ )
        {
            for ( int j = 0; j < size; j++ )
            {
                int tileX = x + i * Tile.TILE_SIZE;
                int tileY = y + j * Tile.TILE_SIZE;
                tiles[i][j] = new Tile( tileX, tileY );
            }
        }
    }
    
    public void generate()
    {
        if ( floors == null || blocks == null )
            System.err.println( "Biomes not initialized" );
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
        for ( Tile[] row : tiles )
        {
            for ( Tile t : row )
            {
                t.draw( g2d );
            }
        }
    }
}
