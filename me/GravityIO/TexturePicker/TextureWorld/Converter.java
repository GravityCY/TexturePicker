package me.GravityIO.TexturePicker.TextureWorld;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
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

	

	public boolean isLoaded(String imageName) {

		return chunkData.getConfigurationSection("chunks") != null
				? chunkData.getConfigurationSection("chunks").contains(imageName)
				: false;
	}

	public Set<String> getLoaded() {
		return chunkData.getConfigurationSection("chunks").getKeys(false);
	}
}
