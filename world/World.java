package world;

import sprites.Sprite;
import sprites.SpriteGroup;

public class World
{
    SpriteGroup sprites;
    public World()
    {
        sprites = new SpriteGroup();
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
