package us.microapple.eggrenade;

import us.microapple.eggrenade.EggRenade;
import org.bukkit.Location;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerEggThrowEvent;
import net.minecraft.server.World;



public class eggListener extends PlayerListener {

	public static EggRenade plugin;
    public eggListener(EggRenade instance) {
    plugin = instance;
    }
    	
   
    
    public void onPlayerEggThrow(PlayerEggThrowEvent event) {
    	Egg egg = event.getEgg();
    	Location loc = egg.getLocation();
    	World world = ((CraftWorld)loc.getWorld()).getHandle();
    	Player player = event.getPlayer();
    	plugin.eggThrown(loc, player, world, egg);
    }
    
}
    	

