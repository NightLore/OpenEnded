package map;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class Map
{
    public enum Biome {
        PLAINS, // FOREST, SNOW, ROCKY, DESERT,  
    }
    public static final Biome[] biomes = Biome.values();
    public static final int MAP_TILE_SIZE = 3;
    
//    private static final String[][] FLOORS = { { "background.png" } };
//    private static final String[][] BLOCKS = { { "DarkGreen.png" } };
    private static EnumMap<Biome, String[]> FLOORS;
    private static EnumMap<Biome, String[]> BLOCKS;
    {
        FLOORS = new EnumMap<Biome, String[]>( Biome.class );
        BLOCKS = new EnumMap<Biome, String[]>( Biome.class );
        FLOORS.put( Biome.PLAINS, new String[]{ "background.png" } );
        BLOCKS.put( Biome.PLAINS, new String[]{ "DarkGreen.png" } );
    }
    
    private EnumMap<Biome, BufferedImage[]> floors;
    private EnumMap<Biome, BufferedImage[]> blocks;
    
    private LargeTile[][] tiles;
    private int width;
    private int height;
    private int x;
    private int y;
    
    public Map( int frameWidth, int frameHeight )
    {
        tiles = new LargeTile[MAP_TILE_SIZE][MAP_TILE_SIZE];
        width = frameWidth;
        height = frameHeight;
    }
    
    /**
     * Creates this map
     */
    public void create()
    {
        floors = new EnumMap<Biome,BufferedImage[]>( Biome.class );
        blocks = new EnumMap<Biome,BufferedImage[]>( Biome.class );
        // TODO initialize images
        int frameSize = Math.max( width, height );
        for ( int i = 0; i < MAP_TILE_SIZE; i++ )
        {
            for ( int j = 0; j < MAP_TILE_SIZE; j++ )
            {
                int tileX = i * frameSize;
                int tileY = j * frameSize;
                tiles[i][j] = new LargeTile( tileX, tileY, frameSize );
            }
        }
        
        for ( Biome b : biomes )
        {
            String[] fFiles = FLOORS.get( b );
            String[] bFiles = BLOCKS.get( b );
            int fLength = fFiles.length;
            int bLength = bFiles.length;
            BufferedImage[] floor = new BufferedImage[fLength];
            BufferedImage[] block = new BufferedImage[bLength];
            for ( int i = 0; i < fLength; i++ ) 
                floor[i] = toImage( fFiles[i] );
            for ( int i = 0; i < bLength; i++ ) 
                block[i] = toImage( bFiles[i] );
            floors.put( b, floor );
            blocks.put( b, block );
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
    public void update( int centerX, int centerY )
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
    
    /**
     * Return BufferedImage object of a picture file
     * @param fileName
     * @return buffered image
     */
    public static BufferedImage toImage( String fileName )
    {
        try { 
            return ImageIO.read( Map.class.getResource( fileName ) );
        } catch ( java.io.IOException e ) { 
            System.out.println( "Cannot find: " + fileName );
            e.printStackTrace(); 
            @SuppressWarnings("resource")
            Scanner scanIn = new Scanner( System.in );
            System.out.print( "Input file: " );
            return toImage( scanIn.nextLine() );
        }
    }
}
