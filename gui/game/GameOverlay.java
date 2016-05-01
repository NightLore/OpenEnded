package gui.game;

import java.util.List;

/**
 *  Represents an Overlay display in game
 *
 *  @author  Nathan M. Lui
 *  @version Apr 3, 2016
 */
public interface GameOverlay
{
    public void displayText( String text );
    public void setCutScene( List<String> text );
    
    public void updateLives( int numLives );
    public void updateKilled( int numKilled );
    public void gameOver();
    
    public int getWidth();
    public int getHeight();
}
