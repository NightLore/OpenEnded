package gui;

import game.Settings;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Cards, ActionListener
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private CardLayout cards;
    private GameScreen game;
    private String currentPane; // note: not actually used
    
    public GamePanel( GameScreen game, Settings settings )
    {
        this.setOpaque( false );
        this.cards = new CardLayout();
        this.game = game;
        this.currentPane = "LOAD";
        this.setLayout( this.cards );

        JPanel loadPanel = new JPanel();

        JPanel gamePanel = new JPanel();
            JPanel gamePausePanel = new JPanel();
            JButton pauseButton = new JButton( "Pause" );
        
        JPanel itemPanel = new JPanel();
        
        JPanel pausePanel = new JPanel();
            JButton resumeButton = new JButton ( "Resume" );
            JButton inventoryButton = new JButton( "Inventory" );
            JButton settingsButton = new JButton( "Settings" );
            JButton returnButton = new JButton( "Return to Main Menu" );

        JPanel settingsPanel = new SettingsScreen( this, "SETTINGS", "PAUSE", settings );
        
        JPanel areYouSurePanel = new JPanel();
        JLabel areYouSureLabel = new JLabel( "Are You Sure You Want To Quit?" );
            JPanel yesNoPanel = new JPanel();
            JButton yesButton = new JButton( "Yes" );
            JButton noButton = new JButton( "No" );
        
        gamePanel.setLayout( new BorderLayout() );
        gamePanel.setOpaque( false );
        gamePanel.setPreferredSize( getPreferredSize() );
        gamePausePanel.setLayout( new BorderLayout() );
        gamePausePanel.setOpaque( false );
            pauseButton.setPreferredSize( new Dimension( 100, 50 ) );
            pauseButton.setActionCommand( "Pause" );
            pauseButton.addActionListener( this );

        pausePanel.setLayout( new BoxLayout( pausePanel, BoxLayout.Y_AXIS ) );
        pausePanel.setBackground( new Color( 0, 0, 0, 64 ) );
            resumeButton.setAlignmentX( CENTER_ALIGNMENT );
            inventoryButton.setAlignmentX( CENTER_ALIGNMENT );
            settingsButton.setAlignmentX( CENTER_ALIGNMENT );
            returnButton.setAlignmentX( CENTER_ALIGNMENT );

            resumeButton.addActionListener( this );
            inventoryButton.addActionListener( this );
            settingsButton.addActionListener( this );
            returnButton.addActionListener( this );
        
        areYouSurePanel.setLayout( new BoxLayout( areYouSurePanel, BoxLayout.Y_AXIS ) );
        areYouSurePanel.setBackground( new Color( 0, 0, 0, 128 ) );
        Font exitFont = areYouSureLabel.getFont();
        exitFont = new Font( exitFont.getFontName(), exitFont.getStyle(), 64 );
        areYouSureLabel.setFont( exitFont );
        areYouSureLabel.setForeground( Color.LIGHT_GRAY );
        areYouSureLabel.setAlignmentX( CENTER_ALIGNMENT );
            yesNoPanel.setOpaque( false );
            yesButton.addActionListener( this );
            noButton.addActionListener( this );
        
        settingsPanel.setBackground( new Color( 0, 0, 0, 192 ) );
        
        pausePanel.add( Box.createVerticalGlue() );
        pausePanel.add( resumeButton );
        pausePanel.add( Box.createVerticalGlue() );
        pausePanel.add( inventoryButton );
        pausePanel.add( Box.createVerticalGlue() );
        pausePanel.add( settingsButton );
        pausePanel.add( Box.createVerticalGlue() );
        pausePanel.add( returnButton );
        pausePanel.add( Box.createVerticalGlue() );
        gamePanel.add( gamePausePanel, BorderLayout.NORTH );
            gamePausePanel.add( pauseButton, BorderLayout.EAST );
        areYouSurePanel.add( Box.createVerticalGlue() );
        areYouSurePanel.add( areYouSureLabel );
        areYouSurePanel.add( yesNoPanel );
        areYouSurePanel.add( Box.createVerticalGlue() );
            yesNoPanel.add( yesButton );
            yesNoPanel.add( noButton );
        this.add( loadPanel, "LOAD" );
        this.add( gamePanel, "GAME" );
        this.add( itemPanel, "ITEM" );
        this.add( pausePanel, "PAUSE" );
        this.add( settingsPanel, "SETTINGS" );
        this.add( areYouSurePanel, "EXIT" );
        
        this.switchTo( "GAME" );
    }
    
    public void switchTo( String to )
    {
        this.switchTo( currentPane, to );
    }

    @Override
    public void switchTo( String from, String to )
    {
        if ( to.equalsIgnoreCase( "Resume" ) ) {
            to = "GAME";
        }
//        else if ( action.equalsIgnoreCase( "Inventory" ) ) {
//            action = "ITEM";
//        }
//        else if ( action.equalsIgnoreCase( "Settings" ) ) {
//            action = "SETTINGS";
//        }
//        else if ( to.equalsIgnoreCase( "Pause" ) ) {
//            action = "PAUSE";
//        }
        else if ( to.equalsIgnoreCase( "Return to Main Menu" ) ) {
            to = "EXIT";
        }
        else if ( to.equalsIgnoreCase( "Yes" ) ) {
            game.returnToMainMenu(); // TODO quit game
            return;
        }
        else if ( to.equalsIgnoreCase( "No" ) ) {
            to = "PAUSE";
        }
        game.inGame = to.equalsIgnoreCase( "GAME" );
        this.cards.show( this, to.toUpperCase() );
        currentPane = to;
    }
    
    public void togglePause()
    {
        String panel = currentPane.equalsIgnoreCase( "PAUSE" ) ? "GAME" : "PAUSE";
        switchTo( panel );
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        String action = e.getActionCommand();
        this.switchTo( action.toUpperCase() );
    }
    
}
