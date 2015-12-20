package sprites;

import gui.Window;

import java.awt.Graphics;
import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;

import world.Map;

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
    public Sprite remove( int i )
    {
        Sprite s = super.remove( size() - 1 );
        if ( i < size() - 1 )
            s = this.set( i, s );
        return s;
    }
    
    @Override
    public boolean remove( Object obj )
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
            else
                this.remove( i );
        }
    }
    
    public void paintAll( Graphics g )
    {
        for ( int i = 0; i < size(); i++ ) {
            if ( !this.get( i ).isDead() )
                this.get( i ).paint( g );
        }
    }
    
    public void continueAnimation()
    {
        for ( int i = 0; i < size(); i++ ) {
            if ( !this.get( i ).isDead() )
                this.get( i ).continueAnimation();
        }
    }
    
    private boolean isAtPoint( Point p )
    {
        Point center = this.getCenter();
        return center != null
            && center.getX() == p.getX()
            && center.getY() == p.getY();
    }
    
    public boolean isAtCenterOf( Window window )
    {
        return isAtPoint( new Point( window.getWidth() / 2, 
                                     window.getHeight() / 2 ) );
    }
    
    public Point getCenter()
    {
        int size = this.size();
        if ( size > 0 ) {
            int x = 0;
            int y = 0;
            for ( Sprite s : this ) {
                if ( !s.isDead() )
                {
                    x += s.getX();
                    y += s.getY();
                }
                else size--;
            }
            x /= size;
            y /= size;
            return new Point( x, y );
        }
        return null;
    }

}
