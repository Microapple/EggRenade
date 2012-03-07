package us.microapple.eggrenade;

import us.microapple.eggrenade.EggRenade;

import org.bukkit.Location;
//import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.player.PlayerEggThrowEvent;

import org.bukkit.World;



public class eggListener implements Listener {

	private EggRenade plugin;
	
    public eggListener(EggRenade plugin) {
    	plugin.getServer().getPluginManager().registerEvents(this, plugin);
    	this.plugin = plugin;
    } 
    

    @EventHandler(priority = EventPriority.LOW)
    public void whenEggIsThrown(final PlayerEggThrowEvent event) {
       	Egg egg = event.getEgg();
    	Location loc = egg.getLocation();
    	Player player = event.getPlayer();
    	World world = loc.getWorld();
    	plugin.eggThrown(loc, player, world, egg, event);
    	event.setHatching(plugin.isHatching);
    }    
}
    	

