package me.GravityIO.TexturePicker;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import me.GravityIO.TexturePicker.Maps.Events.PhysHangingBreak;
import me.GravityIO.TexturePicker.Maps.Events.PlayerBreakMap;
import me.GravityIO.TexturePicker.Maps.Events.PlayerCreativeInteract;
import me.GravityIO.TexturePicker.Maps.Events.PlayerPlaceMap;

public class Main extends JavaPlugin {

	public void onEnable() {
		System.out.println(ChatColor.GREEN + "Enabled " + this.getName());
		setListeners();
		setCommands();
	}

	private void setListeners() {
		this.getServer().getPluginManager().registerEvents(new PlayerPlaceMap(this), this);
		this.getServer().getPluginManager().registerEvents(new PlayerBreakMap(), this);
		this.getServer().getPluginManager().registerEvents(new PlayerCreativeInteract(), this);
		this.getServer().getPluginManager().registerEvents(new PhysHangingBreak(), this);
	}

	private void setCommands() {
		this.getCommand("texturepicker").setExecutor(new Commands(this));
		this.getCommand("texturepicker").setTabCompleter(new Commands(this));
	}

	public void onDisable() {
		System.out.println("BYE BYE FROM TEXTUREPICKER <3");
	}

}
