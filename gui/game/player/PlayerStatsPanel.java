package gui.game.player;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gui.Carder;

/**
 *  Overview panel of the player and their stats
 *
 *  @author  Nathan Man-ho Lui
 *  @version Feb 4, 2016
 */
public class PlayerStatsPanel extends PlayerPanel
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JLabel imageLabel;
    
    public PlayerStatsPanel( Carder frame, int panel )
    {
        this( frame, DONE_PANEL, panel );
    }

    public PlayerStatsPanel( Carder frame, String to, int panel )
    {
        super( frame, to );
        this.setLayout( new BorderLayout() );
        JPanel titlePanel = new JPanel();
        JLabel playerLabel = new JLabel( "Player " + (panel+1) );
        imageLabel = new JLabel();
        JPanel statSidePanel = new JPanel();
        JButton itemButton = new JButton( ITEMS_PANEL );
        JButton controlButton = new JButton( "CONTROLS" );
        JButton doneButton = new JButton( to );
        playerLabel.setAlignmentY( CENTER_ALIGNMENT );
        playerLabel.setForeground( Color.WHITE );
        Font tempFont = playerLabel.getFont();
        tempFont = new Font( tempFont.getFontName(), tempFont.getStyle(), 64 );
        playerLabel.setFont( tempFont );
        titlePanel.setOpaque( false );
        titlePanel.add( playerLabel );
        statSidePanel.setOpaque( false );
        statSidePanel.add( itemButton );
        statSidePanel.add( controlButton );
        this.setOpaque( false );
        this.add( titlePanel, BorderLayout.NORTH );
        this.add( imageLabel, BorderLayout.WEST ); // note: TODO update
        this.add( statSidePanel, BorderLayout.EAST );
        this.add( doneButton, BorderLayout.SOUTH );
        itemButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e )
            {
                carder.switchTo( ITEMS_PANEL );
            }
        } );
        controlButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e )
            {
                carder.switchTo( CTRLS_PANEL );
            }
        } );
        doneButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e )
            {
                cancel();
            }
        } );
    }
    
    public void setPlayerImage( Image i )
    {
        imageLabel.setIcon( new ImageIcon( i ) );
    }

    @Override
    public void confirm()
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void cancel()
    {
        back();
    }

}
