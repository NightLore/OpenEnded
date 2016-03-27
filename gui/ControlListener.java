package gui;

/**
 *  Interface for panels that use keyboard controls to navigate
 *
 *  @author  Nathan Lui
 *  @version Feb 3, 2016
 */
public interface ControlListener
{
    public void up();
    public void left();
    public void down();
    public void right();
    public void confirm();
    public void cancel();
    
    public void act( String selected );
}
