package sprites;

/**
 *  Conceptual interface for classes that can collide
 *
 *  @author  Nathan Man-ho Lui
 *  @version Nov 1, 2015
 *  @author  Assignment: OpenEnded
 *
 *  @author  Sources: none
 */
public interface Collidable
{
    /**
     * Returns whether this object is in a collidable state
     * @return whether this object is in a collidable state
     */
    public boolean canCollide();
    
    /**
     * Set the whether this object is in a collidable state
     * @param canCollide whether this object should be in a collidable state
     */
    public void setCollidable( boolean canCollide );
}
