package sprites.groups;

import java.awt.Point;
import java.io.Serializable;

import sprites.Player;

/**
 *  Manages the group of Players
 *
 *  @author  Nathan Man-ho Lui
 *  @version Jan 11, 2016
 *  @author  Assignment: OpenEnded
 *
 *  @author  Sources: none
 */
public class PlayerGroup implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private Player[] players;
    private int numPlayers;
    
    public PlayerGroup( int maxPlayers )
    {
        players = new Player[maxPlayers];
    }

    
    public Point getCenter()
    {
        if ( numPlayers > 0 ) {
            int x = 0;
            int y = 0;
            for ( Player p : players ) {
                if ( p != null && !p.isDead() )
                {
                    x += p.getX();
                    y += p.getY();
                }
            }
            x = Math.round( x / numPlayers );
            y = Math.round( y / numPlayers );
            return new Point( x, y );
        }
        return null;
    }
    
    public Player set( Player player, int panel )
    {
        Player oldPlayer = this.get( panel );
        players[panel] = player;
        if ( oldPlayer == null )
            numPlayers++; // TODO
        return oldPlayer;
    }
    
    public Player get( int panel )
    {
        return players[panel]; // TODO
    }
    
    public boolean remove( Player player )
    {
        for ( int i = 0; i < players.length; i++ )
        {
            if ( players[i] == player )
            {
                this.remove( i );
                return true;
            }
        }
        return false;
    }
    
    public Player remove( int panel )
    {
        Player oldPlayer = this.get( panel );
        players[panel] = null;
        if ( oldPlayer != null )
            numPlayers--;
        return oldPlayer; // TODO
    }
    
    public boolean hasPlayer( int panel )
    {
        return players[panel] != null;
    }
    
    /** WARNING Does not create a cloned array so do not modify */
    public Player[] toArray()
    {
        return players;
    }
    
    public int numPlayers()
    {
        return numPlayers;
    }
    
    public int maxPlayers()
    {
        return players.length;
    }
}
