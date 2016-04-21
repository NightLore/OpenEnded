package gui.utilities;

import java.awt.Color;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.Border;

/**
 *  Selectable Button that will change border based on whether or not it is 
 *  "selected"
 *
 *  @author  Nathan Man-ho Lui
 *  @version Feb 17, 2016
 *  @see <b>Implements</b>
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
    
    /**
     * Creates a default SelectableButton where the selected border is:<br>
     * BorderFactory.createCompoundBorder( 
     *                BorderFactory.createRaisedBevelBorder(), 
     *                BorderFactory.createLoweredBevelBorder() )<br>
     * 
     * @param text String for the words on the button
     */
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
    
    /**
     * Creates this button with the given text and border where the given border
     * is seen only when this button is considered selected with the method
     * {@link #select()}
     * 
     * @param text
     * @param selected
     * @param opaque
     */
    public SelectableButton( String text, Border selected, boolean opaque )
    {
        super( text );
        this.selectBorder = selected;
        this.isSelected = false;
        setDeselectedBorder( getBorder() );
        this.deselect();
        this.setContentAreaFilled( opaque );
    }
    
    /**
     * Sets the border for when this button is deselected. If the parameter is
     * null, this method will create an empty border based on the border for
     * when it is selected so that the size of this component does not change
     * @param border
     */
    public void setDeselectedBorder( Border border )
    {
        if ( border == null )
        {
            // get Insets of selected border and create empty border based on it
            Insets i = selectBorder.getBorderInsets( this );
            border = BorderFactory.createEmptyBorder( i.top, i.left, i.bottom, i.right );
        }
        this.deselectBorder = border;
        if ( !isSelected )
            deselect();
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
