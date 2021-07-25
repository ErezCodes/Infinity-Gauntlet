package me.erez.IG.Stones;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.GameRule;
//import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Server;
import org.bukkit.SoundCategory;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import me.erez.IG.Glove;
import me.erez.IG.Main;
import me.erez.IG.PastEntities;
import me.erez.IG.TimeStamp;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntityDestroy;

import org.bukkit.event.player.PlayerInteractEvent;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

//variables

public class Reality implements Listener {

	private Main plugin;
	private Material mats[];
	Set<Material> transparent = new HashSet<Material>();

	private boolean enableRewind = false;
	private int loadouts = 0;
	private int rewindMinutes = -1;
	private int rewindTask;
	private World world = Bukkit.getWorlds().get(0);
	
	private ArrayList<HashMap<Player, TimeStamp>> timeStamps = new ArrayList<HashMap<Player, TimeStamp>>();
	private ArrayList<PastEntities> pastEntities = new ArrayList<PastEntities>();
	

	private String missingPower = "You must have the " + ChatColor.DARK_PURPLE + "Power Stone " + ChatColor.WHITE + "in order to use this feature!";
	

	
	//Reality variables
	
	private int blockPage = 0;
	private int itemPage = 0;
	
	private ItemStack blockWish;
	private ItemStack itemWish = null;
	
	
	private ItemStack activate = null;
	private ItemStack cancel = null;
	private ItemStack disable = null;
	
		
	private ItemStack blockChanger = null;
	private ItemStack itemChanger = null;
	
	
	//Space variables
	
	private ItemStack speed = null;
	private ItemStack shortTeleport = null;
	private ItemStack globalTeleport = null;
	
	private int velocity = 2;
	
	private boolean locked = false;
	private Player victim = null;
	
	private Location global = null;
	private double globalX;
	private double globalY = 255;
	private double globalZ;
	
	private Player globalPlayer = null;
	private boolean prompting = false; 
	private Player user = null;
	private boolean step1 = false;
	private boolean step2 = false;
	

	//Power variables
	
	private ItemStack powerStar = null;
	private ItemStack powerSword = null;
	private boolean powerEffect = true;

	
	
	//Time variables
	private ItemStack freezeTimeItem = null;
	private ItemStack clock = null;
	
	private int freezeDuration = 20;
	
	private boolean timeFrozen = false;
	private Player Freezer = null;
	
	
	//Time menus
	private Inventory timeSettingsInv = null;
	private Inventory rewindInv = null;
	private Inventory dayInv = null;
	
	
	//Stone menus
	private Inventory spaceInv = null;
	private Inventory mindInv = null;
	private Inventory realityInv = null;
	private Inventory powerInv = null;
	private Inventory timeInv = null;
	private Inventory soulInv = null;
	
	//Stone features
	
	//Space
	private Inventory speedInv = null;
	private Inventory shortInv = null;
	private Inventory choosePlayerInv = null;
	private Inventory everyoneInv = null;
	
	
	
	
	//Reality
	private Inventory blockInv;
	private Inventory itemInv;	
	
	
	
	
	//Array lists
	private ArrayList<Material> blocks = new ArrayList<Material>();
	private ArrayList<Material> items = new ArrayList<Material>();

	
	
	//tasks
	private int projectileTask;
	private int projectileCD;
	
	private int snowShoot;
	
	private int freezeTask;
	
	//booleans
	boolean projectileBoolean = false;
	
	//numeric cooldowns
	private double projectileDouble = 0.00;
	
	public Reality(Main plugin) {

		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
		this.mats = Material.values();
		
		//general
		activate = createGuiItem(Material.LIME_WOOL, ChatColor.GREEN + "Activate",
				ChatColor.DARK_GREEN + "Activate the item feature");
		cancel = createGuiItem(Material.RED_WOOL, ChatColor.RED + "Cancel",
				ChatColor.DARK_RED + "Go back");
		disable = createGuiItem(Material.BARRIER, ChatColor.RED + "Disable stone",
				ChatColor.DARK_RED + "Disable the stone");
		
		
		//time
		freezeTimeItem = createGuiItem(Material.ICE, ChatColor.GREEN + "Time Freezer", 
				ChatColor.DARK_GREEN + "Stop the clock for 20-40 seconds");
		clock = createGuiItem(Material.CLOCK, ChatColor.GREEN + "Time settings",
				ChatColor.DARK_GREEN + "Adjust the time to your taste");
		
		//space
		speed = createGuiItem(Material.SUGAR, ChatColor.BLUE + "Speed settings", 
				ChatColor.DARK_BLUE + "Control and adjust your speed");
		shortTeleport = createGuiItem(Material.BEACON, ChatColor.BLUE + "Short Telportation", 
				ChatColor.DARK_BLUE + "Teleport yourself or a player");
		globalTeleport = createGuiItem(Material.WARPED_DOOR, ChatColor.BLUE + "Global Teleportation", 
				ChatColor.DARK_BLUE + "Teleport anywhere you want");

		transparent.addAll(Arrays.asList(new Material[] {Material.AIR, Material.WATER, Material.CAVE_AIR, Material.TALL_GRASS}));
		
		
		//reality
		blockWish = createGuiItem(Material.BARRIER, ChatColor.RED + "None",
				ChatColor.RED + "You haven't chosen a block");
		itemWish = createGuiItem(Material.BARRIER, ChatColor.RED + "None",
				ChatColor.RED + "You haven't chosen an item");
		
		blockChanger = createGuiItem(Material.GRASS_BLOCK, (ChatColor.RED + "Block changer"), 
		(ChatColor.DARK_RED + "Transform a block into whatever you want"));
		itemChanger = createGuiItem(Material.GOLDEN_PICKAXE, (ChatColor.RED + "Item Changer"),
				(ChatColor.DARK_RED + "Transform anything into your wish item"));
		
		
		//power
		powerStar = createGuiItem(Material.NETHER_STAR, ChatColor.DARK_PURPLE + "Power projectiles",
				ChatColor.LIGHT_PURPLE + "Shoot power projectiles");
		powerSword = createGuiItem(Material.IRON_SWORD, ChatColor.DARK_PURPLE + "Toggle Power Effect",
				ChatColor.LIGHT_PURPLE + "Toggle on and off your strength IV effect");
		
		
		loadBlocks();
		loadItems();

		
		

	}

	
	
	
	
	
	
	
	// Events // Events // Events // Events // Events // Events // Events // Events 

	
	
	
	
	
	
	@EventHandler
	public void join(PlayerJoinEvent event) {
		Glove glove = new Glove(event.getPlayer(), "none", false, false, false, false, false, false);
		plugin.gloves.add(glove);

	}

	@EventHandler
	public void quit(PlayerQuitEvent event) {
		plugin.gloves.remove(findPlayer(event.getPlayer()));
	}

