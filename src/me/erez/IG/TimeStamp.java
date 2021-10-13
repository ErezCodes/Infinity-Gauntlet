package me.erez.IG;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public class TimeStamp{
	

	private Main plugin;
    public TimeStamp (Main plugin) {
        this.plugin = plugin;
    }
    
    //Add cooldowns sometime
    
    private Glove glove;
    private Player player;
    private Location location;
    private double health;
    
    private Inventory inventory;
    private ItemStack[] inventoryContents;
    private Inventory enderChestInv;
    private ItemStack[] enderChestStorage;
    private ItemStack[] manualEnderChestStorage = new ItemStack[27];
    private Inventory temporaryEnderChestInventory;
    
    private boolean space;
    private boolean mind;
    private boolean reality;
    private boolean power;
    private boolean time;
    private boolean soul;
    private String equipped;
    
    private ArrayList<PotionEffect> effects = new ArrayList<PotionEffect>();
//    private ArrayList<Integer> durations = new ArrayList<Integer>();
//    private ArrayList<Integer> amplifiers = new ArrayList<Integer>();
    
    
    private HashMap<Location, Material> brokenBlocks = new HashMap<>();
    private ArrayList<Location> placedBlocks = new ArrayList<>();
    
    
    
    //lava and water placed by players
    //tnt?
	
    public TimeStamp(Glove g) {
		glove = g;
		
		construct();
	}
    
    public void construct() {
    	player = glove.getPlayer();
    	location = player.getLocation();
    	health = player.getHealth();
    	inventory = player.getInventory();	
    	inventoryContents = inventory.getContents();

    
    	temporaryEnderChestInventory = Bukkit.createInventory(player, 27, "Ender Chest");
    	temporaryEnderChestInventory.setContents(player.getEnderChest().getContents());

    	effects = (ArrayList<PotionEffect>) player.getActivePotionEffects();
    	
    	space = glove.getSpace();
    	mind = glove.getMind();
    	reality = glove.getReality();
    	power = glove.getPower();
    	time = glove.getTime();
    	soul = glove.getSoul();
    	equipped = glove.getEquipped();
    	
    	
    }
    
    
    public void restoreGlove() {
    	
    	
    	glove.setMind(mind);
    	glove.setPower(power);
    	glove.setReality(reality);
    	glove.setSoul(soul);
    	glove.setSpace(space);
    	glove.setTime(time);
    	glove.setEquipped(equipped);
    	
    }
    
    public void restoreBrokenBlocks() {
    	
    	for(Location loc : brokenBlocks.keySet()) {
    		loc.getBlock().setType(brokenBlocks.get(loc));
    	}
    	brokenBlocks.clear();
    	
    }
    
    public void restorePlacedBlocks() {
    	
    	for(int i = 0; i < placedBlocks.size(); i++) {
    		placedBlocks.get(i).getBlock().setType(Material.AIR);
    	}
    	placedBlocks.clear();
    	
    }
    
    public void restorePlayer() {
    	//effects?
    	player.setHealth(health);
    	player.teleport(location);
    	
    	//player.getInventory().clear();
    	player.getInventory().setContents(inventoryContents);
    	player.updateInventory();

    	player.getEnderChest().setContents(temporaryEnderChestInventory.getContents());
    	
    	for(PotionEffect effect : player.getActivePotionEffects()) {
    		player.removePotionEffect(effect.getType());
    	}
    	
    	for(int i = 0; i < effects.size(); i++) {
    		player.addPotionEffect(effects.get(i));
    	}
    	//player.sendMessage("andddd done");
    	
    	
    	
    }
    
    
	public int findPlayer(Player p) {

		for (int i = 0; i < plugin.gloves.size(); i++) {
			if (plugin.gloves.get(i).getPlayer().equals(p))
				return i;
		}

		return -1;
	}


	public Glove getGlove() {
		return glove;
	}


	public void setGlove(Glove glove) {
		this.glove = glove;
	}


	public Player getPlayer() {
		return player;
	}


	public void setPlayer(Player player) {
		this.player = player;
	}


	public Location getLocation() {
		return location;
	}


	public void setLocation(Location location) {
		this.location = location;
	}


	public double getHealth() {
		return health;
	}


	public void setHealth(double health) {
		this.health = health;
	}


	public HashMap<Location, Material> getBrokenBlocks() {
		return brokenBlocks;
	}


	public void setBrokenBlocks(HashMap<Location, Material> brokenBlocks) {
		this.brokenBlocks = brokenBlocks;
	}


	public ArrayList<Location> getPlacedBlocks() {
		return placedBlocks;
	}


	public void setPlacedBlocks(ArrayList<Location> placedBlocks) {
		this.placedBlocks = placedBlocks;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public Inventory getEnderChestInv() {
		return enderChestInv;
	}

	public void setEnderChestInv(Inventory enderChestInv) {
		this.enderChestInv = enderChestInv;
	}

	@Override
	public String toString() {
		return "TimeStamp [glove=" + glove + ", player=" + player + ", location=" + location + ", health=" + health
				+ ", inventory=" + inventory + ", enderChestInv=" + enderChestInv + ", brokenBlocks=" + brokenBlocks
				+ ", placedBlocks=" + placedBlocks + "]";
	}


	
}
