package me.GravityIO.TexturePicker;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	public void onEnable() {
		System.out.println(ChatColor.GREEN + "Enabled " + this.getName());
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
