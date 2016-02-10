package gui;

import game.Settings;
import gui.utilities.MenuNavigator;

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

/**
 *  Settings Screen for the game
 *
 *  @author  Nathan Man-ho Lui
 *  @version Oct 28, 2015
 *  @author  Assignment: OpenEnded
 *
 *  @author  Sources: none
 */
public class SettingsScreen extends ScreenPanel
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private Settings settings;
    private Settings tempSettings;
    
    private JSlider lifeSlider, npcSlider, enemySlider;
    private JButton pFriendlyFireButton, eFriendlyFireButton, debugButton;
    private JLabel numLivesLabel, numNpcLabel, numEnmyLabel;
    
    public SettingsScreen( Carder frame, String back, Settings settings )
    {
        super( frame, Color.BLACK, back );
        this.setLayout( new BorderLayout() );
        
        this.settings = settings;
        this.tempSettings = new Settings( settings );
        
        JPanel titlePanel = new ClearPanel();
        JLabel title = new JLabel( "SETTINGS" );
        
        JPanel centerPanel = new ClearPanel();

        JPanel lifePanel = new ClearPanel();
        JLabel lifeLabel = new JLabel( "Number of Lives: " );
        lifeSlider = new JSlider( JSlider.HORIZONTAL, 0, 10, settings.numNPCs );
        numLivesLabel = new JLabel( "" + settings.numNPCs );
        
        JPanel npcPanel = new ClearPanel();
        JLabel npcLabel = new JLabel( "Number of NPCs: " );
        npcSlider = new JSlider( JSlider.HORIZONTAL, 0, 10, settings.numNPCs );
        numNpcLabel = new JLabel( "" + settings.numNPCs );
        
        JPanel enemyPanel = new ClearPanel();
        JLabel enemyLabel = new JLabel( "Number of Enemies: " );
        enemySlider = new JSlider( JSlider.HORIZONTAL, 0, 100, settings.numEnemies );
        numEnmyLabel = new JLabel( "" + settings.numEnemies );
        
        JPanel friendlyFirePanel = new ClearPanel();
        JLabel friendlyFireLabel = new JLabel( "Friendly Fire: " );
        pFriendlyFireButton = new JButton( "Player: " + toWord( settings.playerFriendlyFire ) );
        eFriendlyFireButton = new JButton ( "Enemy: " + toWord( settings.enemyFriendlyFire ) );
        
        JPanel debugPanel = new ClearPanel();
        debugButton = new JButton( "Toggle Debug: " + toWord( settings.debug ) );
        JPanel backPanel = new ClearPanel();
        JButton confirmButton = new JButton( "OK" );
        JButton cancelButton = new JButton( "CANCEL" );

        title.setFont( new Font( title.getFont().getFontName(), Font.BOLD, 72 ) );
        title.setForeground( Color.WHITE );
        centerPanel.setLayout( new BoxLayout( centerPanel, BoxLayout.Y_AXIS ) );

        lifeLabel.setForeground( Color.WHITE );
        lifeSlider.setOpaque( false );
        lifeSlider.setMajorTickSpacing( 1 );
        numLivesLabel.setForeground( Color.WHITE );
        
        npcLabel.setForeground( Color.WHITE );
        npcSlider.setOpaque( false );
        npcSlider.setMajorTickSpacing( 1 );
        numNpcLabel.setForeground( Color.WHITE );
        
        enemyLabel.setForeground( Color.WHITE );
        enemySlider.setOpaque( false );
        enemySlider.setMajorTickSpacing( 5 );
        numEnmyLabel.setForeground( Color.WHITE );
        
        friendlyFireLabel.setForeground(  Color.WHITE );
        
        confirmButton.addActionListener( this );
        confirmButton.setPreferredSize( new Dimension( 100, 50 ) );
        cancelButton.addActionListener( this );
        cancelButton.setPreferredSize( new Dimension( 100, 50 ) );
        
        titlePanel.add( title );
        centerPanel.add( lifePanel );
        lifePanel.add( lifeLabel );
        lifePanel.add( lifeSlider );
        lifePanel.add( numLivesLabel );
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

        lifeSlider.addChangeListener( new ChangeListener() {
            @Override
            public void stateChanged( ChangeEvent e )
            {
                JSlider slider = (JSlider)e.getSource();
                // if ( !slider.getValueIsAdjusting() )
                tempSettings.numLives = slider.getValue();
                numLivesLabel.setText( "" + tempSettings.numLives );
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
        
        navigator = new MenuNavigator(2,1);
        navigator.addMenuItem( "OK" );
        navigator.addMenuItem( "CANCEL" );
    }
    
    @Override
    public void shown()
    {
        this.tempSettings.copy( settings );
        debugButton.setText( "Toggle Debug: " + toWord( tempSettings.debug ) );
        pFriendlyFireButton.setText( "Player: " + toWord( tempSettings.playerFriendlyFire ) );
        eFriendlyFireButton.setText( "Enemy: " + toWord( tempSettings.enemyFriendlyFire ) );
        numLivesLabel.setText( "" + tempSettings.numLives );
        lifeSlider.setValue( tempSettings.numLives );
        numNpcLabel.setText( "" + tempSettings.numNPCs );
        npcSlider.setValue( tempSettings.numNPCs );
        numEnmyLabel.setText( "" + tempSettings.numEnemies );
        enemySlider.setValue( tempSettings.numEnemies );
    }
    
    @Override
    public void act( String selected )
    {
        check( selected );
        if ( selected.equalsIgnoreCase( "CANCEL" ) )
        {
            back();
        }
        else if ( selected.equalsIgnoreCase( "OK" ) )
        {
            settings.copy( tempSettings );
            back();
        }
    }
    
    private String toWord( boolean b )
    {
        return b ? "On" : "Off";
    }
}
