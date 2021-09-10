package me.erez.IG.Stones;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Stack;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.EntityEffect;
import org.bukkit.GameMode;
import org.bukkit.GameRule;
//import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Egg;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import me.erez.IG.Glove;
import me.erez.IG.Main;
import me.erez.IG.NPC;
import me.erez.IG.PastEntities;
import me.erez.IG.TimeStamp;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.minecraft.server.v1_16_R3.EntityPlayer;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_16_R3.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_16_R3.PlayerConnection;

import org.bukkit.event.player.PlayerInteractEvent;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.annotation.Target;
import java.net.URL;

//variables

public class Reality implements Listener {

	private Main plugin;
	private Random random = new Random();
	private Material mats[];
	Set<Material> transparent = new HashSet<Material>();

	private boolean enableRewind = false;
	private int loadouts = 0;
	private int rewindMinutes = -1;
	private int rewindTask;
	private World world = Bukkit.getWorlds().get(0);
	private World world2 = Bukkit.getWorlds().get(1);
	
	private Stack<HashMap<Player, TimeStamp>> timeStamps = new Stack<HashMap<Player, TimeStamp>>();
	private Stack<PastEntities> pastEntities = new Stack<PastEntities>();
	

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
	

	//Mind variables
	private int ghostTask;
	private int ghostTaskCD;
	private double ghostCD = 0.00;
	private boolean ghostOnCD = false;
	
	
	//Power variables
	
	private ItemStack powerStar = null;
	private ItemStack powerSword = null;
	private boolean powerEffect = true;

	//Soul variables
	
	private Player limboVictim = null;
	private Location limboVictimLocation = null;
	private double victimHealth;
	private Player shooter = null;
	private boolean shooterPower = false;
	private boolean victimDeath = false;
	
	private ArrayList<PigZombie> pigzombies = new ArrayList<PigZombie>();
	private ArrayList<Ghast> ghasts = new ArrayList<Ghast>();
	
	private ItemStack[] limboVictimInv = null;
	
	private Inventory everyoneInvSoul = null;
	private Inventory manChoice = null;
	private Player manVictim = null;
	private int victimID;
	
	
	private ArrayList<Sound> scarySounds = new ArrayList<>();
	
	private UUID eggUUID = null;
	private boolean eggBoolean = false;
	
	private int eggTask;
	private int eggCDTask;
	private double eggCD = 0.00;
	
	private int wave1task;
	private int wave2task;
	
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
	
