package gui.game;

import game.Settings;
import gui.ClearPanel;
import gui.NavigatablePanel;
import gui.SettingsScreen;
import gui.game.player.PlayerManagerPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

/**
 *  Manages the User Interface in game
 *
 *  @author  Nathan Man-ho Lui
 *  @version Dec 30, 2015
 *  @author  Assignment: OpenEnded
 *
 *  @author  Sources: none
 */
public class GamePanel extends NavigatablePanel implements ActionListener
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    public static final String LOAD_PANEL = "LOAD";
    public static final String GAME_PANEL = "GAME";
    public static final String ITEM_PANEL = "ITEM";
    public static final String PAUSE_PANEL = "PAUSE";
    public static final String SETTINGS_PANEL = "SETTINGS";
    public static final String EXIT_PANEL = "EXIT";
    public static final String OVER_PANEL = "OVER";
    
    private GameScreen game;
    private SettingsScreen settingsPanel;
    private PlayerManagerPanel itemPanel;
    private JLabel lifeLabel;
    
    private Action pauseAction, itemsAction;
    
    public GamePanel( GameScreen game, Settings settings )
    {
        this.game = game;
        this.currentPanel = LOAD_PANEL;

        JPanel loadPanel = new JPanel();
        JLabel loadLabel = new JLabel( "Loading..." );

        JPanel gamePanel = new ClearPanel( new BorderLayout() );
            JPanel gamePausePanel = new ClearPanel( new BorderLayout() );
            JButton pauseButton = new JButton( PAUSE_PANEL );
            JPanel gameLifePanel = new ClearPanel();
            lifeLabel = new JLabel( "Number of Lives: " + settings.numLives );
        
        itemPanel = new PlayerManagerPanel( this, PAUSE_PANEL );
        
        JPanel pausePanel = new JPanel();
            JButton resumeButton = new JButton ( "RESUME" );
            JButton inventoryButton = new JButton( "INVENTORY" );
            JButton settingsButton = new JButton( SETTINGS_PANEL );
            JButton returnButton = new JButton( "RETURN TO MAIN MENU" );

        settingsPanel = new SettingsScreen( this, PAUSE_PANEL, settings );
        
        JPanel areYouSurePanel = new JPanel();
        JLabel areYouSureLabel = new JLabel( "Are You Sure You Want To Quit?" );
            JPanel yesNoPanel = new ClearPanel();
            JButton yesButton = new JButton( "Yes" );
            JButton noButton = new JButton( "No" );
            
        JPanel gameOverPanel = new JPanel();
        JLabel gameOverLabel = new JLabel( "GAME OVER" );
            JPanel returnPanel = new ClearPanel();
            JButton mainMenuButton = new JButton( "RETURN TO MAIN MENU" );

        loadPanel.setBackground( new Color( 0, 0, 0, 192 ) );
        loadLabel.setForeground( Color.WHITE );
        
        gamePanel.setPreferredSize( getPreferredSize() );
            pauseButton.setPreferredSize( new Dimension( 100, 50 ) );
            pauseButton.addActionListener( this );
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
            
        this.add( loadPanel, LOAD_PANEL );
        this.add( gamePanel, GAME_PANEL );
        this.add( itemPanel, ITEM_PANEL );
        this.add( pausePanel, PAUSE_PANEL );
        this.add( settingsPanel, SETTINGS_PANEL );
        this.add( areYouSurePanel, EXIT_PANEL );
        this.add( gameOverPanel, OVER_PANEL );
        
//        navigatables.put( LOAD_PANEL, loadPanel );
//        navigatables.put( GAME_PANEL, gamePanel );
        navigatables.put( ITEM_PANEL, itemPanel );
//        navigatables.put( PAUSE_PANEL, pausePanel );
//        navigatables.put( SETTINGS_PANEL, SETTINGS_PANEL );
//        navigatables.put( EXIT_PANEL, areYouSurePanel );
//        navigatables.put( OVER_PANEL, gameOverPanel );
        
        this.switchTo( GAME_PANEL );
        
        pauseAction = new AbstractAction() {
            
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed( ActionEvent arg0 )
            {
                togglePause();
            }
        };
        itemsAction = new AbstractAction() {
            
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed( ActionEvent arg0 )
            {
                switchTo( ITEM_PANEL );
            }
        };
        this.getInputMap( WHEN_IN_FOCUSED_WINDOW ).put( KeyStroke.getKeyStroke( KeyEvent.VK_P, 0 ), PAUSE_PANEL );
        this.getInputMap( WHEN_IN_FOCUSED_WINDOW ).put( KeyStroke.getKeyStroke( KeyEvent.VK_BACK_SPACE, 0 ), PAUSE_PANEL );
        this.getInputMap( WHEN_IN_FOCUSED_WINDOW ).put( KeyStroke.getKeyStroke( KeyEvent.VK_ESCAPE, 0 ), PAUSE_PANEL );
        this.getInputMap( WHEN_IN_FOCUSED_WINDOW ).put( KeyStroke.getKeyStroke( KeyEvent.VK_ENTER, 0 ), ITEM_PANEL );
        this.getInputMap( WHEN_IN_FOCUSED_WINDOW ).put( KeyStroke.getKeyStroke( KeyEvent.VK_SPACE, 0 ), ITEM_PANEL );
        this.getActionMap().put( PAUSE_PANEL, pauseAction );
        this.getActionMap().put( ITEM_PANEL, itemsAction );
    }
    
    public void reset()
    {
        itemPanel.initialize( game.getGame() );
    }
    
    public void update( int numLives )
    {
        lifeLabel.setText( "Number of Lives: " + numLives );
    }

    @Override
    public void switchTo( String from, String to )
    {
        if ( from.equals( SETTINGS_PANEL ) ) {
            game.settingsUpdate();
            update( game.getGame().numLives() );
        }
        
        
        if ( to.equalsIgnoreCase( "RESUME" ) ) {
            to = GAME_PANEL;
        }
        else if ( to.equalsIgnoreCase( "INVENTORY" ) || to.equalsIgnoreCase( ITEM_PANEL ) ) {
            itemPanel.shown();
            to = ITEM_PANEL;
        }
        else if ( to.equalsIgnoreCase( SETTINGS_PANEL ) ) {
            settingsPanel.shown();
//            to = SETTINGS_PANEL;
        }
//        else if ( to.equalsIgnoreCase( PAUSE_PANEL ) ) {
//            to = PAUSE_PANEL;
//        }
        else if ( to.equalsIgnoreCase( "Return to Main Menu" ) ) {
            to = EXIT_PANEL;
        }
        else if ( to.equalsIgnoreCase( "Yes" ) ) {
            game.returnToMainMenu(); // TODO quit game
            return;
        }
        else if ( to.equalsIgnoreCase( "No" ) ) {
            to = PAUSE_PANEL;
        }
        game.inGame = to.equalsIgnoreCase( GAME_PANEL ) || to.equalsIgnoreCase( OVER_PANEL );
        this.cardLayout.show( this, to.toUpperCase() );
    }
    
    public void togglePause()
    {
        String panel = currentPanel.equalsIgnoreCase( PAUSE_PANEL ) ? GAME_PANEL : PAUSE_PANEL;
        switchTo( panel );
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        this.switchTo( e.getActionCommand().toUpperCase() );
    }
    
}
