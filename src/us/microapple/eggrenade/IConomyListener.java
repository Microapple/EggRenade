package us.microapple.eggrenade;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.*;
import org.bukkit.plugin.Plugin;

import com.iCo6.*;
import com.iCo6.system.Account;
import com.iCo6.system.Accounts;
import com.iCo6.system.Holdings;

public class IConomyListener implements Listener {
	

	private EggRenade plugin;

    public IConomyListener (EggRenade plugin) {
        this.plugin = plugin;
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPluginEnable(PluginEnableEvent event) {
        if (plugin.PlugiConomy == null) {
            Plugin iConomy = plugin.getServer().getPluginManager().getPlugin("iConomy");
            if (iConomy != null) {
                if (iConomy.isEnabled() && iConomy.getClass().getName().equals("com.iConomy.iConomy")) {
                    plugin.PlugiConomy = (iConomy)iConomy;
                    System.out.println("[EggRenade] hooked into iConomy.");
                }
            }
        }
    }

    
    @EventHandler(priority = EventPriority.NORMAL)
    public void onDisable(PluginDisableEvent event) {
        if (plugin.PlugiConomy != null) {
            if (event.getPlugin().getDescription().getName().equals("iConomy")) {
                plugin.PlugiConomy = null;
                System.out.println("[EggRenade] un-hooked from iConomy.");
            }
        }
    }
    
    public boolean spendMoney(Player player, boolean bool) {
		FileConfiguration cfg = plugin.getConfig();
	    Account account = new Accounts().get(player.getName());
	    Holdings holdings = account.getHoldings();
	    double withdrawAmmountGren = (double) cfg.getInt("costPerGrenade");
	    double withdrawAmmountMol = (double) cfg.getInt("costPerMolotov");
	    if(!holdings.isNegative() && holdings.hasOver(withdrawAmmountGren) && bool) {
	    	holdings.subtract(withdrawAmmountGren);
	    	return true;
	    }
	    else if (!holdings.isNegative() && holdings.hasOver(withdrawAmmountMol) && !bool) {
	    	holdings.subtract(withdrawAmmountMol);
	    	return true;
	    }
	    else {
	    	return false; 
	    }
    }
    


}
