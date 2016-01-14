package gui.game;

import game.Game;
import gui.Carder;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
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
    private PlayerManagerPanel manager;

    private Game game;

    public PlayerUIPanel( PlayerManagerPanel manager, int panel )
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
        
        JPanel statsPanel = new JPanel();
        JPanel titlePanel = new JPanel();
        JLabel playerLabel = new JLabel( "Player " + (panel+1) );
        imageLabel = new JLabel();
        JButton backButton = new JButton( "Back" );
        playerLabel.setAlignmentY( CENTER_ALIGNMENT );
        playerLabel.setForeground( Color.WHITE );
        Font tempFont = playerLabel.getFont();
        tempFont = new Font( tempFont.getFontName(), tempFont.getStyle(), 64 );
        playerLabel.setFont( tempFont );
        backButton.addActionListener( this );
        titlePanel.setOpaque( false );
        titlePanel.add( playerLabel );
        statsPanel.setLayout( new BorderLayout() );
        statsPanel.setOpaque( false );
        statsPanel.add( titlePanel, BorderLayout.NORTH );
        statsPanel.add( imageLabel, BorderLayout.WEST ); // note: TODO update
        statsPanel.add( backButton, BorderLayout.SOUTH );
        
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
        if ( to.contains( "ADD" ) || to.equalsIgnoreCase( "STATS" ) )
        {
            if ( !game.getPlayers().hasPlayer( panel ) )
                setPlayer( game.addPlayer( panel ) );
            currentPanel = "STATS";
            isDone = false;
        }
        else if ( to.equalsIgnoreCase( "BACK" ) )
        {
            isDone = true;
            currentPanel = "DONE";
            
        }
        else if ( to.equalsIgnoreCase( "START" ) )
        {
            isDone = true;
            currentPanel = "START";
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
