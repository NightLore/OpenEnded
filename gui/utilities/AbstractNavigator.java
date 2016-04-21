package gui.utilities;

/**
 *  An Abstract Navigator that defines {@link #reset()}, {@link #setSelector()}, 
 *  and provides {@link #updateSelector()} methods
 *  <br><br>
 *  Implements {@link Navigator}
 *
 *  @author  Nathan Man-ho Lui
 *  @version Feb 17, 2016
 */
public abstract class AbstractNavigator implements Navigator
{
    protected int selectedX, selectedY;
    private Selector selector;

    @Override
    public void reset()
    {
        select( 0, 0 );
    }
    
    private void select( int x, int y )
    {
        selectedX = x;
        selectedY = y;
        updateSelector();
    }

    /**
     * Sets the Selector and then updates it by calling {@link #updateSelector()}
     * @param selector
     */
    @Override
    public void setSelector( Selector selector )
    {
        this.selector = selector;
        updateSelector();
    }
    
    @Override
    public void updateSelector()
    {
        if ( selector != null )
            this.selector.select( getSelected() );
    }

}
