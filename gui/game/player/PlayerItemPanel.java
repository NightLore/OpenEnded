package gui.game.player;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

import game.sprites.Weapon;
import gui.Carder;
import gui.ClearPanel;
import gui.ScreenPanel;
import gui.utilities.InterSelectorNavigator;
import gui.utilities.MappedSelector;
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
    public static final String[][] ITEMS = { { "INEPT", "ADEPT", "DIRECT" }, // UNKEMPT, REJECT
                                             { "MELEE", "SPELL", "MINES" },
                                             { "MELEE", "SPELL", "MINES" } };
    
    public static final int SKILL_CLASS = 0;
    public static final int PRIMARY = 1;
    public static final int SECONDARY = 2;
    public static final int NUMCHOICES = 3;
    
    public static final int DEFAULT_WIDTH = 5;
    public static final int DEFAULT_HEIGHT = 4;
    
    private JTabbedPane tabs;
    private SelectableButton[] tabButtons;
    
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
        InterSelectorNavigator navigate = new InterSelectorNavigator( NUMCHOICES, true ); // main navigator
        
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
        this.tabButtons = new SelectableButton[NUMCHOICES];
        for ( int i = 0; i < NUMCHOICES; i++ )
        {
            String identifier = TAB_IDENTIFIERS[i];
            
            PlayerTabbedPanel pane = new PlayerTabbedPanel( this, w, h );
            pane.replaceItems( ITEMS[i] );
            tabs.addTab( "", pane );
            
            SelectableButton tabButton = new SelectableButton();
            tabButton.setContentAreaFilled( false );
            tabButton.setDeselectedBorder( null );
            mainSelector.addSelectable( identifier, tabButton );
            tabs.setTabComponentAt( i, tabButton );
            panes[i] = pane;
            this.tabButtons[i] = tabButton;
            navigate.addNavigator( identifier, pane.getNavigator() );
        }
        navigate.setSelector( mainSelector );

        this.navigator = navigate;
        itemSouthPanel.add( backButton );
        this.add( tabs, BorderLayout.CENTER );
        this.add( itemSouthPanel, BorderLayout.SOUTH );
    }
    
//    public void setMenuItems( String[][] selectables )
    {
//        for ( int i = 0; i < NUMCHOICES; i++ )
//        {
//            for ( int j = 0; j < end; j++ )
//            {
//                this.selectables[i][j].setText( selectables[i][j] );
//            }
//        }
    }
    
    public void setItems( String skillClass, Weapon[] weapons )
    {
        setTabName( skillClass, SKILL_CLASS );
        setTabName( weapons[PRIMARY-1].getSkillClass(), PRIMARY );
        setTabName( weapons[SECONDARY-1].getSkillClass(), SECONDARY );
    }
    
    public void setTabName( String name, int identifier )
    {
        tabButtons[identifier].setText( TAB_IDENTIFIERS[identifier] + ": " + name );
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
        InterSelectorNavigator n = (InterSelectorNavigator)navigator;
        int pane = n.getCurrentIndex();
        String s = selected; // selectables[pane][i*j].getText(); // TODO
        if ( s == null || s.equals( "" ) ) return;
        setTabName( s, pane );
        updatePlayer( s, pane );
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
