package me.GravityIO.TexturePicker.Maps;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

public class Renderer extends MapRenderer {

	File texture;

	public void setTexture(File texture) {
		this.texture = texture;
	}

	@Override
	public void render(MapView mapView, MapCanvas mapCanvas, Player player) {
		// TODO Auto-generated method stub
		try {
			mapCanvas.drawImage(0, 0, ImageIO.read(texture));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
