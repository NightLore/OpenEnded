package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
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

    protected Cards carder;
    protected String screen;
    private Image background;
    
    
    public ScreenPanel( Cards frame, String panel )
    {
        carder = frame;
        screen = panel;
        this.setBackground( Color.BLACK );
        this.setPreferredSize( new Dimension( frame.getWidth(), frame.getHeight() ) );
    }
    
    public ScreenPanel( Cards frame, String panel, String fileName )
    {
        this( frame, panel );
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
