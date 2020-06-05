package me.GravityIO.TexturePicker.Maps;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

public class Renderer extends MapRenderer {

	BufferedImage image;

	public void setTexture(File textureFile) {
		try {
			image = ImageIO.read(textureFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void render(MapView mapView, MapCanvas mapCanvas, Player player) {
		try {
			mapCanvas.drawImage(0, 0, image);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
