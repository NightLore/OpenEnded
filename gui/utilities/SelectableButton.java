package gui.utilities;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.Border;

/**
 *  Selectable Button that will change border based on whether or not it is 
 *  "selected"
 *
 *  @author  Nathan Man-ho Lui
 *  @version Feb 17, 2016
 */
public class SelectableButton extends JButton implements Selectable
{
    private static final long serialVersionUID = 1L;
    
    private Border selectBorder, deselectBorder;
    private boolean isSelected;
    
    public SelectableButton()
    {
        this( "" );
    }
    
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
        this.selectBorder = selected;
        this.isSelected = false;
        setDeselectedBorder( getBorder() );
        this.deselect();
        this.setContentAreaFilled( opaque );
    }
    
    public void setDeselectedBorder( Border border )
    {
        this.deselectBorder = border;
        if ( !isSelected )
            deselect();
    }
    
    public boolean isSelected()
    {
        return isSelected;
    }

    @Override
    public void select()
    {
        this.setBorder( selectBorder );
    }

    @Override
    public void deselect()
    {
        this.setBorder( deselectBorder );
    }

}
