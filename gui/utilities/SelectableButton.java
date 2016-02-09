package gui.utilities;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.Border;

public class SelectableButton extends JButton implements Selectable
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private Border selected, deselected;
    
    public SelectableButton( String text )
    {
        this( text, Color.BLACK );
    }
    
    public SelectableButton( String text, Color foreground )
    {
        this( text, BorderFactory.createCompoundBorder( 
                      BorderFactory.createRaisedBevelBorder(), 
                      BorderFactory.createLoweredBevelBorder() ), true );
        this.setForeground( foreground );
    }
    
    public SelectableButton( String text, Border selected, boolean opaque )
    {
        super( text );
        this.selected = selected;
        this.deselected = getBorder();
        this.deselect();
        this.setContentAreaFilled( opaque );
    }
    

    @Override
    public void select()
    {
        this.setBorder( selected );
    }

    @Override
    public void deselect()
    {
        this.setBorder( deselected );
    }

}
