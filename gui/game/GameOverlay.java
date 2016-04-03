package gui.game;

/**
 *  Represents an Overlay display in game
 *
 *  @author  Nathan M. Lui
 *  @version Apr 3, 2016
 */
public interface GameOverlay
{
    public void updateLives( int numLives );
    public void updateKilled( int numKilled );
    public void gameOver();
    
    public int getWidth();
    public int getHeight();
}
