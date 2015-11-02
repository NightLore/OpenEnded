package map;

import java.awt.image.BufferedImage;

import sprites.Sprite;

public class Tile extends Sprite
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public Tile( BufferedImage image, int x, int y )
    {
        super( image );
        this.setRefPixel( image.getWidth(), image.getHeight() );
        this.setPosition( x, y );
    }
}
