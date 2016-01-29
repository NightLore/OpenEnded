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

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
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
public class PlayerUIPanel extends JPanel implements Carder, ActionListener
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private CardLayout cardLayout;
    private int panel;
    private boolean isDone;
    JLabel imageLabel;
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
        
        JPanel addPanel = new JPanel();
        JButton addButton = new JButton( "Add Player " + (panel+1) );
        addPanel.setLayout( new BoxLayout( addPanel, BoxLayout.Y_AXIS ) );
        addPanel.setOpaque( false );
        addButton.addActionListener( this );
        addButton.setAlignmentX( CENTER_ALIGNMENT );
        addPanel.add( Box.createVerticalGlue() );
        addPanel.add( addButton );
        addPanel.add( Box.createVerticalGlue() );
        
        JPanel statsPanel = new JPanel( new BorderLayout() );
        JPanel titlePanel = new JPanel();
        JLabel playerLabel = new JLabel( "Player " + (panel+1) );
        imageLabel = new JLabel();
        JPanel statSidePanel = new JPanel();
        JButton itemButton = new JButton( "ITEM" );
        JButton controlButton = new JButton( "CONTROLS" );
        JButton doneButton = new JButton( "DONE" );
        playerLabel.setAlignmentY( CENTER_ALIGNMENT );
        playerLabel.setForeground( Color.WHITE );
        Font tempFont = playerLabel.getFont();
        tempFont = new Font( tempFont.getFontName(), tempFont.getStyle(), 64 );
        playerLabel.setFont( tempFont );
        itemButton.addActionListener( this );
        controlButton.addActionListener( this );
        doneButton.addActionListener( this );
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
        
        JPanel itemPanel = new JPanel();
        JButton atk1Button = new JButton( "Primary Attack: Mine" ); // TODO
        JButton atk2Button = new JButton( "Secondary Attack: Projectile" );
        JButton backButton = new JButton( "BACK" );
        backButton.addActionListener( this );
        itemPanel.setOpaque( false );
        itemPanel.add( atk1Button );
        itemPanel.add( atk2Button );
        itemPanel.add( backButton );
        
        GridLayout arrowLayout = new GridLayout( 0, 3 );
        GridBagConstraints c;
        JPanel space1 = new JPanel();
        space1.setOpaque( false );
        JPanel space2 = new JPanel();
        space2.setOpaque( false );
        
        JPanel controlPanel = new JPanel( new GridBagLayout() );
        JPanel movementPanel = new JPanel( arrowLayout );
        JButton upButton = new JButton( "UP" );
        JButton leftButton = new JButton( "LEFT" );
        JButton downButton = new JButton( "DOWN");
        JButton rightButton = new JButton( "RIGHT" );
        JPanel attackPanel = new JPanel();
        JButton primaryButton = new JButton();
        JButton scndaryButton = new JButton();
        JPanel confirmPanel = new JPanel();
        JButton okButton = new JButton( "OK" );
        JButton cancelButton = new JButton( "BACK" );
        controlPanel.setOpaque( false );
        movementPanel.setOpaque( false );
        attackPanel.setOpaque( false );
        confirmPanel.setOpaque( false );
        arrowLayout.setHgap( 5 );
        arrowLayout.setVgap( 5 );
        okButton.addActionListener( this );
        cancelButton.addActionListener( this );
        movementPanel.add( space1 );
        movementPanel.add( upButton );
        movementPanel.add( space2 );
        movementPanel.add( leftButton );
        movementPanel.add( downButton );
        movementPanel.add( rightButton );
        attackPanel.add( primaryButton );
        attackPanel.add( scndaryButton );
        confirmPanel.add( okButton );
        confirmPanel.add( cancelButton );
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
        c.weightx = 0.5;
        c.gridx = 3;
        c.gridy = 6;
        controlPanel.add( confirmPanel, c );
        
        JPanel donePanel = new JPanel();
        JLabel doneLabel = new JLabel( "DONE" );
        doneLabel.setForeground( Color.WHITE );
        tempFont = doneLabel.getFont();
        tempFont = new Font( tempFont.getFontName(), tempFont.getStyle(), 64 );
        doneLabel.setFont( tempFont );
        donePanel.setOpaque( false );
        donePanel.add( doneLabel );
        
        this.prevPanel = "START";
        this.currentPanel = "START";
        this.add( addPanel, "START" );
        this.add( statsPanel, "STATS" );
        this.add( itemPanel, "ITEM" );
        this.add( controlPanel, "CONTROLS" );
        this.add( donePanel, "DONE" );
    }
    
    public void setPlayer( Player player )
    {
        if ( player == null ) return;
        imageLabel.setIcon( new ImageIcon( player.getImage() ) );
    }
    
    public void setGame( Game game )
    {
        this.game = game;
    }
    
    public void removePlayer()
    {
        this.switchTo( "START" );
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
            switchTo( "START" );
        else if ( currentPanel.equalsIgnoreCase( "DONE" ) )
            switchTo( "STATS" );
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
        if ( to.contains( "ADD" ) || to.equalsIgnoreCase( "CONTROLS" ) )
        {
            if ( !game.getPlayers().hasPlayer( panel ) )
                setPlayer( game.addPlayer( panel ) );
            currentPanel = "CONTROLS";
            isDone = false;
        }
        else if ( to.equalsIgnoreCase( "STATS" ) || to.equalsIgnoreCase( "OK" ) || to.equalsIgnoreCase( "BACK" ) )
        {
            if ( !game.getPlayers().hasPlayer( panel ) )
                setPlayer( game.addPlayer( panel ) );
            isDone = false;
            currentPanel = "STATS";
        }
        else if ( to.equalsIgnoreCase( "DONE" ) )
        {
            isDone = true;
            currentPanel = "DONE";
        }
        else if ( to.equalsIgnoreCase( "START" ) )
        {
            isDone = true;
            currentPanel = "START";
        }
        else if ( to.equalsIgnoreCase( "ITEM" ) )
        {
            isDone = false;
            currentPanel = "ITEM";
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
