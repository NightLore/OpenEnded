package gui;

import game.Game;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Cards, ActionListener
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private CardLayout cards;
    private Game game;
    private Cards parent;
    
    public GamePanel( Cards parent, Game game )
    {
        this.setOpaque( false );
        this.cards = new CardLayout();
        this.game = game;
        this.parent = parent;
        this.setLayout( this.cards );
//        this.setPreferredSize( new Dimension( parent.getWidth(), parent.getHeight() ) );

        JPanel loadPanel = new JPanel();

        JPanel gamePanel = new JPanel();
        JPanel gamePausePanel = new JPanel();
        JButton pauseButton = new JButton( "Pause" );
        
        JPanel itemPanel = new JPanel();
        
        JPanel pausePanel = new JPanel() {
            private static final long serialVersionUID = 1L;
            @Override
            protected void paintComponent( Graphics g )
            {
                g.setColor( getBackground() );
                g.fillRect( 0, 0, getWidth(), getHeight() );
                super.paintComponent( g );
            }
        };
        JButton resumeButton = new JButton ( "Resume" );
        JButton inventoryButton = new JButton( "Inventory" );
        JButton settingsButton = new JButton( "Settings" );
        JButton returnButton = new JButton( "Return to Main Menu" );

        JPanel settingsPanel = new SettingsScreen( this, "SETTINGS", "PAUSE" );

        pausePanel.setLayout( new BoxLayout( pausePanel, BoxLayout.Y_AXIS ) );
        pausePanel.setBackground( new Color( 0, 0, 0, 20 ) );
        pausePanel.setOpaque( false );
        resumeButton.setAlignmentX( CENTER_ALIGNMENT );
        inventoryButton.setAlignmentX( CENTER_ALIGNMENT );
        settingsButton.setAlignmentX( CENTER_ALIGNMENT );
        returnButton.setAlignmentX( CENTER_ALIGNMENT );
        
        gamePanel.setLayout( new BorderLayout() );
        gamePanel.setOpaque( false );
        gamePanel.setPreferredSize( getPreferredSize() );
        gamePausePanel.setLayout( new BorderLayout() );
        gamePausePanel.setOpaque( false );
        pauseButton.setPreferredSize( new Dimension( 100, 50 ) );
        pauseButton.setActionCommand( "Pause" );
        
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
        this.add( loadPanel, "LOAD" );
        this.add( gamePanel, "GAME" );
        this.add( itemPanel, "ITEM" );
        this.add( pausePanel, "PAUSE" );
        this.add( settingsPanel, "SETTINGS" );
        
        resumeButton.addActionListener( this );
        inventoryButton.addActionListener( this );
        settingsButton.addActionListener( this );
        returnButton.addActionListener( this );
        pauseButton.addActionListener( this );
        this.switchTo( "LOAD", "GAME" );
    }

    @Override
    public void switchTo( String from, String to )
    {
        this.cards.show( this, to );
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        String action = e.getActionCommand();
        String from = "";
        if ( action.equals( "Resume" ) )
        {
            from = "PAUSE";
            action = "GAME";
            GameScreen.gameState = GameScreen.GameState.PLAYING;
        }
        else if ( action.equals( "Inventory" ) || action.equals( "Settings" ) )
        {
            from = "PAUSE";
        }
        else if ( action.equals( "Return to Main Menu" ) )
        {
            return;
        }
        else if ( action.equals( "Pause" ) )
        {
            from = "GAME";
            GameScreen.gameState = GameScreen.GameState.PAUSED;
        }
        this.switchTo( from, action.toUpperCase() );
    }
    
}
