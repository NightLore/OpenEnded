package gui.utilities;

/**
 *  Extends the {@link gui.utilities.MenuNavigator} and overrides the 
 *  keepSelectedInBounds() method in order to add the extra condition that the
 *  selected element should never be null or empty
 *
 *  @author  Nathan M. Lui
 *  @version Mar 25, 2016
 *  @author  Assignment: OpenEnded
 */
public class RestrictedMenuNavigator extends MenuNavigator
{

    public RestrictedMenuNavigator( int w, int h )
    {
        super( w, h );
    }
    
    @Override
    protected boolean keepSelectedInBounds()
    {
        boolean b = super.keepSelectedInBounds();
        String s = getSelected();
        if ( s == null || s.equals( "" ) ) // if selected element is null or empty
        {
            // Assuming entire grid is filled and has no holes...
            selectedY--; // go back in y direction
            if ( selectedY < 0 ) // if can't go back in y direction
                selectedX--; // go back in x direction
            super.keepSelectedInBounds(); // sets anything less than 0 to 0
            b = true;
        }
        return b;
    }
}
