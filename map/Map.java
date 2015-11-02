package map;

import java.awt.Graphics2D;

public class Map
{
    private LargeTile[][] tiles;
    
    public Map()
    {
        tiles = new LargeTile[3][3];
    }
    
    public void create()
    {
        // TODO
    }
    
    public void generate()
    {
        // TODO
    }
    
    
    /**
     * Updates this map with the center of screen position and the width and 
     * height of the screen
     * @param centerX x-coordinate of center of screen
     * @param centerY y-coordinate of center of screen
     * @param screenW width of screen
     * @param screenH height of screen
     */
    public void update( int centerX, int centerY, int screenW, int screenH )
    {
        
    }
    
    public void draw( Graphics2D g2d )
    {
        for ( LargeTile[] row : tiles )
        {
            for ( LargeTile t : row )
            {
                t.draw( g2d );
            }
        }
    }
}
