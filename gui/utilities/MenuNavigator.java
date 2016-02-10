package gui.utilities;

/**
 *  This class navigates through a given grid of Strings (intended to represent 
 *  a function. When  
 *
 *  @author  Nathan Man-ho Lui
 *  @version Feb 7, 2016
 */
public class MenuNavigator implements Navigator
{
    private String[][] menu;
    private int addX, addY;
    private int selectedX, selectedY;
    private Selector selector;
    
    public MenuNavigator( int w, int h )
    {
        menu = new String[w][h];
        addX = 0;
        addY = 0;
        selectedX = 0;
        selectedY = 0;
    }
    
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
            System.err.println( addX + "," + addY + "; ADDING TOO MUCH: " + this );
        }
        addX++;
        if ( addX >= menu.length )
        {
            addX = 0;
            addY++;
        }
    }
    
    private void select( int x, int y )
    {
        selectedX = x;
        selectedY = y;
        updateSelector();
    }

    @Override
    public void up()
    {
        selectedY--;
        if ( selectedY < 0 ) selectedY = 0;
        updateSelector();
    }

    @Override
    public void left()
    {
        selectedX--;
        if ( selectedX < 0 ) selectedX = 0;
        updateSelector();
    }

    @Override
    public void down()
    {
        selectedY++;
        if ( selectedY >= menu[0].length ) selectedY = menu[0].length - 1;
        updateSelector();
    }

    @Override
    public void right()
    {
        selectedX++;
        if ( selectedX >= menu.length ) selectedX = menu.length - 1;
        updateSelector();
    }

    @Override
    public void reset()
    {
        select( 0, 0 );
    }

    @Override
    public void setSelector( Selector selector )
    {
        this.selector = selector;
        updateSelector();
    }
    
    @Override
    public String getSelected()
    {
        return menu[selectedX][selectedY];
    }
    
    private void updateSelector()
    {
        if ( selector != null )
            this.selector.select( getSelected() );
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
