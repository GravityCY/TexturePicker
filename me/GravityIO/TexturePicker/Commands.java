package me.GravityIO.TexturePicker;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import me.GravityIO.TexturePicker.Maps.Map;
import me.GravityIO.TexturePicker.Maps.MapHandler;

public class Commands implements CommandExecutor, TabCompleter {

	Main main;
	MapHandler mapsHandler = new MapHandler();

	public Commands(Main main) {
		this.main = main;
	}

	void deleteDirectoryRecursionJava6(File file) {
		if (file.isDirectory()) {
			File[] entries = file.listFiles();
			if (entries != null) {
				for (File entry : entries) {
					deleteDirectoryRecursionJava6(entry);
				}
			}
		}
		Bukkit.getLogger().log(Level.INFO, ("Deleting " + file.getName() + "..."));
		file.delete();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		// TODO Auto-generated method stub
		if (!(sender instanceof Player))
			return true;
		Player player = (Player) sender;
		if (cmd.getName().equalsIgnoreCase("texturepicker")) {
			if (player.hasPermission("texturepicker.main")) {

				if (args[0].equalsIgnoreCase("createmap") || args[0].equalsIgnoreCase("cmap")) {
					if (player.hasPermission("texturepicker.createmap")) {
						if (args.length != 1) {
							if (new File(main.getDataFolder().getAbsolutePath() + "/textures/" + args[1]).exists()) {
								File texture = new File(
										main.getDataFolder().getAbsolutePath() + "/textures/" + args[1]);
								if (!MapHandler.contains(texture.getName())) {
									MapHandler.createMap(texture);
									player.sendMessage(ChatColor.GREEN + "Created " + texture.getName() + ".");
									return true;
								}
								player.sendMessage(ChatColor.RED + "Map is already loaded...");
								return true;
							}
							player.sendMessage(ChatColor.RED + "File " + args[1] + " was not found...");
							return true;
						}
						player.sendMessage(ChatColor.RED + "This command requires an argument...");
						return true;
					}
					player.sendMessage(ChatColor.RED + "You do not have the approriate perms for this command...");
					return true;
				}

				if (args[0].equalsIgnoreCase("getmap") || args[0].equalsIgnoreCase("gmap")) {
					if (player.hasPermission("texturepicker.getmap")) {
						if (args.length != 1) {
							String texture = args[1];

							Map map = null;
							if (MapHandler.contains(texture)) {
								map = MapHandler.getMap(texture);
								player.getInventory().addItem(map);
								player.sendMessage(ChatColor.GREEN + "Gave " + texture + ".");
								return true;
							}
							player.sendMessage(ChatColor.RED + texture + " is not loaded...");
							return true;
						}
						player.sendMessage(ChatColor.RED + "This command requires an argument...");
						return true;
					}
					player.sendMessage(ChatColor.RED + "Please enter the file name of which to load!");
					return true;
				}

				if (args[0].equalsIgnoreCase("removemap") || args[0].equalsIgnoreCase("rmap")) {
					if (player.hasPermission("texturepicker.removemap")) {
						if (args.length != 1) {
							String texture = args[1];

							if (MapHandler.contains(texture)) {
								MapHandler.removeMap(texture);
								player.sendMessage(ChatColor.GREEN + "Removed " + texture + ".");
								return true;
							}
							player.sendMessage(ChatColor.RED + texture + " is not loaded...");
							return true;
						}
						player.sendMessage(ChatColor.RED + "This command requires an argument...");
						return true;
					}
					player.sendMessage(ChatColor.RED + "Please enter the file name of which to load!");
					return true;
				}
				player.sendMessage(ChatColor.RED + "Unknown command...");
				return true;
			}
			player.sendMessage(ChatColor.RED + "No permission!");
			return true;
		}
		return false;
	}

	String[] commandsLong = { "getmap", "gmap", "createmap", "cmap", "removemap", "rmap" };

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player))
			return null;

		Player player = (Player) sender;
		if (cmd.getName().equalsIgnoreCase("texturepicker")) {
			if (player.hasPermission("texturepicker.main")) {
				if (args.length == 1) {
					return Arrays.asList(commandsLong);
				} else if (args.length == 2) {
					if (args[0].equalsIgnoreCase("createmap") || args[0].equalsIgnoreCase("cmap")) {
						List<String> textures = new ArrayList<String>();
						List<String> loadedTextures = MapHandler.getLoadedMapNames();
						for (String fileName : Arrays
								.asList(new File(main.getDataFolder().getAbsolutePath() + "/textures/").list())) {
							if (!loadedTextures.contains(fileName)) {
								textures.add(fileName);
							}
						}
						return textures;
					}

					if (args[0].equalsIgnoreCase("getmap") || args[0].equalsIgnoreCase("gmap")
							|| args[0].equalsIgnoreCase("removemap") || args[0].equalsIgnoreCase("rmap")) {
						List<String> textures = MapHandler.getLoadedMapNames();
						return textures;
					}
					return new ArrayList<String>(Arrays.asList(""));
				}
				return new ArrayList<String>(Arrays.asList(""));
			}
			return new ArrayList<String>(Arrays.asList(""));
		}
		return null;
	}
}
