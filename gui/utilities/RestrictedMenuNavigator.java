package gui.utilities;

/**
 *  Extends the {@link MenuNavigator} and overrides the 
 *  keepSelectedInBounds() method in order to add the extra condition that the
 *  selected element should never be null or empty
 *
 *  @author  Nathan M. Lui
 *  @version Mar 25, 2016
 *  @author  Assignment: OpenEnded
 */
public class RestrictedMenuNavigator extends MenuNavigator
{

    /**
     * @param w width of grid
     * @param h height of grid
     * @see gui.utilities.MenuNavigator#MenuNavigator(int, int)
     */
    public RestrictedMenuNavigator( int w, int h )
    {
        super( w, h );
    }
    
    @Override
    protected boolean keepSelectedInBounds()
    {
        boolean wentOutOfBounds = super.keepSelectedInBounds();
        String s = getSelected();
        while ( s == null || s.equals( "" ) ) // while selected element is null or empty
        {
            if ( !wentOutOfBounds )
            {
                int x = selectedX;
                selectedX--;
                if ( !this.keepSelectedInBounds() )
                {
                    selectedX = 0;
                }
                else
                {
                    selectedX = x;
                    selectedY = 0;
                }
                wentOutOfBounds = true;
            }
            else
            {
                // Assuming entire grid is filled and has no holes...
                selectedY--; // returns selectedY to the lowest available element using the loop
                if ( selectedY < 0 )
                    selectedX--;
                if ( selectedX < 0 ) // no element in this grid
                    break;
                super.keepSelectedInBounds(); // makes sure that selected is not out of bounds
            }
            s = getSelected(); // update s to the selected element
        }
        return wentOutOfBounds;
    }
}
