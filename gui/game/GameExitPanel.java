package gui.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gui.Carder;
import gui.ClearPanel;
import gui.MenuNavigator;
import gui.ScreenPanel;

/**
 *  Panel asks whether user wants to quit the game
 *
 *  @author  Nathan Man-ho Lui
 *  @version Feb 8, 2016
 */
public class GameExitPanel extends ScreenPanel implements ActionListener
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public GameExitPanel( Carder carder )
    {
        this( carder, GamePanel.PAUSE_PANEL );
    }

    public GameExitPanel( Carder carder, String back )
    {
        super( carder, new Color( 0, 0, 0, 128 ), back );
        this.setLayout( new BoxLayout( this, BoxLayout.Y_AXIS ) );

        JLabel areYouSureLabel = new JLabel( "Are You Sure You Want To Quit?" );
        JPanel yesNoPanel = new ClearPanel();
        JButton yesButton = new JButton( "Yes" );
        JButton noButton = new JButton( "No" );

        Font exitFont = areYouSureLabel.getFont();
        exitFont = new Font( exitFont.getFontName(), exitFont.getStyle(), 64 );
        areYouSureLabel.setFont( exitFont );
        areYouSureLabel.setForeground( Color.LIGHT_GRAY );
        areYouSureLabel.setAlignmentX( CENTER_ALIGNMENT );
        yesButton.addActionListener( this );
        noButton.addActionListener( this );

        yesNoPanel.add( yesButton );
        yesNoPanel.add( noButton );
        this.add( Box.createVerticalGlue() );
        this.add( areYouSureLabel );
        this.add( yesNoPanel );
        this.add( Box.createVerticalGlue() );
        
        navigator = new MenuNavigator(2,1);
        navigator.addMenuItem( "Yes" );
        navigator.addMenuItem( "No" );
    }
    
    @Override
    public void act( String selected )
    {
        super.act( selected );
        if ( selected.equalsIgnoreCase( "YES" ) || selected.equalsIgnoreCase( "NO" ) )
        {
            carder.switchTo( selected );
        }
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        act( e.getActionCommand() );
    }

}
