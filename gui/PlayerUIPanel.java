package gui;

import game.Game;

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
public class PlayerUIPanel extends JPanel implements Cards, ActionListener
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
//    private ArrayedCards carder;
    private CardLayout cardLayout;
    private int panel;
    private boolean isDone;
    JLabel imageLabel;
    private String currentPanel;

    private Game game;

    public PlayerUIPanel( int panel )
    {
//        this.carder = carder;
        this.cardLayout = new CardLayout();
        this.panel = panel;
        this.setLayout( cardLayout );
        this.setOpaque( false );
        this.setBorder( BorderFactory.createRaisedBevelBorder() );
        
        JPanel addPanel = new JPanel();
        JButton addButton = new JButton( "Add Player " + (panel+1) );
        addPanel.setLayout( new BoxLayout( addPanel, BoxLayout.Y_AXIS ) );
        addPanel.setOpaque( false );
        addButton.addActionListener( this );
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
//        if ( panel < players.size() )
//            imageLabel.setIcon( new ImageIcon( players.get( panel ).getImage() ) );
//        backButton.setActionCommand( backButton.getText() + (panel+1) );
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
        
        this.currentPanel = "ADD";
        this.add( addPanel, "ADD" );
        this.add( statsPanel, "STATS" );
        this.add( donePanel, "DONE" );
        
//        if ( panel < players.size() )
//        {
//            carder.switchTo( panel, "STATS" );
//        }
    }
    
    public void setPlayer( Player player )
    {
        if ( player == null ) return;
        imageLabel.setIcon( new ImageIcon( player.getImage() ) );
//        carder.switchTo( panel, "STATS" ); // TODO own actionListener & switcher
    }
    
    public void setGame( Game game )
    {
        this.game = game;
    }
    
    public void removePlayer()
    {
//        carder.switchTo( panel, "ADD" );
    }
    
    public CardLayout getCardLayout()
    {
        return cardLayout;
    }

    public boolean isDone()
    {
        return isDone;
    }

    public void setDone( boolean isDone )
    {
        this.isDone = isDone;
    }
    
    public void switchTo( String to )
    {
        switchTo( currentPanel, to );
        currentPanel = to;
    }

    @Override
    public void switchTo( String from, String to )
    {
        System.out.println( to );
        if ( to.contains( "ADD" ) )
        {
            game.addPlayer( panel );
            to = "STATS";
        }
        else if ( to.equalsIgnoreCase( "BACK" ) )
        {
            to = "DONE";
        }
        cardLayout.show( this, to );
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        this.switchTo( e.getActionCommand().toUpperCase() );
    }

}
