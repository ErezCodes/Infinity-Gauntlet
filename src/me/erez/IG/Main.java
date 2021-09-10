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

import me.erez.IG.Files.DataManager;
import me.erez.IG.Stones.ClickNPC;
import me.erez.IG.Stones.PacketReader;
import me.erez.IG.Stones.Reality;
import me.erez.IG.commands.ToggleFallDamage;
import me.erez.IG.commands.addNPC;
import me.erez.IG.commands.getGlove;
import me.erez.IG.commands.getStones;
import me.erez.IG.commands.printGlovers;
import me.erez.IG.commands.removeNPCS;
import me.erez.IG.commands.summonWolf;

public class Main extends JavaPlugin{
	
	public DataManager data;
	
	public ItemStack SpaceStone = createGuiItem(Material.LAPIS_LAZULI, ChatColor.BLUE + "Space Stone", ChatColor.DARK_BLUE + "I'm everywhere");
	public ItemStack MindStone = createGuiItem(Material.YELLOW_DYE, ChatColor.YELLOW + "Mind Stone", ChatColor.GOLD + "The ultimate manipulator");
	public ItemStack RealityStone = createGuiItem(Material.REDSTONE, ChatColor.RED + "Reality Stone", ChatColor.DARK_RED + "Reality can be whatever I want");
	public ItemStack PowerStone = createGuiItem(Material.PURPLE_DYE, ChatColor.LIGHT_PURPLE + "Power Stone", ChatColor.DARK_PURPLE + "Power is addicting");
	public ItemStack TimeStone = createGuiItem(Material.EMERALD, ChatColor.GREEN + "Time Stone", ChatColor.DARK_GREEN + "Infinite realities");
	public ItemStack SoulStone = createGuiItem(Material.ORANGE_DYE, ChatColor.GOLD + "Soul Stone", "What did it cost?");
	
	public boolean falldamage = false;
	
	public ArrayList<Glove> gloves = new ArrayList<Glove>();
	
	public boolean enableTime = false;
	
	@Override
	public void onEnable() {
		
		if(!Bukkit.getOnlinePlayers().isEmpty()) {
			for(Player player : Bukkit.getOnlinePlayers()) {
				PacketReader reader = new PacketReader();
				reader.inject(player);
			}
		}
		
		this.data = new DataManager(this);
		
		new getStones(this);
		new getGlove(this);
		new printGlovers(this);
		new ToggleFallDamage(this);
		new addNPC(this);
		new removeNPCS(this);
		new summonWolf(this);
		
		new Reality(this);
		new ClickNPC(this);
		
		data.reloadConfig();
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			
			Glove glove = new Glove(p, "none", false, false, false, false, false, false);
			
			String uuid = p.getUniqueId().toString();
			
			glove.setEquipped((String) data.getConfig().get("players." + uuid + ".equipped"));
			glove.setSpace((Boolean) data.getConfig().get("players." + uuid + ".hasSpace"));
			glove.setMind((Boolean) data.getConfig().get("players." + uuid + ".hasMind"));
			glove.setReality((Boolean) data.getConfig().get("players." + uuid + ".hasReality"));
			glove.setPower((Boolean) data.getConfig().get("players." + uuid + ".hasPower"));
			glove.setTime((Boolean) data.getConfig().get("players." + uuid + ".hasTime"));
			glove.setSoul((Boolean) data.getConfig().get("players." + uuid + ".hasSoul"));
			
			gloves.add(glove);
			
		}


		
		
	}
	
	@Override
	public void onDisable() {
		for(Player player : Bukkit.getOnlinePlayers()) {
			PacketReader reader = new PacketReader();
			reader.uninject(player);
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
	
	public int findPlayer(Player p) {

		for (int i = 0; i < gloves.size(); i++) {
			if (gloves.get(i).getPlayer().equals(p))
				return i;
		}

		return -1;
	}
}
