package gui;

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
    
    private int numPlayers;
    private int numNPCs;
    private int numEnemies;
    
    private boolean debug;
    public SettingsScreen( Cards frame, String panel, String back )
    {
        super( frame, panel );
        this.setLayout( new BorderLayout() );
        this.setBackground( Color.BLACK );
        this.back = back;
        
        loadNumbers();
        
        JPanel titlePanel = new JPanel();
        JLabel title = new JLabel( "SETTINGS" );
        JPanel centerPanel = new JPanel();
        JPanel playerPanel = new JPanel();
        JLabel playerLabel = new JLabel( "Number of Players: " );
        JSlider playerSlider = new JSlider( JSlider.HORIZONTAL, 1, 4, numPlayers );
        JLabel numPlayLabel = new JLabel( "" + numPlayers );
        JPanel npcPanel = new JPanel();
        JLabel npcLabel = new JLabel( "Number of NPCs: " );
        JSlider npcSlider = new JSlider( JSlider.HORIZONTAL, 0, 10, numNPCs );
        JLabel numNpcLabel = new JLabel( "" + numNPCs );
        JPanel enemyPanel = new JPanel();
        JLabel enemyLabel = new JLabel( "Number of Enemies: " );
        JSlider enemySlider = new JSlider( JSlider.HORIZONTAL, 0, 100, numEnemies );
        JLabel numEnmyLabel = new JLabel( "" + numEnemies );
        JPanel debugPanel = new JPanel();
        JLabel debugLabel = new JLabel( "Toggle Debug: " );
        JButton debugButton = new JButton( "" + debug );
        JPanel backPanel = new JPanel();
        JButton backButton = new JButton( "Back" );

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
        
        debugPanel.setOpaque( false );
        debugLabel.setForeground( Color.WHITE );
        backPanel.setLayout( new BorderLayout() );
        backPanel.setOpaque( false );
        backButton.addActionListener( this );
        backButton.setPreferredSize( new Dimension( 100, 50 ) );
        
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
        centerPanel.add( debugPanel );
        debugPanel.add( debugLabel );
        debugPanel.add( debugButton );
        backPanel.add( backButton, BorderLayout.WEST );
        this.add( titlePanel, BorderLayout.NORTH );
        this.add( centerPanel, BorderLayout.CENTER );
        this.add( backPanel, BorderLayout.SOUTH );

        debugButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent arg0 )
            {
                debug = !debug;
                debugButton.setText( "" + debug );
            }
        } );
        
        playerSlider.addChangeListener( new ChangeListener() {
            @Override
            public void stateChanged( ChangeEvent e )
            {
                JSlider slider = (JSlider)e.getSource();
                // if ( !slider.getValueIsAdjusting() )
                numPlayers = slider.getValue();
                numPlayLabel.setText( "" + numPlayers );
            }
        } );
        npcSlider.addChangeListener( new ChangeListener() {
            @Override
            public void stateChanged( ChangeEvent e )
            {
                JSlider slider = (JSlider)e.getSource();
                // if ( !slider.getValueIsAdjusting() )
                numNPCs = slider.getValue();
                numNpcLabel.setText( "" + numNPCs );
            }
        } );
        enemySlider.addChangeListener( new ChangeListener() {
            @Override
            public void stateChanged( ChangeEvent e )
            {
                JSlider slider = (JSlider)e.getSource();
                // if ( !slider.getValueIsAdjusting() )
                numEnemies = slider.getValue();
                numEnmyLabel.setText( "" + numEnemies );
            }
        } );
    }
    
    public void loadNumbers()
    {
        numPlayers = 1;
        numNPCs = 5;
        numEnemies = 40;
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        String action = e.getActionCommand();
        if ( action.equals( "Back" ) )
        {
            carder.switchTo( screen, back );
        }
    }
}
