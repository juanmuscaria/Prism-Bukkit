package me.botsko.prism.commands;

import org.bukkit.ChatColor;

import me.botsko.prism.Prism;
import me.botsko.prism.commandlibs.CallInfo;
import me.botsko.prism.commandlibs.SubHandler;
import me.botsko.prism.wands.InspectorWand;
import me.botsko.prism.wands.ProfileWand;
import me.botsko.prism.wands.RestoreWand;
import me.botsko.prism.wands.RollbackWand;
import me.botsko.prism.wands.Wand;

public class WandCommand implements SubHandler {
	
	/**
	 * 
	 */
	private Prism plugin;
	
	
	/**
	 * 
	 * @param plugin
	 * @return 
	 */
	public WandCommand(Prism plugin) {
		this.plugin = plugin;
	}
	
	
	/**
	 * Handle the command
	 */
	public void handle(CallInfo call) {
		
		String type = "i";
		if(call.getArgs().length == 2){
			type = call.getArg(1);
		}
		
		Wand oldwand = null;
		if(plugin.playersWithActiveTools.containsKey(call.getPlayer().getName())){
			// Pull the wand in use
			oldwand = plugin.playersWithActiveTools.get(call.getPlayer().getName());
		}
		
		// Always remove the old one
		plugin.playersWithActiveTools.remove(call.getPlayer().getName());
		
			
		/**
		 * Inspector wand
		 */
		if( type.equalsIgnoreCase("i") ){
			if( !call.getPlayer().hasPermission("prism.lookup") && !call.getPlayer().hasPermission("prism.wand.inspect") ){
				call.getPlayer().sendMessage( plugin.messenger.playerHeaderMsg("You do not have permission for this.") );
				return;
			}
			if(oldwand != null){
				// If disabling this one
				if( oldwand instanceof InspectorWand ){
					call.getPlayer().sendMessage( plugin.messenger.playerHeaderMsg("Inspection wand " + ChatColor.RED + "disabled"+ChatColor.WHITE+".") );
					return;
				}
			}
			InspectorWand wand = new InspectorWand( plugin );
			plugin.playersWithActiveTools.put(call.getPlayer().getName(), wand);
			call.getPlayer().sendMessage( plugin.messenger.playerHeaderMsg("Inspection wand " + ChatColor.GREEN + "enabled"+ChatColor.WHITE+".") );
		}
		
		/**
		 * Profile wand
		 */
		else if( type.equalsIgnoreCase("p") ){
			if( !call.getPlayer().hasPermission("prism.lookup") && !call.getPlayer().hasPermission("prism.wand.profile") ){
				call.getPlayer().sendMessage( plugin.messenger.playerHeaderMsg("You do not have permission for this.") );
				return;
			}
			if(oldwand != null){
				// If disabling this one
				if( oldwand instanceof ProfileWand ){
					call.getPlayer().sendMessage( plugin.messenger.playerHeaderMsg("Profile wand " + ChatColor.RED + "disabled"+ChatColor.WHITE+".") );
					return;
				}
			}
			ProfileWand wand = new ProfileWand( plugin );
			plugin.playersWithActiveTools.put(call.getPlayer().getName(), wand);
			call.getPlayer().sendMessage( plugin.messenger.playerHeaderMsg("Profile wand " + ChatColor.GREEN + "enabled"+ChatColor.WHITE+".") );
		}

		
		/**
		 * Rollback wand
		 */
		else if( type.equalsIgnoreCase("rollback") ){
			if( !call.getPlayer().hasPermission("prism.rollback") && !call.getPlayer().hasPermission("prism.wand.rollback") ){
				call.getPlayer().sendMessage( plugin.messenger.playerHeaderMsg("You do not have permission for this.") );
				return;
			}
			if(oldwand != null){
				// If disabling this one
				if( oldwand instanceof RollbackWand ){
					call.getPlayer().sendMessage( plugin.messenger.playerHeaderMsg("Rollback wand " + ChatColor.RED + "disabled"+ChatColor.WHITE+".") );
					return;
				}
			}
			RollbackWand wand = new RollbackWand( plugin );
			plugin.playersWithActiveTools.put(call.getPlayer().getName(), wand);
			call.getPlayer().sendMessage( plugin.messenger.playerHeaderMsg("Rollback wand " + ChatColor.GREEN + "enabled"+ChatColor.WHITE+".") );
		}
		
		/**
		 * Restore wand
		 * @todo
		 */
		else if(type.equalsIgnoreCase("restore")){
			if( !call.getPlayer().hasPermission("prism.restore") && !call.getPlayer().hasPermission("prism.wand.restore") ){
				call.getPlayer().sendMessage( plugin.messenger.playerHeaderMsg("You do not have permission for this.") );
				return;
			}
			if(oldwand != null){
				// If disabling this one
				if( oldwand instanceof RestoreWand ){
					call.getPlayer().sendMessage( plugin.messenger.playerHeaderMsg("Restore wand " + ChatColor.RED + "disabled"+ChatColor.WHITE+".") );
					return;
				}
			}
			RestoreWand wand = new RestoreWand( plugin );
			plugin.playersWithActiveTools.put(call.getPlayer().getName(), wand);
			call.getPlayer().sendMessage( plugin.messenger.playerHeaderMsg("Restore wand " + ChatColor.GREEN + "enabled"+ChatColor.WHITE+".") );
		}
		
		/**
		 * Off
		 */
		else if(type.equalsIgnoreCase("off")){
			call.getPlayer().sendMessage( plugin.messenger.playerHeaderMsg("Current wand " + ChatColor.RED + "disabled"+ChatColor.WHITE+".") );
		}
		
		// Not a valid wand
		else {
			call.getPlayer().sendMessage( plugin.messenger.playerError("Invalid wand type. Use /prism ? for help.") );
		}
	}
}