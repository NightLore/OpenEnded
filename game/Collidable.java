package game;

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
