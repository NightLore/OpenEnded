package gui.game.player;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

import sprites.Weapon;
import gui.Carder;
import gui.ClearPanel;
import gui.ScreenPanel;
import gui.utilities.InterSelectorNavigator;
import gui.utilities.MappedSelector;
import gui.utilities.MenuNavigator;
import gui.utilities.Navigator;
import gui.utilities.SelectableButton;
import gui.utilities.Selector;

/**
 *  Panel for managing the Player's items and skill class
 *
 *  @author  Nathan Lui
 *  @version Feb 4, 2016
 */
public class PlayerItemPanel extends ScreenPanel
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    public static final String[] TAB_IDENTIFIERS = { "CLASS","PRIMARY ATTACK","SECONDARY ATTACK" };
    public static final int SKILL_CLASS = 0;
    public static final int PRIMARY = 1;
    public static final int SECONDARY = 2;
    public static final int NUMCHOICES = 3;
    
    public static final int DEFAULT_WIDTH = 5;
    public static final int DEFAULT_HEIGHT = 4;
    
    private JTabbedPane tabs;
    private SelectableButton[][] selectables;
    private Navigator[] navigators;
    private int end;
    
    public PlayerItemPanel( Carder carder )
    {
        this( carder, STATS_PANEL );
    }
    public PlayerItemPanel( Carder carder, String to )
    {
        this( carder, to, DEFAULT_WIDTH, DEFAULT_HEIGHT );
    }
    
    public PlayerItemPanel( Carder frame, String to, int w, int h )
    {
        super( frame, to );
        this.setLayout( new BorderLayout() );
        
        JPanel itemSouthPanel = new ClearPanel();
        JButton backButton = new JButton( "BACK" );

        backButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e )
            {
                cancel();
            }
        } );
        this.navigator = new InterSelectorNavigator( NUMCHOICES, true );
        this.end = w*h;
        
        JPanel[] panes = new JPanel[NUMCHOICES];
        
//        Insets oldInsets = UIManager.getInsets("TabbedPane.contentBorderInsets");
        UIManager.put("TabbedPane.contentBorderInsets", new Insets(0, 0, 0, 0));
        UIManager.put("TabbedPane.contentOpaque", false);
        tabs = new JTabbedPane();
        tabs.setOpaque( false );
        tabs.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
//        UIManager.put("TabbedPane.contentBorderInsets", oldInsets); // in case prefer defaults
//        UIManager.put("TabbedPane.contentOpaque", true);
        
        Selector mainSelector = new MappedSelector();
        this.selectables = new SelectableButton[NUMCHOICES][end+1];
        this.navigators = new Navigator[NUMCHOICES];
        for ( int i = 0; i < NUMCHOICES; i++ )
        {
            String identifier = TAB_IDENTIFIERS[i];
            SelectableButton tabButton = new SelectableButton();
            tabButton.setContentAreaFilled( false );
            tabButton.setDeselectedBorder( null );
            
            JPanel pane = new ClearPanel();
            pane.setLayout( new GridLayout( h, w ) ); // row, col instead of w, h
            this.navigators[i] = new MenuNavigator( w, h );
            Selector selector = new MappedSelector();
            
            for ( int j = 0; j < end; j++ )
            {
                SelectableButton button = new SelectableButton();
                button.setActionCommand( i + "" + j );
                button.addActionListener( this );
                button.setContentAreaFilled( false );
                button.setDeselectedBorder( null );
                
                pane.add( button );
                selector.addSelectable( i + "" + j, button );
                navigators[i].addMenuItem( i + "" + j );
                selectables[i][j] = button;
            }
            
            tabs.addTab( "", pane );
            tabs.setTabComponentAt( i, tabButton );
            mainSelector.addSelectable( identifier, tabButton );
            panes[i] = pane;
            this.selectables[i][end] = tabButton;
            this.navigators[i].setSelector( selector );
            ( (InterSelectorNavigator)this.navigator ).addNavigator( identifier, navigators[i] );
        }
        navigator.setSelector( mainSelector );

        itemSouthPanel.add( backButton );
        this.add( tabs, BorderLayout.CENTER );
        this.add( itemSouthPanel, BorderLayout.SOUTH );
    }
    
    public void setMenuItems( String[][] selectables )
    {
        for ( int i = 0; i < NUMCHOICES; i++ )
        {
            for ( int j = 0; j < end; j++ )
            {
                this.selectables[i][j].setText( selectables[i][j] );
            }
        }
    }
    
    public void setItems( String skillClass, Weapon[] weapons )
    {
        setTabName( skillClass, SKILL_CLASS );
        setTabName( weapons[PRIMARY-1].getSkillClass(), PRIMARY );
        setTabName( weapons[SECONDARY-1].getSkillClass(), SECONDARY );
    }
    
    public void setTabName( String name, int identifier )
    {
        selectables[identifier][end].setText( TAB_IDENTIFIERS[identifier] + ": " + name );
    }
    
    @Override
    public void left()
    {
        super.left();
        InterSelectorNavigator n = ( (InterSelectorNavigator)navigator );
        updateTabs( n.getCurrentIndex() );
    }
    
    @Override
    public void right()
    {
        super.right();
        InterSelectorNavigator n = ( (InterSelectorNavigator)navigator );
        updateTabs( n.getCurrentIndex() );
    }
    
    @Override
    public void confirm()
    {
        super.confirm();
        InterSelectorNavigator n = ( (InterSelectorNavigator)navigator );
        if ( !n.inNavigator() )
        {
            n.enterNavigators();
        }
    }
    
    @Override
    public void cancel()
    {
        InterSelectorNavigator n = ( (InterSelectorNavigator)navigator );
        if ( n.inNavigator() )
        {
            n.exitNavigators();
        }
        else
        {
            super.cancel();
        }
    }

    @Override
    public void act( String selected )
    {
        for ( int i = 0; i < NUMCHOICES; i++ )
        {
            if ( selected.equals( TAB_IDENTIFIERS[i] ) )
            {
                updateTabs( i );
                return;
            }
        }
        if ( Character.isDigit( selected.charAt( 0 ) ) )
        {
            int pane = ( (InterSelectorNavigator)navigator ).getCurrentIndex();
            int i = Character.digit( selected.charAt( 0 ), 10 );
            int j = Character.digit( selected.charAt( 1 ), 10 );
            String s = selectables[pane][i*j].getText();
            if ( s == null || s.equals( "" ) ) return;
            setTabName( s, pane );
            updatePlayer( s, pane );
        }
    }
    private void updateTabs( int identifier )
    {
        tabs.setSelectedIndex( identifier );
    }
    private void updatePlayer( String skillClass, int pane ) // TODO update Players
    {
        switch( pane )
        {
            case SKILL_CLASS:
                break;
            case PRIMARY:
                break;
            case SECONDARY:
                break;
        }
    }

}
