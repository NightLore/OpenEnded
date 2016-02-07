package gui;

import java.awt.LayoutManager;

import javax.swing.JPanel;

/**
 *  Convenience JPanel with no opacity as default
 *
 *  @author  Nathan Lui
 *  @version Feb 6, 2016
 */
public class ClearPanel extends JPanel
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    public ClearPanel()
    {
        this.setOpaque( false );
    }
    public ClearPanel( LayoutManager layout )
    {
        super( layout );
        this.setOpaque( false );
    }
}
