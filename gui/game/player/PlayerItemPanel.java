package gui.game.player;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import sprites.Weapon;
import gui.Carder;
import gui.ClearPanel;

/**
 *  Panel for managing the Player's items and skill class
 *
 *  @author  Nathan Lui
 *  @version Feb 4, 2016
 */
public class PlayerItemPanel extends PlayerPanel
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private JButton classButton, atk1Button, atk2Button;
    
    
    public PlayerItemPanel( Carder carder )
    {
        this( carder, STATS_PANEL );
    }
    
    public PlayerItemPanel( Carder frame, String to )
    {
        super( frame, to );
        this.setLayout( new BorderLayout() );
        
        JPanel itemNorthPanel = new ClearPanel();
        JPanel itemSouthPanel = new ClearPanel();
        classButton = new JButton();
        atk1Button = new JButton(); // TODO
        atk2Button = new JButton();
        JButton backButton = new JButton( "BACK" );
        itemNorthPanel.add( classButton );
        itemNorthPanel.add( atk1Button );
        itemNorthPanel.add( atk2Button );
        itemSouthPanel.add( backButton );
        this.add( itemNorthPanel, BorderLayout.NORTH );
        this.add( itemSouthPanel, BorderLayout.SOUTH );

        backButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e )
            {
                cancel();
            }
        } );
    }
    
    public void setItems( String skillClass, Weapon[] weapons )
    {
        classButton.setText( "Class: " + skillClass );
        atk1Button.setText( "Primary Attack: " + weapons[0].getSkillClass() );
        atk2Button.setText( "Secondary Attack: " + weapons[1].getSkillClass() );
    }


    @Override
    public void up()
    {
        // TODO Auto-generated method stub

    }


    @Override
    public void left()
    {
        // TODO Auto-generated method stub

    }


    @Override
    public void down()
    {
        // TODO Auto-generated method stub

    }


    @Override
    public void right()
    {
        // TODO Auto-generated method stub

    }


    @Override
    public void confirm()
    {
        // TODO Auto-generated method stub

    }


    @Override
    public void cancel()
    {
        back();
    }

    @Override
    public void act( String selected )
    {
        // TODO Auto-generated method stub
        
    }

}
