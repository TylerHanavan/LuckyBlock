package com.LuckyBlock.plugin;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Commands implements CommandExecutor {
	
	private Core core = null;
	
	public Commands(Core core) {
		this.core = core;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String label, String[] args) {
		
		if(!label.equalsIgnoreCase("luckyblock"))
			return true;
		
		if(args.length == 0){
			
			sender.sendMessage(ChatColor.GREEN + "LuckyBlock commands:");
			sender.sendMessage(ChatColor.YELLOW + "/luckyblock add");

			sender.sendMessage(ChatColor.YELLOW + "/luckyblock delete (Item ID)");
			sender.sendMessage(ChatColor.YELLOW + "/luckyblock help");
			sender.sendMessage(ChatColor.RED + "/luckyblock reload");
			
			return true;
			
		}
		
		if(args.length == 1) {
			
			if(args[0].equalsIgnoreCase("add")) {
				
				if(!sender.hasPermission("luckyblock.add")) {
					sender.sendMessage(ChatColor.RED + "You lack permission.");
					return true;
				}
				
				if(!(sender instanceof Player))
					return true;
				
				ItemStack item = ((Player) sender).getItemInHand();
				
				if(item != null && item.getType() != Material.AIR) {
				
					this.core.addItem(item);
					
					sender.sendMessage(ChatColor.GREEN + "Item added to luckyblock drops.");
					
					return true;
				
				}
				
				sender.sendMessage(ChatColor.RED + "Your hand cannot be empty.");
				
				return true;
				
			}
			
			if(args[0].equalsIgnoreCase("help")) {
				
				sender.sendMessage(ChatColor.GREEN + "LuckyBlock commands:");
				sender.sendMessage(ChatColor.YELLOW + "/luckyblock add");

				sender.sendMessage(ChatColor.YELLOW + "/luckyblock delete (Item ID)");
				sender.sendMessage(ChatColor.YELLOW + "/luckyblock help");
				sender.sendMessage(ChatColor.RED + "/luckyblock reload");
				
				return true;
				
			}
			
			if(args[0].equalsIgnoreCase("reload")) {
				
				if(!sender.hasPermission("luckyblock.reload")) {
					sender.sendMessage(ChatColor.RED + "You lack permission.");
					return true;
				}
				
				this.core.load();
				
				sender.sendMessage(ChatColor.GREEN + "LuckyBlock reloaded.");
				
				return true;
				
			}
			
		}
		
		if(args.length == 2) {
			
			if(args[0].equalsIgnoreCase("delete")) {
				
				if(!sender.hasPermission("luckyblock.delete")) {
					sender.sendMessage(ChatColor.RED + "You lack permission.");
					return true;
				}
				
				if(!(sender instanceof Player))
					return true;
				
				int i = 0;
				
				try {
					i = Integer.parseInt(args[1]);
				} catch(Exception e){
					
					sender.sendMessage(ChatColor.RED + "You must type a number.");
					
					return true;
				}
				
				if(this.core.delete(i)) {
					
					sender.sendMessage(ChatColor.GREEN + "Item deleted.");
					
					return true;
				
				}
				
				sender.sendMessage(ChatColor.RED + "Item not found.");
				
				return true;
				
			}
			
			sender.sendMessage(ChatColor.RED + "Incorrect arguments!");
			
			return true;
			
		}
		
		if(args.length > 2) {
			
			sender.sendMessage(ChatColor.GREEN + "LuckyBlock commands:");
			sender.sendMessage(ChatColor.YELLOW + "/luckyblock add");
			sender.sendMessage(ChatColor.YELLOW + "/luckyblock delete (Item ID)");
			sender.sendMessage(ChatColor.YELLOW + "/luckyblock help");
			sender.sendMessage(ChatColor.RED + "/luckyblock reload");
			
			return true;
			
		}
		
		return true;
	}

}
