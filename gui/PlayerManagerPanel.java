package gui;

import game.Game;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;

import sprites.PlayerGroup;

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
    
    public PlayerManagerPanel()
    {
        this.setLayout( new GridLayout( 2, 2 ) );
        this.setBackground( new Color( 64, 64, 64 ) );
        
        mainPanels = new PlayerUIPanel[MAX_PLAYERS];
        
        for ( int i = 0; i < MAX_PLAYERS; i++ )
        {
            mainPanels[i] = new PlayerUIPanel( i );
        }
    }
    
    public void initialize( Game game )
    {
        PlayerGroup players = game.getPlayers();
        
        for ( int i = 0; i < MAX_PLAYERS; i++ )
        {
            mainPanels[i].setPlayer( players.get( i ) );
            mainPanels[i].setGame( game );
            this.add( mainPanels[i] );
        }
    }
}
