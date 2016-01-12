package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
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

import sprites.PlayerGroup;

/**
 *  This Class manages the Display of the four players in the Item screen
 *
 *  @author  Nathan Man-ho Lui
 *  @version Jan 10, 2016
 *  @author  Assignment: OpenEnded
 *
 *  @author  Sources: none
 */
public class PlayerManagerPanel extends JPanel implements ActionListener
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    public static final int MAX_PLAYERS = 4;
    
    private CardLayout[] cardLayouts;
    private JPanel[] mainPanels;
    @SuppressWarnings("unused")
    private PlayerGroup players;
    
    public PlayerManagerPanel()
    {
        this.setLayout( new GridLayout( 2, 2 ) );
        this.setBackground( new Color( 64, 64, 64 ) );
        
        cardLayouts = new CardLayout[MAX_PLAYERS];
        mainPanels = new JPanel[MAX_PLAYERS];
        JPanel topLeftPanel = new JPanel();
        JPanel topRightPanel = new JPanel();
        JPanel btmLeftPanel = new JPanel();
        JPanel btmRightPanel = new JPanel();
        
        mainPanels[0] = topLeftPanel; // note: maybe organize into for loop instead?
        mainPanels[1] = topRightPanel;
        mainPanels[2] = btmLeftPanel;
        mainPanels[3] = btmRightPanel;
    }
    
    public void initialize( PlayerGroup players )
    {
        this.players = players;
        
        for ( int i = 0; i < mainPanels.length; i++ )
        {
            JPanel panel = mainPanels[i];
            cardLayouts[i] = new CardLayout();
            panel.setLayout( cardLayouts[i] );
            panel.setOpaque( false );
            panel.setBorder( BorderFactory.createRaisedBevelBorder() );
            
            JPanel addPanel = new JPanel();
            JButton addButton = new JButton( "Add Player " + (i+1) );
            addPanel.setLayout( new BoxLayout( addPanel, BoxLayout.Y_AXIS ) );
            addPanel.setOpaque( false );
            addButton.addActionListener( this );
            addPanel.add( Box.createVerticalGlue() );
            addPanel.add( addButton );
            addPanel.add( Box.createVerticalGlue() );
            
            JPanel statsPanel = new JPanel();
            JPanel titlePanel = new JPanel();
            JLabel playerLabel = new JLabel( "Player " + (i+1) );
            JLabel imageLabel = new JLabel();
            JButton backButton = new JButton( "Back" );
            playerLabel.setAlignmentY( CENTER_ALIGNMENT );
            playerLabel.setForeground( Color.WHITE );
            Font tempFont = playerLabel.getFont();
            tempFont = new Font( tempFont.getFontName(), tempFont.getStyle(), 64 );
            playerLabel.setFont( tempFont );
            if ( i < players.size() )
                imageLabel.setIcon( new ImageIcon( players.get( i ).getImage() ) );
            backButton.setActionCommand( backButton.getText() + (i+1) );
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
            
            panel.add( addPanel, "ADD" );
            panel.add( statsPanel, "STATS" );
            panel.add( donePanel, "DONE" );
            
            this.add( panel );
            if ( i < players.size() )
            {
                switchTo( i, "STATS" );
            }
        }
    }
    
    public void addPlayer( int panel )
    {
        
    }
    
    public void switchTo( int panel, String action )
    {
        System.out.println( action );
        if ( action.contains( "ADD" ) )
        {
            addPlayer( panel );
            action = "STATS";
        }
        else if ( action.equalsIgnoreCase( "BACK" ) )
        {
            action = "DONE";
        }
        cardLayouts[panel].show( mainPanels[panel], action );
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        String action = e.getActionCommand();
        int panel;
        try {
            panel = Integer.parseInt( action.substring( action.length() - 1 ) );
        }
        catch ( Exception ex )
        {
            System.err.println( "Failed parse: " + action );
            return;
        }
        panel--;
        action = action.substring( 0, action.length() - 1 ).toUpperCase();
        this.switchTo( panel, action );
    }
}
