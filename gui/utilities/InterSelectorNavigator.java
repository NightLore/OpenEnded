package gui.utilities;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *  A Navigator that can Navigate between multiple Navigators when !inNavigator()
 *
 *  @author  Nathan Man-ho Lui
 *  @version Feb 17, 2016
 */
public class InterSelectorNavigator extends AbstractNavigator
{
    private HashMap<String,Navigator> navigators;
    private ArrayList<String> navigator;
    private boolean horizontal;
    private boolean inNavigator;
    private int loc;
    
    public InterSelectorNavigator( int size, boolean horizontal )
    {
        init( size, horizontal );
    }
    
    @Override
    public void clear()
    {
        init( this.navigator.size(), this.horizontal );
    }
    
    private void init( int size, boolean horizontal )
    {
        this.navigators = new HashMap<String,Navigator>();
        this.navigator = new ArrayList<String>( size );
        this.horizontal = horizontal;
        this.inNavigator = false;
        this.loc = 0;
        this.reset();
    }

    @Override
    public void up()
    {
        if ( inNavigator )
        {
            navigators.get( navigator.get( loc ) ).up();
        }
        else if ( !horizontal )
        {
            decrement();
            updateSelector();
        }
    }


    @Override
    public void left()
    {
        if ( inNavigator )
        {
            navigators.get( navigator.get( loc ) ).left();
        }
        else if ( horizontal )
        {
            decrement();
            updateSelector();
        }
    }


    @Override
    public void down()
    {
        if ( inNavigator )
        {
            navigators.get( navigator.get( loc ) ).down();
        }
        else if ( !horizontal )
        {
            increment();
            updateSelector();
        }
    }


    @Override
    public void right()
    {
        if ( inNavigator )
        {
            navigators.get( navigator.get( loc ) ).right();
        }
        else if ( horizontal )
        {
            increment();
            updateSelector();
        }
    }

    private void decrement()
    {
        loc--;
        if ( loc < 0 )
            loc = 0;
    }
    
    private void increment()
    {
        loc++;
        if ( loc >= navigator.size() )
            loc = navigator.size() - 1;
    }

    @Override
    public String getSelected()
    {
        String selected = null;
        if ( inNavigator )
        {
            selected = navigators.get( navigator.get( loc ) ).getSelected();
        }
        else
        {
            selected = navigator.get( loc );
        }
        return selected;
    }
    
    public void exitNavigators()
    {
        inNavigator = false;
    }
    
    public void enterNavigators()
    {
        inNavigator = true;
    }
    
    public boolean inNavigator()
    {
        return inNavigator;
    }


    @Override
    public void addMenuItem( String item )
    {
        System.err.println( "Cannot add " + item + " as a MenuItem to an InterSelectableNavigator" );
    }
    
    public void addNavigator( String index, Navigator navigator )
    {
        this.navigator.add( index );
        this.navigators.put( index, navigator );
    }
    
    public int getCurrentIndex()
    {
        return loc;
    }
    
    public Navigator getCurrentNavigator()
    {
        return navigators.get( navigator.get( loc ) );
    }

}
