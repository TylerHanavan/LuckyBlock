package com.LuckyBlock.plugin;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Skull;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagInt;

public class PlayerListener implements Listener {

	private Core core = null;
	
	public PlayerListener(Core core) {
		
		this.core = core;
		
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		
		Player player = event.getPlayer();
		
		if(player.getGameMode().equals(GameMode.SURVIVAL) && !event.isCancelled()) {
			
			ItemStack item = this.core.getRandomItem();
			
			if(item != null && this.blocks.contains(event.getBlock().getType())) {
				
				this.core.getServer().getScheduler().scheduleSyncDelayedTask(this.core, new Runnable() {

					@Override
					public void run() {
						
						event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), item);
						
					}
					
				}, 3L);
				
			}
			
		}
		
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		
		if(event.getBlockPlaced().getType() == Material.SKULL && !event.isCancelled()) {
			
			Skull skull = (Skull) event.getBlockPlaced().getState();
			
			ItemStack item = event.getItemInHand();
			
			ItemStack drop = this.core.fromLuckyToReward(item);
			
			if(drop != null && skull.getOwner().equalsIgnoreCase("Luck")) {
				event.getBlock().getWorld().dropItem(event.getBlock().getLocation(), drop);
				this.core.getServer().getScheduler().scheduleSyncDelayedTask(this.core, new Runnable() {

					@Override
					public void run() {
						
						event.getBlock().setType(Material.AIR);
						
					}
					
				}, 5L);
			}
				
			
		}
		
	}
	
}
