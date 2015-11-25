package world;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import sprites.ImageSprite;
import world.Generator.Generation;

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
        this.size = frameToTileSize( frameSize );
        this.tiles = new Tile[size][size];
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
     * @see world.Generator
     */
    public void generate()
    {
        generate( Generation.randomType() ); // TODO generating
    }
    public void generate( Generation g )
    {
        if ( floors == null || blocks == null ) // TODO remove check when coding finished?
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
    
    public void draw( Graphics2D g2d, Rectangle frame, boolean debug )
    {
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
//                    System.out.println( "DrawCompare:"+r+";"+frame);
                    if ( r.intersects( frame ) )
                        t.draw( g2d );
                    g2d.drawString( "(" + i + "," + j + ")", r.x + 5, r.y + r.height - 5 );
                }
            }
            g2d.translate( -x, -y );
            int rad = 64;
            int x = this.x - rad / 2;
            int y = this.y - rad / 2;
            Rectangle r = getBounds();
            g2d.drawOval( x, y, rad, rad );
            g2d.drawRect( r.x, r.y, r.width, r.height );
        }
        else
        {
            for ( Tile[] row : tiles )
            {
                for ( Tile t : row )
                {
                    if ( t.getBounds().intersects( frame ) )
                        t.draw( g2d );
                }
            }
            g2d.translate( -x, -y );
        }
    }
    
    public boolean isColliding( ImageSprite sprite )
    {
        sprite.setPosition( toTileX( sprite.getX() ), toTileY( sprite.getY() ) ); // TODO
        int x = Tile.toTileSize( sprite.getX() ) + size / 2 - 1;
        int y = Tile.toTileSize( sprite.getY() ) + size / 2 - 1;
        int checkSize = 3;
//        System.out.println( "Locating...(" + x + ", " + y + ")" );
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
        this.x = p.x;
        this.y = p.y;
    }
    
    public Point getPosition()
    {
        return new Point( x, y );
    }
    
    public Rectangle getBounds()
    {
        int pixelSize = this.getSize();
        return new Rectangle( x - pixelSize / 2 - Tile.TILE_SIZE / 2, 
                              y - pixelSize / 2 - Tile.TILE_SIZE / 2, 
                              pixelSize, pixelSize );
    }
    
    public Point toTileCoords( Point p )
    {
        return new Point( toTileX( p.x ), toTileY( p.y ) );
    }
    public int toTileX( int x )
    {
        return x - this.x;
    }
    public int toTileY( int y )
    {
        return y - this.y;
    }
    
    public static int frameToTileSize( int frameSize )
    {
        return Tile.toTileSize( frameSize );
    }
    public static int frameToTilePixelSize( int frameSize )
    {
        return Tile.toPixelSize( frameToTileSize( frameSize ) );
    }
    
    // --------------------------- Debug Code -------------------------- //
    
    @Override
    public String toString()
    {
        return "LargeTile["+x+","+y+";"+getSize()+"]";
    }
}
