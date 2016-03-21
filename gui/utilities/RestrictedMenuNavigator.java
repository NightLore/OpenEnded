package gui.utilities;

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
        if ( s == null || s.equals( "" ) )
        {
            selectedY--;
            if ( selectedY < 0 )
                selectedX--;
            super.keepSelectedInBounds();
            b = true;
        }
        return b;
    }
}
