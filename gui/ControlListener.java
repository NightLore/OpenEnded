package gui;

/**
 *  Interface for panels that use keyboard controls to navigate
 *
 *  @author  Nathan Lui
 *  @version Feb 3, 2016
 */
public interface ControlListener extends Navigator
{
    public static final String INITIAL = "INITIAL";
    public static final String MAINMENU = "MAINMENU";
    public static final String SETTINGS = "SETTINGS";
    public static final String LISTGAME = "LISTGAME";
    public static final String STORYGAME = "STORYGAME";
    public static final String LOADGAME = "LOADGAME";
    public static final String FREEGAME = "FREEGAME";
    // note: switch to Enums
    public static final String START_PANEL = "START";
    public static final String STATS_PANEL = "STATS";
    public static final String ITEMS_PANEL = "ITEMS";
    public static final String CTRLS_PANEL = "CTRLS";
    public static final String DONE_PANEL = "DONE";

    // note: switch to Enums
    public static final String CONFIRM = "CONFIRM";
    public static final String CANCEL = "CANCEL";
    
    public void confirm();
    public void cancel();
    
    public void act( String selected );
}
