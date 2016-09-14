package com.LuckyBlock.plugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;

import com.PazzwordAPI.plugin.util.Yaml;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_8_R3.NBTTagCompound;

public class Core extends JavaPlugin {
	
	private Yaml yaml = null;

	private List<ItemStack> items = new ArrayList<ItemStack>();
	
	private int top = 0;
	
	private Random random = new Random();
	
	private ItemStack luckyBlock = null;
	
	public static boolean DEBUG = false;
	
	private HashMap<Material, Integer> blocks = new HashMap<Material, Integer>();
	
	private List<Material> blockList = new ArrayList<Material>();

	@Override
	public void onEnable() {

		this.blockList.add(Material.STONE);
		this.blockList.add(Material.DIAMOND_ORE);
		this.blockList.add(Material.GOLD_ORE);
		this.blockList.add(Material.IRON_ORE);
		this.blockList.add(Material.LAPIS_ORE);
		this.blockList.add(Material.REDSTONE_ORE);
		this.blockList.add(Material.EMERALD_ORE);
		
		File file = new File(this.getDataFolder().getAbsolutePath() + File.separator + "config.yml");
		
		if(!file.exists())
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		Yaml yaml = new Yaml(file);
		
		this.yaml = yaml;
		
		this.load();
		
		this.luckyBlock = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
		
		SkullMeta meta = (SkullMeta) this.luckyBlock.getItemMeta();
		
		meta.setOwner("Luck");
		
		meta.setDisplayName(ChatColor.GOLD + "Lucky Block");
		
		this.luckyBlock.setItemMeta(meta);
		
		this.getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
		
		this.getCommand("luckyblock").setExecutor(new Commands(this));
		
		
		
	}
	
	@Override
	public void onDisable() {
		
		
		
	}
	
	public void addItem(ItemStack item) {
		
		this.yaml.load();
		
		this.top++;
		
		this.yaml.setItemStack("items.item" + this.top, item, -1);
		
		this.yaml.set("items.item" + this.top + ".id_", this.top);
		
		this.yaml.save();
		
		this.load();
		
	}
	
	public boolean delete(int i) {
		
		this.yaml.load();

		ConfigurationSection section = this.yaml.getConfigurationSection("items");
		
		if(section == null) {
			
			this.yaml.save();
			
			return false;
			
		}
		
		Set<String> set = section.getKeys(false);
		
		if(set == null) {
			
			this.yaml.save();
			
			return false;
			
		}
		
		int j = 0;
		
		for(String s : set) {
			
			ItemStack item = this.yaml.getItemStack("items." + s, -1);
			
			if(item != null) {
				
				if(item.getType().getId() == i) {
					
					++j;
					
					this.yaml.set("items." + s + "", null);
					
				}
				
			}
			
		}
		
		this.yaml.save();
		
		if(j != 0) {
		
			this.load();
			
			return true;
		
		} else {
			
			return false;
			
		}
		
	}
	
	public int getRandomInt(int min, int max) {

	    int randomNum = this.random.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
	
	public ItemStack getLuckyBlock(int id) {
		
		return this.luckyBlock;
		
	}
	
	public void load() {
		
		this.yaml.load();
		
		ConfigurationSection section = yaml.getConfigurationSection("items");
		
		
		
		this.yaml.save();
		
	}
	
	public static String getSkin(ItemStack item) {
		CraftItemStack craftStack = null;
		net.minecraft.server.v1_8_R3.ItemStack itemStack = null;
		if (item instanceof CraftItemStack) {
			craftStack = (CraftItemStack) item;
			itemStack = CraftItemStack.asNMSCopy(item);
		} else if (item instanceof ItemStack) {
			craftStack = CraftItemStack.asCraftCopy(item);
			itemStack = CraftItemStack.asNMSCopy(item);
		}
		NBTTagCompound tag = itemStack.getTag();
		if (tag == null) {
			tag = new NBTTagCompound();
			return null;
		}
		return tag.getString("SkullOwner");
	}
	
}
