package us.microapple.eggrenade;

import net.milkbowl.vault.economy.EconomyResponse;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;


public class EconHandler {
	
	private EggRenade plugin;
    
    public EconHandler (EggRenade plugin) {
        this.plugin = plugin;
    }
    
    public boolean spendMoney(Player player, boolean bool) {
		FileConfiguration cfg = plugin.getConfig();
		if(bool) {
			double withdrawAmmountGren = (double) cfg.getInt("costPerGrenade");
			EconomyResponse r = plugin.econ.withdrawPlayer(player.getName(), withdrawAmmountGren);
			if(!r.transactionSuccess()) {
				return false;
			}
			else {
				return true;
			}
		}
		if(!bool) {
			double withdrawAmmountMol = (double) cfg.getInt("costPerMolotov");
			EconomyResponse r = plugin.econ.withdrawPlayer(player.getName(), withdrawAmmountMol);
			if(!r.transactionSuccess()) {
				return false;
			}
			else {
				return true;
			}
		}
	    else {
	    	return false; 
	    }
    }
}
