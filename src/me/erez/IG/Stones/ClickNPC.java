package me.erez.IG.Stones;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.erez.IG.Main;

public class ClickNPC implements Listener {
	
    private Main plugin;
    
    public ClickNPC(Main plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
	
	@EventHandler
	public void onClick(RightClickNPC event) {

	}

}
