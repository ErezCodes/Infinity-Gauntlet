package me.erez.IG.commands;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitScheduler;

import me.erez.IG.Main;

public class summonWolf implements CommandExecutor {
    private Main plugin;
    
    public summonWolf(Main plugin) {
        this.plugin = plugin;
        plugin.getCommand("summonWolf").setExecutor(this);
    }
    
    private int ghostTask;
    
    @SuppressWarnings("deprecation")
	@Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        GameMode pastGamemode = player.getGameMode();
        player.sendMessage(pastGamemode.toString());
        player.setGameMode(GameMode.SPECTATOR);
		BukkitScheduler sched = player.getServer().getScheduler();
		player.sendMessage(ChatColor.YELLOW + "You have became a ghost for 5 seconds");
		
		
		ghostTask = sched.scheduleSyncRepeatingTask(plugin, new Runnable() {
			
			int timer = 5;
			
			@Override
			public void run() {
								
				if(timer == 0) {
					player.setGameMode(pastGamemode);
					sched.cancelTask(ghostTask);
				}
				
				else if(timer <= 3)
					player.sendMessage(ChatColor.RED + "" + timer);
				

				timer--;
				
			}
			
		}, 0, 20L);
		

        
//        Location location = player.getLocation();
//        double yawy = location.getYaw();
//        double yaw = Math.toRadians(-yawy);
//        double plusX = Math.sin(yaw) * 3;
//        double plusZ = Math.cos(yaw) * 3;
//        Location loc = location.clone().add(plusX, 0, plusZ);
//        
//        //player.sendMessage(player.getEyeLocation().getYaw() + "");
//        //player.sendMessage(plusX/3 +", " + plusZ/3);
//        
//		Wolf wolf = (Wolf) player.getWorld().spawnEntity(loc, EntityType.WOLF);
//		wolf.setAI(false);
//		wolf.teleport(loc.setDirection(player.getLocation().getDirection().multiply(-1)));
//		
//		player.playSound(player.getLocation(), Sound.ENTITY_WOLF_AMBIENT, 5, 1);
//		
//		BukkitScheduler sched = player.getServer().getScheduler();
//		sched.runTaskLater(plugin, new Runnable() {
//			
//			@Override
//			public void run() {
//				player.playSound(player.getLocation(), Sound.ENTITY_WOLF_HURT, 5, 1);
//				
//				wolf.setFireTicks(6000);
//				wolf.teleport(loc.setDirection(player.getLocation().getDirection().multiply(-1)));
//				
//			}
//			
//		}, 40L);
//        Location location = player.getLocation();
//        double yawy = location.getYaw();
//        double yaw = Math.toRadians(-yawy);
//        double plusX = Math.sin(yaw);
//        double plusZ = Math.cos(yaw);
//        Location loc = location.clone().add(plusX, 0, plusZ);
//        
//        //player.sendMessage(player.getEyeLocation().getYaw() + "");
//        //player.sendMessage(plusX/3 +", " + plusZ/3);
//        
//		Creeper creeper = (Creeper) player.getWorld().spawnEntity(loc, EntityType.CREEPER);
//		creeper.teleport(loc.setDirection(player.getLocation().getDirection().multiply(-1)));
//		
//		player.playSound(player.getLocation(), Sound.ENTITY_CREEPER_PRIMED, 10, (float) 0.5);
//		
//		BukkitScheduler sched = player.getServer().getScheduler();
//		sched.runTaskLater(plugin, new Runnable() {
//			
//			@Override
//			public void run() {
//				creeper.remove();
//			}
//			
//		}, 29L);
    	
        
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