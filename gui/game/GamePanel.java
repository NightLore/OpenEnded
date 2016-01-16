package gui.game;

import game.Settings;
import gui.Carder;
import gui.SettingsScreen;

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

/**
 *  Manages the User Interface in game
 *
 *  @author  Nathan Man-ho Lui
 *  @version Dec 30, 2015
 *  @author  Assignment: OpenEnded
 *
 *  @author  Sources: none
 */
public class GamePanel extends JPanel implements Carder, ActionListener
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private CardLayout cards;
    private GameScreen game;
    private String currentPane; // note: not actually used
    private SettingsScreen settingsPanel;
    private PlayerManagerPanel itemPanel;
    private JLabel lifeLabel;
    
    public GamePanel( GameScreen game, Settings settings )
    {
        this.setOpaque( false );
        this.cards = new CardLayout();
        this.game = game;
        this.currentPane = "LOAD";
        this.setLayout( this.cards );

        JPanel loadPanel = new JPanel();
        JLabel loadLabel = new JLabel( "Loading..." );

        JPanel gamePanel = new JPanel();
            JPanel gamePausePanel = new JPanel();
            JButton pauseButton = new JButton( "Pause" );
            JPanel gameLifePanel = new JPanel();
            lifeLabel = new JLabel( "Number of Lives: " + settings.numLives );
        
        itemPanel = new PlayerManagerPanel( this, "PAUSE" );
        
        JPanel pausePanel = new JPanel();
            JButton resumeButton = new JButton ( "Resume" );
            JButton inventoryButton = new JButton( "Inventory" );
            JButton settingsButton = new JButton( "Settings" );
            JButton returnButton = new JButton( "Return to Main Menu" );

        settingsPanel = new SettingsScreen( this, "PAUSE", settings );
        
        JPanel areYouSurePanel = new JPanel();
        JLabel areYouSureLabel = new JLabel( "Are You Sure You Want To Quit?" );
            JPanel yesNoPanel = new JPanel();
            JButton yesButton = new JButton( "Yes" );
            JButton noButton = new JButton( "No" );
            
        JPanel gameOverPanel = new JPanel();
        JLabel gameOverLabel = new JLabel( "GAME OVER" );
            JPanel returnPanel = new JPanel();
            JButton mainMenuButton = new JButton( "Return to Main Menu" );

        loadPanel.setBackground( new Color( 0, 0, 0, 192 ) );
        loadLabel.setForeground( Color.WHITE );
        
        gamePanel.setLayout( new BorderLayout() );
        gamePanel.setOpaque( false );
        gamePanel.setPreferredSize( getPreferredSize() );
        gamePausePanel.setLayout( new BorderLayout() );
        gamePausePanel.setOpaque( false );
            pauseButton.setPreferredSize( new Dimension( 100, 50 ) );
            pauseButton.setActionCommand( "Pause" );
            pauseButton.addActionListener( this );
        gameLifePanel.setOpaque( false );
            lifeLabel.setForeground( Color.WHITE );

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
        
        gameOverPanel.setLayout( new BoxLayout( gameOverPanel, BoxLayout.Y_AXIS ) );
        gameOverPanel.setBackground( new Color( 0, 0, 0, 192 ) );
        gameOverLabel.setForeground( Color.WHITE );
        exitFont = gameOverLabel.getFont();
        exitFont = new Font( exitFont.getFontName(), exitFont.getStyle(), 64 );
        gameOverLabel.setFont( exitFont );
        gameOverLabel.setAlignmentX( CENTER_ALIGNMENT );
            returnPanel.setOpaque( false );
            mainMenuButton.setActionCommand( "Yes" );
            mainMenuButton.addActionListener( this );
        
        loadPanel.add( loadLabel );
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
        gamePanel.add( gameLifePanel, BorderLayout.WEST );
            gameLifePanel.add( lifeLabel );
        areYouSurePanel.add( Box.createVerticalGlue() );
        areYouSurePanel.add( areYouSureLabel );
        areYouSurePanel.add( yesNoPanel );
        areYouSurePanel.add( Box.createVerticalGlue() );
            yesNoPanel.add( yesButton );
            yesNoPanel.add( noButton );
        gameOverPanel.add( gameOverLabel );
        gameOverPanel.add( returnPanel );
            returnPanel.add( mainMenuButton );
        this.add( loadPanel, "LOAD" );
        this.add( gamePanel, "GAME" );
        this.add( itemPanel, "ITEM" );
        this.add( pausePanel, "PAUSE" );
        this.add( settingsPanel, "SETTINGS" );
        this.add( areYouSurePanel, "EXIT" );
        this.add( gameOverPanel, "OVER" );
        
        this.switchTo( "GAME" );
    }
    
    public void reset()
    {
        itemPanel.initialize( game.getGame() );
    }
    
    @Override
    public void switchTo( String to )
    {
        this.switchTo( currentPane, to );
    }
    
    public void update( int numLives )
    {
        lifeLabel.setText( "Number of Lives: " + numLives );
    }

    @Override
    public void switchTo( String from, String to )
    {
        if ( from.equals( "SETTINGS" ) ) {
            game.settingsUpdate();
            update( game.getGame().numLives() );
        }
        
        
        if ( to.equalsIgnoreCase( "Resume" ) ) {
            to = "GAME";
        }
        else if ( to.equalsIgnoreCase( "Inventory" ) ) {
            itemPanel.shown();
            to = "ITEM";
        }
        else if ( to.equalsIgnoreCase( "Settings" ) ) {
            settingsPanel.shown();
//            to = "SETTINGS";
        }
//        else if ( to.equalsIgnoreCase( "Pause" ) ) {
//            to = "PAUSE";
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
        game.inGame = to.equalsIgnoreCase( "GAME" ) || to.equalsIgnoreCase( "OVER" );
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