	@EventHandler()
	public void interact(PlayerInteractEvent event) {

		Player p = event.getPlayer();
		
		if(timeFrozen && p != Freezer) {
			event.setCancelled(true);
			p.sendMessage(ChatColor.RED + "You can't do that while time is frozen");
			return;
		}
		
		ArrayList<String> lore = (ArrayList<String>) p.getInventory().getItemInOffHand().getItemMeta().getLore();

		//open glove menu
		if ( (lore.get(0).equals(ChatColor.DARK_RED + "Must collect them all...")
				&& (event.getAction().equals(Action.RIGHT_CLICK_AIR)
						|| event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) ) && (
				p.getInventory().getItemInMainHand().getType().equals(Material.AIR) ||
				p.getInventory().getItemInMainHand().getType().equals(null) ) ) {

			plugin.gloves.get(findPlayer(p)).generateInventory();

		}
		
		//block changer
		if( (lore.get(0).equals(ChatColor.DARK_RED + "Must collect them all...") && event.getAction().equals(Action.LEFT_CLICK_BLOCK)
				&& plugin.gloves.get(findPlayer(p)).getEquipped().equals("blockChanger"))
					&& plugin.gloves.get(findPlayer(p)).getReality()) {
			
					if(p.getGameMode().equals(GameMode.CREATIVE)) {
						p.sendMessage("You have to be in survival or adventure mode for this feature to work");
						return;
					}
			
					Block b = p.getTargetBlock(null, 6);
					Location bloc = b.getLocation();
					Material bmat = b.getType();
					if(bmat == Material.AIR || bmat == Material.CAVE_AIR) {
						p.sendMessage(ChatColor.RED + "Please look at a physical block");
					}
					else {
						bloc.getBlock().setType(blockWish.getType());
						p.sendMessage(ChatColor.DARK_RED + "Your wish came true");
					}
					
		}
		
		//self short teleport
		if(lore.get(0).equals(ChatColor.DARK_RED + "Must collect them all...") && 
				(event.getAction().equals(Action.LEFT_CLICK_AIR) || event.getAction().equals(Action.LEFT_CLICK_BLOCK)) &&
				plugin.gloves.get(findPlayer(p)).getEquipped().equals("selfShort")
					&& plugin.gloves.get(findPlayer(p)).getSpace() 
					&& (p.getOpenInventory().getType() == InventoryType.CRAFTING || p.getOpenInventory().getType() == InventoryType.CREATIVE) 
					&& ((p.getInventory().getItemInMainHand().getType().equals(Material.AIR)) || p.getInventory().getItemInMainHand().equals(null))) {
			

			
			Location loc = p.getLocation();
			Location target = p.getTargetBlock(null, 30).getLocation().clone().add(0, 1, 0);
			target.setYaw(loc.getYaw());
			target.setPitch(loc.getPitch());
			p.teleport(target);
			return;
			
		}
		
		//targetedShort teleport
		if(lore.get(0).equals(ChatColor.DARK_RED + "Must collect them all...") &&
				event.getAction().equals(Action.LEFT_CLICK_AIR) && plugin.gloves.get(findPlayer(p)).getSpace() &&
				plugin.gloves.get(findPlayer(p)).getEquipped().equals("targetedShort")
				&& (p.getOpenInventory().getType() == InventoryType.CRAFTING || p.getOpenInventory().getType() == InventoryType.CREATIVE)
				&& ((p.getInventory().getItemInMainHand().getType().equals(Material.AIR)) || p.getInventory().getItemInMainHand().equals(null))) {
			
			if(!locked) {
				
				for(int i = 1; i <=10 ; i++) {
				
					Block b = p.getTargetBlock(transparent, 5 * i);
					

					
					for(Player targeted : Bukkit.getOnlinePlayers()){
						
						if(p != targeted) {
							
							if( b.getLocation().distance(targeted.getLocation()) <= 4) {
								
								locked = true;
								victim = targeted;
								p.sendMessage("You are targeting " + ChatColor.DARK_RED + victim.getName());
								return;
								
							}
							
						}
						
						
					}	
				
				}
				if(locked && victim != null) {
					p.sendMessage("You are targeting " + ChatColor.DARK_RED + victim.getName());
					return;
				}
				
				p.sendMessage(ChatColor.RED + "No one is in your range");
				return;
				
			}
			
			if(locked) {
				
				Location loc = p.getLocation();
				Location target = p.getTargetBlock(null, 30).getLocation().clone().add(0, 1, 0);
				target.setYaw(loc.getYaw());
				target.setPitch(loc.getPitch());
				victim.teleport(target);
				victim.sendMessage(ChatColor.BLUE + (p.getName() + " has used the Space Stone against you"));
				p.sendMessage(ChatColor.BLUE + "You have successfully teleported " + victim.getName());
				
				victim = null;
				locked = false;
				
				return;
				
				
			}
			
			
			
			

			
			
		}
		
		
		
		//Power projectile
		if((lore.get(0).equals(ChatColor.DARK_RED + "Must collect them all...") &&
			(event.getAction().equals(Action.LEFT_CLICK_AIR) || event.getAction().equals(Action.LEFT_CLICK_BLOCK)) 
				&& plugin.gloves.get(findPlayer(p)).getEquipped().equals("Power")
				&& (p.getOpenInventory().getType() == InventoryType.CRAFTING || p.getOpenInventory().getType() == InventoryType.CREATIVE)
				&& (p.getInventory().getItemInMainHand().getType().equals(Material.AIR) || p.getInventory().getItemInMainHand().getType().equals(null)))){
			
			
			//loc.getWorld().spawnParticle(Particle.REDSTONE ,loc.getX(),loc.getY(),loc.getZ(),50,0,0,0,0,new Particle.DustOptions(Color.AQUA,1));
//			Location loc = p.getEyeLocation();
//			Vector direction = loc.getDirection();
//			p.getWorld().spawnParticle(Particle.REDSTONE, loc.getX(), loc.getY(), loc.getZ(), 50, (double) direction.getX(), (double) direction.getY(), (double) direction.getZ(), 0 , new Particle.DustOptions(Color.PURPLE, 1));

			if(projectileBoolean) {
				double cd = 5 - projectileDouble/20;
				String cdString = new DecimalFormat("#.#").format(cd);
				p.sendMessage((ChatColor.RED + "Cooldown! Please wait ") + (ChatColor.DARK_RED + cdString + "s") );
				return;
			}
			
			Snowball snowball = (Snowball) p.getWorld().spawn(p.getEyeLocation(), Snowball.class);
			snowball.setBounce(false);
			
			BukkitScheduler sched = p.getServer().getScheduler();
			
			shootParticle(p, Particle.REDSTONE, 7f, new Particle.DustOptions(Color.PURPLE, 1));
			projectileBoolean = true;

			for(Player player : Bukkit.getServer().getOnlinePlayers()) {
				PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(snowball.getEntityId());
				
				((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);;
                snowball.setShooter((ProjectileSource) ((LivingEntity) p));
                snowball.setVelocity(p.getEyeLocation().getDirection().multiply(3D));
                snowball.setBounce(false);
                snowball.setSilent(true);
			}
			
			projectileTask = sched.scheduleSyncRepeatingTask(plugin, new Runnable() {
			
			

				@Override
				public void run() {
					
					Location snowloc = snowball.getLocation();
					
					
					//tickslived, dead
					if(snowball.isDead() || snowball.getTicksLived() >= 100) {
						
						sched.cancelTask(projectileTask);
						return;
						
					}
					
					followTrail(snowloc, snowloc.getDirection(), Particle.REDSTONE, 7f, new Particle.DustOptions(Color.PURPLE, 1));
					
	
					
				}
				
			}, 0L, 1L);
			
			
			
			projectileCD = sched.scheduleSyncRepeatingTask(plugin, new Runnable() {
				
				
				
				@Override
				public void run() {
					projectileDouble += 2;
					if(projectileDouble == 100.00) {
						projectileBoolean = false;
						projectileDouble = 0.00;
						sched.cancelTask(projectileCD);
					}
					
				}
				
			}, 0L, 2L);
			
			
			
		}
		
		
		
		
		//global teleport
		if((lore.get(0).equals(ChatColor.DARK_RED + "Must collect them all...") &&
				(event.getAction().equals(Action.LEFT_CLICK_AIR) || event.getAction().equals(Action.LEFT_CLICK_BLOCK)) 
					&& plugin.gloves.get(findPlayer(p)).getEquipped().equals("global")
					&& (p.getOpenInventory().getType() == InventoryType.CRAFTING || p.getOpenInventory().getType() == InventoryType.CREATIVE)
					&& (p.getInventory().getItemInMainHand().getType().equals(Material.AIR) || p.getInventory().getItemInMainHand().getType().equals(null)))){
			
			
			globalPlayer.teleport(global);
			
			
		}
		
		//Time Freezer
		if((lore.get(0).equals(ChatColor.DARK_RED + "Must collect them all...") &&
				(event.getAction().equals(Action.LEFT_CLICK_AIR) || event.getAction().equals(Action.LEFT_CLICK_BLOCK)) 
					&& plugin.gloves.get(findPlayer(p)).getEquipped().equals("Time")
					&& (p.getOpenInventory().getType() == InventoryType.CRAFTING || p.getOpenInventory().getType() == InventoryType.CREATIVE)
					&& (p.getInventory().getItemInMainHand().getType().equals(Material.AIR) || p.getInventory().getItemInMainHand().getType().equals(null)))){
			
			if(timeFrozen) {
				return;
			}
			
			timeFrozen = true;
			Freezer = p;
			Bukkit.broadcastMessage(ChatColor.GREEN + p.getName() + " has frozen time for " + ChatColor.DARK_GREEN + freezeDuration + " seconds");
			stopTime();
			
		}
		
		//Rewind time
		
		if((lore.get(0).equals(ChatColor.DARK_RED + "Must collect them all...") &&
				(event.getAction().equals(Action.LEFT_CLICK_AIR) || event.getAction().equals(Action.LEFT_CLICK_BLOCK)) 
					&& plugin.gloves.get(findPlayer(p)).getEquipped().equals("Rewind")
					&& (p.getOpenInventory().getType() == InventoryType.CRAFTING || p.getOpenInventory().getType() == InventoryType.CREATIVE)
					&& (p.getInventory().getItemInMainHand().getType().equals(Material.AIR) || p.getInventory().getItemInMainHand().getType().equals(null)))){
		
		
		/*
		for(int i = loadouts; i <= 10 - rewindMinutes; i += 0) {
		
			for(Player player : timeStamps.get(i).keySet()) {
				
				TimeStamp timey = timeStamps.get(i).get(player);
				timey.restoreBrokenBlocks();
				timey.restorePlacedBlocks();
				
				if(i == rewindMinutes) {
					timey.restoreGlove();
					timey.restorePlayer();
					pastEntities.get(i).restoreEntities();
					pastEntities.get(i).restoreChests();
				}
				
			}
			
			loadouts--;
			
			
		}
		*/
		
		//let everyone know that the server was rewinded
		for(Player player : Bukkit.getOnlinePlayers()) {
			if(player != p)
				player.sendTitle(ChatColor.GREEN + p.getName() + " has reversed time in " + rewindMinutes + " minutes!", "", 1, 6, 1);
			
		}

		
			
			
			
		}
		
		

	}

	

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInventoryClick(final InventoryClickEvent e) {
		final Player p = (Player) e.getWhoClicked();
		
		if(timeFrozen && p != Freezer) {
			e.setCancelled(true);
			p.sendMessage(ChatColor.RED + "You can't do that while time is frozen");
			return;
		}
		
		// gloves
		if (e.getView().getTitle().equals(ChatColor.DARK_GREEN + (p.getName() + "'s glove"))) {
			
			Glove glove = plugin.gloves.get(findPlayer(p));
			
			if (e.getRawSlot() != 10 && e.getRawSlot() != 13 && e.getRawSlot() != 16 && e.getRawSlot() != 37
					&& e.getRawSlot() != 40 && e.getRawSlot() != 43 && e.getRawSlot() < 54) {
				e.setCancelled(true);
				return;
			}
			
			if(e.getRawSlot() == 10 ) {
				
				if(glove.getSpace()) {
					e.setCancelled(true);
					generateSpaceInv(p);
					
					
				} else {
					
					if(e.getCursor().getType().equals(Material.AIR) || e.getCursor().getType().equals(null))
						return;
					
					if(e.getCursor().getType().equals(Material.LAPIS_LAZULI) && e.getCursor().getItemMeta()
							.equals(plugin.SpaceStone.getItemMeta())) {
						e.setCursor(new ItemStack(Material.AIR, 1));
						acquiredSpaceStone(p);
						
						return;
						
					} else {
						
						e.setCancelled(true);
						p.sendMessage(ChatColor.RED + "Invalid stone");
						return;
						
					}
					
					
					
				}
				
				
			}
			
			if(e.getRawSlot() == 13) {
				
				if(glove.getMind()) {
					e.setCancelled(true);
					generateMindInv(p);
					
				} else {
					
					if(e.getCursor().getType().equals(Material.AIR) || e.getCursor().getType().equals(null))
						return;
					
					if(e.getCursor().getType().equals(Material.YELLOW_DYE) && e.getCursor().getItemMeta()
							.equals(plugin.MindStone.getItemMeta())) {
						e.setCursor(new ItemStack(Material.AIR, 1));
						acquiredMindStone(p);
						return;
					} else {
						
						e.setCancelled(true);
						p.sendMessage(ChatColor.RED + "Invalid stone");
						return;
						
					}
					
					
				}
				
				
				
			}
			
			if(e.getRawSlot() == 16) {
				
				if(glove.getReality()) {
					e.setCancelled(true);
					generateRealityInv(p);
					
				} else {
					
					if(e.getCursor().getType().equals(Material.AIR) || e.getCursor().getType().equals(null))
						return;
					
					if(e.getCursor().getType().equals(Material.REDSTONE) && e.getCursor().getItemMeta()
							.equals(plugin.RealityStone.getItemMeta())) {
						e.setCursor(new ItemStack(Material.AIR, 1));
						acquiredRealityStone(p);
						return;
					} else {
						
						e.setCancelled(true);
						p.sendMessage(ChatColor.RED + "Invalid stone");
						return;
						
					}
					
					
				}
				
				
				
			}
			
			if(e.getRawSlot() == 37) {
				
				if(glove.getPower()) {
					e.setCancelled(true);
					generatePowerInv(p);
					
				} else {
					
					if(e.getCursor().getType().equals(Material.AIR) || e.getCursor().getType().equals(null))
						return;
					
					if(e.getCursor().getType().equals(Material.PURPLE_DYE) && e.getCursor().getItemMeta()
							.equals(plugin.PowerStone.getItemMeta())) {
						e.setCursor(new ItemStack(Material.AIR, 1));
						acquiredPowerStone(p);
						return;
					} else {
						
						e.setCancelled(true);
						p.sendMessage(ChatColor.RED + "Invalid stone");
						return;
						
					}
					
					
				}
				
				
				
			}
			
			if(e.getRawSlot() == 40) {
				
				if(glove.getTime()) {
					e.setCancelled(true);
					generateTimeInv(p);
					
				} else {
					
					if(e.getCursor().getType().equals(Material.AIR) || e.getCursor().getType().equals(null))
						return;
					
					if(e.getCursor().getType().equals(Material.EMERALD) && e.getCursor().getItemMeta()
							.equals(plugin.TimeStone.getItemMeta())) {
						e.setCursor(new ItemStack(Material.AIR, 1));
						acquiredTimeStone(p);
						return;
					} else {
						
						e.setCancelled(true);
						p.sendMessage(ChatColor.RED + "Invalid stone");
						return;
						
					}
					
					
				}
				
				
				
			}
			
			if(e.getRawSlot() == 43) {
				
				if(glove.getSoul()) {
					e.setCancelled(true);
					generateSoulInv(p);
					
				} else {
					
					if(e.getCursor().getType().equals(Material.AIR) || e.getCursor().getType().equals(null))
						return;
					
					if(e.getCursor().getType().equals(Material.ORANGE_DYE) && e.getCursor().getItemMeta()
							.equals(plugin.SoulStone.getItemMeta())) {
						e.setCursor(new ItemStack(Material.AIR, 1));
						acquiredSoulStone(p);
						return;
					} else {
						
						e.setCancelled(true);
						p.sendMessage(ChatColor.RED + "Invalid stone");
						return;
						
					}
					
					
				}
				
				
				
			}
			


		}
		
		
		
		//REALITY STONE	//REALITY STONE //REALITY STONE 
		if(e.getInventory() == realityInv) {
			e.setCancelled(true);
			
			if(e.getCurrentItem().equals(blockChanger)) {
				generateBlockInventory(p);
				return;
			}
			
			if(e.getCurrentItem().equals(itemChanger)) {
				generateItemInventory(p);
				return;
			}
			if(e.getCurrentItem().equals(disable)) {
				plugin.gloves.get(findPlayer(p)).setEquipped("none");
				p.sendMessage("You have disabled the reality stone");
				plugin.gloves.get(findPlayer(p)).generateInventory();
				return;
			}
			
		}
		
		// blockInv
		if (e.getInventory() == blockInv) {

			e.setCancelled(true);

			final ItemStack clickedItem = e.getCurrentItem();

			// verify current item is not null
			if (clickedItem == null || clickedItem.getType() == Material.AIR)
				return;

			// none of the above
			if (e.getRawSlot() < 45) {
				// p.sendMessage("none of the above");
//				p.sendMessage("" + (blockPage * 45 + e.getRawSlot()));
				blockWish = null;
				String itemName = clickedItem.getType().name();
				itemName = itemName.replaceAll("_", " ");
				blockWish = createGuiItem(clickedItem.getType(), itemName, "Your desired block");
				blockInv.setItem(49, blockWish);

			}

			// next page
			else if (clickedItem.getItemMeta().getLore().get(0).equals("Go to the next page")) {
				blockPage++;
				generateBlockInventory(p);
				p.updateInventory();
			}
			// previous page
			else if (clickedItem.getItemMeta().getLore().get(0).equals("Go to the previous page")) {
				blockPage--;
				generateBlockInventory(p);
				p.updateInventory();
			}
			//confirm
			else if(clickedItem.getItemMeta().equals(activate.getItemMeta())) {
				plugin.gloves.get(findPlayer(p)).setEquipped("blockChanger");
				p.closeInventory();
				p.sendMessage(ChatColor.DARK_RED + "Left click a block in order to turn it into your desired block");
			}
			//cancel
			else if(clickedItem.getItemMeta().equals(cancel.getItemMeta())) {
				p.openInventory(realityInv);
			}

		}
		// ItemInv
		if (e.getInventory() == itemInv) {
			
			e.setCancelled(true);
			
			final ItemStack clickedItem = e.getCurrentItem();
			
			if (clickedItem == null || clickedItem.getType() == Material.AIR)
				return;
			if (e.getRawSlot() < 45) {
				itemWish = null;
				String itemName = clickedItem.getType().name();
				itemName = itemName.replaceAll("_", " ");
				itemWish = createGuiItem(clickedItem.getType(), itemName, "Your desired item");
				itemInv.setItem(49, itemWish);
			}
			
			// next page
			else if (clickedItem.getItemMeta().getLore().get(0).equals("Go to the next page")) {
				itemPage++;
				generateItemInventory(p);
				p.updateInventory();
			}
			// previous page
			else if (clickedItem.getItemMeta().getLore().get(0).equals("Go to the previous page")) {
				itemPage--;
				generateItemInventory(p);
				p.updateInventory();
			}
			//confirm
			else if(clickedItem.getItemMeta().equals(activate.getItemMeta())) {
				plugin.gloves.get(findPlayer(p)).setEquipped("itemChanger");
				p.closeInventory();
				p.sendMessage("Drop an item in order to turn it into your desired item");
				
			}
			//cancel
			else if(clickedItem.getItemMeta().equals(cancel.getItemMeta())) {
				p.openInventory(realityInv);
			}
			
		}
		
		
		
		
		//STRENGTH STONE //STRENGTH STONE //STRENGETH STONE

		if(e.getInventory() == powerInv) {
			
			if(e.getCurrentItem().equals(powerSword)) {
				powerEffect = !powerEffect;
				if(powerEffect) {
					addEmptyEnchantment(powerInv.getItem(21));
					p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 2));
					p.closeInventory();
					p.sendMessage(ChatColor.DARK_PURPLE + "You have enabled your power effect");
					return;
				}
				
				if(!powerEffect) {
					removeEnchantments(powerInv.getItem(21));
					p.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
					p.closeInventory();
					p.sendMessage(ChatColor.DARK_PURPLE + "You have disabled your power effect");
					return;
				}
				
				
			}
			
			if(e.getCurrentItem().equals(powerStar)) {
				p.closeInventory();
				plugin.gloves.get(findPlayer(p)).setEquipped("Power");
				p.sendMessage((ChatColor.DARK_PURPLE + "Click in order to shoot powerful projectiles. ") + "\n" + 
				ChatColor.LIGHT_PURPLE + "Make that you aren't holding anything!");
				return;
			}
			
			if(e.getCurrentItem().equals(disable)) {
				p.closeInventory();
				plugin.gloves.get(findPlayer(p)).setEquipped("none");
				p.sendMessage("You have disabled the power stone");
				return;
			}
			
			
		}
		
	
		
		
		//SPACE STONE //SPACE STONE //SPACE STONE
		
