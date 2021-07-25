package me.erez.IG.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftMinecartChest;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.erez.IG.Main;

public class printGlovers implements CommandExecutor {
    private Main plugin;
    private ItemStack glove = createGuiItem(Material.NAUTILUS_SHELL, ChatColor.RED + "Infinity Gauntlet", ChatColor.DARK_RED + "Must collect them all...");
    public printGlovers(Main plugin) {
        this.plugin = plugin;
        plugin.getCommand("printGlovers").setExecutor(this);
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    	
//    	ArrayList<World> worlds = (ArrayList<World>) Bukkit.getWorlds();
    	Player p = (Player) sender;
    	for(int i = 0; i < plugin.gloves.size(); i++) {
    		Bukkit.broadcastMessage(plugin.gloves.get(i).toString());
    		Bukkit.broadcastMessage("");
    	}
    	World world = p.getWorld();
    	
    	
    	ArrayList<Entity> entityList = (ArrayList<Entity>) world.getEntities();
    	for(int i = 0; i < entityList.size(); i++) {
    		if(entityList.get(i) instanceof CraftMinecartChest) {
    			Bukkit.broadcastMessage("chest");
    			CraftMinecartChest chest = (CraftMinecartChest) entityList.get(i);
    			ItemStack[] storageContents = chest.getInventory().getStorageContents();
    			ItemStack[] contents = chest.getInventory().getContents();
    			
    		}
    	}
    	
    	
    	//Bukkit.broadcastMessage(entityList.toString());
//    	for(Entity entity : entityList) {
//    		Bukkit.broadcastMessage(entity.getName());
//    	}
    	
    	
//    	World world = p.getWorld();
//    	world.getWorldFolder().delete();
//    	//Bukkit.getWorld("world").getWorldFolder().delete();
//    	
//    	Server server = sender.getServer();
//    	server.reload();
//    	Bukkit.reload();
//    	Bukkit.reloadData();
    	
    	

        return true;
    }
    
    protected ItemStack createGuiItem(final Material material, final String name, final String... lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        // Set the name of the item
        meta.setDisplayName(name);

        // Set the lore of the item
        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
    }
}