package gui.game.player;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

import gui.Carder;

/**
 *  Panel for notifying when a player is done with their menu
 *
 *  @author  Nathan
 *  @version Feb 4, 2016
 */
public class PlayerDonePanel extends PlayerPanel
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    
    public PlayerDonePanel( Carder frame )
    {
        this( frame, STATS_PANEL );
    }

    public PlayerDonePanel( Carder frame, String back )
    {
        super( frame, back );
        
        JLabel doneLabel = new JLabel( DONE_PANEL );
        doneLabel.setForeground( Color.WHITE );
        Font tempFont = doneLabel.getFont();
        tempFont = new Font( tempFont.getFontName(), tempFont.getStyle(), 64 );
        doneLabel.setFont( tempFont );
        this.add( doneLabel );
    }

    @Override
    public void up() {}

    @Override
    public void left() {}

    @Override
    public void down() {}

    @Override
    public void right() {}

    @Override
    public void confirm()
    {
        back();
    }

    @Override
    public void cancel()
    {
        back();
    }

}
