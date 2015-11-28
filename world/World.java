package world;

import java.awt.Point;

import sprites.Sprite;
import sprites.SpriteGroup;

public class World
{
    SpriteGroup<Sprite> sprites;
    Map map;
    public World( Map map )
    {
        sprites = new SpriteGroup<Sprite>();
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
