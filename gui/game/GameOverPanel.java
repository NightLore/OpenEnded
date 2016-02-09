package gui.game;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gui.Carder;
import gui.ClearPanel;
import gui.ScreenPanel;

/**
 *  Panel shown once all lives are lost in the game
 *
 *  @author  Nathan Man-ho Lui
 *  @version Feb 8, 2016
 */
public class GameOverPanel extends ScreenPanel
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public GameOverPanel( Carder carder )
    {
        this( carder, "Yes" );
    }

    public GameOverPanel( Carder carder, String back )
    {
        super( carder, new Color( 0, 0, 0, 192 ), back );
        this.setLayout( new BoxLayout( this, BoxLayout.Y_AXIS ) );

        JLabel gameOverLabel = new JLabel( "GAME OVER" );
        JPanel returnPanel = new ClearPanel();
        JButton mainMenuButton = new JButton( "RETURN TO MAIN MENU" );
        
        gameOverLabel.setForeground( Color.WHITE );
        Font exitFont = gameOverLabel.getFont();
        exitFont = new Font( exitFont.getFontName(), exitFont.getStyle(), 64 );
        gameOverLabel.setFont( exitFont );
        gameOverLabel.setAlignmentX( CENTER_ALIGNMENT );
        mainMenuButton.setActionCommand( "Yes" );
        mainMenuButton.addActionListener( this );

        this.add( gameOverLabel );
        this.add( returnPanel );
        returnPanel.add( mainMenuButton );
    }
    
    @Override
    public void confirm()
    {
        back();
    }
    
    @Override
    public void act( String selected )
    {
        super.act( selected );
        if ( selected.equalsIgnoreCase( "YES" ) )
        {
            carder.switchTo( selected );
        }
    }

}
