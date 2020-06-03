package me.GravityIO.TexturePicker;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import me.GravityIO.TexturePicker.TextureWorld.Setup;

public class Main extends JavaPlugin {

	Setup setup = new Setup(this);

	public void onEnable() {
		System.out.println(ChatColor.GREEN + "Enabled " + this.getName());
		setCommands();
		setup();
	}

	private void setup() {
		setup.createTextureWorld();
		setup.createTextureFolder();
	}

	private void setCommands() {
		// Texture Picker (MAIN)
		this.getCommand("texturepicker").setExecutor(new Commands(this));
		this.getCommand("texturepicker").setTabCompleter(new Commands(this));
	}

	public void onDisable() {
		System.out.println("BYE BYE FROM TEXTUREPICKER <3");
	}

}
