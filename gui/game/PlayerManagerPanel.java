package gui.game;

import game.Game;
import gui.Carder;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;

/**
 *  This Class manages the Display of the four players in the Item screen
 *
 *  @author  Nathan Man-ho Lui
 *  @version Jan 10, 2016
 *  @author  Assignment: OpenEnded
 *
 *  @author  Sources: none
 */
public class PlayerManagerPanel extends JPanel
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    public static final int MAX_PLAYERS = 4;
    
    private PlayerUIPanel[] mainPanels;
    private Carder carder;
    private String back;
    public PlayerManagerPanel( Carder carder, String back )
    {
        this.carder = carder;
        this.back = back;
        this.setLayout( new GridLayout( 2, 2 ) );
        this.setBackground( new Color( 64, 64, 64 ) );
        
        mainPanels = new PlayerUIPanel[MAX_PLAYERS];
        
        for ( int i = 0; i < MAX_PLAYERS; i++ )
        {
            mainPanels[i] = new PlayerUIPanel( this, i );
        }
    }
    
    public void initialize( Game game )
    {
        for ( int i = 0; i < MAX_PLAYERS; i++ )
        {
            mainPanels[i].setGame( game );
            if ( i == 0 )
                mainPanels[i].switchTo( "STATS" );
            this.add( mainPanels[i] );
        }
    }
    
    public void shown()
    {
        reset();
    }
    
    public boolean check()
    {
        for ( PlayerUIPanel p : mainPanels )
        {
            if ( !p.isDone() )
            {
                return false;
            }
        }
        carder.switchTo( back );
        return true;
    }
    
    public void reset()
    {
        for ( PlayerUIPanel p : mainPanels )
        {
            p.reset();
        }
    }
}
