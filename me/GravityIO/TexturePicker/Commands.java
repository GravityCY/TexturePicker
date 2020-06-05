package me.GravityIO.TexturePicker;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

import javax.imageio.ImageIO;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapView;

import me.GravityIO.TexturePicker.Maps.Renderer;
import me.GravityIO.TexturePicker.TextureWorld.ChunkHandler;
import me.GravityIO.TexturePicker.TextureWorld.Converter;

public class Commands implements CommandExecutor, TabCompleter {

	Main main;

	final Converter converter = new Converter();
	final ChunkHandler chunkHandler = new ChunkHandler(main);

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
				if (args.length >= 1) {
					// teleports to texture world
					if (args[0].equalsIgnoreCase("textureworld") || args[0].equalsIgnoreCase("tw")) {
						if (main.getServer().getWorld("TextureWorld") != null) {
							player.teleport(main.getServer().getWorld("TextureWorld").getSpawnLocation());
							player.sendMessage(ChatColor.GREEN + "Teleporting to texture world...");
							return true;
						}
						player.sendMessage(ChatColor.RED + "No texture world...");
						return true;
					}
					// teleports to def world
					if (args[0].equalsIgnoreCase("normalworld") || args[0].equalsIgnoreCase("nw")) {
						if (main.getServer().getWorld("world") != null) {
							player.teleport(main.getServer().getWorld("world").getSpawnLocation());
							player.sendMessage(ChatColor.GREEN + "Teleporting to default world...");
							return true;
						}
						player.sendMessage(ChatColor.RED + "Could not find default world...");
						return true;
					}
					// deletes to texture world
					if (args[0].equalsIgnoreCase("deletetextureworld") || args[0].equalsIgnoreCase("deltw")) {
						if (main.getServer().getWorld("TextureWorld") != null) {
							if (player.getWorld().equals(main.getServer().getWorld("TextureWorld"))) {
								player.teleport(main.getServer().getWorld("world").getSpawnLocation());
								player.sendMessage(ChatColor.GREEN + "Teleporting to default world...");
							}
							main.getServer().unloadWorld(main.getServer().getWorld("TextureWorld"), false);
							deleteDirectoryRecursionJava6(new File("TextureWorld"));
							player.sendMessage(ChatColor.GREEN + "Texture World has been deleted...");
							return true;
						}
						player.sendMessage(ChatColor.RED + "Could not find texture world...");
						return true;
					}
					// genreates to texture world
					if (args[0].equalsIgnoreCase("generatetextureworld") || args[0].equalsIgnoreCase("gentw")) {
						if (main.getServer().getWorld("TextureWorld") == null) {
							main.setup.createTextureWorld();
							player.sendMessage(ChatColor.GREEN + "Created texture world...");
							return true;
						}
						player.sendMessage(ChatColor.RED + "Could not create TextureWorld...");
						return true;
					}
					// loads texture will automatically convert it to blocks
					if (args[0].equalsIgnoreCase("lt") || args[0].equalsIgnoreCase("loadtexture")) {
						if (player.hasPermission("texturepicker.loadtexture")) {
							if (main.getServer().getWorld("TextureWorld") != null) {
								if (args.length != 1) {
									File texture = new File(
											main.getDataFolder().getAbsolutePath() + "/textures/" + args[1]);
									if (texture.exists()) {
										try {
											BufferedImage image = ImageIO.read(texture);
											if (image.getWidth() != 128 || image.getHeight() != 128) {
												player.sendMessage(ChatColor.RED + "Image must be 128x128!");
												return true;
											}
										} catch (IOException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										if (!chunkHandler.isLoaded(args[1])) {
//											converter.convertImage(texture);
											chunkHandler.loadImage(texture);
											player.sendMessage(ChatColor.GREEN + "Loading texture...");
											return true;
										}
										player.sendMessage(ChatColor.RED + "Texture already loaded!");
										return true;
									}
									player.sendMessage(ChatColor.RED + "No such file name...");
									return true;
								}
								player.sendMessage(ChatColor.RED + "Please enter the file name of which to load!");
								return true;
							}
							player.sendMessage(ChatColor.RED + "Could not find texture world...");
							return true;
						}
					}
					// unloads texture
					if (args[0].equalsIgnoreCase("ut") || args[0].equalsIgnoreCase("unloadtexture")) {
						if (player.hasPermission("texturepicker.unloadtexture")) {
							if (main.getServer().getWorld("TextureWorld") != null) {
								if (args.length != 1) {
									if (chunkHandler.contains(args[1])) {
										chunkHandler.unloadImage(args[1]);
										player.sendMessage(ChatColor.GREEN + "Unloading texture...");
										return true;
									}
									player.sendMessage(ChatColor.RED + "No such file name...");
									return true;
								}
								player.sendMessage(ChatColor.RED + "Please enter the file name of which to load!");
								return true;
							}
							player.sendMessage(ChatColor.RED + "Could not find texture world...");
							return true;
						}
						player.sendMessage(ChatColor.RED + "You do not have the approriate perms for this command...");
						return true;
					}

					if (args[0].equalsIgnoreCase("getmap") || args[0].equalsIgnoreCase("gm")) {
						if (player.hasPermission("texturepicker.getmap")) {
							if (main.getServer().getWorld("TextureWorld") != null) {
								if (args.length != 1) {
									if (new File(main.getDataFolder().getAbsolutePath() + "/textures/" + args[1])
											.exists()) {
										Renderer renderer = new Renderer();
										renderer.setTexture(new File(main.getDataFolder().getAbsolutePath() + "/textures/" + args[1]));
										ItemStack map = new ItemStack(Material.FILLED_MAP);
										MapView mapView = Bukkit.createMap(Bukkit.getWorld("world"));
										mapView.getRenderers().clear();
										mapView.addRenderer(renderer);

										MapMeta mapMeta = ((MapMeta) map.getItemMeta());
										mapMeta.setMapView(mapView);

										map.setItemMeta(mapMeta);

										player.getInventory().addItem(map);
										return true;
									}
									player.sendMessage(ChatColor.RED + "No such file name...");
									return true;
								}
								player.sendMessage(ChatColor.RED + "Please enter the file name of which to load!");
								return true;
							}
							player.sendMessage(ChatColor.RED + "Could not find texture world...");
							return true;
						}
						player.sendMessage(ChatColor.RED + "You do not have the approriate perms for this command...");
						return true;
					}
					player.sendMessage(ChatColor.RED + "Unknown command...");
					return true;
				}
				player.sendMessage(ChatColor.RED + "At least 1 argument!");
				return true;
			}
			player.sendMessage(ChatColor.RED + "No permission!");
			return true;
		}
		return false;
	}

	String[] commandsLong = { "textureworld", "normalworld", "deletetextureworld", "generatetextureworld",
			"loadtexture", "unloadtexture", "reload" };

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
					if (args[0].equalsIgnoreCase("loadtexture") || args[0].equalsIgnoreCase("lt")) {
						List<String> textures = new ArrayList<String>();
						for (String texture : Arrays.asList(new File(main.getDataFolder(), "textures").list())) {
							if (!converter.isConverted(texture)) {
								textures.add(texture);
							}
						}
						return textures;
					}
					if (args[0].equalsIgnoreCase("unloadtexture") || args[0].equalsIgnoreCase("ut")) {
						List<String> textures = new ArrayList<String>();
						for (String name : chunkHandler.getLoadedChunkNames())
							textures.add(name);
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
