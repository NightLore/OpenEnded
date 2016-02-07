package gui.game.player;

import gui.Carder;
import gui.ClearPanel;
import gui.ScreenPanel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JPanel;

import sprites.Player;

/**
 *  Player panel that allows the change of controls for the associated player.
 *
 *  @author  Nathan Lui
 *  @version Feb 3, 2016
 */
public class PlayerControlPanel extends ScreenPanel
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private JButton upButton, leftButton, downButton, rightButton, primaryButton, scndaryButton;
    private Player player;
    private int[] controls;
    
    public PlayerControlPanel( Carder carder )
    {
        this( carder, STATS_PANEL );
    }
    
    public PlayerControlPanel( Carder carder, String to )
    {
        super( carder, to );
        
        this.setLayout( new GridBagLayout() );
        GridBagConstraints c;

        GridLayout arrowLayout = new GridLayout( 0, 3 );
        
        JPanel movementPanel = new ClearPanel( arrowLayout );
        upButton = new JButton();
        leftButton = new JButton();
        downButton = new JButton();
        rightButton = new JButton();
        JPanel attackPanel = new ClearPanel();
        primaryButton = new JButton();
        scndaryButton = new JButton();
        JPanel confirmPanel = new ClearPanel();
        JButton okButton = new JButton( "OK" );
        
        arrowLayout.setHgap( 5 );
        arrowLayout.setVgap( 5 );
        movementPanel.add( new ClearPanel() );
        movementPanel.add( upButton );
        movementPanel.add( new ClearPanel() );
        movementPanel.add( leftButton );
        movementPanel.add( downButton );
        movementPanel.add( rightButton );
        attackPanel.add( primaryButton );
        attackPanel.add( scndaryButton );
        confirmPanel.add( okButton );
        
        c = new GridBagConstraints();
        c.weightx = 0.5;
        c.gridx = 2;
        c.gridy = 2;
        this.add( movementPanel, c );
        c = new GridBagConstraints();
        c.weightx = 0.5;
        c.gridx = 4;
        c.gridy = 2;
        this.add( attackPanel, c );
        c = new GridBagConstraints();
        c.weightx = 0.0;
        c.gridx = 3;
        c.gridy = 6;
        this.add( confirmPanel, c );

        okButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e )
            {
                confirm();
            }
        } );
        
        upButton.setActionCommand( Player.UP + "" );
        leftButton.setActionCommand( Player.LEFT + "" );
        downButton.setActionCommand( Player.DOWN + "" );
        rightButton.setActionCommand( Player.RIGHT + "" );
        primaryButton.setActionCommand( Player.PRIMARY + "" );
        scndaryButton.setActionCommand( Player.SECONDARY + "" );
        
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e )
            {
                setControl( Integer.parseInt( e.getActionCommand() ) );
            }
        };
        upButton.addActionListener( listener );
        leftButton.addActionListener( listener );
        downButton.addActionListener( listener );
        rightButton.addActionListener( listener );
        primaryButton.addActionListener( listener );
        scndaryButton.addActionListener( listener );
    }
    
    public void setPlayer( Player player )
    {
        this.player = player;
        this.setControls( player.getControls() );
    }
    public void setControls( int[] controls )
    {
        this.controls = controls;
        upButton.setText( KeyEvent.getKeyText( controls[Player.UP] ) );
        leftButton.setText( KeyEvent.getKeyText( controls[Player.LEFT] ) );
        downButton.setText( KeyEvent.getKeyText( controls[Player.DOWN] ) );
        rightButton.setText( KeyEvent.getKeyText( controls[Player.RIGHT] ) );
        primaryButton.setText( KeyEvent.getKeyText( controls[Player.PRIMARY] ) );
        scndaryButton.setText( KeyEvent.getKeyText( controls[Player.SECONDARY] ) );
    }
    
    @Override
    public void confirm()
    {
        player.setControls( controls );
        back();
    }
    
    public void setControl( int control )
    {
        // TODO allow control change
        // controls[control] = anotherPanel.getControl();
        JButton button = null;
        switch ( control )
        {
            case Player.UP:
                button = upButton;
                break;
            case Player.LEFT:
                button = leftButton;
                break;
            case Player.DOWN:
                button = downButton;
                break;
            case Player.RIGHT:
                button = rightButton;
                break;
            case Player.PRIMARY:
                button = primaryButton;
                break;
            case Player.SECONDARY:
                button = scndaryButton;
                break;
        }
        if ( button != null )
            button.setText( KeyEvent.getKeyText( controls[control] ) );
    }

    @Override
    public void act( String selected ) {}
}
