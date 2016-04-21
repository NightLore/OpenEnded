package gui.utilities;

/**
 *  Interface for selectable objects
 *
 *  @author  Nathan Man-ho Lui
 *  @version Feb 17, 2016
 *  @see <b>Subclasses:</b>
 *  {@link SelectableButton}
 */
public interface Selectable
{
    /** Changes this Selectable to appear as though it was selected */
    public void select();
    
    /** Changes this Selectable to appear as though it was not selected */
    public void deselect();
}
