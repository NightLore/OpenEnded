package gui.utilities;

import java.util.HashMap;

/**
 *  Selector that extends a HashMap in order to keep track of its selectables
 *
 *  @author  Nathan Man-ho Lui
 *  @version Feb 17, 2016
 */
public class MappedSelector extends HashMap<String,Selectable> implements Selector
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String selected;

    @Override
    public void addSelectable( String s, Selectable selectable )
    {
        this.put( s, selectable );
    }

    @Override
    public void select( String selected )
    {
        if ( this.selected != null )
        {
            this.getSelected().deselect();
        }
        this.selected = selected;
        if ( this.selected != null )
        {
            this.getSelected().select();
        }
    }

    @Override
    public Selectable getSelected()
    {
        return this.get( this.selected );
    }

}
