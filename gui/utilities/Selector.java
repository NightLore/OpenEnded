package gui.utilities;

/**
 *  Interface for objects that manage Selectables
 *
 *  @author  Nathan Man-ho Lui
 *  @version Feb 17, 2016
 */
public interface Selector
{
    public void addSelectable( String s, Selectable selectable );
    public void select( String selected );
    
    public Selectable getSelected();
}
