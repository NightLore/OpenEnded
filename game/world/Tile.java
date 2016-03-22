package game.world;

import game.sprites.CollidableAdapter;
import game.sprites.Sprite;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 *  Represents a Tile on the map, manages wall collision
 *
 *  @author  Nathan Man-ho Lui
 *  @version Nov 1, 2015
 *  @author  Assignment: OpenEnded
 *
 *  @author  Sources: none
 */
public class Tile extends CollidableAdapter
{
    public static final int TILE_SIZE = 64;
    
    private TileSprite floor;
    private TileSprite block;
    
    private int x;
    private int y;
    public Tile( int x, int y )
    {
        this.setPosition( x, y );
        this.setCollidable( false );
    }
    
    /**
     * Draws this Tile
     * @param g2d
     */
    public void draw( Graphics2D g2d, boolean debug )
    {
        floor.paint( g2d, debug );
        if ( block != null )
            block.paint( g2d, debug );
    }
    
    public boolean isColliding( Sprite sprite )
    {
        return canCollide() && block.collidesWith( sprite );
    }
    
    public boolean intersects( Rectangle r )
    {
        return this.getBounds().intersects( r );
    }
    
    /**
     * Sets this Tiles Images
     * @param floor
     * @param block
     * @see game.world.Tile#setFloor(BufferedImage)
     * @see game.world.Tile#setBlock(BufferedImage)
     */
    public void setTileImages( BufferedImage floor, BufferedImage block )
    {
        setFloor( floor );
        setBlock( block );
        syncPosition();
    }
    
    /**
     * Sets the floor image to the specified image
     * @param floor
     */
    private void setFloor( BufferedImage floor )
    {
        this.floor = newSprite( floor, x, y );
    }
    
    /**
     * Sets the block image to the specified image and determines whether this
     * Tile is collidable based on whether parameter is null or not
     * @param block
     */
    private void setBlock( BufferedImage block )
    {
        boolean collidable = block != null;
        this.block = collidable ? newSprite( block, x, y ) : null;
        this.setCollidable( collidable );
    }
    
    /**
     * Sets this Tile's position
     * @param x
     * @param y
     */
    private void setPosition( int x, int y )
    {
        this.x = x;
        this.y = y;
        syncPosition();
    }
    
    private void syncPosition()
    {
        if ( floor != null )
        {
            this.floor.setPosition( x, y );
            if ( this.canCollide() )
            {
                this.block.setPosition( x, y );
            }
        }
    }
    
    public Point getPosition()
    {
        return new Point( x, y );
    }
    public int getX()
    {
        return x;
    }
    public int getY()
    {
        return y;
    }
    
    
    private static TileSprite newSprite( BufferedImage image, int x, int y )
    {
        return newSprite( image, x, y, false );
    }
    public static TileSprite newSprite( BufferedImage image, int x, int y, boolean half )
    {
        TileSprite sprite = new TileSprite( image );
        int refX = ( half ? image.getWidth() : TILE_SIZE ) / 2;
        int refY = ( half ? image.getHeight() : TILE_SIZE ) / 2;
        sprite.setRefPixel( refX, refY );
        sprite.setPosition( x, y );
        return sprite;
    }
    
    public static int toTileSize( int pixelSize )
    {
        return pixelSize / TILE_SIZE;
    }
    
    public static int toPixelSize( int tileSize )
    {
        return tileSize * TILE_SIZE;
    }
    
    //-------------------------- Debug Code -------------------------- //

    
    public Rectangle getBounds()
    {
        return floor.getBounds();
    }
    
    /**
     * return Tile[x,y;canCollide()]
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return "Tile[x="+x+",y="+y+";collide="+canCollide()+"]";
    }
}
