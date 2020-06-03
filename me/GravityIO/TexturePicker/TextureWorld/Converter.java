package me.GravityIO.TexturePicker.TextureWorld;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.util.Vector;
import org.yaml.snakeyaml.Yaml;

import me.GravityIO.TexturePicker.Main;

public class Converter {

	File chunkDataFile = new File(Main.getPlugin(Main.class).getDataFolder(), "chunkData.yml");
	YamlConfiguration chunkData = new YamlConfiguration();

	public Converter() {
		// TODO Auto-generated constructor stub
		try {
			chunkData.options().pathSeparator('#');
			chunkData.load(chunkDataFile);
			chunkData.options().pathSeparator('#');
			if (!chunkDataFile.exists())
				chunkDataFile.createNewFile();

		} catch (IOException | InvalidConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	public boolean isLoaded(String imageName) {
		return chunkData.getConfigurationSection("chunks") != null
				? chunkData.getConfigurationSection("chunks").contains(imageName)
				: false;
	}

	public void loadImage(File imageFile) {

		Yaml yaml = new Yaml();
		Map<String, Object> map = yaml.load(getClass().getClassLoader().getResourceAsStream("test/out.yml"));

		try {
			BufferedImage image = ImageIO.read(imageFile);
			int totalChunks = estimateChunk(imageFile.getName());
			int currentChunk = totalChunks - 1;

			for (int y = 0; y < image.getHeight(); y++) {
				for (int x = 0; x < image.getWidth(); x++) {
					Vector pixelRGB = rgbToVec(image.getRGB(x, y));
					String closestName = getBlockFromPixel(map, pixelRGB);
					placeInChunk(x, y, currentChunk, closestName);
				}
			}
			System.out.println("Finished loading");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String getBlockFromPixel(Map<String, Object> map, Vector pixelRGB) {
		// TODO Auto-generated method stub
		String closestName = null;
		Vector closestRgb = null;
		for (Map.Entry<String, Object> mapo : map.entrySet()) {
			String thisName = mapo.getKey();
			String rgbs = mapo.getValue().toString();
			rgbs = rgbs.replace(",", "   ");
			rgbs = rgbs.replace("}", "   ");
			int redd = Integer.parseInt(rgbs.substring(rgbs.indexOf("red=") + 4, rgbs.indexOf("red=") + 7).trim());
			int greenn = Integer
					.parseInt(rgbs.substring(rgbs.indexOf("green=") + 6, rgbs.indexOf("green=") + 9).trim());
			int bluee = Integer.parseInt(rgbs.substring(rgbs.indexOf("blue=") + 5, rgbs.indexOf("blue=") + 8).trim());
			Vector thisRGB = new Vector(redd, greenn, bluee);
			if (closestName == null || thisRGB.distance(pixelRGB) < closestRgb.distance(pixelRGB)) {
				closestRgb = thisRGB;
				closestName = thisName.toUpperCase();
			}
		}
		return closestName;
	}

	private Vector rgbToVec(int rgb) {
		// TODO Auto-generated method stub
		int blue = rgb & 0xff;
		int green = (rgb & 0xff00) >> 8;
		int red = (rgb & 0xff0000) >> 16;

		return new Vector(red, green, blue);
	}

	private int estimateChunk(String name) throws IOException {
		if (!chunkData.contains("chunks")) {
			chunkData.createSection("chunks");
			chunkData.set("totalchunks", (0));
		}

		chunkData.set("chunks#" + name, chunkData.getInt("totalchunks"));
		chunkData.set("totalchunks", (chunkData.getInt("totalchunks") + 1));
		try {
			chunkData.save(chunkDataFile);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return chunkData.getInt("totalchunks");
	}

	private void placeInChunk(int x, int y, int currentChunk, String closest) {
		Location loc = new Location(Bukkit.getWorld("TextureWorld"), (x - 64) + 128 * currentChunk, 3, y - 64);
		if (Material.matchMaterial(closest) != null) {
			loc.getBlock().setType(Material.matchMaterial(closest));
		} else {
			loc.getBlock().setType(Material.AIR);
		}
	}

	public void unloadImage(File texture) {

		int currentChunk = chunkData.getInt("chunks#" + texture.getName());
		chunkData.set("chunks#" + texture.getName(), null);
		chunkData.set("totalchunks", chunkData.getInt("totalchunks") - 1);
		for (int x = 0; x < 128; x++) {
			for (int y = 0; y < 128; y++) {
				new Location(Bukkit.getWorld("TextureWorld"), (x - 64) + 128 * currentChunk, 3, y - 64).getBlock()
						.setType(Material.GRASS_BLOCK);
			}
		}
		
		try {
			chunkData.save(chunkDataFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Finished unloading");
	}

	public Set<String> getLoaded() {
		return chunkData.getConfigurationSection("chunks").getKeys(false);
	}
}
