package gui.game.player;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gui.Carder;
import gui.ClearPanel;
import gui.MenuNavigator;
import gui.ScreenPanel;

/**
 *  Overview panel of the player and their stats
 *
 *  @author  Nathan Man-ho Lui
 *  @version Feb 4, 2016
 */
public class PlayerStatsPanel extends ScreenPanel
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
        JPanel titlePanel = new ClearPanel();
        JPanel centerPanel = new ClearPanel( new GridLayout( 1, 2 ) );
        JLabel playerLabel = new JLabel( "Player " + (panel+1) );
        JPanel imagePanel = new ClearPanel();
        imageLabel = new JLabel();
        JPanel sidePanel = new ClearPanel();
        JButton itemButton = new JButton( ITEMS_PANEL );
        JButton controlButton = new JButton( "CONTROLS" );
        JButton doneButton = new JButton( to );
        
        playerLabel.setAlignmentY( CENTER_ALIGNMENT );
        playerLabel.setForeground( Color.WHITE );
        Font tempFont = playerLabel.getFont();
        tempFont = new Font( tempFont.getFontName(), tempFont.getStyle(), 64 );
        playerLabel.setFont( tempFont );
        itemButton.setAlignmentX( CENTER_ALIGNMENT );
        controlButton.setAlignmentX( CENTER_ALIGNMENT );
        
        titlePanel.add( playerLabel );
        imagePanel.add( imageLabel );
        sidePanel.setLayout( new GridLayout( 0, 1, gapX, gapY ) );
        sidePanel.setBorder( BorderFactory.createEmptyBorder( gapY, gapX, gapY, gapX ) );
        sidePanel.add( itemButton );
        sidePanel.add( controlButton );
        centerPanel.add( imagePanel );
        centerPanel.add( sidePanel );
        
        this.add( titlePanel, BorderLayout.NORTH );
        this.add( centerPanel, BorderLayout.CENTER );
        this.add( doneButton, BorderLayout.SOUTH );
        
        itemButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e )
            {
                act( ITEMS_PANEL );
            }
        } );
        controlButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e )
            {
                act( CTRLS_PANEL );
            }
        } );
        doneButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e )
            {
                back();
            }
        } );
        
        navigator = new MenuNavigator(1,2);
        navigator.addMenuItem( ITEMS_PANEL );
        navigator.addMenuItem( CTRLS_PANEL );
    }
    
    public void setPlayerImage( Image i )
    {
        imageLabel.setIcon( new ImageIcon( i ) );
    }

    @Override
    public void act( String selected )
    {
        if ( selected.equals( ITEMS_PANEL ) || selected.equals( CTRLS_PANEL ) )
        {
            carder.switchTo( selected );
        }
    }

}
