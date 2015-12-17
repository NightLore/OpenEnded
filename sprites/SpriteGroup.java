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
    
    public void moveAll( long gameTime, Map map )
    {
        for ( int i = 0; i < size(); i++ ) {
            if ( !this.get( i ).isDead() )
                this.get( i ).move( gameTime, map, this );
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
                x += s.getX();
                y += s.getY();
            }
            x /= size;
            y /= size;
            return new Point( x, y );
        }
        return null;
    }

}