		//menu
		if(e.getInventory().equals(spaceInv)) {
			
			e.setCancelled(true);
			
			if(e.getCurrentItem().equals(speed)) {
				generateSpeedInventory(p);
				return;
			}
			
			if(e.getCurrentItem().equals(shortTeleport)) {
				generateShortInventory(p);
				return;
			}
			

			
			//doesn't have full potential
			if(e.getCurrentItem().getItemMeta().getLore().get(0).equals(globalTeleport.getItemMeta().getLore().get(0))) {
				
				if(e.getCurrentItem().getType().equals(Material.WARPED_DOOR)) {
					generateChoosePlayerInventory(p);
					return;
				}
				
				if(e.getCurrentItem().getType().equals(Material.GRAY_DYE)) {
					p.sendMessage(missingPower);
					p.closeInventory();
					return;
				}
				

				
			}
			

			
			if(e.getCurrentItem().equals(disable)) {
				plugin.gloves.get(findPlayer(p)).setEquipped("none");
				p.sendMessage("You have disabled the space stone");
				plugin.gloves.get(findPlayer(p)).generateInventory();
				return;
			}
			
			return;
			
		}
		
		//speed
		if(e.getInventory().equals(speedInv)) {
			
			e.setCancelled(true);
			
			if(e.getCurrentItem().getType().equals(Material.GRAY_DYE)) {
				p.sendMessage(missingPower);
				p.closeInventory();
				return;
			}
			
			if(e.getCurrentItem().getType().equals(Material.GUNPOWDER)) {
				
				int slot = -1;
				
				if(velocity == 0) {
					return;
				}
				
				else if(velocity == 2)
					slot = 12;
				else if(velocity == 3)
					slot = 13;
				else if(velocity == 4)
					slot = 14;
				else if(velocity == 5)
					slot = 15;
				else if(velocity == 6)
					slot = 20;
				else if(velocity == 7)
					slot = 21;
				else if(velocity == 8)
					slot = 22;
				else if(velocity == 9)
					slot = 23;
				else if(velocity == 10)
					slot = 24;
				
				speedInv.getItem(slot).setType(Material.SUGAR);
				addEmptyEnchantment(e.getCurrentItem());
				
				velocity = 0;
				
				return;
			}
			
			if(e.getCurrentItem().getType().equals(Material.SUGAR)) {
				//11,12,13,14,15 ; 20,21,22,23,24
				
				int slot = -1;
				
				if(velocity == 0)
					slot = 11;
				else if(velocity == 2)
					slot = 12;
				else if(velocity == 3)
					slot = 13;
				else if(velocity == 4)
					slot = 14;
				else if(velocity == 5)
					slot = 15;
				else if(velocity == 6)
					slot = 20;
				else if(velocity == 7)
					slot = 21;
				else if(velocity == 8)
					slot = 22;
				else if(velocity == 9)
					slot = 23;
				else if(velocity == 10)
					slot = 24;
				
				if(slot != 11) {
					speedInv.getItem(slot).setType(Material.SUGAR);
					
				}
				else {
					//speedInv.getItem(slot).getItemMeta().removeEnchant(Enchantment.WATER_WORKER);
					removeEnchantments(speedInv.getItem(slot));
				}
				
				velocity = e.getCurrentItem().getAmount();
				
				
				e.getCurrentItem().setType(Material.GLOWSTONE_DUST);
				
				return;
			}
			
			if(e.getCurrentItem().equals(activate)) {
				p.closeInventory();
				p.removePotionEffect(PotionEffectType.SPEED);
				p.sendMessage(ChatColor.BLUE + "You have set your speed amplification to " + velocity);
				if(velocity != 0) {
					p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 99999, velocity - 1));
				}
				
