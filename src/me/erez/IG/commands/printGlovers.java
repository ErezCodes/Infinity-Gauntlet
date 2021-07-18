package me.erez.IG.commands;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
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

    	for(int i = 0; i < plugin.gloves.size(); i++) {
    		Bukkit.broadcastMessage(plugin.gloves.get(i).toString());
    		Bukkit.broadcastMessage("");
    	}
    	
//    	Server server = sender.getServer();
//    	server.reload();

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