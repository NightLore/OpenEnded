package gui.game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import constants.GamePanelConstants;
import game.Assets;
import gui.Carder;
import gui.ClearPanel;
import gui.ScreenPanel;

/**
 *  Overlay panel for the game
 *
 *  @author  Nathan Man-ho Lui
 *  @version Feb 8, 2016
 */
public class InGamePanel extends ScreenPanel implements GameOverlay, GamePanelConstants
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private JLabel lifeLabel, killLabel;
    private JPanel lifePanel;
    
    private Color overlayColor;
    
    private Image lifeImage;
    private GridBagConstraints constraints;
    
    private int numLives;

    public InGamePanel( Carder carder, Assets assets )
    {
        this( carder, assets, PAUSE_PANEL );
    }
    
    public InGamePanel( Carder carder, Assets assets, String back )
    {
        super( carder, back );
        this.setLayout( new BorderLayout() );
        JPanel gamePausePanel = new ClearPanel( new BorderLayout() );
        JButton pauseButton = new JButton( PAUSE_PANEL );
        JPanel westPanel = new ClearPanel();
        JPanel gameLifePanel = new JPanel( new BorderLayout() );
        JPanel southPanel = new JPanel();
        lifePanel = new ClearPanel( new GridBagLayout() );
        lifeLabel = new JLabel( "LIVES" );
        killLabel = new JLabel();
        overlayColor = new Color( 0, 0, 0, 128 );
        this.lifeImage = assets.getSkin( Assets.REDHEART ).getScaledInstance( 32, 32, Image.SCALE_DEFAULT );
        
        // set constraints to have padding between elements
        constraints = new GridBagConstraints();
        constraints.ipadx = 10;
        constraints.ipady = 10;
        
        pauseButton.setPreferredSize( new Dimension( 100, 50 ) );
        pauseButton.addActionListener( this );
        gameLifePanel.setBackground( overlayColor );
        lifeLabel.setForeground( Color.WHITE );
        lifeLabel.setBorder( BorderFactory.createEmptyBorder( 10, 10, 10, 10 ) );
        lifePanel.setBorder( BorderFactory.createEmptyBorder( 10, 10, 10, 10 ) );
        southPanel.setBackground( overlayColor );
        killLabel.setForeground( Color.WHITE );

        gamePausePanel.add( pauseButton, BorderLayout.EAST );
        gameLifePanel.add( lifeLabel, BorderLayout.NORTH );
        gameLifePanel.add( lifePanel, BorderLayout.CENTER );
        westPanel.add( gameLifePanel );
        southPanel.add( killLabel );
        this.add( gamePausePanel, BorderLayout.NORTH );
        this.add( westPanel, BorderLayout.WEST );
        this.add( southPanel, BorderLayout.SOUTH );
    }
    
    @Override
    public void confirm() {}
    
    @Override
    public void cancel() {}
    
    @Override
    public void act( String selected )
    {
        if ( selected.equals( "P" ) || selected.equals( ESCAPE ) 
          || selected.equals( BACKSPACE ) || selected.equals( PAUSE_PANEL ) )
        {
            back();
        }
        else if ( selected.equals( ENTER ) || selected.equals( SPACE ) )
        {
            carder.switchTo( ITEM_PANEL );
        }
    }

    public void updateLives( int numLives )
    {
        // add Lives until it matches numLives
        for ( ; this.numLives < numLives; this.numLives++ )
        {
            addLife( this.numLives );
        }
        // remove lives until it matches numLives
        for ( ; this.numLives > numLives; this.numLives-- )
        {
            lifePanel.remove( this.numLives - 1 );
        }
    }
    
    public void updateKilled( int numKilled )
    {
        killLabel.setText( "Enemies Killed: " + numKilled );
    }
    
    /** @param i number representing this life (index) */
    private void addLife( int i )
    {
        JLabel label = new JLabel( i+1 + "" );
        label.setForeground( Color.WHITE );
        label.setIcon( new ImageIcon( lifeImage ) );
        label.setHorizontalTextPosition( JLabel.CENTER );
        
        // max 10 lives per column
        constraints.gridx = i / 10;
        constraints.gridy = i % 10;
        lifePanel.add( label, constraints );
    }

    @Override
    public void gameOver()
    {
        carder.switchTo( OVER_PANEL );
    }

    @Override
    public void displayText( String text )
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setCutScene( List<String> text )
    {
        // TODO Auto-generated method stub
        
    }
}
