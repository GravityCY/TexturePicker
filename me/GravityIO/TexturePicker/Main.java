package me.GravityIO.TexturePicker;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import me.GravityIO.TexturePicker.Maps.Events.PlaceMap;

public class Main extends JavaPlugin {

	public void onEnable() {
		System.out.println(ChatColor.GREEN + "Enabled " + this.getName());
		this.getServer().getPluginManager().registerEvents(new PlaceMap(this), this);
		setCommands();
	}

	private void setCommands() {
		this.getCommand("texturepicker").setExecutor(new Commands(this));
		this.getCommand("texturepicker").setTabCompleter(new Commands(this));
	}

	public void onDisable() {
		System.out.println("BYE BYE FROM TEXTUREPICKER <3");
	}

}
