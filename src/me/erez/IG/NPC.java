package me.erez.IG;

import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.craftbukkit.v1_16_R3.CraftServer;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_16_R3.DataWatcher;
import net.minecraft.server.v1_16_R3.DataWatcherObject;
import net.minecraft.server.v1_16_R3.DataWatcherRegistry;
import net.minecraft.server.v1_16_R3.DedicatedServer;
import net.minecraft.server.v1_16_R3.EntityPlayer;
import net.minecraft.server.v1_16_R3.EntityPose;
import net.minecraft.server.v1_16_R3.EnumItemSlot;
import net.minecraft.server.v1_16_R3.IMaterial;
import net.minecraft.server.v1_16_R3.Item;
import net.minecraft.server.v1_16_R3.MinecraftServer;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntityHeadRotation;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntityMetadata;
import net.minecraft.server.v1_16_R3.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_16_R3.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_16_R3.PlayerConnection;
import net.minecraft.server.v1_16_R3.PlayerInteractManager;
import net.minecraft.server.v1_16_R3.WorldServer;
import net.minecraft.server.v1_16_R3.PacketPlayOutPlayerInfo.EnumPlayerInfoAction;

public class NPC {
	
	private static List<EntityPlayer> NPC = new ArrayList<EntityPlayer>();
	
	public static void createNPC(Player player, String skin) {
		//Server server = ((Server) Bukkit.getServer());
		MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
		WorldServer world = ((CraftWorld) Bukkit.getWorld(player.getWorld().getName())).getHandle();
		GameProfile gameProfile = new GameProfile(UUID.randomUUID(), nameAdjuster(player.getName()));
		EntityPlayer npc = new EntityPlayer(server, world, gameProfile, new PlayerInteractManager(world));
		npc.setLocation(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(),
				player.getLocation().getYaw(), player.getLocation().getPitch());
/*
		//npc.inventory.armor.add(new ItemStack(Item.getById(838)));
//		for(int i = 0; i < 1000; i++) {
//			Bukkit.broadcastMessage("" + i + ": " + new ItemStack(Item.getById(i)).toString());
//		}
		//npc.setPose(EntityPose.CROUCHING);
		
		//Bukkit.broadcastMessage("" + npc.getEquipment(EnumItemSlot.CHEST));
		
		ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1);
		SkullMeta meta = (SkullMeta) skull.getItemMeta();
		meta.setOwningPlayer(player);
		skull.setItemMeta(meta);
//		net.minecraft.server.v1_16_R3.ItemStack item = CraftItemStack.asNMSCopy(skull);
*/			

	    DataWatcher watcher = npc.getDataWatcher();
	    watcher.set(new DataWatcherObject<>(16, DataWatcherRegistry.a), (byte) 0xFF);
		
		
		Property name = getSkin(player, skin);
		gameProfile.getProperties().put("textures", name);
		
		for (Player pp : Bukkit.getOnlinePlayers()) {
			PlayerConnection connection = ((CraftPlayer)pp).getHandle().playerConnection;
			connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc));
			connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
			connection.sendPacket(new PacketPlayOutEntityHeadRotation(npc, (byte) (npc.yaw * 256 /360)));
			connection.sendPacket(new PacketPlayOutEntityMetadata(npc.getId(), watcher, true));
		}
		
		NPC.add(npc);
		

	}
	
    //player.getName() and name are the same
    private static Property getSkin(Player player, String name) {
    	
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
    		return new Property("textures", texture, signature);
    		

    		
    	}	catch (Exception e) {
    		EntityPlayer p = ((CraftPlayer) player).getHandle();
    		GameProfile profile = p.getProfile();
    		Property property = profile.getProperties().get("textures").iterator().next();
    		String texture = property.getValue();
    		String signature = property.getSignature();
    		return new Property("textures", texture, signature);
    		
    	}
    	
    	
    }
	
	public static void addNPCPacket(EntityPlayer npc) {

	}
	
	public static void removeNPCPackets() {
		
		for(EntityPlayer npc : NPC) {
		
			for (Player player : Bukkit.getOnlinePlayers()) {
				PlayerConnection connection = ((CraftPlayer)player).getHandle().playerConnection;
				connection.sendPacket(new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.REMOVE_PLAYER, npc));
				connection.sendPacket(new PacketPlayOutEntityDestroy(npc.getId()));
			}
		
		}
		
		NPC.clear();
	}
	
	public static void removeNPC(int id) {
		for(EntityPlayer npc : NPC) {
			
			if(npc.getId() == id) {
				for (Player player : Bukkit.getOnlinePlayers()) {
					PlayerConnection connection = ((CraftPlayer)player).getHandle().playerConnection;
					connection.sendPacket(new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.REMOVE_PLAYER, npc));
					connection.sendPacket(new PacketPlayOutEntityDestroy(id));
					NPC.remove(npc);
				}
				return;
			}
			
		}
	}
	
	public static void addJoinPacket(Player player) {
		for (EntityPlayer npc : NPC) {
			PlayerConnection connection = ((CraftPlayer)player).getHandle().playerConnection;
			connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc));
			connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
			connection.sendPacket(new PacketPlayOutEntityHeadRotation(npc, (byte) (npc.yaw * 256 /360)));
		}
	}
	
	public static List<EntityPlayer> getNPCS(){
		return NPC;
	}
	
	public static String nameAdjuster(String name) {
		
		if(name.length() > 9) {
			
			while(name.length() != 9) {
				name = name.substring(0, name.length() -1);
			}
			
		}
		
		
		return name + "'s body";
	}
	
	public static int getID(String name) {
		
		for(EntityPlayer npc : NPC) {
			if(npc.getName().equals(nameAdjuster(name))){
				return npc.getId();
			}
		}
		
		return 0;
	}
	
	public static void printNames() {
		
		for(EntityPlayer npc : NPC) {
			
			Bukkit.broadcastMessage(npc.getName());
			
		}

		
	}
	

}
