package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *  Base panel for most of the ui in the game, manages all defaults
 *
 *  @author  Nathan Man-ho Lui
 *  @version Oct 28, 2015
 *  @author  Assignment: OpenEnded
 *
 *  @author  Sources: none
 */
public class ScreenPanel extends JPanel implements Screen
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    protected Carder carder;
    private Image background;
    
    
    public ScreenPanel( Carder frame )
    {
        carder = frame;
        this.setBackground( Color.BLACK );
        this.setFocusable( true );
    }
    
    public ScreenPanel( Carder frame, String fileName )
    {
        this( frame );
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
    
//    @Override
//    public Dimension getPreferredSize() {
//        return new Dimension(
//            background.getWidth(null),
//            background.getHeight(null));
//    }
    
    @Override
    public void shown()
    {
        
    }

    @Override
    public void cover()
    {
        
    }

}
