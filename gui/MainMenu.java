package gui;

import gui.Window.Panel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import static gui.Window.WINDOW_SIZE;

public class MainMenu extends ScreenPanel implements ActionListener
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    public MainMenu( Window frame )
    {
        super( frame, "GrassyBackground.png" );
        Dimension buttonSize = new Dimension( WINDOW_SIZE.width / 10, WINDOW_SIZE.height / 20 );
        Dimension filler = new Dimension( WINDOW_SIZE.width / 20, WINDOW_SIZE.height / 20 );

        JPanel titlePanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        JLabel title = new JLabel( window.getName() );
        JButton playButton = new JButton( "Play Game" );
        JButton loadButton = new JButton( "Load Game" );
        JButton stgsButton = new JButton( "Settings" );
        JButton backButton = new JButton( "Back" );
        JButton exitButton = new JButton( "Exit Game" );
        
        this.setLayout( new BorderLayout() );
        buttonPanel.setLayout( new BoxLayout( buttonPanel, BoxLayout.Y_AXIS ) );

        title.setFont( new Font( title.getFont().getFontName(), Font.BOLD, 72 ) );
        
        playButton.addActionListener( this );
        loadButton.addActionListener( this );
        stgsButton.addActionListener( this );
        backButton.addActionListener( this );
        exitButton.addActionListener( this );
        
        this.setPreferredSize( WINDOW_SIZE );
        buttonPanel.setPreferredSize( new Dimension( WINDOW_SIZE.width / 4, WINDOW_SIZE.height / 3 ) );
        playButton.setPreferredSize( buttonSize );
        loadButton.setPreferredSize( buttonSize );
        stgsButton.setPreferredSize( buttonSize );
        backButton.setPreferredSize( buttonSize );
        exitButton.setPreferredSize( buttonSize );

        title.setAlignmentX( CENTER_ALIGNMENT );
        playButton.setAlignmentX( CENTER_ALIGNMENT );
        loadButton.setAlignmentX( CENTER_ALIGNMENT );
        stgsButton.setAlignmentX( CENTER_ALIGNMENT );
        backButton.setAlignmentX( CENTER_ALIGNMENT );
        exitButton.setAlignmentX( CENTER_ALIGNMENT );

        this.add( titlePanel, BorderLayout.NORTH );
        this.add( buttonPanel, BorderLayout.EAST );

        titlePanel.add( title );
        buttonPanel.add( Box.createVerticalGlue() );
        buttonPanel.add( playButton );
        buttonPanel.add( Box.createRigidArea( filler ) );
        buttonPanel.add( loadButton );
        buttonPanel.add( Box.createRigidArea( filler ) );
        buttonPanel.add( stgsButton );
        buttonPanel.add( Box.createRigidArea( filler ) );
        buttonPanel.add( backButton );
        buttonPanel.add( Box.createRigidArea( filler ) );
        buttonPanel.add( exitButton );
        buttonPanel.add( Box.createRigidArea( filler ) );
        buttonPanel.add( Box.createVerticalGlue() );
    }

    @Override
    public void shown()
    {
        
    }

    @Override
    public void cover()
    {
        
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        if ( e.getActionCommand().equals( "Play Game" ) )
        {
            window.switchTo( this, Panel.GAME );
        }
        else if ( e.getActionCommand().equals( "Load Game" ) )
        {
            
        }
        else if ( e.getActionCommand().equals( "Settings" ) )
        {
            
        }
        else if ( e.getActionCommand().equals( "Back" ) )
        {
            window.switchTo( this, Panel.INIT );
        }
        else if ( e.getActionCommand().equals( "Exit Game" ) )
        {
            System.exit( 0 );
        }
    }
}
