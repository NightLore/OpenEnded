package game.world;

import game.sprites.Sprite;
import game.world.Generator.Generation;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 *  Manages a chunk of the map
 *
 *  @author  Nathan Man-ho Lui
 *  @version Nov 1, 2015
 *  @author  Assignment: OpenEnded
 *
 *  @author  Sources: none
 */
public class LargeTile extends TileCoordinator
{
    private BufferedImage[] floors;
    private BufferedImage[] blocks;
    private Tile[][] tiles;
    private Generation.Properties properties;
    public LargeTile( int x, int y, int frameSize )
    {
        super( x, y, frameToTileSize( frameSize ) );
        int size = this.getSize();
        this.tiles = new Tile[size][size];
    }

    @Override
    public void create()
    {
        int size = this.getSize();
        int adjust = Tile.toPixelSize( size ) / 2;
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
     * @see game.world.Generator
     */
    @Override
    public void generate()
    {
        generate( Generation.randomType() ); // TODO generating
    }
    public void generate( Generation g, int x, int y, int w, int h )
    {
        if ( floors == null || blocks == null ) // TODO remove check when coding finished?
            System.err.println( "Biomes not initialized" );
        int size = this.getSize();
        properties = Generator.generate( g, size, x, y, w, h ); 
        boolean[][] map = properties.getMap();
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
    public void generate( Generation g )
    {
        generate( g, 0, 0, 0, 0);
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
    
    public void draw( Graphics2D g2d, Rectangle frame, boolean debug )
    {
        int x = this.getX();
        int y = this.getY();
        int size = this.getSize();
        g2d.translate( x, y );
        frame.setLocation( toTileCoords( frame.getLocation() ) );
        if ( debug )
        {
            g2d.setColor( java.awt.Color.WHITE );
            for ( int i = 0; i < size; i++ )
            {
                for ( int j = 0; j < size; j++ )
                {
                    Tile t = tiles[i][j];
                    Rectangle r = t.getBounds();
                    if ( r.intersects( frame ) )
                    {
                        t.draw( g2d, debug );
                        g2d.drawString( "(" + i + "," + j + ")", r.x + 5, r.y + r.height - 5 );
                    }
                }
            }
        }
        else
        {
            for ( Tile[] row : tiles )
            {
                for ( Tile t : row )
                {
                    if ( t.getBounds().intersects( frame ) )
                        t.draw( g2d, debug );
                }
            }
        }
        g2d.translate( -x, -y );
        if ( debug )
        {
            int rad = 64;
            int x1 = x - rad / 2;
            int y1 = y - rad / 2;
            Rectangle r = getBounds();
            g2d.drawOval( x1, y1, rad, rad );
            g2d.drawRect( r.x, r.y, r.width, r.height );
        }
    }
    
    @Override
    public boolean isColliding( Sprite sprite )
    {
        sprite.setPosition( toTileCoordX( sprite.getX() ), toTileCoordY( sprite.getY() ) ); // TODO
        int size = this.getSize();
        int x = Tile.toTileSize( sprite.getX() ) + size / 2 - 1;
        int y = Tile.toTileSize( sprite.getY() ) + size / 2 - 1;
        int checkSize = 3;
        for ( int i = 0; i < checkSize; i++ )
        {
            for ( int j = 0; j < checkSize; j++ )
            {
                int a = x+i;
                int b = y+j;
                if ( inTileBounds( a, b ) && tiles[a][b].isColliding( sprite ) )
                    return true;
            }
        }
        return false;
    }
    
    public Generation.Properties getProperties()
    {
        return properties;
    }
    
    public boolean intersects( Rectangle rect )
    {
        return this.getBounds().intersects( rect );
    }
    
    public Rectangle getBounds()
    {
        int pixelSize = Tile.toPixelSize( this.getSize() );
        Point p = getPosition();
        return new Rectangle( p.x - pixelSize / 2 - Tile.TILE_SIZE / 2, 
                              p.y - pixelSize / 2 - Tile.TILE_SIZE / 2, 
                              pixelSize, pixelSize );
    }
    
    public static int frameToTileSize( int frameSize )
    {
        return Tile.toTileSize( frameSize );
    }
    public static int frameToTilePixelSize( int frameSize )
    {
        return Tile.toPixelSize( frameToTileSize( frameSize ) );
    }
}
