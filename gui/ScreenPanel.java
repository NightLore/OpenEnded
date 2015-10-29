package gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ScreenPanel extends JPanel implements Screen
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    protected Window window;
    private BufferedImage background;
    
    
    public ScreenPanel( Window frame )
    {
        window = frame;
//        this.setPreferredSize( new Dimension( frame.getWidth(), frame.getHeight() ) );
    }
    
    public ScreenPanel( Window frame, String fileName )
    {
        this( frame );
        fileName =  "/imgs/" + fileName; // Package images are found in
        while ( background == null )
        {
            try
            {
                background = ImageIO.read( ScreenPanel.class.getResource( fileName ) );
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
    
    @Override
    protected void paintComponent( Graphics g ) 
    {
        super.paintComponent( g );
        g.drawImage( background, 0, 0, null );
    }
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(
            background.getWidth(null),
            background.getHeight(null));
    }
    
    @Override
    public void shown()
    {
        
    }

    @Override
    public void cover()
    {
        
    }

}
