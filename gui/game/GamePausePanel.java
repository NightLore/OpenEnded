package gui.game;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import gui.Carder;
import gui.MenuNavigator;
import gui.ScreenPanel;

/**
 *  Panel shown when game is paused
 *
 *  @author  Nathan Man-ho Lui
 *  @version Feb 8, 2016
 */
public class GamePausePanel extends ScreenPanel implements ActionListener
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    public GamePausePanel( Carder carder )
    {
        this( carder, GamePanel.GAME_PANEL );
    }

    public GamePausePanel( Carder carder, String back )
    {
        super( carder, new Color( 0, 0, 0, 64 ), back );
        int marginX = 8 * gapX;
        int marginY = 2 * gapY;
        
        this.setLayout( new GridLayout( 0, 1, 0, marginY ) );
        this.setBorder( BorderFactory.createEmptyBorder( marginY, marginX, 3 * marginY / 2, marginX) );
        
        JButton resumeButton = new JButton ( "RESUME" );
        JButton inventoryButton = new JButton( "INVENTORY" );
        JButton settingsButton = new JButton( GamePanel.SETTINGS_PANEL );
        JButton returnButton = new JButton( "RETURN TO MAIN MENU" );

        resumeButton.addActionListener( this );
        inventoryButton.addActionListener( this );
        settingsButton.addActionListener( this );
        returnButton.addActionListener( this );
        
        this.add( resumeButton );
        this.add( inventoryButton );
        this.add( settingsButton );
        this.add( returnButton );
        
        navigator = new MenuNavigator(1,4);
        navigator.addMenuItem( GamePanel.GAME_PANEL );
        navigator.addMenuItem( GamePanel.ITEM_PANEL );
        navigator.addMenuItem( GamePanel.SETTINGS_PANEL );
        navigator.addMenuItem( GamePanel.EXIT_PANEL );
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

    @Override
    public void actionPerformed( ActionEvent e )
    {
        act( e.getActionCommand() );
    }

}
