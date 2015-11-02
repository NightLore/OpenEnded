package map;

import java.awt.Graphics2D;

public class LargeTile
{
    private Tile[][] tiles;
    private int x;
    private int y;
    public LargeTile( int x, int y, int width, int height )
    {
        this.x = x;
        this.y = y;
        tiles = new Tile[width][height];
    }
    
    public void generate()
    {
        // TODO
    }
    
    public void draw( Graphics2D g2d )
    {
        for ( Tile[] row : tiles )
        {
            for ( Tile t : row )
            {
                t.paint( g2d );
            }
        }
    }
}
