package game.sprites.groups;

import game.sprites.Sprite;
import game.sprites.groups.ItemType.DefaultItemType;
import game.sprites.groups.ItemType.InventoryItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *  Stores the Weapons, Items, Skill Classes, and Story Players of a game instance
 *  <br><br>
 *  <i>note:</i> Set may be better than list in order to prevent repeats and keep 
 *  things organized.
 *
 *  @author  Nathan M. Lui
 *  @version Apr 18, 2016
 *  @author  Assignment: OpenEnded
 */
public class Inventory extends HashMap<ItemType, List<InventoryItem>> 
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    
    public List<InventoryItem> getWeapons()
    {
        return this.get( DefaultItemType.WEAPON );
    }
    
    public void addWeapon( Sprite s )
    {
        this.addSprite( s, DefaultItemType.WEAPON );
    }
    
    /**
     * Adds new Sprite to this inventory with that given type.<br>
     * if there is no mapping for the type, it will create a <b>new</b> ArrayList
     * to fill the spot
     * @see java.util.HashMap#get(java.lang.Object)
     */
    public void addSprite( Sprite s, ItemType type )
    {
        if ( !this.containsKey( type ) )
            this.put( type, new ArrayList<InventoryItem>() );
        this.get( type ).add( new InventoryItem( s, type ) );
    }

}
