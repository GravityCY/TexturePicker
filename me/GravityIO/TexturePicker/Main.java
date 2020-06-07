package me.GravityIO.TexturePicker;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import me.GravityIO.TexturePicker.Maps.Map;
import me.GravityIO.TexturePicker.Maps.MapHandler;
import me.GravityIO.TexturePicker.Maps.Events.PhysHangingBreak;
import me.GravityIO.TexturePicker.Maps.Events.PlayerBreakMap;
import me.GravityIO.TexturePicker.Maps.Events.PlayerCreativeInteract;
import me.GravityIO.TexturePicker.Maps.Events.PlayerPlaceMap;

public class Main extends JavaPlugin {

	final File textureFolder = new File(getDataFolder().getAbsoluteFile() + "/textures/");

	@Override
	public void onEnable() {
		System.out.println(ChatColor.GREEN + "Enabled " + this.getName());
		setListeners();
		setCommands();
		loadAllImages();
	}

	@Override
	public void onDisable() {
		saveListToFile();
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

	private void saveListToFile() {
		for (Map map : MapHandler.getLoadedMaps()) {
			this.getConfig().set(map.getFileName().replace('.', '_'), map.getMapId());
		}
		this.saveConfig();
	}

	private void loadAllImages() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				List<File> textures = getTextureFiles(textureFolder);

				for (File texture : textures) {
					String path = getPathFromTextureFolder(texture);
					System.out.println(path);
					// If this is a new texture that wasnt in file before
					if (!getConfig().contains(texture.getName().replace('.', '_'))) {
						MapHandler.createMap(texture, path);
						System.out.println("Creating new map... " + texture.getName());
						// Else if this a previous texture in file
					} else {
						int prevMapId = getConfig().getInt(texture.getName().replace('.', '_'));
						// If the mapViews renderers does not contain mine meaning this was a restart
						// Need to Bukkit.getMapId(num).addrenderer(myRenderer);
						System.out.println("Using pre-existing map id with my renderer... " + texture.getName());
						MapHandler.loadMapId(texture, prevMapId, path);
					}
				}
			}
		}).start();

	}

	static public String getPathFromTextureFolder(File texture) {
		int index = texture.getAbsolutePath().indexOf("TexturePicker\\textures") + 23;
		int lastIndex = texture.getAbsolutePath().length() - texture.getName().length() - 1;

		return texture.getAbsolutePath().substring(index, lastIndex);
	}

	static public List<File> getTextureFiles(File textureFolder) {
		List<File> textureFiles = new ArrayList<File>();
		for (File texture : textureFolder.listFiles()) {
			if (!texture.isDirectory()) {
				textureFiles.add(texture);
			} else {
				textureFiles.addAll(getTextureFiles(texture));
			}
		}
		return textureFiles;
	}

}
