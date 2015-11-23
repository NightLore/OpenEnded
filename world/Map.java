package world;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.EnumMap;
import java.util.Scanner;

import javax.imageio.ImageIO;

import sprites.ImageSprite;
import world.Generator.Generation;

/**
 *  Infinite map design
 *
 *  @author  Nathan Man-ho Lui
 *  @version Nov 8, 2015
 *  @author  Assignment: OpenEnded
 *
 *  @author  Sources: 
 */
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
    private Rectangle frame;
    private int x;
    private int y;
    private int size;
    
    public Map( int x, int y, int frameWidth, int frameHeight )
    {
        this.tiles = new LargeTile[MAP_TILE_SIZE][MAP_TILE_SIZE];
        frame = new Rectangle( x - frameWidth / 2, y - frameHeight / 2, frameWidth, frameHeight );
        int frameSize = Math.max( frameWidth, frameHeight );
        int tileSize = LargeTile.frameToTilePixelSize( frameSize );
        this.size = tileSize;
        this.x = 0;
        this.y = 0;
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
                int tileX = i * size - size;
                int tileY = j * size - size;
                tiles[i][j] = new LargeTile( tileX, tileY, size );
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
            String[] fFiles = FLOORS.get( b );
            String[] bFiles = BLOCKS.get( b );
            int fLength = fFiles.length;
            int bLength = bFiles.length;
            BufferedImage[] floor = new BufferedImage[fLength];
            BufferedImage[] block = new BufferedImage[bLength];
            for ( int i = 0; i < fLength; i++ ) 
                floor[i] = toImage( "/imgs/" + fFiles[i] );// TODO image packages
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
                t.generate( Generation.DEFAULT );
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
    public void update( Point center ) // TODO update
    {
        frame.setLocation( center.x - frame.width / 2, center.y - frame.height / 2 );
        int dx = center.x - x;
        int dy = center.y - y;
        int adjust = size;
        int distance = size;

        LargeTile temp;
        Point pTemp;
        Point p;
        if ( Math.abs( dx ) > distance )
        {
            if ( dx > 0 )
            {
                for ( int j = 0; j < MAP_TILE_SIZE; j++ )
                {
                    int i = 0;
                    temp = tiles[i][j];
                    pTemp = temp.getPosition();
                    for ( ; i < MAP_TILE_SIZE - 1; i++ )
                    {
                        p = tiles[i+1][j].getPosition();
                        tiles[i+1][j].setPosition( pTemp );
                        tiles[i][j] = tiles[i+1][j];
                        pTemp = p;
                    }
                    temp.setPosition( pTemp );
                    tiles[i][j] = temp;
                    temp.generate();
                }
                x += adjust;
            }
            else
            {
                for ( int j = 0; j < MAP_TILE_SIZE; j++ )
                {
                    int i = MAP_TILE_SIZE - 1;
                    temp = tiles[i][j];
                    pTemp = temp.getPosition();
                    for ( ; i > 0; i-- )
                    {
                        p = tiles[i-1][j].getPosition();
                        tiles[i-1][j].setPosition( pTemp );
                        tiles[i][j] = tiles[i-1][j];
                        pTemp = p;
                    }
                    temp.setPosition( pTemp );
                    tiles[i][j] = temp;
                    temp.generate();
                }
                x -= adjust;
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
                    for ( ; j < MAP_TILE_SIZE - 1; j++ )
                    {
                        p = tiles[i][j+1].getPosition();
                        tiles[i][j+1].setPosition( pTemp );
                        tiles[i][j] = tiles[i][j+1];
                        pTemp = p;
                    }
                    temp.setPosition( pTemp );
                    tiles[i][j] = temp;
                    temp.generate();
                }
                y += adjust;
            }
            else
            {
                for ( int i = 0; i < MAP_TILE_SIZE; i++ )
                {
                    int j = MAP_TILE_SIZE - 1;
                    temp = tiles[i][j];
//                    System.out.println( i + ":temp=" + j + "," + tiles[i][j] );
                    pTemp = temp.getPosition();
//                    System.out.println( i + ":pTemp=" + pTemp );
                    for ( ; j > 0; j-- )
                    {
                        p = tiles[i][j-1].getPosition();
//                        System.out.println( i + ":p=" + p );
                        tiles[i][j-1].setPosition( pTemp );
//                        System.out.println( i + ":tile" + (j-1) + "p=" + pTemp );
//                        System.out.println( i + ":tile" + j + "," + tiles[i][j] + "=" + (j-1) + "," + tiles[i][j-1] );
                        tiles[i][j] = tiles[i][j-1];
                        pTemp = p;
//                        System.out.println( i + ":pTemp=" + pTemp );
                    }
                    temp.setPosition( pTemp );
//                    System.out.println( i + ":temp p=" + pTemp );
//                    System.out.println( i + ":" + j + "," + tiles[i][j] + "=" + temp + ",temp" );
                    tiles[i][j] = temp;
                    temp.generate();
                }
                y -= adjust;
            }
        }
    }
//    private void shift( int x, int y, int dx, int dy )// TODO compress code for update
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
    
    public void draw( Graphics2D g2d, boolean debug )
    {
        g2d.translate( x, y );
        for ( LargeTile[] row : tiles ) // TODO check efficiency (draw only if in screen)
        {
            for ( LargeTile t : row )
            {
                Rectangle bounds = new Rectangle( frame );
                bounds.setLocation( frame.x - x, frame.y - y );
                if ( bounds.intersects( t.getBounds() ) )
                    t.draw( g2d, bounds, debug );
            }
        }
        g2d.translate( -x, -y );
    }
    
    public boolean isColliding( ImageSprite sprite )
    {
        Rectangle rect = ImageSprite.getBounds( sprite );
        rect.setLocation( rect.x - x, rect.y - y );
        for ( LargeTile[] row : tiles )
        {
            for ( LargeTile t : row )
            {
                Rectangle r = t.getBounds();
                if ( r.intersects( rect ) )
                {
                    ImageSprite s = Tile.newSprite( sprite.getImage(), sprite.getX(), sprite.getY(), true );
                    s.setPosition( sprite.getX() - x, sprite.getY() - y );
                    if ( t.isColliding( s ) )
                        return true;
                }
            }
        }
        return false;
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
