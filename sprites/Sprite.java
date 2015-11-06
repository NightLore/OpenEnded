package sprites;

import game.Collidable;

import java.awt.image.BufferedImage;
import java.util.Scanner;

public class Sprite extends ImageSprite implements Collidable
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private boolean canCollide;
    public Sprite( String imgFile )
    {
        this( makeTransparent( toImage( "/imgs/" + imgFile ), java.awt.Color.WHITE ) );
    }

    public Sprite( BufferedImage img )
    {
        super( img );
        this.setCollidable( false );
    }
    
    public void move( long gameTime )
    {
        // TODO
    }
    
    public void translate( int dx, int dy)
    {
        this.setPosition( getX() + dx, getY() + dy );
    }
    
    public void translateX( int dx )
    {
        translate( dx, 0 );
    }
    
    public void translateY( int dy )
    {
        translate( 0, dy );
    }

    @Override
    public boolean canCollide()
    {
        return canCollide;
    }

    @Override
    public void setCollidable( boolean canCollide )
    {
        this.canCollide = canCollide;
    }
    
    /**
     * Return BufferedImage object of a picture file
     * @param fileName
     * @return buffered image
     */
    public static java.awt.image.BufferedImage toImage( String fileName )
    {
//        System.out.println( Sprite.class.getResource( fileName ) );
        try { 
            return javax.imageio.ImageIO.read( Sprite.class.getResource( fileName ) );
        } catch ( java.io.IOException e ) { 
            System.out.println( "Cannot find: " + fileName );
            e.printStackTrace(); 
            @SuppressWarnings("resource")
            Scanner scanIn = new Scanner( System.in );
            System.out.print( "Input file: " );
            return toImage( scanIn.nextLine() );
        }
        // return null;
    }

}
