package gui;

import gui.Window.Panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SettingsScreen extends ScreenPanel implements ActionListener
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private boolean debug;
    private JButton debugButton;
    public SettingsScreen( Window frame, Panel panel )
    {
        super( frame, panel );
        this.setLayout( new BorderLayout() );
        this.setBackground( Color.BLACK );
        
        JPanel titlePanel = new JPanel();
        JLabel title = new JLabel( "SETTINGS" );
        JPanel centerPanel = new JPanel();
        JPanel debugPanel = new JPanel();
        JLabel debugLabel = new JLabel( "Toggle Debug: " );
        debugButton = new JButton( "" + debug );
        JPanel backPanel = new JPanel();
        JButton backButton = new JButton( "Back" );

        titlePanel.setOpaque( false );
        title.setFont( new Font( title.getFont().getFontName(), Font.BOLD, 72 ) );
        title.setForeground( Color.WHITE );
        centerPanel.setLayout( new BoxLayout( centerPanel, BoxLayout.Y_AXIS ) );
        centerPanel.setOpaque( false );
        debugPanel.setOpaque( false );
        debugLabel.setForeground( Color.WHITE );
        debugButton.setActionCommand( "Debug" );
        debugButton.addActionListener( this );
        backPanel.setLayout( new BorderLayout() );
        backPanel.setOpaque( false );
        backButton.addActionListener( this );
        backButton.setPreferredSize( new Dimension( 100, 50 ) );
        
        titlePanel.add( title );
        centerPanel.add( debugPanel );
        debugPanel.add( debugLabel );
        debugPanel.add( debugButton );
        backPanel.add( backButton, BorderLayout.EAST );
        this.add( titlePanel, BorderLayout.NORTH );
        this.add( centerPanel, BorderLayout.CENTER );
        this.add( backPanel, BorderLayout.SOUTH );
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        String action = e.getActionCommand();
        if ( action.equals( "Back" ) )
        {
            window.switchTo( screen, Panel.MAINMENU );
        }
        else if ( action.equals( "Debug" ) )
        {
            debug = !debug;
            debugButton.setText( "" + debug );
        }
    }
}
