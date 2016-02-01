package gui.game;

import game.Game;
import gui.Carder;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import sprites.Player;
import sprites.Weapon;

/**
 * Manages the User Interface for spawning in players
 *
 *  @author  Nathan Man-ho Lui
 *  @version Jan 10, 2016
 *  @author  Assignment: OpenEnded
 *
 *  @author  Sources: none
 */
public class PlayerUIPanel extends JPanel implements Carder, ActionListener
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    public static final String START_PANEL = "START";
    public static final String STATS_PANEL = "STATS";
    public static final String ITEMS_PANEL = "ITEMS";
    public static final String CTRLS_PANEL = "CTRLS";
    public static final String DONE_PANEL = "DONE";

    private CardLayout cardLayout;
    private int panel;
    private boolean isDone;
    private JLabel imageLabel;
    private JButton upButton, leftButton, downButton, rightButton, primaryButton, scndaryButton;
    private JButton classButton, atk1Button, atk2Button;
    
    private String prevPanel, currentPanel;
    private Manager manager;

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
        JPanel addPanel = new JPanel();
        JButton addButton = new JButton( "Add Player " + (panel+1) );
        addPanel.setLayout( new BoxLayout( addPanel, BoxLayout.Y_AXIS ) );
        addPanel.setOpaque( false );
        addButton.addActionListener( this );
        addButton.setAlignmentX( CENTER_ALIGNMENT );
        addPanel.add( Box.createVerticalGlue() );
        addPanel.add( addButton );
        addPanel.add( Box.createVerticalGlue() );
        
        // ------------------------ STATS PANEL ------------------------ //
        JPanel statsPanel = new JPanel( new BorderLayout() );
        JPanel titlePanel = new JPanel();
        JLabel playerLabel = new JLabel( "Player " + (panel+1) );
        imageLabel = new JLabel();
        JPanel statSidePanel = new JPanel();
        JButton itemButton = new JButton( ITEMS_PANEL );
        JButton controlButton = new JButton( "CONTROLS" );
        JButton doneButton = new JButton( DONE_PANEL );
        playerLabel.setAlignmentY( CENTER_ALIGNMENT );
        playerLabel.setForeground( Color.WHITE );
        Font tempFont = playerLabel.getFont();
        tempFont = new Font( tempFont.getFontName(), tempFont.getStyle(), 64 );
        playerLabel.setFont( tempFont );
        itemButton.addActionListener( this );
        controlButton.addActionListener( this );
        doneButton.addActionListener( this );
        controlButton.setActionCommand( CTRLS_PANEL );
        titlePanel.setOpaque( false );
        titlePanel.add( playerLabel );
        statSidePanel.setOpaque( false );
        statSidePanel.add( itemButton );
        statSidePanel.add( controlButton );
        statsPanel.setOpaque( false );
        statsPanel.add( titlePanel, BorderLayout.NORTH );
        statsPanel.add( imageLabel, BorderLayout.WEST ); // note: TODO update
        statsPanel.add( statSidePanel, BorderLayout.EAST );
        statsPanel.add( doneButton, BorderLayout.SOUTH );
        
        // ------------------ ITEM PANEL --------------------- //
        JPanel itemPanel = new JPanel( new BorderLayout() );
        JPanel itemNorthPanel = new JPanel();
        JPanel itemSouthPanel = new JPanel();
        classButton = new JButton();
        atk1Button = new JButton(); // TODO
        atk2Button = new JButton();
        JButton backButton = new JButton( "BACK" );
        backButton.addActionListener( this );
        itemPanel.setOpaque( false );
        itemNorthPanel.setOpaque( false );
        itemSouthPanel.setOpaque( false );
        itemNorthPanel.add( classButton );
        itemNorthPanel.add( atk1Button );
        itemNorthPanel.add( atk2Button );
        itemSouthPanel.add( backButton );
        itemPanel.add( itemNorthPanel, BorderLayout.NORTH );
        itemPanel.add( itemSouthPanel, BorderLayout.SOUTH );
        
        // ---------------- CONTROL PANEL -------------------- //
        GridLayout arrowLayout = new GridLayout( 0, 3 );
        GridBagConstraints c;
        JPanel space1 = new JPanel();
        space1.setOpaque( false );
        JPanel space2 = new JPanel();
        space2.setOpaque( false );
        
        JPanel controlPanel = new JPanel( new GridBagLayout() );
        JPanel movementPanel = new JPanel( arrowLayout );
        upButton = new JButton();
        leftButton = new JButton();
        downButton = new JButton();
        rightButton = new JButton();
        JPanel attackPanel = new JPanel();
        primaryButton = new JButton();
        scndaryButton = new JButton();
        JPanel confirmPanel = new JPanel();
        JButton okButton = new JButton( "OK" );
        controlPanel.setOpaque( false );
        movementPanel.setOpaque( false );
        attackPanel.setOpaque( false );
        confirmPanel.setOpaque( false );
        arrowLayout.setHgap( 5 );
        arrowLayout.setVgap( 5 );
        okButton.addActionListener( this );
        movementPanel.add( space1 );
        movementPanel.add( upButton );
        movementPanel.add( space2 );
        movementPanel.add( leftButton );
        movementPanel.add( downButton );
        movementPanel.add( rightButton );
        attackPanel.add( primaryButton );
        attackPanel.add( scndaryButton );
        confirmPanel.add( okButton );
        c = new GridBagConstraints();
        c.weightx = 0.5;
        c.gridx = 2;
        c.gridy = 2;
        controlPanel.add( movementPanel, c );
        c = new GridBagConstraints();
        c.weightx = 0.5;
        c.gridx = 4;
        c.gridy = 2;
        controlPanel.add( attackPanel, c );
        c = new GridBagConstraints();
        c.weightx = 0.0;
        c.gridx = 3;
        c.gridy = 6;
        controlPanel.add( confirmPanel, c );
        
        // ----------------------- DONE PANEL ---------------------- //
        JPanel donePanel = new JPanel();
        JLabel doneLabel = new JLabel( DONE_PANEL );
        doneLabel.setForeground( Color.WHITE );
        tempFont = doneLabel.getFont();
        tempFont = new Font( tempFont.getFontName(), tempFont.getStyle(), 64 );
        doneLabel.setFont( tempFont );
        donePanel.setOpaque( false );
        donePanel.add( doneLabel );
        
        this.prevPanel = START_PANEL;
        this.currentPanel = START_PANEL;
        this.add( addPanel, START_PANEL );
        this.add( statsPanel, STATS_PANEL );
        this.add( itemPanel, ITEMS_PANEL );
        this.add( controlPanel, CTRLS_PANEL );
        this.add( donePanel, DONE_PANEL );
    }
    
    public void setPlayer( Player player )
    {
        if ( player == null ) return;
        imageLabel.setIcon( new ImageIcon( player.getImage() ) );
        
        updateControls( player.getControls() );
        updateItems( player.getSkillClass(), player.getWeapons() );
    }
    private void updateControls( int[] controls )
    {
        upButton.setText( KeyEvent.getKeyText( controls[Player.UP] ) );
        leftButton.setText( KeyEvent.getKeyText( controls[Player.LEFT] ) );
        downButton.setText( KeyEvent.getKeyText( controls[Player.DOWN] ) );
        rightButton.setText( KeyEvent.getKeyText( controls[Player.RIGHT] ) );
        primaryButton.setText( KeyEvent.getKeyText( controls[Player.PRIMARY] ) );
        scndaryButton.setText( KeyEvent.getKeyText( controls[Player.SECONDARY] ) );
    }
    private void updateItems( String skillClass, Weapon[] weapons )
    {
        classButton.setText( "Class: " + skillClass );
        atk1Button.setText( "Primary Attack: " + weapons[0].getSkillClass() );
        atk2Button.setText( "Secondary Attack: " + weapons[1].getSkillClass() );
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

}
