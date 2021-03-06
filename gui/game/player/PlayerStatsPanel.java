package gui.game.player;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import constants.PlayerPanelConstants;
import game.sprites.Player;
import gui.Carder;
import gui.ClearPanel;
import gui.ScreenPanel;
import gui.utilities.MappedSelector;
import gui.utilities.MenuNavigator;
import gui.utilities.SelectableButton;
import gui.utilities.Selector;

/**
 *  Overview panel of the player and their stats
 *
 *  @author  Nathan Man-ho Lui
 *  @version Feb 4, 2016
 */
public class PlayerStatsPanel extends ScreenPanel implements PlayerPanelConstants
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private StatDisplay statDisplay;
    
    public PlayerStatsPanel( Carder frame, int panel )
    {
        this( frame, DONE_PANEL, panel );
    }

    public PlayerStatsPanel( Carder frame, String to, int panel )
    {
        super( frame, to );
        
        this.setLayout( new BorderLayout() );
        JPanel titlePanel = new ClearPanel();
        JPanel centerPanel = new ClearPanel( new GridLayout( 1, 2 ) );
        JLabel playerLabel = new JLabel( "Player " + (panel+1) );
        statDisplay = new StatDisplay();
        JPanel sidePanel = new ClearPanel();
        SelectableButton itemButton = new SelectableButton( ITEMS_PANEL );
        SelectableButton controlButton = new SelectableButton( "CONTROLS" );
        SelectableButton doneButton = new SelectableButton( DONE_PANEL );
        
        playerLabel.setAlignmentY( CENTER_ALIGNMENT );
        playerLabel.setForeground( Color.WHITE );
        Font tempFont = playerLabel.getFont();
        tempFont = new Font( tempFont.getFontName(), tempFont.getStyle(), 64 );
        playerLabel.setFont( tempFont );
        
        titlePanel.add( playerLabel );
        statDisplay.setBorder( BorderFactory.createEmptyBorder( gapY, gapX, gapY, gapX ) );
        sidePanel.setLayout( new GridLayout( 0, 1, gapX, gapY / 2 ) );
        sidePanel.setBorder( BorderFactory.createEmptyBorder( gapY, gapX, gapY, gapX ) );
        sidePanel.add( itemButton );
        sidePanel.add( controlButton );
        sidePanel.add( doneButton );
        centerPanel.add( statDisplay );
        centerPanel.add( sidePanel );
        
        this.add( titlePanel, BorderLayout.NORTH );
        this.add( centerPanel, BorderLayout.CENTER );
        
        controlButton.setActionCommand( CTRLS_PANEL );
        itemButton.addActionListener( this );
        controlButton.addActionListener( this );
        doneButton.addActionListener( this );
        
        navigator = new MenuNavigator(1,3);
        navigator.addMenuItem( ITEMS_PANEL );
        navigator.addMenuItem( CTRLS_PANEL );
        navigator.addMenuItem( DONE_PANEL );
        Selector selector = new MappedSelector();
        selector.addSelectable( ITEMS_PANEL, itemButton );
        selector.addSelectable( CTRLS_PANEL, controlButton );
        selector.addSelectable( DONE_PANEL, doneButton );
        navigator.setSelector( selector );
    }
    
    @Override
    public void shown()
    {
        statDisplay.updateVisuals();
    }
    
    public void setPlayer( Player p )
    {
//        imageLabel.setIcon( new ImageIcon( p.getImage() ) );
        statDisplay.setSprite( p );
    }

    @Override
    public void act( String selected )
    {
        if ( selected.equals( ITEMS_PANEL ) || selected.equals( CTRLS_PANEL ) || selected.equals( DONE_PANEL ) )
        {
            carder.switchTo( selected );
        }
    }

}
