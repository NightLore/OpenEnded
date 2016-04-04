package gui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import constants.DirectionalConstants;

/**
 *  Interface for panels that use keyboard controls to navigate
 *
 *  @author  Nathan Lui
 *  @version Feb 3, 2016
 */
public interface ControlListener extends DirectionalConstants
{
    public void up();
    public void left();
    public void down();
    public void right();
    public void confirm();
    public void cancel();
    
    public void act( String selected );
    
    public class ControlAction extends AbstractAction
    {
        /**
         * 
         */
        private static final long serialVersionUID = 1L;
        
        private ControlListener listener;
        private String action;
        public ControlAction( ControlListener listener, String action )
        {
            this.listener = listener;
            this.action = action;
        }

        @Override
        public void actionPerformed( ActionEvent e )
        {
            listener.act( action );
        }
    }
}
