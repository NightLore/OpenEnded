package gui;

/**
 *  Interface for all screens, allows for screens to know when they are shown 
 *  and covered.
 *
 *  @author  Nathan Man-ho Lui
 *  @version Oct 28, 2015
 *  @author  Assignment: OpenEnded
 *
 *  @author  Sources: none
 */
public interface Screen
{
    /**
     * Method called when this screen is shown
     */
    public void shown();
    
    /**
     * Method called when this screen is hidden
     */
    public void cover();
    
}
