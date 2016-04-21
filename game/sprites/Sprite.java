package game.sprites;

import game.sprites.groups.SpriteGroup;
import game.world.Map;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

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

    public Sprite( BufferedImage img )
    {
        super( img );
        this.setCollidable( false );
        this.setRefPixel( getWidth() / 2, getHeight() / 2 );
        data = new SpriteData();
        data.setDirFacing( SOUTH );
        init();
    }
    
    /**
     * Empty Method called by the Constructor in case subclasses need more 
     * control in constructing
     */
    public void init() {}
    
    @Override
    public Sprite clone()
    {
        Sprite s = (Sprite)super.clone();
        s.data = new SpriteData( getSpriteData() );
        return s;
    }
    
    /**
     * Paints this Sprite and the debug information if indicated
     * @param g
     * @param debug
     */
    public void paint( Graphics g, boolean debug )
    {
        super.paint( g );
        if ( debug )
        {
            paintDebug( g );
        }
    }
    
    /**
     * Paints this Sprite's debug information
     * @param g
     */
    public void paintDebug( Graphics g )
    {
        Rectangle rect = this.getBounds();
        g.setColor( Color.WHITE );
        g.drawRect( rect.x, rect.y, rect.width, rect.height );
    }
    
    public void move( long gameTime, Map map, SpriteGroup sprites )
    {
        for ( Sprite sprite : sprites )
        {
            for ( Sprite s : sprites )
            {
                if ( s instanceof FightingSprite )
                    sprite.seeSprite( s ); // unimplemented event check
            }
        }
        
        double dir = getDirection( gameTime ); // getThis sprites direction to go
        if ( dir == -1 ) return; // -1 means don't move
        this.setDirFacing( (int)dir ); // updates animation direction
        dir = Math.toRadians( dir ); // converts to radians
        int speed = getSpeed(); // get speed
        // calculate change in x and y
        int x = (int)Math.round( speed * Math.cos( dir ) ); // note: does not work because ints
        int y = (int)Math.round( speed * Math.sin( dir ) );
        
        translate( x, 0 ); // move in x direction
        if ( map.isColliding( this ) || isColliding( sprites, true ) )
        {
            translate( -x, 0 ); // move back if collided
        }
        translate( 0, y ); // move in y direction
        if ( map.isColliding( this ) || isColliding( sprites, true ) )
        {
            translate( 0, -y ); // move back if collided
        }
    }
    /**
     * Returns whether this sprite collides with any of the sprites in the SpriteGroup
     * <br><br>
     * Note: in order to inform sprites of their collisions, call 
     * isColliding(SpriteGroup,boolean)
     * 
     * @param sprites SpriteGroup to compare with
     * @return whether this sprite collides with any of the sprites in the SpriteGroup
     * @see game.sprites.Sprite#isColliding(SpriteGroup,boolean)
     */
    public boolean isColliding( SpriteGroup sprites )
    {
        return isColliding( sprites, false );
    }
    /**
     * Returns whether this sprite collides with any of the sprites in the SpriteGroup
     * @param sprites SpriteGroup to compare with
     * @param inform if true, this method will inform the sprites of their collisions
     * @return whether this sprite collides with any of the sprites in the SpriteGroup
     */
    public boolean isColliding( SpriteGroup sprites, boolean inform )
    {
        for ( Sprite s : sprites )
        {
            if ( this.collidesWith( s ) )
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
    
    /**
     * Determines whether or not this sprite collides with a Sprite.
     * <br><br>Calls the additionalCollisions(Sprite) method
     * @param s Sprite to compare with
     * @return
     * @see game.sprites.Sprite#additionalCollisions(Sprite)
     */
    public boolean collidesWith( Sprite s )
    {
        return s != this && collidesWith( s, PIXEL_PERFECT ) && additionalCollisions( s );
    }
    /**
     * Called with the given sprite such that Sprite <i>s</i> satisfies the 
     * following conditions:
     * <br> 1. s != this
     * <br> 2. this.collidesWith( s, PIXEL_PERFECT )
     * 
     * Return true if all additional collision conditions are satisfied. If true,
     * this sprite will collide with the sprite in the parameter
     * 
     * False means that this sprite is not to be collided with
     * @param s Sprite that is visually colliding with this sprite
     * @return
     * @see game.sprites.Sprite#collidesWith(ImageSprite, boolean)
     * @see game.sprites.Sprite#PIXEL_PERFECT
     */
    public abstract boolean additionalCollisions( Sprite s );
    
    /**
     * Sets the direction facing by rounding the given <i>dir</i> to the nearest 
     * 90 degrees
     * <br><br>
     * This direction is meant to be used for animation and Player input purposes
     */
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
    
    /**
     * Returns this sprite's data
     * <br><br>Note: does NOT make a copy of the SpriteData
     * @return data
     */
    public SpriteData getSpriteData()
    {
        return data;
    }
    
    /**
     * Returns the direction that this sprite should move or -1.0
     * if not moving.
     * <br><br>
     * This method is where attacking should be done for FightingSprites
     * @param gameTime the total time in the game
     * @return direction in degrees
     */
    public abstract double getDirection( long gameTime );
    
    /**
     * Method called when sprite hits the wall
     * <br><br>
     * Note: not properly supported by {@link game.world.Map#isColliding(Sprite)}
     * 
     * @param dir direction that the wall is (will be: NORTH, EAST, SOUTH, WEST)
     */
    public abstract void hitWall( int dir );
    /**
     * Method called when this hits any sprite including Weapon, Enemy, Player
     * @param sprite any sprite in game
     */
    public abstract void hitSprite( Sprite sprite );
    /**
     * Unimplemented event based method that should be called when this sprite 
     * "sees" the given sprite
     * @param sprite Sprite seen
     */
    public void seeSprite( Sprite sprite ) {}
    
    /**
     * Method called when this sprite takes damage
     * @param damage
     */
    public abstract void takeDamage( int damage );
    
    /**
     * Returns whether or not this sprite has friendly fire on
     * @return true if friendlyFire is on
     */
    public abstract boolean friendlyFire();
    
    @Override
    public void splitSprite( int cols, int rows )
    {
        if ( cols <= 1 && rows <= 1 ) return;
        super.splitSprite( cols, rows );
        this.setRefPixel( getWidth() / 2, getHeight() / 2 );
    }
    
    /** Returns whether Hp <= 0 */
    public boolean isDead()
    {
        return data.isDead(); // TODO
    }
    /** sets Hp to 0 */
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
    
    /** Returns the direction that the given sprite is to this sprite */
    public int direction( Sprite sprite )
    {
        int x = sprite.getX() - this.getX();
        int y = sprite.getY() - this.getY();
        return (int)Math.toDegrees( Math.atan2( y, x ) );
    }
    
    @Override
    public String toString()
    {
        return getClass().toString().substring( 6 ) + "["+getX()+","+getY()+"]";
    }
}
