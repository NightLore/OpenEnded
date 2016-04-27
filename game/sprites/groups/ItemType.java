package game.sprites.groups;

import javax.swing.ImageIcon;

import game.sprites.Sprite;

/**
 *  Interface represents an item type (can be replaced with a String 
 *  implementation, used for type-safety practice that is not really used in 
 *  this project)
 *  <br><br>
 *  <i>note:</i> the DefaultItemType enum can replace this interface for efficiency, 
 *  but this interface is used for the possibility of more ItemTypes.
 *
 *  @author  Nathan M. Lui
 *  @version Apr 19, 2016
 *  @author  Assignment: OpenEnded
 */
public interface ItemType
{
    public static final DefaultItemType[] DEFAULT_ITEM_TYPES = DefaultItemType.values();
    
    /**
     *  Enum represents all the default item types that will be in the inventory.
     *  <br><br>
     *  Current instances are:<i><b>
     *  <br> SKILL_CLASS
     *  <br> WEAPON
     *  <br> CHARACTER
     *  <br> ITEM
     *  </i></b>
     *
     *  @author  Nathan M. Lui
     *  @version Apr 19, 2016
     *  @author  Assignment: OpenEnded
     */
    public enum DefaultItemType implements ItemType
    {
        SKILL_CLASS, WEAPON, CHARACTER, ITEM;
    }
    
    
    /**
     *  Represents an item in the Inventory, stores a sprite with stats and its
     *  type.
     *
     *  @author  Nathan M. Lui
     *  @version Apr 19, 2016
     *  @author  Assignment: OpenEnded
     */
    public class InventoryItem extends ImageIcon
    {
        private static final long serialVersionUID = 1L;
        
        
        private Sprite sprite;
        private ItemType type;
        public InventoryItem( Sprite sprite, ItemType type )
        {
            super( sprite.getImage() );
            this.sprite = sprite;
            this.type = type;
        }
        
        public Sprite getSprite()
        {
            return sprite;
        }
        
        public ItemType getType()
        {
            return type;
        }
        
    }
}
