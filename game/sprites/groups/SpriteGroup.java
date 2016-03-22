package game.sprites.groups;

import game.sprites.Sprite;
import game.world.Map;

/**
 *  Manages Sprites with their collisions and moving
 *
 *  @author  Nathan Man-ho Lui
 *  @version Oct 28, 2015
 *  @author  Assignment: OpenEnded
 *
 *  @author  Sources: none
 */
public class SpriteGroup extends SpriteList<Sprite>
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    public void moveAll( long gameTime, Map map )
    {
        for ( int i = 0; i < size(); i++ ) {
            if ( !this.get( i ).isDead() )
                this.get( i ).move( gameTime, map, this );
        }
    }
}
