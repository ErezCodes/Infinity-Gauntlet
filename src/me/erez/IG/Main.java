package me.erez.IG;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import me.erez.IG.Stones.Reality;
import me.erez.IG.commands.getGlove;
import me.erez.IG.commands.getStones;
import me.erez.IG.commands.printGlovers;

public class Main extends JavaPlugin{
	
	public ItemStack SpaceStone = createGuiItem(Material.LAPIS_LAZULI, ChatColor.BLUE + "Space Stone", ChatColor.DARK_BLUE + "I'm everywhere");
	public ItemStack MindStone = createGuiItem(Material.YELLOW_DYE, ChatColor.YELLOW + "Mind Stone", ChatColor.GOLD + "The ultimate manipulator");
	public ItemStack RealityStone = createGuiItem(Material.REDSTONE, ChatColor.RED + "Reality Stone", ChatColor.DARK_RED + "Reality can be whatever I want");
	public ItemStack PowerStone = createGuiItem(Material.PURPLE_DYE, ChatColor.LIGHT_PURPLE + "Power Stone", ChatColor.DARK_PURPLE + "Power is addicting");
	public ItemStack TimeStone = createGuiItem(Material.EMERALD, ChatColor.GREEN + "Time Stone", ChatColor.DARK_GREEN + "Infinite realities");
	public ItemStack SoulStone = createGuiItem(Material.ORANGE_DYE, ChatColor.GOLD + "Soul Stone", "What did it cost?");
	
	
	public ArrayList<Glove> gloves = new ArrayList<Glove>();
	
	public boolean enableTime = false;
	
	@Override
	public void onEnable() {
		new getStones(this);
		new getGlove(this);
		new printGlovers(this);
		
		
		new Reality(this);
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			gloves.add(new Glove(p, "none", false, false, false, false, false, false));
		}
		
		
		
		
		
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
