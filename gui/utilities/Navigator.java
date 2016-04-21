package gui.utilities;

import constants.DirectionalConstants;

/**
 *  Interface for Classes that can navigate grid spaces (intended for menus but
 *  can be used for sprites as well)
 *
 *  @author  Nathan Man-ho Lui
 *  @version Feb 7, 2016
 *  @see <b>Subclasses:</b> 
 *  {@link AbstractNavigator},
 *  {@link InterSelectorNavigator},
 *  {@link MenuNavigator},
 *  {@link RestrictedMenuNavigator}
 */
public interface Navigator extends DirectionalConstants
{
    public void up();
    public void left();
    public void down();
    public void right();
    
    /** Empties this Navigator, completely resetting it */
    public void clear();
    
    /**
     * Resets what is selected, usually selecting what was initially selected 
     * when this class is initialized
     */
    public void reset();
    
    public void setSelector( Selector selector );
    /**
     * Returns what is currently selected
     * @return
     */
    public String getSelected();
    
    public void addMenuItem( String item );
    
    /** Has its {@link Selector} update its {@link Selectable}'s */
    public void updateSelector();
}
