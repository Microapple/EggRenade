package us.microapple.eggrenade;
/*
 * EggRenade
 * Version 1.7.1
 * By microapple
 * Tested with CB build 733
 */


import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.Plugin;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.*;
import org.bukkit.event.Event;
import org.bukkit.World;
//import net.minecraft.server.EntityTNTPrimed;
import us.microapple.eggrenade.eggListener;
//import com.nijiko.permissions.PermissionHandler;
//import com.nijikokun.bukkit.Permissions.Permissions;


public class EggRenade extends JavaPlugin {

    public Set<Player> eggUsers = new HashSet<Player>();
    public Set<Player> molotovUsers = new HashSet<Player>();
    private Random generator = new Random();
	//public static PermissionHandler Permissions;
	public boolean isHatching;
	public long delayTime;
	public float yield;
	public String defaultOn;
	public int molotovYield;

	
	public void onEnable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		System.out.println(pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!");
		this.getConfig().options().copyDefaults(true);
		this.saveConfig();
		this.reloadConfig();
		FileConfiguration cfg = this.getConfig();
        String stringyield = cfg.getString("TNT_Yield", "1.0");
        try
        {
          yield = Float.valueOf(stringyield.trim()).floatValue();
        }
        catch (NumberFormatException nfe)
        {
          System.out.println("EggRenade: Invalid Yeild ammount");
        }
        int intDelayTime = cfg.getInt("Grenade_Delay_Time", 4);
        delayTime = Long.valueOf(intDelayTime);
        defaultOn = cfg.getString("Default_On", "false");
        molotovYield = cfg.getInt("molotov_Yield", 10);
        new eggListener(this);
		}
	
	public void onDisable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		System.out.println(pdfFile.getName() + " version " + pdfFile.getVersion() + " has been disabled!");        
		}
	
	public void eggThrown(final Location loc, Player player, final World world, Egg egg, Event event){
		if(defaultOn == "true" || eggUsers.contains(player)) {
			isHatching = false;
			long actualDelayTime = delayTime * 20;
			this.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {

			    public void run() {
			        grenade(world, loc);
			    }
			}, actualDelayTime);
			
		}
		if(molotovUsers.contains(player)) {
			isHatching = false;
			molotov(loc);
		}
		else {
			isHatching = true;
		}
	
	}
	
	public void molotov(Location loc) {
		int molotovTimer = 0;
		int realmolotovYield = molotovYield - 1;
		while(molotovTimer < realmolotovYield) {
			Location spawnLoc = getSpawnLocation(loc, 5, 0);
			Block block = loc.getWorld().getBlockAt(spawnLoc);
			block.setType(Material.FIRE);
			molotovTimer = molotovTimer + 1;
		}
	}
	public void grenade(World world, Location loc) {
		
		//EntityTNTPrimed tnt = new EntityTNTPrimed((net.minecraft.server.World) world, loc.getX(), loc.getY(), loc.getZ());
		//world.a(tnt);
		float realYield = yield * 4;
		//world.a(tnt, loc.getX(), loc.getY(), loc.getZ(), realYield); */
		world.createExplosion(loc, realYield);		
	}
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args)	{
		String commandName = command.getName().toLowerCase();
        
        if (sender instanceof Player) {
            if (commandName.equals("grenade")) {
                		if(args.length >= 0) {
                			Player player = (Player) sender;
                			if(player.hasPermission("egg.grenade") || player.isOp()){
	        	            		boolean onList = eggUsers.contains(player);
	        	            		if(onList == true) {
	        	            			eggUsers.remove(player);
	        	            			player.sendMessage("EggRenade Disabled");
	        	            		}
	        	            		else {
	        	            			if(molotovUsers.contains(player)) {
	        	            				molotovUsers.remove(player);
	        	            				player.sendMessage("Eggmolotov Disabled");
	        	            				}
	        	            			eggUsers.add(player);
	        	            			player.sendMessage("EggRenade Enabled");
	        	            		}
	        	            		
	        	            }
	        	            else {
	    	                      sender.sendMessage(ChatColor.RED + "You do not have access to this command.");
	        	            	}
                		}	 
                }
                
             else if(commandName.equals("molotov")){
            	if(args.length >= 0) {
            		if(sender.hasPermission("egg.molotov") || sender.isOp()) {
    	            		Player player = (Player) sender;
    	            		boolean onList = molotovUsers.contains(player);
    	            		if(onList == true) {
    	            			molotovUsers.remove(player);
    	            			player.sendMessage("Eggmolotov Disabled");
    	            		}
    	            		else {
    	            			if(eggUsers.contains(player)) {
    	            				eggUsers.remove(player);
    	            				player.sendMessage("EggRenade Disabled");
    	            			}
    	            			molotovUsers.add(player);
    	            			player.sendMessage("Eggmolotov Enabled");
    	            		}
    	            }
    	            else {
	                        sender.sendMessage(ChatColor.RED + "You do not have access to this command.");
    	            	}
        			}
        		}
        	else {
        			return false;
        		}
            	
            }
		return true;
	}
	
	
	@SuppressWarnings("static-access")
	private void setupPermissions() {
	      Plugin test = this.getServer().getPluginManager().getPlugin("Permissions");

	      /*if (this.Permissions == null) {
	          if (test != null) {
	              this.Permissions = ((Permissions)test).getHandler();
	          } else {
	              log.info("EggRenade: Permission system not detected, defaulting to OP");
	          }
	      }*/
	  }
	/*public void loadConfigFile() {
		// load config file, creating it first if it doesn't exist
		// Needs import java.io.File;
		//import java.io.InputStream;
		//import java.io.FileOutputStream;
		//import java.util.jar.JarFile;
		//import java.util.jar.JarEntry;
		File configFile = new File(this.getDataFolder(), "config.yml");
			if (!configFile.canRead()) try {
				configFile.getParentFile().mkdirs();
				JarFile jar = new JarFile(this.getFile());
				JarEntry entry = jar.getJarEntry("config.yml");
				InputStream is = jar.getInputStream(entry);
				FileOutputStream os = new FileOutputStream(configFile);
				byte[] buf = new byte[(int)entry.getSize()];
				is.read(buf, 0, (int)entry.getSize());
				os.write(buf);
		os.close();
		FileConfiguration cfg = this.getConfig();
		cfg.load(configFile);
		} 
		catch (Exception e) {
		System.out.println("EggRenade: could not create configuration file");
		} 


	} */
public Location getSpawnLocation(Location location, int SpawnRadius, int initial) {
		
	
		double randX = generator.nextDouble() * SpawnRadius;
		double randZ = generator.nextDouble() * SpawnRadius;
		double realRandX = randX + initial;
		double realRandZ = randZ + initial;
		
		Location spawnLoc = location;
		double currentX = location.getX();
		double currentZ = location.getZ();
		
		boolean shouldAddX = generator.nextBoolean();
		boolean shouldAddZ = generator.nextBoolean();
		
		if(shouldAddX == true) {
			spawnLoc.setX(currentX + realRandX);
		}
		else {
			spawnLoc.setX(currentX - realRandX);
		}
		if(shouldAddZ == true) {
			spawnLoc.setZ(currentZ + realRandZ);
		}
		else {
			spawnLoc.setZ(currentZ - realRandZ);
		}
		return spawnLoc;
		
	}
	

}
