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

import javax.swing.JPanel;

import constants.KeyConstants;

/**
 *  Base panel for most of the ui in the game, manages all defaults
 *  Gives all key input to its own navigator, except for confirm() and cancel()
 *  where confirm will call act( navigator.getSelected() ) and cancel() will
 *  call back()
 *  
 *  <br> Implements Screen, ControlListener, and ActionListener
 *
 *  @author  Nathan Man-ho Lui
 *  @version Oct 28, 2015
 *  @author  Assignment: OpenEnded
 *
 *  @author  Sources: none
 */
public class ScreenPanel extends JPanel implements Screen, ControlListener, ActionListener, KeyConstants
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
     * @param carder
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
    public ScreenPanel( Carder carder, Image image, String back )
    {
        this( carder, Color.BLACK, back );
        this.background = image;
    }
    
    
    /**
     * Called by {@link gui.ScreenPanel#paintComponent(Graphics)}. Meant to be
     * overridden to draw additional elements on this gui 
     * @param g2d
     */
    public void draw(Graphics2D g2d) {}
    
    @Override
    protected void paintComponent( Graphics g ) 
    {
        Graphics2D g2d = (Graphics2D)g;        
        super.paintComponent( g2d );
        g.drawImage( background, 0, 0, this );
        draw( g2d );
    }
    
    /**
     * This method may be overridden for when this ScreenPanel is shown
     * @see gui.Screen#shown()
     */
    @Override
    public void shown() {}

    /**
     * This method calls the navigator's reset method, when overridden, it is 
     * suggested to call super.cover() or the navigator's reset method in order
     * to keep the functionality
     * @see gui.Screen#cover()
     */
    @Override
    public void cover() {
        if ( navigator != null )
            navigator.reset();
    }
    
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

    /**
     * This method is intended to be called when a key is pressed or when an 
     * item is selected based on the navigator
     * 
     * <br> This method currently calls {@link gui.ScreenPanel#check(String)}
     * @param selected the String representing what is selected or what key is 
     *          pressed
     * @see gui.ControlListener#act(java.lang.String)
     */
    @Override
    public void act( String selected ) {
        check( selected );
    }
    
    /**
     * Checks whether the selected element is a SPACE, ENTER, ESCAPE, BACKSPACE
     * of the {@link constants.KeyConstants}
     * <br> if SPACE or ENTER, calls confirm()
     * <br> if ESCAPE or BACKSPACE, calls cancel();
     * @param selected
     * @return true if <i>selected</i> is one of the above four Strings
     */
    public boolean check( String selected )
    {
        if ( selected.equals( SPACE ) || selected.equals( ENTER ) )
        {
            confirm();
            return true;
        }
        else if ( selected.equals( ESCAPE ) || selected.equals( BACKSPACE ) )
        {
            cancel();
            return true;
        }
        return false;
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        act( e.getActionCommand() );
    }
}
