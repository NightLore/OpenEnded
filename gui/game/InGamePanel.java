package gui.game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

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

    public InGamePanel( Carder carder, int numLives )
    {
        this( carder, GamePanel.PAUSE_PANEL, numLives );
    }
    
    public InGamePanel( Carder carder, String back, int numLives )
    {
        super( carder, back );
        this.setLayout( new BorderLayout() );
        JPanel gamePausePanel = new ClearPanel( new BorderLayout() );
        JButton pauseButton = new JButton( GamePanel.PAUSE_PANEL );
        JPanel gameLifePanel = new ClearPanel();
        lifeLabel = new JLabel( "Number of Lives: " + numLives );
        
        pauseButton.setPreferredSize( new Dimension( 100, 50 ) );
        pauseButton.addActionListener( this );
        lifeLabel.setForeground( Color.WHITE );

        gamePausePanel.add( pauseButton, BorderLayout.EAST );
        gameLifePanel.add( lifeLabel );
        this.add( gamePausePanel, BorderLayout.NORTH );
        this.add( gameLifePanel, BorderLayout.WEST );
    }
    
    @Override
    public void confirm()
    {
        act( GamePanel.ITEM_PANEL );
    }
    
    @Override
    public void act( String selected )
    {
        super.act( selected );
        if ( selected.equals( "P" ) )
        {
            back();
        }
        else if ( !selected.equals( SPACE ) && !selected.equals( ENTER ) 
              && !selected.equals( ESCAPE ) && !selected.equals( BACKSPACE ) )
        {
            carder.switchTo( selected );
        }
    }

    public void update( int numLives )
    {
        lifeLabel.setText( "Number of Lives: " + numLives );
    }
}
