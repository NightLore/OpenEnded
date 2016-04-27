package gui.game.player;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.plaf.basic.BasicProgressBarUI;

import game.sprites.Sprite;
import game.sprites.SpriteData;
import gui.ClearPanel;

/**
 *  This panel takes a Sprite and neatly displays that Sprite's stats in itself
 *
 *  @author  Nathan
 *  @version Apr 21, 2016
 *  @author  Assignment: OpenEnded
 */
public class StatDisplay extends ClearPanel
{
    private static final long serialVersionUID = 1L;
    
    private JProgressBar hpBar;
    private JLabel imgLabel, atkLabel;

    private Sprite sprite;

    /**
     * 
     */
    public StatDisplay()
    {
        super( new BorderLayout() );
        ClearPanel topPanel, btmPanel, imgPanel, statPanel, barsPanel;
        topPanel = new ClearPanel();
        btmPanel = new ClearPanel( new BorderLayout() );
        imgPanel = new ClearPanel();
        statPanel = new ClearPanel( new GridLayout( 0, 2 ) );
        barsPanel = new ClearPanel();
        topPanel.setLayout( new BoxLayout( topPanel, BoxLayout.X_AXIS ) );
        barsPanel.setLayout( new BoxLayout( barsPanel, BoxLayout.Y_AXIS ) );
        
        imgLabel = new JLabel();
        
        hpBar = new JProgressBar();
        hpBar.setStringPainted( true );
        hpBar.setForeground( Color.GREEN );
        hpBar.setBackground( Color.GRAY );
        hpBar.setUI( new BasicProgressBarUI() {
            protected Color getSelectionBackground() { return Color.WHITE; }
            protected Color getSelectionForeground() { return Color.BLACK; }
        } );
        atkLabel = new JLabel();
        atkLabel.setForeground( Color.WHITE );
        
        imgPanel.add( imgLabel );
        barsPanel.add( hpBar );
        statPanel.add( atkLabel );
        topPanel.add( Box.createHorizontalGlue() );
        topPanel.add( imgPanel );
        topPanel.add( Box.createHorizontalGlue() );
        topPanel.add( barsPanel );
        topPanel.add( Box.createHorizontalGlue() );
        btmPanel.add( statPanel, BorderLayout.CENTER );
        this.add( topPanel, BorderLayout.NORTH );
        this.add( btmPanel, BorderLayout.SOUTH );
    }

    public void setSprite( Sprite s )
    {
        this.sprite = s;
        updateVisuals();
    }
    
    public void updateVisuals()
    {
        imgLabel.setIcon( new ImageIcon( sprite.getImage() ) );
        SpriteData data = sprite.getSpriteData();
        int maxHp = data.getMaxHp();
        int hp = data.getHp();
        hpBar.setMaximum( maxHp );
        hpBar.setValue( hp );
        hpBar.setString( hp + "/" + maxHp );
        atkLabel.setText( "Atk: " + data.getAtk() );
    }
}
