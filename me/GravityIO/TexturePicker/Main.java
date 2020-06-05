package me.GravityIO.TexturePicker;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import me.GravityIO.TexturePicker.TextureWorld.ChunkHandler;
import me.GravityIO.TexturePicker.TextureWorld.Setup;

public class Main extends JavaPlugin {

	Setup setup = new Setup(this);
	ChunkHandler chunkHandler = new ChunkHandler(this);

	public void onEnable() {
		System.out.println(ChatColor.GREEN + "Enabled " + this.getName());
		setCommands();
		setup();
	}

	private void setup() {
		setup.createTextureWorld();
		setup.createTextureFolder();
		chunkHandler.createChunkDataYml();
	}

	private void setCommands() {

		this.getCommand("texturepicker").setExecutor(new Commands(this));
		this.getCommand("texturepicker").setTabCompleter(new Commands(this));
	}

	public void onDisable() {
		System.out.println("BYE BYE FROM TEXTUREPICKER <3");
	}

}
