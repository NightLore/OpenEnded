package world;

import java.awt.Point;

import sprites.Sprite;
import sprites.groups.SpriteGroup;

/**
 *  Class intended to manage interactions in the game (not implemented)
 *
 *  @author  Nathan Man-ho Lui
 *  @version Nov 22, 2015
 *  @author  Assignment: OpenEnded
 *
 *  @author  Sources: none
 */
public class World
{
    SpriteGroup sprites;
    Map map;
    public World( Map map )
    {
        sprites = new SpriteGroup();
        this.map = map;
    }
    
    public void update( long gameTime, Point center )
    {
        map.update( center ); // TODO
        sprites.moveAll( gameTime, map );
    }
    
    public void addSprite( Sprite sprite )
    {
        sprites.add( sprite );
    }
    
    public void removeSprite( Sprite sprite )
    {
        sprites.remove( sprite );
    }
}
