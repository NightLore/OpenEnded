package gui.utilities;

import java.util.HashMap;

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
            this.get( this.selected ).deselect();
        }
        this.selected = selected;
        if ( this.selected != null )
        {
            this.get( this.selected ).select();
        }
    }

}
