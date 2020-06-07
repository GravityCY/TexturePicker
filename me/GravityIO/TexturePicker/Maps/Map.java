package me.GravityIO.TexturePicker.Maps;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.map.MapView;

public class Map extends ItemStack {

	private String fileName;
	@SuppressWarnings("unused")
	private String fileTree;
	private int mapId;

	public Map(File texture, String fileTree) {
		super(Material.PAPER);

		String textureName = texture.getName();
		ItemMeta thisIMeta = getItemMeta();
		thisIMeta.setDisplayName(ChatColor.GREEN + textureName);
		setItemMeta(thisIMeta);
		setFileName(textureName);
		MyMapRenderer renderer = new MyMapRenderer();
		renderer.setTexture(texture);
		MapView mapView = Bukkit.createMap(Bukkit.getWorld("world"));
		mapView.getRenderers().clear();
		mapView.addRenderer(renderer);
		setMapId(mapView.getId());
		setFileTree(fileTree);
	}

	@SuppressWarnings("deprecation")
	public Map(File texture, int mapId, String fileTree) {
		super(Material.PAPER);

		ItemMeta thisIMeta = getItemMeta();
		thisIMeta.setDisplayName(ChatColor.GREEN + texture.getName());
		setItemMeta(thisIMeta);
		setFileName(texture.getName());
		setMapId(mapId);
		MyMapRenderer renderer = new MyMapRenderer();
		renderer.setTexture(texture);
		MapView mapView = Bukkit.getMap(mapId);
		mapView.getRenderers().clear();
		mapView.addRenderer(renderer);
		setFileTree(fileTree);
	}

	private void setFileTree(String fileTree) {
		this.fileTree = fileTree;
	}

	private void setFileName(String fileName) {
		this.fileName = fileName;
	}

	private void setMapId(int mapId) {
		this.mapId = mapId;
	}

	public String getFileName() {
		return fileName;
	}

	public int getMapId() {
		return mapId;
	}
}
