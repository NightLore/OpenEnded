package gui.utilities;

/**
 *  This class navigates through a given grid of Strings (intended to represent 
 *  a function. The grid is assumed to be completely filled so if there is no
 *  element in that section of the grid, it will either return <b>null</b> or
 *  an empty String.
 *
 *  @author  Nathan Man-ho Lui
 *  @version Feb 7, 2016
 */
public class MenuNavigator extends AbstractNavigator
{
    private String[][] menu;
    private int addX, addY;
    
    /**
     * Initializes an empty MenuNavigator with the given width and height
     * 
     * @param w width of grid
     * @param h height of grid
     */
    public MenuNavigator( int w, int h )
    {
        init( w, h );
    }
    
    @Override
    public void clear()
    {
        init( menu.length, menu[0].length );
    }
    
    private void init( int w, int h )
    {
        menu = new String[w][h];
        addX = 0;
        addY = 0;
        reset();
    }

    /**
     * Adds a menu item to this navigator
     * <br><br>
     * <b>Note:</b> Does not update its selector with the new item
     * @param item
     */
    @Override
    public void addMenuItem( String item )
    {
        while ( menu[addX][addY] != null && !menu[addX][addY].equals( "" ) ) incrementXY();
        addMenuItem( item, addX, addY );
        incrementXY();
    }
    
    public void addMenuItem( String item, int x, int y )
    {
        menu[x][y] = item;
    }
    
    private void incrementXY()
    {
        if ( addY >= menu[0].length )
        {
            System.err.println( "In MenuNavigator: " + addX + "," + addY + "; ADDING TOO MUCH:\n" + this );
        }
        addX++;
        if ( addX >= menu.length )
        {
            addX = 0;
            addY++;
        }
    }

    @Override
    public void up()
    {
        selectedY--;
        keepSelectedInBounds();
        updateSelector();
    }

    @Override
    public void left()
    {
        selectedX--;
        keepSelectedInBounds();
        updateSelector();
    }

    @Override
    public void down()
    {
        selectedY++;
        keepSelectedInBounds();
        updateSelector();
    }

    @Override
    public void right()
    {
        selectedX++;
        keepSelectedInBounds();
        updateSelector();
    }
    
    /**
     * Keeps the selected coordinates within the grid
     * @return true if a change was made
     */
    protected boolean keepSelectedInBounds()
    {
        boolean b = false;
        if ( selectedX < 0 ) { selectedX = menu.length - 1;    b = true; }
        if ( selectedY < 0 ) { selectedY = menu[0].length - 1; b = true; }
        if ( selectedX >= menu.length    ) { selectedX = 0;    b = true; }
        if ( selectedY >= menu[0].length ) { selectedY = 0;    b = true; }
        return b;
    }
    
    @Override
    public String getSelected()
    {
        return menu[selectedX][selectedY];
    }
    
    @Override
    public String toString()
    {
        String s = "Navigator:\n";
        for ( int i = 0; i < menu.length; i++ )
        {
            s += "\t[" + ( i == selectedX && 0 == selectedY ? "_" : "" ) + menu[i][0];
            for ( int j = 1; j < menu[i].length; j++ )
            {
                s += ",";
                s += ( i == selectedX && j == selectedY ) ? "_" : " ";
                s += menu[i][j];
            }
            s += "]\n";
        }
        return s;
    }
}
