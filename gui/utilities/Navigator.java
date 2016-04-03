package gui.utilities;

import constants.DirectionalConstants;

/**
 *  Interface for Classes that can navigate grid spaces (intended for menus but
 *  can be used for sprites as well)
 *
 *  @author  Nathan Man-ho Lui
 *  @version Feb 7, 2016
 */
public interface Navigator extends DirectionalConstants
{
    public void up();
    public void left();
    public void down();
    public void right();
    
    public void clear();
    public void reset();
    public void setSelector( Selector selector );
    public String getSelected();
    public void addMenuItem( String item );
    public void updateSelector();
}
