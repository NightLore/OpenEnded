package gui;

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

/**
 *  Main Menu Screen
 *
 *  @author  Nathan Man-ho Lui
 *  @version Oct 28, 2015
 *  @author  Assignment: OpenEnded
 *
 *  @author  Sources: none
 */
public class MainMenuScreen extends ScreenPanel implements ActionListener
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    public MainMenuScreen( Window frame )
    {
        super( frame, "GrassyBackground.png" );
        Dimension buttonSize = frame.buttonSize;
        Dimension filler = new Dimension( frame.getWidth() / 20, frame.getHeight() / 20 );
        
        JPanel titlePanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        JLabel title = new JLabel( frame.getGameName() );
        JLabel version = new JLabel( "                " + frame.getVersion() );
        JButton playButton = new JButton( "Play Game" );
        JButton stgsButton = new JButton( "Settings" );
        JButton backButton = new JButton( "Back" );
        JButton exitButton = new JButton( "Exit Game" );
        
        this.setLayout( new BorderLayout() );
        titlePanel.setLayout( new BoxLayout( titlePanel, BoxLayout.Y_AXIS ) );
        buttonPanel.setLayout( new BoxLayout( buttonPanel, BoxLayout.Y_AXIS ) );

        titlePanel.setOpaque( false );
        buttonPanel.setOpaque( false );
        title.setFont( new Font( title.getFont().getFontName(), Font.BOLD, 72 ) );
        version.setFont( new Font( version.getFont().getFontName(), Font.BOLD, 20 ) );

        // -------------------- Setting Listeners ----------------------- //
        playButton.addActionListener( this );
        stgsButton.addActionListener( this );
        backButton.addActionListener( this );
        exitButton.addActionListener( this );

        // -------------------- Setting Sizes     ----------------------- //
        buttonPanel.setPreferredSize( new Dimension( frame.getWidth() / 4, frame.getHeight() / 3 ) );
        playButton.setPreferredSize( buttonSize );
        stgsButton.setPreferredSize( buttonSize );
        backButton.setPreferredSize( buttonSize );
        exitButton.setPreferredSize( buttonSize );

        // -------------------- Setting Alignment ----------------------- //
        title.setAlignmentX( CENTER_ALIGNMENT );
        version.setAlignmentX( LEFT_ALIGNMENT );
        playButton.setAlignmentX( CENTER_ALIGNMENT );
        stgsButton.setAlignmentX( CENTER_ALIGNMENT );
        backButton.setAlignmentX( CENTER_ALIGNMENT );
        exitButton.setAlignmentX( CENTER_ALIGNMENT );

        // -------------------- Adding Components ----------------------- //
        this.add( titlePanel, BorderLayout.NORTH );
        this.add( buttonPanel, BorderLayout.EAST );

        titlePanel.add( title );
        titlePanel.add( version );
        buttonPanel.add( Box.createVerticalGlue() );
        buttonPanel.add( playButton );
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
        String p = null;
        
        if ( e.getActionCommand().equals( "Play Game" ) )      p = "LISTGAME";
        else if ( e.getActionCommand().equals( "Load Game" ) ) p = "LOADGAME";
        else if ( e.getActionCommand().equals( "Settings" ) )  p = "SETTINGS";
        else if ( e.getActionCommand().equals( "Back" ) )      p = "INITIAL";
        else if ( e.getActionCommand().equals( "Exit Game" ) ) System.exit( 0 );
        
        if ( p != null ) carder.switchTo( p );
    }
}