				return;
				
			}
			
			if(e.getCurrentItem().equals(cancel)) {
				p.openInventory(spaceInv);
				return;
			}
			
			
		}
		
		//short
		if(e.getInventory() == shortInv) {
			e.setCancelled(true);
			
			if(e.getRawSlot() == 21){
				p.closeInventory();
				p.sendMessage(ChatColor.BLUE + "Click to teleport yourself to where you look");
				plugin.gloves.get(findPlayer(p)).setEquipped("selfShort");
				return;
			}
			
			if(e.getRawSlot() == 23) {
				p.closeInventory();
				p.sendMessage(ChatColor.RED + "Click while looking at your target, "
						+ "then click again to teleport your victim to wherever you look");
				plugin.gloves.get(findPlayer(p)).setEquipped("targetedShort");
				return;
			}
			
			if(e.getRawSlot() == 36) {
				p.openInventory(spaceInv);
				return;
			}
			
			
			
		}
		
		//choosePlayerInv //global
		if(e.getInventory() == choosePlayerInv) {
			
			e.setCancelled(true);
			
			//self
			if(e.getRawSlot() == 21) {
//				SkullMeta meta = (SkullMeta)e.getCurrentItem().getItemMeta();
//				globalPlayer = (Player)meta.getOwningPlayer();
				globalPlayer = p;
				
				p.closeInventory();
				p.sendMessage(ChatColor.BLUE + "Please type your desired X");
				p.sendMessage(ChatColor.RED + "If you would like to cancel this action, type 'Cancel'");
				prompting = true;
				step1 = true;
				user = p;
				
				return;
			}
			
			//different player
			if(e.getRawSlot() == 23) {

				everyPlayer(p);
				
				return;
			}
			
			if(e.getRawSlot() == 36) {
				p.openInventory(spaceInv);
				return;
			}
			
			return;
			
		}
		
		if(e.getInventory() == everyoneInv) {
			
			e.setCancelled(true);
			
			SkullMeta meta = (SkullMeta)e.getCurrentItem().getItemMeta();
			globalPlayer = (Player)meta.getOwningPlayer();
			
		
			p.closeInventory();
			p.sendMessage(ChatColor.BLUE + "Please type your desired X");
			p.sendMessage(ChatColor.RED + "If you would like to cancel this action, type 'Cancel'");
			prompting = true;
			step1 = true;
			user = p;
		}
		
		
		
		
		//TIME STONE //TIME SONE //TIME STONE
		
		//menu
		if(e.getInventory().equals(timeInv)) {
			
			e.setCancelled(true);
			
			if(e.getCurrentItem().equals(freezeTimeItem)) {
				plugin.gloves.get(findPlayer(p)).setEquipped("Time");
				p.closeInventory();
				p.sendMessage(ChatColor.GREEN + "Hit the air in order to freeze the time for " +
				ChatColor.DARK_GREEN + freezeDuration + " seconds");
				p.sendMessage(ChatColor.LIGHT_PURPLE + "Note that you are unable to damage players and entities while time is frozen");
				return;
			}
			
			if(e.getCurrentItem().equals(clock)) {
				generateTimeSettingsInventory(p);
				return;
			}
			
			if(e.getCurrentItem().equals(disable)) {
				plugin.gloves.get(findPlayer(p)).setEquipped("none");
				p.sendMessage("You have disabled the time stone");
				plugin.gloves.get(findPlayer(p)).generateInventory();
				return;
			}
			
		}
		//TimeSettingsInv
		if(e.getInventory().equals(timeSettingsInv)) {
			e.setCancelled(true);
			
			
			if(e.getCurrentItem().equals(cancel)) {
				generateTimeInv(p);
				return;
			}
			
			if(e.getRawSlot() == 21) { //Day settings
				generateDayInv(p);
				return;
			}
			
			if(e.getRawSlot() == 23) { //Rewind
				generateRewindInv(p);
				return;
			}
			
		}
		//rewind
		if(e.getInventory().equals(rewindInv)) {
			e.setCancelled(true);
			
			if(e.getCurrentItem().getType().equals(Material.BOOK)) {
				/*
				for(int i = 0; i < 5; i++) {
					Material mat = rewindInv.getItem(i + 11).getType();
					if (mat.equals(Material.AIR) || mat.equals(null))
						break;
					else removeEnchantments(rewindInv.getItem(i + 11));
				}
				
				for(int i = 0; i < 5; i++) {
					Material mat = rewindInv.getItem(i + 11).getType();
					if (mat.equals(Material.AIR) || mat.equals(null))
						break;
					else removeEnchantments(rewindInv.getItem(i + 20));
				}
				*/
				
				if(rewindMinutes != -1) {
					
					int addition;
					if(rewindMinutes > 5)
						addition = 14;
					else addition = 10;
					
					removeEnchantments(rewindInv.getItem(rewindMinutes + addition));
					
				}
				
				int amount = e.getCurrentItem().getAmount();
				rewindMinutes = amount;
				addEmptyEnchantment(e.getCurrentItem());

			}
			
			if(e.getCurrentItem().equals(activate)) {
				p.closeInventory();
				p.sendMessage(ChatColor.GREEN + "Hit the air in order to go back in time with everyone");
				plugin.gloves.get(findPlayer(p)).setEquipped("Rewind");
			}
			
			if(e.getCurrentItem().equals(cancel)) {
				p.closeInventory();
				return;
			}
			
			
		}
		//day settings
		if(e.getInventory().equals(dayInv)) {
			e.setCancelled(true);
		}
		

		
		
		


	}

	@EventHandler
	public void onInventoryDrag(final InventoryDragEvent e) {
		Player p = (Player) e.getWhoClicked();
		if (e.getInventory() == blockInv || e.getInventory() == realityInv) {
			e.setCancelled(true);
			return;
		}

		if (e.getView().getTitle().equals(ChatColor.DARK_GREEN + (p.getName() + "'s glove"))) {
			
			Set<Integer> slots = e.getInventorySlots();
			Iterator<Integer> iterator = slots.iterator();
			
			int slot = iterator.next();
			
			if (slot == 10) {
				
				if (e.getNewItems().get(10).getType().equals(Material.LAPIS_LAZULI) && 
						e.getNewItems().get(10).getItemMeta().equals(plugin.SpaceStone.getItemMeta())) {
						acquiredSpaceStone(p);
						return;
					}
				
					else {
						e.setCancelled(true);
						p.sendMessage(ChatColor.RED + "Invalid stone");
						return;
					
					}
				
				
			}
			
			if (slot == 13) {
				
				if (e.getNewItems().get(13).getType().equals(Material.YELLOW_DYE) &&
						e.getNewItems().get(13).getItemMeta().equals(plugin.MindStone.getItemMeta())) {
					acquiredMindStone(p);
					return;
				}
				else {
					e.setCancelled(true);
					p.sendMessage(ChatColor.RED + "Invalid stone");
					return;
				}
				
			}
			
			if (slot == 16) {
				
				if (e.getNewItems().get(16).getType().equals(Material.REDSTONE) &&
						e.getNewItems().get(16).getItemMeta().equals(plugin.RealityStone.getItemMeta())) {
						acquiredRealityStone(p);
						return;
					}
					else {
						e.setCancelled(true);
						p.sendMessage(ChatColor.RED + "Invalid stone");
						return;
					}
				
				
			}
			
			if (slot == 37) {
				
				if (e.getNewItems().get(37).getType().equals(Material.PURPLE_DYE) &&
						e.getNewItems().get(37).getItemMeta().equals(plugin.PowerStone.getItemMeta())) {
					
						acquiredPowerStone(p);
						return;
					}
					else {
						e.setCancelled(true);
						p.sendMessage(ChatColor.RED + "Invalid stone");
						return;
					}
			
				
			}
			
			if (slot == 40) {
				
				if (e.getNewItems().get(40).getType().equals(Material.EMERALD) && 
						e.getNewItems().get(40).getItemMeta().equals(plugin.TimeStone.getItemMeta())) {
					
						acquiredTimeStone(p);
						return;
					}
					else {
						e.setCancelled(true);
						p.sendMessage(ChatColor.RED + "Invalid stone");
						return;
					}
				
				
			}
			
			if (slot == 43) {
				
				if (e.getNewItems().get(43).getType().equals(Material.ORANGE_DYE) && 
						e.getNewItems().get(43).getItemMeta().equals(plugin.SoulStone.getItemMeta())) {
					
						acquiredSoulStone(p);
						return;
					}
					else {
						e.setCancelled(true);
						p.sendMessage(ChatColor.RED + "Invalid stone");
						return;
					}
				
				
			}
			
			
			
		}

	}
	
	@EventHandler()
	public void dropItem(PlayerDropItemEvent event) {
		Player p = event.getPlayer();
		if(timeFrozen && p != Freezer) {
			event.setCancelled(true);
			p.sendMessage(ChatColor.RED + "You can't move while the time is frozen");
			return;
		}
		Glove glove = plugin.gloves.get(findPlayer(p));
		if(glove.getReality() && itemWish != null && glove.getEquipped().equals("itemChanger")){
			itemWish.setAmount(event.getItemDrop().getItemStack().getAmount());
			event.getItemDrop().setItemStack(itemWish);
			ItemMeta meta = event.getItemDrop().getItemStack().getItemMeta();
			meta.setLore(Arrays.asList(ChatColor.DARK_PURPLE + "You got a wishful thinking"));
			event.getItemDrop().getItemStack().setItemMeta(meta);
			p.sendMessage(ChatColor.DARK_RED + "Your wish came true");
		}
		
	}



	//global teleport prompter
	@EventHandler
	public void chat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		if(p == user && prompting) {
			e.setCancelled(true);
			String string = e.getMessage();
			
			if(step2) {
				
				if(string.equalsIgnoreCase("cancel")) {
					step1 = false;
					step2 = false;
					string = null;
					user.sendMessage(ChatColor.RED + "You have cancelled this action");
					user = null;
					global = null;
					globalPlayer = null;
					plugin.gloves.get(findPlayer(p)).setEquipped("none");
					
					return;
				}
				
				int length = string.length();
				
				if(length > 8) {
					p.sendMessage(ChatColor.RED + "Too far");
					return;
				}
				
				for(int i = 0; i < length; i++) {
					char charry = string.charAt(i);
					if(charry == '0' || charry == '1' || charry == '2' || charry == '3' || charry == '4' || charry == '5'
							   || charry == '6' || charry == '7' || charry == '8' || charry == '9' || charry == '.' || charry == '-') {
								//kloom
					}
					else {
						p.sendMessage(ChatColor.RED + "Invalid input");
						p.sendMessage(ChatColor.DARK_RED + "If you would like to cancel this action, type 'Cancel'");
						return;
					}
				}
				
				globalZ = Double.parseDouble(string);
				step2 = false;
				prompting = false;
				
				global = new Location(globalPlayer.getWorld(), globalX, globalY, globalZ); 
				findLand();
				p.sendMessage((ChatColor.GREEN + "Success. Your desired location is ") + (ChatColor.AQUA + "X: " + globalX + " ; " + "Z: " + globalZ) );
				
				p.sendMessage(ChatColor.BLUE + "Hit the air in order to execute the teleport");
				
				plugin.gloves.get(findPlayer(p)).setEquipped("global");
				
				
				
				return;
				
			}
			
			if(step1) {
			
				if(string.equalsIgnoreCase("cancel")) {
					step1 = false;
					step2 = false;
					string = null;
					user.sendMessage(ChatColor.RED + "You have cancelled this action");
					user = null;
					global = null;
					globalPlayer = null;
					
					return;
				}
				
				int length = string.length();
				
				if(length > 8) {
					p.sendMessage(ChatColor.RED + "Too far");
					return;
				}
				
				for(int i = 0; i < length; i++) {
					char charry = string.charAt(i);
					if(charry == '0' || charry == '1' || charry == '2' || charry == '3' || charry == '4' || charry == '5'
					   || charry == '6' || charry == '7' || charry == '8' || charry == '9' || charry == '.' || charry == '-') {
						//kloom
					}
					else {
						p.sendMessage(ChatColor.RED + "Invalid input");
						p.sendMessage(ChatColor.DARK_RED + "If you would like to cancel this action, type 'Cancel'");
						return;
					}
				}
				
				globalX = Double.parseDouble(string);
				step1 = false;
				step2 = true;
				p.sendMessage(ChatColor.BLUE + "Please type your desired Z");
				
				return;
				
				
			}
			

			
		}
		
		e.setCancelled(true);
		p.sendMessage(timeStamps.size() + "");
		p.sendMessage(pastEntities.size() + "");
		
		
	}
	
	@EventHandler
	public void EntityDamageByEntity(EntityDamageByEntityEvent e) {
		if(!(e.getDamager() instanceof Snowball)) return;
		Snowball snowball = (Snowball) e.getDamager();
        LivingEntity shooter = (LivingEntity) snowball.getShooter();
        if (!(shooter instanceof Player)) return;
        Player p = (Player) shooter;
        
        if (!plugin.gloves.get(findPlayer(p)).getEquipped().equals("Power")) return;
		
        e.setDamage(e.getDamage() + 15);
		
	}
	
	@EventHandler
	public void ProjectileLaunch(ProjectileLaunchEvent e) {
	
		if(!(e.getEntity() instanceof Snowball)) return;
		Snowball snowball = (Snowball) e.getEntity();
		if(!(snowball.getShooter() instanceof Player)) return;
		Player p = (Player) snowball.getShooter();
		if (!plugin.gloves.get(findPlayer(p)).getEquipped().equals("Power")) return;
		
		e.setCancelled(true);
		p.sendMessage(ChatColor.DARK_PURPLE + "You are not allowed to throw snowballs while having the power stone equipped");
//		if(!p.getInventory().getItemInMainHand().getType().equals(Material.SNOWBALL)) return;
		
//		BukkitScheduler sched = p.getServer().getScheduler();
//		
//		snowShoot = sched.scheduleSyncRepeatingTask(plugin, new Runnable() {
//			@Override
//			public void run() {
//				
//				
//				
//				if(snowball.isDead() || !p.getInventory().getItemInMainHand().getType().equals(Material.SNOWBALL)) {
//					snowball.remove();
//					p.sendMessage("snowball removed");
//					sched.cancelTask(snowShoot);
//				}
//				
//				
//
//
//				
//				
//			}
//			
//		}, 0L, 1L);
		
	}
	//prevent water and lava spread when time is stopped
	@EventHandler
	public void BlockFromTo(BlockFromToEvent e) {
		if(timeFrozen) {
			e.setCancelled(true);
		}
		else {
			e.setCancelled(false);
		}
	}
	
	@EventHandler
	public void move(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if(timeFrozen && p != Freezer) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void invOpen(InventoryOpenEvent e) {
		Player p = (Player) e.getPlayer();
		if(timeFrozen && p != Freezer) {
			e.setCancelled(true);
			p.sendMessage(ChatColor.RED + "You can't move while the time is frozen");
		}
	}
	
	@EventHandler
	public void kick(PlayerKickEvent e) {
		if(e.getReason().equalsIgnoreCase("Flying is not enabled on this server")) {
			
			e.setCancelled(true);
			
			if(!timeFrozen || e.getPlayer() == Freezer) {
				
				for(Player player : Bukkit.getOnlinePlayers()) {
					if(player.isOp()) {
						player.sendMessage(ChatColor.DARK_RED + "[ALERT] " + (ChatColor.RED + e.getPlayer().getName() + " used a fly hack (or lagged heavily, who knows)"));
					}
				}
				
			}
			
		}
	}
	
	@EventHandler
	public void breakEvent(BlockBreakEvent e) {
		
		if(!enableRewind) return;
		Player player = e.getPlayer();
		timeStamps.get(timeStamps.size() - 1).get(player).getPlacedBlocks().add(e.getBlock().getLocation());
		
	}
	
	@EventHandler
	public void blockPlace(BlockPlaceEvent event) {
		
		if (event.getItemInHand().getItemMeta().getLore().get(0).equals(ChatColor.DARK_RED + "Reality can be whatever I want")) {
			
			event.setCancelled(true);
		}
		
		if(enableRewind) {
			Player player = event.getPlayer();
			timeStamps.get(timeStamps.size() - 1).get(player).getBrokenBlocks().put(event.getBlock().getLocation(), event.getBlock().getType());
		}
		
		
	}
	

	
	
	
	//Functions //Functions //Functions //Functions //Functions //Functions //Functions 
	
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

		for (int i = 0; i < plugin.gloves.size(); i++) {
			if (plugin.gloves.get(i).getPlayer().equals(p))
				return i;
		}

		return -1;
	}
	
	public void shootParticle(Player player, Particle particle, double velocity, Object options) {
	        Location location = player.getEyeLocation();
	        Vector direction = location.getDirection();
	        player.getWorld().spawnParticle(particle, location.getX(), location.getY(), location.getZ(), 60, (float) direction.getX(), (float) direction.getY(), (float) direction.getZ(),velocity , options);
    }
	
	public void followTrail(Location loc, Vector direction, Particle particle, double velocity, Object options) {
		
		loc.getWorld().spawnParticle(particle, loc.getX(), loc.getY(), loc.getZ(), 30, (float) direction.getX(), (float) direction.getY(), (float) direction.getZ(),velocity , options);
    
	}
	
	public void loadBlocks() {
		
		for(int i = 1; i < 569; i++) {
			blocks.add(mats[i]);
		}
		
		for(int i = 934; i < 975; i++) {
			blocks.add(mats[i]); 
		}
		//610 in total
	}
	
	public void loadItems() {
		
		for(int i = 570; i < 934; i++) {
			items.add(mats[i]);
		}
		//365 in total
	}
	
	public void addEmptyEnchantment(ItemStack item) {
		item.addUnsafeEnchantment(Enchantment.WATER_WORKER, 1);
		ItemMeta meta = item.getItemMeta();
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		item.setItemMeta(meta);
	}
	
	public void removeEnchantments(ItemStack item) {
	
	if(item.getEnchantments() == null)
		return;
	
	for(Enchantment e : item.getEnchantments().keySet()){
		item.removeEnchantment(e);
		}
	}

	public void findLand() {
		
		global.setY(255);
	
		while(true) {
		
			if (global.getBlock().getType().equals(Material.AIR) || global.getBlock().getType().equals(Material.CAVE_AIR)) {
				
				global = global.clone().subtract(0, 1, 0);
				
			} else {
				
				return;
				
			}
		
		}
		
	}

	
	
	//Space //Space //Space //Space //Space //Space //Space //Space //Space //Space //Space 
	
	public void generateSpeedInventory(Player p) {
		//11,12,13,14,15 | Normal speeds
		//20,21,22,23,24 | Empowered speeds 
		
		speedInv = Bukkit.createInventory(null, 36, ChatColor.BLUE + "Speed settings");
		
		
		ItemStack one = createGuiItem(Material.GUNPOWDER, ChatColor.GRAY + "0", ChatColor.DARK_AQUA + "0 speed amplifications");
		
		ItemStack two = createGuiItem(Material.SUGAR, ChatColor.AQUA + "II", ChatColor.DARK_AQUA + "Speed II");
		two.setAmount(2);
		
		ItemStack three = createGuiItem(Material.SUGAR, ChatColor.AQUA + "III", ChatColor.DARK_AQUA + "Speed III");
		three.setAmount(3);
		
		ItemStack four = createGuiItem(Material.SUGAR, ChatColor.AQUA + "IV", ChatColor.DARK_AQUA + "Speed IV");
		four.setAmount(4);
		
		ItemStack five = createGuiItem(Material.SUGAR, ChatColor.AQUA + "V", ChatColor.DARK_AQUA + "Speed V");
		five.setAmount(5);
		
		ItemStack six = createGuiItem(Material.SUGAR, ChatColor.AQUA + "VI", ChatColor.DARK_AQUA + "Speed VI");
		six.setAmount(6);
		
		ItemStack seven = createGuiItem(Material.SUGAR, ChatColor.AQUA + "VII", ChatColor.DARK_AQUA + "Speed VII");
		seven.setAmount(7);
		
		ItemStack eight = createGuiItem(Material.SUGAR, ChatColor.AQUA + "VIII", ChatColor.DARK_AQUA + "Speed VIII");
		eight.setAmount(8);
		
		ItemStack nine = createGuiItem(Material.SUGAR, ChatColor.AQUA + "IX", ChatColor.DARK_AQUA + "Speed IX");
		nine.setAmount(9);
		
		ItemStack ten = createGuiItem(Material.SUGAR, ChatColor.AQUA + "X", ChatColor.DARK_AQUA + "Speed X");
		ten.setAmount(10);
		
		
		
		if(velocity == 0) {
			addEmptyEnchantment(one);
		}
		
		else if(velocity == 2) {
			two.setType(Material.GLOWSTONE_DUST);
		}
		
		else if(velocity == 3) {
			three.setType(Material.GLOWSTONE_DUST);
		}
		
		else if(velocity == 4) {
			four.setType(Material.GLOWSTONE_DUST);
		}
		
		else if(velocity == 5) {
			five.setType(Material.GLOWSTONE_DUST);
		}
		
		else if(velocity == 6) {
			six.setType(Material.GLOWSTONE_DUST);
		}
		
		else if(velocity == 7) {
			seven.setType(Material.GLOWSTONE_DUST);
		}
		
		else if(velocity == 8) {
			eight.setType(Material.GLOWSTONE_DUST);
		}
		
		else if(velocity == 9) {
			nine.setType(Material.GLOWSTONE_DUST);
		}
		
		else if(velocity == 10) {
			ten.setType(Material.GLOWSTONE_DUST);
		}
		
		speedInv.setItem(11, one);
		speedInv.setItem(12, two);
		speedInv.setItem(13, three);
		speedInv.setItem(14, four);
		speedInv.setItem(15, five);
		
		if(plugin.gloves.get(findPlayer(p)).getPower() == false) {
			six.setType(Material.GRAY_DYE);
			seven.setType(Material.GRAY_DYE);
			eight.setType(Material.GRAY_DYE);
			nine.setType(Material.GRAY_DYE);
			ten.setType(Material.GRAY_DYE);
		}
		
		speedInv.setItem(20, six);
		speedInv.setItem(21, seven);
		speedInv.setItem(22, eight);
		speedInv.setItem(23, nine);
		speedInv.setItem(24, ten);
		
		speedInv.setItem(35, activate);
		speedInv.setItem(27, cancel);
		
		p.openInventory(speedInv);
		
		
	}
	
	public void generateShortInventory(Player p) {
		shortInv = Bukkit.createInventory(null, 45, ChatColor.BLUE + "Short teleportation");
		//22, 24
		
		ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1);
		SkullMeta meta = (SkullMeta) skull.getItemMeta();
		meta.setOwningPlayer(p);
		meta.setDisplayName(ChatColor.BLUE + "Teleport yourself");
		meta.setLore(Arrays.asList(ChatColor.DARK_BLUE + "Teleport yourself to wherever you look"));
		skull.setItemMeta(meta);
		
		ItemStack steve = createGuiItem(Material.PLAYER_HEAD, ChatColor.RED + "Teleport a player",
				ChatColor.DARK_RED + "Teleport a player to wherever you look");
		
		
		shortInv.setItem(21, skull);
		
		shortInv.setItem(23, steve);
		
		shortInv.setItem(36, cancel);
		
		p.openInventory(shortInv);
	}
	
	public void generateChoosePlayerInventory(Player p) {
		choosePlayerInv = Bukkit.createInventory(null, 45, ChatColor.BLUE + "Choose a player");
		//22, 24
		
		ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1);
		SkullMeta meta = (SkullMeta) skull.getItemMeta();
		meta.setOwningPlayer(p);
		meta.setDisplayName(ChatColor.BLUE + "Teleport yourself");
		meta.setLore(Arrays.asList(ChatColor.DARK_BLUE + "Teleport yourself wherever you want"));
		skull.setItemMeta(meta);
		
		ItemStack steve = createGuiItem(Material.PLAYER_HEAD, ChatColor.RED + "Teleport a player",
				ChatColor.DARK_RED + "Teleport a player wherever you want");
		
		
		choosePlayerInv.setItem(21, skull);
		
		choosePlayerInv.setItem(23, steve);
		
		choosePlayerInv.setItem(36, cancel);
		
		p.openInventory(choosePlayerInv);
	}
	
	public void everyPlayer(Player p) {
		
		int totalPlayers = Bukkit.getOnlinePlayers().size() - 1;
		
		if(totalPlayers == 0) {
			p.sendMessage(ChatColor.BLUE + "You're the only online player");
			p.closeInventory();
			return;
		}
		
		int size = totalPlayers/9;
		if (totalPlayers % 9 != 0) {
			size++;
		}
		
		size *= 9;
		
		everyoneInv = Bukkit.createInventory(null, size, ChatColor.BLUE + "Online players");
		for(Player player : Bukkit.getOnlinePlayers()) {
			
			if(player != p) {
				ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1);
				SkullMeta meta = (SkullMeta) skull.getItemMeta();
				meta.setOwningPlayer(player);
				meta.setDisplayName(ChatColor.RED + player.getName());
				skull.setItemMeta(meta);
				
				everyoneInv.addItem(skull);
			}
			
			
		}
		
		p.openInventory(everyoneInv);
		
		
		
		
		
	}
	
	
	
	//Reality //Reality //Reality //Reality //Reality //Reality //Reality //Reality //Reality 
	
	public void addInventoryBar(Inventory inventory) {
		
		int lastPage = 0;
		int page = 0;
		boolean block;
		
		if(inventory == blockInv) {
			page = blockPage;
			lastPage = 13;
			block = true;
			
		}
		
		else if (inventory == itemInv) {
			page = itemPage;
			lastPage = 8;
			block = false;
		}
		
		else {
			Bukkit.broadcastMessage("Incorrect inventory!");
			return;
		}
			

		if (page == 0) {

		}

		else {

			inventory.setItem(48, createGuiItem(Material.ARROW, "Previous Page", "Go to the previous page"));
		}

		if(block) {
			inventory.setItem(49, blockWish);
		}
		
		else {
			inventory.setItem(49, itemWish);
		}
		// find the number
		if (page == lastPage) {

		}

		else {
			inventory.setItem(50, createGuiItem(Material.ARROW, "Next Page", "Go to the next page"));
		}
		
		inventory.setItem(53, activate);
		
		inventory.setItem(45, cancel);



	}
	
	public void generateBlockInventory(Player p) {
		blockInv = Bukkit.createInventory(null, 54, ChatColor.RED + "Block list");
		
		for(int i = 0; i < 45; i++) {
			if(i + blockPage * 45 == blocks.size())
				break;
			blockInv.addItem(new ItemStack(blocks.get(i + blockPage * 45), 1));
		}
		addInventoryBar(blockInv);
		
		p.openInventory(blockInv);
		
	}
	
	public void generateItemInventory(Player p) {
		itemInv = Bukkit.createInventory(null, 54, ChatColor.DARK_RED + "Item list");
		
		for(int i = 0; i < 45; i++) {
			
			if(i + itemPage * 45 == items.size())
				break;
			itemInv.addItem(new ItemStack(items.get(i + itemPage * 45), 1));
		}
		
		addInventoryBar(itemInv);
		
		p.openInventory(itemInv);
	}
	
	
	
	//Time //Time //Time //Time //Time //Time //Time //Time //Time //Time //Time //Time //Time 
	
	public void generateTimeSettingsInventory(Player p) {
		timeSettingsInv = Bukkit.createInventory(null, 45, ChatColor.GREEN + "Time settings");
		
		ItemStack rewind = createGuiItem(Material.EMERALD, ChatColor.GREEN + "Go back in time",
				ChatColor.DARK_GREEN + "Return everyone to your desired timeline");
		
		timeSettingsInv.setItem(23, rewind);
		
		ItemStack daySettings = createGuiItem(Material.CLOCK, ChatColor.GREEN + "Day settings",
				ChatColor.DARK_GREEN + "Play with the hours of the day");
		
		timeSettingsInv.setItem(21, daySettings);
		
		timeSettingsInv.setItem(36, cancel);
		
		p.openInventory(timeSettingsInv);
		
	}
	
	public void generateRewindInv(Player p) {
		
		//Power adjustment
		int pages;
		int maximum;
		
		if(plugin.gloves.get(findPlayer(p)).getPower()) {
			pages = 36;
			rewindInv = Bukkit.createInventory(null, pages, ChatColor.GREEN + "Rewind");
			if(loadouts > 11) {
				maximum = 11;
			} else maximum = loadouts;
			
			rewindInv.setItem(27, cancel);
			rewindInv.setItem(35, activate);
		}
		else {
			pages = 27;
			rewindInv = Bukkit.createInventory(null, pages, ChatColor.GREEN + "Rewind");
			if (loadouts > 6) {
				maximum = 6;
			} else maximum = loadouts;
			
			rewindInv.setItem(18, cancel);
			rewindInv.setItem(26, activate);
		}
		
		
		if(loadouts < 2) {
			p.sendMessage("There aren't any rewind slots available yet.");
			p.sendMessage("Please wait up to 1 minute");
			p.closeInventory();
			return;
		}
		
		
		for(int i = 1; i < maximum; i++) {
			
			boolean enchant;
			if(rewindMinutes == -1)
				enchant = false;
			else enchant = true;
			
			ItemStack load = createGuiItem(Material.BOOK, ChatColor.GREEN + "" + i + " minute", ChatColor.DARK_GREEN + "Go back in time");
			load.setAmount(i);
			int slot;
			if(i > 5) {
				slot = 19;
				rewindInv.setItem(slot + (i - 5), load);
			}
			else {
				slot = 10;
				rewindInv.setItem(slot + i, load);
			}
			
			if(enchant) {
				int addition;
				if(rewindMinutes > 5)
					addition = 14;
				else addition = 10;
				addEmptyEnchantment(rewindInv.getItem(addition + rewindMinutes));
			}
			
		}
		
		
		
		
		
		p.openInventory(rewindInv);
		
		
	}
	
	public void generateDayInv(Player p) {
		
		dayInv = Bukkit.createInventory(null, 45, ChatColor.GREEN + "Day settings");
		
		p.openInventory(dayInv);
		
		
	}
	
	public void stopTime() {
		World world = Freezer.getWorld();
		BukkitScheduler sched = Freezer.getServer().getScheduler();
		world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
		
		if(plugin.gloves.get(findPlayer(Freezer)).getPower()) freezeDuration = 40;
		else freezeDuration = 20;
		
		timeFrozen = true;
		
		for(LivingEntity mobs : world.getLivingEntities()) {
			

			if(mobs instanceof Player) {
				
				Player player = (Player) mobs;
				if(player != Freezer) {
					player.setNoDamageTicks(freezeDuration * 20);
					
				}
				
				
				
			}
			
			else {
				
				mobs.setNoDamageTicks(freezeDuration * 20);
				mobs.setSilent(true);
				mobs.setAI(false);
				
				
			}
			
			
			
		}
		
		freezeTask = sched.scheduleSyncRepeatingTask(plugin, new Runnable() {
			
			int timer = freezeDuration;
			
			

			
			
			@Override
			public void run() {
				
				if(timer == 0) {
					
					sched.cancelTask(freezeTask);
					Freezer = null;
					timeFrozen = false;
					world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, true);
					Bukkit.broadcastMessage(ChatColor.GREEN + "Time has been unfrozen!");
					
					for(LivingEntity mobs : world.getLivingEntities()) {
	
							mobs.setSilent(false);
							mobs.setAI(true);

					}
					return;
					
				}
				
				if(timer == 10 || timer == 15) {
					Bukkit.broadcastMessage(ChatColor.YELLOW +"" + timer + " seconds remain");
				}
				
				else if (timer <= 5) {
					Bukkit.broadcastMessage("" + ChatColor.RED + timer);
				}
				
				timer--;
				
				
			}
			
			
		}, 0L, 20L);
		

		
	}
	
	
	
	//Stone Menus //Stone Menus //Stone Menus //Stone Menus //Stone Menus //Stone Menus //Stone Menus //Stone Menus 
	
	
	public void generateSpaceInv(Player p) {
		spaceInv = Bukkit.createInventory(null, 45, ChatColor.BLUE + "Space Stone");
		//18, 20, 22

		spaceInv.setItem(20, speed);
		
		
		spaceInv.setItem(22, shortTeleport);
		

		if(plugin.gloves.get(findPlayer(p)).getPower() == false){
			globalTeleport.setType(Material.GRAY_DYE);
		}
		
		else {
			globalTeleport.setType(Material.WARPED_DOOR);
		}
		
		spaceInv.setItem(24, globalTeleport);
		
		spaceInv.setItem(40, disable);
		
		
		p.openInventory(spaceInv);
	}
	
	public void generateMindInv(Player p) {
		mindInv = Bukkit.createInventory(null, 45, ChatColor.YELLOW + "Mind Stone");
		p.openInventory(mindInv);
	}

	public void generateRealityInv(Player p) {
		realityInv = Bukkit.createInventory(null, 45, ChatColor.RED + "Reality Stone");
		
		realityInv.setItem(21, blockChanger);
		realityInv.setItem(23, itemChanger);
		realityInv.setItem(40, disable);
		
		p.openInventory(realityInv);
	}
	
	public void generatePowerInv(Player p) {
		powerInv = Bukkit.createInventory(null, 45, ChatColor.DARK_PURPLE + "Power Stone");
		
		if(powerEffect) {
			addEmptyEnchantment(powerSword);
		}
		else {
			removeEnchantments(powerSword);
		}
		
		powerInv.setItem(21, powerSword);
			
		powerInv.setItem(23, powerStar);
		
		powerInv.setItem(40, disable);
				
		p.openInventory(powerInv);
	}
	
	public void generateTimeInv(Player p) {
		timeInv = Bukkit.createInventory(null, 45, ChatColor.GREEN + "Time Stone");
		
		if(plugin.gloves.get(findPlayer(p)).getPower()) {
			freezeDuration = 40;
		} else {
			freezeDuration = 20;
		}
		
		timeInv.setItem(21, freezeTimeItem);
		
		timeInv.setItem(23, clock);
		
		
		timeInv.setItem(40, disable);
		
		p.openInventory(timeInv);
	}
	
	public void generateSoulInv(Player p) {
		soulInv = Bukkit.createInventory(null, 45, ChatColor.GOLD + "Soul Stone");
		p.openInventory(soulInv);
	}
	
	
	//acquired //acquired //acquired //acquired //acquired //acquired //acquired 
	
	public void acquiredSpaceStone(Player p) {
		plugin.gloves.get(findPlayer(p)).setSpace(true);
		Bukkit.broadcastMessage(ChatColor.BLUE + p.getName() + " has acquired the space stone");
		p.removePotionEffect(PotionEffectType.SPEED);
		p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, velocity));
		p.closeInventory();
	}
	
	public void acquiredMindStone(Player p) {
		plugin.gloves.get(findPlayer(p)).setMind(true);
		Bukkit.broadcastMessage(ChatColor.YELLOW + p.getName() + " has acquired the mind stone");
		p.closeInventory();
	}
	
	public void acquiredRealityStone(Player p) {
		plugin.gloves.get(findPlayer(p)).setReality(true);
		Bukkit.broadcastMessage(ChatColor.RED + p.getName() + " has acquired the reality stone");
		p.closeInventory();
	}
	
	public void acquiredPowerStone(Player p) {
		plugin.gloves.get(findPlayer(p)).setPower(true);
		Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + p.getName() + " has acquired the power stone");
		p.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
		p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 2));
		p.closeInventory();
	}
	
	public void acquiredTimeStone(Player p) {
		plugin.gloves.get(findPlayer(p)).setTime(true);
		Bukkit.broadcastMessage(ChatColor.GREEN + p.getName() + " has acquired the time stone");
		p.closeInventory();
		
		enableRewind = true;
		
		HashMap<Player, TimeStamp> timeStamp = new HashMap<Player, TimeStamp>();
		
		for(Player player : Bukkit.getOnlinePlayers()) {
			timeStamp.put(player, new TimeStamp(plugin.gloves.get(findPlayer(player))));
		}
		timeStamps.add(timeStamp);
		
		pastEntities.add(new PastEntities(world));
		
		BukkitScheduler sched = p.getServer().getScheduler();
		
		rewindTask = sched.scheduleSyncRepeatingTask(plugin, new Runnable() {
			
			

			@Override
			public void run() {
				
				if(enableRewind) {
					
					loadouts++;
					
					if(loadouts != 1) {
						if(loadouts > 10) {
							timeStamps.remove(1);
							pastEntities.remove(1);
							loadouts--;
						}
						p.sendMessage(ChatColor.GREEN + "A new timestamp has been created");
						HashMap<Player, TimeStamp> timeStamp = new HashMap<Player, TimeStamp>();
						
						for(Player player : Bukkit.getOnlinePlayers()) {
							timeStamp.put(player, new TimeStamp(plugin.gloves.get(findPlayer(player))));
						}
						
						timeStamps.add(timeStamp);
						
						
						pastEntities.add(new PastEntities(world));
					}
					
					
					
				}

				
			}
			
			
		}, 0L, 200L);
		
	}
	
	public void acquiredSoulStone(Player p) {
		plugin.gloves.get(findPlayer(p)).setSoul(true);
		Bukkit.broadcastMessage(ChatColor.GOLD + p.getName() + " has acquired the soul stone");
		p.closeInventory();
	}
	
	

  
	

	
}