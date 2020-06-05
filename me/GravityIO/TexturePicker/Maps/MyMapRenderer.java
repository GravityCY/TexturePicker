package me.GravityIO.TexturePicker.Maps;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.bukkit.map.MapView.Scale;

public class MyMapRenderer extends MapRenderer {

	private BufferedImage image;
	private boolean renderedBefore = false;

	@Override
	public void render(MapView mapView, MapCanvas mapCanvas, Player player) {

		if (!renderedBefore) {

			mapView.setScale(Scale.CLOSEST);
			mapView.setUnlimitedTracking(false);

			try {
				mapCanvas.drawImage(0, 0, image);
				renderedBefore = true;
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		}
		return;
	}

	public void setTexture(File textureFile) {
		try {
			image = ImageIO.read(textureFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
