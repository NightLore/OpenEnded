package map;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Map
{
    public enum Biome {
        PLAINS, // FOREST, SNOW, ROCKY, DESERT,  
    }
    public static final Biome[] biomes = Biome.values();
    public static final int MAP_TILE_SIZE = 3;
    
    private BufferedImage[][] floors;
    private BufferedImage[][] blocks;
    
    private LargeTile[][] tiles;
    private int width;
    private int height;
    
    public Map( int frameWidth, int frameHeight )
    {
        tiles = new LargeTile[MAP_TILE_SIZE][MAP_TILE_SIZE];
        width = frameWidth;
        height = frameHeight;
    }
    
    public void create( int x, int y )
    {
        floors = new BufferedImage[biomes.length][];
        blocks = new BufferedImage[biomes.length][];
        // TODO initialize images
        int frameSize = Math.max( width, height );
        for ( int i = 0; i < MAP_TILE_SIZE; i++ )
        {
            for ( int j = 0; j < MAP_TILE_SIZE; j++ )
            {
                int tileX = x + i * frameSize;
                int tileY = y + j * frameSize;
                tiles[i][j] = new LargeTile( tileX, tileY, frameSize );
            }
        }
    }
    
    public void generate()
    {
        for ( LargeTile[] row : tiles )
        {
            for ( LargeTile t : row )
            {
                // TODO generation formula/biome determination
                t.generate();
            }
        }
    }
    
    
    /**
     * Updates this map with the center of screen position and the width and 
     * height of the screen
     * @param centerX x-coordinate of center of screen
     * @param centerY y-coordinate of center of screen
     * @param screenW width of screen
     * @param screenH height of screen
     */
    public void update( int centerX, int centerY, int screenW, int screenH )
    {
        // TODO updating formula
    }
    
    public void draw( Graphics2D g2d )
    {
        for ( LargeTile[] row : tiles )
        {
            for ( LargeTile t : row )
            {
                t.draw( g2d );
            }
        }
    }
}
