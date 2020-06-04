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

	
	// Check if the given name is already in the converted database
	public boolean isConverted(String name) {

		return false;
	}

	// Convert the image to a list of blocks and place it in a database
	public void convertImage(File imageFile) {

		try {
			BufferedImage image = ImageIO.read(imageFile);

			for (int y = 0; y < image.getHeight(); y++) {
				for (int x = 0; x < image.getWidth(); x++) {
					Vector pixelRGB = rgbToVec(image.getRGB(x, y));
					String closestName = getBlockFromPixel(pixelRGB);
				}
			}
			System.out.println("Finished loading");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Get pixel rgb and compare to list of blocks to find closest color
	private String getBlockFromPixel(Vector pixelRGB) {
		String closestName = null;
		Vector closestRgb = null;
		for () {
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
}
