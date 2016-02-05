package gui.game.player;

import game.Game;
import gui.Carder;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

import sprites.Player;

/**
 * Manages the User Interface for spawning in players
 *
 *  @author  Nathan Man-ho Lui
 *  @version Jan 10, 2016
 *  @author  Assignment: OpenEnded
 *
 *  @author  Sources: none
 */
public class PlayerUIPanel extends JPanel implements Carder, ActionListener, ControlListener
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    // EnumMaps and enums may be more appropriate
    private HashMap<String,ControlListener> navigatables = new HashMap<String,ControlListener>();
    
    private CardLayout cardLayout;
    private int panel;
    private boolean isDone;
    private PlayerControlPanel controlPanel;
    private PlayerStatsPanel statsPanel;
    private PlayerItemPanel itemPanel;
    
    private String prevPanel, currentPanel;
    private Manager manager;

    @SuppressWarnings("unused")
    private Action upAction, leftAction, downAction, rightAction, confirmAction, cancelAction;

    private Game game;

    public PlayerUIPanel( Manager manager, int panel )
    {
        this.cardLayout = new CardLayout();
        this.manager = manager;
        this.panel = panel;
        this.isDone = true;
        this.setLayout( cardLayout );
        this.setOpaque( false );
        this.setBorder( BorderFactory.createRaisedBevelBorder() );
        
        // ------------------------- ADD PANEL -------------------------- //
        JPanel addPanel = new PlayerAddPanel( this, panel );
//        JPanel addPanel = new JPanel();
//        JButton addButton = new JButton( "Add Player " + (panel+1) );
//        addPanel.setLayout( new BoxLayout( addPanel, BoxLayout.Y_AXIS ) );
//        addPanel.setOpaque( false );
//        addButton.addActionListener( this );
//        addButton.setAlignmentX( CENTER_ALIGNMENT );
//        addPanel.add( Box.createVerticalGlue() );
//        addPanel.add( addButton );
//        addPanel.add( Box.createVerticalGlue() );
        
        // ------------------------ STATS PANEL ------------------------ //
        statsPanel = new PlayerStatsPanel( this, panel );
        
        // ------------------ ITEM PANEL --------------------- //
        itemPanel = new PlayerItemPanel( this );
        
        // ---------------- CONTROL PANEL -------------------- //
        controlPanel = new PlayerControlPanel( this );
        
        // ----------------------- DONE PANEL ---------------------- //
        PlayerDonePanel donePanel = new PlayerDonePanel( this );
        
        this.prevPanel = START_PANEL;
        this.currentPanel = START_PANEL;
        this.add( addPanel, START_PANEL );
        this.add( statsPanel, STATS_PANEL );
        this.add( itemPanel, ITEMS_PANEL );
        this.add( controlPanel, CTRLS_PANEL );
        this.add( donePanel, DONE_PANEL );
        
        navigatables.put( START_PANEL, this );
        navigatables.put( STATS_PANEL, statsPanel );
        navigatables.put( ITEMS_PANEL, this );
        navigatables.put( CTRLS_PANEL, controlPanel );
        navigatables.put( DONE_PANEL, donePanel );

        confirmAction = new AbstractAction() {
            /**
             * 
             */
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed( ActionEvent e )
            {
                act( "CONFIRM" );
            }
            
        };
    }
    
    public void setPlayer( Player player )
    {
        if ( player == null ) return;
        statsPanel.setPlayerImage( player.getImage() );
        controlPanel.setPlayer( player );
        itemPanel.setItems( player.getSkillClass(), player.getWeapons() );
    }
    
    public void setGame( Game game )
    {
        this.game = game;
    }
    
    public void removePlayer()
    {
        this.switchTo( START_PANEL );
    }
    
    public CardLayout getCardLayout()
    {
        return cardLayout;
    }

    public boolean isDone()
    {
        return isDone;
    }
    
    public void reset()
    {
        if ( !game.getPlayers().hasPlayer( panel ) )
            switchTo( START_PANEL );
        else if ( currentPanel.equalsIgnoreCase( DONE_PANEL ) )
            switchTo( STATS_PANEL );
    }
    
    @Override
    public void switchTo( String to )
    {
        switchTo( prevPanel, to );
        prevPanel = to;
    }

    @Override
    public void switchTo( String from, String to )
    {
        if ( to.contains( "ADD" ) || to.equalsIgnoreCase( CTRLS_PANEL ) )
        {
            if ( !game.getPlayers().hasPlayer( panel ) )
                setPlayer( game.addPlayer( panel ) );
            currentPanel = CTRLS_PANEL;
            isDone = false;
        }
        else if ( to.equalsIgnoreCase( STATS_PANEL ) || to.equalsIgnoreCase( "OK" ) || to.equalsIgnoreCase( "BACK" ) )
        {
            if ( !game.getPlayers().hasPlayer( panel ) )
                setPlayer( game.addPlayer( panel ) );
            isDone = false;
            currentPanel = STATS_PANEL;
        }
        else if ( to.equalsIgnoreCase( DONE_PANEL ) )
        {
            isDone = true;
            currentPanel = DONE_PANEL;
        }
        else if ( to.equalsIgnoreCase( START_PANEL ) )
        {
            isDone = true;
            currentPanel = START_PANEL;
        }
        else if ( to.equalsIgnoreCase( ITEMS_PANEL ) )
        {
            isDone = false;
            currentPanel = ITEMS_PANEL;
        }
        cardLayout.show( this, currentPanel );
        manager.check();
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        this.switchTo( e.getActionCommand().toUpperCase() );
    }
    
    public void act( String action )
    {
        ControlListener navigator = navigatables.get( currentPanel );
        if ( action.equals( "UP" ) ) // note: enums?
        {
            navigator.up();
        }
        else if ( action.equals( "LEFT" ) )
        {
            navigator.left();
        }
        else if ( action.equals( "DOWN" ) )
        {
            navigator.down();
        }
        else if ( action.equals( "RIGHT" ) )
        {
            navigator.right();
        }
        else if ( action.equals( "CONFIRM" ) )
        {
            navigator.confirm();
        }
        else if ( action.equals( "CANCEL" ) )
        {
            navigator.cancel();
        }
    }

    @Override
    public void up()
    {
        
    }

    @Override
    public void left()
    {
        
    }

    @Override
    public void down()
    {
        
    }

    @Override
    public void right()
    {
        
    }

    @Override
    public void confirm()
    {
        
    }

    @Override
    public void cancel()
    {
        this.switchTo(/* currentPanel.equalsIgnoreCase( STATS_PANEL ) ? DONE_PANEL :*/ STATS_PANEL );
    }

}
