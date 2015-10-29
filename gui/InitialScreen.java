package gui;

import gui.Window.Panel;

import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class InitialScreen extends ScreenPanel implements KeyListener, MouseListener
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public InitialScreen( Window frame )
    {
        super( frame, "GrassyBackground.png" );
        this.addKeyListener( this );
        this.addMouseListener( this );
        this.setLayout( new BoxLayout( this, BoxLayout.Y_AXIS ) );
        
        JPanel menuTitlePanel = new JPanel();
        menuTitlePanel.setOpaque( false );
        JLabel title = new JLabel( frame.getName() );
        JLabel start = new JLabel( "PRESS ANY KEY TO CONTINUE" );

        title.setFont( new Font( title.getFont().getFontName(), Font.BOLD, 72 ) );
        title.setAlignmentX( CENTER_ALIGNMENT );
        start.setAlignmentX( CENTER_ALIGNMENT );

        menuTitlePanel.add( title );
        this.add( Box.createVerticalGlue() );
        this.add( menuTitlePanel );
        this.add( Box.createVerticalGlue() );
        this.add( Box.createVerticalGlue() );
        this.add( start );
        this.add( Box.createVerticalGlue() );
        this.add( Box.createVerticalGlue() );
        this.add( Box.createVerticalGlue() );
        this.add( Box.createVerticalGlue() );
    }
    public void start() { window.switchTo( this, Panel.MENU ); }
    @Override
    public void mouseClicked( MouseEvent e ) {}
    @Override
    public void mouseEntered( MouseEvent e ) {}
    @Override
    public void mouseExited( MouseEvent e ) {}
    @Override
    public void mousePressed( MouseEvent e ) { start(); }
    @Override
    public void mouseReleased( MouseEvent e ) {}
    @Override
    public void keyPressed( KeyEvent e ) { start(); }
    @Override
    public void keyReleased( KeyEvent e ) {}
    @Override
    public void keyTyped( KeyEvent e ) {}
}
