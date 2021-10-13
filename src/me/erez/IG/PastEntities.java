package me.erez.IG;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;

//EntityExplodeEvent
//BlockBurnEvent
public class PastEntities {
	
	private World world;
    private ArrayList<Entity> entityArrayList = new ArrayList<Entity>();;
    private HashMap<Entity, Location> entityHashMap = new HashMap<Entity, Location>();
    private HashMap<Chest, ItemStack[]> chests = new HashMap<Chest, ItemStack[]>();
    private ArrayList<HashMap<Location, Material>> explodedBlocks = new ArrayList<HashMap<Location, Material>>();
    private ArrayList<HashMap<Location, Material>> burntBlocks = new ArrayList<HashMap<Location, Material>>();
    private long worldTime = 0L;

    
    public PastEntities(World w) {

		world = w;

		construct();
		
	}
    

    
	public void construct() {
    	
    	worldTime = world.getTime();
		entityArrayList = (ArrayList<Entity>) world.getEntities();
		fillHashMap();
		for(Chunk c : world.getLoadedChunks()){
            for(BlockState b : c.getTileEntities()){
                if(b instanceof Chest){
                	
                	//chests.add((Chest) b);
                	Chest chest = (Chest) b;
                	chests.put(chest, chest.getInventory().getContents());
                	
                
                }
            }
        }
		
    }
	


    
    public void fillHashMap() {
    	
    	for(int i = 0; i < entityArrayList.size(); i++) {
    		
    		entityHashMap.put(entityArrayList.get(i), entityArrayList.get(i).getLocation());
    		
    	}
    	
    }
    
    //stop CreatureSpawnEvent for a few moments//
    
    public void restoreEntities() {
    	/*
    	ArrayList<Entity> currentEntities = (ArrayList<Entity>) world.getEntities();
    	Location azzah = new Location(world, 0, -1000, 0);
    	int entitiesSize = currentEntities.size();
    	for(int i = 0; i < entitiesSize; i++) {
    		Entity entity = currentEntities.get(i);
    		
    		if(!(entity instanceof Player)){
    			  entity.teleport(azzah);
			}
    		
    	}
    	
    	for(Entity entity : entityHashMap.keySet()) {

    		world.spawnEntity(entityHashMap.get(entity), entity.getType());
    		
    	}
    	
    	world.setTime(worldTime);
    	
    	*/
    	int size = entityArrayList.size();
    	Location azzah = new Location(world, 700000, -10000, -700000);
    	for(int i = 0; i < size; i++) {
    		if(!(entityArrayList.get(i) instanceof Player))
    			entityArrayList.get(i).teleport(azzah);
    	}
    	
    	for(Entity entity : entityHashMap.keySet()) {
    		
    		if(!(entity instanceof Player)) {
    			world.spawnEntity(entityHashMap.get(entity), entity.getType());
    		}
    	}
    	
    	world.setTime(worldTime);
    	
    }
    
    public void restoreChests() {
    	
    	for(Chest chest : chests.keySet()) {
    		
    		
    		Location loc = chest.getLocation();
    		loc.getBlock().setType(Material.CHEST);
    		chest.getInventory().setContents(chests.get(chest));
    		loc.getBlock().setBlockData(chest.getBlockData());
    		
    	}
    	
    }

    public void restoreExplodedBlocks() {
    	
    	
    	for(int i = 0; i < explodedBlocks.size(); i++) {
    		
    		HashMap<Location, Material> hashy = explodedBlocks.get(i);

    		
    		for(Location loc : hashy.keySet()) {
    			loc.getBlock().setType(hashy.get(loc));
    		}
    		
    	}
    	
    	
    }
    
    public void restoreBurntBlocks() {
    	
    	for(int i = 0; i < burntBlocks.size(); i++) {
    		
    		HashMap<Location, Material> hashy = burntBlocks.get(i);

    		
    		for(Location loc : hashy.keySet()) {
    			loc.getBlock().setType(hashy.get(loc));
    		}
    		
    	}
    	
    	
    }
    
    public void addExplodedBlocks(HashMap<Location, Material> hashy) {
    	explodedBlocks.add(hashy);
    }
    
    public void addBurntBlock(HashMap<Location, Material> hashy) {
    	burntBlocks.add(hashy);
    }


	public ArrayList<Entity> getEntityArrayList() {
		return entityArrayList;
	}



	public void setEntityArrayList(ArrayList<Entity> entityArrayList) {
		this.entityArrayList = entityArrayList;
	}



	public HashMap<Entity, Location> getEntityHashMap() {
		return entityHashMap;
	}



	public void setEntityHashMap(HashMap<Entity, Location> entityHashMap) {
		this.entityHashMap = entityHashMap;
	}



	public World getWorld() {
		return world;
	}



	public void setWorld(World world) {
		this.world = world;
	}



	
    public long getWorldTime() {
		return worldTime;
	}

	public void setWorldTime(long worldTime) {
		this.worldTime = worldTime;
	}



	@Override
	public String toString() {
		return "PastEntities [world=" + world + ", entityArrayList=" + entityArrayList + ", entityHashMap="
				+ entityHashMap + ", chests=" + chests + ", worldTime=" + worldTime + "]";
	}



	public HashMap<Chest, ItemStack[]> getChests() {
		return chests;
	}



	public void setChests(HashMap<Chest, ItemStack[]> chests) {
		this.chests = chests;
	}
    
}
