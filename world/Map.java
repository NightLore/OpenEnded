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
public class Map extends TileCoordinator
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
    
    private static EnumMap<Biome, String[]> FLOORS;
    private static EnumMap<Biome, String[]> BLOCKS;
    public static void loadFileNames()
    {
        FLOORS = new EnumMap<Biome, String[]>( Biome.class );
        BLOCKS = new EnumMap<Biome, String[]>( Biome.class );
        FLOORS.put( Biome.PLAINS, new String[]{ "background.png" } );
        BLOCKS.put( Biome.PLAINS, new String[]{ "DarkGreen.png" } );
    }
    
    private static EnumMap<Biome, BufferedImage[]> floors;
    private static EnumMap<Biome, BufferedImage[]> blocks;
    
    /**
     * Loads all biome images
     */
    public static void loadAssets()
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
    
    // ------------------------------- Class --------------------------- //
    
    private LargeTile[][] tiles;
    private Rectangle frame;
    
    public Map( int x, int y, int frameWidth, int frameHeight )
    {
        super( x, y, LargeTile.frameToTilePixelSize( Math.max( frameWidth, frameHeight ) ) );
        this.tiles = new LargeTile[MAP_TILE_SIZE][MAP_TILE_SIZE];
        int tSize = Tile.TILE_SIZE;
        frame = new Rectangle( x - frameWidth / 2 - tSize, 
                               y - frameHeight / 2 - tSize, 
                               frameWidth + tSize, 
                               frameHeight + tSize );
    }
    
    /**
     * Creates this map
     */
    @Override
    public void create()
    {
        int size = this.getSize();
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
                Rectangle bounds = new Rectangle( frame );
                bounds.setLocation( toTileCoords( frame.getLocation() ) );
                if ( bounds.intersects( t.getBounds() ) )
                    t.draw( g2d, bounds, debug );
            }
        }
        g2d.translate( -x, -y );
    }
    
    @Override
    public boolean isColliding( ImageSprite sprite )
    {
        Rectangle rect = ImageSprite.getBounds( sprite );
        rect.setLocation( toTileCoords( rect.getLocation() ) );
        for ( LargeTile[] row : tiles )
        {
            for ( LargeTile t : row )
            {
                Rectangle r = t.getBounds();
                if ( r.intersects( rect ) )
                {
                    ImageSprite s = Tile.newSprite( sprite.getImage(), sprite.getX(), sprite.getY(), true );
                    s.setPosition( toTileCoordX( sprite.getX() ), toTileCoordY( sprite.getY()) ); // TODO
                    if ( t.isColliding( s ) )
                        return true;
                }
            }
        }
        return false;
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
        int dx = toTileCoordX( center.x );
        int dy = toTileCoordY( center.y );
        int size = this.getSize();
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
                this.setX( getX() + adjust );
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
                this.setY( getY() + adjust );
            }
            else
            {
                for ( int i = 0; i < MAP_TILE_SIZE; i++ )
                {
                    int j = MAP_TILE_SIZE - 1;
                    temp = tiles[i][j];
                    pTemp = temp.getPosition();
                    for ( ; j > 0; j-- )
                    {
                        p = tiles[i][j-1].getPosition();
                        tiles[i][j-1].setPosition( pTemp );
                        tiles[i][j] = tiles[i][j-1];
                        pTemp = p;
                    }
                    temp.setPosition( pTemp );
                    tiles[i][j] = temp;
                    temp.generate();
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
    public Point getSpawnableLocation()
    {
        int loc = Generator.randInt( 8 );
        if ( loc >= 4 ) loc++;
        return this.toThisCoords( tiles[loc/MAP_TILE_SIZE][loc%MAP_TILE_SIZE].getSpawnableLocation() );
    }
    public boolean inMap( ImageSprite sprite )
    {
        Rectangle rect = ImageSprite.getBounds( sprite );
        rect.setLocation( toTileCoords( rect.getLocation() ) );
        int tile = findTile( rect )[0];
        System.out.println( "found " + sprite + " in " + tile );
        return tile > 0;
    }
    public int[] findTile( Rectangle rect ) // TODO check
    {
        int[] tile = new int[5];
        for ( int i = 0; i < MAP_TILE_SIZE; i++ )
        {
            for ( int j = 0; j < MAP_TILE_SIZE; j++ )
            {
                Rectangle r = tiles[i][j].getBounds();
                if ( r.intersects( rect ) )
                {
                    tile[0]++;
                    tile[tile[0]] = i * MAP_TILE_SIZE + j;
                }
            }
        }
        return tile;
    }
    
    /**
     * Return BufferedImage object of a picture file, starts at this project's path
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
