package gui;

import java.awt.Dimension;

import javax.swing.JFrame;

/**
 *  Main JFrame of the game
 *
 *  @author  Nathan Man-ho Lui
 *  @version Oct 28, 2015
 *  @author  Assignment: OpenEnded
 *
 *  @author  Sources: none
 */
public class Window extends JFrame
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    public static final Dimension windowSize = new Dimension( 1280, 720 );
    public static final Dimension buttonSize = new Dimension( windowSize.width, windowSize.height / 20 );
    
    public Window()
    {
        this.setTitle( getGameName() + " " + getVersion() );
        this.setName( getGameName() );
        this.getContentPane().setPreferredSize( windowSize );
        this.pack();
//        this.setMinimumSize( windowSize );
        this.setLocationRelativeTo( null );
        this.setDefaultCloseOperation( EXIT_ON_CLOSE );
//        this.setResizable( false );
        this.setVisible( true );
        
        MainPanel main = new MainPanel();
        this.add( main );
        main.switchTo( MainPanel.INITIAL );
    }
    
    public static String getGameName()
    {
        return "OpenEnded";
    }
    
    public static String getVersion()
    {
        return "V0.19";
    }

    public static void main( String[] args ) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Window();
            }
        });
    }
}
