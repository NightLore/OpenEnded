package map;

import game.CollidableAdapter;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import sprites.ImageSprite;

public class Tile extends CollidableAdapter
{
    public static final int TILE_SIZE = 64;
    
    private ImageSprite floor;
    private ImageSprite block;
    
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
    public void draw( Graphics2D g2d )
    {
        floor.paint( g2d );
        block.paint( g2d );
    }
    
    /**
     * Sets this Tiles Images
     * @param floor
     * @param block
     * @see map.Tile#setFloor(BufferedImage)
     * @see map.Tile#setBlock(BufferedImage)
     */
    public void setTileImages( BufferedImage floor, BufferedImage block )
    {
        setFloor( floor );
        setBlock( block );
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
    public void setBlock( BufferedImage block )
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
    public void setPosition( int x, int y )
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
    
    private static ImageSprite newSprite( BufferedImage image, int x, int y )
    {
        ImageSprite sprite = new ImageSprite( image );
        sprite.setRefPixel( TILE_SIZE / 2, TILE_SIZE / 2 );
        sprite.setPosition( x, y );
        return sprite;
    }
}
