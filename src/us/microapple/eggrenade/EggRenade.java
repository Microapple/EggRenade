package us.microapple.eggrenade;
/*
 * EggRenade
 * Version 1.5
 * By microapple
 * Tested with CB build 617
 */

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.Plugin;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.event.Event;
import net.minecraft.server.World;
import net.minecraft.server.EntityTNTPrimed;
import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;


public class EggRenade extends JavaPlugin {

    public Set<Player> eggUsers = new HashSet<Player>();
	public static PermissionHandler Permissions;
    private static final Logger log = Logger.getLogger("Minecraft");


    
    private final eggListener eggeventlistener = new eggListener(this);
	public void onEnable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		System.out.println(pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!");    
		 
		PluginManager pm = getServer().getPluginManager();
        //Create PlayerCommand listener
		pm.registerEvent(Event.Type.PLAYER_EGG_THROW, this.eggeventlistener, Event.Priority.Low, this);
		setupPermissions();
		}
	
	public void onDisable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		System.out.println(pdfFile.getName() + " version " + pdfFile.getVersion() + " has been disabled!");        
		}
	public void eggThrown(Location loc, Player player, World world, Egg egg){
		if(enabled(player) == true){
			EntityTNTPrimed tnt = new EntityTNTPrimed((net.minecraft.server.World) world, loc.getX(), loc.getY(), loc.getZ());
			world.a(tnt);
		}
	
	}
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args)	{
		String commandName = command.getName().toLowerCase();
        
        if (sender instanceof Player) {
            if (commandName.equals("grenade")) {
                		if(args.length >= 0) {
                			if(getServer().getPluginManager().isPluginEnabled("Permissions") == true) {
                				//Permisions ONLINE mode
	        	            	Player player = (Player) sender;
	        	            	if (EggRenade.Permissions.has(player, "egg.renade") == true) {
	        	            		boolean onList = eggUsers.contains(player);
	        	            		if(onList == true) {
	        	            			eggUsers.remove(player);
	        	            			player.sendMessage("EggRenade Disabled");
	        	            		}
	        	            		else {
	        	            			eggUsers.add(player);
	        	            			player.sendMessage("EggRenade Enabled");
	        	            		}
	        	            		
	        	            	}
	        	            	else {
	    	                        player.sendMessage(ChatColor.RED + "You do not have access to this command.");
	        	            	}
                			} 
                			else {
                				//Permissions OFFLINE mode
                				boolean op;;
        						op = sender.isOp();
                            	if (op == true){
                            		Player player = (Player) sender;
                            		boolean onList = eggUsers.contains(player);
	        	            		if(onList == true) {
	        	            			eggUsers.remove(player);
	        	            			player.sendMessage("EggRenade Disabled");
	        	            		}
	        	            		else {
	        	            			eggUsers.add(player);
	        	            			player.sendMessage("EggRenade Enabled");
	        	            		}
                            		
                            	}
                            	else {
    	        	            	Player player = (Player) sender;
	    	                        player.sendMessage(ChatColor.RED + "You do not have access to this command.");

                            	}
                			}
                		}
                		else {
                			return false;
                		}
                }
        }
		return true;
	}
	
	public boolean enabled(Player player) {
		boolean onList = eggUsers.contains(player);
		if(onList == true) {
			return true;
		}
		else {
			return false;
		}
	}
	
	@SuppressWarnings("static-access")
	private void setupPermissions() {
	      Plugin test = this.getServer().getPluginManager().getPlugin("Permissions");

	      if (this.Permissions == null) {
	          if (test != null) {
	              this.Permissions = ((Permissions)test).getHandler();
	          } else {
	              log.info("EggRenade: Permission system not detected, defaulting to OP");
	          }
	      }
	  }
	

}
