package sprites;

import java.awt.Point;

/**
 *  Manages the group of Players
 *
 *  @author  Nathan Man-ho Lui
 *  @version Jan 11, 2016
 *  @author  Assignment: OpenEnded
 *
 *  @author  Sources: none
 */
public class PlayerGroup extends SpriteList<Player>
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    
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
