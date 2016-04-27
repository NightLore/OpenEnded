package gui.game.player;

import game.Game;
import game.sprites.Player;
import gui.Carder;
import gui.ControlListener;
import gui.NavigatablePanel;
import gui.ScreenPanel;

import java.awt.CardLayout;

import javax.swing.BorderFactory;

import constants.PlayerPanelConstants;

/**
 * Manages the User Interface for spawning in players
 *
 *  @author  Nathan Man-ho Lui
 *  @version Jan 10, 2016
 *  @author  Assignment: OpenEnded
 *
 *  @author  Sources: none
 */
public class PlayerUIPanel extends NavigatablePanel implements Carder, ControlListener, PlayerPanelConstants
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private int panel;
    private boolean isDone;
    private PlayerControlPanel controlPanel;
    private PlayerStatsPanel statsPanel;
    private PlayerItemPanel itemPanel;
    
    private Manager manager;

    private Game game;

    public PlayerUIPanel( Manager manager, int panel )
    {
        this.manager = manager;
        this.panel = panel;
        this.isDone = true;
        this.setBorder( BorderFactory.createRaisedBevelBorder() );
        
        ScreenPanel addPanel = new PlayerAddPanel( this, panel );
        statsPanel = new PlayerStatsPanel( this, panel );
        itemPanel = new PlayerItemPanel( this );
        controlPanel = new PlayerControlPanel( this );
        ScreenPanel donePanel = new PlayerDonePanel( this );
        
        this.currentPanel = START_PANEL;
        this.add( addPanel, START_PANEL );
        this.add( statsPanel, STATS_PANEL );
        this.add( itemPanel, ITEMS_PANEL );
        this.add( controlPanel, CTRLS_PANEL );
        this.add( donePanel, DONE_PANEL );
        
        navigatables.put( START_PANEL, addPanel );
        navigatables.put( STATS_PANEL, statsPanel );
        navigatables.put( ITEMS_PANEL, itemPanel );
        navigatables.put( CTRLS_PANEL, controlPanel );
        navigatables.put( DONE_PANEL, donePanel );

        this.putKeyAction( getDefaultKey( panel, Player.UP ), UP, upAction );
        this.putKeyAction( getDefaultKey( panel, Player.LEFT ), LEFT, leftAction );
        this.putKeyAction( getDefaultKey( panel, Player.DOWN ), DOWN, downAction );
        this.putKeyAction( getDefaultKey( panel, Player.RIGHT ), RIGHT, rightAction );
        this.putKeyAction( getDefaultKey( panel, Player.PRIMARY ), CONFIRM, confirmAction );
        this.putKeyAction( getDefaultKey( panel, Player.SECONDARY ), CANCEL, cancelAction );
    }
    private int getDefaultKey( int player, int control )
    {
        return Player.defaultCtrls[player][control];
    }
    
    public void setPlayer( Player player )
    {
        if ( player == null ) return;
        statsPanel.setPlayer( player );
        controlPanel.setPlayer( player );
        itemPanel.setPlayer( player );
    }
    
    public void setGame( Game game )
    {
        this.game = game;
        itemPanel.setGame( game );
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
            this.switchTo( START_PANEL );
        else
            this.switchTo( STATS_PANEL );
    }

    @Override
    public void switchTo( String from, String to )
    {
        if ( to.equalsIgnoreCase( CTRLS_PANEL ) )
        {
            if ( !game.getPlayers().hasPlayer( panel ) )
            {
                Player p = game.addPlayer( panel );
                if ( p == null ) 
                {
                    reset();
                    return;
                }
                setPlayer( p );
            }
            isDone = false;
        }
        else if ( to.equalsIgnoreCase( STATS_PANEL ) )
        {
            isDone = false;
        }
        else if ( to.equalsIgnoreCase( DONE_PANEL ) )
        {
            isDone = true;
            manager.check();
        }
        else if ( to.equalsIgnoreCase( START_PANEL ) )
        {
            isDone = true;
        }
        else if ( to.equalsIgnoreCase( ITEMS_PANEL ) )
        {
            isDone = false;
        }
        cardLayout.show( this, currentPanel );
    }
}