	private int voidtask;
	
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
		
		
		scarySounds.add(Sound.ENTITY_GHAST_SCREAM);
		scarySounds.add(Sound.ENTITY_PHANTOM_SWOOP);
		
		
		

	}

	
	
	
	
	
	
	
	
	
	
	
	// Events // Events // Events // Events // Events // Events // Events // Events 
	
	@EventHandler
	public void join(PlayerJoinEvent event) {
		
		PacketReader reader = new PacketReader();
		reader.inject(event.getPlayer());
		
		Glove glove = new Glove(event.getPlayer(), "none", false, false, false, false, false, false);
		plugin.gloves.add(glove);
		
		Player player = event.getPlayer();
		String uuid = player.getUniqueId().toString();
		
		this.plugin.data.reloadConfig();
		if(!(this.plugin.data.getConfig().contains("players." + uuid))) {
				
			this.plugin.data.getConfig().set("players." + uuid + ".equipped",  "none");
			this.plugin.data.getConfig().set("players." + uuid + ".hasSpace", false);
			this.plugin.data.getConfig().set("players." + uuid + ".hasMind", false);
			this.plugin.data.getConfig().set("players." + uuid + ".hasReality", false);
			this.plugin.data.getConfig().set("players." + uuid + ".hasPower", false);
			this.plugin.data.getConfig().set("players." + uuid + ".hasTime", false);
			this.plugin.data.getConfig().set("players." + uuid + ".hasSoul", false);
			
			this.plugin.data.saveConfig();
			return;
		}
		
		glove.setEquipped((String) this.plugin.data.getConfig().get("players." + uuid + ".equipped"));
		glove.setSpace((Boolean) this.plugin.data.getConfig().get("players." + uuid + ".hasSpace"));
		glove.setMind((Boolean) this.plugin.data.getConfig().get("players." + uuid + ".hasMind"));
		glove.setReality((Boolean) this.plugin.data.getConfig().get("players." + uuid + ".hasReality"));
		glove.setPower((Boolean) this.plugin.data.getConfig().get("players." + uuid + ".hasPower"));
		glove.setTime((Boolean) this.plugin.data.getConfig().get("players." + uuid + ".hasTime"));
		glove.setSoul((Boolean) this.plugin.data.getConfig().get("players." + uuid + ".hasSoul"));
		this.plugin.data.saveConfig();
		
		if(NPC.getNPCS() == null)
			return;
		if(NPC.getNPCS().isEmpty())
			return;
		NPC.addJoinPacket(player);
			
		

	}

	@EventHandler
	public void quit(PlayerQuitEvent event) {
		
		PacketReader reader = new PacketReader();
		reader.uninject(event.getPlayer());
		
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
		
		//limbo
		if((lore.get(0).equals(ChatColor.DARK_RED + "Must collect them all...") &&
			(event.getAction().equals(Action.LEFT_CLICK_AIR) || event.getAction().equals(Action.LEFT_CLICK_BLOCK)) 
				&& plugin.gloves.get(findPlayer(p)).getEquipped().equals("limbo")
				&& (p.getOpenInventory().getType() == InventoryType.CRAFTING || p.getOpenInventory().getType() == InventoryType.CREATIVE)
				&& (p.getInventory().getItemInMainHand().getType().equals(Material.AIR) || p.getInventory().getItemInMainHand().getType().equals(null)))){
			
			if(eggBoolean) {
				double cd = 60 - eggCD/20;
				String cdString = new DecimalFormat("#.#").format(cd);
				p.sendMessage((ChatColor.RED + "Cooldown! Please wait ") + (ChatColor.DARK_RED + cdString + "s") );
				return;
			}
			
			Egg egg = (Egg) p.getWorld().spawn(p.getEyeLocation(),  Egg.class);
			egg.setBounce(false);
			
			eggUUID = egg.getUniqueId();
			
			p.getWorld().playSound(p.getLocation(), Sound.ENTITY_WITHER_SHOOT, 1, 1);
			
			BukkitScheduler sched = p.getServer().getScheduler();
			
			for(Player player : Bukkit.getServer().getOnlinePlayers()) {
				PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(egg.getEntityId());
				
				((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);;
                egg.setShooter((ProjectileSource) ((LivingEntity) p));
                egg.setVelocity(p.getEyeLocation().getDirection().multiply(3D));
                egg.setBounce(false);
                egg.setSilent(true);
			}
			
			eggBoolean = true;
			
			eggTask = sched.scheduleSyncRepeatingTask(plugin, new Runnable() {
				
				@Override
				public void run() {
					Location eggloc = egg.getLocation();
					
					if(egg.isDead() || egg.getTicksLived() >= 100) {
						
						sched.cancelTask(eggTask);
						return;
						
					}
					
					followTrail(eggloc, eggloc.getDirection(), Particle.REDSTONE, 7f, new Particle.DustOptions(Color.ORANGE, 1));
					followTrail(eggloc, eggloc.getDirection(), Particle.REDSTONE, 7f, new Particle.DustOptions(Color.BLACK, 1));

					
				}
				
			}, 0, 1);
			
			eggCDTask = sched.scheduleSyncRepeatingTask(plugin, new Runnable() {
				
				@Override
				public void run() {
					eggCD += 2;
					if(eggCD == 1200) {
						eggBoolean = false;
						eggCD = 0.00;
						sched.cancelTask(eggCDTask);
						eggUUID = null;
					}
				}
				
			}, 0, 2L);
			
			
		}
		
		
		//global teleport
		if((lore.get(0).equals(ChatColor.DARK_RED + "Must collect them all...") &&
				(event.getAction().equals(Action.LEFT_CLICK_AIR) || event.getAction().equals(Action.LEFT_CLICK_BLOCK)) 
					&& plugin.gloves.get(findPlayer(p)).getEquipped().equals("global")
					&& (p.getOpenInventory().getType() == InventoryType.CRAFTING || p.getOpenInventory().getType() == InventoryType.CREATIVE)
					&& (p.getInventory().getItemInMainHand().getType().equals(Material.AIR) || p.getInventory().getItemInMainHand().getType().equals(null)))){
			
			
			globalPlayer.teleport(global);
			return;
			
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
			return;
			
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
		
		if(rewindMinutes < 0) {
			p.sendMessage(ChatColor.GREEN + "Invalid rewind");
			return;
		}
			
		Bukkit.broadcastMessage(ChatColor.GREEN + "It's rewind time!");	
		
		for(int i = 0; i <= rewindMinutes; i++) {
			
			boolean last = false;
			
			Bukkit.broadcastMessage("Working on loadout number: " + i );
			Bukkit.broadcastMessage("Is it the last loadout? " + last);
			
			if(i == rewindMinutes) {
				last = true;
			}
			
			Bukkit.broadcastMessage("Is it the last loadout now? " + last);
			
			HashMap<Player, TimeStamp> hash = timeStamps.pop();
			
			Bukkit.broadcastMessage("popped Hash");
			
			PastEntities past = pastEntities.pop();
			
			Bukkit.broadcastMessage("popped pastEntities");
			
			Bukkit.broadcastMessage("restoring exploded blocks");
			past.restoreExplodedBlocks();
			Bukkit.broadcastMessage("restoring burnt blocks");
			past.restoreBurntBlocks();
			
			for(Player player : hash.keySet()) {
				TimeStamp timeStamp = hash.get(player);
				timeStamp.restoreBrokenBlocks();
				Bukkit.broadcastMessage("restored broken blocks");
				timeStamp.restorePlacedBlocks();
				Bukkit.broadcastMessage("restored placed blocks");
				

				

				
				if(last) {
					
					player.sendTitle(ChatColor.GREEN + "Server rewinded"
							, ChatColor.DARK_GREEN + "The server has been rewinded by " + rewindMinutes + " minutes", 20, 140, 20);
					
					Bukkit.broadcastMessage("sent a title");
					
					Bukkit.broadcastMessage("Restoring Players for the last time");
					timeStamp.restoreGlove();
					Bukkit.broadcastMessage("restored a player's glove");
					timeStamp.restorePlayer();
					Bukkit.broadcastMessage("restored a player");

				}
			}
			


			if(last) {

				past.restoreChests();
				Bukkit.broadcastMessage("restored chests");
				past.restoreEntities();
				Bukkit.broadcastMessage("restored entities");
				
				//plugin.gloves.get(findPlayer(p)).setEquipped("none");
				//Bukkit.broadcastMessage("set caster's stone to 'none'");
				timeStamps.empty();
				Bukkit.broadcastMessage("emptied timestamps");
				pastEntities.empty();
				Bukkit.broadcastMessage("emptied pastEntities");
				
				HashMap<Player, TimeStamp> timeStamp = new HashMap<Player, TimeStamp>();			
				for(Player player : Bukkit.getOnlinePlayers()) {
					timeStamp.put(player, new TimeStamp(plugin.gloves.get(findPlayer(player))));
					Bukkit.broadcastMessage("generated new HashMap<Player, TimeStamp>");
				}
				timeStamps.push(timeStamp);
				Bukkit.broadcastMessage("pushed the timeStamp to the HashMap<Player, TimeStamp>");
				pastEntities.push(new PastEntities(world));
				Bukkit.broadcastMessage("pushed a new pastEntities");
			}
			

			
		}
			


		
			
			
			
		}
		
		//Phase shift
		
		if((lore.get(0).equals(ChatColor.DARK_RED + "Must collect them all...") &&
				(event.getAction().equals(Action.LEFT_CLICK_AIR) || event.getAction().equals(Action.LEFT_CLICK_BLOCK)) 
					&& plugin.gloves.get(findPlayer(p)).getEquipped().equals("phaseShift")
					&& (p.getOpenInventory().getType() == InventoryType.CRAFTING || p.getOpenInventory().getType() == InventoryType.CREATIVE)
					&& (p.getInventory().getItemInMainHand().getType().equals(Material.AIR) || p.getInventory().getItemInMainHand().getType().equals(null)))){
		
			if(ghostOnCD) {
				double cd = 30 - ghostCD/20;
				
				String cdString = new DecimalFormat("#.#").format(cd);
				p.sendMessage((ChatColor.RED + "Cooldown! Please wait ") + (ChatColor.DARK_RED + cdString + "s") );
				return;
			}
				ghostOnCD = true;
				BukkitScheduler sched = p.getServer().getScheduler();
				ghostTaskCD = sched.scheduleSyncRepeatingTask(plugin, new Runnable(){
					
					
					
					@Override
					public void run() {
						
						
						if(ghostCD == 600) {
							ghostCD = 0;
							ghostOnCD = false;
							sched.cancelTask(ghostTaskCD);
							return;
						}
						
						ghostCD += 2;
						
					}
					
				}, 0, 2L);
				phaseShift(p);
				return;
			
			
			
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
					
					rewindInv.getItem(rewindMinutes + addition).setType(Material.BOOK);
					
				}
				
				int amount = e.getCurrentItem().getAmount();
				rewindMinutes = amount;
				e.getCurrentItem().setType(Material.ENCHANTED_BOOK);

			}
			
			if(e.getCurrentItem().equals(activate)) {
				p.closeInventory();
				
				if(rewindMinutes < 0) {
					p.sendMessage(ChatColor.RED + "Invalid rewind");
					return;
				}
				
				String s = "s";
				if(rewindMinutes == 1)
					s = "";
				p.sendMessage(ChatColor.GREEN + "Success, hit the air in order to go back in time with everyone for " + rewindMinutes + " minute" + s);
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
		

		
		//SOUL STONE //SOUL STONE //SOUL STONE 
		
		if(e.getInventory().equals(soulInv)) {
			e.setCancelled(true);
			
			//limbo
			if(e.getSlot() == 21) {
				p.closeInventory();
				plugin.gloves.get(findPlayer(p)).setEquipped("limbo");
				String place = "the void";
				if(plugin.gloves.get(findPlayer(p)).getPower()) {
					place = "hell";
				}
				p.sendMessage(ChatColor.GOLD + "Hit a player with the limbo projectile in order to send him to " + place);
				return;
			}
			
			//manipulate
			if(e.getSlot() == 23) {
				generateEveryoneInvSoul(p);
			}
			
			//disable
			if(e.getCurrentItem().equals(disable)) {
				plugin.gloves.get(findPlayer(p)).setEquipped("none");
				p.sendMessage("You have disabled the soul stone");
				plugin.gloves.get(findPlayer(p)).generateInventory();
				return;
			}
		}
		
		if(e.getInventory().equals(everyoneInvSoul)) {
			e.setCancelled(true);
			
			SkullMeta meta = (SkullMeta)e.getCurrentItem().getItemMeta();
			manVictim = (Player)meta.getOwningPlayer();
			
			generateChooseManipulateInv(p);
		}
		
		if(e.getInventory().equals(manChoice)) {
			e.setCancelled(true);
			
			if(e.getRawSlot() == 0) {
				generateSoulInv(p);
				return;
			}
			
			if(e.getRawSlot() == 3) {
				intoxicatePlayer(manVictim);
				p.closeInventory();
				p.sendMessage(ChatColor.GOLD + manVictim.getName() + "'s soul has been intoxicated");
				return;
			}
			
			if(e.getRawSlot() == 4) {
				blindPlayer(manVictim);
				p.closeInventory();
				p.sendMessage(ChatColor.GOLD + manVictim.getName() + " has been blinded");
				return;
			}
			
			if(e.getRawSlot() == 5) {
				int rand = random.nextInt(3) + 1;
				p.sendMessage(ChatColor.GOLD + manVictim.getName() + " has been scared");
				p.closeInventory();
				switch(rand) {
					case 1:
						dogAnimation(manVictim);
						return;
					case 2:
						creeperAnimation(manVictim);
						return;
					case 3:
						scarePlayer(manVictim);
						return;
						
				}
			}
			
			
		}
		
		//MIND STONE //MIND STONE //MIND STONE
		
		if(e.getInventory().equals(mindInv)) {
			e.setCancelled(true);
			
			if(e.getRawSlot() == 25) {
				plugin.gloves.get(findPlayer(p)).setEquipped("phaseShift");
				p.sendMessage(ChatColor.YELLOW + "Left click to beocme a ghost for 5 seconds");
				p.closeInventory();
				return;
			}
			
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
	
	
	@EventHandler
	public void swapHands(PlayerSwapHandItemsEvent e) {
		//cycle
		Player player = e.getPlayer();
		
		String lore = e.getMainHandItem().getItemMeta().getLore().get(0);
		
		if(lore.equals(ChatColor.DARK_RED + "Must collect them all...") && e.getOffHandItem().getType().equals(Material.AIR) || e.getOffHandItem().equals(null)) {
			
			e.setCancelled(true);
			boolean reverse = false;
			if(player.isSneaking())
				reverse = true;
			
			plugin.gloves.get(findPlayer(player)).cycle(reverse);
			
			
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
		
		/*
		e.setCancelled(true);
		p.sendMessage(timeStamps.size() + "");
		p.sendMessage(pastEntities.size() + "");
		*/
		
	}
	
	@EventHandler
	public void EntityDamageByEntity(EntityDamageByEntityEvent e) {
		if(e.getDamager() instanceof Snowball) {
			Snowball snowball = (Snowball) e.getDamager();
	        LivingEntity shooter = (LivingEntity) snowball.getShooter();
	        if (!(shooter instanceof Player)) return;
	        Player p = (Player) shooter;
	        
	        if (!plugin.gloves.get(findPlayer(p)).getEquipped().equals("Power")) return;
			
	        e.setDamage(e.getDamage() + 15);
	        return;
		}
		
		if(e.getDamager() instanceof Egg) {
			Egg egg = (Egg) e.getDamager();
			LivingEntity damagee = (LivingEntity) e.getEntity();
			if(!(damagee instanceof Player)) return;
			
			Player victim = (Player) e.getEntity();
			
			if(!egg.getUniqueId().equals(eggUUID)) return;
			//Bukkit.broadcastMessage(victim.getName() + " was hit by the special egg");
			
			
			shooter = (Player) egg.getShooter();
			String where = "";
			if(plugin.gloves.get(findPlayer(shooter)).getPower()) {
				where = "hell";
				shooterPower = true;
			}
			else {
				where = "void";
				shooterPower = false;
			}
			shooter.sendMessage(ChatColor.GOLD + "You have successfully sent " + victim.getName() + "'s soul to the " + where);
			
			tothevoid(victim);
			
			victim.getWorld().playSound(victim.getLocation(), Sound.ENTITY_WITHER_SPAWN, 5, 1);
			
		}
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
		timeStamps.get(timeStamps.size() - 1).get(player).getBrokenBlocks().put(e.getBlock().getLocation(), e.getBlock().getType());
		
		//player.sendMessage("breakEvent triggered");
		
	}
	
	@EventHandler
	public void blockPlace(BlockPlaceEvent e) {
		
		if(enableRewind) {
			Player player = e.getPlayer();
			timeStamps.get(timeStamps.size() - 1).get(player).getPlacedBlocks().add(e.getBlockPlaced().getLocation());
			//player.sendMessage("blockPlace triggered");
		}
		
		if (e.getItemInHand().getItemMeta().getLore().get(0).equals(ChatColor.DARK_RED + "Reality can be whatever I want")) {
			
			e.setCancelled(true);
		}
		

		
		
	}
	
	@EventHandler
	public void kaboom(EntityExplodeEvent e) {
		if(!enableRewind) return;
		
		PastEntities currentPastEntity = pastEntities.get(pastEntities.size() - 1);
		HashMap<Location, Material> hashy = new HashMap<Location, Material>();
		for(Block block : e.blockList()) {
			hashy.put(block.getLocation(), block.getType());
		}

		currentPastEntity.addExplodedBlocks(hashy);
			
		
	}
	
	@EventHandler
	public void fired(BlockBurnEvent e) {
		if(!enableRewind) return;
		
		PastEntities currentPastEntity = pastEntities.get(pastEntities.size() - 1);
		HashMap<Location, Material> hashy = new HashMap<Location, Material>();
		Block block = e.getBlock();
		hashy.put(block.getLocation(), block.getType());

		currentPastEntity.addBurntBlock(hashy);
	}
	
	@EventHandler
	public void antiFallDamage(EntityDamageEvent e) {
		if(e.getCause().equals(DamageCause.FALL)) {
			if(!(e.getEntity() instanceof Player)) return;
			
			if (plugin.falldamage) return;
			
			e.setCancelled(true);
			
		}
	}
	
	//prevent the soul stone user from being targeted by mobs
	@EventHandler
	public void entityTargetLivingEntity(EntityTargetLivingEntityEvent e) {
		if(!(e.getTarget() instanceof CraftPlayer)) return;
		Player target = (Player) e.getTarget();
		if(plugin.gloves.get(findPlayer(target)).getSoul()) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void antiVoidDamage(EntityDamageEvent e) {
		if(!(e.getEntity() instanceof Player))
			return;
		if(!e.getCause().equals(DamageCause.VOID))
			return;
		e.setCancelled(true);
		
	}
	
	@EventHandler
	public void playerDeath(PlayerDeathEvent e) {
		if(e.getEntity().equals(limboVictim))
			victimDeath = true;
	}
	
	
	@EventHandler
	public void eggThrow(PlayerEggThrowEvent e) {
		if(e.getEgg().getUniqueId().equals(eggUUID))
			e.setHatching(false);
	}
	
	@EventHandler
	public void teleport(PlayerTeleportEvent e) {
		if(e.getCause().equals(TeleportCause.SPECTATE)) {
			e.setCancelled(true);
			e.getPlayer().sendMessage(ChatColor.RED + "This feature is disabled in this server");
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
		
		if(loadouts < 1) {
			p.sendMessage("There aren't any rewind slots available yet.");
			p.sendMessage("Please wait up to 1 minute");
			p.closeInventory();
			return;
		}
		
		//Power adjustment
		boolean hasPower = plugin.gloves.get(findPlayer(p)).getPower();
		int pages;
		
		if(hasPower) {
			pages = 36;
			rewindInv = Bukkit.createInventory(null, pages, ChatColor.GREEN + "Rewind");

			
			rewindInv.setItem(27, cancel);
			rewindInv.setItem(35, activate);
		}
		else {
			pages = 27;
			rewindInv = Bukkit.createInventory(null, pages, ChatColor.GREEN + "Rewind");
			
			rewindInv.setItem(18, cancel);
			rewindInv.setItem(26, activate);
		}
		
		

		
		
		for(int i = 1; i < loadouts; i++) {
			
			if(!hasPower && i > 5) {
				p.openInventory(rewindInv);
				return;
			}
			
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
	
	
	
	
	
	
	
	//Soul //Soul //Soul //Soul //Soul //Soul //Soul //Soul //Soul //Soul //Soul //Soul //Soul //Soul //Soul +
	
	@EventHandler
	public void touchNPC(RightClickNPC event) {
		Player player = event.getPlayer();
		
		player.sendMessage(ChatColor.DARK_AQUA + "You have interrupted " + limboVictim.getName() + "'s body");
		player.sendMessage(ChatColor.DARK_AQUA + limboVictim.getName() + "'s soul has returned to it's body");
		
		shooter.sendMessage(ChatColor.RED + "Your victim's body has been interrupted, " + limboVictim.getName() + "'s soul has been released");
		
		becomeYourself(limboVictim);
		
		limboVictim.teleport(limboVictimLocation);
		limboVictim.sendMessage(ChatColor.GOLD + "Your body has been interrupted, your soul has returned to your body");
		
		preventTrap();
		limboVictim.setHealth(victimHealth);
		
		player.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, limboVictimLocation.getX(), limboVictimLocation.getY(), limboVictimLocation.getZ(), 30, 0, 0, 0, 0 , null);
		player.getWorld().playSound(limboVictimLocation, Sound.ENTITY_GENERIC_EXPLODE, 3, 1);
		
		BukkitScheduler sched = player.getServer().getScheduler();
		sched.cancelTask(voidtask);
		
		limboVictim.getInventory().setContents(limboVictimInv);
		
		limboVictim = null;
		limboVictimLocation = null;
		Arrays.fill(limboVictimInv, null);
		shooter = null;
		
		sched.cancelTask(wave1task);
		sched.cancelTask(wave2task);
		
		NPC.removeNPC(victimID);
		
		
	}
	
	

	//poison splash
	public void intoxicatePlayer(Player player) {
		//nausea, poison?
		//player.sendTitle(ChatColor.DARK_GREEN + "Your soul is being intoxicated", "", 20, 100, 20);
		PotionEffect toxin = new PotionEffect(PotionEffectType.POISON, 100, 1);
		player.addPotionEffect(toxin);
		player.playSound(player.getLocation(), Sound.ENTITY_WITCH_CELEBRATE, 3, 1);
		
	}
	//ender eye
	public void blindPlayer(Player player) {
		PotionEffect blindness = new PotionEffect(PotionEffectType.BLINDNESS, 100, 1);
		player.addPotionEffect(blindness);
		player.playSound(player.getLocation(), Sound.AMBIENT_CAVE, 3, 1 + random.nextFloat());
	}
	
	//white skull
	public void dogAnimation(Player player) {
		
		player.closeInventory();
		
        Location location = player.getLocation();
        double yawy = location.getYaw();
        double yaw = Math.toRadians(-yawy);
        double plusX = Math.sin(yaw) * 3;
        double plusZ = Math.cos(yaw) * 3;
        Location loc = location.clone().add(plusX, 0, plusZ);
        
        //player.sendMessage(player.getEyeLocation().getYaw() + "");
        //player.sendMessage(plusX/3 +", " + plusZ/3);
        
		Wolf wolf = (Wolf) player.getWorld().spawnEntity(loc, EntityType.WOLF);
		wolf.setAI(false);
		wolf.teleport(loc.setDirection(player.getLocation().getDirection().multiply(-1)));
		
		player.playSound(player.getLocation(), Sound.ENTITY_WOLF_AMBIENT, 5, 1);
		
		BukkitScheduler sched = player.getServer().getScheduler();
		sched.runTaskLater(plugin, new Runnable() {
			
			@Override
			public void run() {
				player.playSound(player.getLocation(), Sound.ENTITY_WOLF_HURT, 5, 1);
				
				wolf.setFireTicks(6000);
				wolf.teleport(loc.setDirection(player.getLocation().getDirection().multiply(-1)));
				
			}
			
		}, 40L);
		
	}
	
	public void creeperAnimation(Player player) {
		
		player.closeInventory();
		
        Location location = player.getLocation();
        double yawy = location.getYaw();
        double yaw = Math.toRadians(-yawy);
        double plusX = Math.sin(yaw);
        double plusZ = Math.cos(yaw);
        Location loc = location.clone().add(plusX, 0, plusZ);
        
        //player.sendMessage(player.getEyeLocation().getYaw() + "");
        //player.sendMessage(plusX/3 +", " + plusZ/3);
        
		Creeper creeper = (Creeper) player.getWorld().spawnEntity(loc, EntityType.CREEPER);
		creeper.teleport(loc.setDirection(player.getLocation().getDirection().multiply(-1)));
		
		player.playSound(player.getLocation(), Sound.ENTITY_CREEPER_PRIMED, 10, (float) 0.5);
		
		BukkitScheduler sched = player.getServer().getScheduler();
		sched.runTaskLater(plugin, new Runnable() {
			
			@Override
			public void run() {
				creeper.remove();
			}
			
		}, 29L);
		
	}
	
	public void scarePlayer(Player player) {
		player.playEffect(EntityEffect.GUARDIAN_TARGET);
		PotionEffect slowness = new PotionEffect(PotionEffectType.SLOW, 300, 2);
		player.playSound(player.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_CURSE, 3, 1);
		player.addPotionEffect(slowness);
		/*Scary Sounds:
		 * 	- Witch laugh
		 * 	- Ghast scream
		 * 	- Wolf death
		 * 	- Creeper about to explode
		 * 	- Phantom screech
		 * 	- Horse death
		 */
		
		
	}


	
	public void becomeSoul(Player player) {
		Property property = new Property("textures", "eyJ0aW1lc3RhbXAiOjE1ODY2NTg5ODMxMTEsInByb2ZpbGVJZCI6IjAyZTVhMGU4MDBjNDRhYzFhNjVjMWYzN2IwM2FiZGJlIiwicHJvZmlsZU5hbWUiOiJBaWJvaCIsInNpZ25hdHVyZVJlcXVpcmVkIjp0cnVlLCJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTkyZTNmZTdkYWZlMDJjMGJlMDFjMTdjYmU2NGRjMGQ5YTI1MDVjMWNmMjM4MzdjNDllMmFlNzk0YWMxMjE4NCJ9fX0=", "UcCIXU9YXLtZXQ1MRNDKw3OBizTbw8ydZc9gy6F+h1rjgOX09sM/8tIxgeK8Se6794cbZwac6Hn0Io7ivUHZsp0cZ1CM/JNceal+au0JqoUDGTflUuEdgyCVEyp7Ee1/G8yDiQq8/mH87k2OP+QSQY8Ufl6YQkmbucuKhFo7Cf263jOp004pZBsC1avjcxX1nf7cs1FtzcgqIQIWlMC7vqptsfzY1tCI26ARbVoekYNkUgUm1eaqzT6lUqxxyeGZq21mZFfShzA8wtrMMEF9EHsPL2z1AwHkZiThj2dNVYTdpiwtmnRukwsJftbRfvvJ/TWmdjQKKMi7jM8py+nRpoupBGyLT1oyL1IhS2e2gMKeRJtA379WT2p/ZmDhDLHRvE4iAS74iie5aqz/thol/GnbYyV8WZPNm7x7IzqnshoKDyyVfcXnH4dZO2awNCPfYQflp1thAn9GJMoUTVINp6aFY1zoU81kkb98HH9ycXHwXCdpwtuR+m9mPdAdbnMTA3oZAJYf/t/Up+M/rw4jOIqbKAf0LIMJ9px+PH7J14Vd2yW3R8k37AViE4HUfIX3lzeDPt7fpwPFypc+6tz7QHxJnCo4LTCvrxl42iXbZ53qZ8E2X+58sGOzlhs1qu8vYKbc3k5yhGm+koaoPwJo+K5kqV8wF6ndNUMJA3WyZgU=");
		
    	GameProfile profile = ((CraftPlayer)player).getHandle().getProfile();
    	PlayerConnection connection = ((CraftPlayer)player).getHandle().playerConnection;
    	
    	connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, ((CraftPlayer)player).getHandle()));
    	
    	profile.getProperties().removeAll("textures");
    	profile.getProperties().put("textures", property);

    	
    	connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, ((CraftPlayer)player).getHandle()));
    	
//    	Location loc = player.getLocation();
//    	player.teleport(new Location(world2, 0, 256, 0));
//    	
//    	player.hidePlayer(player);
//    	player.showPlayer(player);
//    	
//    	player.teleport(loc);
    	
	}
	
	public void becomeYourself(Player player) {
		
		String name = player.getName();
		
		Property propertyy;
		
    	try {
    		URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + name);
    		InputStreamReader reader = new InputStreamReader(url.openStream());
    		String uuid = new JsonParser().parse(reader).getAsJsonObject().get("id").getAsString();
    		
    		URL url2 = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid	
    				+ "?unsigned=false");
    		InputStreamReader reader2 = new InputStreamReader(url2.openStream());
    		JsonObject property = new JsonParser().parse(reader2).getAsJsonObject().get("properties")
    				.getAsJsonArray().get(0).getAsJsonObject();
    		String texture = property.get("value").getAsString();
    		String signature = property.get("signature").getAsString();
    		propertyy = new Property("textures", texture, signature);
    		

    		
    	}	catch (Exception e) {
    		EntityPlayer p = ((CraftPlayer) player).getHandle();
    		GameProfile profile = p.getProfile();
    		Property property = profile.getProperties().get("textures").iterator().next();
    		String texture = property.getValue();
    		String signature = property.getSignature();
    		propertyy = new Property("textures", texture, signature);
    		
    	}
    	
    	GameProfile profile = ((CraftPlayer)player).getHandle().getProfile();
    	PlayerConnection connection = ((CraftPlayer)player).getHandle().playerConnection;
    	
    	connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, ((CraftPlayer)player).getHandle()));
    	
    	profile.getProperties().removeAll("textures");
    	profile.getProperties().put("textures", propertyy);

    	
    	connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, ((CraftPlayer)player).getHandle()));
	}
		
	
	public void tothevoid(Player player) {
		NPC.createNPC(player, player.getName());
		victimID = NPC.getID(player.getName());
		limboVictim = player;
		limboVictimLocation = player.getLocation();
		victimHealth = limboVictim.getHealth();
		limboVictimInv = limboVictim.getInventory().getContents();
		limboVictim.getInventory().clear();
		

		becomeSoul(player);
		
		BukkitScheduler sched = player.getServer().getScheduler();
		sched.runTaskLater(plugin, new Runnable() {
			
			@Override
			public void run() {
				if(!shooterPower){
					World end = Bukkit.getWorld("world_the_end");
		    		Location nowhere = new Location(end, 69, -420, 69);
		    		player.teleport(nowhere);
		    		player.sendMessage(ChatColor.RED + "Your soul was sent to the mental limbo for 30 seconds");
		    		player.sendMessage(ChatColor.RED + "If someone interrupts your body, you will wake up immediatly");
				}
				
				else {
					World hell = Bukkit.getWorld("world_nether");
					
					Location roof = new Location(hell, 20, 129, 20);
					
					player.teleport(roof);
					player.setHealth(20);
					player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 70, 255));
					
		    		player.sendMessage(ChatColor.DARK_RED + "Your soul was sent to the mental hell for 30 seconds,"
		    				+ " if the demons get your soul, your body will corrupt and die");
		    		player.sendMessage(ChatColor.RED + "If someone interrupts your body, you will wake up immediatly");
					
		    		
		    		summonPigZombies(40, limboVictim.getLocation(), hell);
		    		summonGhasts(15, limboVictim.getLocation(), hell);
		    		
		    		sched.runTaskLater(plugin, new Runnable() {
		    			
		    			@Override
		    			public void run() {
		    				
				    		summonPigZombies(20, limboVictim.getLocation(), hell);
				    		summonGhasts(7, limboVictim.getLocation(), hell);
				    		
				    		limboVictim.sendMessage(ChatColor.DARK_RED + "Another wave has been summoned");
				    		
		    			}
		    			
		    			
		    		}, 200L);
		    		
		    		sched.runTaskLater(plugin, new Runnable() {
		    			
		    			@Override
		    			public void run() {
		    				
				    		summonPigZombies(50, limboVictim.getLocation(), hell);
				    		summonGhasts(20, limboVictim.getLocation(), hell);
				    		
				    		limboVictim.sendMessage(ChatColor.DARK_RED + "A large wave has been summoned, may god have mercy on your soul");
		    			}
		    			
		    			
		    		}, 440L);

					
				}
		    	
		    	limboVictim.playSound(limboVictimLocation, Sound.BLOCK_PORTAL_TRAVEL, 10, 1);
			}
			
			
		}, 1);
		
		
		
		
		voidtask = sched.scheduleSyncRepeatingTask(plugin, new Runnable() {
			double timer = 30.00;		
			@Override
			public void run() {
				
				if(timer <= 0) {
					
					becomeYourself(limboVictim);
					
					player.teleport(limboVictimLocation);
					
					shooter.sendMessage(ChatColor.RED + "It's been 30 seconds, " + limboVictim.getName() + "'s soul has been released");

					player.sendMessage(ChatColor.DARK_AQUA + "It's been 30 seconds, your soul has been released from the mental limbo");
					preventTrap();
										
					NPC.removeNPC(victimID);
					
					limboVictim.getInventory().setContents(limboVictimInv);
					limboVictim.setHealth(victimHealth);

					
					limboVictim = null;
					limboVictimLocation = null;
					Arrays.fill(limboVictimInv, null);
					shooter = null;
					
					if(shooterPower) {
						for(int i = 0; i < pigzombies.size(); i++) {
							pigzombies.get(i).remove();
						}
						pigzombies.clear();
						for(int i = 0; i < ghasts.size(); i++) {
							ghasts.get(i).remove();
						}
						ghasts.clear();
					}
					
					sched.cancelTask(voidtask);
					return;
				}
				
				if(victimDeath) {
					sched.cancelTask(voidtask);
					sched.cancelTask(wave1task);
					sched.cancelTask(wave2task);
					shooter.sendMessage(ChatColor.DARK_GREEN + limboVictim.getName() + "'s soul was eliminated by the demons making his body corrupt and die");
					
					World world = Bukkit.getWorld("world");
					for(int i = 0; i < 40; i++) {
						world.dropItemNaturally(limboVictimLocation, limboVictimInv[i]);
					}
					
					becomeYourself(limboVictim);
					NPC.removeNPC(victimID);
					
					
					limboVictim = null;
					limboVictimLocation = null;
					Arrays.fill(limboVictimInv, null);
					shooter = null;
					
					for(int i = 0; i < pigzombies.size(); i++) {
						pigzombies.get(i).remove();
					}
					pigzombies.clear();
					for(int i = 0; i < ghasts.size(); i++) {
						ghasts.get(i).remove();
					}
					ghasts.clear();
					
					victimDeath = false;
					
					
					
					return;
				}
				
				if(Math.abs(10 - timer) < 1e-4) {
					player.sendMessage(ChatColor.DARK_AQUA + "You have 10 seconds left");
				}
				
				if(Math.abs(5 - timer) < 1e-4) {
					player.sendMessage(ChatColor.DARK_AQUA + "5");
				}
				
				if(Math.abs(4 - timer) < 1e-4) {
					player.sendMessage(ChatColor.DARK_AQUA + "4");
				}
				
				if(Math.abs(3 - timer) < 1e-4) {
					player.sendMessage(ChatColor.DARK_AQUA + "3");
				}
				
				if(Math.abs(2 - timer) < 1e-4) {
					player.sendMessage(ChatColor.DARK_AQUA + "2");
				}
				
				if(Math.abs(1 - timer) < 1e-4) {
					player.sendMessage(ChatColor.DARK_AQUA + "1");
				}
				
				timer -= 0.05;
			}
			
		}, 0, 1L);
		
		
	}
	
	
	public void preventTrap() {
		
		// 3; 3; 3
		Location original = limboVictimLocation;
		limboVictimLocation = limboVictimLocation.clone().subtract(3, 4, 3);
		// 0; 0; 0
		
		for(int i = 0; i < 7; i++) {
			 
			 
			
			for(int j = 0; j < 7; j++) {
				 
				 
				 
				for(int k = 0; k < 7; k++) {
					 
					 
					 limboVictimLocation.getBlock().setType(Material.AIR);
					 limboVictimLocation = limboVictimLocation.clone().add(1, 0, 0);
					 
				}
				
				limboVictimLocation = limboVictimLocation.clone().add(0, 1, 0);
				limboVictimLocation = limboVictimLocation.clone().subtract(7, 0, 0);
				 
			}
			
			
			limboVictimLocation = limboVictimLocation.clone().add(0, 0, 1);
			limboVictimLocation = limboVictimLocation.clone().subtract(0, 7, 0);
			 
		 }
		
		limboVictimLocation = original;
		 
	}
	
	public void summonPigZombies(int amount, Location location, World hell) {
		Location close;
		for(int i = 0; i < amount; i++) {
			close = location.clone().add(random.nextInt(20) - 10, 0, random.nextInt(20) - 10);
			PigZombie pigzombie = (PigZombie) hell.spawnEntity(close, EntityType.ZOMBIFIED_PIGLIN);
			pigzombie.setAngry(true);
			pigzombie.setTarget(limboVictim);
			pigzombies.add(pigzombie);
		}
	}
	
	public void summonGhasts(int amount, Location location, World hell) {
		Location close;
		for(int i = 0; i < amount; i++) {
			close = location.clone().add(random.nextInt(40) - 20, random.nextInt(20) + 5, random.nextInt(40) - 20);
			Ghast ghast = (Ghast) hell.spawnEntity(close, EntityType.GHAST);
			ghast.setTarget(limboVictim);
			ghasts.add(ghast);
		}
	}
	
	
	public void generateEveryoneInvSoul(Player p) {
		
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
		
		everyoneInvSoul = Bukkit.createInventory(null, size, ChatColor.BLUE + "Online players");
		for(Player player : Bukkit.getOnlinePlayers()) {
			
			if(player != p) {
				ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1);
				SkullMeta meta = (SkullMeta) skull.getItemMeta();
				meta.setOwningPlayer(player);
				meta.setDisplayName(ChatColor.RED + player.getName());
				skull.setItemMeta(meta);
				
				everyoneInvSoul.addItem(skull);
			}
			
			
		}
		
		p.openInventory(everyoneInvSoul);
	}
	
	public void generateChooseManipulateInv(Player player){
		manChoice = Bukkit.createInventory(null, 9, ChatColor.GOLD + "Choose effect");
		
		ItemStack back = createGuiItem(Material.RED_WOOL, ChatColor.RED + "Cancel", ChatColor.DARK_RED + "Go back");
		manChoice.setItem(0, back);
		
		ItemStack eye = createGuiItem(Material.ENDER_EYE, ChatColor.BLACK + "Blind", ChatColor.GOLD + "Blind your target");
		manChoice.setItem(4, eye);
		
		ItemStack poison = createGuiItem(Material.SPLASH_POTION, ChatColor.DARK_GREEN + "Toxin", ChatColor.GOLD + "Intoxicate your target's soul");
		PotionMeta meta = (PotionMeta) poison.getItemMeta();
		meta.setColor(Color.GREEN);
		poison.setItemMeta(meta);
		manChoice.setItem(3, poison);
		
		ItemStack fear = createGuiItem(Material.SKELETON_SKULL, ChatColor.DARK_RED + "Fear", ChatColor.GOLD + "Scare your target");
		manChoice.setItem(5, fear);
		
		player.openInventory(manChoice);
	}
	
	
	
	
	//MIND STONE //MIND STONE //MIND STONE //MIND STONE //MIND STONE //MIND STONE //MIND STONE //MIND STONE //MIND STONE 
	
	public void phaseShift(Player player) {
        GameMode pastGamemode = player.getGameMode();
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
	}
	
	
	public void astral(Player player) {
		
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
		
		ItemStack protection = createGuiItem(Material.GOLDEN_APPLE, ChatColor.YELLOW + "Pain killers", ChatColor.GOLD + "You are too smart for pain");
		ItemStack spy = createGuiItem(Material.COMPASS, ChatColor.YELLOW + "", ChatColor.GOLD + "");
		ItemStack astral = createGuiItem(Material.END_CRYSTAL, ChatColor.YELLOW + "Astral vision", ChatColor.GOLD + "Meditate and view the world");
		ItemStack phase = createGuiItem(Material.CHORUS_FRUIT, ChatColor.YELLOW + "Phase shift", ChatColor.GOLD + "Become a ghost for 5 seconds");
		
		mindInv.setItem(19, protection);
		mindInv.setItem(21, spy);
		mindInv.setItem(23, astral);
		mindInv.setItem(25, phase);
		
		
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
		
		ItemStack limbo = createGuiItem(Material.WITHER_SKELETON_SKULL, ChatColor.GOLD + "Mental Limbo",
				ChatColor.RED + "Send a player's soul to a limbo");
		ItemStack manipulate = createGuiItem(Material.TOTEM_OF_UNDYING, ChatColor.GOLD + "Manipulate", 
				ChatColor.RED + "Manipulate whoever you desire");
		
		soulInv.setItem(21, limbo);
		
		soulInv.setItem(23, manipulate);
		
		soulInv.setItem(40, disable);
		
		
		p.openInventory(soulInv);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	//acquired //acquired //acquired //acquired //acquired //acquired //acquired 
	
	public void acquiredSpaceStone(Player p) {
		this.plugin.data.reloadConfig();
		plugin.gloves.get(findPlayer(p)).setSpace(true);
		Bukkit.broadcastMessage(ChatColor.BLUE + p.getName() + " has acquired the space stone");
		p.removePotionEffect(PotionEffectType.SPEED);
		p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, velocity));
		p.closeInventory();
		
		String uuid = p.getUniqueId().toString();
		this.plugin.data.getConfig().set("players." + uuid + ".hasSpace", true);
		this.plugin.data.saveConfig();
	}
	
	public void acquiredMindStone(Player p) {
		this.plugin.data.reloadConfig();
		plugin.gloves.get(findPlayer(p)).setMind(true);
		Bukkit.broadcastMessage(ChatColor.YELLOW + p.getName() + " has acquired the mind stone");
		p.closeInventory();
		
		String uuid = p.getUniqueId().toString();
		this.plugin.data.getConfig().set("players." + uuid + ".hasMind", true);
		this.plugin.data.saveConfig();
	}
	
	public void acquiredRealityStone(Player p) {
		this.plugin.data.reloadConfig();

		plugin.gloves.get(findPlayer(p)).setReality(true);
		Bukkit.broadcastMessage(ChatColor.RED + p.getName() + " has acquired the reality stone");
		p.closeInventory();
		
		String uuid = p.getUniqueId().toString();
		this.plugin.data.getConfig().set("players." + uuid + ".hasReality", true);
		this.plugin.data.saveConfig();
	}
	
	public void acquiredPowerStone(Player p) {
		this.plugin.data.reloadConfig();
		plugin.gloves.get(findPlayer(p)).setPower(true);
		Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + p.getName() + " has acquired the power stone");
		p.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
		p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 2));
		p.closeInventory();
		
		String uuid = p.getUniqueId().toString();
		this.plugin.data.getConfig().set("players." + uuid + ".hasPower", true);
		this.plugin.data.saveConfig();
	}
	
	public void acquiredTimeStone(Player p) {
		this.plugin.data.reloadConfig();
		plugin.gloves.get(findPlayer(p)).setTime(true);
		Bukkit.broadcastMessage(ChatColor.GREEN + p.getName() + " has acquired the time stone");
		p.closeInventory();
		
		String uuid = p.getUniqueId().toString();
		this.plugin.data.getConfig().set("players." + uuid + ".hasTime", true);
		this.plugin.data.saveConfig();
		
		enableRewind = true;
		
		HashMap<Player, TimeStamp> timeStamp = new HashMap<Player, TimeStamp>();
		
		for(Player player : Bukkit.getOnlinePlayers()) {
			timeStamp.put(player, new TimeStamp(plugin.gloves.get(findPlayer(player))));
		}
		timeStamps.push(timeStamp);
		
		
		pastEntities.push(new PastEntities(world));
		
		BukkitScheduler sched = p.getServer().getScheduler();
		
		rewindTask = sched.scheduleSyncRepeatingTask(plugin, new Runnable() {
			
			

			@Override
			public void run() {
				
				if(enableRewind) {
					
					loadouts++;
					
					if(loadouts != 1) {
						if(loadouts > 11) {
							timeStamps.removeElementAt(10);
							pastEntities.removeElementAt(10);
							loadouts--;
						}
						p.sendMessage(ChatColor.GREEN + "A new timestamp has been created");
						HashMap<Player, TimeStamp> timeStamp = new HashMap<Player, TimeStamp>();
						
						for(Player player : Bukkit.getOnlinePlayers()) {
							timeStamp.put(player, new TimeStamp(plugin.gloves.get(findPlayer(player))));
						}
						
						timeStamps.push(timeStamp);
						
						pastEntities.push(new PastEntities(world));
					}
					
					
					
				}

				
			}
			
			
		}, 0L, 1200L);
		
	}
	
	public void acquiredSoulStone(Player p) {
		this.plugin.data.reloadConfig();
		plugin.gloves.get(findPlayer(p)).setSoul(true);
		Bukkit.broadcastMessage(ChatColor.GOLD + p.getName() + " has acquired the soul stone");
		p.closeInventory();
		String uuid = p.getUniqueId().toString();
		this.plugin.data.getConfig().set("players." + uuid + ".hasSoul", true);
		this.plugin.data.saveConfig();
		
		//NPC.createNPC(p, p.getName());
	}
	
	

  
	

	

}