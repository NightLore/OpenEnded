package world;

import java.awt.Point;

import sprites.Sprite;
import sprites.SpriteGroup;

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
        for ( Sprite s : sprites )
        {
            s.move( gameTime, map );
        }
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
