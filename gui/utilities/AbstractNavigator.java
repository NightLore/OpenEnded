package gui.utilities;

/**
 *  An Abstract Navigator that defines reset(), setSelector(), and provides 
 *  updateSelector()
 *
 *  @author  Nathan Man-ho Lui
 *  @version Feb 17, 2016
 */
public abstract class AbstractNavigator implements Navigator
{
    protected int selectedX, selectedY;
    private Selector selector;
    
    public AbstractNavigator()
    {
        selectedX = 0;
        selectedY = 0;
    }

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

    @Override
    public void setSelector( Selector selector )
    {
        this.selector = selector;
        updateSelector();
    }
    
    public void updateSelector()
    {
        if ( selector != null )
            this.selector.select( getSelected() );
    }

}
