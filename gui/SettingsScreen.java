package gui;

import game.Settings;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SettingsScreen extends ScreenPanel implements ActionListener
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String back;
    
    private Settings settings;
    private Settings tempSettings;
    
    private JSlider playerSlider, npcSlider, enemySlider;
    private JButton pFriendlyFireButton, eFriendlyFireButton, debugButton;
    private JLabel numPlayLabel, numNpcLabel, numEnmyLabel;
    
    public SettingsScreen( Cards frame, String panel, String back, Settings settings )
    {
        super( frame, panel );
        this.setLayout( new BorderLayout() );
        this.back = back;
        
        this.settings = settings;
        this.tempSettings = new Settings( settings );
        
        JPanel titlePanel = new JPanel();
        JLabel title = new JLabel( "SETTINGS" );
        
        JPanel centerPanel = new JPanel();
        
        JPanel playerPanel = new JPanel();
        JLabel playerLabel = new JLabel( "Number of Players: " );
        playerSlider = new JSlider( JSlider.HORIZONTAL, 1, 4, settings.numPlayers );
        numPlayLabel = new JLabel( "" + settings.numPlayers );
        
        JPanel npcPanel = new JPanel();
        JLabel npcLabel = new JLabel( "Number of NPCs: " );
        npcSlider = new JSlider( JSlider.HORIZONTAL, 0, 10, settings.numNPCs );
        numNpcLabel = new JLabel( "" + settings.numNPCs );
        
        JPanel enemyPanel = new JPanel();
        JLabel enemyLabel = new JLabel( "Number of Enemies: " );
        enemySlider = new JSlider( JSlider.HORIZONTAL, 0, 100, settings.numEnemies );
        numEnmyLabel = new JLabel( "" + settings.numEnemies );
        
        JPanel friendlyFirePanel = new JPanel();
        JLabel friendlyFireLabel = new JLabel( "Friendly Fire: " );
        pFriendlyFireButton = new JButton( "Player: " + toWord( settings.playerFriendlyFire ) );
        eFriendlyFireButton = new JButton ( "Enemy: " + toWord( settings.enemyFriendlyFire ) );
        
        JPanel debugPanel = new JPanel();
        debugButton = new JButton( "Toggle Debug: " + toWord( settings.debug ) );
        JPanel backPanel = new JPanel();
        JButton confirmButton = new JButton( "Ok" );
        JButton cancelButton = new JButton( "Cancel" );

        titlePanel.setOpaque( false );
        title.setFont( new Font( title.getFont().getFontName(), Font.BOLD, 72 ) );
        title.setForeground( Color.WHITE );
        centerPanel.setLayout( new BoxLayout( centerPanel, BoxLayout.Y_AXIS ) );
        centerPanel.setOpaque( false );
        
        playerPanel.setOpaque( false );
        playerLabel.setForeground( Color.WHITE );
        playerSlider.setOpaque( false );
        playerSlider.setMajorTickSpacing( 1 );
        numPlayLabel.setForeground( Color.WHITE );
        
        npcPanel.setOpaque( false );
        npcLabel.setForeground( Color.WHITE );
        npcSlider.setOpaque( false );
        npcSlider.setMajorTickSpacing( 1 );
        numNpcLabel.setForeground( Color.WHITE );
        
        enemyPanel.setOpaque( false );
        enemyLabel.setForeground( Color.WHITE );
        enemySlider.setOpaque( false );
        enemySlider.setMajorTickSpacing( 5 );
        numEnmyLabel.setForeground( Color.WHITE );
        
        friendlyFirePanel.setOpaque( false );
        friendlyFireLabel.setForeground(  Color.WHITE );
        
        debugPanel.setOpaque( false );
        backPanel.setOpaque( false );
        confirmButton.addActionListener( this );
        confirmButton.setPreferredSize( new Dimension( 100, 50 ) );
        cancelButton.addActionListener( this );
        cancelButton.setPreferredSize( new Dimension( 100, 50 ) );
        
        titlePanel.add( title );
        centerPanel.add( playerPanel );
        playerPanel.add( playerLabel );
        playerPanel.add( playerSlider );
        playerPanel.add( numPlayLabel );
        centerPanel.add( npcPanel );
        npcPanel.add( npcLabel );
        npcPanel.add( npcSlider );
        npcPanel.add( numNpcLabel );
        centerPanel.add( enemyPanel );
        enemyPanel.add( enemyLabel );
        enemyPanel.add( enemySlider );
        enemyPanel.add( numEnmyLabel );
        centerPanel.add( friendlyFirePanel );
        friendlyFirePanel.add( friendlyFireLabel );
        friendlyFirePanel.add( pFriendlyFireButton );
        friendlyFirePanel.add( eFriendlyFireButton );
        centerPanel.add( debugPanel );
        debugPanel.add( debugButton );
        backPanel.add( confirmButton );
        backPanel.add( cancelButton );
        this.add( titlePanel, BorderLayout.NORTH );
        this.add( centerPanel, BorderLayout.CENTER );
        this.add( backPanel, BorderLayout.SOUTH );

        debugButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent arg0 )
            {
                tempSettings.debug = !tempSettings.debug;
                debugButton.setText( "Toggle Debug: " + toWord( tempSettings.debug ) );
            }
        } );
        pFriendlyFireButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent arg0 )
            {
                tempSettings.playerFriendlyFire = !tempSettings.playerFriendlyFire;
                pFriendlyFireButton.setText( "Player: " + toWord( tempSettings.playerFriendlyFire ) );
            }
        } );
        eFriendlyFireButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent arg0 )
            {
                tempSettings.enemyFriendlyFire = !tempSettings.enemyFriendlyFire;
                eFriendlyFireButton.setText( "Enemy: " + toWord( tempSettings.enemyFriendlyFire ) );
            }
        } );
        
        playerSlider.addChangeListener( new ChangeListener() {
            @Override
            public void stateChanged( ChangeEvent e )
            {
                JSlider slider = (JSlider)e.getSource();
                // if ( !slider.getValueIsAdjusting() )
                tempSettings.numPlayers = slider.getValue();
                numPlayLabel.setText( "" + tempSettings.numPlayers );
            }
        } );
        npcSlider.addChangeListener( new ChangeListener() {
            @Override
            public void stateChanged( ChangeEvent e )
            {
                JSlider slider = (JSlider)e.getSource();
                // if ( !slider.getValueIsAdjusting() )
                tempSettings.numNPCs = slider.getValue();
                numNpcLabel.setText( "" + tempSettings.numNPCs );
            }
        } );
        enemySlider.addChangeListener( new ChangeListener() {
            @Override
            public void stateChanged( ChangeEvent e )
            {
                JSlider slider = (JSlider)e.getSource();
                // if ( !slider.getValueIsAdjusting() )
                tempSettings.numEnemies = slider.getValue();
                numEnmyLabel.setText( "" + tempSettings.numEnemies );
            }
        } );
    }
    
    @Override
    public void shown()
    {
        this.tempSettings.copy( settings );
        debugButton.setText( "Toggle Debug: " + toWord( tempSettings.debug ) );
        pFriendlyFireButton.setText( "Player: " + toWord( tempSettings.playerFriendlyFire ) );
        eFriendlyFireButton.setText( "Enemy: " + toWord( tempSettings.enemyFriendlyFire ) );
        numPlayLabel.setText( "" + tempSettings.numPlayers );
        numNpcLabel.setText( "" + tempSettings.numNPCs );
        numEnmyLabel.setText( "" + tempSettings.numEnemies );
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        String action = e.getActionCommand();
        if ( action.equals( "Cancel" ) )
        {
            carder.switchTo( screen, back );
        }
        else if ( action.equals( "Ok" ) )
        {
            settings.copy( tempSettings );
            carder.switchTo( screen, back );
        }
    }
    
    private String toWord( boolean b )
    {
        return b ? "On" : "Off";
    }
}
