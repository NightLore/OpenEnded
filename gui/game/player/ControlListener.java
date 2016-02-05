package gui.game.player;

/**
 *  Interface for panels that use keyboard controls to navigate
 *
 *  @author  Nathan Lui
 *  @version Feb 3, 2016
 */
public interface ControlListener
{
    
    public static final String START_PANEL = "START";
    public static final String STATS_PANEL = "STATS";
    public static final String ITEMS_PANEL = "ITEMS";
    public static final String CTRLS_PANEL = "CTRLS";
    public static final String DONE_PANEL = "DONE";
    
    public void up();
    public void left();
    public void down();
    public void right();
    public void confirm();
    public void cancel();
}
