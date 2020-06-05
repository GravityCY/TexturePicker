package me.GravityIO.TexturePicker.TextureWorld;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.bukkit.Material;
import org.bukkit.util.Vector;
import org.yaml.snakeyaml.Yaml;

public class Converter {

	private Yaml yaml = new Yaml();
	private Map<String, Object> map;

	public Converter() {
		map = yaml.load(this.getClass().getClassLoader().getResourceAsStream("test/out.yml"));
	}

	private Map<String, List<Material>> convertedMats = new HashMap<String, List<Material>>();

	// Check if the given name is already in the converted database
	public boolean isConverted(String name) {
		return convertedMats.containsKey(name);
	}

	// Get list of materails from converetedMats list
	public List<Material> getConverted(String name) {
		return convertedMats.get(name);
	}

	// Convert the image to a list of blocks and place it in a database
	public List<Material> convertImage(File imageFile) {

		try {
			BufferedImage image = ImageIO.read(imageFile);

			List<Material> convertedMat = new ArrayList<Material>();
			for (int y = 0; y < image.getHeight(); y++) {
				for (int x = 0; x < image.getWidth(); x++) {
					Vector pixelRGB = rgbToVec(image.getRGB(x, y));
					Material mat = getMaterialFromPixel(pixelRGB);
					convertedMat.add(mat);
				}
			}
			System.out.println("Finished loading");
			return convertedMat;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	// Get pixel rgb and compare to list of blocks to find closest color
	private Material getMaterialFromPixel(Vector pixelRGB) {
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
		Material mat = Material.matchMaterial(closestName);
		return mat == null ? Material.AIR : mat;
	}

	private Vector rgbToVec(int rgb) {
		// TODO Auto-generated method stub
		int blue = rgb & 0xff;
		int green = (rgb & 0xff00) >> 8;
		int red = (rgb & 0xff0000) >> 16;

		return new Vector(red, green, blue);
	}
}
