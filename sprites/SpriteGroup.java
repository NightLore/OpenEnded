package sprites;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.ArrayList;

import world.Map;

/**
 *  Manages Sprites and removes by replacing the removed sprite with the last 
 *  one
 *
 *  @author  Nathan Man-ho Lui
 *  @version Oct 28, 2015
 *  @author  Assignment: OpenEnded
 *
 *  @author  Sources: none
 */
public class SpriteGroup<S extends Sprite> extends ArrayList<Sprite> implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
     * Overrides the ArrayList's remove(int) method to replace the removed 
     * element with the last element
     * 
     * @see java.util.ArrayList#remove(int)
     */
    @Override
    public synchronized Sprite remove( int i )
    {
        Sprite s = super.remove( size() - 1 );
        if ( i < size() ) // size decremented because of removal
            s = this.set( i, s );
        return s;
    }
    
    @Override
    public synchronized boolean remove( Object obj ) // TODO broken, thread issue? or...needs testing
    {
        int i = this.indexOf( obj );
        if ( i >= 0 )
        {
            this.remove( i );
            return true;
        }
        return false;
    }
    
    public void moveAll( long gameTime, Map map )
    {
        for ( int i = 0; i < size(); i++ ) {
            if ( !this.get( i ).isDead() )
                this.get( i ).move( gameTime, map, this );
        }
    }

    public void paintAll( Graphics g, Rectangle frame, boolean debug )
    {
        for ( int i = 0; i < size(); i++ ) {
            if ( !this.get( i ).isDead() && frame.intersects( Sprite.getBounds( this.get( i ) ) ) )
                this.get( i ).paint( g, debug );
        }
    }
    
    public void continueAnimation()
    {
        for ( int i = 0; i < size(); i++ ) {
            if ( !this.get( i ).isDead() )
                this.get( i ).continueAnimation();
        }
    }
    
    public Point getCenter()
    {
        if ( this.size() > 0 ) {
            int x = 0;
            int y = 0;
            for ( Sprite s : this ) {
                if ( !s.isDead() )
                {
                    x += s.getX();
                    y += s.getY();
                }
            }
//            if ( size <= 0 ) size = 1; // multi-thread problem
            x = Math.round( x / this.size() );
            y = Math.round( y / this.size() );
            return new Point( x, y );
        }
        return null;
    }

}
