package gui;

import gui.game.player.ControlListener;

public class MenuNavigator implements ControlListener
{
    private String[][] menu;
    private int addX, addY;
    private int selectedX, selectedY;
    
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
        while ( menu[addX][addY] != null ) incrementXY();
        addMenuItem( item, addX, addY );
        incrementXY();
    }
    
    public void addMenuItem( String item, int x, int y )
    {
        menu[x][y] = item;
    }
    
    private void incrementXY()
    {
        addX++;
        if ( addX >= menu.length )
        {
            addX = 0;
            addY++;
            if ( addY >= menu[addX].length )
                System.err.println( "at (" + addX + "," + addY + ") Maxed Space: " + menu );
        }
    }
    
    public void select( int x, int y )
    {
        selectedX = x;
        selectedY = y;
    }
    
    public String getSelected()
    {
        return menu[selectedX][selectedY];
    }

    @Override
    public void up()
    {
        selectedY--;
    }

    @Override
    public void left()
    {
        selectedX--;
    }

    @Override
    public void down()
    {
        selectedY++;
    }

    @Override
    public void right()
    {
        selectedX++;
    }
    
    // note: throw new UnsupportedException()
    @Override
    public void confirm() { System.err.println( "Unsupported call" ); }

    @Override
    public void cancel() { System.err.println( "Unsupported call" ); }
}
