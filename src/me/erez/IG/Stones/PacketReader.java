package me.erez.IG.Stones;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.MessageToMessageDecoder;
import me.erez.IG.Main;
import me.erez.IG.NPC;
import net.minecraft.server.v1_16_R3.EntityPlayer;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketPlayInUseEntity;

public class PacketReader {
	
	Channel channel;
	public static Map<UUID, Channel> channels = new HashMap<UUID, Channel>();
	
	public void inject(Player player) {
		CraftPlayer craftPlayer = (CraftPlayer) player;
		channel = craftPlayer.getHandle().playerConnection.networkManager.channel;
		channels.put(player.getUniqueId(), channel);
		
		if(channel.pipeline().get("PacketInjector") != null) 
			return;
		
		channel.pipeline().addAfter("decoder", "PacketInjector", new MessageToMessageDecoder<PacketPlayInUseEntity>(){
			
			@Override
			protected void decode(ChannelHandlerContext channel, PacketPlayInUseEntity packet, List<Object> arg) throws Exception {
				arg.add(packet);
				readPacket(player, packet);
			}
			
		});
		
	}
	
	public void readPacket(Player player, Packet<?> packet) {
		
		System.out.println("PACKET >> " + packet);
		
		if(packet.getClass().getSimpleName().equalsIgnoreCase(("PacketPlayInUseEntity"))){
		
			int id = (int) getValue(packet, "a");
/*			
			if(getValue(packet, "action").toString().equalsIgnoreCase("ATTACK"))
				return;
			
			if(getValue(packet, "d").toString().equalsIgnoreCase("OFF_HAND"))
				return;
			
			if(getValue(packet, "action").toString().equalsIgnoreCase("INTERACT_AT"))
				return;
			
			if(getValue(packet, "action").toString().equalsIgnoreCase("INTERACT")) {
				for(EntityPlayer npc : NPC.getNPCS()) {
					if(npc.getId() == id) {
						Bukkit.getScheduler().scheduleAsyncDelayedTask(Main.getPlugin(Main.class), new Runnable() {

							@Override
							public void run() {
								Bukkit.getPluginManager().callEvent(new RightClickNPC(player, npc));
							}
							
						}, 0);
					}
				}
          }	
			
*/
			
			

			if(getValue(packet, "action").toString().equalsIgnoreCase("ATTACK")/* ||
			   getValue(packet, "d").toString().equalsIgnoreCase("OFF_HAND") ||
			   getValue(packet, "action").toString().equalsIgnoreCase("INTERACT_AT") ||
			   getValue(packet, "action").toString().equalsIgnoreCase("INTERACT")*/) {
				
				for(EntityPlayer npc : NPC.getNPCS()) {
					if(npc.getId() == id) {
						Bukkit.getScheduler().runTaskLater(Main.getPlugin(Main.class), new Runnable() {

							@Override
							public void run() {
								Bukkit.getPluginManager().callEvent(new RightClickNPC(player, npc));
							}
							
						}, 0);
					}
				}
				
			}	

		}
	}
	
	public void uninject(Player player) {
		channel = channels.get(player.getUniqueId());
		if(channel.pipeline().get("PacketInjector") != null)
			channel.pipeline().remove("PacketInjector");
	}
	
	private Object getValue(Object instance, String name) {
		Object result = null;
		
		try {
			
			Field field = instance.getClass().getDeclaredField(name);
			field.setAccessible(true);
			
			result = field.get(instance);
			
			field.setAccessible(false);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
		
		
	}
	
	
}
