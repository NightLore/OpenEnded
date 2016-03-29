package game.sprites;

import java.awt.Color;
import java.awt.Graphics;

/**
 *  Holds all the sprite data for sprites
 *
 *  @author  Nathan Man-ho Lui
 *  @version Dec 19, 2015
 *  @author  Assignment: OpenEnded
 *
 *  @author  Sources: none
 */
public class SpriteData
{
    private static final int HP = 0;
    private static final int MAXHP = 1;
    private static final int ATK = 2;
    private static final int NUMDATA = 3;
    
    private int[] data;
    private int dirFacing;
    
    public SpriteData()
    {
        data = new int[NUMDATA];
        this.setHp( 10 );
        this.setMaxHp( 10 );
        this.setAtk( 2 );
    }
    
    public SpriteData( SpriteData sprite )
    {
        this();
        System.arraycopy( data, 0, sprite.data, 0, NUMDATA );
    }
    
    public void drawHp( Graphics g, Sprite sprite, boolean edgeScreen )
    {
        if ( !edgeScreen )
        {
            int w = sprite.getWidth() + 10;
            int h = 7;
            int x = sprite.getX() - w / 2;
            int y = sprite.getRealY() + sprite.getHeight();
            g.setColor( Color.DARK_GRAY );
            g.fillRect( x, y, w, h );
            g.setColor( Color.GREEN );
            g.fillRect( x + 1, y + 1, ( w - 2 ) * getHp() / getMaxHp(), h - 2 );
        }
    }
    
    public int getHp()
    {
        return data[HP];
    }
    
    public int getMaxHp()
    {
        return data[MAXHP];
    }
    
    public int getAtk()
    {
        return data[ATK];
    }
    
    public void resetHp()
    {
        setHp( data[MAXHP] );
    }
    
    public void increaseHp( int increase )
    {
        data[HP] += increase;
    }
    
    public void decreaseHp( int decrease )
    {
        increaseHp( -decrease );
    }
    
    public void setHp( int hp )
    {
        data[HP] = hp;
    }
    
    public void setMaxHp( int maxHp )
    {
        data[MAXHP] = maxHp;
    }
    
    public void dies()
    {
        this.setHp( 0 );
    }
    
    public boolean isDead()
    {
        return this.getHp() <= 0;
    }
    
    public void setAtk( int atk )
    {
        data[ATK] = atk;
    }
    
    /** Returns the direction this sprite is facing where EAST = 0, NORTH = 90... */
    public int getDirFacing()
    {
        return dirFacing;
    }
    
    public void setDirFacing( int newDir )
    {
        dirFacing = newDir;
    }
    
    
}
