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
    
    public MainMenuScreen( Carder frame )
    {
        this( frame, INITIAL );
    }
    
    public MainMenuScreen( Carder frame, String back )
    {
        super( frame, "GrassyBackground.png", back );
        Dimension buttonSize = Window.buttonSize;
        Dimension windowSize = Window.windowSize;
        Dimension filler = new Dimension( windowSize.width / 20, windowSize.height / 20 );
        
        JPanel titlePanel = new ClearPanel();
        JPanel buttonPanel = new ClearPanel();
        JLabel title = new JLabel( Window.getGameName() );
        JLabel version = new JLabel( "                " + Window.getVersion() );
        JButton playButton = new JButton( "PLAY GAME" );
        JButton stgsButton = new JButton( SETTINGS );
        JButton backButton = new JButton( "BACK" );
        JButton exitButton = new JButton( "EXIT GAME" );
        
        this.setLayout( new BorderLayout() );
        titlePanel.setLayout( new BoxLayout( titlePanel, BoxLayout.Y_AXIS ) );
        buttonPanel.setLayout( new BoxLayout( buttonPanel, BoxLayout.Y_AXIS ) );

        title.setFont( new Font( title.getFont().getFontName(), Font.BOLD, 72 ) );
        version.setFont( new Font( version.getFont().getFontName(), Font.BOLD, 20 ) );

        // -------------------- Setting Listeners ----------------------- //
        playButton.addActionListener( this );
        stgsButton.addActionListener( this );
        backButton.addActionListener( this );
        exitButton.addActionListener( this );

        // -------------------- Setting Sizes     ----------------------- //
        buttonPanel.setPreferredSize( new Dimension( windowSize.width / 4, windowSize.height / 3 ) );
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
        
        navigator = new MenuNavigator(1,4);
        navigator.addMenuItem( LISTGAME );
        navigator.addMenuItem( SETTINGS );
        navigator.addMenuItem( INITIAL );
        navigator.addMenuItem( "EXIT GAME" );
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        act( e.getActionCommand() );
    }
    @Override
    public void act( String selected )
    {
        String p = selected;
        
        if ( selected.equals( "PLAY GAME" ) )      p = LISTGAME;
        // else if ( selected.equals( SETTINGS ) )  p = SETTINGS;
        else if ( selected.equals( "BACK" ) )      p = INITIAL;
        else if ( selected.equals( "EXIT GAME" ) ) System.exit( 0 );
        
        if ( p != null ) carder.switchTo( p );
    }
}
