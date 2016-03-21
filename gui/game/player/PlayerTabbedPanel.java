package gui.game.player;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;

import gui.ClearPanel;
import gui.ScreenPanel;
import gui.utilities.MappedSelector;
import gui.utilities.Navigator;
import gui.utilities.RestrictedMenuNavigator;
import gui.utilities.SelectableButton;
import gui.utilities.Selector;

/**
 *  Panel that represents a tab for the player's gridded menu
 *
 *  @author  Nathan M. Lui
 *  @version Mar 20, 2016
 *  @author  Project: OpenEnded
 */
public class PlayerTabbedPanel extends ScreenPanel
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private ScreenPanel parent;
    private Selector selector;
    private JPanel panel;

    public PlayerTabbedPanel( ScreenPanel p, int w, int h )
    {
        super( null, null );
        this.setLayout( new BorderLayout() );
        this.navigator = new RestrictedMenuNavigator( w, h );
        this.selector = new MappedSelector();
        this.parent = p;
        this.panel = new ClearPanel( new GridLayout( 0, w, 10, 10 ) ); // row, col instead of w, h
        JPanel pane = new ClearPanel( new GridLayout( 1, 0 ) );
        pane.add( panel );
        this.add( pane, BorderLayout.PAGE_START );
    }
    
    public void replaceItems( String... items )
    {
        this.panel.removeAll();
        this.navigator.clear();
        this.selector = new MappedSelector();
        this.addItems( items );
        this.navigator.setSelector( selector );
    }
    
    public void addItems( String... items )
    {
        for ( String item : items )
        {
            addItem( item );
        }
    }
    
    public void addItem( String item )
    {
        SelectableButton button = new SelectableButton( item, Color.WHITE );
        button.setActionCommand( item );
        button.addActionListener( parent );
        button.setContentAreaFilled( false );
        button.setDeselectedBorder( null );
        
        this.panel.add( button );
        this.selector.addSelectable( item, button );
        this.navigator.addMenuItem( item );
    }
    
    public Navigator getNavigator()
    {
        return navigator;
    }
}
