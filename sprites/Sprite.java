package sprites;

import game.Collidable;

import java.awt.image.BufferedImage;
import java.util.Scanner;

import world.Map;

public abstract class Sprite extends ImageSprite implements Collidable
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    public static final int NORTH = 90;
    public static final int EAST = 0;
    public static final int SOUTH = 270;
    public static final int WEST = 180;
    
    private boolean canCollide;
    private boolean dead;
    private int dirFacing;
    public Sprite( String imgFile )
    {
        this( makeTransparent( toImage( "/imgs/" + imgFile ), java.awt.Color.WHITE ) );
        dead = false;
    }

    public Sprite( BufferedImage img )
    {
        super( img );
        this.setCollidable( false );
        dirFacing = SOUTH;
    }
    
    public void move( long gameTime, Map map, SpriteGroup<? extends Sprite> sprites )
    {
        double dir = getDirection( gameTime );
        if ( dir == -1 ) return;
        this.setDirFacing( (int)dir );
        dir = Math.toRadians( dir );
        int speed = getSpeed();
        int x = (int)Math.round( speed * Math.cos( dir ) ); // note: does not work because ints
        int y = (int)Math.round( speed * Math.sin( dir ) );
        translate( x, 0 );
        if ( map.isColliding( this ) || isColliding( sprites ) )
        {
            translate( -x, 0 );
        }
        translate( 0, y );
        if ( map.isColliding( this ) || isColliding( sprites ) )
        {
            translate( 0, -y );
        }
    }
    public boolean isColliding( SpriteGroup<? extends Sprite> sprites )
    {
        for ( Sprite s : sprites )
        {
            if ( s != this && this.collidesWith( s, false ) && additionalCollisions( s ) )
            {
                this.hitSprite( s );
                s.hitSprite( this );
                return true;
            }
        }
        return false;
    }
    /**
     * Return true if all additional collision conditions are satisfied. If true,
     * this sprite will collide with the sprite in the parameter
     * @param s Sprite that is visually colliding with this sprite
     * @return
     */
    public abstract boolean additionalCollisions( Sprite s );
    
    public void setDirFacing( int dir )
    {
        if ( dir < 0 ) return;
        else if ( dir < 45 || dir > 315 ) dirFacing = EAST;
        else if ( dir == 45 ) {
            if ( dirFacing != EAST && dirFacing != NORTH ) dirFacing = ( dirFacing == SOUTH ) ? EAST : NORTH;
        }
        else if ( dir < 135 ) dirFacing = NORTH;
        else if ( dir == 135 ) {
            if ( dirFacing != WEST && dirFacing != NORTH ) dirFacing = ( dirFacing == SOUTH ) ? WEST : NORTH;
        }
        else if ( dir < 225 ) dirFacing = WEST;
        else if ( dir == 225 ) {
            if ( dirFacing != WEST && dirFacing != SOUTH ) dirFacing = ( dirFacing == NORTH ) ? WEST : SOUTH;
        }
        else if ( dir < 315 ) dirFacing = SOUTH;
        else if ( dir == 315 ) {
            if ( dirFacing != EAST && dirFacing != SOUTH ) dirFacing = ( dirFacing == NORTH ) ? EAST : SOUTH;
        }
    }
    
    public int directionFacing()
    {
        return dirFacing;
    }
    
    /**
     * Returns the direction that this sprite should move or -1.0
     * if not moving.
     * @param gameTime the total time in the game
     * @return direction in degrees
     */
    public abstract double getDirection( long gameTime );
    
//    public abstract void hitWall( Rectangle wall );
    public abstract void hitSprite( Sprite sprite );
    
    public abstract void takeDamage( int damage );
    
    public abstract int getSpeed();
    
    public boolean isDead()
    {
        return dead; // TODO
    }
    public void dying()
    {
        dead = true;
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
    
    @Override
    public String toString()
    {
        return getClass().toString().substring( 6 ) + "["+getX()+","+getY()+"]";
    }

}
