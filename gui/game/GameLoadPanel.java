package gui.game;

import java.awt.Color;

import javax.swing.JLabel;

import gui.Carder;
import gui.ScreenPanel;

/**
 *  Panel meant to be shown during loading
 *
 *  @author  Nathan Man-ho Lui
 *  @version Feb 8, 2016
 */
public class GameLoadPanel extends ScreenPanel
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    public GameLoadPanel( Carder carder )
    {
        this( carder, GamePanel.GAME_PANEL );
    }

    public GameLoadPanel( Carder frame, String back )
    {
        super( frame, new Color( 0, 0, 0, 192 ), back );
        JLabel loadLabel = new JLabel( "Loading..." );

        loadLabel.setForeground( Color.WHITE );
        this.add( loadLabel );
    }

}
