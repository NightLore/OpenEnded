package sprites;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

/**
 *  Allows the drawing of all sprites. Removes by replacing the removed sprite 
 *  with the last one
 *
 *  @author  Nathan Man-ho Lui
 *  @version Jan 11, 2016
 *  @author  Assignment: OpenEnded
 *
 *  @author  Sources: none
 */
public class SpriteList<E extends Sprite> extends ArrayList<E>
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Overrides the ArrayList's remove(int) method to replace the removed 
     * element with the last element
     * 
     * @see java.util.ArrayList#remove(int)
     */
    @Override
    public synchronized E remove( int i )
    {
        E s = super.remove( size() - 1 );
        if ( i < size() ) // size decremented because of removal
            s = this.set( i, s );
        return s;
    }
    
    @Override
    public synchronized boolean remove( Object obj )
    {
        int i = this.indexOf( obj );
        if ( i >= 0 )
        {
            this.remove( i );
            return true;
        }
        return false;
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

}
