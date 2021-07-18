package me.erez.IG.commands;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.erez.IG.Main;

public class getStones implements CommandExecutor {
    private Main plugin;
    
    public getStones(Main plugin) {
        this.plugin = plugin;
        plugin.getCommand("getStones").setExecutor(this);
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;
        
        p.getInventory().addItem(plugin.SpaceStone);
        p.getInventory().addItem(plugin.MindStone);
        p.getInventory().addItem(plugin.RealityStone);
        p.getInventory().addItem(plugin.PowerStone);
        p.getInventory().addItem(plugin.TimeStone);
        p.getInventory().addItem(plugin.SoulStone);
        
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