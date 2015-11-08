package map;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.EnumMap;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class Map
{
    public enum Biome {
        PLAINS //, FOREST, SNOW, ROCKY, DESERT, 
        ;
        public static Biome randomType()
        {
            return biomes[Generator.randInt( NUMBIOMES )];
//            return biomes.get( Generator.randInt( NUMTYPES ) );
        } 
    }
//    public static final List<Biome> biomes = Collections.unmodifiableList(Arrays.asList(Biome.values()));
    private static final Biome[] biomes = Biome.values();
    private static final int NUMBIOMES = biomes.length;
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
    private int frameSize;
    private int x;
    private int y;
    private int tileSize;
    
    public Map( int frameWidth, int frameHeight )
    {
        this.tiles = new LargeTile[MAP_TILE_SIZE][MAP_TILE_SIZE];
        this.x = 0;
        this.y = 0;
        this.frameSize = Math.max( frameWidth, frameHeight );
        this.tileSize = LargeTile.frameToTilePixelSize( frameSize );
    }
    
    /**
     * Creates this map
     */
    public void create()
    {
        for ( int i = 0; i < MAP_TILE_SIZE; i++ )
        {
            for ( int j = 0; j < MAP_TILE_SIZE; j++ )
            {
                int tileX = i * frameSize - tileSize;
                int tileY = j * frameSize - tileSize;
                tiles[i][j] = new LargeTile( tileX, tileY, frameSize );
                tiles[i][j].changeBiome( floors.get( Biome.randomType() ), blocks.get( Biome.randomType() ) );
                tiles[i][j].create();
            }
        }
        
    }
    
    /**
     * Loads all biome images
     */
    public void loadAssets()
    {
        floors = new EnumMap<Biome,BufferedImage[]>( Biome.class );
        blocks = new EnumMap<Biome,BufferedImage[]>( Biome.class );
        for ( Biome b : biomes )
        {
//            System.out.println( b );
            String[] fFiles = FLOORS.get( b );
            String[] bFiles = BLOCKS.get( b );
            int fLength = fFiles.length;
            int bLength = bFiles.length;
            BufferedImage[] floor = new BufferedImage[fLength];
            BufferedImage[] block = new BufferedImage[bLength];
            for ( int i = 0; i < fLength; i++ ) 
                floor[i] = toImage( "/imgs/" + fFiles[i] );// TODO packages
            for ( int i = 0; i < bLength; i++ ) 
                block[i] = toImage( "/imgs/" + bFiles[i] );
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
    public void update( Point center )
    {
        double dx = center.x - x; // TODO if comes out negative
        double dy = center.y - y;

        LargeTile temp = tiles[0][0];
        if ( Math.abs( dx ) > tileSize )
        {
            if ( dx > 0 )
            {
                for ( int i = 0; i < MAP_TILE_SIZE; i++ )
                {
                    int j = 0;
                    temp = tiles[i][j];
                    for ( ; j < MAP_TILE_SIZE - 1; j++ ) // TODO set Positions of tiles
                    {
                        tiles[i][j] = tiles[i][j+1];
                    }
                    tiles[i][j] = temp;
//                    temp.generate();
                }
                
            }
            else
            {
                for ( int i = 0; i < MAP_TILE_SIZE; i++ )
                {
                    int j = MAP_TILE_SIZE - 1;
                    temp = tiles[i][j];
                    for ( ; j > 0; j++ )
                    {
                        tiles[i][j] = tiles[i][j-1];
                    }
                    tiles[i][j] = temp;
//                    temp.generate();
                }
            }
        }
        if ( Math.abs( dy ) > tileSize )
        {
            if ( dy > 0 )
            {
                
            }
            else
            {
                
            }
        }
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
//        URL url = Map.class.getResource( fileName );
//        System.out.println( url );
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
