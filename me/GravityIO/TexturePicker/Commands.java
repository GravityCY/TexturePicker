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

public class Commands implements CommandExecutor, TabCompleter {

	Main main;

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
				if (args[0].equalsIgnoreCase("getmap") || args[0].equalsIgnoreCase("gm")) {
					if (player.hasPermission("texturepicker.getmap")) {
						if (args.length != 1) {
							if (new File(main.getDataFolder().getAbsolutePath() + "/textures/" + args[1]).exists()) {
								File texture = new File(
										main.getDataFolder().getAbsolutePath() + "/textures/" + args[1]);
								Map map = new Map(texture);
								player.getInventory().addItem(map);
								player.sendMessage(ChatColor.GREEN + "Added map");
								return true;
							}
							player.sendMessage(ChatColor.RED + "No such file name...");
							return true;
						}
						player.sendMessage(ChatColor.RED + "Please enter the file name of which to load!");
						return true;
					}
					player.sendMessage(ChatColor.RED + "You do not have the approriate perms for this command...");
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

	String[] commandsLong = { "getmap", "gm" };

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
					if (args[0].equalsIgnoreCase("getmap") || args[0].equalsIgnoreCase("gm")) {
						List<String> textures = Arrays
								.asList(new File(main.getDataFolder().getAbsolutePath() + "/textures/").list());
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
