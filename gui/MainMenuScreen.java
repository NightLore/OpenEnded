package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
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
        this.setLayout( new BorderLayout() );
        
        GridLayout gridLayout = new GridLayout( 0, 1 );
        gridLayout.setVgap( gapY );
        
        JPanel titlePanel = new ClearPanel();
        JPanel buttonPanel = new ClearPanel();
        JLabel title = new JLabel( Window.getGameName() );
        JLabel version = new JLabel( "                " + Window.getVersion() );
        JButton playButton = new JButton( "PLAY GAME" );
        JButton stgsButton = new JButton( SETTINGS );
        JButton backButton = new JButton( BACK );
        JButton exitButton = new JButton( "EXIT GAME" );
        
        titlePanel.setLayout( new BoxLayout( titlePanel, BoxLayout.Y_AXIS ) );
        buttonPanel.setLayout( gridLayout );
        buttonPanel.setBorder( BorderFactory.createEmptyBorder( 2*gapY, 2*gapX, 4*gapY, 2*gapX ) );

        title.setFont( new Font( title.getFont().getFontName(), Font.BOLD, 72 ) );
        version.setFont( new Font( version.getFont().getFontName(), Font.BOLD, 20 ) );

        // -------------------- Setting Listeners ----------------------- //
        playButton.addActionListener( this );
        stgsButton.addActionListener( this );
        backButton.addActionListener( this );
        exitButton.addActionListener( this );

        // -------------------- Setting Sizes     ----------------------- //
        buttonPanel.setPreferredSize( new Dimension( windowSize.width / 3, windowSize.height / 3 ) );

        // -------------------- Setting Alignment ----------------------- //
        title.setAlignmentX( CENTER_ALIGNMENT );
        version.setAlignmentX( LEFT_ALIGNMENT );

        // -------------------- Adding Components ----------------------- //
        this.add( titlePanel, BorderLayout.NORTH );
        this.add( buttonPanel, BorderLayout.EAST );

        titlePanel.add( title );
        titlePanel.add( version );
        buttonPanel.add( playButton );
        buttonPanel.add( stgsButton );
        buttonPanel.add( backButton );
        buttonPanel.add( exitButton );
        
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
        super.act( selected );
        String p = null;
        
        if ( selected.equals( "PLAY GAME" ) || selected.equals( LISTGAME ) ) p = LISTGAME;
         else if ( selected.equals( SETTINGS ) )                          p = SETTINGS;
        else if ( selected.equals( BACK ) || selected.equals( INITIAL ) ) p = INITIAL;
        else if ( selected.equals( "EXIT GAME" ) ) System.exit( 0 );
        
        if ( p != null ) carder.switchTo( p );
    }
}
