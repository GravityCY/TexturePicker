package me.GravityIO.TexturePicker.Maps;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapView;

public class Map extends ItemStack {

	public Map(File texture) {
		super(Material.FILLED_MAP);
		Renderer renderer = new Renderer();
		renderer.setTexture(texture);

		String textureName = Character.toUpperCase(texture.getName().charAt(0)) + texture.getName().substring(1);
		String textureNameFormat = textureName.substring(0, textureName.indexOf('.'));

		MapView mapView = Bukkit.createMap(Bukkit.getWorld("world"));
		mapView.getRenderers().clear();
		mapView.addRenderer(renderer);

		MapMeta mapMeta = (MapMeta) getItemMeta();
		mapMeta.setMapView(mapView);
		mapMeta.setDisplayName(ChatColor.GREEN + textureNameFormat);
		setItemMeta(mapMeta);
	}
}
