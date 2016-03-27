package gui.game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
public class InGamePanel extends ScreenPanel
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private JLabel lifeLabel;
    private JPanel lifePanel;
    
    private Color overlayColor;
    
    private Image lifeImage;
    private GridBagConstraints constraints;
    
    private int numLives;

    public InGamePanel( Carder carder, Assets assets )
    {
        this( carder, assets, GamePanel.PAUSE_PANEL );
    }
    
    public InGamePanel( Carder carder, Assets assets, String back )
    {
        super( carder, back );
        this.setLayout( new BorderLayout() );
        JPanel gamePausePanel = new ClearPanel( new BorderLayout() );
        JButton pauseButton = new JButton( GamePanel.PAUSE_PANEL );
        JPanel westPanel = new ClearPanel();
        JPanel gameLifePanel = new JPanel( new BorderLayout() );
        lifePanel = new ClearPanel( new GridBagLayout() );
        lifeLabel = new JLabel( "LIVES" );
        overlayColor = new Color( 0, 0, 0, 128 );
        this.lifeImage = assets.getSkin( Assets.REDHEART ).getScaledInstance( 32, 32, Image.SCALE_DEFAULT );
        
        constraints = new GridBagConstraints();
        constraints.ipadx = 10;
        constraints.ipady = 10;
        
        pauseButton.setPreferredSize( new Dimension( 100, 50 ) );
        pauseButton.addActionListener( this );
        gameLifePanel.setBackground( overlayColor );
        lifeLabel.setForeground( Color.WHITE );
        lifeLabel.setBorder( BorderFactory.createEmptyBorder( 10, 10, 10, 10 ) );
        lifePanel.setBorder( BorderFactory.createEmptyBorder( 10, 10, 10, 10 ) );

        gamePausePanel.add( pauseButton, BorderLayout.EAST );
        gameLifePanel.add( lifeLabel, BorderLayout.NORTH );
        gameLifePanel.add( lifePanel, BorderLayout.CENTER );
        westPanel.add( gameLifePanel );
        this.add( gamePausePanel, BorderLayout.NORTH );
        this.add( westPanel, BorderLayout.WEST );
    }
    
    @Override
    public void confirm() {}
    
    @Override
    public void cancel() {}
    
    @Override
    public void act( String selected )
    {
        if ( selected.equals( "P" ) || selected.equals( ESCAPE ) 
          || selected.equals( BACKSPACE ) || selected.equals( GamePanel.PAUSE_PANEL ) )
        {
            back();
        }
        else if ( selected.equals( ENTER ) || selected.equals( SPACE ) )
        {
            carder.switchTo( GamePanel.ITEM_PANEL );
        }
    }

    public void update( int numLives )
    {
        for ( ; this.numLives < numLives; this.numLives++ )
        {
            addLife( this.numLives );
        }
        for ( ; this.numLives > numLives; this.numLives-- )
        {
            lifePanel.remove( this.numLives - 1 );
        }
    }
    
    /** @param i number representing this life (index) */
    private void addLife( int i )
    {
        JLabel label = new JLabel( i+1 + "" );
        label.setForeground( Color.WHITE );
        label.setIcon( new ImageIcon( lifeImage ) );
        label.setHorizontalTextPosition( JLabel.CENTER );
        
        constraints.gridx = i / 10;
        constraints.gridy = i % 10;
        lifePanel.add( label, constraints );
    }
}
