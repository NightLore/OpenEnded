package gui;

/**
 *  Interface for Classes that can navigate grid spaces (intended for menus but
 *  can be used for sprites as well)
 *
 *  @author  Nathan Man-ho Lui
 *  @version Feb 7, 2016
 */
public interface Navigator
{
    public static final String UP = "UP";
    public static final String LEFT = "LEFT";
    public static final String DOWN = "DOWN";
    public static final String RIGHT = "RIGHT";
    
    public void up();
    public void left();
    public void down();
    public void right();
    
    public String getSelected();
    public void addMenuItem( String item );
}
