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
    public void shown();
    public void cover();
//    public void pause();
//    public void resume();
    
}
