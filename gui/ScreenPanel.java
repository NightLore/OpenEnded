package gui;

import game.Window;
import gui.utilities.Navigator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *  Base panel for most of the ui in the game, manages all defaults
 *  Gives all key input to its own navigator, except for confirm() and cancel()
 *  where confirm will call act( navigator.getSelected() ) and cancel() will
 *  call back()
 *
 *  @author  Nathan Man-ho Lui
 *  @version Oct 28, 2015
 *  @author  Assignment: OpenEnded
 *
 *  @author  Sources: none
 */
public class ScreenPanel extends JPanel implements Screen, ControlListener, ActionListener
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    public static final Dimension windowSize = Window.windowSize;
    public static final int gapY = windowSize.height / 15;
    public static final int gapX = windowSize.width / 20;

    protected Carder carder;
    private Image background;

    private String back;
    protected Navigator navigator;
    
    /**
     * Constructs a transparent ScreenPanel
     * if opacity is set back to true, default background color is Color.BLACK
     * 
     * @param frame
     * @param back
     */
    public ScreenPanel( Carder carder, String back )
    {
        this( carder, Color.BLACK, back );
        this.setOpaque( false );
    }
    
    /**
     * Constructs a ScreenPanel with given background color
     * 
     * @param carder
     * @param background
     * @param back
     */
    public ScreenPanel( Carder carder, Color background, String back )
    {
        this.carder = carder;
        this.back = back;
        this.setBackground( background );
        this.setFocusable( true );
    }
    
    /**
     * Constructs a ScreenPanel with a given background image
     * 
     * @param carder
     * @param fileName
     * @param back
     */
    public ScreenPanel( Carder carder, String fileName, String back )
    {
        this( carder, Color.BLACK, back );
        fileName =  "/imgs/" + fileName; // Package images are found in
        while ( background == null )
        {
            try
            {
                background = ImageIO.read( ScreenPanel.class.getResource( fileName ) );
//                background = new ImageIcon( ScreenPanel.class.getResource( fileName ) ).getImage();
            }
            catch ( IOException e )
            {
                e.printStackTrace(); 
                System.out.println( "Cannot find: " + fileName );
                @SuppressWarnings("resource")
                Scanner scanIn = new Scanner( System.in );
                System.out.print( "Try different file: " );
                fileName = scanIn.nextLine();
            }
        }
    }
    public void draw(Graphics2D g2d) {}
    
    @Override
    protected void paintComponent( Graphics g ) 
    {
        Graphics2D g2d = (Graphics2D)g;        
        super.paintComponent( g2d );
        g.drawImage( background, 0, 0, this );
        draw( g2d );
    }
    
    @Override
    public void shown() {}

    @Override
    public void cover() {}
    
    public void back()
    {
        carder.switchTo( back );
    }

    @Override
    public void up()
    {
        if ( navigator != null )
            navigator.up();
    }

    @Override
    public void left()
    {
        if ( navigator != null )
            navigator.left();
    }

    @Override
    public void down()
    {
        if ( navigator != null )
            navigator.down();
    }

    @Override
    public void right()
    {
        if ( navigator != null )
            navigator.right();
    }

    @Override
    public void confirm()
    {
        if ( navigator != null )
            act( navigator.getSelected() );
    }

    /**
     * Calls back()
     * 
     * @see gui.ControlListener#cancel()
     */
    @Override
    public void cancel()
    {
        back();
    }

    @Override
    public void act( String selected ) {
        if ( selected.equals( SPACE ) || selected.equals( ENTER ) )
        {
            confirm();
        }
        else if ( selected.equals( ESCAPE ) || selected.equals( BACKSPACE ) )
        {
            cancel();
        }
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        act( e.getActionCommand() );
    }
}
