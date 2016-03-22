package game.sprites;

import game.sprites.groups.SpriteGroup;
import game.world.Map;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Scanner;

/**
 *  Abstract class for all sprites, also used as a modifier for the ImageSprite 
 *  class
 *
 *  @author  Nathan Man-ho Lui
 *  @version Nov 1, 2015
 *  @author  Assignment: OpenEnded
 *
 *  @author  Sources: none
 */
public abstract class Sprite extends ImageSprite implements Collidable
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    public static boolean PIXEL_PERFECT = false;
    
    // NOTE: going back to enums may be better for type safety
    public static final int NORTH = 90;
    public static final int EAST = 0;
    public static final int SOUTH = 270;
    public static final int WEST = 180;
    
    private boolean canCollide;

    private SpriteData data;
    private int speed;
    private String skillClass;
    public Sprite( String imgFile )
    {
        this( makeTransparent( toImage( "/imgs/" + imgFile ), java.awt.Color.WHITE ) );
    }

    public Sprite( BufferedImage img )
    {
        super( img );
        this.setCollidable( false );
        this.setRefPixel( getWidth() / 2, getHeight() / 2 );
        data = new SpriteData();
        data.setDirFacing( SOUTH );
    }

    @Deprecated
    @Override
    public void paint( Graphics g )
    {
        paint( g, false );
    }
    
    public void paint( Graphics g, boolean debug )
    {
        super.paint( g );
        if ( debug )
        {
            Rectangle rect = this.getBounds();
            g.setColor( Color.WHITE );
            g.drawRect( rect.x, rect.y, rect.width, rect.height );
        }
    }
    
    public void move( long gameTime, Map map, SpriteGroup sprites )
    {
        for ( Sprite sprite : sprites )
        {
            for ( Sprite s : sprites )
            {
                if ( s instanceof FightingSprite )
                    sprite.seeSprite( s );
            }
        }
        double dir = getDirection( gameTime );
        if ( dir == -1 ) return;
        this.setDirFacing( (int)dir );
        dir = Math.toRadians( dir );
        int speed = getSpeed();
        int x = (int)Math.round( speed * Math.cos( dir ) ); // note: does not work because ints
        int y = (int)Math.round( speed * Math.sin( dir ) );
        translate( x, 0 );
        if ( map.isColliding( this ) || isColliding( sprites, true ) )
        {
            translate( -x, 0 );
        }
        translate( 0, y );
        if ( map.isColliding( this ) || isColliding( sprites, true ) )
        {
            translate( 0, -y );
        }
    }
    public boolean isColliding( SpriteGroup sprites, boolean inform )
    {
        for ( Sprite s : sprites )
        {
            if ( s != this && this.collidesWith( s ) && additionalCollisions( s ) )
            {
                if ( inform )
                {
                    this.hitSprite( s );
                    s.hitSprite( this );
                }
                return true;
            }
        }
        return false;
    }
    
    public boolean collidesWith( ImageSprite s )
    {
        return super.collidesWith( s, PIXEL_PERFECT );
    }
    /**
     * Return true if all additional collision conditions are satisfied. If true,
     * this sprite will collide with the sprite in the parameter
     * 
     * False means that this sprite is not to be collided with
     * @param s Sprite that is visually colliding with this sprite
     * @return
     */
    public abstract boolean additionalCollisions( Sprite s );
    
    public void setDirFacing( int dir )
    {
        int dirFacing = data.getDirFacing();
        if ( dir < 0 ) return; // note: can turn into a loop (may be easier with enums)
        else if ( dir < EAST + 45 || dir > SOUTH + 45 ) dirFacing = EAST;
        else if ( dir == EAST + 45 ) {
            if ( dirFacing != EAST && dirFacing != NORTH ) dirFacing = ( dirFacing == SOUTH ) ? EAST : NORTH;
        }
        else if ( dir < NORTH + 45 ) dirFacing = NORTH;
        else if ( dir == NORTH + 45 ) {
            if ( dirFacing != WEST && dirFacing != NORTH ) dirFacing = ( dirFacing == SOUTH ) ? WEST : NORTH;
        }
        else if ( dir < WEST + 45 ) dirFacing = WEST;
        else if ( dir == WEST + 45 ) {
            if ( dirFacing != WEST && dirFacing != SOUTH ) dirFacing = ( dirFacing == NORTH ) ? WEST : SOUTH;
        }
        else if ( dir < SOUTH + 45 ) dirFacing = SOUTH;
        else if ( dir == SOUTH + 45 ) {
            if ( dirFacing != EAST && dirFacing != SOUTH ) dirFacing = ( dirFacing == NORTH ) ? EAST : SOUTH;
        }
        data.setDirFacing( dirFacing );
    }
    
    public SpriteData getSpriteData()
    {
        return data;
    }
    
    /**
     * Returns the direction that this sprite should move or -1.0
     * if not moving.
     * @param gameTime the total time in the game
     * @return direction in degrees
     */
    public abstract double getDirection( long gameTime );
    
    /**
     * Method called when sprite hits the wall
     * @param dir direction that the wall is (will be: NORTH, EAST, SOUTH, WEST)
     */
    public abstract void hitWall( int dir );
    /**
     * Method called when this hits any sprite including Weapon, Enemy, Player
     * @param sprite any sprite in game
     */
    public abstract void hitSprite( Sprite sprite );
    public void seeSprite( Sprite sprite ) {}
    
    public abstract void takeDamage( int damage );
    
    public abstract boolean friendlyFire();
    
    @Override
    public void splitSprite( int cols, int rows )
    {
        if ( cols <= 1 && rows <= 1 ) return;
        super.splitSprite( cols, rows );
        this.setRefPixel( getWidth() / 2, getHeight() / 2 );
    }
    
    public boolean isDead()
    {
        return data.isDead(); // TODO
    }
    public void dying()
    {
        data.dies();
    }
    
    public String getSkillClass()
    {
        return skillClass;
    }
    public void setSkillClass( String newClass )
    {
        this.skillClass = newClass;
    }
    
    // -------------------------- DRAWING GETTERS/SETTERS ------------------- //
    
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
    
    public int getSpeed()
    {
        return speed;
    }

    public void setSpeed( int speed )
    {
        this.speed = speed;
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
    
    public Point getPosition()
    {
        return new Point( this.getX(), this.getY() );
    }
    
    public Rectangle getBounds()
    {
        return Sprite.getBounds( this );
    }
    
    public double distance( Sprite sprite )
    {
        return this.distance( sprite.getPosition() );
    }
    
    public double distance( Point p )
    {
        return this.getPosition().distance( p );
    }
    
    public int direction( Sprite sprite )
    {
        int x = sprite.getX() - this.getX();
        int y = sprite.getY() - this.getY();
        return (int)Math.toDegrees( Math.atan2( y, x ) );
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
