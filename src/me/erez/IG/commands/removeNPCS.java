package me.erez.IG.commands;
import me.erez.IG.Main;
import me.erez.IG.NPC;

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

public class removeNPCS implements CommandExecutor {
    private Main plugin;
    
    public removeNPCS(Main plugin) {
        this.plugin = plugin;
        plugin.getCommand("removeNPCS").setExecutor(this);
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    	Player p = (Player) sender;
    	NPC.removeNPCPackets();
    	p.sendMessage(ChatColor.DARK_AQUA + "removed every NPC");
        return true;
    }
    
    protected ItemStack createGuiItem(final Material material, int amount, final String name, final String... lore) {
        final ItemStack item = new ItemStack(material, amount);
        final ItemMeta meta = item.getItemMeta();

        // Set the name of the item
        meta.setDisplayName(name);

        // Set the lore of the item
        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
    }
    
    
}

