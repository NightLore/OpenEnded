package gui.game.player;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;

import gui.Carder;
import gui.ScreenPanel;

/**
 *  Panel for adding a Player
 *
 *  @author  Nathan Lui
 *  @version Feb 4, 2016
 */
public class PlayerAddPanel extends ScreenPanel
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;


    public PlayerAddPanel( Carder carder, int panel )
    {
        this( carder, CTRLS_PANEL, panel );
    }
    

    public PlayerAddPanel( Carder frame, String to, int panel )
    {
        super( frame, to );
        JButton addButton = new JButton( "Add Player " + (panel+1) );
        this.setLayout( new BoxLayout( this, BoxLayout.Y_AXIS ) );
        addButton.setAlignmentX( CENTER_ALIGNMENT );
        this.add( Box.createVerticalGlue() );
        this.add( addButton );
        this.add( Box.createVerticalGlue() );
        
        addButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e )
            {
                confirm();
            }
        } );
    }

    @Override
    public void confirm()
    {
        back();
    }

    @Override
    public void cancel() {}

    @Override
    public void act( String selected ) {}
}
