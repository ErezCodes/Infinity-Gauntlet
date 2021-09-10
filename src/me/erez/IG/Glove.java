package me.erez.IG;

import java.util.ArrayList;
import java.util.Arrays;


import me.erez.IG.Main;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Glove {

    private Main plugin;
    public Glove (Main plugin) {
        this.plugin = plugin;
    }
    
	private Player player;
	private Inventory inventory = null;
	private boolean hasSpace;
	private boolean hasMind;
	private boolean hasReality;
	private boolean hasPower;
	private boolean hasTime;
	private boolean hasSoul;
	private String equipped;
	
	private ArrayList<String> cycle = new ArrayList<String>();
	private ArrayList<String> cycleNames = new ArrayList<String>();
	private int position = -1;
	//selfShort, targetedShort, global, astral, phaseShift, blockChanger, itemChanger, Power, Time, Rewind
	
	private ItemStack blueGlass = createGuiItem(Material.BLUE_STAINED_GLASS_PANE, " ", " ");
	private ItemStack yellowGlass = createGuiItem(Material.YELLOW_STAINED_GLASS_PANE, " ", " ");
	private ItemStack redGlass = createGuiItem(Material.RED_STAINED_GLASS_PANE, " ", " ");
	private ItemStack purpleGlass = createGuiItem(Material.PURPLE_STAINED_GLASS_PANE, " ", " ");
	private ItemStack greenGlass = createGuiItem(Material.LIME_STAINED_GLASS_PANE, " ", " ");
	private ItemStack orangeGlass = createGuiItem(Material.ORANGE_STAINED_GLASS_PANE, " ", " ");
	
	private ItemStack lapisBlock = createGuiItem(Material.LAPIS_BLOCK, " ", " ");
	private ItemStack yellowBlock = createGuiItem(Material.YELLOW_WOOL, " ", " ");
	private ItemStack redstoneBlock = createGuiItem(Material.REDSTONE_BLOCK, " ", " ");
	private ItemStack purpleBlock = createGuiItem(Material.PURPLE_WOOL, " ", " ");
	private ItemStack emeraldBlock = createGuiItem(Material.EMERALD_BLOCK, " ", " ");
	private ItemStack orangeBlock = createGuiItem(Material.ORANGE_WOOL, " ", " ");
	
	private ItemStack SpaceStone = createGuiItem(Material.LAPIS_LAZULI, ChatColor.BLUE + "Space Stone", ChatColor.DARK_BLUE + "I'm everywhere");
	private ItemStack MindStone = createGuiItem(Material.YELLOW_DYE, ChatColor.YELLOW + "Mind Stone", ChatColor.GOLD + "The ultimate manipulator");
	private ItemStack RealityStone = createGuiItem(Material.REDSTONE, ChatColor.RED + "Reality Stone", ChatColor.DARK_RED + "Reality can be whatever I want");
	private ItemStack PowerStone = createGuiItem(Material.PURPLE_DYE, ChatColor.LIGHT_PURPLE + "Power Stone", ChatColor.DARK_PURPLE + "Power is addicting");
	private ItemStack TimeStone = createGuiItem(Material.EMERALD, ChatColor.GREEN + "Time Stone", ChatColor.DARK_GREEN + "Infinite realities");
	private ItemStack SoulStone = createGuiItem(Material.ORANGE_DYE, ChatColor.GOLD + "Soul Stone", "What did it cost?");
	
	
	public Glove(Player p, String eq, boolean space, boolean mind, boolean reality, boolean power, boolean time, boolean soul) {
		
		player = p;
		equipped = eq;
		hasSpace = space; //Blue, Lapis lazuli
		hasMind = mind; //Yellow
		hasReality = reality; //Red, redstone
		hasPower = power; //purple
		hasTime = time; //Green, emerald
		hasSoul = soul; //Orange
		
		fillCycle();
		
	}
	
	public void generateInventory() {
		
		inventory = Bukkit.createInventory(null, 54, ChatColor.DARK_GREEN + player.getName() + "'s glove");
		
		ItemStack blue = blueGlass;
		if(equipped.equals("selfShort") || equipped.equals("targetedShort") || equipped.equals("global")){
			addEmptyEnchantment(blue);
		}
		else {
			removeEnchantments(blue);
		}
		
		inventory.setItem(0, blue);
		inventory.setItem(1, blue);
		inventory.setItem(2, blue);
		inventory.setItem(9, blue);
		inventory.setItem(11, blue);
		inventory.setItem(18, blue);
		inventory.setItem(19, blue);
		inventory.setItem(20, blue);
		
		if(hasSpace) {
			inventory.setItem(10, SpaceStone);
		}
		
		
		ItemStack yellow = yellowGlass;
		if(equipped.equals("astral") || equipped.equalsIgnoreCase("phaseShift")) {
			addEmptyEnchantment(yellow);
		}
		else {
			removeEnchantments(yellow);
		}
		
		inventory.setItem(3, yellow);
		inventory.setItem(4, yellow);
		inventory.setItem(5, yellow);
		inventory.setItem(12, yellow);
		inventory.setItem(14, yellow);
		inventory.setItem(21, yellow);
		inventory.setItem(22, yellow);
		inventory.setItem(23, yellow);
		
		if(hasMind) {
			inventory.setItem(13, MindStone);
		}
		
		
		ItemStack red = redGlass;
		if(equipped.equals("itemChanger") || equipped.equals("blockChanger")) {
			addEmptyEnchantment(red);
		}
		else {
			removeEnchantments(red);
		}
		
		inventory.setItem(6, red);
		inventory.setItem(7, red);
		inventory.setItem(8, red);
		inventory.setItem(15, red);
		inventory.setItem(17, red);
		inventory.setItem(24, red);
		inventory.setItem(25, red);
		inventory.setItem(26, red);
		
		if(hasReality) {
			inventory.setItem(16, RealityStone);
		}
		
		
		ItemStack purple = purpleGlass;
		if(equipped.equals("Power")) {
			addEmptyEnchantment(purple);
		}
		
		else {
			removeEnchantments(purple);
		}
		
		inventory.setItem(27, purple);
		inventory.setItem(28, purple);
		inventory.setItem(29, purple);
		inventory.setItem(36, purple);
		inventory.setItem(38, purple);
		inventory.setItem(27, purple);
		inventory.setItem(45, purple);
		inventory.setItem(46, purple);
		inventory.setItem(47, purple);


		if(hasPower) {
			inventory.setItem(37, PowerStone);
		}
		
		
		ItemStack green = greenGlass;
		if(equipped.equals("Time") || equipped.equals("Rewind")) {
			addEmptyEnchantment(green);
		}
		
		else {
			removeEnchantments(green);
		}
		
		inventory.setItem(30, green);
		inventory.setItem(31, green);
		inventory.setItem(32, green);
		inventory.setItem(39, green);
		inventory.setItem(41, green);
		inventory.setItem(48, green);
		inventory.setItem(49, green);
		inventory.setItem(50, green);
		
		if(hasTime) {
			inventory.setItem(40, TimeStone);
		}
		ItemStack orange = orangeGlass;
		if(equipped.equals("limbo") || equipped.equals("manipulate")) {
			addEmptyEnchantment(orange);
		}
		
		else {
			removeEnchantments(orange);
		}
		
		inventory.setItem(33, orange);
		inventory.setItem(34, orange);
		inventory.setItem(35, orange);
		inventory.setItem(42, orange);
		inventory.setItem(44, orange);
		inventory.setItem(51, orange);
		inventory.setItem(52, orange);
		inventory.setItem(53, orange);
		
		if(hasSoul) {
			inventory.setItem(43, SoulStone);
		}
		
//		if(equipped.equals("none")) {
//			removeEnchantments(blue);
//			removeEnchantments(yellow);
//			removeEnchantments(red);
//			removeEnchantments(purple);
//			removeEnchantments(green);
//			removeEnchantments(orange);
//		}
		
		player.openInventory(inventory);
		
	}
	
	public void emptyGlove() {
		World world = player.getWorld();
		Location loc = player.getLocation();
		
		if(hasSpace) {
			hasSpace = false;
			world.dropItemNaturally(loc, plugin.SpaceStone);
		}
		
		if(hasMind) {
			hasMind = false;
			world.dropItemNaturally(loc, plugin.MindStone);
		}
		
		if(hasReality) {
			hasReality = false;
			world.dropItemNaturally(loc, plugin.RealityStone);
		}
		
		if(hasPower) {
			hasPower = false;
			world.dropItemNaturally(loc, plugin.PowerStone);
		}
		
		if(hasTime) {
			hasTime = false;
			world.dropItemNaturally(loc, plugin.TimeStone);
		}
		
		if(hasSoul) {
			hasSoul = false;
			world.dropItemNaturally(loc, plugin.SoulStone);
		}
		
	}
	
	public String toString() {		
		
		return "Owner: " + player.getName() +"\n" + "Current stone equipped: " + equipped +"\n" + ChatColor.DARK_BLUE + (hasSpace) +
				"\n" + ChatColor.YELLOW + (hasMind) + "\n" + ChatColor.DARK_RED + (hasReality) + "\n" + ChatColor.DARK_PURPLE + (hasPower)
				+ "\n" + ChatColor.DARK_GREEN + (hasTime) + "\n" + ChatColor.GOLD + (hasSoul);
 	}
	
	public void fillCycle() {
		cycle.add("selfShort"); //0
		cycle.add("targetedShort"); //1
		cycle.add("global"); //2
		cycle.add("astral"); //3
		cycle.add("phaseShift"); //4
		cycle.add("blockChanger"); //5
		cycle.add("itemChanger"); //6
		cycle.add("Power"); //7
		cycle.add("Time"); //8
		cycle.add("Rewind"); //9
		cycle.add("limbo"); //10
		cycle.add("manipulate"); //11
		
		cycleNames.add(ChatColor.UNDERLINE + "Self close teleportation"); //0
		cycleNames.add(ChatColor.UNDERLINE + "Targeted close teleportation"); //1
		cycleNames.add(ChatColor.UNDERLINE + "Global teleportation"); //2
		cycleNames.add(ChatColor.UNDERLINE + "Astral vision"); //3
		cycleNames.add(ChatColor.UNDERLINE + "Phase shift"); //4
		cycleNames.add(ChatColor.UNDERLINE + "Block changer"); //5
		cycleNames.add(ChatColor.UNDERLINE + "Item changer"); //6
		cycleNames.add(ChatColor.UNDERLINE + "Power projectile"); //7
		cycleNames.add(ChatColor.UNDERLINE + "Time freezer"); //8
		cycleNames.add(ChatColor.UNDERLINE + "Time rewinder"); //9
		cycleNames.add(ChatColor.UNDERLINE + "Limbo"); //10
		cycleNames.add(ChatColor.UNDERLINE + "Manipulator"); //11
		
		
		
	}
	
	public void cycle(boolean reverse) {
		
		if(position == -1) {
			if(hasSpace) {
				if(reverse) 
					position = 2;
				
				else position = 0;
				
				equipped = cycle.get(position);
				
				player.sendMessage(ChatColor.BLUE + message(cycleNames.get(position)));
				
				return;
			}
			
			
			if(hasMind) {
				
				if(reverse)
					position = 4;
				else position = 3;
				
				equipped = cycle.get(position);
				
				player.sendMessage(ChatColor.YELLOW + message(cycleNames.get(position)));
				
				return;
			}
			
			if(hasReality) {
				
				if(reverse)
					position = 6;
				else position = 5;
				
				equipped = cycle.get(position);
				
				player.sendMessage(ChatColor.RED + message(cycleNames.get(position)));
				
				return;
				
			}
			
			if(hasPower) {
				
				equipped = cycle.get(7);
				
				player.sendMessage(ChatColor.DARK_PURPLE + message(cycleNames.get(position)));
				
				return;
				
			}
			
			if(hasTime) {
				
				if(reverse)
					position = 9;
				else position = 8;
				
				equipped = cycle.get(position);
				
				player.sendMessage(ChatColor.DARK_GREEN + message(cycleNames.get(position)));
				
				return;
				
			}
			
			if(hasSoul) {
				
				if(reverse)
					position = 11;
				else position = 10;
				
				equipped = cycle.get(position);
				
				player.sendMessage(ChatColor.GOLD + message(cycleNames.get(position)));
				
			}
			
			player.sendMessage("You do not have any stones");
			
			return;
			
			
			
		}
		//space
		if(position <= 2) {
			
			if(position == 1) {
				
				if(reverse) 
					position = 0;
				else position = 2;
				
				equipped = cycle.get(position);
				player.sendMessage(message(cycleNames.get(position)));
				return;
				
			}
			
			if(position == 0) {
				
				if(!reverse) {
					position++;
					equipped = cycle.get(position);
					player.sendMessage(message(cycleNames.get(position)));
					return;
				}
				
				if(hasSoul) 
					position = 11;			
				else if(hasTime) 
					position = 9;
				else if(hasPower)
					position = 7;
				else if(hasReality)
					position = 6;
				else if(hasMind)
					position = 4;
				else 
					position = 2;
				
				equipped = cycle.get(position);
				player.sendMessage(message(cycleNames.get(position)));
				return;
				
			}
			
			//2
			if(reverse) {
				position = 1;
			
				equipped = cycle.get(position);
				player.sendMessage(message(cycleNames.get(position)));
				return;
			}
			
			if(hasMind)
				position = 3;
			else if(hasReality) 
				position = 5;
			else if(hasPower)
				position = 7;
			else if(hasTime)
				position = 8;
			else if(hasSoul)
				position = 10;
			else 
				position = 0;
			
			equipped = cycle.get(position);
			player.sendMessage(message(cycleNames.get(position)));
			return;
			
		}
		//mind
		if(position <= 4) {
			
			if(position == 3) {
				
				if(!reverse) {
					position++;
					
					equipped = cycle.get(position);
					player.sendMessage(message(cycleNames.get(position)));
					return;
				}
				
				if(hasSpace)
					position = 2;
				else if(hasSoul)
					position = 11;
				else if(hasTime)
					position = 9;
				else if(hasPower)
					position = 7;
				else if(hasReality)
					position = 6;
				else
					position = 4;
				
				equipped = cycle.get(position);
				player.sendMessage(message(cycleNames.get(position)));
				return;
				
			}
		
			//4
			if(reverse) {
				position--;
			
				equipped = cycle.get(position);
				player.sendMessage(message(cycleNames.get(position)));
				return;
			}
			
			if(hasReality) 
				position = 5;
			else if(hasPower)
				position = 7;
			else if(hasTime)
				position = 8;
			else if(hasSoul)
				position = 10;
			else if(hasSpace)
				position = 0;
			else
				position = 3;
			
			equipped = cycle.get(position);
			player.sendMessage(message(cycleNames.get(position)));
			return;
				
			
			
		}
		//reality
		if(position <= 6) {
			
			if(position == 5) {
				
				if(!reverse) {
					position++;
					
					equipped = cycle.get(position);
					player.sendMessage(message(cycleNames.get(position)));
					return;
				}
				
				if(hasMind)
					position = 4;
				else if(hasSpace) 
					position = 2;
				else if(hasSoul)
					position = 11;
				else if(hasTime)
					position = 9;
				else if(hasPower)
					position = 7;
				else 
					position = 6;

				equipped = cycle.get(position);
				player.sendMessage(message(cycleNames.get(position)));
				return;
				
			}
			
			//6
			if(reverse) {
				position--;
				
				equipped = cycle.get(position);
				player.sendMessage(message(cycleNames.get(position)));
				return;
			}
			
			if(hasPower)
				position = 7;
			else if(hasTime)
				position = 8;
			else if(hasSoul)
				position = 10;
			else if(hasSpace)
				position = 0;
			else if(hasMind)
				position = 3;
			else
				position = 5;
			
			equipped = cycle.get(position);
			player.sendMessage(message(cycleNames.get(position)));
			return;
			
		}
		//power
		if(position == 7) {
		
			if(reverse) {
				if(hasReality)
					position = 6;
				else if(hasMind)
					position = 4;
				else if(hasSpace)
					position = 2;
				else if(hasSoul)
					position = 11;
				else if(hasTime)
					position = 9;
				
				equipped = cycle.get(position);
				player.sendMessage(message(cycleNames.get(position)));
				return;
			}
			
			if(hasTime)
				position = 8;
			else if(hasSoul)
				position = 10;
			else if(hasSpace)
				position = 0;
			else if(hasMind)
				position = 3;
			else if(hasReality)
				position = 5;
			
			equipped = cycle.get(position);
			player.sendMessage(message(cycleNames.get(position)));
			return;
			
		}
		//time
		if(position <= 9) {
			
			if(position == 8) {
				
				if(!reverse) {
					position++;
					
					equipped = cycle.get(position);
					player.sendMessage(message(cycleNames.get(position)));
					return;
				}
				
				if(hasPower)
					position = 7;
				else if(hasReality)
					position = 6;
				else if(hasMind)
					position = 4;
				else if(hasSpace)
					position = 2;
				else if(hasSoul)
					position = 11;
				else position = 9;
				
				equipped = cycle.get(position);
				player.sendMessage(message(cycleNames.get(position)));
				return;
				
				
			}
			
			//9
			if(reverse) {
				position--;
				
				equipped = cycle.get(position);
				player.sendMessage(message(cycleNames.get(position)));
				return;
			}
			
			if(hasSoul)
				position = 10;
			else if(hasSpace)
				position = 0;
			else if(hasMind)
				position = 3;
			else if(hasReality)
				position = 5;
			else if(hasPower)
				position = 7;
			else
				position = 8;
			
			equipped = cycle.get(position);
			player.sendMessage(message(cycleNames.get(position)));
			return;
			
		}
		//soul
		if(position <= 11) {
			
			if(position == 10) {
				
				if(!reverse) {
					position++;
					
					equipped = cycle.get(position);
					player.sendMessage(message(cycleNames.get(position)));
					return;
				}
				
				if(hasTime)
					position = 9;
				else if(hasPower)
					position = 7;
				else if(hasReality)
					position = 6;
				else if(hasMind)
					position = 4;
				else if(hasSpace)
					position = 2;
				else
					position = 11;
				
				equipped = cycle.get(position);
				player.sendMessage(message(cycleNames.get(position)));
				return;
			}
			
			//11
			if(reverse) {
				position--;
				
				equipped = cycle.get(position);
				player.sendMessage(message(cycleNames.get(position)));
				return;
			}
			
			if(hasSpace) 
				position = 0;
			else if(hasMind)
				position = 3;
			else if(hasReality)
				position = 5;
			else if(hasPower)
				position = 7;
			else if(hasTime)
				position = 8;
			else
				position = 10;
			
			equipped = cycle.get(position);
			player.sendMessage(message(cycleNames.get(position)));
			
			
		}
		
	}
	
	public String message(String ability) {
		ChatColor color = ChatColor.WHITE;
		if(position <= 2)
			color = ChatColor.BLUE;
		else if(position <= 4)
			color = ChatColor.YELLOW;
		else if(position <= 6)
			color = ChatColor.RED;
		else if(position == 7)
			color = ChatColor.DARK_PURPLE;
		else if(position <= 9)
			color = ChatColor.DARK_GREEN;
		else if(position <= 11)
			color = ChatColor.GOLD;
		
		player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
			
		return color + "You have equipped the " + ability + " ability";
	}
	
	//sets
	
	public void setSpace(Boolean bool) {
		this.hasSpace = bool;
	}
	
	public void setMind(Boolean bool) {
		this.hasMind = bool;
	}
	
	public void setReality(Boolean bool) {
		this.hasReality = bool;
	}
	
	public void setPower(Boolean bool) {
		this.hasPower = bool;
	}
	
	public void setTime(Boolean bool) {
		this.hasTime = bool;
	}
	
	public void setSoul(Boolean bool) {
		this.hasSoul = bool;
	}
	
	public void setEquipped(String s) {
		this.equipped = s;
	}
	
	//gets
	
	public Player getPlayer() {
		return player;
	}
	
	public Boolean getSpace() {
		return hasSpace;
	}
	
	public Boolean getMind() {
		return hasMind;
	}
	
	public Boolean getReality() {
		return hasReality;
	}
	
	public Boolean getPower() {
		return hasPower;
	}
	
	public Boolean getTime() {
		return hasTime;
	}
	
	public Boolean getSoul() {
		return hasSoul;
	}
	
	public String getEquipped() {
		return equipped;
	}
	
	
	//imported useful functions
	
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
	
	public void addEmptyEnchantment(ItemStack item) {
		item.addUnsafeEnchantment(Enchantment.WATER_WORKER, 1);
		ItemMeta meta = item.getItemMeta();
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		item.setItemMeta(meta);
	}

	public void removeEnchantments(ItemStack item) {
	for(Enchantment e : item.getEnchantments().keySet()){
		item.removeEnchantment(e);
		}
	}
	



}