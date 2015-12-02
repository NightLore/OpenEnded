package sprites.weapons;

import java.awt.image.BufferedImage;

import sprites.Sprite;

public class Weapon extends Sprite
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;


    public Weapon( BufferedImage img )
    {
        // TODO Auto-generated constructor stub
        super( img );
    }


    @Override
    public double getDirection( long gameTime )
    {
        // TODO Auto-generated method stub
        return 0;
    }


    @Override
    public void hitSprite( Sprite sprite )
    {
        // TODO Auto-generated method stub

    }


    @Override
    public int getSpeed()
    {
        // TODO Auto-generated method stub
        return 0;
    }

}
