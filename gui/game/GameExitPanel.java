package gui.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gui.Carder;
import gui.ClearPanel;
import gui.ScreenPanel;
import gui.utilities.MappedSelector;
import gui.utilities.MenuNavigator;
import gui.utilities.SelectableButton;
import gui.utilities.Selector;

/**
 *  Panel asks whether user wants to quit the game
 *
 *  @author  Nathan Man-ho Lui
 *  @version Feb 8, 2016
 */
public class GameExitPanel extends ScreenPanel
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    public static final String YES = "YES";
    public static final String NO = "NO";

    public GameExitPanel( Carder carder )
    {
        this( carder, GamePanel.PAUSE_PANEL );
    }

    public GameExitPanel( Carder carder, String back )
    {
        super( carder, new Color( 0, 0, 0, 128 ), back );
        this.setLayout( new BoxLayout( this, BoxLayout.Y_AXIS ) );

        JPanel areYouSurePanel = new ClearPanel();
        JLabel areYouSureLabel = new JLabel( "Are You Sure You Want To Quit?" );
        JPanel yesNoPanel = new ClearPanel( new GridLayout( 1, 0, 100, 100 ) );
        SelectableButton yesButton = new SelectableButton( YES );
        SelectableButton noButton = new SelectableButton( NO );

        Font exitFont = areYouSureLabel.getFont();
        exitFont = new Font( exitFont.getFontName(), exitFont.getStyle(), 64 );
        areYouSureLabel.setFont( exitFont );
        areYouSureLabel.setForeground( Color.LIGHT_GRAY );
        
        yesNoPanel.setBorder( BorderFactory.createEmptyBorder( 100, 200, 300, 200 ) );
        yesButton.addActionListener( this );
        noButton.addActionListener( this );
        yesButton.setActionCommand( GamePanel.LOAD_PANEL );
        noButton.setActionCommand( GamePanel.PAUSE_PANEL );

        areYouSurePanel.add( areYouSureLabel );
        yesNoPanel.add( yesButton );
        yesNoPanel.add( noButton );
        this.add( Box.createVerticalGlue() );
        this.add( areYouSurePanel );
        this.add( yesNoPanel );
        this.add( Box.createVerticalGlue() );
        
        navigator = new MenuNavigator(2,1);
        navigator.addMenuItem( GamePanel.LOAD_PANEL );
        navigator.addMenuItem( GamePanel.PAUSE_PANEL );
        Selector selector = new MappedSelector();
        selector.addSelectable( GamePanel.LOAD_PANEL, yesButton );
        selector.addSelectable( GamePanel.PAUSE_PANEL, noButton );
        navigator.setSelector( selector );
    }
    
    @Override
    public void act( String selected )
    {
        if ( check( selected ) ) return;
        carder.switchTo( selected );
    }

}
