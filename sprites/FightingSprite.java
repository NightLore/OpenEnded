package sprites;

import java.awt.image.BufferedImage;

import sprites.weapons.Weapon;

public abstract class FightingSprite extends Sprite
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public FightingSprite( BufferedImage img )
    {
        super( img );
    }

    // public abstract void hitWeapon( Sprite weapon );
    public abstract void seeSprite( Sprite sprite );

    public abstract Weapon attack( int attack );
}
