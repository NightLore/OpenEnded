package gui.game.player;

import game.Game;
import gui.Carder;
import gui.ScreenPanel;

import java.awt.Color;
import java.awt.GridLayout;

/**
 *  This Class manages the Display of the four players in the Item screen
 *
 *  @author  Nathan Man-ho Lui
 *  @version Jan 10, 2016
 *  @author  Assignment: OpenEnded
 *
 *  @author  Sources: none
 */
public class PlayerManagerPanel extends ScreenPanel implements Manager
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    public static final int MAX_PLAYERS = 4;
    
    private PlayerUIPanel[] mainPanels;
    public PlayerManagerPanel( Carder carder, String back )
    {
        super( carder, new Color( 64, 64, 64 ), back );
        this.setLayout( new GridLayout( 2, 2 ) );
        
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
                mainPanels[i].switchTo( STATS_PANEL );
            this.add( mainPanels[i] );
        }
    }
    
    @Override
    public void shown()
    {
        this.requestFocus();
        reset();
    }
    
    @Override
    public boolean check()
    {
        for ( PlayerUIPanel p : mainPanels )
        {
            if ( !p.isDone() )
            {
                return false;
            }
        }
        back();
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
